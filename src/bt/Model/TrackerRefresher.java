package bt.Model;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import bt.Exceptions.UnknownBittorrentException;
import bt.Utils.Bencoder2;
import bt.Utils.TorrentInfo;
import bt.Utils.Utilities;
import bt.View.ClientGUI;
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
	private int interval;
	private int min_interval;
	
	/**
	 * Access a single tracker refresher instance.
	 * @param TorrentInfo ti
	 * @param String[] peers
	 * @param List peerList
	 * @return
	 */
	public static TrackerRefresher getInstance(
			TorrentInfo ti, String[] peers, List<Peer> peerList) {
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
	private TrackerRefresher(
			TorrentInfo ti, String[] peers, List<Peer> peerList) {
		this.torrentInfo = ti;
		Thread thread = new Thread(this);
		thread.start();
	}
	
	  public void run() {
			int refresh = this.getInterval();
			int tr_interval = -1;
			int tr_min_interval = -1;
			while(this.refresh) {
				try {
					Thread.sleep(refresh*1000);
					String[] recentList = this.getPeerList();
					try {
						tr_interval = this.getInterval();
					} catch (Exception e) {}
					try {
						tr_min_interval = this.getMinInterval();
					} catch (Exception e) {}
					if (tr_min_interval > tr_interval){
						// min_interval and interval might be at odds with one another
						refresh = 2*tr_min_interval;
					}
					else {
						refresh = tr_interval;
					}
					if ((refresh > 180) || (refresh < 0))
						refresh = 180;
					if(recentList != null) {
						ClientGUI.getInstance().publishEvent(
								"min_interval = "+ tr_min_interval +
								", interval = "+ tr_interval +
								"\nUpdating tracker every "+ refresh +" seconds...");
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
			Map trackerResponse = (Map)Bencoder2.decode(responseInBytes);
			newList = Utilities.decodeCompressedPeers(trackerResponse);
			this.interval = Utilities.decodeInterval(trackerResponse);
			this.min_interval = Utilities.decodeMinInterval(trackerResponse);
			// close streams
			fromServer.close();
			return newList;
		} catch (Exception e) { return null; }
	}
	
	/**
	 * Returns the interval requested by the tracker
	 * @return integer interval
	 */
	private int getInterval() {
		return this.interval;
	}
	
	/**
	 * Returns the min_interval requested by the tracker
	 * @return integer min_interval
	 */
	private int getMinInterval() {
		return this.min_interval;
	}
	
	/**
	 * Notifies the tracker that the client is stopping.
	 * @throws UnknownBittorrentException 
	 * @throws MalformedURLException 
	 */
	public void notifyClose() throws UnknownBittorrentException, MalformedURLException {
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
