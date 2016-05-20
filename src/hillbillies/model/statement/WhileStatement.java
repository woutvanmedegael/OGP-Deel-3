package hillbillies.model.statement;

import hillbillies.model.ContextWrapper;
import hillbillies.model.expressions.IBooleanExpression;
import hillbillies.model.world.WorldException;

public class WhileStatement<T extends IBooleanExpression> extends Statement{
	
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
	public Statement[] getStatements() {
		Statement[] statements = new Statement[1];
		statements[0] = this.body;
		return statements;
	}

	@Override
	public IBooleanExpression[] getExpressions() {
		return new IBooleanExpression[]{condition};

	}


	@Override
	public Statement copy() throws WorldException {
		return new WhileStatement<T>(this.condition, this.body.copy());
		}
	
	

}
