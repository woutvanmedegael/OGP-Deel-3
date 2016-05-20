package hillbillies.model.expressions;

import hillbillies.model.exceptions.TaskInterruptionException;
import hillbillies.model.exceptions.WorldException;
import hillbillies.model.exceptions.WrongVariableException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.task.ContextWrapper;
import hillbillies.model.util.Position;

public interface IUnitExpression extends IExpression{
	
	public Unit evaluateUnit(ContextWrapper c) throws WorldException, WrongVariableException;
}
