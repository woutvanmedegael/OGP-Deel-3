/**
 * 
 */
package hillbillies.model.statement;

import hillbillies.model.exceptions.WorldException;
import hillbillies.model.exceptions.WrongVariableException;
import hillbillies.model.expressions.IExpression;
import hillbillies.model.task.ContextWrapper;

public abstract class Statement{
	
	private Boolean executed = false;
	
	public abstract Boolean executeNext(ContextWrapper context) throws WorldException, WrongVariableException ;
	
	public Boolean hasBeenExecuted(){
		return this.executed;
	}
	
	public void setExecuted(Boolean b){
		this.executed = b;
	}
	
	public Statement[] getStatements() {
		return new Statement[]{};
	}
	public IExpression[] getExpressions() {
		return new IExpression[]{};
	}
	public abstract Statement copy() throws WorldException;
	
}
