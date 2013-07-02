package bt.Exceptions;

/**
 * Custom exception for Bencoding.
 * 
 * @author Yke, Rob and Fernando
 *
 */
public class BencodingException extends Exception {
	
	/**
	 * Constructs a new BencodignException.
	 * @param String error
	 */
	public BencodingException(String error) {
		super(error);
	}

}
