package bt.Utils;

import bt.Model.Bittorrent;
import bt.Model.Peer;
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
	
	/**
	 * This dummy variable to access Peer class static methods
	 */
	static Peer agent = null;
	
	public static void execute(String command) {
		switch(command) {
		case "run":
			agent.run();
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
			break;
		default:
			throw new IllegalArgumentException("Invalid command, input help for commands.");
		}
	}
	
	/**
	 * Prints a list of available commands. It will expand as we advance.
	 */
	public static void printHelp() {
		System.out.println("\nhelp\t - Available Commands (so far):");
		System.out.println("quit\t - terminates the client.");
		System.out.println("serverstatus\t - server's bound status.");
		System.out.println("--------------------------------\n");
	}
	
}
