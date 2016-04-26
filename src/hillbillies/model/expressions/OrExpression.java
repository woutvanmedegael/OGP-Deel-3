package hillbillies.model.expressions;

import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class OrExpression extends BooleanExpression{
	//WOUT
	
	private final BooleanExpression right;
	private final BooleanExpression left;
	
	public OrExpression(Expression left, Expression right) throws SyntaxException{
		if (!(left instanceof BooleanExpression && right instanceof BooleanExpression)){
			throw new SyntaxException();	
		}
		this.right = (BooleanExpression) right;
		this.left = (BooleanExpression) left;
	}
	
	
	@Override
	public Boolean evaluate(World world, Unit unit, Position selectedCube) throws WorldException {
		return (this.right.evaluate(world, unit, selectedCube) || this.left.evaluate(world, unit, selectedCube));
	}
}

	
