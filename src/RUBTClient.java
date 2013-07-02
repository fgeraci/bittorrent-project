import bt.Model.Bittorrent;

/**
 * This is the main entry point for the client. It will just simply
 * initialize the Bittorrent client.
 * 
 * @author Yke, Rob and Fernando
 *
 */

public class RUBTClient {
	
	/**
	 * Client entry point.
	 * @param String[] command-line arguments.
	 */
	public static void main(String[] args) {
		Bittorrent bittorrent = Bittorrent.getInstance();
	}
}
