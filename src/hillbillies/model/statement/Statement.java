/**
 * 
 */
package hillbillies.model.statement;

import hillbillies.model.IContainsSelected;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public abstract class Statement implements IContainsSelected{
	
	public abstract Boolean execute(World world, Unit unit, Position selectedCube) throws WorldException ;
	
	public boolean checkSelectedKeyword(){
		return true;
	}
}
