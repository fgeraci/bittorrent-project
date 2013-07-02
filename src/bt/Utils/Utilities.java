package bt.Utils;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class Utilities {
	
	/**
	 * Converts an InputStream into a bytes array.
	 * @param file
	 * @return byte[] File Bytes
	 */
	
	public static byte[] getBytesFromFile(InputStream file) {
		// process bytes from file
		ArrayList<Byte> bytes = new ArrayList<Byte>();
		DataInputStream dis = new DataInputStream(file);
		try {
			int b;
			while(true) {
				b = dis.read();
				if(b == -1) break;
				else bytes.add((byte)b);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		int listLength = bytes.size();
		byte[] bytesArray = new byte[listLength];
		for(int i = 0; i < listLength; ++i) {
			bytesArray[i] = bytes.get(i);
		}
		return bytesArray;
	}
	
}