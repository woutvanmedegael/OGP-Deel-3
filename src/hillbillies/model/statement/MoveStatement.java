package hillbillies.model.statement;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.TaskInterruptionException;
import hillbillies.model.expressions.IExpression;
import hillbillies.model.expressions.IPositionExpression;
import hillbillies.model.world.WorldException;

public class MoveStatement<T extends IPositionExpression> extends ActionStatement {
	//ADRIAAN
	@Override
	public Boolean executeNext(ContextWrapper c) throws WorldException, WrongVariableException {
		try{
			Position target = positionExpression.evaluatePosition(c);
			c.getExecutingUnit().moveTo(target.getCubexpos(), target.getCubeypos(), target.getCubezpos());
			this.setExecuted(true);
		} catch (TaskInterruptionException t){
			c.getExecutingUnit().interrupt();
		}
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
	public ArrayList<IExpression> getExpressions() {
		ArrayList<IExpression> expressions = new ArrayList<IExpression>();
		expressions.add(positionExpression);
		return expressions;
	}
	


}
