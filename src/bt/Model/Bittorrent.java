package bt.Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.Socket;



public class Bittorrent {
	
	// static Bittorrent instance
	private static Bittorrent instance = null;
	
	// fields
	private String dotTorrentFilePath = "rsc"+File.separator+"cs352.png.torrent";
	private DataInputStream readFromServer;
	private DataOutputStream writeToServer;
	private Socket clientSocket;
	
	// constructor
	private Bittorrent() { 
		File torrentFile = new File(this.dotTorrentFilePath);
		
	}
	
	public static Bittorrent getInstance() {
		if(Bittorrent.instance == null) {
			Bittorrent.instance = new Bittorrent();
		}
		return Bittorrent.instance;
	}

}
