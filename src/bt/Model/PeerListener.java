package bt.Model;

import java.io.IOException;
import java.io.InputStream;

class PeerListener implements Runnable{
	private InputStream in = null;
	private Peer parent = null;
	
	PeerListener (Peer parent, InputStream inStream) {
		in = inStream;
		this.parent = parent;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		byte[] nextLine = null;
		while(true) {
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

}
