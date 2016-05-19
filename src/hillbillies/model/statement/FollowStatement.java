package hillbillies.model.statement;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.TaskInterruptionException;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.IExpression;
import hillbillies.model.expressions.IUnitExpression;
import hillbillies.model.world.WorldException;

public class FollowStatement<T extends IUnitExpression> extends ActionStatement{
	
	@Override
	public Boolean executeNext(ContextWrapper c) throws  WorldException, WrongVariableException {
		try{
			c.getExecutingUnit().startFollowing(this.unitExpression.evaluateUnit(c));
			this.setExecuted(true);
		} catch (TaskInterruptionException t){
			c.getExecutingUnit().interrupt();
		}
		return true;
	}

	
	private final T unitExpression;
	public FollowStatement(T unit) throws WorldException {
		if (unit==null){
			throw new WorldException();
		}
		this.unitExpression = unit;
		
		
		
		
	}

	@Override
	public IExpression[] getExpressions() {
		return new IExpression[]{unitExpression};
	}

	@Override
	public Statement copy() throws WorldException {
		return new FollowStatement<T>(this.unitExpression);
	}

}
