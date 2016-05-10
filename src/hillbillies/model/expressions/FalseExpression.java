package hillbillies.model.expressions;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.WorldException;

public class FalseExpression extends BooleanExpression {

	@Override
	public Boolean evaluateBoolean(ContextWrapper c) throws WorldException, WrongVariableException {
		return false;
	}

	@Override
	public Boolean containsSelected() {
		return false;
	}

	@Override
	public ArrayList<Expression<?>> getExpressions() {
		ArrayList<Expression<?>> list = new ArrayList<>();
		return list;
	}

}
