package bt.Interfaces;

/**
 * This interface is for the Timer class so it can measure times between activities and determine
 * whether or not is a good time to execute a tasks.
 * @author Isaac Yochelson, Robert Schomburg and Fernando Geraci.
 *
 */
public interface Timed {
	
	/**
	 * Returns true if busy false otherwise.
	 * @return
	 */
	public boolean isBusy();
	
	/**
	 * Works as some sort of function pointer.
	 */
	public void timeIsUp();
}
