package hillbillies.model.expressions;

import java.util.Set;

import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.Boulder;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class BoulderExpression extends PositionExpression {
	//WOUT
	
	
	@Override
	public Position evaluate(World world, Unit unit, Position selectedCube) throws WorldException {
		Set<Boulder> boulders = world.getBoulders();
		Boulder boulder = null;
		for(Boulder b : boulders)
		{
		    if (unit.distanceTo(b)<unit.distanceTo(boulder)){
		    	boulder = b;
		    }
		}
		   
		
		
		if (boulder == null){
			//een unit.interrupt() roepen ofzo?
			return null;
		}
		return new Position(boulder.getDoublePosition()[0],boulder.getDoublePosition()[1],boulder.getDoublePosition()[2], world);
	}

	@Override
	public Boolean containsSelected() {
		return false;
	}

	

}
