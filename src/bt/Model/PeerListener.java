package bt.Model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import bt.Exceptions.UnknownBittorrentException;
import bt.Utils.Utilities;
import bt.View.ClientGUI;
import bt.View.UserInterface;

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
			if(!this.parent.peerAccepted) {
				receiveHandshake();
			} else {
				try { // ...reading InputStream to this instance
					this.readLine();
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						continue;
						}
				} catch (IOException e) {
					System.err.println(e.getMessage()); // this will be triggered if client drops connection.
					try {
						Bittorrent.getInstance().terminatePeer(parent.toString());
						break;
					} catch (Exception ex) { /* why whould this happen? */ }
				} catch (Exception e) {
					try {
						Bittorrent.getInstance().terminatePeer(parent.toString());
					} catch (UnknownBittorrentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					e.printStackTrace();
					break;
				}
			}
		}
	}
	
	/**
	 * This method is called when a message has been received and we have not yet completed handshaking
	 * successfully.
	 */
	void receiveHandshake() {
		boolean unchockedPeer = false;
		byte[] tcpArray = new byte[68];
		try {
			this.in.read(tcpArray);
			ClientGUI.getInstance().publishEvent("<<< Data received, processing...");
			ClientGUI.getInstance().publishEvent(">>> Listening from Peer : "+this.parent+"...");
			this.parent.validateInfoHash(tcpArray);
			if(parent.isIncoming()) {
				try {
					parent.handShake();
					Thread.sleep(100);
					parent.sendBitfield();
					Thread.sleep(100);
					parent.unChoke();
					unchockedPeer = true;
				} catch (Exception e) { ClientGUI.getInstance().publishEvent(e.getMessage()); }
			}
			ClientGUI.getInstance().publishEvent("-- HANDSHAKE VALIDATED !!! w/ peer "+this.parent+" -");
			// this is optional if client has no pieces
			try {
				if (!Bittorrent.getInstance().noPieces()) {
					this.parent.sendBitfield(); 
				}
			} catch (Exception e) {
				ClientGUI.getInstance().publishEvent(e.getMessage());}
			// initiate an open communication with the parent peer of this listener
			this.parent.showInterested();
			if(!unchockedPeer) {
				this.parent.unChoke();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// This just means we are ahead slightly with our timing.
			} 
		} catch (IOException e) {
			ClientGUI.getInstance().publishEvent(e.getMessage());
		}
	}
	
	/**
	 * This method handles any incoming message once handshaking has been completed successfully.
	 * @throws IOException IOException is thrown when we cannot read from the TCP buffer.
	 */
	private void readLine() throws IOException {
		byte[] lengthArray = new byte [4];
		this.in.read(lengthArray, 0, 4);
		ByteBuffer lengthBuffer = ByteBuffer.wrap(lengthArray);
		int length = lengthBuffer.getInt();
		if(length < 0 || length > 16394) {
			if (length == 0) {
				parent.updateTimeout();  // This is a keep alive
			}
			return;
		}
		byte[] tcpArray = new byte[length];
		// read message from the remote parent peer of this instance
		this.in.read(tcpArray, 0, length);
		// load message into ByteBuffer container for convenience
		ByteBuffer tcpInput = ByteBuffer.wrap(tcpArray);

		if (length == 0) {
			parent.updateTimeout();  // This is a keep alive
		} else {
			switch (tcpArray[0]) {
			case 0:	// choke
				parent.setChoke(true);
				break;
			case 1: // remote-peer is unchoked, start requesting
				parent.setChoke(false);
				ClientGUI.getInstance().publishEvent(">>> Peer "+parent+" just unchoked me");
				try {
					Bittorrent.getInstance().updateGUIState();
				} catch (UnknownBittorrentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
				byte[] bitfield = new byte[length];
				tcpInput.get(bitfield, 0, length);
				parent.receiveBitfield(bitfield);
				break;
			case 6:	// request
				byte b = tcpInput.get();
				int requestindex = tcpInput.getInt();
				int requestbegin = tcpInput.getInt();
				int requestlength = tcpInput.getInt();
				ClientGUI.getInstance().publishEvent("<<< Receiving request for index: "+requestindex
													+" begin: "+requestbegin+
													" length: "+requestlength);
				parent.requestReceived(requestindex, requestbegin, requestlength);
				parent.updateUploaded(requestlength);
				break;
			case 7:	// piece
				boolean isCompleted = false;
				try {
					isCompleted = Bittorrent.getInstance().isFileCompleted();
				} catch (Exception e) {}
				if(!isCompleted) {
					ClientGUI.getInstance().publishEvent("-- Piece received from: "+parent+", analyzing...");
					byte[] payload = new byte[length - 9];
					tcpInput.position(1);
					int index = tcpInput.getInt();
					int begin = tcpInput.getInt();
					for(int i = 0; i < payload.length; ++i) {
						payload[i] = tcpInput.get();
					}
					ClientGUI.getInstance().publishEvent("Index: "+index+" - Begin: "+begin);
					try {
						parent.getPiece(index, begin, payload);
						parent.updateDownloaded(payload.length);
					} catch (Exception e) {e.printStackTrace();}
					break;
				} else break;
			case 8:	// cancel
				parent.cancelIndex(tcpInput.getInt(5),
						tcpInput.getInt(9),
						tcpInput.getInt(13));
			default:
				ClientGUI.getInstance().publishEvent("Invalid message was received: " + tcpInput);
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
		this.in = null;
		this.parent = null;
	}
	
}

