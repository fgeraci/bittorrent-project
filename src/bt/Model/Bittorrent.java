package bt.Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.Socket;

import bt.Utils.TorrentInfo;
import bt.Utils.Utilities;

/**
 * Bittorrent is the main class for the client. It will initialize the
 * Tracker data fields in its constructor for then establishing a connection
 * with it.
 * 
 * @author Yke, Rob and Fernando
 *
 */

public class Bittorrent {
	
	/**
	 * Single Bittorrent instance.
	 */
	private static Bittorrent instance = null;
	
	/**
	 * Hard coded .torrent file path.
	 */
	private String dotTorrentFilePath = "rsc"+File.separator+"cs352.png.torrent";
	
	/**
	 * Decoded .torrent file information.
	 */
	private TorrentInfo torrentInfo;
	/**
	 * Input stream from server.
	 */
	private DataInputStream readFromServer;
	/**
	 * Output stream to server.
	 */
	private DataOutputStream writeToServer;
	/**
	 * Client socket to communicate with the tracker server.
	 */
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
			this.printTorrentInfoFields();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Returns a singleton instance of the Bittorrent main client.
	 * @return Bittorrent instance
	 */
	public static Bittorrent getInstance() {
		if(Bittorrent.instance == null) {
			Bittorrent.instance = new Bittorrent();
		}
		return Bittorrent.instance;
	}
	
	/**
	 * Prints a list of decoded information from the .torrent file
	 * after the file was succesfully decoded by the Bittorrent ctor.
	 */
	private void printTorrentInfoFields() {
		System.out.println("\nTRACKER INFO (Successfully Decoded):");
		System.out.println("----------------------");
		System.out.println(this.torrentInfo.announce_url);
		System.out.println();
	}

}
