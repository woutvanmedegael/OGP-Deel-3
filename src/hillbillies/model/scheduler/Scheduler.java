package hillbillies.model.scheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class Scheduler {

	private TreeSet<Task> tasks = new TreeSet<>();
	
	public void add(Task task){
		if (task!=null){
			tasks.add(task);
		}
	}
	
	public void remove(Task task){
		if (task!=null){
			tasks.remove(task);
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
		//TODO check voor not being executed
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
	
	public boolean arePartOfScheduler(Collection<Task> tasks){
		for (Task task : tasks){
			if (!this.isPartOfScheduler(task)){
				return false;
			}
		}
		return true;
	}
	
	
	
	
	
	
	
}
