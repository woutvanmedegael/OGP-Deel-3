package hillbillies.model.expressions;

import hillbillies.model.ContextWrapper;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.WorldException;

public class ThisExpression extends UnitExpression{
	
	@Override
	public Unit evaluateUnit(ContextWrapper c) throws WorldException {
		return c.getExecutingUnit();
	}
	

}
