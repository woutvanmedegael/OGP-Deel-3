package hillbillies.model.statement;

import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.UnitExpression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.hillbilliesobject.unit.UnitException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class FollowStatement extends ActionStatement{
	
	@Override
	public Boolean execute(World world, Unit unit, Position selectedCube) throws  WorldException {
		// TODO Auto-generated method stub
		unit.startFollowing(this.unitExpression.evaluate(world, unit, selectedCube));
		return true;
	}

	@Override
	public Boolean containsSelected() {
		// TODO Auto-generated method stub
		return unitExpression.containsSelected();
	}
	private final UnitExpression unitExpression;
	public FollowStatement(Expression unit) throws SyntaxException{
		if (!(unit instanceof UnitExpression)){
			throw new SyntaxException();
		}
		this.unitExpression = (UnitExpression)unit;
		
		
		
		
	}

}
