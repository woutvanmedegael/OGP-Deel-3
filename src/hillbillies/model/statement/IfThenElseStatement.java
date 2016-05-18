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
		this.thenStatement.setExecuted(b);
		this.elseStatement.setExecuted(b);
	}
	
	@Override
	public ArrayList<Statement> getStatements() {
		ArrayList<Statement> statements = new ArrayList<Statement>();
		statements.add(elseStatement);
		statements.add(thenStatement);
		return statements;
	}

	@Override
	public ArrayList<IExpression> getExpressions() {
		ArrayList<IExpression> expressions = new ArrayList<IExpression>();
		expressions.add(condition);
		return expressions;
	}


	@Override
	public Statement copy() throws WorldException {
		return new IfThenElseStatement<T>(this.condition,this.thenStatement.copy(),this.elseStatement.copy());
	}

}
