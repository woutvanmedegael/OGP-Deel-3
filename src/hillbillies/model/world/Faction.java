package hillbillies.model.world;

import java.util.HashSet;
import java.util.Set;

import hillbillies.model.hillbilliesobject.unit.Unit;

public class Faction {
	/**
	 * Set containing all units of this faction.
	 */
	private final Set<Unit> units = new HashSet<>();
	/**
	 * Adds the given unit to this faction.
	 */
	public void addUnit(Unit unit){
		units.add(unit);
	}
	/**
	 * Removes the given unit from this faction.
	 */
	public void removeUnit(Unit unit){
		units.remove(unit);
	}
	/**
	 * Returns the units from this faction.
	 */
	public Set<Unit> getUnits(){
		return units;
	}
}
