package hillbilliesobject.unit;

/**
 * @value
 * @author Wout Van Medegael & Adriaan Van Gerven
 */
/**
 * A class for signaling illegal names for a unit.
 */
public class IllegalNameException extends UnitException{
	
	/**
	 * default serial ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Variable registering the value involved in this illegal name
	 * exception.
	 */
	public final String value;
	/**
	 * Initialize this new illegal name exception with a given value.
	 * 
	 * @param  value
	 *         The value for this new illegal name exception
	 * @post   The value of this new illegal name exception is equal
	 *         to the given value.
	 *       | new.getValue() == value
	 */

	public IllegalNameException(String value){
		this.value = value;
	}
}