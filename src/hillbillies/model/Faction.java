package hillbillies.model;

import java.util.HashSet;
import java.util.Set;

public class Faction {
	private final Set<Unit> units;
	public Faction(){
		units = new HashSet<Unit>();
	}
	public void addUnit(Unit unit){
		units.add(unit);
	}
	public void removeUnit(Unit unit){
		units.remove(unit);
	}
	public Set<Unit> getUnits(){
		return units;
	}
}
