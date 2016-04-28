package hillbillies.model.scheduler;

import java.util.ArrayList;
import java.util.Set;

import hillbillies.model.ITask;
import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.Statement;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class Task implements Comparable<Task>,ITask{
	
	
	private final Statement statement;
	private boolean executing = false;
	private Unit assignedUnit = null;
	private int priority = 0;
	private String name = null;
	private Set<Scheduler> schedulers = null;
	
	
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
	
	public Task(String name, int prio, Statement stat, Position selectedCube) throws SyntaxException{
		if (selectedCube==null && this.containsSelectedKeyword()){
			throw new SyntaxException();
		}
		if (name==null || stat==null){
			throw new IllegalArgumentException();
		}
		this.priority = prio;
		this.name = name;
		this.statement = stat;
		
	}
	
	public Boolean containsSelectedKeyword(){
		return statement.containsSelected();
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


	@Override
	public void assignUnit(Unit unit) {
		this.assignedUnit = unit;
		
	}



	@Override
	public void setPriority(int newPrio) {
		this.priority = newPrio;
		
	}



	@Override
	public void addScheduler(Scheduler scheduler) {
		if (scheduler!=null){
			this.schedulers.add(scheduler);
		}
		
	}



	@Override
	public void removeScheduler(Scheduler scheduler) {
		this.schedulers.remove(scheduler);
	}



	@Override
	public void interrupt() {
		// TODO Auto-generated method stub
		
	}
	
	

	
}
