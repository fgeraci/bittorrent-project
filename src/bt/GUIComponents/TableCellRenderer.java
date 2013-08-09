package bt.GUIComponents;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * NOT UTILIZED.
 * @author Isaac Yochelson, Robert Schomburg and Fernando Geraci.
 *
 */

@SuppressWarnings("serial")
public class TableCellRenderer extends DefaultTableCellRenderer {
	
	public Component getTableCellRendererComponent(	JTable table, 
													Object value, 
													boolean isSelected,
													boolean hasFocus,
													int row,
													int column) {
		JLabel renderedCell = (JLabel)super.getTableCellRendererComponent(
				table,value,isSelected, hasFocus, row, column);
		renderedCell.setForeground(Color.RED); // just for testing purposes
		renderedCell.setHorizontalAlignment(JLabel.CENTER);
		return renderedCell;
	}

}
