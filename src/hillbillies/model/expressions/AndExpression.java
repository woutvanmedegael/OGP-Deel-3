package hillbillies.model.expressions;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class AndExpression<L extends BooleanExpression, R extends BooleanExpression> extends BooleanExpression {
	
	private final R right;
	private final L left;

	public AndExpression(L left, R right){
		this.right = right;
		this.left = left;
	}
	
	@Override
	public Boolean evaluate(ContextWrapper c) throws WorldException{
		return (right.evaluate(c) && left.evaluate(c));
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

}
