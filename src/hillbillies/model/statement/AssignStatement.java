package hillbillies.model.statement;

import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;

public class AssignStatement extends Statement{

	@Override
	public Boolean execute(World world, Unit unit, Position selectedCube) {
		return true;
		
	}

	@Override
	public Boolean containsSelected() {
		// TODO Auto-generated method stub
		return null;
	}

}
