package hillbillies.model.expressions;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class IsAliveExpression<T extends UnitExpression> extends BooleanExpression {
	//ADRIAAN
	
	private final T unitExpression;
	@Override
	public Boolean evaluateBoolean(ContextWrapper c) throws WorldException, WrongVariableException{
		Unit myUnit = this.unitExpression.evaluateUnit(c);
		return !(myUnit.getCurrentHP()==0);
		
	}
	public IsAliveExpression(T unit) throws SyntaxException{
		this.unitExpression = unit;
	}

	@Override
	public Boolean containsSelected() {
		// TODO Auto-generated method stub
		return unitExpression.containsSelected();
	}
	
	@Override
	public ArrayList<Expression<?>> getExpressions() {
		ArrayList<Expression<?>> expressions = new ArrayList<>();
		expressions.add(this.unitExpression);
		return expressions;
	}

	
}
