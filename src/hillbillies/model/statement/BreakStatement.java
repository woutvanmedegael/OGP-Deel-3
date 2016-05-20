package hillbillies.model.statement;

import hillbillies.model.ContextWrapper;
import hillbillies.model.world.WorldException;

public class BreakStatement extends Statement{
	@Override
	public Boolean executeNext(ContextWrapper c) throws WorldException {
		this.setExecuted(true);
		return false;
	}

	@Override
	public Statement copy() throws WorldException {
		return new BreakStatement();
	}

	

	
}
