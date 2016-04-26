package hillbillies.model.expressions;

import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public abstract class PositionExpression extends Expression {

	@Override
	public abstract Position evaluate(World world, Unit unit) throws WorldException;

}