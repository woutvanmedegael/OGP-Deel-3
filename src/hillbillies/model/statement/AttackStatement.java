package hillbillies.model.statement;

import hillbillies.model.exceptions.TaskInterruptionException;
import hillbillies.model.exceptions.WorldException;
import hillbillies.model.exceptions.WrongVariableException;
import hillbillies.model.expressions.IUnitExpression;
import hillbillies.model.task.ContextWrapper;

public class AttackStatement<T extends IUnitExpression> extends ActionStatement{
	
	private T unitExpression;
	
	public AttackStatement(T defender) throws WorldException{
		if (defender==null){
			throw new WorldException();
		}
		unitExpression = defender;
		
	}

	

	@Override
	public Boolean executeNext(ContextWrapper context) throws WorldException, WrongVariableException{
		try{
		context.getExecutingUnit().startAttacking(unitExpression.evaluateUnit(context));
		this.setExecuted(true);
		} catch (TaskInterruptionException t){
			context.getExecutingUnit().interrupt();
		}
		return true;
	}


	@Override
	public IUnitExpression[] getExpressions() {
		return new IUnitExpression[]{unitExpression};
	}



	@Override
	public Statement copy() throws WorldException {
		return new AttackStatement<T>(this.unitExpression);
	}
}
