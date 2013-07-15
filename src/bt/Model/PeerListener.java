package bt.Model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * 
 * The main listener thread for each peer this client is connected to.
 * @author Isaac Yochelson, Robert Schomburg and Fernando Geraci
 *
 */

class PeerListener implements Runnable {
	
	private InputStream in = null;
	private Peer parent = null;
	private boolean running = true;
	
	/**
	 * This is the constructor for the PeerListener class.  There is no constructor without these
	 * parameters because a Listener must have a reference to its parent to call methods therein,
	 * and a PeerListener with no InputStream would have no meaning.  The InputStream cannot be
	 * changed after the PeerListener has been constructed.
	 * @param parent
	 * @param inStream
	 */
	PeerListener (Peer parent, InputStream inStream) {
		this.in = inStream;
		this.parent = parent;
	}

	/**
	 * This is the thread loop for a PeerListener, which receives all messages from a particular peer.
	 */
	public void run() {
		while(running) {
			if(!parent.peerAccepted) {
				receiveHandshake();
			} else {
				try {
					readLine();
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						continue;
						}
				} catch (IOException e) {
					System.err.println(e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * This method is called when a message has been received and we have not completed handshaking
	 * successfully.
	 */
	void receiveHandshake() {		
		byte[] tcpArray = new byte[68];
		try {
			in.read(tcpArray);
			System.out.println("<<< Data received, processing...");
			System.out.println(">>> Listening from Peer : "+this.parent+"...");
			parent.validateInfoHash(tcpArray);
			System.out.println("-- HANDSHAKE VALIDATED !!! w/ peer "+parent+" -");
			parent.showInterested();
			parent.sendBitfield();
			parent.unChoke();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// This just means we are off slightly on our timing.
			} 
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * This method handles any incoming message once hanshaking has been completed successfully.
	 * @throws IOException IOException is thrown when we cannot read from the TCP buffer.
	 */
	private void readLine() throws IOException {
		byte[] lengthArray = new byte [4];
		in.read(lengthArray, 0, 4);
		ByteBuffer lengthBuffer = ByteBuffer.wrap(lengthArray);
		int length = lengthBuffer.getInt();
		byte[] tcpArray = new byte[length];
		in.read(tcpArray, 0, length);
		ByteBuffer tcpInput = ByteBuffer.wrap(tcpArray);
		if (length == 0) {
			parent.updateTimout();  // This is a keep alive
			} else {
			switch (tcpArray[0]) {
			case 0:	// choke
				parent.setChoke(true);
				break;
			case 1: // remote-peer is unchoked, start requesting
				parent.setChoke(false);
				System.out.println(">>> Peer "+parent+" just unchoked me, start requesting pieces");
				try {
					Bittorrent.getInstance().simpleDownloadAlgorithm();
				} catch (Exception e) {
					System.err.println("Failed to get an instance of BitTorrent.");
				}
				break; // message was received and processed.
			case 2:	// interested
				parent.setInterested(true);
				break;
			case 3:	// not interested
				parent.setInterested(false);
				break;
			case 4:	// have
				parent.haveReceived(tcpInput.getInt());
				break;
			case 5:	// bitfield
				byte[] bitfield = new byte[length - 1];
				tcpInput.get(bitfield, 0, length - 1);
				parent.receiveBitfield(bitfield);
				break;
			case 6:	// request
				parent.requestReceived(tcpInput.getInt(),
						tcpInput.getInt(),
						tcpInput.getInt());
				break;
			case 7:	// piece
				System.out.println("-- Piece received, analyzing...");
				byte[] payload = new byte[length - 9];
				tcpInput.position(1);
				int index = tcpInput.getInt();
				int begin = tcpInput.getInt();
				for(int i = 0; i < payload.length; ++i) {
					payload[i] = tcpInput.get();
				}
				System.out.println("Index: "+index+" - Begin: "+begin);
				try {
					parent.getPiece(index, begin, payload);
				} catch (Exception e) {e.printStackTrace();}
				break;
			case 8:	// cancel
				parent.cancelIndex(tcpInput.getInt(5),
						tcpInput.getInt(9),
						tcpInput.getInt(13));
			default:
				System.err.println("Invalid message was received: " + tcpInput);
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
