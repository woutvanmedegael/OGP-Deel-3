package hillbillies.model.scheduler;

import java.util.ArrayList;

import Activity.Activity;
import hillbilliesobject.unit.Unit;

public class Task implements Comparable<Task>{
	
	private final int priority;
	private final String name;
	private ArrayList<Activity> activities;
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
	public Task(String name, int prio, ArrayList<Activity> activities){
		if (name==null || activities.size()==0){
			throw new IllegalArgumentException();
		}
		this.priority = prio;
		this.name = name;
		this.activities = activities;
	}
	
	
	
	/**
	 * @return the activities
	 */
	public ArrayList<Activity> getActivities() {
		return activities;
	}



	/**
	 * @param activities the activities to set
	 */
	public void setActivities(ArrayList<Activity> activities) {
		this.activities = activities;
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
