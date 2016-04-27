package hillbillies.model.expressions;

import hillbillies.model.IContainsSelected;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public abstract class Expression implements IContainsSelected{
	
	public abstract Object evaluate(World world, Unit unit, Position selectedCube) throws WorldException;
}
