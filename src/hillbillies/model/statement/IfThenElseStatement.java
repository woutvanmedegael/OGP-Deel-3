package hillbillies.model.statement;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.expressions.BooleanExpression;
import hillbillies.model.expressions.Expression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class IfThenElseStatement<T extends BooleanExpression> extends Statement {
	
	private final BooleanExpression condition;
	private final Statement thenStatement;
	private final Statement elseStatement;
	private boolean isEvaluated = false;
	private boolean enteredBody = false;
	
	public IfThenElseStatement(T ifExpr, Statement thenStat, Statement elseStat) throws SyntaxException{
		this.condition = ifExpr;
		this.thenStatement = thenStat;
		this.elseStatement = elseStat;
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
	public Boolean containsSelected() {
		return (condition.containsSelected() || thenStatement.containsSelected() || elseStatement.containsSelected());
	}
	
	public BooleanExpression getCondition(){
		return this.condition;
	}
	
	public Statement getThenStatement(){
		return this.thenStatement;
	}
	
	public Statement getElseStatement(){
		return this.elseStatement;
	}
	
	@Override
	public void setExecuted(Boolean b){
		super.setExecuted(b);
		this.thenStatement.setExecuted(b);
		this.elseStatement.setExecuted(b);
	}

}
