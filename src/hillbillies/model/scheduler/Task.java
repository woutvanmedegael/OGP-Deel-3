package hillbillies.model.scheduler;

import java.util.ArrayList;

import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.Statement;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class Task implements Comparable<Task>{
	
	private final int priority;
	private final String name;
	private Unit assignedUnit = null;
	private Position selectedCube;
	private final Statement statement;
	
	
	/**
	 * @return the assignedUnit
	 */
	public Unit getAssignedUnit() {
		return assignedUnit;
	}



	/**
	 * @param assignedUnit the assignedUnit to set
	 */
	public void setAssignedUnit(Unit assignedUnit) {
		this.assignedUnit = assignedUnit;
	}



	/**
	 * @return the executing
	 */
	public boolean isExecuting() {
		return executing;
	}



	/**
	 * @param executing the executing to set
	 */
	public void setExecuting(boolean executing) {
		this.executing = executing;
	}

	public void execute(World world, Unit unit, Position selectedCube) throws WorldException{
		this.statement.execute(world, unit, selectedCube);
		
	}

	private boolean executing = false;
	
	public Task(String name, int prio, Statement stat, Position selectedCube){
		if (selectedCube==null){
			this.checkSelectedKeyword();
		}
		if (name==null || stat==null){
			throw new IllegalArgumentException();
		}
		this.priority = prio;
		this.name = name;
		this.statement = stat;
		
	}
	
	public void checkSelectedKeyword(){
		if (statement.checkSelectedKeyword()){
			throw new IllegalArgumentException();
		}
	}




	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Task task){
		if (this.equals(task)){
			return 0;
		}
		if (task.getPriority()>this.getPriority()){	
			return 1;
		}
		return -1;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Task [priority=" + priority + ", name=" + name + "]";
	}

	
}
