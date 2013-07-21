package bt.Model;

/**
 * This class serves as an immutable storage object for an incoming request.
 * @author Isaac Yochelson, Robert Schomburg and Fernando Geraci
 *
 */
class Request {
	private int index;
	private int begin;
	private int length;
	
	/**
	 * This is the only constructor for this class because an object of this type without these fields
	 * would lack meaning.
	 * @param index Index of the piece of the file being requested.
	 * @param begin Byte offset into the piece from which the requested byte array should begin.
	 * @param length Length of the byte array being requested.
	 */
	Request (final int index, final int begin, final int length){
		this.index = index;
		this.begin = begin;
		this.length = length;
	}
	
	/**
	 * Get the index from this object
	 * @return The index of the piece of the file being requested.
	 */
	int getIndex () {
		return this.index;
	}
	
	/**
	 * Get the beginning byte offset of the requested byte array.
	 * @return The byte offset of the beginning of the requested byte array.
	 */
	int getBegin () {
		return this.begin;
	}
	
	/**
	 * Get the requested byte array length.
	 * @return The requested byte array length.
	 */
	int getLength() {
		return this.length;
	}
	
	protected Object clone() {
		return new Request (this.index, this.begin, this.length);
	}
}
