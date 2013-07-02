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
		if(args.length >= 2) {
			Bittorrent bittorrent = Bittorrent.getInstance(args[0], args[1]);
		} else {
			System.err.println("Insuficient arguments");
		}
	}
}
