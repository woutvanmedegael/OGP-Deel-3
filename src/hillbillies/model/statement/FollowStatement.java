package hillbillies.model.statement;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.UnitExpression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.hillbilliesobject.unit.UnitException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class FollowStatement<T extends UnitExpression> extends ActionStatement{
	
	@Override
	public Boolean executeNext(ContextWrapper c) throws  WorldException, WrongVariableException {
		// TODO Auto-generated method stub
		c.getExecutingUnit().startFollowing(this.unitExpression.evaluateUnit(c));
		this.setExecuted(true);
		return true;
	}

	@Override
	public Boolean containsSelected() {
		// TODO Auto-generated method stub
		return unitExpression.containsSelected();
	}
	private final T unitExpression;
	public FollowStatement(T unit) throws SyntaxException{
		this.unitExpression = unit;
		
		
		
		
	}

	@Override
	public Expression<?> getExpression() {
		return (Expression)this.unitExpression;
	}

}
