package cs352.bittorrent.Model;

/**
 * 
 * @author Yke, Rob and Fernando
 * 
 * Backend will provide all the processing and interfaces
 * between the client and the server. It will also respond
 * to user commands such as login, authentication, connect,
 * disconnect, status and download.
 * Due to its role and nature, Backend is implemented as a
 * singleton.
 *
 */

public class Backend {

	private static Backend instance = null;
	
	private Backend() {}
	
	public static Backend getInstance(){
		if(Backend.instance == null) {
			Backend.instance = new Backend();
		}
		return Backend.instance;
	}
}
