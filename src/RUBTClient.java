import bt.View.ClientGUI;

/**
 * This is the main entry point for the client. It will just simply
 * initialize the Bittorrent client.
 * 
 * @author Isaac Yochelson, Robert Schomburg and Fernando Geraci
 *
 */

public class RUBTClient {
	
	
	/**
	 * Client entry point.
	 * @param args command-line arguments.
	 * 	(e.g. somefile.torrent, picture.jpg)
	 */
	public static void main(String[] args) {
		try {
			// GUI initialization
			ClientGUI cGUI = ClientGUI.getInstance();
			cGUI.startGUI();
		} catch (Exception e) {}
	}
}