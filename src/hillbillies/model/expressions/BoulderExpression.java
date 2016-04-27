package hillbillies.model.expressions;

import java.util.Random;
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
		Boulder prevboulder=null;
		Random random = new Random();
		
		for(Boulder b : boulders)
		{
			boulder = b;
		    if (unit.distanceTo(b)>unit.distanceTo(prevboulder)){
		    	prevboulder = boulder;
		    }
		}
		   
		
		
		if (prevboulder == null){
			//een unit.interrupt() roepen ofzo?
			return null;
		}
		return new Position(prevboulder.getDoublePosition()[0],prevboulder.getDoublePosition()[1],prevboulder.getDoublePosition()[2], world);
	}

	

}
