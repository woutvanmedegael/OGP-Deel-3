package hillbillies.model.statement;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.expressions.Expression;
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
	public Boolean executeNext(ContextWrapper context) throws WorldException {
		context.addNewVariable(variableName, expr);
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
