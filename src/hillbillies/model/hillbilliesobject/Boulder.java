package hillbillies.model.hillbilliesobject;

import hillbillies.model.Position;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

/**
 * A class boulder used to represent a boulder.
 * @value
 * @author Wout Van Medegael & Adriaan Van Gerven
 */
public class Boulder extends Load{
	/**
	 * Initialize this new boulder with given position and a random weight between 10 and 50.
	*/
	public Boulder(Position position,World world) throws WorldException {
		super(position,world);
	}	
}