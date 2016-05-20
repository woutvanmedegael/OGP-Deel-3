package hillbillies.model.statement;

import hillbillies.model.exceptions.WorldException;
import hillbillies.model.exceptions.WrongVariableException;
import hillbillies.model.expressions.IBooleanExpression;
import hillbillies.model.task.ContextWrapper;

public class IfThenElseStatement<T extends IBooleanExpression> extends Statement {
	
	private final T condition;
	private final Statement thenStatement;
	private final Statement elseStatement;
	private boolean isEvaluated = false;
	private boolean enteredBody = false;
	
	public IfThenElseStatement(T ifExpr, Statement thenStat, Statement elseStat) throws WorldException{
		if (ifExpr==null || thenStat==null){
			throw new WorldException();
		}
		this.condition = ifExpr;
		this.thenStatement = thenStat;
		if (elseStat==null){
			this.elseStatement = new NullStatement();
		} else {
			this.elseStatement = elseStat;
		}
	}


	@Override
	public Boolean executeNext(ContextWrapper c) throws WorldException, WrongVariableException {
		if (isEvaluated){
			if (enteredBody){
				Boolean executed = this.thenStatement.executeNext(c);
				if (this.thenStatement.hasBeenExecuted()){
					this.setExecuted(true);
				}
				return executed;
			} else {
				Boolean executed = this.elseStatement.executeNext(c);
				if (this.elseStatement.hasBeenExecuted()){
					this.setExecuted(true);
				}
				return executed;
			}
		}
		this.isEvaluated = true;
		if (condition.evaluateBoolean(c)){
			this.enteredBody = true;
			Boolean executed = this.thenStatement.executeNext(c);
			if (this.thenStatement.hasBeenExecuted()){
				this.setExecuted(true);
			}
			return executed;
		} else {
			Boolean executed = this.elseStatement.executeNext(c);
			if (this.elseStatement.hasBeenExecuted()){
				this.setExecuted(true);
			}
			return executed;		}
	}

	
	@Override
	public void setExecuted(Boolean b){
		super.setExecuted(b);
		this.isEvaluated = false;
		this.thenStatement.setExecuted(b);
		this.elseStatement.setExecuted(b);
	}
	
	@Override
	public Statement[] getStatements() {
		Statement[] statements = new Statement[2];
		statements[0] = thenStatement;
		statements[1] = elseStatement;
		return statements;
	}

	@Override
	public IBooleanExpression[] getExpressions() {
		return new IBooleanExpression[]{condition};
	}


	@Override
	public Statement copy() throws WorldException {
		return new IfThenElseStatement<T>(this.condition,this.thenStatement.copy(),this.elseStatement.copy());
	}

}
