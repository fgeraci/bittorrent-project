package bt.Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.Socket;

import bt.Utils.TorrentInfo;
import bt.Utils.Utilities;



public class Bittorrent {
	
	/**
	 * Single Bittorrent instance.
	 */
	private static Bittorrent instance = null;
	
	/**
	 * Hard coded .torrent file path.
	 */
	private String dotTorrentFilePath = "rsc"+File.separator+"cs352.png.torrent";
	
	// fields
	private TorrentInfo torrentInfo;
	private DataInputStream readFromServer;
	private DataOutputStream writeToServer;
	private Socket clientSocket;
	
	/**
	 * The constructor will initialize all the fields given by the .torrent file.
	 */
	private Bittorrent() {
		// open the file
		File torrentFile = new File(this.dotTorrentFilePath);
		try {
			// get file info
			this.torrentInfo = new TorrentInfo(Utilities.getBytesFromFile(torrentFile));
			System.out.println("DEBUG: .torrent File successfully read and decoded.");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static Bittorrent getInstance() {
		if(Bittorrent.instance == null) {
			Bittorrent.instance = new Bittorrent();
		}
		return Bittorrent.instance;
	}

}
