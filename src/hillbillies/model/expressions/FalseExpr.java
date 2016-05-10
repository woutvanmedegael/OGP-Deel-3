package hillbillies.model.expressions;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.WorldException;

public class FalseExpr extends BooleanExpression {

	@Override
	public Boolean evaluateBoolean(ContextWrapper c) throws WorldException, WrongVariableException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Boolean containsSelected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Expression<?>> getExpressions() {
		// TODO Auto-generated method stub
		return new ArrayList<Expression<?>>();
	}

}
