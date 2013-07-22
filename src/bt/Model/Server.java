package bt.Model;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class will initiate a new thread as the server side of the
 * Bittorrent client.
 * @author Isaac Yochelson, Robert Schomburg and Fernando Geraci
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
	 * Boolean controller for the server.
	 */
	private boolean running = true;
	
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
	* Returns the first available port given the range or -1 if none are available.
	* @param from left bound
	* @param to right bound
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
			}
		}
	}
	
	/**
	 * Returns the server active port.
	 * return port number of this server
	 */
	public int getPort() {
		return this.port;
	}
	
	/**
	 * Closes the current TCP connection.
	 * @throws Exception
	 */
	public void terminateServer() throws Exception {
		this.running = false;
		this.serverSocket.close();
	}
	
	/**
	 * Interface method for the thread.
	 */
	public void run() {
		this.initServer(6881, 6889);
		while(running) {
			try {
				Socket newSocket = this.serverSocket.accept();
				InetAddress IP = newSocket.getInetAddress();
				String IPAddress = IP.getHostAddress();
			} catch (Exception e) {
				System.out.println("-- SERVER ERROR: Failed to connect to incoming peer.");
			}
		}
		System.out.println("Server initiated OK");
		
	}

}
