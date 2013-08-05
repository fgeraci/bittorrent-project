package bt.GUIComponents;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LoadingDialog extends JDialog {
	
	/**
	 * Constructs a Loading label dialog.
	 * @param frame
	 * @param modal
	 */
	public LoadingDialog(JFrame frame, boolean modal) {
		super(frame, false);
		JPanel panel = new JPanel();
		panel.add(new JLabel(" Loadin ... "));
		panel.setBorder(BorderFactory.createRaisedBevelBorder());
		this.add(panel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * Terminates dialog.
	 */
	public void closeWindow() {
		this.dispose();
	}
}
