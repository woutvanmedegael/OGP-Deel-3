package hillbillies.model.expressions;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class OrExpression<L extends BooleanExpression, R extends BooleanExpression> extends BooleanExpression{
	//WOUT
	
	private final R right;
	private final L left;
	
	public OrExpression(L left, R right) throws SyntaxException{
		this.right = right;
		this.left = left;
	}
	
	
	@Override
	public Boolean evaluateBoolean(ContextWrapper c) throws WorldException, WrongVariableException {
		return (this.right.evaluateBoolean(c) || this.left.evaluateBoolean(c));
	}


	@Override
	public Boolean containsSelected() {
		return (right.containsSelected() || left.containsSelected());
	}
	
	@Override
	public ArrayList<Expression<?>> getExpressions() {
		ArrayList<Expression<?>> expressions = new ArrayList<>();
		expressions.add(this.right);
		expressions.add(this.left);
		return expressions;
	}
}

	
