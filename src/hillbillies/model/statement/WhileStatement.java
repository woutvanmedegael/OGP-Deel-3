package hillbillies.model.statement;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.expressions.BooleanExpression;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.IBooleanExpression;
import hillbillies.model.expressions.IExpression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class WhileStatement<T extends IBooleanExpression> extends Statement{
	//WOUT
	
	private final T condition;
	private final Statement body;
	private Boolean isEvaluated = false;
	
	
	public WhileStatement(T condition, Statement body) throws WorldException{
		if (condition==null || body==null){
			throw new WorldException();
		}
		
		this.condition = condition;
		this.body = body;
	}

	
	@Override
	public Boolean executeNext(ContextWrapper c) throws WorldException, WrongVariableException {
		if (isEvaluated){
			Boolean noBreakCalled = body.executeNext(c);
			if (!noBreakCalled){
				this.setExecuted(true);
			}
			if (body.hasBeenExecuted()){
				this.setExecuted(false);
				this.isEvaluated = false;
			}
			return true;
		}
		if (condition.evaluateBoolean(c)){
			this.isEvaluated = true;
			body.executeNext(c);
			return true;
		}
		else {
			this.setExecuted(true);
		}
		return true;
	}
	
	@Override
	public void setExecuted(Boolean b){
		super.setExecuted(b);
		this.body.setExecuted(b);
	}
	
	@Override
	public ArrayList<Statement> getStatements() {
		ArrayList<Statement> statements = new ArrayList<Statement>();
		statements.add(body);
		return statements;
	}

	@Override
	public ArrayList<IExpression> getExpressions() {
		ArrayList<IExpression> expressions = new ArrayList<IExpression>();
		expressions.add(condition);
		return expressions;
	}
	
	

}
