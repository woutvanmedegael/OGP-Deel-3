package hillbillies.model.statement;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.expressions.IExpression;
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
	public ArrayList<Statement> getStatements() {
		return new ArrayList<Statement>();
	}

	@Override
	public ArrayList<IExpression> getExpressions() {
		return new ArrayList<IExpression>();
	}

	@Override
	public Statement copy() throws WorldException {
		return new BreakStatement();
	}

	

	
}
