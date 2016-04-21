package hillbilliesobject;

import hillbillies.model.Position;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

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