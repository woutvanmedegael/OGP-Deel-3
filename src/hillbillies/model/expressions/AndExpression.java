package hillbillies.model.expressions;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.IContainsSelected;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class AndExpression<L extends IBooleanExpression & IContainsSelected, R extends IBooleanExpression & IContainsSelected> extends BooleanExpression{
	
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
	public Boolean containsSelected() {
		return (right.containsSelected() || left.containsSelected());
	}

	@Override
	public ArrayList<Expression<?>> getExpressions() {
		ArrayList<Expression<?>> expressions = new ArrayList<>();
		expressions.add((Expression<?>) left);
		expressions.add((Expression<?>) right);
		return expressions;
	}

}
