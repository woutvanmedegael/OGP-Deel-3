package hillbillies.model.statement;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class BreakStatement extends Statement{
	//WOUT
	@Override
	public Boolean executeNext(ContextWrapper c) throws WorldException {
		this.setExecuted(true);
		return false;
	}

	@Override
	public Boolean containsSelected() {
		return false;
	}

	
}
