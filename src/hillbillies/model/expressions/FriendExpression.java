package hillbillies.model.expressions;

import java.util.Random;
import java.util.Set;

import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.Faction;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class FriendExpression extends UnitExpression{
	//WOUT
	@Override
	public Unit evaluate(World world, Unit unit, Position selectedCube) throws WorldException {
		Faction fact = unit.getFaction();
		Set<Unit> units = world.getUnitsOfFaction(fact);
		Random random = new Random();
		Unit friend = null;
		int index = random.nextInt(units.size());
		int i=0;
		for(Unit u: units)
		{
		    if (i == index)
		         friend = u;
		    i = i + 1;
		}
		if (friend == null){
			//Idem: unit.interrupt()?
			return null;
		}
		return friend;
		
	}

	

}
