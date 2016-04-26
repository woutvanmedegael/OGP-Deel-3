package hillbillies.model.expressions;

import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class AndExpression extends BooleanExpression {
	
	private final BooleanExpression right;
	private final BooleanExpression left;

	public AndExpression(Expression left, Expression right) throws SyntaxException{
		if (!(left instanceof BooleanExpression && right instanceof BooleanExpression)){
			throw new SyntaxException();
		}
		this.right = (BooleanExpression) right;
		this.left = (BooleanExpression) left;
	}
	
	@Override
	public Boolean evaluate(World world, Unit unit) throws WorldException {
		return (right.evaluate(world, unit) && left.evaluate(world, unit));
	}

	public Expression getLeft() {
		return left;
	}

	public Expression getRight() {
		return right;
	}

}
