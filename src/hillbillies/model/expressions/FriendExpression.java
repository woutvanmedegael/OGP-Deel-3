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
		units.remove(unit);
		Unit friend = null;
		Unit prevfriend = null;
		for(Unit u: units)
		{
			
			friend = u;
		    if (!friend.equals(u) && (prevfriend==null || (unit.distanceTo(friend)<unit.distanceTo(prevfriend)))){
		    	prevfriend = friend;
		    }
		}
		if (prevfriend == null){
			//Idem: unit.interrupt()?
			return null;
		}
		return prevfriend;
		
	}

	

}
