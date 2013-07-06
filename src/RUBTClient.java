import java.util.Scanner;

import bt.Model.Bittorrent;
import bt.Utils.CommandParser;

/**
 * This is the main entry point for the client. It will just simply
 * initialize the Bittorrent client.
 * 
 * @author Ike, Robert and Fernando
 *
 */

public class RUBTClient {
	
	/**
	 * Client entry point.
	 * @param String[] command-line arguments.
	 * 	(e.g. somefile.torrent, picture.jpg)
	 */
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		if(args.length >= 2) {
			Bittorrent bittorrent = Bittorrent.getInstance(args[0], args[1]);
		} else {
			System.err.println("ERROR: Insuficient arguments");
			System.out.println("usage: RUBTClient <torrent filename> <output filename>");
			System.exit(-1);
		}
		
		// Program's loop
		System.out.println("Connection Successfull, welcome.");
		while(true) {
			try {
				System.out.print("%> ");
				CommandParser.execute(sc.nextLine());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
>>>>>>> master
