package bt.Exceptions;

public class UnknownBittorrentException extends Exception {

	/**
	 * Custom Exception.
	 */
	public UnknownBittorrentException() {
		super("The client was never initialized for this torrent.");
	}

	/**
	 * Custom Exception.
	 */
	public UnknownBittorrentException(String message) {
		super(message);
	}

	/**
	 * Custom Exception.
	 */
	public UnknownBittorrentException(Throwable cause) {
		super(cause);
	}

	/**
	 * Custom Exception.
	 */
	public UnknownBittorrentException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Custom Exception.
	 */
	public UnknownBittorrentException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
