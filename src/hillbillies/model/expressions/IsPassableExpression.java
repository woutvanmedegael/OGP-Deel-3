package hillbillies.model.expressions;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class IsPassableExpression<T extends IPositionExpression> extends BooleanExpression{
	//ADRIAAN
	@Override
	public Boolean evaluateBoolean(ContextWrapper c) throws WorldException, WrongVariableException {
		Position pos = positionExpression.evaluatePosition(c);
		return pos.isPassablePos();
		
	}
	
	private final T positionExpression;
	
	public IsPassableExpression(T position) throws WorldException{
		if (position==null){
			throw new WorldException();
		}
		this.positionExpression = position;
		
	}
	

	
	
	@Override
	public ArrayList<IExpression> getExpressions() {
		ArrayList<IExpression> expressions = new ArrayList<>();
		expressions.add(this.positionExpression);
		return expressions;
	}
	
	
	
	

}
