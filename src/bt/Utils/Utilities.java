package bt.Utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Map;

import bt.Model.Bittorrent;

/**
 * A pure utilities class filled of static methods for specific tasks.
 * 
 * @author Ike, Robert and Fernando
 *
 */
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
	 * It will encode the info_hash to a URL parameter recursively.
	 * @param String info_hash
	 * @return String encoded info_hash URL
	 */
	
	public static String encodeInfoHashToURL(String infoHash) {
		String encodedURL = "";
		if(infoHash.length() == 0) return infoHash;
		char ch = infoHash.charAt(0);
		if(ch == 'x') 
			encodedURL += "%"+encodeInfoHashToURL(infoHash.substring(1));
		else if(ch == '\\')
			encodedURL += encodeInfoHashToURL(infoHash.substring(1));
		else
			encodedURL += ch+encodeInfoHashToURL(infoHash.substring(1));
		return encodedURL;
	}
	
	/**
	 * Generates a random 20 character string as a peerID.
	 * @return String peer ID
	 */
	public static String generateID() {
		StringBuilder generatedID = new StringBuilder();
		char nextChar;
		for(int i = 0 ; i < 20; ++i) {
			// create a random character between 65 - 90 ASCII
			nextChar = (char)(65 + (int)(Math.random()*25));
			generatedID.append(nextChar);
		}
		System.out.println("Random ID is: "+generatedID);
		return generatedID.toString();
	}
	
	/**
	 * Returns a peer list from the ByteBuffer return.
	 * @param Map map
	 * @return
	 */
	 public static String[] decodeCompressedPeers(Map map) {
	        ByteBuffer peers = (ByteBuffer)map.get(ByteBuffer.wrap("peers".getBytes()));
	        ArrayList<String> peerURLs = new ArrayList<String>();
	        try {
	            while (true) {
	                String ip = String.format("%d.%d.%d.%d",
	                    peers.get() & 0xff,
	                    peers.get() & 0xff,
	                    peers.get() & 0xff,
	                    peers.get() & 0xff);
	                int port = peers.get() * 256 + peers.get();
	                peerURLs.add(ip + ":" + port);
	            }
	        } catch (Exception e) {
	        }
	        return peerURLs.toArray(new String[peerURLs.size()]);
	  }
	 
	 /**
	  * Terminates the client.
	  */
	 public static void callClose() {
		 // do stuff
		 try {
			 Bittorrent.getInstance().stopServer();
			 System.out.println("\n -- Client Terminated -- ");
			 System.exit(0);
		 } catch (Exception e) { /* this should never happen */	 } 
	 }
}
