package bt.GUIComponents;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class FileSelectionDialog extends JDialog {

	private JPanel mainPanel;
	private JTextField textField;
	private JButton browseButton;
	private JButton continueButton;
	private JButton cancelButton;
	private JLabel titleLabel;
	
	
	public FileSelectionDialog(JFrame parent, boolean modal) {
		super(parent, modal);		
		this.initLayout();
		this.initDecorations();
		this.add(this.mainPanel);
		this.setUndecorated(true);
		this.initBehaviors();
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private void initLayout() {
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gc;
		this.mainPanel = new JPanel(gb);
		
		// init components
		this.titleLabel = new JLabel(" CS352 - Bittorrent Project ");
		this.textField = new JTextField(25);
		this.browseButton = new JButton(" ... ");
		this.cancelButton = new JButton(" Exit ");
		this.continueButton = new JButton(" Start Client ");
		
		gc = new GridBagConstraints();
		gc.insets = new Insets(10,5,10,5);
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.anchor = GridBagConstraints.CENTER;
		this.mainPanel.add(this.titleLabel, gc);
		
		gc = new GridBagConstraints();
		gc.insets = new Insets(5,5,10,5);
		gc.gridwidth = GridBagConstraints.RELATIVE;
		this.mainPanel.add(this.textField,gc);
		
		gc = new GridBagConstraints();
		gc.insets = new Insets(5,5,10,5);
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.anchor = GridBagConstraints.EAST;
		this.mainPanel.add(this.browseButton, gc);
		
		gc = new GridBagConstraints();
		gc.insets = new Insets(5,5,5,5);
		gc.gridwidth = GridBagConstraints.RELATIVE;
		gc.anchor = GridBagConstraints.WEST;
		this.mainPanel.add(this.cancelButton, gc);
		
		gc = new GridBagConstraints();
		gc.insets = new Insets(5,5,5,5);
		gc.gridwidth = GridBagConstraints.REMAINDER;
		this.mainPanel.add(this.continueButton, gc);
		
	}
	
	private void initBehaviors() {
		this.browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// open JBrowser
			}
		});
		
		this.continueButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				// attempt to load torrent file and fire client up.
			}
		});
		
		this.cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	
	private void initDecorations() {
		this.mainPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		this.titleLabel.setFont(new Font("Courrier", Font.BOLD, 14));
	}
}
