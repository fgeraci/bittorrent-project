/**
 * 
 */
package bt.Model;

/**
 * @author Isaac Yochelson, Robert Schomburg and Fernando Geraci
 * This class encapsulates an object of the class Request to hold a queue weight for the request.
 */
class WeightedRequest extends Request implements Comparable<WeightedRequest> {
	private int weight;

	/**
	 * Constructor with default weight of 1
	 * @param index index of piece to be requested
	 * @param begin offset from beginning of piece requested byte stream begins at.
	 * @param length length of requested byte stream
	 */
	WeightedRequest(int index, int begin, int length) {
		super(index, begin, length);
		this.weight = 1;
	}
	
	/**
	 * Constructor for a weighted request from an existing request with a default weight of 1.
	 * @param request request to be encapsulated in this weighted request
	 */
	WeightedRequest(Request request) {
		super(request.getIndex(), request.getBegin(), request.getLength());
		this.weight = 1;
	}
	
	/**
	 * Constructor with all fields specified
	 * Constructor with default weight parameter of 1
	 * @param index index of piece to be requested
	 * @param begin offset from beginning of piece requested byte stream begins at.
	 * @param length length of requested byte stream
	 * @param weight weight of this weighted request in the queue
	 */
	WeightedRequest(int index, int begin, int length, int weight) {
		super(index, begin, length);
		this.weight = weight;
	}
	
	/**
	 * Constructor for a weighted request from an existing request with an explicitly defined weight.
	 * @param request request to be encaplsulated in this weighted request
	 * @param weight weight of this weighted request in the queue
	 */
	WeightedRequest(Request request, int weight) {
		super(request.getIndex(), request.getBegin(), request.getLength());
		this.weight = weight;
	}
	
	/**
	 * Accessor method for the weight of this request in the queue
	 * @return weight of this request in the queue
	 */
	int getWeight() {
		return this.weight;
	}
	
	/**
	 * Mutator method to update the weight of this request in the queue.
	 * @param newWeight new weight of this request in the queue
	 */
	void update(int newWeight) {
		this.weight = newWeight;
	}

	public int compareTo(WeightedRequest o) {
		return this.weight - o.getWeight();
	}
	
	
}
