package hillbillies.model.statement;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.expressions.BooleanExpression;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.PositionExpression;
import hillbillies.model.expressions.UnitExpression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class AssignStatement extends Statement{

	private final String variableName;
	private final Expression<?> expr;
	
	@Override
	public Boolean containsSelected() {
		return false;
	}

	@Override
	public Boolean executeNext(ContextWrapper context) throws WorldException, WrongVariableException {
		if (expr instanceof BooleanExpression){
			context.addNewVariable(variableName, ((BooleanExpression) expr).evaluateBoolean(context));
		}
		else if (expr instanceof PositionExpression){
			context.addNewVariable(variableName, ((PositionExpression) expr).evaluatePosition(context));
		}
		else if (expr instanceof UnitExpression){
			context.addNewVariable(variableName, ((UnitExpression) expr).evaluateUnit(context));
		} else throw new WrongVariableException();
		
		this.setExecuted(true);
		return true;	
	}
	
	public String getVariableName(){
		return this.variableName;
	}
	
	public AssignStatement(String variable, Expression<?> expr) throws WorldException{
		if (variable==null || expr == null){
			throw new WorldException();
		}
		this.variableName = variable;
		this.expr = expr;
	}

}
