package hillbillies.model.statement;

import hillbillies.model.ContextWrapper;
import hillbillies.model.world.WorldException;

public class NullStatement extends Statement{

	@Override
	public Boolean containsSelected() {
		return false;
	}

	@Override
	public Boolean executeNext(ContextWrapper context) throws WorldException, WrongVariableException {
		this.setExecuted(true);
		return true;
	}

}
