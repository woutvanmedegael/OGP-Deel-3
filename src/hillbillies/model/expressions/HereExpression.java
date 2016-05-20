package hillbillies.model.expressions;

import hillbillies.model.exceptions.WorldException;
import hillbillies.model.task.ContextWrapper;
import hillbillies.model.util.Position;

public class HereExpression extends PositionExpression{
	
	@Override
	public Position evaluatePosition(ContextWrapper c) throws WorldException {
		return new Position(c.getExecutingUnit().getxpos(),c.getExecutingUnit().getypos(),c.getExecutingUnit().getzpos(),c.getThisWorld());
	}
}
