package hillbillies.model.statement;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.expressions.BooleanExpression;
import hillbillies.model.expressions.Expression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class WhileStatement extends Statement{
	//WOUT
	
	private final BooleanExpression condition;
	private final Statement body;
	private Boolean isEvaluated = false;
	
	
	public WhileStatement(Expression condition, Statement body) throws SyntaxException{
		if (!(condition instanceof BooleanExpression)){
			throw new SyntaxException();
		}
		
		this.condition = (BooleanExpression) condition;
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
	public Boolean containsSelected() {
		return (condition.containsSelected() || body.containsSelected());
	}
	
	public BooleanExpression getCondition(){
		return this.condition;
	}
	
	public Statement getBody(){
		return this.body;
	}
	
	@Override
	public void setExecuted(Boolean b){
		super.setExecuted(b);
		this.body.setExecuted(b);
	}
	
	

}
