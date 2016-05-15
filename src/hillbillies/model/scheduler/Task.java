package hillbillies.model.scheduler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.SubType;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.Statement;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class Task implements Comparable<Task>{
	
	
	private final Statement statement;
	private Unit assignedUnit = null;
	private int priority = 0;
	private String name = null;
	private Set<Scheduler> schedulers = new HashSet<Scheduler>();
	private final ContextWrapper contextWrapper = new ContextWrapper();
	private final SubType tree;
	private boolean executing = false;

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

	public void setExecuting(boolean b){
		this.executing = b;
	}

	/**
	 * @return the executing
	 */
	public boolean isExecuting() {
		return this.executing;
	}
	
	public void setUnit(Unit executingUnit){
		this.contextWrapper.setExecutingUnit(executingUnit);
	}
	
	public void setWorld(World thisWorld){
		this.contextWrapper.setThisWorld(thisWorld);
	}
	
	public Boolean hasFinished(){
		return this.statement.hasBeenExecuted();
	}
	
	private void removeFromSchedulers(){
		for (Scheduler s: this.getSchedulers()){
			s.removeTask(this);
		}
	}


	public void execute() throws WorldException{
		try {
			this.statement.executeNext(this.contextWrapper);
		} catch (WrongVariableException e) {
			e.printStackTrace();
		}
		
	}
	
	public Task(String name, int prio, Statement stat, Position selectedPos){
		if (name==null || stat==null){
			throw new IllegalArgumentException();
		}
		this.priority = prio;
		this.name = name;
		this.statement = stat;
		this.contextWrapper.setSelectedPos(selectedPos);
		this.tree = new SubType(stat);
		
		
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

	public int compareTo(Task task){
		if (this.equals(task)){
			return 0;
		}
		if (task.getPriority()>this.getPriority()){	
			return 1;
		}
		return -1;
	} 



	public void setPriority(int newPrio) {
		this.priority = newPrio;
		
	}




	public void addScheduler(Scheduler scheduler) {
		if (scheduler!=null){
			this.schedulers.add(scheduler);
		}
		
	}




	public void removeScheduler(Scheduler scheduler) {
		this.schedulers.remove(scheduler);
	}



	public void interrupt() {
		this.statement.setExecuted(false);
		this.setExecuting(false);
		this.contextWrapper.clear();
		this.setAssignedUnit(null);
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
			
	}
		
	
	
	public Boolean isWellFormed(){
		ArrayList<String> variables = new ArrayList<>();
		Boolean unassignedVariables = this.tree.containsUnassignedVariable(variables);
		Boolean uncoveredBreak = this.tree.containsUncoveredBreak();
		return !(unassignedVariables || uncoveredBreak);
	}
	
	/**
	 * Terminate this Task.
	 *
	 * @post   This Task  is terminated.
	 *       | new.isTerminated()
	 * @post   ...
	 *       | ...
	 */
	 public void terminate() {
		 this.statement.setExecuted(false);
		 this.removeFromSchedulers();
		 this.isTerminated = true;
	 }
	 
	 /**
	  * Return a boolean indicating whether or not this Task
	  * is terminated.
	  */
	 @Basic @Raw
	 public boolean isTerminated() {
		 return this.isTerminated;
	 }
	 
	 /**
	  * Variable registering whether this person is terminated.
	  */
	 private boolean isTerminated = false;
	 
	
	
	
	

	
}
