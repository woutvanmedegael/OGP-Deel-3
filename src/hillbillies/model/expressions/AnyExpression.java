package hillbillies.model.expressions;

import java.util.Set;

import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.Faction;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class AnyExpression extends UnitExpression{
	//ADRIAAN
	@Override
	public Unit evaluate(World world, Unit unit, Position selectedCube) throws WorldException {
		Set<Unit> units = world.getUnits();
		Unit testUnit= null;
		Unit closestUnit = null;
		for(Unit u: units)
		{
			if (!(u== unit)){
			testUnit= u;
		    if (closestUnit==null || (unit.distanceTo(testUnit)<unit.distanceTo(closestUnit))){
		    	closestUnit = testUnit;
		    }
		}
		}
		if (closestUnit == null){
			//Idem: unit.interrupt()?
			return null;
		}
		
		return closestUnit;
		
	}

	@Override
	public Boolean containsSelected() {
		return false;
	}
		

	}

	


