package bt.GUIComponents;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * Customized filter for the JFileChooser such that it only recognizes the specified extensions.
 * @author Isaac Yochelson, Robert Schomburg and Fernando Geraci
 *
 */
public class TorrentFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if(f.isDirectory()) {
			return true;
		}
		String extension = f.toString().substring(f.toString().lastIndexOf('.'));
		if(	extension.equals(".torrent")) {
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
