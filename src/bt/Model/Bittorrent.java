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
	 * Last event in client's activity.
	 */
	private String event;
	
	/**
	 * Bytes uploaded so far.
	 */
	private int uploaded;
	
	/**
	 * Bytes downloaded so far.
	 */
	private int downloaded;
	
	/**
	 * Bytes left to downloaded for current file.
	 */
	private int left;
	
	/**
	 * The constructor will initialize all the fields given by the .torrent file.
	 */
	private Bittorrent(String torrentFile, String saveFile) {
		// open the file
		File file = new File((this.rscFileFolder+torrentFile));
		try {
			// get file info
			this.torrentInfo = new TorrentInfo(Utilities.getBytesFromFile(file));
			this.printTorrentInfoFields();
			this.initClientState();
			this.properties.load(new FileInputStream(this.rscFileFolder+"prop.properties"));
			// request the tracker for peers
			this.sendRequestToTracker();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	private void initClientState() throws Exception {
		this.properties = new Properties();
		this.properties.load(new FileInputStream(this.rscFileFolder+"prop.properties"));
		this.event = this.properties.getProperty("event");
		this.uploaded = Integer.parseInt(this.properties.getProperty("uploaded"));
		this.downloaded = Integer.parseInt(this.properties.getProperty("downloaded"));
		this.left = Integer.parseInt(this.properties.getProperty("left"));
		
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
				"&uploaded="+ this.uploaded+
				"&downloaded="+ this.downloaded+
				"&left="+ this.left+
				"&event="+ this.event);
			
			// establish the connection
			//URLConnection trackerConnection = tracker.openConnection();
			// open streams
			BufferedReader fromServer = new BufferedReader(
					new InputStreamReader(tracker.openStream()));
			
			// read all the response from the server
			//String line = null;
			response += fromServer.readLine();
			/*while((line += fromServer.readLine()) != null) {
				response+=line;
			}*/
			System.out.println(response);
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
