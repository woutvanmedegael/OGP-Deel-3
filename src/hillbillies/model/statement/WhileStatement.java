package hillbillies.model.statement;

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
	public Boolean execute(World world, Unit unit, Position selectedCube) throws WorldException {
		Boolean noBreakCalled = true;
		while (this.condition.evaluate(world, unit, selectedCube) && noBreakCalled){
			noBreakCalled = this.body.execute(world, unit, selectedCube);
		}
		return true;
	}
	
	

}
