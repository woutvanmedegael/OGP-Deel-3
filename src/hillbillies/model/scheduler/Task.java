package hillbillies.model.scheduler;

import java.util.ArrayList;

import hillbillies.model.hillbilliesobject.unit.Unit;

public class Task implements Comparable<Task>{
	
	private final int priority;
	private final String name;
	private Unit assignedUnit = null;
	
	
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



	private boolean executing = false;
	public Task(String name, int prio){
		if (name==null){
			throw new IllegalArgumentException();
		}
		this.priority = prio;
		this.name = name;
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
