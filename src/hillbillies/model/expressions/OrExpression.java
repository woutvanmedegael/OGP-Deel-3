package hillbillies.model.expressions;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
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
	public Boolean evaluate(ContextWrapper c) throws WorldException {
		return (this.right.evaluate(c) || this.left.evaluate(c));
	}


	@Override
	public Boolean containsSelected() {
		return (right.containsSelected() || left.containsSelected());
	}
}

	
