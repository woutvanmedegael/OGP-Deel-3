package hillbillies.model.expressions;

import hillbillies.model.exceptions.WorldException;
import hillbillies.model.exceptions.WrongVariableException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.task.ContextWrapper;
import hillbillies.model.util.Position;

public class PositionOfExpression<T extends IUnitExpression> extends PositionExpression{
	
	private final T unit;
	
	public PositionOfExpression(T unit) throws WorldException{
		if (unit==null){
			throw new WorldException();
		}
		this.unit = unit;
		
	}
	@Override
	public Position evaluatePosition(ContextWrapper c) throws WorldException, WrongVariableException {
		Unit myUnit = this.unit.evaluateUnit(c);
		return new Position(myUnit.getxpos(), myUnit.getypos(), myUnit.getzpos(), c.getThisWorld());
	}
	
	
	@Override
	public IUnitExpression[] getExpressions() {
		return new IUnitExpression[]{unit};
	}


}
