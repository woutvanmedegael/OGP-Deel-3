package hillbillies.model.expressions;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.world.WorldException;

public class HereExpression extends PositionExpression{
	
	@Override
	public Position evaluatePosition(ContextWrapper c) throws WorldException {
		return new Position(c.getExecutingUnit().getxpos(),c.getExecutingUnit().getypos(),c.getExecutingUnit().getzpos(),c.getThisWorld());
	}
}
