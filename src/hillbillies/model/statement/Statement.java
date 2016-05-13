/**
 * 
 */
package hillbillies.model.statement;

import hillbillies.model.ContextWrapper;
import hillbillies.model.IContainsSelected;
import hillbillies.model.world.WorldException;

public abstract class Statement implements IContainsSelected{
	
	private Boolean executed = false;
	
	public abstract Boolean executeNext(ContextWrapper context) throws WorldException, WrongVariableException ;
	
	public Boolean hasBeenExecuted(){
		return this.executed;
	}
	
	public void setExecuted(Boolean b){
		this.executed = b;
	}
	
}
