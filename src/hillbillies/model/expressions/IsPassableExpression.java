package hillbillies.model.expressions;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class IsPassableExpression<T extends PositionExpression> extends BooleanExpression{
	//ADRIAAN
	@Override
	public Boolean evaluateBoolean(ContextWrapper c) throws WorldException, WrongVariableException {
		// TODO Auto-generated method stub
		Position pos = positionExpression.evaluatePosition(c);
		return pos.isPassablePos();
		
	}
	
	private final T positionExpression;
	
	public IsPassableExpression(T position) throws SyntaxException{
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
