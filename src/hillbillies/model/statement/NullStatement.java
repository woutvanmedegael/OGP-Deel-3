package hillbillies.model.statement;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.expressions.IExpression;
import hillbillies.model.world.WorldException;

public class NullStatement extends Statement{

	

	@Override
	public Boolean executeNext(ContextWrapper context) throws WorldException, WrongVariableException {
		this.setExecuted(true);
		return true;
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
		return new NullStatement();
	}

}
