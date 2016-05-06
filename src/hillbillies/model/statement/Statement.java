/**
 * 
 */
package hillbillies.model.statement;

import hillbillies.model.ContextWrapper;
import hillbillies.model.IContainsSelected;
import hillbillies.model.world.WorldException;

public abstract class Statement implements IContainsSelected{
	
	public abstract Boolean execute(ContextWrapper context) throws WorldException ;
	
}
