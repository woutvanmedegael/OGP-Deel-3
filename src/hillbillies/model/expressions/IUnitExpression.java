package hillbillies.model.expressions;

import hillbillies.model.exceptions.WorldException;
import hillbillies.model.exceptions.WrongVariableException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.task.ContextWrapper;

public interface IUnitExpression extends IExpression{
	
	public Unit evaluateUnit(ContextWrapper c) throws WorldException, WrongVariableException;
}
