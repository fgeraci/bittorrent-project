package bt.Exceptions;

/**
 * Custom exception for Bencoding.
 * 
 * @author Ike, Robert and Fernando
 *
 */

public class BencodingException extends Exception {
	
	/**
	 * Constructs a new BencodignException.
	 * @param error the message of the error being thrown
	 */
	public BencodingException(String error) {
		super(error);
	}

}