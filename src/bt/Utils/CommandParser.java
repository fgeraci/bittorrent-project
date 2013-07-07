package bt.Utils;

import bt.Model.Bittorrent;
import bt.Model.Server;

/**
 * It will statically handle commands and parse them while the client runs.
 * @author Ike, Robert and Fernando
 *
 */

public class CommandParser {
	
	/** 
	 * Main entry point for parsing commands.
	 * @param String command
	 */
	public static void execute(String command) {
		switch(command) {
		case "run":
//			bt.Model.Peer.run();
			break;
		case "quit":
			Utilities.callClose();
		case "help":
			CommandParser.printHelp();
			break;
		case "serverstatus":
			if(Server.getInstance().getServerStatus()) {
				System.out.println("Server bounded to client");
			} else {
				System.out.println("Server unbounded listening for incoming connections");
			}
		default:
			throw new IllegalArgumentException("Invalid command, input help for commands.");
		}
	}
	
	/**
	 * Prints a list of available commands. It will expand as we advance.
	 */
	public static void printHelp() {
		System.out.println("\nHELP - Available Commands (so far):");
		System.out.println("quit\t - terminates the client.");
		System.out.println("--------------------------------\n");
	}
	
}
