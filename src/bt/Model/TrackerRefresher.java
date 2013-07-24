package bt.Model;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import bt.Utils.Bencoder2;
import bt.Utils.TorrentInfo;
import bt.Utils.Utilities;
import bt.View.UserInterface;


/**
 * This class will contact the Tracker periodically for refreshing event status and peers list.
 * @author Isaac Yochelson, Robert Schomburg and Fernando Geraci
 *
 */

public class TrackerRefresher implements Runnable {

	private boolean refresh = true;
	private TorrentInfo torrentInfo;
	private static TrackerRefresher instance = null;
	
	/**
	 * Access a single tracker refresher instance.
	 * @param TorrentInfo ti
	 * @param String[] peers
	 * @param List peerList
	 * @return
	 */
	public static TrackerRefresher getInstance(TorrentInfo ti, String[] peers, List<Peer> peerList) {
		if(TrackerRefresher.instance == null) {
			TrackerRefresher.instance = new TrackerRefresher(ti, peers, peerList);
		}
		return TrackerRefresher.instance;
	}
	
	/**
	 * Provides an already instantiated single tracker refresher instance.
	 * @return
	 */
	public static TrackerRefresher getInstance() {
		if(TrackerRefresher.instance == null) {
			throw new IllegalAccessError("Tracker Refresher was never instantiated.");
		}
		return TrackerRefresher.instance;
	}
	
	/**
	 * Constructs a tracker refresher instance.
	 * @param ti
	 * @param peers
	 * @param peerList
	 */
	private TrackerRefresher(TorrentInfo ti, String[] peers, List<Peer> peerList) {
		this.torrentInfo = ti;
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		while(this.refresh) {
			try {
				Thread.sleep(10*1000);
				String[] recentList = this.getPeerList();
				if(recentList != null) {
					UserInterface.getInstance().receiveEvent("Updating peer list...");
				}
			} catch (Exception e) { /* this should never happen */ }
		}
	}
	
	/**
	 * Refreshes the peers list.
	 * @return String[] peers list
	 */
	private String[] getPeerList() {
		
		try {
			String[] newList = null;
			Server server = Server.getInstance();
			int port = server.getPort();
			Bittorrent bt = Bittorrent.getInstance();
			String response = null;
			// create the tracker URL for the GET request

			URL tracker = new URL(
				this.torrentInfo.announce_url+
				"?info_hash="+Utilities.encodeInfoHashToURL(bt.getInfoHash())+
				"&peer_id="+bt.getPeerId()+
				"&port="+Server.getInstance().getPort()+
				"&uploaded="+ bt.getUploaded()+
				"&downloaded="+ bt.getDownloaded()+
				"&left="+ bt.getLeft()+
				"&event="+ bt.getEvent());
			// open streams
			InputStream fromServer = tracker.openStream();
			byte[] responseInBytes = new byte[512];
			// read all the response from the server
			int b = -1;
			int pos = 0;
			while((b = fromServer.read()) != -1) {
				responseInBytes[pos] = (byte)b;
				++pos;
			}
			newList = Utilities.decodeCompressedPeers((Map)Bencoder2.decode(responseInBytes));
			
			// close streams
			fromServer.close();
			return newList;
		} catch (Exception e) { return null; }
	}
	
	/**
	 * Notifies the tracker that the client is stopping.
	 */
	public void notifyClose() throws Exception {
		Server server = Server.getInstance();
		int port = server.getPort();
		Bittorrent bt = Bittorrent.getInstance();
		bt.setEvent("stopped");
		// create the tracker URL for the GET request
		URL tracker = new URL(
			this.torrentInfo.announce_url+
			"?info_hash="+Utilities.encodeInfoHashToURL(bt.getInfoHash())+
			"&peer_id="+bt.getPeerId()+
			"&port="+port+
			"&uploaded="+ bt.getUploaded()+
			"&downloaded="+ bt.getDownloaded()+
			"&left="+ bt.getLeft()+
			"&event="+ bt.getEvent());
	}
}
