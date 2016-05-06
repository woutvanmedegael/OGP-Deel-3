package hillbillies.model.expressions;

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
	public Boolean evaluate(World world, Unit unit, Position selectedCube) throws WorldException {
		return (this.right.evaluate(world, unit, selectedCube) || this.left.evaluate(world, unit, selectedCube));
	}


	@Override
	public Boolean containsSelected() {
		return (right.containsSelected() || left.containsSelected());
	}
}

	
