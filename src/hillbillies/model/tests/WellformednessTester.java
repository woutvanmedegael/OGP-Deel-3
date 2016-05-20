package hillbillies.model.tests;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.task.Task;
import hillbillies.model.task.TaskFactory;
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
	public void testBreakWellPlaced(){
		Facade facade = new Facade();
		//no break in while
		List<Task> tasks = TaskParser.parseTasksFromString(
				"name: \"while without break\"\npriority: 10000\nactivities: while is_solid(13, 10, 10) do work here; done", facade.createTaskFactory(),
				null);
		Task correctWhileWithoutBreak = tasks.get(0);
		assertTrue(correctWhileWithoutBreak.isWellFormed());
		//correctly placed in while
		tasks = TaskParser.parseTasksFromString("name: \"while with correct break\"\npriority: 10000\nactivities: while is_solid(13, 10, 10) do work here; break; done", facade.createTaskFactory(), null);
		Task correctWhileWithBreak = tasks.get(0);
		assertTrue(correctWhileWithBreak.isWellFormed());
		//break correctly placed in if then,trying different cases 
		tasks = TaskParser.parseTasksFromString("name: \"while with correct break in if then else\"\npriority: 10000"
				+ "\nactivities: while is_solid(13, 10, 10) do if is_passable(12,10,10) then work here; else break;  fi done" , facade.createTaskFactory(), 
				null);
		Task correctWhileWithBreakInIfThenElse = tasks.get(0);
		assertTrue(correctWhileWithBreakInIfThenElse.isWellFormed());
		tasks = TaskParser.parseTasksFromString("name: \"while with correct break in if then else\"\npriority: 10000"
				+ "\nactivities: while is_solid(13, 10, 10) do if is_passable(12,10,10) then break; else work here;  fi done" , facade.createTaskFactory(), 
				null);
		correctWhileWithBreakInIfThenElse = tasks.get(0);
		assertTrue(correctWhileWithBreakInIfThenElse.isWellFormed());
		
		tasks = TaskParser.parseTasksFromString("name: \"while with correct break in if then else\"\npriority: 10000"
				+ "\nactivities: while is_solid(13, 10, 10) do if is_passable(12,10,10) then if is_passable(10,9,9) then break; else work here; fi else work here;  fi done" , facade.createTaskFactory(), 
				null);
		correctWhileWithBreakInIfThenElse = tasks.get(0);
		assertTrue(correctWhileWithBreakInIfThenElse.isWellFormed());
		
		//break placed outside of while
		tasks = TaskParser.parseTasksFromString(
				"name: \"incorrect break basic\"\npriority: 10000\nactivities: break; ", facade.createTaskFactory(),
				Collections.singletonList(new int[] {1,1,1}));
		Task basicBreakWrong = tasks.get(0);
		assertFalse(basicBreakWrong.isWellFormed());
		tasks = TaskParser.parseTasksFromString(
				"name: \"incorrect break less basic\"\npriority: 10000\nactivities:if is_solid(13,10,10) then work here; fi moveTo(12,10,10);  break; work here; ", facade.createTaskFactory(),
				null);
		Task breakWrong= tasks.get(0);
		assertFalse(breakWrong.isWellFormed());
		tasks = TaskParser.parseTasksFromString(
				"name: \"incorrect break with while \"\npriority: 10000\nactivities:while is_solid(13,10,10) do work here; done work here; break; work here; ", facade.createTaskFactory(),
				null);
		breakWrong= tasks.get(0);
		assertFalse(breakWrong.isWellFormed());
		//break placed outside of while in if then
		tasks = TaskParser.parseTasksFromString(
				"name: \"incorrect break if else\"\npriority: 10000"
				+ "\nactivities:if is_solid(13,10,10) then if is_solid(12,10,11) then moveTo(11,10,11); else break; fi  moveTo(12,10,10); fi work here; ", facade.createTaskFactory(),
				null);
		Task breakWrongIfElse= tasks.get(0);
		assertFalse(breakWrongIfElse.isWellFormed());
		
		tasks = TaskParser.parseTasksFromString(
				"name: \"incorrect break if else\"\npriority: 10000"
				+ "\nactivities:if is_solid(13,10,10) then if is_solid(12,10,11) then moveTo(11,10,11); else if is_passable(10,9,9) then break; else work here; fi  fi  moveTo(12,10,10); fi work here; ", facade.createTaskFactory(),
				null);
		breakWrongIfElse= tasks.get(0);
		assertFalse(breakWrongIfElse.isWellFormed());
		
		tasks = TaskParser.parseTasksFromString(
				"name: \"incorrect break if else with a while\"\npriority: 10000"
				+ "\nactivities:while is_solid(13,10,10) do if is_solid(12,10,11) then moveTo(11,10,11); fi done if is_passable(10,9,9) then break; else work here; fi  moveTo(12,10,10); work here; ", facade.createTaskFactory(),
				null);
		Task breakWrongIfElseWithWhile= tasks.get(0);
		assertFalse(breakWrongIfElseWithWhile.isWellFormed());
		
		tasks = TaskParser.parseTasksFromString(
				"name: \"incorrect break if else with a while\"\npriority: 10000"
				+ "\nactivities:while is_solid(13,10,10) do if is_solid(12,10,11) then moveTo(11,10,11); else break; fi done if is_passable(10,9,9) then break; else work here; fi  moveTo(12,10,10); work here; "
			, facade.createTaskFactory(),
				null);
		breakWrongIfElseWithWhile= tasks.get(0);
		assertFalse(breakWrongIfElseWithWhile.isWellFormed());
		
	}
	@Test
	public void testAssignVariables(){
		// tests correct use
		
		Facade facade = new Facade();
		
		List<Task> tasks = TaskParser.parseTasksFromString("name: \"basic use assign variable\"\npriority : 5 "
				+ "activities : w := workshop; moveTo boulder ; moveTo w;work here ;", facade.createTaskFactory(),
				null);
		Task assignVariableGood = tasks.get(0);
		assertTrue(assignVariableGood.isWellFormed());
		
		tasks = TaskParser.parseTasksFromString("name: \"no use of the assigned variable\"\npriority : 5 "
				+ "activities : w := workshop; moveTo boulder ; work here ;", facade.createTaskFactory(),
				null);
		assignVariableGood = tasks.get(0);
		assertTrue(assignVariableGood.isWellFormed());
		
		tasks = TaskParser.parseTasksFromString("name: \"multiple use\"\npriority : 5 "
				+ "activities : b := boulder ; moveTo workshop; moveTo b; if is_solid b then work here; fi", facade.createTaskFactory(),
				null);
		assignVariableGood = tasks.get(0);
		assertTrue(assignVariableGood.isWellFormed());
		
		//test unassigned variable
		
		tasks = TaskParser.parseTasksFromString("name: \"wrong use of assign variable\"\npriority : 5 "
				+ "activities : moveTo boulder ; moveTo w;work here ;", facade.createTaskFactory(),
				null);
		Task assignVariableWrong = tasks.get(0);
		assertFalse(assignVariableWrong.isWellFormed());
		
		tasks = TaskParser.parseTasksFromString("name: \"wrong use of assign variable\"\npriority : 5 "
				+ "activities : b := boulder ; moveTo boulder ; moveTo w;work here ;", facade.createTaskFactory(),
				null);
		assignVariableWrong = tasks.get(0);
		assertFalse(assignVariableWrong.isWellFormed());
		
		tasks = TaskParser.parseTasksFromString("name: \"variable is not always assigned\"\npriority : 5 "
				+ "activities : if is_solid(10,3,10) then b := boulder ; fi moveTo boulder ; moveTo b;work here ;", facade.createTaskFactory(),
				null);
		assignVariableWrong = tasks.get(0);
		assertFalse(assignVariableWrong.isWellFormed());
		
		tasks = TaskParser.parseTasksFromString("name: \"variable is not always assigned\"\npriority : 5 "
				+ "activities : w:= workshop; if is_solid(10,3,10) then b := boulder ; fi moveTo boulder ; moveTo b; moveTo w ;", facade.createTaskFactory(),
				null);
		assignVariableWrong = tasks.get(0);
		assertFalse(assignVariableWrong.isWellFormed());
		
		
		
		
		
	}

}

