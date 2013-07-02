package bt.Utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class Utilities {
	
	/**
	 * Returns a byte stream from the given file.
	 * @param file
	 * @return byte[] File Bytes
	 */
	
	public static byte[] getBytesFromFile(File file) {
		ArrayList<Byte> bytes = new ArrayList<Byte>();
		DataInputStream dis;
		try {
			// open file
			dis = new DataInputStream(new FileInputStream(file));
			int b;
			// read bytes
			while(true) {
				b = dis.read();
				if(b == -1) break;
				else bytes.add((byte)b);
			}
			// close the file stream
			dis.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		// translate into byte[]
		int listLength = bytes.size();
		byte[] bytesArray = new byte[listLength];
		for(int i = 0; i < listLength; ++i) {
			bytesArray[i] = bytes.get(i);
		}

		return bytesArray;
	}
	
}