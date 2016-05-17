package hillbillies.model.expressions;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.WorldException;

public class TrueExpression extends BooleanExpression {

	@Override
	public Boolean evaluateBoolean(ContextWrapper c) throws WorldException, WrongVariableException {
		return true;
	}

	

	@Override
	public ArrayList<IExpression> getExpressions() {
		return new ArrayList<IExpression>();
	}

}
