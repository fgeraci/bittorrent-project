package bt.Model;

import java.io.IOException;
import java.io.InputStream;

class PeerListener implements Runnable{
	private InputStream in = null;
	private Peer parent = null;
	private boolean running = true;
	
	PeerListener (Peer parent, InputStream inStream) {
		in = inStream;
		this.parent = parent;
	}

	@Override
	public void run() {
		// Remember, this won't actually work because there is not guarantee that nextLine is a
		// single incoming message.  The TCP buffer could contain more than one message, and if
		// it does we need to handle all of them and not just the first.
		byte[] nextLine = null;
		while(running) {
			try {
				in.read(nextLine);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
			switch (nextLine[4]) {
			case 0:
				parent.setChoke(true);
				break;
			}
		}
	}
	
	/**
	 * This method is called by the parent of this object as the first action of its own dispose method to
	 * remove references to itself which might prevent garbage collection.
	 */
	void dispose () {
		running = false;
		in = null;
		parent = null;
	}
}
