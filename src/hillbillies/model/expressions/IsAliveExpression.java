package hillbillies.model.expressions;

import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class IsAliveExpression extends BooleanExpression {
	//ADRIAAN
	
	private final UnitExpression unitExpression;
	@Override
	public Boolean evaluate(World world, Unit unit, Position selectedCube) throws WorldException {
		Unit myUnit = this.unitExpression.evaluate(world, unit, selectedCube);
		return !(myUnit.getCurrentHP()==0);
		
	}
	public IsAliveExpression(Expression unit) throws SyntaxException{
		if (!(unit instanceof UnitExpression)){
			throw new SyntaxException();
		}
		this.unitExpression = (UnitExpression) unit;
	}

	@Override
	public Boolean containsSelected() {
		// TODO Auto-generated method stub
		return unitExpression.containsSelected();
	}

	
}
