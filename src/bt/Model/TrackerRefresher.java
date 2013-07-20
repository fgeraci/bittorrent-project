package bt.Model;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import bt.Utils.Bencoder2;
import bt.Utils.TorrentInfo;
import bt.Utils.Utilities;
import bt.View.UserInterface;

public class TrackerRefresher implements Runnable {

	private boolean refresh = true;
	private TorrentInfo torrentInfo;
	
	public TrackerRefresher(TorrentInfo ti, String[] peers, List<Peer> peerList) {
		this.torrentInfo = ti;
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		while(this.refresh) {
			try {
				Thread.sleep(15*1000);
				String[] recentList = this.getPeerList();
				if(recentList != null) {
					UserInterface.receiveEvent("Updating peer list...");
				}
			} catch (Exception e) { /* this should never happen */ }
		}
	}
	
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
				"&port="+port+
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
}
