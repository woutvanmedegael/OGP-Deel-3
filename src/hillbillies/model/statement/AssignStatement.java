package hillbillies.model.statement;

import hillbillies.model.exceptions.WorldException;
import hillbillies.model.exceptions.WrongVariableException;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.IBooleanExpression;
import hillbillies.model.expressions.IExpression;
import hillbillies.model.expressions.IPositionExpression;
import hillbillies.model.expressions.IUnitExpression;
import hillbillies.model.task.ContextWrapper;

public class AssignStatement extends Statement{

	private final String variableName;
	private final Expression<?> expr;
	
	

	@Override
	public Boolean executeNext(ContextWrapper context) throws WorldException, WrongVariableException {
		if (expr instanceof IBooleanExpression){
			context.addNewVariable(variableName, (((IBooleanExpression) expr).evaluateBoolean(context)));
		}
		else if (expr instanceof IPositionExpression){
			context.addNewVariable(variableName, ((IPositionExpression) expr).evaluatePosition(context));
		}
		else if (expr instanceof IUnitExpression){
			context.addNewVariable(variableName, ((IUnitExpression) expr).evaluateUnit(context));
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



	@Override
	public IExpression[] getExpressions() {
		return new IExpression[]{expr};
	}

	@Override
	public Statement copy() throws WorldException {
		return new AssignStatement(this.variableName,this.expr);
	}

}
