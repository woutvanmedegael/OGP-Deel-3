package hillbillies.model.scheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import hillbillies.model.hillbilliesobject.unit.Unit;
/**
 *  A class Scheduler used to manage the different tasks.
 *  @author Wout Van Medegael & Adriaan Van Gerven
 */
public class Scheduler{
	/**
	 * Final ArrayList tasks representing the tasks of this scheduler.
	 */
	private final ArrayList<Task> tasks = new ArrayList<>();
	/**
	 * A comparator used to compare 2 integers.
	 */
    private final Comparator<Task> comp = (t1, t2) -> Integer.compare( t1.getPriority(), t2.getPriority());
    
    /**
     * Adds a task to this scheduler.
     * @param  task
     *         The Task to add.
     * @post   If the given task was different from null the task is added to tasks
     * 			| new.tasks.contains(task)
     * @effect This scheduler is added to the scheduler of the task.
     * 			| task.addScheduler(new)
     * 
     */
	public void addTask(Task task){
		if (task!=null){
			tasks.add(task);
			task.addScheduler(this);
		}
	}
	
	/**
	 * Removes the given task from this scheduler.
	 * @param task
	 * 		  The Task to remove.
	 * @post If the given task was different from null the task is removed from tasks.
	 * 			| !new.task.contains(task)
	 * @effect The task removes this scheduler.
	 * 			| task.removeScheduler(new)
	 */
	public void removeTask(Task task){
		if (task!=null){
			tasks.remove(task);
			task.removeScheduler(this);
			}
	}
	/**
	 * Returns true if the given task is part of the scheduler.
	 * @param task
	 * 		  The Task to check.
	 * @return True if the task is different from null and the task is in tasks.
	 * 			| result == (task !=null  && tasks.contains(task))
	 * 
	 */
	public boolean isPartOfScheduler(Task task){
		if (task==null){
			return false;
		}
		return tasks.contains(task);
	}
	

	/**
	 * Returns the task in tasks with the highest priority.
	 * @return result = tasks.stream().filter(t->!t.isExecuting()).max(comp).get();
	 */
	public Task getHighestPrio(){
		Task highestPrio = null;
		highestPrio = tasks.stream().filter(t->!t.isExecuting()).max(comp).get();
		return highestPrio;
	}
	/**
	 * Returns the tasks of this scheduler.
	 */
	public ArrayList<Task> getTasks(){
		return new ArrayList<Task>(tasks);
	}
	/**
	 * Replaces the given prevTask with the given newTask in the tasks of this scheduler.
	 * @param prevTask
	 * 		  The old task to replace.
	 * @param newTask
	 * 		  The new task to insert.
	 * @effect If the prevTask was executing, the assigned unit interrupts the task
	 * 			| if (prevTask.isExecuting()) then prevTask.getAssignedUnit().interupt()
	 * @effect If the prevTask was in this scheduler's tasks the prevTask removes this scheduler.
	 * 			| prevTask.removeScheduler(this)
	 * @post   If the prevTask was in this scheduler's task the newTask is added to the tasks.
	 * 			| if tasks.remove(prevTask) then tasks.add(newTask)
	 * 
	 */
	public void replaceTask(Task prevTask, Task newTask){
		if (prevTask.isExecuting()){
			prevTask.getAssignedUnit().interrupt();
		}
		prevTask.removeScheduler(this);
		if (tasks.remove(prevTask)){
			tasks.add(newTask);
		}
	}

	/**
	 * Returns true if the collection of tasks is part of this scheduler.
	 * @param givenTasks
	 * 		  A collection of Tasks
	 * @return Returns true if  the condition this.isPartOfScheduler holds for every task in givenTasks.
	 * 		   for task in givenTasks {
	 *				if (!this.isPartOfScheduler(task)){
	 *					result == false}}
	 *		   result == true		
	 */
	public boolean checkTaskPartOfScheduler(Collection<Task> givenTasks) {
		for (Task task : givenTasks){
			if (!this.isPartOfScheduler(task)){
				return false;
			}
		}
		return true;
	}
	/**
	 * Returns the task  with the highest priority in the tasks of this scheduler that is not assigned to another unit.
	 * @param u
	 * @return 
	 *TODO:
	 */
	
	public Task getHighestPrioNonActiveTask(Unit u) {
		Task highestPrio = null;
		System.out.println(this.tasks);
		for (Task t: this.tasks){
			
			
			if (!t.isExecuting() && !t.isTerminated()){
				// Only for test suites.
				if (u==null){
					if (highestPrio == null || highestPrio.getPriority()<t.getPriority() && t.getAssignedUnit() ==null){
						highestPrio = t;
					}
				}
				else {
					if (t.getAssignedUnit() == u || t.getAssignedUnit()==null){
						if(highestPrio == null || highestPrio.getPriority()<t.getPriority()){
							highestPrio = t;
						}
					}
				}
			}
		}
		return highestPrio;
	}
	/**
	 * Returns a copy the tasks of this scheduler
	 * @return new HashSet<>(this.tasks)
	 */
	public Collection<Task> allTasks() {
		Set<Task> myTasks = new HashSet<>(this.tasks);
		return myTasks;
		
	}
	/**
	 * Returns the collection of tasks from this scheduler that satisfies the given predicate. 
	 * @param pred
	 * 	 	  | The predicate used for filtering the tasks of this scheduler.
	 * @return Returns the set of tasks that satisfies this condition.
	 * 	 		myTasks.stream().filter(pred).collect(Collectors.toSet());
	 */
	public Collection<Task> getTasksSatisfying(Predicate<Task> pred) {
		Set<Task> myTasks = (Set<Task>)this.allTasks();
		final Set<Task> filteredMyTasks =  myTasks.stream().filter(pred).collect(Collectors.toSet());
		return filteredMyTasks;
	}
	/**
	 * Returns an iterator returning all the tasks in descending order of priority in this scheduler.
	 * @return new Iterator<Task>f
	 */
	public Iterator<Task> getIterator(){
		return new Iterator<Task>(){
			
			private boolean[] handledTasks = new boolean[tasks.size()];
			private int nbTasksHandled;
			/**
			 * Returns true if the this iterator has a next element.
			 * @return
			 * 		|result == nbTasksHandled<tasks.size()
			 */
			@Override
			public boolean hasNext() {
				return (nbTasksHandled<tasks.size());
			}
			/**
			 * Returns the next task. This is the task from tasks with the highest priority for which the element with corresponding index in handledTasks has not been set to True.
			 * @return Returns the next Task.
			 * 		   Each task in tasks is checked, if the index of the task in tasks corresponds with false in handledTasks
			 * 		   and if the highestPrio equals null or the priority of the task is higher than the priority from highestPrio
			 * 		   then highestPrio becomes the task that is being checked.
			 * 		| for (int i = 0; tasks.size(); i++)
			 * 		| 		if (handledTasks[i] = false && (highestPrio == null || tasks.get(i).getPriority()>highestPrio.getPriority()))
			 * 		|			highestPrio = tasks.get(i)
			 * 		| result == highestPrio
			 * @post if the found highestPrio is different from null
			 * 			| if (highestPrio !=null)
			 * 				| new.handledTasks[tasks.indexOf(highestPrio)] = true
			 * 				| new.nbTasksHandled = old.nbTasksHandled +1
			 * 
			 */
			@Override
			public Task next() {
				Task highestPrio = null;
				for (int i=0;i<tasks.size();i++){
					if (!handledTasks[i]){
						if (highestPrio==null || (tasks.get(i).getPriority()>highestPrio.getPriority())){
							highestPrio = tasks.get(i);
						}
					}
				}
				if (highestPrio!=null){
					handledTasks[tasks.indexOf(highestPrio)]=true;
					nbTasksHandled++;
				}
				
				return highestPrio;
			}
			
		};
	}
	
	
	
	
	
	
	
	
}

