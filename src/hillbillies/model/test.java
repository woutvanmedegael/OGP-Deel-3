package hillbillies.model;

import java.util.Collections;
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
		Facade facade = new Facade();
		int[][][] types = new int[3][3][3];
		types[1][1][0] = TYPE_ROCK;
		types[1][1][1] = TYPE_ROCK;
		types[1][1][2] = TYPE_TREE;
		types[2][2][2] = TYPE_WORKSHOP;

		World world = facade.createWorld(types, new DefaultTerrainChangeListener());
		Unit unit = facade.createUnit("Test", new int[] { 0, 0, 0 }, 50, 50, 50, 50, true);
		facade.addUnit(unit, world);


		List<Task> tasks = TaskParser.parseTasksFromString(
				"name: \"operate workshop\"\npriority : -10\nactivities : moveTo workshop; work selected;", facade.createTaskFactory(),
				Collections.emptyList());
		System.out.println("starting iteration");
		System.out.println(tasks.get(0).isWellFormed());
		System.out.println("contains selected");
		System.out.println(tasks.get(0).containsSelectedKeyword());

	}

}
