package hillbillies.model.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import hillbillies.model.exceptions.WorldException;
import hillbillies.model.exceptions.WrongVariableException;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.TrueExpression;
import hillbillies.model.hillbilliesobject.CurrentState;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.IfThenElseStatement;
import hillbillies.model.statement.MultipleStatement;
import hillbillies.model.statement.NullStatement;
import hillbillies.model.statement.Statement;
import hillbillies.model.task.ContextWrapper;
import hillbillies.model.task.Task;
import hillbillies.model.task.TaskFactory;
import hillbillies.model.util.Position;
import hillbillies.model.world.World;
import hillbillies.part2.listener.TerrainChangeListener;

public class TaskTest {
	
	TerrainChangeListener changeListener = new TerrainChangeListener(){
		@Override
		public void notifyTerrainChanged(int x, int y, int z) {
		}
	};
	Unit test;
	static int[][][] smallWorld = new int[3][3][3];
	static int[][][] bigWorld = new int[15][15][15];
	TaskFactory taskFactory = new TaskFactory();

	@Before
	public void setUp() throws Exception {
		 for (int x=0;x<3;x++){
			 for (int y=0;y<3;y++){
				 smallWorld[x][y][0] = 1;
				 smallWorld[x][y][1] = 0;
				 smallWorld[x][y][2] = 0;
			 }
		 }
		 for (int x=0;x<15;x++){
			 for (int y=0;y<15;y++){
				 for (int z=0;z<15;z++){
					 if (2*z-y-x<0){
						 bigWorld[x][y][z] = 1;
					 }
					 else {
						 bigWorld[x][y][z] = 0;
					 }
				 }
			 }
		 }
		test = new Unit(0,0,1,"Adri'aan en W\"out", 50, 50, 50, 50, false);

	}

	@Test
	public void testBasicMoveTask() throws WorldException{
		World world = new World(smallWorld,changeListener);
		world.addUnit(test);
		test.setDefaultBehaviourEnabled(true);
		List<int[]> validMovePos = new ArrayList<>();
		validMovePos.add(new int[]{2,2,1});
		
		// Some valid tasks first
		// 1) MoveTask
		Task task = taskFactory.createTasks("abc", 10, taskFactory.createMoveTo(taskFactory.createSelectedPosition(null), null), validMovePos).get(0);
		test.getFaction().getScheduler().addTask(task);
		
		test.advanceTime(0.01);
		test.setDefaultBehaviourEnabled(false);
		
		assert (test.isMoving());
		this.advanceSeconds(test, 10);
		assert (!test.isMoving());
	}
	
	
	
	
	/**
	 * Tests generally whether a unit fully executes tasks when executeDefaultBehaviour is enabled. 
	 * @throws WorldException 
	 */
	@Test
	public void testBasicAttackTask() throws WorldException{
		World world = new World(smallWorld,changeListener);
		Unit enemy = world.spawnUnit(false);
		world.addUnit(test);
		test.setDefaultBehaviourEnabled(true);
		enemy.moveTo(2, 2, 1);
		this.advanceSeconds(enemy, 10);
		// 2) AttackEnemyTask
		Expression<?> enemyExpression = taskFactory.createEnemy(null);
		Statement enemyStatement = taskFactory.createAssignment("enemy", enemyExpression, null);
		Expression<?> readEnemy = taskFactory.createReadVariable("enemy", null);
		Expression<?> enemyPos = taskFactory.createPositionOf(readEnemy, null);
		Expression<?> nextToExpression = taskFactory.createNextToPosition(enemyPos, null);
		Statement moveStatement = taskFactory.createMoveTo(nextToExpression, null);
		Statement attackStatement = taskFactory.createAttack(readEnemy, null);
		List<Statement> statements = new ArrayList<>();
		statements.add(enemyStatement);
		statements.add(moveStatement);
		statements.add(attackStatement);
		Statement multipleStatement = taskFactory.createSequence(statements, null);
		Task task = taskFactory.createTasks("abc", 10, multipleStatement, null).get(0);
		test.getFaction().getScheduler().addTask(task);
		test.setDefaultBehaviourEnabled(true);
		test.advanceTime(0.01);
		assert (test.getMyTask()==task);
		while (test.isMoving()){
			test.advanceTime(0.01);
		}
		test.advanceTime(0.01);
		assert (test.getMyState()==CurrentState.ATTACKING);
		test.setDefaultBehaviourEnabled(false);
		this.advanceSeconds(test, 10);
		assert (test.getMyState()!=CurrentState.ATTACKING);
		//TODO MEER VOORBEELDEN
	}
	
	@Test
	public void testBasicFollowTask() throws WorldException{
		World world = new World(bigWorld,changeListener);
		Unit other = world.spawnUnit(true);
		world.addUnit(test);
		test.setDefaultBehaviourEnabled(true);
		other.moveTo(14, 14, 14);
		while (other.getMyState()==CurrentState.MOVING){
			other.advanceTime(0.1);
		}
		Expression<?> anyExpression = taskFactory.createAny(null);
		Statement follow = taskFactory.createFollow(anyExpression, null);
		Task task = taskFactory.createTasks("abc", 10, follow, null).get(0);
		test.getFaction().getScheduler().addTask(task);
		test.advanceTime(0.1);
		assert (test.getTargetUnit()==other);
		while (test.isMoving()){
			assert (test.getMyState()==CurrentState.FOLLOWING);
			other.advanceTime(0.1);
			test.advanceTime(0.1);
		}
		Position testPos = new Position(test.getxpos(),test.getypos(),test.getzpos(),test.getWorld());
		Position otherPos = new Position(other.getxpos(),other.getypos(),other.getzpos(),other.getWorld());
		assert (testPos.isAdjacent(otherPos));
	}
	
	@Test
	public void testBasicWorkTask() throws WorldException{
		World world = new World(smallWorld,changeListener);
		world.addUnit(test);
		test.setDefaultBehaviourEnabled(true);
		List<int[]> validWorkPos = new ArrayList<>();
		validWorkPos.add(new int[]{0,0,0});
		Statement workStatement = taskFactory.createWork(taskFactory.createSelectedPosition(null), null);
		Task task = taskFactory.createTasks("abc", 10, workStatement, validWorkPos).get(0);
		test.getFaction().getScheduler().addTask(task);
		test.advanceTime(0.1);
		assert (!world.getCube(0, 0, 0).isPassable());
		assert (test.getMyState()==CurrentState.WORKING);
		while (test.getMyState()==CurrentState.WORKING){
			test.advanceTime(0.1);
		}
		assert (world.getCube(0,0,0).isPassable());
	}
	
	@Test
	public void testAssignedUnit() throws WorldException{
		World world = new World(smallWorld,changeListener);
		world.addUnit(test);
		test.setDefaultBehaviourEnabled(true);
		Unit dummy1 = world.spawnUnit(true);
		Unit dummy2 = world.spawnUnit(true);
		Unit dummy3 = world.spawnUnit(true);
		Unit dummy4 = world.spawnUnit(true);
		Unit other = world.spawnUnit(true);
		Task task1 = taskFactory.createTasks("abc", 10, new NullStatement(), null).get(0);
		Task task2 = taskFactory.createTasks("abc", 5, new NullStatement(), null).get(0);
		task1.setAssignedUnit(test);
		assert (test.getFaction()==other.getFaction());
		test.getFaction().getScheduler().addTask(task1);
		test.getFaction().getScheduler().addTask(task2);
		assert (test.getFaction().getScheduler().allTasks().contains(task1));
		assert (test.getFaction().getScheduler().allTasks().contains(task2));
		other.advanceTime(0.01);
		other.advanceTime(0.01);
		assert (!test.getFaction().getScheduler().allTasks().contains(task2));
		other.advanceTime(0.01);
		other.advanceTime(0.01);
		assert (test.getFaction().getScheduler().allTasks().contains(task1));
		test.advanceTime(0.01);
		test.advanceTime(0.01);
		assert (!test.getFaction().getScheduler().allTasks().contains(task1));
	}
	
	@Test
	public void testNumberOfExecutedStatements() throws WorldException{
		World world = new World(smallWorld,changeListener);
		world.addUnit(test);
		test.setDefaultBehaviourEnabled(true);
		ArrayList<Statement> nullstatements = new ArrayList<>();
		ArrayList<Statement> statements = new ArrayList<>();
		for (int i = 0;i<50;i++){
			nullstatements.add(new NullStatement());
		}
		for (int i = 0; i<5;i++){
			statements.add(new MultipleStatement(nullstatements.subList(10*i, 10*i+10)));
		}
		Statement ifThenElseStatement = new IfThenElseStatement<TrueExpression>(new TrueExpression(),statements.get(0),new NullStatement());
		statements.remove(0);
		statements.add(0, ifThenElseStatement);
		Statement statement = new MultipleStatement(statements);
		Task task = new Task("abc", 0, statement, null);
		test.getFaction().getScheduler().addTask(task);
		Random rand = new Random();
		int index = 0;
		while (index<50){
			int i = rand.nextInt(9)+1;
			if (index+i>50){
				i = 50-index;
				double dt = i/1000.0;
				test.advanceTime(dt);
				break;
			}
			double dt = i/1000.0;
			test.advanceTime(dt);
			
			for (int p = 0; p<50;p++){
				if (p<index+i){
					assert (nullstatements.get(p).hasBeenExecuted());
				} else {
					assert (!nullstatements.get(p).hasBeenExecuted());
				}
			}
			index+=i;
			int q = (index)/10;
			for (int s = 0; s<5;s++){
				if (s<q){
					assert (statements.get(s).hasBeenExecuted());
				}
				else {
					assert (!statements.get(s).hasBeenExecuted());
				}
			}
		}
		assert (statement.hasBeenExecuted());
		assert (!task.isTerminated());
		test.advanceTime(0.001);
		assert (!statement.hasBeenExecuted());
		assert (task.isTerminated()); 
	}
	
	@Test
	public void testBooleanExpressionTypeSafety() throws WorldException{
		//case 1 : BooleanExpression taking BooleanExpression arguments (e.g. AndExpression
		Expression<?> left = taskFactory.createTrue(null);
		Expression<?> right = taskFactory.createFalse(null);
		Expression<?> wrong = taskFactory.createEnemy(null);
		taskFactory.createAnd(left, right, null);
		try{
			taskFactory.createAnd(left, wrong, null);
			fail();
		} catch (ClassCastException c){
			//Good
		}
		
		//case 2 : BooleanExpression taking PositionExpression arguments (e.g. IsPassableExpression)
		wrong = taskFactory.createAny(null);
		Expression<?> good = taskFactory.createWorkshopPosition(null);
		taskFactory.createIsPassable(good, null);
		try{
			taskFactory.createIsPassable(wrong, null);
			fail();
		} catch (ClassCastException c){
			//Good
		}
		
		//case 3 : BooleanExpression taking UnitExpression arguments (e.g. IsEnemyExpression)
		good = taskFactory.createFriend(null);
		wrong = taskFactory.createTrue(null);
		taskFactory.createIsEnemy(good, null);
		try{
			taskFactory.createIsEnemy(wrong,null);
			fail();
		} catch (ClassCastException c){
			//Good
		}
	}
	
	@Test
	public void testPositionExpressionTypeSafety(){
		// case 1: PositionOfExpression (taking UnitExpression arguments)
		Expression<?> good = taskFactory.createAny(null);
		Expression<?> wrong = taskFactory.createHerePosition(null);
		taskFactory.createPositionOf(good, null);
		try{
			taskFactory.createPositionOf(wrong,null);
			fail();
		} catch (ClassCastException c){
			//Good
		}
		
		// case 2: NextToExpression (taking PositionExpression arguments)
		wrong = taskFactory.createTrue(null);
		good = taskFactory.createSelectedPosition(null);
		taskFactory.createNextToPosition(good, null);
		try{
			taskFactory.createNextToPosition(wrong,null);
			fail();
		} catch (ClassCastException c){
			//Good
		}
	}
	
	@Test
	public void testReadVariableExpressionAlwaysWorks(){
		Expression<?> readVariable = taskFactory.createReadVariable("abc", null);
		taskFactory.createNot(readVariable, null);
		taskFactory.createIsSolid(readVariable,null);
		taskFactory.createIsAlive(readVariable, null);
		taskFactory.createPositionOf(readVariable, null);
		taskFactory.createNextToPosition(readVariable, null);
	}
	
	@Test
	public void testFailedExpressionInterruptsTask() throws WorldException{
		World world = new World(smallWorld,changeListener);
		world.addUnit(test);
		test.setDefaultBehaviourEnabled(true);
		Expression<?> workshop = taskFactory.createWorkshopPosition(null);
		Expression<?> any = taskFactory.createAny(null);
		Statement moveWorkShop = taskFactory.createMoveTo(workshop, null);
		Statement attackAny = taskFactory.createAttack(any, null);
		Task moveTask = taskFactory.createTasks("abc", 10, moveWorkShop, null).get(0);
		Task attackTask = taskFactory.createTasks("abc", 10, attackAny, null).get(0);
		test.getFaction().getScheduler().addTask(moveTask);
		test.getFaction().getScheduler().addTask(attackTask);
		this.advanceSeconds(test, 1);
		assert (!moveTask.isTerminated());
		assert (!attackTask.isTerminated());
		assert (moveTask.getPriority()==Integer.MIN_VALUE);
		assert (attackTask.getPriority()==Integer.MIN_VALUE);
		
	}
	
	
	/**
	 * Advances time for unit during 'time' seconds, in steps of 0.01 second.
	 * @param unit
	 * 			The unit which advancetime has to be called on.
	 * @param time
	 * 			The time to be advanced.
	 * @throws WorldException 
	 */
	public void advanceSeconds(Unit unit, double time) throws WorldException{
		int steps = (int) (time/0.01);
		for (int i = 0; i<steps;i++){
			unit.advanceTime(0.01);
		}
	}
	
	@Test
	public void testBreakInWhile() throws WorldException, WrongVariableException{
		Expression<?> t = taskFactory.createTrue(null);
		Expression<?> f = taskFactory.createFalse(null);
		Statement assign = taskFactory.createAssignment("b", f, null);
		Statement assign2 = taskFactory.createAssignment("b", t, null);
		Expression<?> r = taskFactory.createReadVariable("b", null);
		Statement b = taskFactory.createBreak(null);
		Statement body = taskFactory.createIf(r, assign, b, null);
		Statement w = taskFactory.createWhile(t, body, null);
		ArrayList<Statement> list = new ArrayList<>();
		list.add(assign2);
		list.add(w);
		Statement stat = taskFactory.createSequence(list, null);
		ContextWrapper c = new ContextWrapper();
		
		// Boolean variable has been assigned to true.
		stat.executeNext(c);
		assert (assign2.hasBeenExecuted());
		
		// Variable changed to false.
		stat.executeNext(c);
		assert (assign.hasBeenExecuted());
		
		// BreakStatement has been called, statement ended.
		stat.executeNext(c);
		assert (stat.hasBeenExecuted());
	}

}
