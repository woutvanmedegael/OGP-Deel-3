package hillbillies.model.expressions;

import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public abstract class Expression {
	
	public abstract Object evaluate(World world, Unit unit, Position selectedCube) throws WorldException;
}
