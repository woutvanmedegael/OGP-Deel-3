package hillbillies.model.statement;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.PositionExpression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.hillbilliesobject.unit.UnitException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class MoveStatement<T extends PositionExpression> extends ActionStatement {
	//ADRIAAN
	@Override
	public Boolean execute(ContextWrapper c) throws WorldException {
		Position target = positionExpression.evaluate(c);
		c.getExecutingUnit().moveTo(target.getCubexpos(), target.getCubeypos(), target.getCubezpos());
		
		return true;
	}
	private final T positionExpression;
	public MoveStatement(T position) throws SyntaxException{
		positionExpression = position;
	}

	@Override
	public Boolean containsSelected() {
		return positionExpression.containsSelected();
	}
	


}
