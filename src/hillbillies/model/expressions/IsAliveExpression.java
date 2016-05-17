package hillbillies.model.expressions;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class IsAliveExpression<T extends IUnitExpression> extends BooleanExpression {
	//ADRIAAN
	
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
	public ArrayList<IExpression> getExpressions() {
		ArrayList<IExpression> expressions = new ArrayList<>();
		expressions.add(this.unitExpression);
		return expressions;
	}

	
}
