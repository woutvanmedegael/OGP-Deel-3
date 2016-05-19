package hillbillies.model.expressions;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class HereExpression extends PositionExpression{
	
	@Override
	public Position evaluatePosition(ContextWrapper c) throws WorldException {
		return new Position(c.getExecutingUnit().getxpos(),c.getExecutingUnit().getypos(),c.getExecutingUnit().getzpos(),c.getThisWorld());
	}

	
	
	@Override
	public IExpression[] getExpressions() {
		return new IExpression[]{};
	}

	


}
