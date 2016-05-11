package hillbillies.model.statement;

import hillbillies.model.ContextWrapper;
import hillbillies.model.IContainsSelected;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.IUnitExpression;
import hillbillies.model.expressions.UnitExpression;
import hillbillies.model.world.WorldException;

public class AttackStatement<T extends IUnitExpression & IContainsSelected> extends ActionStatement{
	
	private T unitExpression;
	
	public AttackStatement(T defender) throws WorldException{
		if (defender==null){
			throw new WorldException();
		}
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
