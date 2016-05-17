package hillbillies.model.expressions;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.TaskInterruptionException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.WorldException;

public interface IUnitExpression extends IExpression{
	public Unit evaluateUnit(ContextWrapper c) throws WorldException, WrongVariableException;
}
