package bt.Utils;

/**
 * It will statically handle commands and parse them while the client runs.
 * @author Yke, Robert and Fernando
 *
 */

public class CommandParser {
	
	/** 
	 * Main entry point for parsing commands.
	 * @param String command
	 */
	public static void execute(String command) {
		switch(command) {
		case "quit":
			Utilities.callClose();
		default:
			throw new IllegalArgumentException("Invalid command");
		}
	}
	
}
