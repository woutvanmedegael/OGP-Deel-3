package hillbillies.model.scheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import hillbillies.model.IScheduler;

public class Scheduler implements IScheduler{

	private TreeSet<Task> tasks = new TreeSet<>();
	
	@Override
	public void addTask(Task task){
		if (task!=null){
			tasks.add(task);
			task.addScheduler(this);
		}
	}
	
	@Override
	public void removeTask(Task task){
		if (task!=null){
			tasks.remove(task);
			task.removeScheduler(this);
			}
	}
	
	public boolean isPartOfScheduler(Task task){
		if (task==null){
			return false;
		}
		return tasks.contains(task);
	}
	
	public Task getHighestPrio(){
		return tasks.first();
	}
	
	public ArrayList<Task> getTasks(){
		return new ArrayList<Task>(tasks);
	}
	
	public void replaceTask(Task prevtask, Task newtask){
		if (tasks.remove(prevtask)){
			tasks.add(newtask);
		}
		//TODO stop prevtask
	}

	
	@Override
	public boolean checkTaskPartOfScheduler(Collection<Task> Task) {
		for (Task task : tasks){
			if (!this.isPartOfScheduler(task)){
				return false;
			}
		}
		return true;
	}

	@Override
	public Task getHighestPrioNonActiveTask() {
		for (Task t: this.tasks){
			if (!t.isExecuting()){
				return t;
			}
		}
		return null;
	}

	@Override
	public Collection<Task> allTasks() {
		Set<Task> myTasks = new HashSet<>(this.tasks);
		return myTasks;
		
	}

	@Override
	public Collection<Task> getTasksSatisfying(Predicate<Task> pred) {
		Set<Task> myTasks = (Set<Task>)this.allTasks();
		myTasks.stream().
			filter(pred);
		return myTasks;
	}
	
	
	
	
	
	
	
}
