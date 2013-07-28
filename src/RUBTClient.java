import java.util.Scanner;

import bt.Exceptions.NotifyPromptException;
import bt.Model.Bittorrent;
import bt.Utils.CommandParser;
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
		@SuppressWarnings("resource")
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
		
		Bittorrent bittorrent = null;
		@SuppressWarnings("unused")
		UserInterface ui = null;
		
		if(args.length >= 2) {
			bittorrent = Bittorrent.getInstance(args[0], args[1]);
			ui = UserInterface.getInstance(bittorrent);
			System.out.println(" --> Client successfully initiated. <-- ");
			clientLoop(); // THIS IS NOT OFFICIAL
		} else {
			System.err.println("ERROR: Insufficient arguments");
			System.out.println("usage: RUBTClient <torrent filename> <output filename>");
			System.exit(-1);
		}
	}
}