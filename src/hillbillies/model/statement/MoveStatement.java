package hillbillies.model.statement;

import hillbillies.model.ContextWrapper;
import hillbillies.model.IContainsSelected;
import hillbillies.model.Position;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.IPositionExpression;
import hillbillies.model.expressions.PositionExpression;
import hillbillies.model.world.WorldException;

public class MoveStatement<T extends IPositionExpression & IContainsSelected> extends ActionStatement {
	//ADRIAAN
	@Override
	public Boolean executeNext(ContextWrapper c) throws WorldException, WrongVariableException {
		Position target = positionExpression.evaluatePosition(c);
		c.getExecutingUnit().moveTo(target.getCubexpos(), target.getCubeypos(), target.getCubezpos());
		this.setExecuted(true);
		return true;
	}
	private final T positionExpression;
	public MoveStatement(T position) throws WorldException{
		if (position==null){
			throw new WorldException();
		}
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
