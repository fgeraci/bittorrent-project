package bt.Utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
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
	
	/**
	* Returns the first available port given the range or -1 if non if available.
	* @param int left bound
	* @param int right bound
	* @return int port
	*/
	public static int getAvailablePort(int from, int to) {
		int port = from;
		ServerSocket ss = null;
		while(true) {
			try {
				ss = new ServerSocket(port);
				ss.close();
				break;
			} catch (Exception e) {
				++port;
				if(port > to) {
					port = -1;
					break;
				}
			} finally {
				try {
					if(ss != null) ss.close();
				} catch (Exception e) { System.err.println(e.getMessage()); }
			}
		}
		return port;
	}
	
	/**
	 * Returns a String representation of a ByteBuffer.
	 * @param ByteBuffer bb
	 * @return String message
	 */
	public static String getStringFromByteBuffer(ByteBuffer bb) {
		StringBuilder message = new StringBuilder();
		int bytes;
		while(true) {
			try {
				bytes = bb.get();
				// format the product of two bytes and a bitwise AND with 0xFF
				message.append("\\x"+String.format("%02x", bytes&0xff));
			} catch (Exception e) {
				break;
			}
		}
		return message.toString();
	}
	
	/**
	 * It will encode the info_hash to a URL parameter.
	 * @param String info_hash
	 * @return String encoded info_hash URL
	 */
	
	public static String encodeInfoHashToURL(String infoHash) {
		String URLEnconded = "";
		return URLEnconded;
	}
	
}