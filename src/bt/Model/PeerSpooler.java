package bt.Model;

import java.util.List;

public class PeerSpooler implements Runnable {

	Bittorrent bt;
	private boolean running = true;
	private long sleep;
	
	public PeerSpooler(Bittorrent bt, long sleep) {
		this.bt = bt;
		this.sleep = sleep;
		Thread spooler = new Thread(this);
		spooler.start();
	}

	@Override
	public void run() {
		while(running) {
			try {
				Thread.sleep((sleep));
				// spool peers
				// measure times
				// keep 3
				// choke 1
				// unchoke random peer
				this.execute();
			} catch (Exception e) { }
		}
	}
	
	private void execute() {
		List<Peer> peers = bt.getPeerList();
		int unchocked = 0;
		for(Peer p:peers) {
			if(!p.isChoked()) {
				unchocked++;
			}
		}
		if(unchocked > 4) {
			// get ranked list
			Peer[] rankedList = this.getRankedList(peers);
			int size = rankedList.length;
			if(rankedList.length > 4) {
				
			} // if not don't even bother.
			// leave first 3 unchocked
			// choke th fourth
			// select random peer
			// unchoke random peer
		}
	}
	
	/**
	 * Returns a ranked array by download rate of peers.
	 * Sorry about the insertion sort, but for this size, does it make any sense to go any bigger?
	 * @param peers
	 * @return
	 */
	private Peer[] getRankedList(List<Peer> peers) {
		Peer[] rankedList = new Peer[peers.size()];
		// same as calling to array
		for(int i = 0; i < peers.size(); i++) {
			rankedList[i] = peers.get(i);
		}
		for(int i = 1; i < peers.size(); i++) {
			Peer currPeer = rankedList[i];
			for(int u = i-1; u >= 0; --u) {
				if(currPeer.getDownloadRate() > rankedList[u].getDownloadRate()) {
					Peer tmp = rankedList[u];
					rankedList[u] = currPeer;
					rankedList[u+1] = tmp;
				} else break;
			}
		}
		return rankedList;
	}

}
