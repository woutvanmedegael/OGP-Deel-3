package hillbillies.model.expressions;

import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class HereExpression extends PositionExpression{
	//WOUT
	@Override
	public Position evaluate(World world, Unit unit, Position selectedCube) throws WorldException {
		return new Position(unit.getxpos(),unit.getypos(),unit.getzpos(),world);
	}

	@Override
	public Boolean containsSelected() {
		return false;
	}

	


}
