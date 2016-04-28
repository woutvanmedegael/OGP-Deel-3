package hillbillies.model;

import java.util.ArrayList;
import java.util.Set;

import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.scheduler.Scheduler;
import hillbillies.model.scheduler.Task;
import hillbillies.model.statement.Statement;

public interface ITask {
	
	final ArrayList<Statement> statements = new ArrayList<Statement>();
	boolean executing = false;
	Unit assignedUnit = null;
	int priority = 0;
	String name = null;
	Set<Scheduler> schedulers = null;
	
	public void startExecuting();
	public void stopExecuting();
	public boolean isExecuting();
	public void assignUnit(Unit unit);
	public void setPriority(int newPrio);
	public int getPriority();
	public String getName();
	public int compareTo(Task task);
	public void addScheduler(Scheduler scheduler);
	public void removeScheduler(Scheduler scheduler);
	
}
