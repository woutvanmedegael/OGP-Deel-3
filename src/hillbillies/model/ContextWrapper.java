package hillbillies.model;

import java.util.HashMap;
import java.util.Map;

import hillbillies.model.expressions.Expression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.Cube;
import hillbillies.model.world.World;

public class ContextWrapper {
	private Unit executingUnit = null;
	private Cube selectedCube;
	private World thisWorld = null;
	
	private final Map<String, Expression<Object>> assignMapper = new HashMap<String, Expression<Object>>();	
	
	public Cube getSelecteCube(){
		return selectedCube;
	}
	public void setSelectedCube(Cube selectedCube){
		this.selectedCube = selectedCube;
	}
	
	public Unit getExecutingUnit() {
		return executingUnit;
	}
	public void setExecutingUnit(Unit executingUnit) {
		this.executingUnit = executingUnit;
	}
	public World getThisWorld() {
		return thisWorld;
	}
	public void setThisWorld(World thisWorld) {
		this.thisWorld = thisWorld;
	}
	public void addNewVariable(String key,Expression value){
		assignMapper.put(key, value);
	}
	
	public Expression<Object> returnVariable(String key){
	
		return assignMapper.get(key);
	}
	
	
}
