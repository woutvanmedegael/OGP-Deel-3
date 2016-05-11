package hillbillies.model.expressions;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class IsSolidExpression<T extends PositionExpression> extends BooleanExpression {
	//ADRIAAN
	@Override
	public Boolean evaluateBoolean(ContextWrapper c) throws WorldException, WrongVariableException {
		Position pos = positionExpression.evaluatePosition(c);
		Boolean b = !pos.isPassablePos();
		System.out.println(b);
		return b;
	}
private final T positionExpression;
	
	public IsSolidExpression(T position) throws WorldException{
		if (position==null){
			throw new WorldException();
		}
		this.positionExpression = position;
		
	}

	@Override
	public Boolean containsSelected() {
		// TODO Auto-generated method stub
		return positionExpression.containsSelected();
	}
	
	@Override
	public ArrayList<Expression<?>> getExpressions() {
		ArrayList<Expression<?>> expressions = new ArrayList<>();
		expressions.add(this.positionExpression);
		return expressions;
	}

	

	

}
