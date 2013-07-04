package bt.Model;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayDeque;
import java.util.Queue;


/**
 * Creates a connection with a peer to download a file.
 * @author Ike, Robert and Fernando
 *
 */

public class Peer implements Runnable {

	private boolean choked = true;
	private boolean interested = false;
	private Socket dataSocket = null;
	/**
	 * interestedQueue is a maintained list of the piece requests a particular peer has made to this client.
	 * When there is space on the outgoing TCP queue, and the connection is not choked, the oldest value in
	 * this queue will be sent to the peer.
	 */
	private Queue <Integer> interestedQueue;
	/**
	 * This is a constructor for a Peer taking the address and port as parameters.  The address and port of a
	 * peer object are immutable during running, and therefore can only be set with this constructor.
	 * @param address Address of the peer this object represents.
	 * @param port Port on which to contact the peer which this object represents.
	 * @throws UnknownHostException If the address cannot be resolved to a host, this exception will be thrown.
	 * @throws IOException If a connection cannot be opened to this host, this exception will be thrown.
	 */
	public Peer(final String address, final int port) throws UnknownHostException, IOException {
		interestedQueue = new ArrayDeque <Integer> ();
		dataSocket = new Socket(address, port);
	}
	
	/**
	 * This method will request a piece of the file from the peer this object represents so that
	 * the piece will be added to the queue of interested pieces that peer maintains for this client.
	 * @param piece The piece of the file to be requested.
	 * @return true if the request is acknowledged before timeout, false otherwise.
	 */
	boolean showInterested (int piece) {
		return false;
	}
	
	/**
	 * This method will notify this client that we have successfully completed the transfer of a piece of
	 * the file from some peer.  The peer this object represents will therefore be able to remove this
	 * piece from the queue of interested pieces it is maintaining for this client.
	 * @param piece The piece of the file which has been completed.
	 */
	void showFinished (int piece) {
		//stub
	}
	
	/**
	 * Sets the interested bit flag on this peer's connection.
	 * @param value Value for interested flag.
	 */
	void setInterested (boolean value) {
		interested = value;
	}
	
	/**
	 * Sets the choked bit flag on this peer's connection.
	 * @param value Value for the choked flag.
	 */
	void setChoke (boolean value) {
		choked = value;
	}
	
	public void run() {
		
	}
	
}
