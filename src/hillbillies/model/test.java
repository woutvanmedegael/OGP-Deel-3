package hillbillies.model;

import java.util.Set;

import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.Faction;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class test {
	
	static int[][][] ints = new int[10][10][10];

	public static void main(String[] args) throws WorldException {
		World world = new World(ints,null);
		Unit unit1 = world.spawnUnit(false);
		Unit unit2 = world.spawnUnit(false);
		Unit unit3 = world.spawnUnit(false);
		Unit unit4 = world.spawnUnit(false);
		Unit unit5 = world.spawnUnit(false);
		Unit unit6 = world.spawnUnit(false);
		Unit unit7 = world.spawnUnit(false);
		Unit unit8 = world.spawnUnit(false);
		Unit unit9 = world.spawnUnit(false);
		Faction faction = unit1.getFaction();
		Set<Unit> units = world.getUnitsOfFaction(faction);
		System.out.println(units.size());
		units.remove(unit1);
		System.out.println(units.size());
		units = world.getUnitsOfFaction(faction);
		System.out.println(units.size());

	}

}
