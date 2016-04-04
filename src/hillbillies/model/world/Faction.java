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
	 * Adds a unit to this faction.
	 * @param unit 
	 * 		The unit to add.
	 */
	public void addUnit(Unit unit){
		units.add(unit);
	}
	/**
	 * Removes a unit from this faction.
	 * @param unit
	 * 		The unit to remove.
	 */
	public void removeUnit(Unit unit){
		units.remove(unit);
	}
	/**
	 * Returns the units from this faction.
	 * @return
	 * 		A set containing all units of this faction.
	 */
	public Set<Unit> getUnits(){
		return units;
	}
}
