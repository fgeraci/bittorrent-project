package bt.View;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bt.GUIComponents.FileSelectionDialog;
import bt.Model.Bittorrent;
import bt.Utils.TorrentInfo;

/**
 * Client's graphic user interface.
 * @author Isaac Yochelson, Robert Schomburg and Fernando Geraci.
 *
 */

@SuppressWarnings("serial")
public class ClientGUI extends JFrame {
	
	/**
	 * Static singleton instance.
	 */
	public static ClientGUI instance = null;
	
	GridBagLayout gb = new GridBagLayout();
	GridBagConstraints gc;
	Bittorrent client;
	private Container container;
	private JPanel dataPanel;
	private JPanel centralPanel;
	
	// data panel members
	private JLabel labelUserIDTitle = new JLabel(" My Peer ID ");
	private JLabel labeltorrentFileTitle = new JLabel(" Torrent File ");
	private JLabel labelTorrentFileSizeTitle = new JLabel(" File Size ");
	private JLabel labelUserID = new JLabel(" ");
	private JLabel labelTorrentFileName = new JLabel(" ");
	private JLabel labelTorrentFileSize = new JLabel(" ");
	private JLabel labelClientEventTitle = new JLabel(" Client Event ");
	private JLabel labelClientEvent = new JLabel(" ");	
	
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
			int xPanel = (int)(dim.getWidth()*.6); // 80% of total length
			int yPanel = (int)(dim.getHeight()*.7); // 90 % of total height
			this.setMinimumSize(new Dimension(xPanel, yPanel));
			
			this.container = this.getContentPane();
			this.container.setLayout(this.gb);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			// initialize all layout components
			this.initLayout();
			// initialize components' decorations.
			this.initDecorations();
			
			// initialize generic window behaviors
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
		this.updateDataPanel();
		this.pack();
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
		// data panel
		this.initDataPanel();
		this.gc = new GridBagConstraints();
		this.gc.insets = new Insets(10, 5, 5, 5);
		this.gc.weightx = 1;
		this.gc.weighty = .3;
		this.gc.gridwidth = GridBagConstraints.REMAINDER;
		this.gc.fill = GridBagConstraints.HORIZONTAL;
		this.container.add(this.dataPanel, this.gc);
		// central panel
		this.initCentralPanel();
	}
	
	private void initCentralPanel() {
		
	}
	
	/**
	 * Local panel initializator.
	 */
	private void initDataPanel() {
		this.dataPanel = new JPanel();
		this.dataPanel.setLayout(new GridBagLayout());
		GridBagConstraints gcL = new GridBagConstraints();
		gcL.insets = new Insets(2,5,5,5);
		gcL.fill = GridBagConstraints.HORIZONTAL;
		gcL.weightx = 1;
		this.dataPanel.add(this.labelUserIDTitle);
		gcL.gridwidth = GridBagConstraints.REMAINDER;
		this.dataPanel.add(this.labelUserID, gcL);
		gcL = new GridBagConstraints();
		gcL.insets = new Insets(2,5,5,5);
		this.dataPanel.add(this.labeltorrentFileTitle);
		gcL.gridwidth = GridBagConstraints.REMAINDER;
		gcL.fill = GridBagConstraints.HORIZONTAL;
		gcL.weightx = 1;
		this.dataPanel.add(this.labelTorrentFileName, gcL);
		gcL = new GridBagConstraints();
		gcL.insets = new Insets(2,5,5,5);
		this.dataPanel.add(this.labelTorrentFileSizeTitle);
		gcL.gridwidth = GridBagConstraints.REMAINDER;
		gcL.fill = GridBagConstraints.HORIZONTAL;
		gcL.weightx = 1;
		this.dataPanel.add(this.labelTorrentFileSize, gcL);
		gcL = new GridBagConstraints();
		gcL.insets = new Insets(2,5,5,5);
		this.dataPanel.add(this.labelClientEventTitle);
		gcL.gridwidth = GridBagConstraints.REMAINDER;
		gcL.fill = GridBagConstraints.HORIZONTAL;
		gcL.weightx = 1;
		this.dataPanel.add(this.labelClientEvent, gcL);
	}
	
	/**
	 * Initializes data panel components.
	 */
	private void updateDataPanel() {
		try {
			if(Bittorrent.getInstance() != null) {
				Bittorrent bt = Bittorrent.getInstance();
				this.labelUserID.setText(" "+bt.getPeerId());
				this.labelTorrentFileName.setText(" "+bt.getFileName());
				this.labelTorrentFileSize.setText(" "+bt.getFileLength()+" bytes");
				this.labelClientEvent.setText(" "+bt.getEvent().toUpperCase());
			}
		} catch (Exception e) {}
	}
	
	
	/**
	 * Initializes frames and panels decorations.
	 */
	private void initDecorations() {
		this.dataPanel.setBorder(BorderFactory.createTitledBorder("Torrent & Tracker Data"));
		this.labelTorrentFileSizeTitle.setHorizontalAlignment(JLabel.LEFT);
		this.labelUserID.setBorder(BorderFactory.createLoweredBevelBorder());
		this.labelUserID.setFont(new Font("Courrier", Font.ITALIC, 14));
		this.labelTorrentFileName.setBorder(BorderFactory.createLoweredBevelBorder());
		this.labelTorrentFileName.setFont(new Font("Courrier", Font.ITALIC, 14));
		this.labelTorrentFileSize.setBorder(BorderFactory.createLoweredBevelBorder());
		this.labelTorrentFileSize.setFont(new Font("Courrier", Font.ITALIC, 14));
		this.labelClientEvent.setBorder(BorderFactory.createLoweredBevelBorder());
		this.labelClientEvent.setFont(new Font("Courrier", Font.ITALIC, 14));
	}
	
	/**
	 * Initializes components behaviors.
	 */
	private void initBehaviors() {
		
	}

}
