package hillbillies.model.expressions;

import hillbillies.model.exceptions.WorldException;
import hillbillies.model.exceptions.WrongVariableException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.task.ContextWrapper;

public class IsAliveExpression<T extends IUnitExpression> extends BooleanExpression {
	
	private final T unitExpression;
	@Override
	public Boolean evaluateBoolean(ContextWrapper c) throws WorldException, WrongVariableException{
		Unit myUnit = this.unitExpression.evaluateUnit(c);
		return !(myUnit.getCurrentHP()==0);
		
	}
	public IsAliveExpression(T unit) throws WorldException{
		if (unit==null){
			throw new WorldException();
		}
		this.unitExpression = unit;
	}

	
	
	@Override
	public IUnitExpression[] getExpressions() {
		return new IUnitExpression[]{unitExpression};
	}

	
}
