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
	
	
	public WhileStatement(Expression condition, Statement body) throws SyntaxException{
		if (!(condition instanceof BooleanExpression)){
			throw new SyntaxException();
		}
		
		this.condition = (BooleanExpression) condition;
		this.body = body;
	}

	
	@Override
	public Boolean execute(ContextWrapper c) throws WorldException {
		Boolean noBreakCalled = true;
		while (this.condition.evaluate(c) && noBreakCalled){
			noBreakCalled = this.body.execute(c);
		}
		return true;
	}


	@Override
	public Boolean containsSelected() {
		return (condition.containsSelected() || body.containsSelected());
	}
	
	

}
