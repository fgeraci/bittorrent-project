package bt.Model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import bt.Utils.Utilities;

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
		
		byte[] nextLine = new byte[73]; // if this is not initialized, we get a nullptrexception error.
		
		while(running) { 
			try {
				in.read(nextLine);
				if(!parent.peerAccepted) {
					parent.validateInfoHash(nextLine);
					parent.setChoke(false);
					parent.showInterested();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// break;
			}
			/*
			ByteBuffer bb = ByteBuffer.allocate(nextLine.length);
			bb.put(nextLine);
			bb.rewind();
			String message = Utilities.getStringFromByteBuffer(bb);
			System.out.println(message);
			*/
			switch (nextLine[4]) {
			case 0:
				parent.setChoke(true);
				break;
			default:
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
