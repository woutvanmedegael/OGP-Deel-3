package hillbillies.model.statement;

import hillbillies.model.exceptions.WorldException;
import hillbillies.model.exceptions.WrongVariableException;
import hillbillies.model.task.ContextWrapper;

public class NullStatement extends Statement{

	

	@Override
	public Boolean executeNext(ContextWrapper context) throws WorldException, WrongVariableException {
		this.setExecuted(true);
		return true;
	}

	@Override
	public Statement copy() throws WorldException {
		return new NullStatement();
	}

}
