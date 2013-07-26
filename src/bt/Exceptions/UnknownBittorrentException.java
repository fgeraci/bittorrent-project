package bt.Exceptions;

public class UnknownBittorrentException extends Exception {

	public UnknownBittorrentException() {
		super("The client was never initialized for this torrent.");
	}

	public UnknownBittorrentException(String message) {
		super(message);
	}

	public UnknownBittorrentException(Throwable cause) {
		super(cause);
	}

	public UnknownBittorrentException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownBittorrentException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
