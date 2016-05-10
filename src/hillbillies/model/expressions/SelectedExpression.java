package hillbillies.model.expressions;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class SelectedExpression extends PositionExpression{
	//WOUT
	@Override
	public Position evaluatePosition(ContextWrapper c) throws WorldException {
		if (c.getSelectedPos()==null){
			throw new IllegalArgumentException();
		}
		Position pos = c.getSelectedPos();
		pos.setWorld(c.getThisWorld());
		return pos;
	}

	@Override
	public Boolean containsSelected() {
		return true;
	}
	
	@Override
	public ArrayList<Expression<?>> getExpressions() {
		return new ArrayList<Expression<?>>();
	}

	

}
