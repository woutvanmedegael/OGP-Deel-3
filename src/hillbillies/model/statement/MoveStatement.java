package hillbillies.model.statement;

import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.PositionExpression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.hillbilliesobject.unit.UnitException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class MoveStatement extends ActionStatement {
	//ADRIAAN
	@Override
	public Boolean execute(World world, Unit unit, Position selectedCube) throws WorldException {
		Position target = positionExpression.evaluate(world, unit, selectedCube);
		unit.moveTo(target.getCubexpos(), target.getCubeypos(), target.getCubezpos());
		
		return true;
	}
	private final PositionExpression positionExpression;
	public MoveStatement(Expression position) throws SyntaxException{
		if (!(position instanceof PositionExpression)){
			throw new SyntaxException();
		}
		positionExpression = (PositionExpression)position;
	}

	@Override
	public Boolean containsSelected() {
		// TODO Auto-generated method stub
		return null;
	}
	


}
