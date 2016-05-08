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
	public Boolean executeNext(ContextWrapper c) throws WorldException, WrongVariableException {
		Position target = positionExpression.evaluatePosition(c);
		c.getExecutingUnit().moveTo(target.getCubexpos(), target.getCubeypos(), target.getCubezpos());
		this.setExecuted(true);
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

	@Override
	public Expression<?> getExpression() {
		return (Expression<?>)this.positionExpression;
	}
	


}
