package hillbillies.model.tests;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.exceptions.WorldException;
import hillbillies.model.exceptions.WrongVariableException;
import hillbillies.model.expressions.BooleanExpression;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.PositionExpression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.task.ContextWrapper;
import hillbillies.model.task.Task;
import hillbillies.model.task.TaskFactory;
import hillbillies.model.world.Scheduler;
import hillbillies.model.world.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part3.facade.Facade;
import hillbillies.part3.programs.TaskParser;

public class TestStatementsAndExpressions {

	private static final int TYPE_ROCK = 1;
	private static final int TYPE_TREE = 2;
	private static final int TYPE_WORKSHOP = 3;
	
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
	public void testFindsClostestWorkshop() throws WorldException {
		Facade facade = new Facade();
		World testWorld = createTestingWorldWith2Workshops();
		Unit testUnit = testWorld.spawnUnit(false);
		testUnit.moveTo(3, 0, 0);
		advanceSeconds(testWorld, 100);
		Scheduler schedulerTest = testUnit.getFaction().getScheduler() ;
		List<Task> tasks = TaskParser.parseTasksFromString(
				"name: \"move to workshop\"\npriority: 10000\nactivities: moveTo workshop;", facade.createTaskFactory(),
				Collections.singletonList(new int[] { 1, 1, 1 })); 
		 Task moveToClosestWorkshopTask = tasks.get(0);
		 schedulerTest.addTask(moveToClosestWorkshopTask);
		 testUnit.setDefaultBehaviourEnabled(true);
		 testWorld.advanceTime(0.001);
		 assertTrue(testUnit.getMyTask() == moveToClosestWorkshopTask);
		 testUnit.setDefaultBehaviourEnabled(false);
		 advanceSeconds(testWorld,10);
		 //the position of the closest workshop.
		 assertTrue((int) testUnit.getxpos()== 2 && (int) testUnit.getxpos()== 2 && (int) testUnit.getzpos() ==0);
		 

		 

	}
	@Test
	public void findClosestLog() throws WorldException {
		Facade facade = new Facade();
		World testWorld = createTestingWorldRockAndWood();
		Unit testUnit = testWorld.spawnUnit(false);
		testUnit.moveTo(2, 0, 0);
		advanceSeconds(testWorld, 100);
		testUnit.workAt(3, 0, 0);
		advanceSeconds(testWorld, 100);
		testUnit.moveTo(1, 3, 0);
		advanceSeconds(testWorld, 100);
		testUnit.workAt(0, 3, 0);
		advanceSeconds(testWorld, 100);
		testUnit.moveTo(2, 3, 0);
		advanceSeconds(testWorld, 100);
		Scheduler schedulerTest = testUnit.getFaction().getScheduler() ;
		List<Task> tasks = TaskParser.parseTasksFromString(
				"name: \"move to workshop\"\npriority: 10000\nactivities: moveTo log;", facade.createTaskFactory(),
				Collections.singletonList(new int[] { 1, 1, 1 }));
		Task findClosestLogTask = tasks.get(0);
		
		schedulerTest.addTask(findClosestLogTask);
		testUnit.setDefaultBehaviourEnabled(true);
		testWorld.advanceTime(0.001);
		testUnit.setDefaultBehaviourEnabled(false);
		advanceSeconds(testWorld,100);
		//found the closest log.
		assertTrue((int)testUnit.getxpos() == 0 && (int)testUnit.getypos() == 3 && (int)testUnit.getzpos()==0 );
		
		 
	}
	
	
	
	@Test
	public void testBooleanExpressions() throws WorldException, WrongVariableException{
		World testWorld = createTestingWorldRockAndWood();
		//isCarryingItem
		Unit testUnit = testWorld.spawnUnit(false);
		testUnit.moveTo(2, 0, 0);
		advanceSeconds(testWorld, 100);
		testUnit.workAt(3, 0, 0);
		advanceSeconds(testWorld, 100);
		testUnit.workAt(3, 0, 0);
		advanceSeconds(testWorld, 100);
		assertTrue(testUnit.isCarryingLog());
		TaskFactory taskFactory = new TaskFactory();
		Expression<?> unitExpression = taskFactory.createThis(null);
		BooleanExpression carriesItemExpression = (BooleanExpression) taskFactory.createCarriesItem(unitExpression, null);
		ContextWrapper context = new ContextWrapper();
		context.setExecutingUnit(testUnit);
		context.setThisWorld(testWorld);
		assertTrue(carriesItemExpression.evaluateBoolean(context));
		//isPassable
		PositionExpression position1 = (PositionExpression) taskFactory.createLiteralPosition(2, 2, 2, null);
		BooleanExpression isPassable = (BooleanExpression) taskFactory.createIsPassable(position1,null);
		assertTrue(isPassable.evaluateBoolean(context));
		PositionExpression position2 = (PositionExpression) taskFactory.createLiteralPosition(0,1,0, null);
		BooleanExpression isNotPassable = (BooleanExpression) taskFactory.createIsPassable(position2, null);
		assertFalse(isNotPassable.evaluateBoolean(context));
		
		//Test true and False Expression
		BooleanExpression trueExpression = (BooleanExpression) taskFactory.createTrue(null);
		assertTrue(trueExpression.evaluateBoolean(context));
		BooleanExpression falseExpression = (BooleanExpression) taskFactory.createFalse(null);
		assertFalse(falseExpression.evaluateBoolean(context));
		
		
		
		//test not expression
		BooleanExpression notTrueExpression = (BooleanExpression) taskFactory.createNot(trueExpression, null);
		BooleanExpression notFalseExpression =  (BooleanExpression) taskFactory.createNot(falseExpression, null);
		assertFalse(notTrueExpression.evaluateBoolean(context));
		assertTrue(notFalseExpression.evaluateBoolean(context));
		
		//test and and or
		BooleanExpression andTrueTrue = (BooleanExpression) taskFactory.createAnd(trueExpression, trueExpression, null);
		BooleanExpression andFalseTrue = (BooleanExpression) taskFactory.createAnd(falseExpression, trueExpression, null);
		BooleanExpression andTrueFalse = (BooleanExpression) taskFactory.createAnd(falseExpression, trueExpression, null);
		BooleanExpression andFalseFalse = (BooleanExpression) taskFactory.createAnd(falseExpression, falseExpression, null);
		assertTrue(andTrueTrue.evaluateBoolean(context));
		assertFalse(andFalseTrue.evaluateBoolean(context));
		assertFalse(andTrueFalse.evaluateBoolean(context));
		assertFalse(andFalseFalse.evaluateBoolean(context));

		
		BooleanExpression orTrueTrue = (BooleanExpression) taskFactory.createOr(trueExpression, trueExpression, null);
		BooleanExpression orFalseTrue = (BooleanExpression) taskFactory.createOr(falseExpression, trueExpression, null);
		BooleanExpression orTrueFalse = (BooleanExpression) taskFactory.createOr(falseExpression, trueExpression, null);
		BooleanExpression orFalseFalse = (BooleanExpression) taskFactory.createOr(falseExpression, falseExpression, null);
		
		assertTrue(orTrueTrue.evaluateBoolean(context));
		assertTrue(orFalseTrue.evaluateBoolean(context));
		assertTrue(orTrueFalse.evaluateBoolean(context));
		assertFalse(orFalseFalse.evaluateBoolean(context));

		
	}
	//TODO: idem hier
	@Test
	public void testHereIsSameAsPositionOfThis() throws WorldException, WrongVariableException{		
		World testWorld = createTestingWorldRockAndWood();
		Unit testUnit = testWorld.spawnUnit(false);
		ContextWrapper context = new ContextWrapper();
		context.setExecutingUnit(testUnit);
		context.setThisWorld(testWorld);
		TaskFactory taskFactory = new TaskFactory();
		Expression<?> unitExpression = taskFactory.createThis(null);
		PositionExpression hereExpression = (PositionExpression) taskFactory.createHerePosition(null);
		PositionExpression positionOfThisUnit = (PositionExpression) taskFactory.createPositionOf(unitExpression, null);
		assertTrue(hereExpression.evaluatePosition(context).equals(positionOfThisUnit.evaluatePosition(context)));
		
		
		
		
	}
	
	/**
	 * Advances time for the given world during 'time' seconds, in steps of 0.01 second.
	 * @param world
	 * 			The unit which advancetime has to be called on.
	 * @param time
	 * 			The time to be advanced.
	 * @throws WorldException 
	 */
	public void advanceSeconds(World world, double time) throws WorldException{
		int steps = (int) (time/0.01);
		for (int i = 0; i<steps;i++){
			world.advanceTime(0.01);
		}
	}
	/**
	 * Creates a 4*4*4 world looking like this:
	 * Level zero looks like: 
	 * 		[0030]
	 * 		[0000]
	 * 		[0000]
	 * 		[0100]					
	 * Level one looks like;
	 * 		[0000]
	 * 		[0000]
	 * 		[0000]
	 * 		[0100]
	 * Level two looks like
	 * 		[0000]
	 * 		[0000]
	 * 		[0000]
	 * 		[0100]
	 * Level three looks like
	 * 		[0000]
	 * 		[0000]
	 * 		[0300]
	 * 		[0100]	
	 */
	private World createTestingWorldWith2Workshops() throws WorldException {
		int[][][] w  = new int[4][4][4];
		
		//create a world
			
			
		for (int z=0;z<4;z++){
			for (int x=0;x<4;x++){
				for (int y=0; y<4; y++ ){
					if (x==0 && y==1){
						w[x][y][z] = TYPE_ROCK;
					}
				}
			}
		}
		
			
		
		w[2][2][0] = TYPE_WORKSHOP;
		w[0][1][3] = TYPE_WORKSHOP;
		
	
	
		return  new World(w,new DefaultTerrainChangeListener());
	}
	
	/**
	 * Creates a 4*4*4 world looking like this:
	 * Level zero looks like: 
	 * 		[2000]
	 * 		[0000]
	 * 		[0000]
	 * 		[0102]					
	 * Level one looks like;
	 * 		[0000]
	 * 		[0000]
	 * 		[0000]
	 * 		[0100]
	 * Level two looks like
	 * 		[0000]
	 * 		[0000]
	 * 		[0000]
	 * 		[0100]
	 * Level three looks like
	 * 		[0000]
	 * 		[0000]
	 * 		[0200]
	 * 		[0100]	
	 */
	private World createTestingWorldRockAndWood() throws WorldException {
		int[][][] w  = new int[4][4][4];
		
		//create a world
			
			
		for (int z=0;z<4;z++){
			for (int x=0;x<4;x++){
				for (int y=0; y<4; y++ ){
					if (x==0 && y==1){
						w[x][y][z] = TYPE_ROCK;
					}
				}
			}
		}
		
			
		
		w[3][0][0] = TYPE_TREE;
		w[0][1][3] = TYPE_TREE;
		w[0][3][0] = TYPE_TREE;
		
	
	
		return  new World(w,new DefaultTerrainChangeListener());
	}
	
}


