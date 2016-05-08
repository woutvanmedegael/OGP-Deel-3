package hillbillies.model;

import java.util.HashMap;
import java.util.Map;

import hillbillies.model.expressions.Expression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.Cube;
import hillbillies.model.world.World;

public class ContextWrapper {
	private Unit executingUnit = null;
	private Position selectedPos;
	private World thisWorld = null;
	
	private final Map<String, Expression<?>> assignMapper = new HashMap<String, Expression<?>>();	
	
	public Position getSelectedPos(){
		return selectedPos;
	}
	public void setSelectedPos(Position selectedPos){
		this.selectedPos = selectedPos;
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
	public void addNewVariable(String key,Expression<?> expr){
		assignMapper.put(key, expr);
	}
	
	public Expression<?> returnVariable(String key){
	
		return assignMapper.get(key);
	}
	
	public void clear(){
		this.assignMapper.clear();
		this.executingUnit = null;
		this.thisWorld = null;
	}
	
	
}
