package hillbillies.model.statement;

import hillbillies.model.exceptions.WorldException;
import hillbillies.model.task.ContextWrapper;

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
