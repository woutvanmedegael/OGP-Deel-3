package hillbillies.model.expressions;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.WorldException;

public class IsSolidExpression<T extends IPositionExpression> extends BooleanExpression {

	@Override
	public Boolean evaluateBoolean(ContextWrapper c) throws WorldException, WrongVariableException {
		Position pos = positionExpression.evaluatePosition(c);
		Boolean b = !pos.isPassablePos();
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
	public IPositionExpression[] getExpressions() {
		return new IPositionExpression[]{positionExpression};
	}

	

	

}
