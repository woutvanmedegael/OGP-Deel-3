package hillbillies.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.scheduler.Scheduler;
import hillbillies.model.scheduler.Task;
import hillbillies.model.world.Faction;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part3.facade.Facade;
import hillbillies.part3.programs.TaskParser;
import ogp.framework.util.ModelException;

public class test {
	
	static int[][][] ints = new int[10][10][10];
	
	private Facade facade;

	private static final int TYPE_AIR = 0;
	private static final int TYPE_ROCK = 1;
	private static final int TYPE_TREE = 2;
	private static final int TYPE_WORKSHOP = 3;

	public static void main(String[] args) throws WorldException, ModelException {
		Task task1 = new Task("ablk", 10, null, null);
		Task task2 = new Task(null, 20, null, null);
		Task task3 = new Task(null, -5, null, null);
		Scheduler scheduler = new Scheduler();
		scheduler.addTask(task1);
		scheduler.addTask(task2);
		scheduler.addTask(task3);
		Iterator<Task> it = scheduler.getIterator();
		System.out.println(it.hasNext());
		System.out.println(it.next().getPriority());
		System.out.println(it.hasNext());
		System.out.println(it.next().getPriority());
		System.out.println(it.hasNext());
		System.out.println(it.next().getPriority());
		System.out.println(it.hasNext());


	}

}
