package hillbillies.model.expressions;

import hillbillies.model.ContextWrapper;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.WorldException;

public class NotExpression<T extends IBooleanExpression> extends BooleanExpression{
	
	@Override
	public Boolean evaluateBoolean(ContextWrapper c) throws WorldException, WrongVariableException {		// TODO Auto-generated method stub
		
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
	public IBooleanExpression[] getExpressions() {
		return new IBooleanExpression[]{notExpression};
	}

	
}
