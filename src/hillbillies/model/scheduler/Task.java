package hillbillies.model.scheduler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import hillbillies.model.ContextWrapper;
import hillbillies.model.ITask;
import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.Statement;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class Task implements Comparable<Task>,ITask{
	
	
	private final Statement statement;
	private Unit assignedUnit = null;
	private int priority = 0;
	private String name = null;
	private Set<Scheduler> schedulers = new HashSet<Scheduler>();
	private final ContextWrapper contextWrapper = new ContextWrapper();
	
	
	



	public Set<Scheduler> getSchedulers() {
		return schedulers;
	}



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
		return this.getAssignedUnit() !=null;
	}


	public void execute(World world, Unit unit) throws WorldException{
		this.contextWrapper.setExecutingUnit(unit);
		this.contextWrapper.setThisWorld(world);
		this.statement.execute(this.contextWrapper);
		
	}
	
	public Task(String name, int prio, Statement stat, Position selectedPos) throws SyntaxException{
		if (selectedPos==null && this.containsSelectedKeyword()){
			throw new SyntaxException();
		}
		if (name==null || stat==null){
			throw new IllegalArgumentException();
		}
		this.priority = prio;
		this.name = name;
		this.statement = stat;
		this.contextWrapper.setSelectedPos(selectedPos);
		
		
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
		// TODO set statements op not-executed.
		this.assignUnit(null);
		if (this.getPriority() >0){
				this.setPriority(this.getPriority()/2);
		}
		else if (this.getPriority() < 0){
			if (this.getPriority() > Integer.MIN_VALUE/2){
			this.setPriority(this.getPriority()*2);}
			else{
				this.setPriority(Integer.MIN_VALUE);
			}
		}
		else{
			this.setPriority(-1);
		}
		for (Scheduler scheduler:this.schedulers){
			scheduler.removeTask(this);
			scheduler.addTask(this);
			
		}
		
	}
	
	

	
}
