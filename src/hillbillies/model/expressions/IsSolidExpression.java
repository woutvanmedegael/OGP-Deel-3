package hillbillies.model.expressions;

import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class IsSolidExpression<T extends PositionExpression> extends BooleanExpression {
	//ADRIAAN
	@Override
	public Boolean evaluate(World world, Unit unit, Position selectedCube) throws WorldException {
		Position pos = positionExpression.evaluate(world, unit, selectedCube);
		return (!pos.isPassablePos());
	}
private final T positionExpression;
	
	public IsSolidExpression(T position) throws SyntaxException{
		this.positionExpression = position;
		
	}

	@Override
	public Boolean containsSelected() {
		// TODO Auto-generated method stub
		return positionExpression.containsSelected();
	}

	

	

}
