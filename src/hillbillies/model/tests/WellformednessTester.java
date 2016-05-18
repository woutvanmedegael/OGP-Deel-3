package hillbillies.model.tests;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.scheduler.Task;
import hillbillies.part3.facade.Facade;
import hillbillies.part3.programs.TaskParser;

public class WellformednessTester {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSelectedWelFormed() {
		// selected cubes are given
		Facade facade = new Facade();
		List<Task> tasks = TaskParser.parseTasksFromString(
				"name: \"work task\"\npriority: 1\nactivities: work selected;", facade.createTaskFactory(),
				Collections.singletonList(new int[] { 1, 1, 1 }));
		assertTrue(tasks.get(0).isWellFormed());
		// no selected cubes are given
		List<Task> tasks1 = TaskParser.parseTasksFromString(
				"name: \"work task\"\npriority: 1\nactivities: work selected;", facade.createTaskFactory(),
				null);
		System.out.println(tasks1.size());
		//TODO:? licht aan de wellformed precies?
		assertFalse(tasks1.get(0).isWellFormed());
		
		
	}
	//@Test
	public void testBreakWellPlaced(){
		Facade facade = new Facade();
		//break correctly placed
		List<Task> tasks = TaskParser.parseTasksFromString(
				"name: \"while statement example\"\npriority: 10000\nactivities: while (is_solid(13, 10, 10)) do work here; done;\nbreak ;", facade.createTaskFactory(),
				Collections.singletonList(null));
		Task goodTask = tasks.get(0);
		//break correctly placed in if then
		
		//break placed outside of while
		
		//break placed outside of while in if then
		
	}
	@Test
	public void testAssignVariables(){
		// test correct use
		
		//test unassigned variable
	}

}

