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
	public void execute(World world, Unit unit) throws WorldException {
		Position pos = position.evaluate(world, unit);
		unit.workAt(pos.getCubexpos(), pos.getCubeypos(), pos.getCubezpos());
		
	}
	
	
}
