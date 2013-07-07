package bt.Model;

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
		
	}

}
