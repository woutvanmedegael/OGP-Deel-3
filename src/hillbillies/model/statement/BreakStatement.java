package hillbillies.model.statement;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.expressions.IExpression;
import hillbillies.model.expressions.IUnitExpression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class BreakStatement extends Statement{
	//WOUT
	@Override
	public Boolean executeNext(ContextWrapper c) throws WorldException {
		this.setExecuted(true);
		return false;
	}

	@Override
	public Statement[] getStatements() {
		return new Statement[]{};
	}

	@Override
	public IExpression[] getExpressions() {
		return new IExpression[]{};
	}

	@Override
	public Statement copy() throws WorldException {
		return new BreakStatement();
	}

	

	
}
