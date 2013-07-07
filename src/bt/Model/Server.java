package bt.Model;

import java.net.ServerSocket;

/**
 * This class will initiate a new thread as the server side of the
 * Bittorrent client.
 * @author Ike, Robert and Fernando
 *
 */

public class Server implements Runnable {
	
	/**
	 * Server's singleton instance.
	 */
	private static Server instance;
	
	/**
	 * It returns true if the server socket is bounded to an address.
	 * @return boolean true if bounded, false otherwise.
	 */
	public boolean getServerStatus() {
		return this.serverSocket.isBound();
	}
	
	/**
	 * Listening socket for the server.
	 */
	private int port;
	
	/**
	 * The server socekt.
	 */
	private ServerSocket serverSocket;
	
	/**
	 * Server constructor.
	 */
	private Server() {
		Thread serverthread = new Thread(this);
		serverthread.start();
	}
	
	public static Server getInstance() {
		if(Server.instance == null) {
			Server.instance = new Server();
		}
		return Server.instance;
	}
	
	/**
	* Returns the first available port given the range or -1 if non if available.
	* @param int left bound
	* @param int right bound
	* @return int port
	*/
	private void initServer(int from, int to) {
		int port = from;
		while(true) {
			try {
				this.serverSocket = new ServerSocket(port);
				this.port = port;
				// ss.close();
				break;
			} catch (Exception e) {
				++port;
				if(port > to) {
					port = -1;
					break;
				}
			} finally {
				try {
					// if(ss != null) ss.close(); // NOT NECESSARY
				} catch (Exception e) { System.err.println(e.getMessage()); }
			}
		}
	}
	
	/**
	 * Returns the server active port.
	 * return int port
	 */
	public int getPort() {
		return this.port;
	}
	
	/**
	 * Closes the current TCP connection.
	 * @throws Exception
	 */
	public void terminateServer() throws Exception {
		this.serverSocket.close();
	}
	
	/**
	 * Interface method for the thread.
	 */
	public void run() {
		this.initServer(6881, 6889);
		System.out.println("Server intiated OK");
		
	}

}
