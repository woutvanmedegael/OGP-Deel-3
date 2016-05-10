package hillbillies.model.expressions;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class NotExpression<T extends BooleanExpression> extends BooleanExpression{
	//ADRIAAN
	@Override
	public Boolean evaluateBoolean(ContextWrapper c) throws WorldException, WrongVariableException {
		// TODO Auto-generated method stub
		
		return (!notExpression.evaluateBoolean(c));
	}
	private final T notExpression;
	public NotExpression(T notExpr) throws WorldException{
		if (notExpr==null){
			throw new WorldException();
		}
		this.notExpression = notExpr;
		
	}
	@Override
	public Boolean containsSelected() {
		// TODO Auto-generated method stub
		return notExpression.containsSelected();
	}
	
	@Override
	public ArrayList<Expression<?>> getExpressions() {
		ArrayList<Expression<?>> expressions = new ArrayList<>();
		expressions.add(this.notExpression);
		return expressions;
	}

	
}
