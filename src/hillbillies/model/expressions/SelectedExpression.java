package hillbillies.model.expressions;

import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class SelectedExpression extends PositionExpression{
	//WOUT
	@Override
	public Position evaluate(World world, Unit unit, Position selectedCube) throws WorldException {
		if (selectedCube==null){
			throw new IllegalArgumentException();
		}
		return selectedCube;
	}

	

}
