package hillbillies.model.expressions;

import java.util.Set;

import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.Log;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class LogExpression extends PositionExpression{
	//ADRIAAN
	
	@Override
	public Position evaluate(World world, Unit unit, Position selectedCube) throws WorldException {
		Set<Log> logs = world.getLogs();
		Log log = null;
		for(Log b : logs)
		{
		    if (unit.distanceTo(b)<unit.distanceTo(log)){
		    	log = b;
		    }
		}
		   
		
		
		if (log == null){
			//een unit.interrupt() roepen ofzo?
			return null;
		}
		return new Position(log.getDoublePosition()[0],log.getDoublePosition()[1],log.getDoublePosition()[2], world);
	}
	

	@Override
	public Boolean containsSelected() {

		return false;
	}

	

}
