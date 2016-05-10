package hillbillies.model.statement;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.PositionExpression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.hillbilliesobject.unit.UnitException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class WorkStatement<T extends PositionExpression> extends ActionStatement{
	
	private T position;
	
	public WorkStatement(T pos) throws WorldException{
		if (pos==null){
			throw new WorldException();
		}
		this.position = pos;
	}
	
	@Override
	public Boolean executeNext(ContextWrapper c) throws WorldException, WrongVariableException {
		Position pos = position.evaluatePosition(c);
		c.getExecutingUnit().workAt(pos.getCubexpos(), pos.getCubeypos(), pos.getCubezpos());
		this.setExecuted(true);
		return true;
	}

	@Override
	public Boolean containsSelected() {
		return position.containsSelected();
	}

	@Override
	public Expression<?> getExpression() {
		return (Expression<?>)this.position;
	}
	
	
}
