package bt.Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;



public class Bittorrent implements Runnable {
	
	// static Bittorrent instance
	private static Bittorrent instance = null;
	
	// fields
	private String dotTorrentFilePath = "";
	private DataInputStream readFromServer;
	private DataOutputStream writeToServer;
	private Socket clientSocket;
	
	// constructor
	private Bittorrent(String filePath) { this.dotTorrentFilePath = filePath; }
	
	public static Bittorrent getInstance(String filePath) {
		if(Bittorrent.instance == null) {
			Bittorrent.instance = new Bittorrent(filePath);
		}
		return Bittorrent.instance;
	}
	
	public void run() {
		// read .torrent File
		
		
	}

}
