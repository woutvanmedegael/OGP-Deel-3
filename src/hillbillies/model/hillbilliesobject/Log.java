package hillbillies.model.hillbilliesobject;

import hillbillies.model.exceptions.WorldException;
import hillbillies.model.util.Position;
import hillbillies.model.world.World;

/**
 * A class log used to represent a boulder.
 * @value
 * @author Wout Van Medegael & Adriaan Van Gerven
 */
public class Log extends Load{
	/**
	 * Initialize this new log with given position and a random weight between 10 and 50.
	*/
	public Log(Position position, World world) throws WorldException {
		super(position,world);
	}
	

	
}