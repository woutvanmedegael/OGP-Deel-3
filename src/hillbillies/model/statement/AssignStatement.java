package hillbillies.model.statement;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.expressions.BooleanExpression;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.IBooleanExpression;
import hillbillies.model.expressions.IExpression;
import hillbillies.model.expressions.IPositionExpression;
import hillbillies.model.expressions.IUnitExpression;
import hillbillies.model.expressions.PositionExpression;
import hillbillies.model.expressions.UnitExpression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class AssignStatement extends Statement{

	private final String variableName;
	private final IExpression expr;
	
	

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
	
	public AssignStatement(String variable, IExpression expr) throws WorldException{
		if (variable==null || expr == null){
			throw new WorldException();
		}
		this.variableName = variable;
		this.expr = expr;
	}

	@Override
	public ArrayList<Statement> getStatements() {
		return new ArrayList<Statement>();
	}

	@Override
	public ArrayList<IExpression> getExpressions() {
		ArrayList<IExpression> expressions = new ArrayList<IExpression>();
		expressions.add(expr);
		return expressions; 
	}

	@Override
	public Statement copy() throws WorldException {
		return new AssignStatement(this.variableName,this.expr);
	}

}
