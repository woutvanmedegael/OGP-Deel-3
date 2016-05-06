package hillbillies.model.expressions;

import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class NotExpression<T extends BooleanExpression> extends BooleanExpression{
	//ADRIAAN
	@Override
	public Boolean evaluate(World world, Unit unit, Position selectedCube) throws WorldException {
		// TODO Auto-generated method stub
		
		return (!notExpression.evaluate(world, unit, selectedCube));
	}
	private final T notExpression;
	public NotExpression(T notExpr) throws SyntaxException{
		this.notExpression = notExpr;
		
	}
	@Override
	public Boolean containsSelected() {
		// TODO Auto-generated method stub
		return notExpression.containsSelected();
	}

	
}
