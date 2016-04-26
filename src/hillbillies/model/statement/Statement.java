/**
 * 
 */
package hillbillies.model.statement;

import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public abstract class Statement {
	
	public abstract void execute(World world, Unit unit, Position selectedCube) throws WorldException ;
	
	public boolean checkSelectedKeyword(){
		return true;
	}
}
