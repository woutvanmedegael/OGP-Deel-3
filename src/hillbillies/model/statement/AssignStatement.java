package hillbillies.model.statement;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class AssignStatement extends Statement{

	@Override
	public Boolean containsSelected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Boolean execute(ContextWrapper context) throws WorldException {
		return null;
	}

}
