package bt.View;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import bt.GUIComponents.FileSelectionDialog;
import bt.Model.Bittorrent;

/**
 * Client's graphic user interface.
 * @author Isaac Yochelson, Robert Schomburg and Fernando Geraci.
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
	
	/**
	 * Retrieves a single instance of ClientGUI
	 * @return ClientGUI
	 */
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
			// windows size
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			int xPanel = (int)(dim.getWidth()*.8); // 80% of total length
			int yPanel = (int)(dim.getHeight()*.9); // 90 % of total height
			this.setMinimumSize(new Dimension(xPanel, yPanel));
			
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
			
		} catch(Exception e) {
			System.out.println(" << GUI initialization error >> ");
		}
		
	}
	
	/**
	 * Fires GUI up.
	 */
	public void startGUI() {
		this.loadTorrentFile();
		this.setVisible(true);
	}
	
	/**
	 * Created a dialog for prompting for torrent file.
	 * @return
	 */
	private void loadTorrentFile() {
		// calls a custom file selection dialog before starting main window.
		new FileSelectionDialog(this,true); 
	}
	
	/**
	 * Initializes GUI layout.
	 */
	private void initLayout() {
		
	}
	
	/**
	 * Initializes frames and panels decorations.
	 */
	private void initDecorations() {
		
	}
	
	/**
	 * Initializes components behaviors.
	 */
	private void initBehaviors() {
		
	}

}
