package hillbillies.model.statement;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.UnitExpression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class AttackStatement<T extends UnitExpression> extends ActionStatement{
	
	private T unitExpression;
	
	public AttackStatement(T defender){
		unitExpression = defender;
		
	}

	@Override
	public Boolean containsSelected() {
		return unitExpression.containsSelected();
	}

	@Override
	public Boolean executeNext(ContextWrapper context) throws WorldException, WrongVariableException {
		context.getExecutingUnit().startAttacking(unitExpression.evaluateUnit(context));
		this.setExecuted(true);
		return true;
	}

	@Override
	public Expression<?> getExpression() {
		return (Expression<?>)this.unitExpression;
	}
}
