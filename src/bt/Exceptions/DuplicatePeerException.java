/**
 * 
 */
package bt.Exceptions;

/**
 * @author Isaac Yochelson, Fernando Geraci, Robert Shomburg
 *
 */
public class DuplicatePeerException extends Exception {

	/**
	 * 
	 */
	public DuplicatePeerException() {
		super("This peer is already known.");
	}

	/**
	 * @param message
	 */
	public DuplicatePeerException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DuplicatePeerException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DuplicatePeerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public DuplicatePeerException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
