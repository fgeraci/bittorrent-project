import java.util.Scanner;

import bt.Model.Bittorrent;
import bt.Utils.CommandParser;

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
		System.out.println("Input help for commands");
		while(true) {
			try {
				System.out.print("%> ");
				CommandParser.execute(sc.nextLine());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	/**
	 * Client entry point.
	 * @param args command-line arguments.
	 * 	(e.g. somefile.torrent, picture.jpg)
	 */
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		Bittorrent bittorrent = null;
		if(args.length >= 2) {
			bittorrent = Bittorrent.getInstance(args[0], args[1]);
		} else {
			System.err.println("ERROR: Insuficient arguments");
			System.out.println("usage: RUBTClient <torrent filename> <output filename>");
			System.exit(-1);
		}
		
		// Program's loop
		System.out.println("Connection Successfull, welcome");
		// client's loop OR connect directly to a client (for project 0)
		//RUBTClient.clientLoop(); /* <- Client's loop */
		try {
			// 1. connect to peers - need to remove this once working
			bittorrent.connectToPeer("128.6.171.3:6916");
			bittorrent.connectToPeer("128.6.171.4:6929");
			
			// 2. wait for executing algorithm
			while(bittorrent.peersUnchocked()) {
				System.out.println("-- Waiting for all peers to unchocke.");
				try {
					Thread.sleep(1500);
				} catch(Exception e) {
					e.getMessage();
				}
			}
			System.out.println("-- All peers are unchocked, start DownloadingAlgorithm --");
			
			// 3. start bitfields queue
			
		} catch (Exception e) { 
			bittorrent.disposePeers();
			System.out.println(e.getMessage());	}
		
	}
}