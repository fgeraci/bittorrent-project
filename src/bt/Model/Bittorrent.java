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
	private String dotTorrentFileFolder = "rsc"+File.separator;;
	
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
	private Bittorrent(String torrentFile, String saveFile) {
		// open the file
		File file = new File((this.dotTorrentFileFolder+torrentFile));
		try {
			// get file info
			this.torrentInfo = new TorrentInfo(Utilities.getBytesFromFile(file));
			this.printTorrentInfoFields();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Returns a singleton instance of the Bittorrent main client.
	 * @return Bittorrent instance
	 */
	public static Bittorrent getInstance(String torrentFile, String saveFile) {
		if(Bittorrent.instance == null) {
			Bittorrent.instance = new Bittorrent(torrentFile, saveFile);
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
		System.out.println("Announce: "+this.torrentInfo.announce_url);
		System.out.println("Info Hash: "+this.torrentInfo.info_hash);
		System.out.println("File Name: "+this.torrentInfo.file_name);
		System.out.println("File Length: "+this.torrentInfo.file_length);
		System.out.println();
	}

}
