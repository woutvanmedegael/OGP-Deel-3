package hillbillies.model;

import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.scheduler.Scheduler;
import hillbillies.model.scheduler.Task;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public interface ITask extends Comparable<Task> {
	
	public void execute(World world, Unit unit) throws WorldException;
	public boolean isExecuting();
	public void assignUnit(Unit unit);
	public void setPriority(int newPrio);
	public int getPriority();
	public String getName();
	public void addScheduler(Scheduler scheduler);
	public void removeScheduler(Scheduler scheduler);
	public void interrupt();
	
}
