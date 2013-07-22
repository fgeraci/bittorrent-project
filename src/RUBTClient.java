import java.util.Scanner;

import bt.Exceptions.NotifyPromptException;
import bt.Model.Bittorrent;
import bt.Utils.CommandParser;
import bt.Utils.Utilities;
import bt.View.UserInterface;

/**
 * This is the main entry point for the client. It will just simply
 * initialize the Bittorrent client.
 * 
 * @author Isaac Yochelson, Robert Schomburg and Fernando Geraci
 *
 */

public class RUBTClient {
	/**
	 * This method will be the main input loop for the text based controller.
	 */
	private static void clientLoop() {
		Scanner sc = new Scanner(System.in);
		while(true) {
			try {
				System.out.print("%> ");
				CommandParser.execute(sc.nextLine());
			} catch(NotifyPromptException ne) {
				System.out.println(ne.getMessage());
				System.out.print("%> ");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	/**
	 * Received a notification.
	 * @param String message
	 */
	public static void receiveEvent(String message) {
		System.out.println(message);
		RUBTClient.clientLoop();
	}
	
	/**
	 * Client entry point.
	 * @param args command-line arguments.
	 * 	(e.g. somefile.torrent, picture.jpg)
	 */
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		Bittorrent bittorrent = null;
		UserInterface ui = null;
		
		if(args.length >= 2) {
			bittorrent = Bittorrent.getInstance(args[0], args[1]);
			ui = UserInterface.getInstance(bittorrent);
			UserInterface.receiveEvent(" --> Client successfully initiated. <-- ");
		} else {
			System.err.println("ERROR: Insufficient arguments");
			System.out.println("usage: RUBTClient <torrent filename> <output filename>");
			System.exit(-1);
		}
		
		/** Program's loop
		System.out.println("Connection Successful, welcome");
		System.out.println("Input help for commands");
		// client's loop OR connect directly to a client (for project 0)
		//RUBTClient.clientLoop(); /* <- Client's loop 
		try {
			// 1. connect to peers - need to remove this once working
			bittorrent.connectToPeer("128.6.171.3:6916");
			bittorrent.connectToPeer("128.6.171.4:6929");
			bittorrent.connectToPeer("128.6.171.5:6986");
			
			// 2. wait for getting unchoked.
			while(bittorrent.peersUnchocked()) {
				System.out.println("-- Waiting for all peers to unchoke.");
				try {
					Thread.sleep(1500);
				} catch(Exception e) {
					e.getMessage();
				}
			}
			System.out.println("-- All peers are unchoked, start DownloadingAlgorithm --");
			
			// 3. start bitfields queue
			bittorrent.downloadAlgorithm();
			RUBTClient.clientLoop();
			
		} catch (NotifyPromptException ne) { // to be triggered just for notification purposes.
			
			System.out.println(ne.getMessage());
			RUBTClient.clientLoop();
			
		} catch (Exception e) { // FATAL ERROR 
			
			Utilities.callClose();
			System.out.println(e.getMessage());	}
			*/
		
	}
}