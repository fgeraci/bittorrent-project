package bt.Exceptions;

/**
 * Communication exception between the backbone and the view package.
 * @author Fernando
 *
 */

@SuppressWarnings("serial")
public class NotifyPromptException extends Exception {
	
	public NotifyPromptException(String message) {
		super(message);
	}

}
