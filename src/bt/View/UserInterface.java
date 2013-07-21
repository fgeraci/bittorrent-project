package bt.View;



import java.util.Scanner;

import bt.Model.*;
import bt.Exceptions.NotifyPromptException;
import bt.Utils.CommandParser;
import bt.Utils.Utilities;

public class UserInterface {
	
	private Bittorrent bittorrent;
	
	private static UserInterface instance = null;
	
	private UserInterface(Bittorrent bt) {
		this.bittorrent = bt;
		// Program's loop
		System.out.println("Connection Successfull, welcome");
		System.out.println("Input help for commands");
		// client's loop OR connect directly to a client (for project 0)
		//RUBTClient.clientLoop(); /* <- Client's loop */
		try {
			// 1. connect to peers - need to remove this once working
			bittorrent.connectToPeer("128.6.171.3:6916");
			bittorrent.connectToPeer("128.6.171.4:6929");
			bittorrent.connectToPeer("128.6.171.5:6986");
			
			// 2. wait for getting unchoked.
			while(bittorrent.peersUnchoked()) {
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
			
		} catch (NotifyPromptException ne) { // to be triggered just for notification purposes.
			System.out.println(ne.getMessage());
		} catch (Exception e) { // FATAL ERROR 
			
			Utilities.callClose();
			System.out.println(e.getMessage());	}
	}
	
	public static UserInterface getInstance() {
		if(UserInterface.instance == null) {
			throw new IllegalAccessError("UserInterface never instantiated.");
		}
		return UserInterface.instance;
	}
	
	public static UserInterface getInstance(Bittorrent bt) {
		if(UserInterface.instance == null) {
			UserInterface.instance = new UserInterface(bt);
		}
		return UserInterface.instance;
	}
	
	/**
	 * This method will be the main input loop for the text based controller.
	 */
	private void clientLoop() {
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
		System.out.println("\n"+message);
		UserInterface.instance.clientLoop();
	}
}
