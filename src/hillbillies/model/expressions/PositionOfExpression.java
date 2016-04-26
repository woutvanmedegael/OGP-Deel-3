package hillbillies.model.expressions;

import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class PositionOfExpression extends PositionExpression{
	private final UnitExpression unitExpression;
	
	public PositionOfExpression(Expression unit) throws SyntaxException{
		if (!(unit instanceof UnitExpression)  ){
			throw new SyntaxException();
		}
		this.unitExpression = (UnitExpression) unit;
		
	}
	@Override
	public Position evaluate(World world,Unit unit) throws WorldException {
		Unit myUnit = this.unitExpression.evaluate(world, unit);
		return new Position(myUnit.getxpos(), myUnit.getypos(), myUnit.getzpos(), world);
	}


}
