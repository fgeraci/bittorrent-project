package bt.GUIComponents;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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

@SuppressWarnings("serial")
public class AboutDialog extends JDialog {

	private JLabel labelProject = new JLabel(" CS352 - Bittorrent Project ");
	private JLabel labelSemester = new JLabel(" Summer 2013 ");
	private JLabel labelAuthors = new JLabel(" Authors: Isaac Yochelson, Robert Schomburg and Fernando Geraci ");
	private JLabel labelIsaac = new JLabel(" Isaac Yochelson ");
	private JLabel labelRobert = new JLabel("     Robert Schomburg ");
	private JLabel labelFernando = new JLabel("         Fernando Geraci "); 
	private JButton buttonClose = new JButton(" Close ");
	private JPanel panelContainer;
	
	public AboutDialog(JFrame parent, boolean modal) {
		super(parent, modal);
		initLayout();
		initBehaviors();
		this.panelContainer.setBorder(BorderFactory.createRaisedBevelBorder());
		this.setResizable(false);
		// this.setUndecorated(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private void initLayout() {
		this.setLayout(new FlowLayout());
		panelContainer = new JPanel(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(2,5,2,5);
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.HORIZONTAL;
		this.panelContainer.add(this.labelProject,gc);
		this.panelContainer.add(this.labelSemester,gc);
		this.panelContainer.add(this.labelAuthors,gc);
		this.panelContainer.add(this.labelIsaac,gc);
		this.panelContainer.add(this.labelRobert,gc);
		this.panelContainer.add(this.labelFernando);
		this.add(this.panelContainer, BorderLayout.CENTER);
		this.add(this.buttonClose, BorderLayout.SOUTH);
	}
	
	private void initBehaviors() {
		this.buttonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AboutDialog.this.dispose();
			}
		});
	}
	
}
