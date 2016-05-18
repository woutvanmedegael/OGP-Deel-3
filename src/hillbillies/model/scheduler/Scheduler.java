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

import hillbillies.model.IScheduler;
import hillbillies.model.hillbilliesobject.unit.Unit;

public class Scheduler implements IScheduler{

	private ArrayList<Task> tasks = new ArrayList<>();
    final Comparator<Task> comp = (t1, t2) -> Integer.compare( t1.getPriority(), t2.getPriority());

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
	
	private Task getHighestPriorityOf(Task t, Task u){
		if (t==null){
			return u;
		}
		if (u==null){
			return t;
		}
		if (t.getPriority()>=u.getPriority()){
			return t;
		}
		return u;
	}
	
	public Task getHighestPrio(){
		Task highestPrio = null;
		highestPrio = tasks.stream().filter(t->!t.isExecuting()).max(comp).get();
//		this.tasks.stream().filter(t->!t.isExecuting()).reduce(null,(t,u)->));
//		for (Task t: this.tasks){
//			if (highestPrio==null || highestPrio.getPriority()<t.getPriority()){
//				highestPrio = t;
//			}
//		}
		return highestPrio;
	}
	
	public ArrayList<Task> getTasks(){
		return new ArrayList<Task>(tasks);
	}
	
	public void replaceTask(Task prevtask, Task newtask){
		if (prevtask.isExecuting()){
			prevtask.getAssignedUnit().interrupt();
		}
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
	public Task getHighestPrioNonActiveTask(Unit u) {
		Task highestPrio = null;
		System.out.println(this.tasks);
		for (Task t: this.tasks){
			
			
			if (!t.isExecuting() && !t.isTerminated()){
				System.out.println("got here?");
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
//			if (!t.isExecuting() && ((highestPrio==null || highestPrio.getPriority()<t.getPriority())) && !t.isTerminated()){
//				if (t.getAssignedUnit()==null || t.getAssignedUnit()==u){
//					highestPrio = t;
//				}
//			}
		}
		return highestPrio;
	}

	@Override
	public Collection<Task> allTasks() {
		Set<Task> myTasks = new HashSet<>(this.tasks);
		return myTasks;
		
	}

	@Override
	public Collection<Task> getTasksSatisfying(Predicate<Task> pred) {
		Set<Task> myTasks = (Set<Task>)this.allTasks();
		final Set<Task> filteredMyTasks =  myTasks.stream().filter(pred).collect(Collectors.toSet());
		return filteredMyTasks;
	}
	
	public Iterator<Task> getIterator(){
		return new Iterator<Task>(){
			
			private boolean[] handledTasks = new boolean[tasks.size()];
			private int nbTasksHandled;
			
			@Override
			public boolean hasNext() {
				return (nbTasksHandled<tasks.size());
			}

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

