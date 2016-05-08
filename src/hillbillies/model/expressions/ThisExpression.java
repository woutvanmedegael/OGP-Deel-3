package hillbillies.model.expressions;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class ThisExpression extends UnitExpression{
	//ADRIAAN
	@Override
	public Unit evaluateUnit(ContextWrapper c) throws WorldException {
		return c.getExecutingUnit();
	}

	@Override
	public Boolean containsSelected() {
		return false;
	}
	
	@Override
	public ArrayList<Expression<?>> getExpressions() {
		return new ArrayList<Expression<?>>();
	}

	

}
