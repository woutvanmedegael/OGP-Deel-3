package hillbillies.model.statement;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.PositionExpression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.hillbilliesobject.unit.UnitException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class WorkStatement<T extends PositionExpression> extends ActionStatement{
	
	private T position;
	
	public WorkStatement(T pos) throws SyntaxException{
		this.position = pos;
	}
	
	@Override
	public Boolean execute(ContextWrapper c) throws WorldException {
		Position pos = position.evaluate(c);
		c.getExecutingUnit().workAt(pos.getCubexpos(), pos.getCubeypos(), pos.getCubezpos());
		return true;
	}

	@Override
	public Boolean containsSelected() {
		return position.containsSelected();
	}
	
	
}
