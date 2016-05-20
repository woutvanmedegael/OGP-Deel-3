package hillbillies.model.expressions;

import hillbillies.model.exceptions.WorldException;
import hillbillies.model.task.ContextWrapper;
import hillbillies.model.util.Position;

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
