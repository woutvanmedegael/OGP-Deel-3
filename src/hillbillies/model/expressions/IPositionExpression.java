package hillbillies.model.expressions;

import hillbillies.model.exceptions.TaskInterruptionException;
import hillbillies.model.exceptions.WorldException;
import hillbillies.model.exceptions.WrongVariableException;
import hillbillies.model.task.ContextWrapper;
import hillbillies.model.util.Position;

public interface IPositionExpression extends IExpression {
	
	public Position evaluatePosition(ContextWrapper c) throws WorldException, WrongVariableException;
}
