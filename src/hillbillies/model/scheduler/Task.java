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
/**
 * A class Task used to represent a Task object
 * @author Wout Van Medegael & Adriaan Van Gerven
 *
 */
public class Task implements Comparable<Task>{
	
	/**
	 * The statement of this task.
	 */
	private final Statement statement;
	/**
	 * The assigned unit of this task, initialized at null.
	 */
	private Unit assignedUnit = null;
	/**
	 * The priority of this task.
	 */
	private int priority = 0;
	/**
	 * The name of this task.
	 */
	private String name = null;
	/**
	 * A set with the different schedulers in which the task is scheduled.
	 */
	private Set<Scheduler> schedulers = new HashSet<Scheduler>();
	/**
	 * A contextWrapper used to bundle different objects used to execute a task.
	 */
	private final ContextWrapper contextWrapper = new ContextWrapper();
	/**
	 * A SubType used to check the welformedness of this task.
	 */
	private final SubType tree;
	/**
	 * A boolean to keep track whether this task is executing.
	 */
	private boolean executing = false;
	/**
	 * Returns the schedulers of this task.
	 * @return this.schedulers
	 */
	public Set<Scheduler> getSchedulers() {
		return this.schedulers;
	}



	/**
	 * Returns the unit that is assigned to this task.
	 * @return this.assignedUnit
	 */
	public Unit getAssignedUnit() {
		return this.assignedUnit;
	}



	/**
	 * Sets the assignedUnit to the given unit.
	 * @param assignedUnit
	 * 		  The unit to assign.
	 * @post The assignedUnit is equal to the given unit.
	 * 		 | new.getAssignedUnit() == assignedUnit
	 */
	public void setAssignedUnit(Unit assignedUnit) {
		this.assignedUnit = assignedUnit;
	}

	/**
	 * Sets the setExecuting to the given boolean.
	 * @param b
	 * 		  The boolean to set executing to
	 * @post  isExecuting is equal to the given boolean.
	 * 		 | new.isExecuting == b
	 */
	public void setExecuting(boolean b){
		this.executing = b;
	}

	/**
	 * @return this.executing
	 */
	public boolean isExecuting() {
		return this.executing;
	}
	/**
	 * Sets the given Unit in the contextWrapper.
	 * @param executingUnit
	 * 		  The unit to set in the contextWrapper
	 * @effect The contextWrapper sets it's executingUnit.
	 * 		  | contextWrapper.setExecutingUnit(executingUnit)
	 */
	public void setUnit(Unit executingUnit){
		this.contextWrapper.setExecutingUnit(executingUnit);
	}
	
	/**
	 * Sets the given World in the contextWrapper.
	 * @param thisWorld
	 * 		  The World to set in the contextWrapper
	 * @effect The contextWrapper sets it's World
	 * 		  | contextWrapper.setThisWorld(thisWorld)
	 */
	public void setWorld(World thisWorld){
		this.contextWrapper.setThisWorld(thisWorld);
	}
	/**
	 * Returns true if the task has finished and thus the statement has been executed.
	 * @return @effect this.statement.HasBeenExecuted()
	 */
	public Boolean hasFinished(){
		return this.statement.hasBeenExecuted();
	}
	/**
	 * This tasks removes itself from all it's schedulers and clears the schedulers list.
	 * @effect The task is removed from all the schedulers and removes the scheduler.
	 * 			| for (Scheduler s: this.getSchedulers)
	 * 				| s.removeTask(this)
	 * @effect The list schedulers is cleared.
	 * 			|this.schedulers.clear()
	 * 
	 */
	private void removeFromSchedulers(){
		for (Scheduler s: this.getSchedulers()){
			s.removeTask(this);
		}
		this.schedulers.clear();
	}

	/**
	 * Executes this task.
	 * @effect If possible (no wrong variableException is thrown) the statement of this task is executed else the StackTrace is printed.
	 * 			| this.statement.executNext(this.contextWrapper)
	 * @throws WorldException
	 * 			| An exception is thrown if something went wrong in executing the statement. 
	 */
	public void execute() throws WorldException{
		try {
			this.statement.executeNext(this.contextWrapper);
		} catch (WrongVariableException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Initialize this Task with given name, priority, stat and selectedPos
	 * @param name
	 * 			| The name for this task.
	 * @param prio
	 * 			| The priority for this task.
	 * @param stat
	 * 			| The statement for this task.
	 * @param selectedPos
	 * 			| The selectedPos for this task.
	 * @effect The priority is set to the given priority.
	 * 			| this.setPriority(prio)
	 * @effect The name is set to the given name.
	 * 			| this.setName(name)
	 * @post   The statement is set.
	 * 			|new.statement = stat
	 * @effect The selectedPos is set in the contexWrapper
	 * 			| this.contextWrapper.setSelectedPos(selectedPos)
	 * @post   This.tree is set to a new SubTyp(stat)
	 * 		 	new.tree = //TODO
	 * @post ...
	 * 		  
	 * 
	 */
	public Task(String name, int prio, Statement stat, Position selectedPos){
		if (name==null || stat==null){
			throw new IllegalArgumentException();
		}
		this.setPriority(prio);
		this.setName(name);
		this.statement = stat;
		this.contextWrapper.setSelectedPos(selectedPos);
		this.tree = new SubType(stat);
		
		
	}
	/**
	 * Sets the name of this task.
	 * @param name
	 * 		  The name for this task.
	 * @post new.name = name;
	 */
	private void setName(String name) {
		this.name = name;
		
	}


	/**
	 * Returns true if and only if tree.containsSelected().
	 * @return @effect tree.containsSelected()
	 */
	public Boolean containsSelectedKeyword(){
		return tree.containsSelected();
	}




	/**
	 * Returns the priority of this task
	 * @return the priority
	 * 			| result == this.priority
	 */
	public int getPriority() {
		return this.priority;
	}



	/**
	 * Returns the name of this task
	 * @return the name
	 * 			| result == this.name
	 * 			
	 */
	
	public String getName() {
		return name;
	}
	/**
	 * Compares this task to a given task based on priority
	 * @return Returns 0 if this task is equal to the other task if the priority of the given task is bigger than the priority of this 
	 * 			task 1 is returned else -1
	 * 			| if (this.equals(task)) then result ==0
	 * 			| else if (task.getPriority()>this.getPriority()) then result == 1 else result == 0   
	 */
	//TODO: override is erbij gezet, ook de equal zou eigenlijk op priority moetn zijn 
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


	/**
	 * Sets the priority of this task to the given priority.
	 * @param newPrio
	 * 		  The new Priority
	 * @post new.priority = newPrio
	 */
	public void setPriority(int newPrio) {
		this.priority = newPrio;
		
	}



	/**
	 * Adds a scheduler to the schedulers of this task.
	 * @param scheduler	
	 * 		  	| The scheduler to add
	 * @post if the scheduler is not null the scheduler is added to this.schedulers
	 * 			| if (scheduler != null) then new.schedulers.contains(scheduler)
	 */
	public void addScheduler(Scheduler scheduler) {
		if (scheduler!=null){
			this.schedulers.add(scheduler);
		}
		
	}

	/**
	 * Removes the given scheduler from the schedulers of this task.
	 * @param scheduler
	 * 			| The scheduler to remove.
	 * @effect  The scheduler is removed
	 * 			|this.schedulers.remove(scheduler)
	 * 
	 */
	public void removeScheduler(Scheduler scheduler) {
		this.schedulers.remove(scheduler);
	}


	/**
	 * Interrupts this task and changes the priority.
	 * @effect The statement is not fully executed and there for set to false.
	 * 			| this.statement.setExecuted(false)
	 * @effect The task is not being executed anymore, executing is set to false.
	 * 			| this.setExecuting(false)
	 * @effect   The contextWrapper is cleared
	 * 			|this.contexWrapper.clear()
	 * @effect The assigned unit is set to null
	 * 			| this.setAssignedUnit(null)
	 * @post   The priority is reduced in the following way
	 * 			|if this.getPriority() >0 then new.getPriority == this.getPriority/2
	 * 			| else if this.getPriotiry<0 and this.getPriority> Integer.MINVALUE/2 then new.getPriority = this.getPriority()*2
	 * 			| else new.getPriority == -1
	 */
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
		
	
	/**
	 * Returns true if this task is wellFormed.
	 * 
	 * @return
	 */
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
	 * @effect  The statement's setExecuted is set to false.
	 *       | this.statement.setExecuted(false)
	 * @effect This Task removes itself from the schedulers
	 * 		 | this.removeFromSchedulers()
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
