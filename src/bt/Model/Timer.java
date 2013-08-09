package bt.Model;

import bt.Interfaces.Timed;

/**
 * This class will function as a generic timer for any object which implements timed interface.
 * @param <T extends Timed>
 */

public class Timer<T extends Timed> implements Runnable {
	
	private long milliseconds;
	private boolean enabled;
	private T timedObject;
	
	/**
	 * Constructs a timer object.
	 * @param milliseconds
	 * @param object
	 */
	public Timer(long milliseconds, T object) {
		this.milliseconds = milliseconds;
		this.enabled = true;
		this.timedObject = object;
		Thread timer = new Thread(this);
		timer.start();
	}
	
	public void run() {
		while(enabled) {
			try {
				Thread.sleep(this.milliseconds);
				if(!this.timedObject.isBusy()) {
					this.timedObject.timeIsUp();
				}
			} catch (Exception e) { }
		}
	}
	
}
