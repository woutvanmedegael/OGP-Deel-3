package hillbillies.model;

import java.util.Collection;
import java.util.TreeSet;
import java.util.function.Predicate;

import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.scheduler.Task;

public interface IScheduler {
	
	TreeSet<Task> tasks = new TreeSet<>();
	
	public void addTask(Task task);
	
	public void removeTask(Task task);
	
	public void replaceTask(Task prevTask, Task newTask);
	
	public boolean checkTaskPartOfScheduler(Collection<Task> Task);
	
	public Task getHighestPrioNonActiveTask(Unit u);
	
	public Collection<Task> allTasks();
	
	public Collection<Task> getTasksSatisfying(Predicate<Task> pred);
	
}
