package hillbillies.model.scheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
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
		System.out.println("in remove task");
		if (task!=null){
			System.out.println("in de if ");
			tasks.remove(task);
			System.out.println("succesvol verwijderd uit deze scheduler");
			task.removeScheduler(this);
			System.out.println("succesvol zichzelf verwijderd");
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
		prevtask.removeScheduler(this);
		if (tasks.remove(prevtask)){
			tasks.add(newtask);
		}
	}

	
	@Override
	public boolean checkTaskPartOfScheduler(Collection<Task> givenTasks) {
		for (Task task : givenTasks){
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
	
	public Iterator<Task> getIterator(){
		return this.tasks.iterator();
	}
	
	
	
	
	
	
	
	
}
