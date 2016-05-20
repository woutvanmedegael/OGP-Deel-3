package hillbillies.model.expressions;

import hillbillies.model.exceptions.WorldException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.task.ContextWrapper;

public class ThisExpression extends UnitExpression{
	
	@Override
	public Unit evaluateUnit(ContextWrapper c) throws WorldException {
		return c.getExecutingUnit();
	}
	

}
