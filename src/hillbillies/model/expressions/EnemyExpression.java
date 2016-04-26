package hillbillies.model.expressions;

import java.util.Random;
import java.util.Set;

import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.Faction;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class EnemyExpression extends UnitExpression {

	@Override
	public Unit evaluate(World world, Unit unit) throws WorldException {
		//get random Enemy factions
		//TODO: kijke eens na of die faction niet echt verwijderd wordt van de facionlist
		Set<Faction> factionList = world.getActiveFactions();
		Random random = new Random();
		Faction randomFaction = null;
		factionList.remove(unit.getFaction());
		int index = random.nextInt(factionList.size());
		int i=0;
		for(Faction f: factionList)
		{
		    if (i == index)
		         randomFaction = f;
		    i = i + 1;
		}
		
		if (randomFaction == null){
			throw new WorldException();
		}
		//get random unit of the enemy factions
		Set<Unit> unitList = world.getUnitsOfFaction(randomFaction);
		Unit randomUnit =null;
		int index2 = random.nextInt(unitList.size());
		int k=0;
		for(Unit u: unitList)
		{
		    if (k == index2)
		         randomUnit = u;
		    k = k + 1;
		}
		
		if (randomUnit == null){
			throw new WorldException();
		}
		
		return randomUnit;
		
	}

}
