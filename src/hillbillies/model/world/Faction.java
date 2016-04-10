package hillbillies.model.world;

import java.util.HashSet;
import java.util.Set;
import hillbillies.model.hillbilliesobject.unit.Unit;
/**
 * @value
 * @author Wout Van Medegael & Adriaan Van Gerven
 */
/**
 * A class faction used to keep track of the units in a faction.
 */
public class Faction {
	/**
	 * Set containing all units of this faction.
	 */
	private final Set<Unit> units = new HashSet<>();
	/**
	 * Adds the given unit to this faction is made package accesible.
	 * @throws WorldException 
	 */
	void addUnit(Unit unit) throws WorldException{
		if (units.size()>=50){
			throw new WorldException();
		}
		units.add(unit);
	}
	/**
	 * Removes the given unit from this faction is made package accessible.
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