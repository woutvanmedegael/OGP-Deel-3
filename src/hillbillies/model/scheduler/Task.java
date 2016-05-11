package hillbillies.model.scheduler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import hillbillies.model.ContextWrapper;
import hillbillies.model.ITask;
import hillbillies.model.Position;
import hillbillies.model.SubType;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.Statement;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class Task implements Comparable<Task>,ITask{
	
	
	private final Statement statement;
	private Unit assignedUnit = null;
	private int priority = 0;
	private String name = null;
	private Set<Scheduler> schedulers = new HashSet<Scheduler>();
	private final ContextWrapper contextWrapper = new ContextWrapper();
	private final SubType tree;

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
	
	public void setUnit(Unit executingUnit){
		this.contextWrapper.setExecutingUnit(executingUnit);
	}
	
	public void setWorld(World thisWorld){
		this.contextWrapper.setThisWorld(thisWorld);
	}
	
	public Boolean hasFinished(){
		return this.statement.hasBeenExecuted();
	}
	
	public void finishTask(){
		this.statement.setExecuted(false);
		this.removeFromSchedulers();
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
		this.statement.setExecuted(false);
		this.contextWrapper.clear();
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
	
	public Boolean isWellFormed(){
		ArrayList<String> variables = new ArrayList<>();
		Boolean unassignedVariables = this.tree.containsUnassignedVariable(variables);
		Boolean uncoveredBreak = this.tree.containsUncoveredBreak();
		return !(unassignedVariables || uncoveredBreak);
	}
	
	

	
}
