package hillbillies.model.statement;

import hillbillies.model.SyntaxException;
import hillbillies.model.expressions.BooleanExpression;
import hillbillies.model.expressions.Expression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class IfThenElseStatement extends Statement {
	
	private final BooleanExpression condition;
	private final Statement thenStatement;
	private final Statement elseStatement;
	
	public IfThenElseStatement(Expression ifExpr, Statement thenStat, Statement elseStat) throws SyntaxException{
		if (!(ifExpr instanceof BooleanExpression)){
			throw new SyntaxException();
		}
		this.condition = (BooleanExpression) ifExpr;
		this.thenStatement = thenStat;
		this.elseStatement = elseStat;
	}


	@Override
	public void execute(World world, Unit unit) throws WorldException {
		if (condition.evaluate(world, unit)){
			this.thenStatement.execute(world, unit);
		} else {
			this.elseStatement.execute(world, unit);
		}
		
	}

}
