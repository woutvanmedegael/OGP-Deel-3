package hillbillies.model.expressions;

import hillbillies.model.ContextWrapper;
import hillbillies.model.TaskInterruptionException;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.WorldException;

public interface IBooleanExpression extends IExpression {
	
	public Boolean evaluateBoolean(ContextWrapper c) throws WorldException, WrongVariableException;
}
