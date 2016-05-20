package hillbillies.model.expressions;

import hillbillies.model.ContextWrapper;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.WorldException;

public class OrExpression<L extends IBooleanExpression, R extends IBooleanExpression> extends BooleanExpression{
		
	private final R right;
	private final L left;
	
	public OrExpression(L left, R right) throws WorldException{
		if (left==null || right==null){
			throw new WorldException();
		}
		this.right = right;
		this.left = left;
	}
	
	
	@Override
	public Boolean evaluateBoolean(ContextWrapper c) throws WorldException, WrongVariableException {
		return (this.right.evaluateBoolean(c) || this.left.evaluateBoolean(c));
	}


	
	
	@Override
	public IBooleanExpression[] getExpressions() {
		return new IBooleanExpression[]{right,left};
	}
}

	
