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
	public ArrayList<Statement> getStatements() {
		return new ArrayList<Statement>();
	}

	@Override
	public ArrayList<IExpression> getExpressions() {
		return new ArrayList<IExpression>();
	}

}
