package hillbillies.model.expressions;

import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class IsSolidExpression extends BooleanExpression {
	//ADRIAAN
	@Override
	public Boolean evaluate(World world, Unit unit, Position selectedCube) throws WorldException {
		Position pos = positionExpression.evaluate(world, unit, selectedCube);
		return (!pos.isPassablePos());
	}
private final PositionExpression positionExpression;
	
	public IsSolidExpression(Expression position) throws SyntaxException{
		if (!(position instanceof PositionExpression)){
			throw new SyntaxException();
		}
		this.positionExpression = (PositionExpression)position;
		
	}

	@Override
	public Boolean containsSelected() {
		// TODO Auto-generated method stub
		return positionExpression.containsSelected();
	}

	

	

}
