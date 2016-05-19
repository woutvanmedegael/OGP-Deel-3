package hillbillies.model.expressions;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.WorldException;

public class IsEnemyExpression<T extends IUnitExpression> extends BooleanExpression{
	//WOUT
	
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
