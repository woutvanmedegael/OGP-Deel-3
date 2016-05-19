package hillbillies.model.expressions;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class AndExpression<L extends IBooleanExpression, R extends IBooleanExpression> extends BooleanExpression{
	
	private final R right;
	private final L left;

	public AndExpression(L left, R right) throws WorldException{
		if (left==null || right==null){
			throw new WorldException();
		}
		this.right = right;
		this.left = left;
	}
	
	@Override
	public Boolean evaluateBoolean(ContextWrapper c) throws WorldException, WrongVariableException{
		return (right.evaluateBoolean(c) && left.evaluateBoolean(c));
	}

	public L getLeft() {
		return left;
	}

	public R getRight() {
		return right;
	}
	
	@Override
	public IBooleanExpression[] getExpressions() {
		return new IBooleanExpression[]{right,left};
	}

}
