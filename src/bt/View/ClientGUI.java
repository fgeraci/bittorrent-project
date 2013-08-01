package bt.View;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import bt.Model.Bittorrent;
import bt.Utils.Utilities;

/**
 * Client's graphic user interface.
 * @author Fernando
 *
 */

public class ClientGUI extends JFrame {
	
	/**
	 * Static singleton instance.
	 */
	public static ClientGUI instance = null;
	GridBagLayout gb = new GridBagLayout();
	GridBagConstraints gc = new GridBagConstraints();
	Bittorrent client;
	private Container container;
	
	
	public static ClientGUI getInstance() {
		if(ClientGUI.instance == null) {
			ClientGUI.instance = new ClientGUI();
		}
		return ClientGUI.instance;
	}
	
	/**
	 * Constructs a ClientGUI object.
	 */
	private ClientGUI() {
		try {
			this.client = Bittorrent.getInstance();
			this.setMinimumSize(new Dimension(750,480));
			this.container = this.getContentPane();
			this.setLayout(this.gb);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.initLayout();
			this.initDecorations();
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {
					// Utilities.callClose();
				}
			});
			this.setLocationRelativeTo(null);
			this.setVisible(true);
		} catch(Exception e) {
			System.out.println(" << GUI initialization error >> ");
		}
		
	}
	
	/**
	 * Created a dialog for prompting for torrent file.
	 * @return
	 */
	private String loadTorrentFile() {
		return null;
	}
	
	/**
	 * Initializes GUI layout.
	 */
	private void initLayout() {
		
	}
	
	/**
	 * Initializes frames and panles decorations.
	 */
	private void initDecorations() {
		
	}
	
	/**
	 * Initilizes components behaviors.
	 */
	private void initBehaviors() {
		
	}

}
