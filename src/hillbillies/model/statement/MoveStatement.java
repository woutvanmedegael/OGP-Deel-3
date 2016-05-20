package hillbillies.model.statement;

import hillbillies.model.exceptions.TaskInterruptionException;
import hillbillies.model.exceptions.WorldException;
import hillbillies.model.exceptions.WrongVariableException;
import hillbillies.model.expressions.IPositionExpression;
import hillbillies.model.task.ContextWrapper;
import hillbillies.model.util.Position;

public class MoveStatement<T extends IPositionExpression> extends ActionStatement {
	
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
	public IPositionExpression[] getExpressions() {
		return new IPositionExpression[]{positionExpression};
	}




	@Override
	public Statement copy() throws WorldException {
		return new MoveStatement<T>(this.positionExpression);
	}
	


}
