package bt.Model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

class PeerListener implements Runnable{
	private InputStream in = null;
	private Peer parent = null;
	private boolean running = true;
	
	PeerListener (Peer parent, InputStream inStream) {
		this.in = inStream;
		this.parent = parent;
	}

	@Override
	public void run() {
		
		// Remember, this won't actually work because there is no guarantee that nextLine is a
		// single incoming message.  The TCP buffer could contain more than one message, and if
		// it does, we need to handle all of them and not just the first.
		
		byte[] tcpArray = new byte[73]; // if this is not initialized, we get a nullptrexception error.
		
		while(running) { 
			try {
				in.read(tcpArray);
				if(!parent.peerAccepted) {
					parent.validateInfoHash(tcpArray);
					System.out.println(">>> HANDSHAKE VALIDATED !!! w/ peer "+parent+" -");
					parent.showInterested();
					parent.sendBitfield();
					parent.unChoke();
				} else {
					int offset = 0;
					ByteBuffer tcpInput = ByteBuffer.wrap(tcpArray);
					ByteBuffer lineWrapper = null;
					while (offset < tcpArray.length) {
						int length = tcpInput.getInt(offset);
						byte[] currentLine = new byte[length];
						tcpInput.get(currentLine, offset + 4, length);
						switch (currentLine[0]) {
						case 0:
							parent.setChoke(true);
							break;
						case 1: // remote-peer is unchoked, start requesting
							parent.setChoke(false);
							System.out.println(">>> Peer "+parent+" just unchoked me, start requesting pieces");
							break;
						case 2:
							parent.setInterested(true);
							break;
						case 3:
							parent.setInterested(false);
							break;
						case 4:
							lineWrapper = ByteBuffer.wrap(currentLine);
							parent.haveReceived(lineWrapper.getInt(1));
							break;
						case 5:
							lineWrapper = ByteBuffer.wrap(currentLine);
							byte[] bitfield = new byte[length - 1];
							lineWrapper.get(bitfield, 1, length - 1);
							parent.receiveBitfield(bitfield);
							break;
						case 6:
							lineWrapper = ByteBuffer.wrap(currentLine);
							parent.requestReceived(lineWrapper.getInt(1),
									lineWrapper.getInt(5),
									lineWrapper.getInt(9));
							break;
						case 7:
							lineWrapper = ByteBuffer.wrap(currentLine);
							byte[] payload = new byte[length - 9];
							lineWrapper.get(payload, 9, length - 9);
							parent.getPiece(lineWrapper.getInt(1), lineWrapper.getInt(5), payload);
							break;
						case 8:
							lineWrapper = ByteBuffer.wrap(currentLine);
							parent.cancelIndex(lineWrapper.getInt(1),
									lineWrapper.getInt(5),
									lineWrapper.getInt(9));
						default:
							System.err.println("Invalid message was received: " + currentLine);
							break;
						}
					}
				}
			} catch (IOException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
				// break;
			}
			switch (nextLine[4]) {
//			switch (currentLine[4]) {
			case 0:
				parent.setChoke(true);
				break;
			case 1: // remote-peer is unchoked, start requesting
				System.out.println(">>> Peer "+parent+" just unchoked me, start requesting pieces");
				break;
			default:
				break;

//				System.err.println(e.getMessage());
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
