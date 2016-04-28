package hillbillies.model.statement;

import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.PositionExpression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.hillbilliesobject.unit.UnitException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class WorkStatement extends ActionStatement{
	
	private PositionExpression position;
	
	public WorkStatement(Expression pos) throws SyntaxException{
		if (!(pos instanceof PositionExpression)){
			throw new SyntaxException();
		}
		this.position = (PositionExpression) pos;
	}
	
	@Override
	public Boolean execute(World world, Unit unit, Position selectedCube) throws WorldException {
		Position pos = position.evaluate(world, unit, selectedCube);
		unit.workAt(pos.getCubexpos(), pos.getCubeypos(), pos.getCubezpos());
		return true;
	}

	@Override
	public Boolean containsSelected() {
		return position.containsSelected();
	}
	
	
}
