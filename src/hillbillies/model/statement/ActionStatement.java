package hillbillies.model.statement;

import java.util.ArrayList;

import hillbillies.model.expressions.Expression;

public abstract class ActionStatement extends Statement {

	public abstract Expression<?> getExpression();
	
}
