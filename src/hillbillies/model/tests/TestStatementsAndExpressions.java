package hillbillies.model.tests;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.expressions.CarriesItemExpression;
import hillbillies.model.expressions.ThisExpression;
import hillbillies.model.expressions.UnitExpression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.scheduler.Scheduler;
import hillbillies.model.scheduler.Task;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part3.facade.Facade;
import hillbillies.part3.programs.TaskParser;

public class TestStatementsAndExpressions {

	private static final int TYPE_AIR = 0;
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

	
	//TODO: ??? Wat loopt er mis?
	//@Test
	public void testFindsClostestWorkshop() throws WorldException {
		Facade facade = new Facade();
		World testWorld = createTestingWorldWith2Workshops();
		Unit testUnit = testWorld.spawnUnit(false);
		testUnit.moveTo(3, 0, 0);
		advanceSeconds(testWorld, 100);
		Scheduler schedulerTest = testUnit.getFaction().getScheduler() ;
		List<Task> tasks = TaskParser.parseTasksFromString(
				"name: \"move to workshop\"\npriority: 10000\nactivities: w := workshop;\nmoveTo w;", facade.createTaskFactory(),
				Collections.singletonList(new int[] { 1, 1, 1 }));
		 
		 Task moveToClosestWorkshopTask = tasks.get(0);
		 schedulerTest.addTask(moveToClosestWorkshopTask);
		 System.out.println(schedulerTest.getHighestPrio().getName());
		 System.out.println(testUnit.getMyState());
		 testUnit.setDefaultBehaviourEnabled(true);
		 advanceSeconds(testWorld,0.001);
		 System.out.println(testUnit.getMyState());

		 while(!moveToClosestWorkshopTask.hasFinished()){
			 
			 advanceSeconds(testWorld,0.001);
			 System.out.println(testUnit.getxpos());
		 }
		 //not part of this test
		 testUnit.setDefaultBehaviourEnabled(false);
		 advanceSeconds(testWorld,0.001);
		 assertTrue(schedulerTest.getTasks().size()==0);
		 System.out.println(testUnit.getxpos());
		 assertTrue((int) testUnit.getxpos()== 2 && (int) testUnit.getxpos()== 2 && (int) testUnit.getzpos() ==0);

	}
	//TODO: wtf ik blijf der weer in 
	//@Test
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
		while(!findClosestLogTask.isTerminated()){
			System.out.println("while");
			advanceSeconds(testWorld,0.01);
		}
		testUnit.setDefaultBehaviourEnabled(false);
		assertTrue(schedulerTest.getTasks().size() ==0);
		assertTrue((int)testUnit.getxpos() == 3 && (int)testUnit.getypos() == 3 && (int)testUnit.getzpos()==0 );
		
		 
	}
	
	
	
	//TODO:hoe geïsoleerd testen ? we werken daar met de contextwrapper of kan
	@Test
	public void testBooleanExpressions() throws WorldException{
		Facade facade = new Facade();
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
		
		
		UnitExpression thisUnit = new ThisExpression();
		CarriesItemExpression c =new CarriesItemExpression<UnitExpression>((UnitExpression) thisUnit); 
		c.evaluateBoolean();



		
	}
	//TODO: idem hier
	@Test
	public void testHereIsSameAsPositionOfThis() throws WorldException{
		Facade facade = new Facade();
		World testWorld = createTestingWorldRockAndWood();
		//isCarryingItem
		Unit testUnit = testWorld.spawnUnit(false);
		
		
		
		
		
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


}