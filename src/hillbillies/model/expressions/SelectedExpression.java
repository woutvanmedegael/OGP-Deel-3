package hillbillies.model.expressions;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.world.WorldException;

public class SelectedExpression extends PositionExpression{

	@Override
	public Position evaluatePosition(ContextWrapper c) throws WorldException {
		if (c.getSelectedPos()==null){
			throw new IllegalArgumentException();
		}
		Position pos = c.getSelectedPos();
		pos.setWorld(c.getThisWorld());
		return pos;
	}
}
