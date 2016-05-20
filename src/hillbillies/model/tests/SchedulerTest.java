package hillbillies.model.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.exceptions.WorldException;
import hillbillies.model.expressions.TrueExpression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.PrintStatement;
import hillbillies.model.task.Task;
import hillbillies.model.world.Faction;
import hillbillies.model.world.Scheduler;
import hillbillies.model.world.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part3.facade.Facade;
import hillbillies.part3.programs.TaskParser;
import ogp.framework.util.ModelException;

public class SchedulerTest {
	private static final int TYPE_AIR = 0;
	private static final int TYPE_ROCK = 1;
	private static final int TYPE_TREE = 2;
	private static final int TYPE_WORKSHOP = 3;
	
	private Scheduler schedulerTest;
	World world;
	Facade facade;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	Unit unit;

	@Before
	public void setUp() throws Exception {
		facade = new Facade();
		int[][][] types = new int[3][3][3];
		types[1][1][0] = TYPE_ROCK;
		types[1][1][1] = TYPE_ROCK;
		types[1][1][2] = TYPE_TREE;
		types[2][2][2] = TYPE_WORKSHOP;
		
		world = facade.createWorld(types, new DefaultTerrainChangeListener());
		unit = facade.createUnit("Test", new int[] { 0, 0, 0 }, 50, 50, 50, 50, true);
		facade.addUnit(unit, world);
		Faction faction = facade.getFaction(unit);
		schedulerTest = facade.getScheduler(faction);
	}

	@After
	public void tearDown() throws Exception {
	}
	/**
	 * Using the world created in the 
	 * @throws ModelException 
	 * @throws WorldException 
	 */
	@Test
	public void testAddRemove() throws ModelException, WorldException {
		Scheduler scheduler = new Scheduler();
		Task task1 = new Task("name1", 9,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task1);
		Task task2 = new Task("name2", 3,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task2);
		Task task3 = new Task("name3", 30,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task3);
		Task task4 = new Task("name4", 10,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task4);
		Task task5 = new Task("name5", 2,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task5);
		assertTrue(scheduler.getTasks().contains(task3));
		scheduler.removeTask(task3);
		assertFalse(scheduler.getTasks().contains(task3));
		assertTrue(scheduler.getTasks().contains(task1));
		assertTrue(scheduler.getTasks().contains(task2));
		assertTrue(scheduler.getTasks().contains(task4));
		assertTrue(scheduler.getTasks().contains(task5));
		scheduler.addTask(task3);
		assertTrue(scheduler.getTasks().contains(task3));
	}
	
	@Test
	public void testReplaceTask() throws WorldException{
		Scheduler scheduler= new Scheduler();
		Task task1 = new Task("name1", 9,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task1);
		Task task2 = new Task("name2", 3,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task2);
		Task task3 = new Task("name3", 30,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task3);
		Task task4 = new Task("name4", 10,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task4);
		Task task5 = new Task("name5", 2,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task5);
		
		assertTrue(scheduler.getTasks().contains(task3));
		Task task6 = new Task("name6",1000,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		assertFalse(scheduler.getTasks().contains(task6));
		scheduler.replaceTask(task3, task6);
		assertTrue(scheduler.getTasks().contains(task6));
		assertFalse(scheduler.getTasks().contains(task3));
	}
	
	@Test
	public void testGetHighestPriorityTask() throws WorldException{
		Scheduler scheduler= new Scheduler();
		Task task1 = new Task("name1", 9,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task1);
		Task task2 = new Task("name2", 3,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task2);
		Task task3 = new Task("name3", 30,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task3);
		Task task4 = new Task("name4", 10,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task4);
		Task task5 = new Task("name5", 2,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task5);
		//general
		assertTrue(scheduler.getHighestPrio() == task3);
		assertTrue(scheduler.getHighestPrioNonActiveTask(null)== task3);
		//tested for a specific unit;
		task3.setAssignedUnit(unit);
		assertTrue(scheduler.getHighestPrioNonActiveTask(null)== task4);
		assertTrue(scheduler.getHighestPrioNonActiveTask(unit) == task3);
		
		
		
	}
	@Test
	public void testArePartOfScheduler() throws WorldException {
		Scheduler scheduler= new Scheduler();
		Task task1 = new Task("name1", 9,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task1);
		Task task2 = new Task("name2", 3,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task2);
		Task task3 = new Task("name3", 30,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task3);
		Task task4 = new Task("name4", 10,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task4);
		Task task5 = new Task("name5", 2,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task5);
		
		Task task6 = new Task("name6",1000,new PrintStatement<TrueExpression>(new TrueExpression()), null);

		assertTrue(scheduler.isPartOfScheduler(task2));
		assertFalse(scheduler.isPartOfScheduler(task6));
		Collection<Task> shouldBeTrue1 = new HashSet<Task>(Arrays.asList(task2,task5));
		Collection<Task> shouldBeTrue2 = new HashSet<Task>(Arrays.asList(task4));
		Collection<Task> shouldBeTrue3 = new HashSet<Task>(Arrays.asList(task3,task1,task4));
		Collection<Task> shouldBeFalse = new HashSet<Task>(Arrays.asList(task3,task1,task4,task6));

		assertTrue(scheduler.checkTaskPartOfScheduler(shouldBeTrue1));
		assertTrue(scheduler.checkTaskPartOfScheduler(shouldBeTrue2));
		assertTrue(scheduler.checkTaskPartOfScheduler(shouldBeTrue3));
		assertFalse(scheduler.checkTaskPartOfScheduler(shouldBeFalse));
		}
	@Test
	public void testReturnTasks() throws WorldException {
		//Return all
		Scheduler scheduler= new Scheduler();
		Task task1 = new Task("name1", 9,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task1);
		Task task2 = new Task("name2", 12,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task2);
		Task task3 = new Task("name3", 30,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task3);
		Task task4 = new Task("name4", 10,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task4);
		Task task5 = new Task("name5", 2,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		scheduler.addTask(task5);
		Task task6 = new Task("name6", 100,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		Collection<Task> allTheTasks =  new ArrayList<Task>(Arrays.asList(task1,task2,task3,task4,task5));
		assertTrue(scheduler.getTasks().containsAll(allTheTasks));
		allTheTasks.add(task6);
		assertFalse(scheduler.getTasks().containsAll(allTheTasks));
		allTheTasks.remove(task6);
		
		
	
		//specific condition
		Set<Task> c= (Set<Task>) scheduler.getTasksSatisfying(priorityIsHigherThan10());
		System.out.println(c);
		assertTrue(c.equals(new HashSet<Task>(Arrays.asList(task3,task2))));
		Set<Task> d = (Set<Task>) scheduler.getTasksSatisfying(priorityIsLowerThan10());
		assertTrue(d.equals(new HashSet<Task>(Arrays.asList(task1,task5))));
		Set<Task> e = (Set<Task>)  scheduler.getTasksSatisfying(priorityIs10());
		assertTrue(e.equals(new HashSet<Task>(Arrays.asList(task4))));
		Set<Task> f = (Set<Task>) scheduler.getTasksSatisfying(nameIsname5());
		assertTrue(f.equals(new HashSet<Task>(Arrays.asList(task5))));
	}
	
	

	
	    public Predicate<Task> priorityIsHigherThan10() {
	        return t-> t.getPriority() > 10 ;
	    }
	     
	    public  Predicate<Task> priorityIsLowerThan10() {
	        return t-> t.getPriority() < 10 ;
	    }
	     
	    public Predicate<Task> priorityIs10() {
	        return t-> t.getPriority()==10;
	    }
	     
	    public Predicate<Task> nameIsname5() {
	        return t-> t.getName().equals("name5");
	    }
	  
	
	
	@Test
	public void testMarkTaskAsScheduled() throws WorldException, ModelException {
		Task task1 = new Task("name1", 9,new PrintStatement<TrueExpression>(new TrueExpression()),null);
		schedulerTest.addTask(task1);
		Task task2 = new Task("name2", 12,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		schedulerTest.addTask(task2);
		Task task3 = new Task("name3", 30,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		schedulerTest.addTask(task3);
		Task task4 = new Task("name4", 10,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		schedulerTest.addTask(task4);
		//assign unit and make sure that the highest priority task is the task that gets executed
		assertTrue(unit.getFaction().getNextTask(unit) == task3);
		task2.setAssignedUnit(this.unit);
		unit.advanceTime(0.001);
		assertTrue(unit.getFaction().getNextTask(unit) == task2);
		Unit enemy = facade.spawnUnit(world, true);
		Unit dummy1 = facade.spawnUnit(world, true);
		Unit dummy2 = facade.spawnUnit(world, true);
		Unit dummy3 = facade.spawnUnit(world, true);
		Unit friend = facade.spawnUnit(world, true);
		assert (friend.getFaction()==unit.getFaction());
		Scheduler scheduler2 = enemy.getFaction().getScheduler();
		scheduler2.addTask(task2);
		scheduler2.addTask(task4);
		scheduler2.addTask(task1);
		assertFalse(friend.getFaction().getNextTask(friend)== task2);
		assertTrue(friend.getFaction().getNextTask(friend) == task4);
		assertTrue(enemy.getFaction().getNextTask(enemy)==task4);
		friend.advanceTime(0.001);
		assertTrue(enemy.getFaction().getNextTask(enemy)==task1);
		
		
		


	}
	
	//TODO; idee voor een beter test ? Miss met een advance time ?
	
	@Test
	public void testGotRemovedFromSchedulerAfterFinished() throws Exception {
		setUp();
		Task task1 = new Task("name1", 9,new PrintStatement<TrueExpression>(new TrueExpression()),null);
		Task task2 = new Task("name2", 12,new PrintStatement<TrueExpression>(new TrueExpression()), null);
		schedulerTest.addTask(task1);
		schedulerTest.addTask(task2);
		assertTrue(schedulerTest.isPartOfScheduler(task1));
		assertTrue(schedulerTest.isPartOfScheduler(task2));
		//terminate is called whenever a unit has finished it's task
		task2.terminate();
		assertFalse(schedulerTest.isPartOfScheduler(task2));
		assertTrue(schedulerTest.isPartOfScheduler(task1));
		
		
		
		
		
		
	}
	
	
	

}

