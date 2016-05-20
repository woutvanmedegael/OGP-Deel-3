package hillbillies.model.expressions;

import hillbillies.model.exceptions.WorldException;
import hillbillies.model.exceptions.WrongVariableException;
import hillbillies.model.task.ContextWrapper;

public interface IBooleanExpression extends IExpression {
	
	public Boolean evaluateBoolean(ContextWrapper c) throws WorldException, WrongVariableException;
}
