package hillbillies.model.statement;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.expressions.BooleanExpression;
import hillbillies.model.expressions.Expression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class IfThenElseStatement<T extends BooleanExpression> extends Statement {
	
	private final BooleanExpression condition;
	private final Statement thenStatement;
	private final Statement elseStatement;
	
	public IfThenElseStatement(T ifExpr, Statement thenStat, Statement elseStat) throws SyntaxException{
		this.condition = ifExpr;
		this.thenStatement = thenStat;
		this.elseStatement = elseStat;
	}


	@Override
	public Boolean execute(ContextWrapper c) throws WorldException {
		if (condition.evaluate(c)){
			return this.thenStatement.execute(c);
		} else {
			return this.elseStatement.execute(c);
		}
	}


	@Override
	public Boolean containsSelected() {
		return (condition.containsSelected() || thenStatement.containsSelected() || elseStatement.containsSelected());
	}

}
