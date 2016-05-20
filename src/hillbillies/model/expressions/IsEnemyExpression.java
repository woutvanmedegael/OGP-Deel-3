package hillbillies.model.expressions;

import hillbillies.model.exceptions.WorldException;
import hillbillies.model.exceptions.WrongVariableException;
import hillbillies.model.task.ContextWrapper;

public class IsEnemyExpression<T extends IUnitExpression> extends BooleanExpression{
	
	private T enemy;
	
	public IsEnemyExpression(T enemy) throws WorldException{
		if (enemy==null){
			throw new WorldException();
		}
		this.enemy = enemy;
		
	}
	
	@Override
	public Boolean evaluateBoolean(ContextWrapper c) throws WorldException, WrongVariableException{
		return (enemy.evaluateUnit(c).getFaction()!=c.getExecutingUnit().getFaction());
	}

	
	
	@Override
	public IUnitExpression[] getExpressions() {
		return new IUnitExpression[]{enemy};
	}

	

}
