package bt.View;



import java.util.Scanner;

import bt.Exceptions.NotifyPromptException;
import bt.Model.Bittorrent;
import bt.Model.Peer;
import bt.Utils.CommandParser;
import bt.Utils.Utilities;

/**
 * The class represents the UI between the user and the client.
 * @author Isaac Yochelson, Robert Schomburg and Fernando Geraci
 *
 */
public class UserInterface implements Runnable {
	
	private Bittorrent bittorrent;
	private Peer peer=null;
	private boolean running = true;
	private static UserInterface instance = null;
	
	/**
	 * Constructs a UserInterface instance.
	 * @param bt
	 */
	private UserInterface() {
		
		// Program's loop
		System.out.println("Connection Successful, welcome");
		System.out.println("Input help for commands");
		Thread UIThread = new Thread(this);
		UIThread.start();
	}
	
	public void run() {
		try {
			
			
			try {
				this.clientLoop();
			} catch (Exception e) { 
				System.err.println(e.getMessage());	
			}		
		} catch (Exception e) { // FATAL ERROR 
			
			Utilities.callClose();
			System.out.println(e.getMessage());	}
	}
	
	/**
	 * Alternative Instance getter.
	 * @return UserInterface instance.
	 */
	public static UserInterface getInstance() {
		if(UserInterface.instance == null) {
			UserInterface.instance = new UserInterface();
		}
		return UserInterface.instance;
	}
	
	/**
	 * This method will be the main input loop for the text based controller.
	 */
	private void clientLoop() {
		
		Scanner sc = new Scanner(System.in);
		while(running) {
			try {
				System.out.print("\n%> ");
				String nextLine = sc.nextLine();
				CommandParser.execute(nextLine);
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
	public void receiveEvent(String message) {
		System.out.println("\n"+message);
		System.out.print("%> ");
	}
	public void stopUI() {
		running = false;
	}
}
