package bt.Model;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import bt.Utils.TorrentInfo;
import bt.Utils.Utilities;

/**
 * Bittorrent is the main class for the client. It will initialize the
 * Tracker data fields in its constructor for then establishing a connection
 * with it.
 * 
 * @author Yke, Robert and Fernando
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
	private String rscFileFolder = "rsc"+File.separator;;
	
	/**
	 * Decoded .torrent file information.
	 */
	private TorrentInfo torrentInfo;
	
	/**
	 * Decoded info hash from the .torrent file.
	 */
	private String info_hash;
	
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
	 * Randomly generated user ID.
	 */
	private int clientID;
	
	/**
	 * Properties file.
	 */
	private Properties properties;
	
	/**
	 * The constructor will initialize all the fields given by the .torrent file.
	 */
	private Bittorrent(String torrentFile, String saveFile) //throws Exception 
	{	
		// open the file
		File file = new File((this.rscFileFolder+torrentFile));
		try {
			// get file info
			this.torrentInfo = new TorrentInfo(Utilities.getBytesFromFile(file));
			this.printTorrentInfoFields();
			this.properties = new Properties();
			this.properties.load(new FileInputStream(this.rscFileFolder+"prop.properties"));
			// request the tracker for peers
			this.sendRequestToTracker();
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
	 * It will issue and HTTP GET request to obtain bencoded information 
	 * about peers and seeds in the server.
	 */
	public String sendRequestToTracker() {
		int port = Utilities.getAvailablePort(6881, 6889);
		String response = null;
		try {
			
			// create the tracker URL for the GET request
			URL tracker = new URL(
				this.torrentInfo.announce_url+
				"?info_hash="+Utilities.encodeInfoHashToURL(this.info_hash)+
				"&peer_id="+Utilities.generateID()+
				"&port="+port+
				"&uploaded="+ this.properties.getProperty("uploaded")+
				"&downloaded="+ this.properties.getProperty("downloaded")+
				"&left="+ this.properties.getProperty("left")+
				"&event="+ this.properties.getProperty("event"));
			
			// establish the connection
			URLConnection trackerConnection = tracker.openConnection();
			// open streams
			BufferedReader fromServer = new BufferedReader(
					new InputStreamReader(trackerConnection.getInputStream()));
			
			// read all the response from the server
			String line = null;
			while((line += fromServer.readLine()) != null) {
				response+=line;
			}
			// close streams
			fromServer.close();
			// close connection
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return response;
	}
	
	/**
	 * Prints a list of decoded information from the .torrent file
	 * after the file was succesfully decoded by the Bittorrent ctor.
	 */
	private void printTorrentInfoFields() {
		this.info_hash = Utilities.getStringFromByteBuffer(this.torrentInfo.info_hash);
		System.out.println("\nTRACKER INFO (Successfully Decoded):");
		System.out.println("-----------------------------------");
		System.out.println("Announce: "+this.torrentInfo.announce_url);
		System.out.println("Info Hash: "+this.info_hash);
		System.out.println("Info Hash URL Encoded: "+Utilities.encodeInfoHashToURL(this.info_hash));
		System.out.println("File Name: "+this.torrentInfo.file_name);
		System.out.println("File Length: "+this.torrentInfo.file_length);
		System.out.println("-----------------------------------");
		System.out.println();
	}

}
