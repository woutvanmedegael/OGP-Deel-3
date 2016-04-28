package hillbillies.model.expressions;

import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class PositionOfExpression extends PositionExpression{
	private final UnitExpression unit;
	
	public PositionOfExpression(Expression unit) throws SyntaxException{
		if (!(unit instanceof UnitExpression)  ){
			throw new SyntaxException();
		}
		this.unit = (UnitExpression) unit;
		
	}
	@Override
	public Position evaluate(World world,Unit unit, Position selectedCube) throws WorldException {
		Unit myUnit = this.unit.evaluate(world, unit, selectedCube);
		return new Position(myUnit.getxpos(), myUnit.getypos(), myUnit.getzpos(), world);
	}
	@Override
	public Boolean containsSelected() {
		return unit.containsSelected();
	}


}
