/**
 * 
 */
package hillbillies.model.statement;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.expressions.IExpression;
import hillbillies.model.world.WorldException;

public abstract class Statement{
	
	private Boolean executed = false;
	
	public abstract Boolean executeNext(ContextWrapper context) throws WorldException, WrongVariableException ;
	
	public Boolean hasBeenExecuted(){
		return this.executed;
	}
	
	public void setExecuted(Boolean b){
		this.executed = b;
	}
	
	public abstract ArrayList<Statement> getStatements();
	public abstract ArrayList<IExpression> getExpressions();
	
}
