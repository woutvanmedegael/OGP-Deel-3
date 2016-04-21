package hillbillies.model.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.event.TreeSelectionEvent;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.NbCompare;
import hillbillies.model.Position;
import hillbillies.model.world.Cube;
import hillbillies.model.world.Faction;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.tests.util.PositionAsserts;
import hillbilliesobject.CurrentState;
import hillbilliesobject.unit.IllegalNameException;
import hillbilliesobject.unit.Unit;
import hillbilliesobject.unit.UnitException;
/**
 * Different tests on unit.
 * @value
 * @author Wout Van Medegael & Adriaan Van Gerven
 */
public class UnitTest {
	
	
	ChangeListener changeListener = new ChangeListener();
	Unit test;
	static int[][][] smallWorld = new int[3][3][3];
	static int[][][] bigWorld = new int[15][15][15];
	
	/**
	 * Object to compare floating-point numbers.
	 */
	NbCompare comp = new NbCompare();
	
	private class ChangeListener implements TerrainChangeListener{

		@Override
		public void notifyTerrainChanged(int x, int y, int z) {
			
		}
		
	}

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
	
	/**
	 * Tests whether all restrictions of the name property work. A unit created with a valid name should return that name upon checking.
	 * A unit with an invalid name should throw an IllegalNameException. 
	 * @throws UnitException
	 * 			If the creation of a unit throws an exception (not IllegalNameException in last 2 creations).
	 */
	@Test
	public void testName() throws UnitException {
		assert(test.getName()== "Adri'aan en W\"out");
		test.setName("Ad");
		assert(test.getName() == "Ad");
		try{
			test.setName("Adri'aan @en W\"out");
			fail("IllegalNameException isn't thrown");
		} catch (IllegalNameException e){
			  //Great
		}
		try{
			test.setName("adri'aan en W\"out");
			fail("IllegalNameException isn't thrown");
		} catch (IllegalNameException e){
			  // Great
		}
		try{
			test.setName("A");
			fail("IllegalNameException isn't thrown");
		} catch (IllegalNameException e){
			  // Great
		}
	}
	
	/**
	 * Tests whether valid coordinates render no problems.
	 * A unit created with valid coordinates should return the middle of the cube the unit is created on.
	 * @throws WorldException 
	 */
	@Test
	public void testValidCoordinates() throws WorldException{
		World world = new World(smallWorld,changeListener);
		test.setWorld(world);
		PositionAsserts.assertDoublePositionEquals(0.5, 0.5, 1.5, new double[] 
				{test.getxpos(),test.getypos(),test.getzpos()});
		test.moveTo(2, 2, 1);
		this.advanceSeconds(test, 20);
		PositionAsserts.assertDoublePositionEquals(2.5, 2.5, 1.5, new double[] 
				{test.getxpos(),test.getypos(),test.getzpos()});
	}
	
	/**
	 * Tests whether invalid coordinates throw UnitExceptions, as they should.
	 * @throws WorldException
	 */
	@Test
	public void testInvalidCoordinates() throws WorldException{
		World smallworld = new World(smallWorld,changeListener);
		test = new Unit(5,0,0,"Adri'aan en W\"out", 50, 50, 50, 50, false);
		try{
			test.setWorld(smallworld);
			fail("5 isn't a valid coordinate in smallworld");
		} catch (UnitException e){
			//Good
		}
		try{
			test = new Unit(-5,0,0,"Adri'aan en W\"out", 50, 50, 50, 50, false);
			fail("-5 is not a valid coordinate!");
		} catch (UnitException e){
			//Good
		}
		
	}
	
	/**
	 * Tests whether all restrictions on the properties of a unit work.
	 * As these were to be programmed total, an exception shouldn't be thrown, and unit should always have valid properties.
	 * @throws UnitException
	 */
	@Test
	public void testProperties() throws UnitException{
		Unit prop = new Unit(0,13.293935,49.999999,"Adri'aan en W\"out", 25, 110, -10, 50, false);
		assert (prop.getAgility()==25);
		assert (prop.getToughness()==50);
		assert (prop.getStrength()==100);
		assert (prop.getWeight()==62);
		prop.setAgility(120);
		prop.setStrength(400);
		prop.setToughness(-30);
		assert (prop.getAgility()==120);
		assert (prop.getStrength()==200);
		assert (prop.getToughness()==30);
		assert (prop.getWeight()==160);
		prop.setWeight(100);
		assert (prop.getWeight()==160);
		prop.setWeight(180);
		assert (prop.getWeight()==180);
		prop.setToughness(0);
		assert (prop.getToughness()==1);
		prop.setToughness(1);
		assert (prop.getToughness()==1);
		prop.setToughness(200);
		assert (prop.getToughness()==200);
	}
	
	/**
	 * Tests whether the advance time method returns exceptions if invalid arguments are given.
	 * @throws WorldException 
	 */
	@Test
	public void testAdvanceTime() throws WorldException{
		test.advanceTime(0.1);
		try{
			test.advanceTime(-0.1);
			fail();
		} catch (UnitException e){
			  //Great
		}
		try{
			test.advanceTime(0.3);
			fail();
		} catch (UnitException e){
			  //Great
		}
		try{
			test.advanceTime(0);
			fail();
		} catch (UnitException e){
			  //Great
		}
	}

	
	/**
	 * Tests whether resting increases the hitpoints of a unit.
	 * @throws WorldException 
	 */
	@Test
	public void testRestingIncreasesHP() throws WorldException{
		World world = new World(smallWorld,changeListener);
		test.setWorld(world);
		test.startResting();
		assert (test.getCurrentHP()==test.getMaxHP());
		assert (test.getMyState()==CurrentState.RESTING);
		this.advanceSeconds(test, 0.01);
		assert (test.getCurrentHP()==test.getMaxHP());
		assert (test.getMyState()!=CurrentState.RESTING);
		Unit test2 = new Unit(1,1,1,"Other", 50, 50, 50, 50, false);
		test2.setWorld(world);
		while (test.getMaxHP()==test.getCurrentHP()){
			test2.startAttacking(test);
			this.advanceSeconds(test, 2);
			this.advanceSeconds(test2, 2);
			test2.moveTo(1, 0, 1);
			this.advanceSeconds(test2, 2);
		}
		assert (test.getMaxHP()!=test.getCurrentHP());
		test.startResting();
		this.advanceSeconds(test, 20);
		assert (test.getCurrentHP()==test.getMaxHP());
		
	}
	
	
	/**
	 * Tests whether resting increases the stamina points of a unit.
	 * @throws WorldException
	 */
	@Test
	public void testRestingIncreasesSP() throws WorldException{
		World world = new World(smallWorld,changeListener);
		test.setWorld(world);
		test.startResting();
		assert (test.getCurrentSP()==test.getMaxSP());
		assert (test.getMyState()==CurrentState.RESTING);
		this.advanceSeconds(test, 0.01);
		assert (test.getCurrentSP()==test.getMaxSP());
		assert (test.getMyState()!=CurrentState.RESTING);
		test.moveTo(2, 2, 1);
		test.setToggledSprint(true);
		this.advanceSeconds(test, 10);
		assert (test.getMaxSP()!=test.getCurrentSP());
		test.startResting();
		this.advanceSeconds(test, 20);
		assert (test.getCurrentSP()==test.getMaxSP());
	}
	
	
	/**
	 * Tests whether the correct number of hitpoints and stamina points are increased by resting.
	 * @throws WorldException
	 */
	@Test
	public void testRestingIncreasesCorrectNumbers() throws WorldException{
		World world = new World(smallWorld,changeListener);
		test.setWorld(world);
		decreaseHPAndSP(world);
		assert (test.getCurrentHP()!=test.getMaxHP() && test.getCurrentSP()!=test.getMaxSP());
		test.startResting();
		while (test.getCurrentHP()!=test.getMaxHP()){
			int prevHP = test.getCurrentHP();
			this.advanceSeconds(test, (double)40/test.getToughness()+0.01);
			assert (test.getCurrentHP()==prevHP+1);
		}
		while (test.getCurrentSP()!=test.getMaxSP()){
			int prevSP = test.getCurrentSP();
			this.advanceSeconds(test, (double)20/test.getToughness()+0.01);
			assert (test.getCurrentSP()==prevSP+1);
		}
	}
	
	/**
	 * Tests whether a unit automatically starts resting after 3 minutes of gametime.
	 * @throws WorldException
	 */
	@Test
	public void testRestingAfter3Minutes() throws WorldException{
		World world = new World(smallWorld,changeListener);
		test.setWorld(world);
		decreaseHPAndSP(world);
		assert (!test.isResting());
		this.advanceSeconds(test, 180-12+0.1);
		assert (test.isResting());
	}

	/**
	 * Help function to decrease HP and SP of a unit.
	 * @param world
	 * @throws UnitException
	 * @throws WorldException
	 */
	private void decreaseHPAndSP(World world) throws UnitException, WorldException {
		test.setFaction(new Faction());
		Unit test2 = new Unit(1,1,1,"Other", 50, 50, 50, 50, false);
		test2.setWorld(world);
		test2.setFaction(new Faction());
		while (test.getMaxHP()==test.getCurrentHP()){
			test2.startAttacking(test);
			this.advanceSeconds(test, 2);
			this.advanceSeconds(test2, 2);
			test2.moveTo(1, 0, 1);
			this.advanceSeconds(test2, 2);
		}
		test.moveTo(2, 2, 1);
		test.setToggledSprint(true);
		this.advanceSeconds(test, 10);
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
	
	/**
	 * Tests whether sprinting doubles the speed of a unit.
	 * @throws WorldException
	 */
	@Test
	public void testSprintingGoesFaster() throws WorldException{
		World world = new World(smallWorld,changeListener);
		test.setWorld(world);
		double timeNotSprinting = 0;
		double timeSprinting = 0;
		test.moveTo(2, 2, 1);
		while (test.getMyState()==CurrentState.MOVING){
			timeNotSprinting+=0.01;
			test.advanceTime(0.01);
			
		}
		test.moveTo(0,0,1);
		test.setToggledSprint(true);
		while (test.getMyState()==CurrentState.MOVING){
			timeSprinting+=0.01;
			test.advanceTime(0.01);
		}
		assert (timeNotSprinting>=timeSprinting*1.95);
		assert (timeNotSprinting<=timeSprinting*2.05);
	}
	
	/**
	 * Tests whether sprinting decreases the stamina points.
	 * @throws WorldException
	 */
	@Test
	public void testSprintingDecreasesSP() throws WorldException{
		World world = new World(bigWorld,changeListener);
		test.setWorld(world);
		test.moveTo(14, 14, 14);
		test.setToggledSprint(true);
		int prevSP = test.getCurrentSP();
		this.advanceSeconds(test, 0.05);
		while (test.getMyState()==CurrentState.MOVING && test.getToggledSprint()){
			this.advanceSeconds(test, 0.1);
			assert (test.getCurrentSP()==prevSP-1);
			prevSP = test.getCurrentSP();
		}
	}

	/**
	 * Tests whether a unit stops sprinting at 0 stamina points.
	 * @throws WorldException
	 */
	@Test
	public void testSprintingStops0SP() throws WorldException{
		World world = new World(bigWorld,changeListener);
		test.setWorld(world);
		test.moveTo(14, 14, 14);
		test.setToggledSprint(true);
		while (test.getCurrentSP()>1){
			this.advanceSeconds(test, 0.1);
		}
		assert (test.getToggledSprint());
		this.advanceSeconds(test, 0.15);
		assert (!test.getToggledSprint());
	}
	
	/**
	 * Tests whether only units of different factions can attack eachother.
	 * @throws WorldException
	 */
	@Test
	public void testFightingOnlyDifferentFactions() throws WorldException{
		World world = new World(smallWorld,changeListener);
		Unit test1 = world.spawnUnit(false);
		Unit test2 = world.spawnUnit(false);
		world.spawnUnit(false);
		world.spawnUnit(false);
		world.spawnUnit(false);
		Unit test3 = world.spawnUnit(false);
		assert (test1.getFaction()==test3.getFaction());
		test1.moveTo(0, 0, 1);
		test2.moveTo(1, 0, 1);
		test3.moveTo(0, 1, 1);
		this.advanceSeconds(test1, 5);
		this.advanceSeconds(test2, 5);
		this.advanceSeconds(test3, 5);
		test1.startAttacking(test3);
		assert (test1.getMyState()!=CurrentState.ATTACKING);
		test1.startAttacking(test2);
		assert (test1.getMyState()==CurrentState.ATTACKING);

	}
	
	/**
	 * Tests whether only units at adjacent cubes can attack eachother.
	 * @throws WorldException
	 */
	@Test
	public void testFightingOnlyInRange() throws WorldException{
		World world = new World(smallWorld,changeListener);
		Unit test1 = world.spawnUnit(false);
		Unit test2 = world.spawnUnit(false);
		test1.moveTo(0, 0, 1);
		test2.moveTo(2, 0, 1);
		this.advanceSeconds(test1, 5);
		this.advanceSeconds(test2, 5);
		test1.startAttacking(test2);
		assert (test1.getMyState()!=CurrentState.ATTACKING);
		test2.moveTo(1, 1, 1);
		this.advanceSeconds(test2, 3);
		test1.startAttacking(test2);
		assert (test1.getMyState()==CurrentState.ATTACKING);
	}
	
	/**
	 * Tests whether defending either grants experience points, or decreases the hitpoints.
	 * @throws WorldException
	 */
	@Test
	public void testDefendingDecreasesHPOrGrantsXP() throws WorldException{
		World world = new World(smallWorld,changeListener);
		test.setWorld(world);
		for (int i = 0;i<90;i++){
			Unit attacker = world.spawnUnit(false);
			attacker.moveTo(1, 1, 1);
			this.advanceSeconds(attacker, 5);
			assert (test.getCurrentHP()==test.getMaxHP());
			attacker.startAttacking(test);
			int prevPropDef = test.getToughness()+test.getAgility()+test.getStrength();
			int prevPropAtt = attacker.getToughness()+attacker.getAgility()+attacker.getStrength();
			this.advanceSeconds(attacker, 3);
			this.advanceSeconds(test, 0.1);
			int newPropDef = test.getToughness()+test.getAgility()+test.getStrength();
			int newPropAtt = attacker.getToughness()+attacker.getAgility()+attacker.getStrength();
			Boolean HPdecreased = (test.getMaxHP()!=test.getCurrentHP());
			if (HPdecreased){
				assert (newPropAtt==prevPropAtt+2);
				assert (newPropDef==prevPropDef);
			} else {
				assert (newPropAtt==prevPropAtt);
				assert (newPropDef==prevPropDef+2);
			}
			while (test.getCurrentHP()!=test.getMaxHP()){
				test.advanceTime(0.15);
			}
			test.moveTo(0, 0, 1);
			this.advanceSeconds(test, 4);
			
		}
	}
	
	/**
	 * Tests whether a unit that jumped away from an attack always lands on a valid position.
	 * @throws WorldException
	 */
	@Test
	public void testJumpingAwayOnlyValidTerrain() throws WorldException{
		smallWorld[1][0][1] = 1;
		smallWorld[0][1][1] = 1;
		smallWorld[2][1][1] = 1;
		World world = new World(smallWorld,changeListener);
		Unit defender = new Unit(1,1,1,"Abc",99,99,99,99,false);
		Unit attacker = new Unit(1,2,1,"Def",25,25,25,25,false);
		defender.setWorld(world);
		attacker.setWorld(world);
		for (int i = 0;i<100;i++){
			attacker.startAttacking(defender);
			this.advanceSeconds(attacker, 1.5);
			Position pos = new Position(defender.getxpos(), defender.getypos(), defender.getzpos(), world);
			assert (pos.getCube().isWalkable());
			defender.moveTo(1, 1, 1);
			this.advanceSeconds(defender, 2);
		}
	}
	
	/**
	 * Tests whether moving to an adjacent valid position renders no problems.
	 * @throws WorldException
	 */
	@Test
	public void testMoveToAdjacentToValidPos() throws WorldException{
		World world = new World(smallWorld,changeListener);
		test.setWorld(world);
		test.moveToAdjacent(0, 1, 0);
		this.advanceSeconds(test, 4);
		assert (comp.equals(test.getxpos(), 0.5));
		assert (comp.equals(test.getypos(), 1.5));
		assert (comp.equals(test.getzpos(), 1.5));
		test.moveToAdjacent(1, -1, 0);
		this.advanceSeconds(test, 4);
		assert (comp.equals(test.getxpos(), 1.5));
		assert (comp.equals(test.getypos(), 0.5));
		assert (comp.equals(test.getzpos(), 1.5));
	}
	
	/**
	 * Tests whether moving to adjacent invalid positions throws UnitExceptions.
	 * @throws WorldException
	 */
	@Test
	public void testMoveToAdjacentToInvalidPos() throws WorldException{
		World world = new World(bigWorld,changeListener);
		test.setWorld(world);
		try{
			test.moveToAdjacent(0, 1, -1);
			fail("Not a valid move");
		} catch (UnitException e){
			//Good
		}
		try{
			test.moveToAdjacent(0, -1, 0);
			fail("Not a valid move");
		} catch (UnitException e){
			//Good
		}
		test.moveTo(3, 3, 4);
		this.advanceSeconds(test, 30);
		try{
			test.moveToAdjacent(-1, -1, 1);
			fail("Not a valid move");
		} catch (UnitException e){
			//Good
		}
		
	}
	
	/**
	 * Tests whether moving to invalid positions throws UnitExceptions.
	 * @throws WorldException
	 */
	@Test
	public void testMoveToToInvalidPos() throws WorldException{
		World world = new World(smallWorld,changeListener);
		test.setWorld(world);
		try{
			test.moveTo(-1, 0, 0);
			fail("Position out of map");
		} catch (UnitException e){
			//Good
		}
		try{
			test.moveTo(1, 0, 0);
			fail("Cube solid");
		} catch (UnitException e){
			//Good
		}
	}
	
	
	/**
	 * Tests whether moving to valid positions renders no problems.
	 * @throws WorldException
	 */
	@Test
	public void testMoveToToValidPos() throws WorldException{
		World world = new World(bigWorld,changeListener);
		test.setWorld(world);
		Position prevTarget = new Position(test.getxpos(),test.getypos(),test.getzpos(),world);
		for (int i=0;i<100;i++){
			Position target = generateWalkablePos(world);
			test.moveTo(target.getCubexpos(), target.getCubeypos(), target.getCubezpos());
			double est = prevTarget.getEstimatedTimeTo(target);
			this.advanceSeconds(test, est*1.2);
			assert comp.equals(test.getxpos()-0.5, target.getxpos());
			assert comp.equals(test.getypos()-0.5, target.getypos());
			assert comp.equals(test.getzpos()-0.5, target.getzpos());
			prevTarget.setPositionAt(target);
		}
	}
	
	/**
	 * Tests whether moving to a valid, though unreachable position has no effect.
	 * @throws WorldException
	 */
	@Test
	public void testMoveToToUnreachablePos() throws WorldException{
		bigWorld[14][14][0] = 0;
		World world = new World(bigWorld,changeListener);
		test.setWorld(world);
		test.moveTo(14, 14, 0);
		assert !test.isMoving();
	}
	
	/**
	 * Tests whether working at invalid positions throws UnitExceptions.
	 * @throws WorldException
	 */
	@Test
	public void testWorkAtInValidPos() throws WorldException{
		World world  = new World(smallWorld,changeListener);
		test.setWorld(world);
		try{
			test.workAt(2, 2, 2);
			fail("Position out of reach");
		} catch (UnitException e){
			//Good
		}
		try{
			test.workAt(-1,0,0);
			fail("Position out of map");
		} catch (UnitException e){
			//Good
		}
	}
	
	/**
	 * Tests whether working grants a unit experience points.
	 * @throws WorldException
	 */
	@Test
	public void testWorkAtGrantsXP() throws WorldException{
		World world  = new World(smallWorld,changeListener);
		test.setWorld(world);
		test.workAt(1, 1, 0);
		int totProp = test.getToughness()+test.getAgility()+test.getStrength();
		this.advanceSeconds(test, 20);
		int newTotProp = test.getToughness()+test.getAgility()+test.getStrength();
		assert (newTotProp==totProp+1);
		test.workAt(1, 1, 1);
		totProp = test.getToughness()+test.getAgility()+test.getStrength();
		this.advanceSeconds(test, 20);
		newTotProp = test.getToughness()+test.getAgility()+test.getStrength();
		assert (newTotProp==totProp);
	}
	
	/**
	 * Tests whether working at a cube goes in the correct order.
	 * @throws WorldException
	 */
	@Test
	public void testWorkAt() throws WorldException{
		smallWorld[1][1][1] = 3;
		smallWorld[0][0][0] = 2;
		World world = new World(smallWorld,changeListener);
		test.setWorld(world);
		//Collapsing tree
		test.workAt(0, 0, 0);
		this.advanceSeconds(test, 15);
		//Picking up log
		test.workAt(0, 0, 0);
		this.advanceSeconds(test, 15);
		assert (test.isCarryingLog());
		//Dropping log
		test.workAt(1, 1, 1);
		this.advanceSeconds(test, 15);
		assert (!test.isCarryingLog());
		//Collapsing rock
		test.workAt(1, 0, 0);
		this.advanceSeconds(test, 15);
		//Picking up boulder
		test.workAt(1, 0, 0);
		this.advanceSeconds(test, 15);
		assert (test.isCarryingBoulder());
		//Dropping boulder
		test.workAt(1, 1, 1);
		this.advanceSeconds(test, 15);
		assert (!test.isCarryingBoulder());
		Cube workCube = world.getCube(1, 1, 1);
		assert workCube.containsBoulder();
		assert workCube.containsLog();
		//Improving equipment, consuming log and boulder
		int prevTotProp = test.getToughness()+test.getAgility()+test.getStrength();
		test.workAt(1, 1, 1);
		this.advanceSeconds(test, 15);
		int newTotProp = test.getToughness()+test.getAgility()+test.getStrength();
		assert (newTotProp==prevTotProp+2);
		assert !workCube.containsBoulder();
		assert !workCube.containsLog();
		
	}
	
	/**
	 * Tests whether a unit falls at the correct speed.
	 * @throws WorldException
	 */
	@Test
	public void testFallingSpeed() throws WorldException{
		for (int i=1;i<7;i++){
			bigWorld[1][0][i]=1;
		}
		bigWorld[1][0][6] = 1;
		bigWorld[1][1][6] = 1;
		bigWorld[1][2][6] = 1;
		World world = new World(bigWorld,changeListener);
		test.setWorld(world);
		test.moveTo(1, 3, 6);
		this.advanceSeconds(test, 30);
		test.workAt(1, 2, 6);
		while(test.getMyState()==CurrentState.WORKING){
			test.advanceTime(0.1);
		}
		assert (test.getMyState()==CurrentState.FALLING);
		assert (comp.equals(test.getSpeed(), 3));
	}
	
	/**
	 * Tests whether a falling unit moves down until walkable cube.
	 * @throws WorldException
	 */
	@Test
	public void testFallingMovesDown() throws WorldException{
		for (int i=1;i<5;i++){
			bigWorld[1][0][i]=1;
		}
		bigWorld[1][0][4] = 1;
		bigWorld[1][1][4] = 1;
		bigWorld[1][2][4] = 1;
		World world = new World(bigWorld,changeListener);
		test.setWorld(world);
		test.moveTo(1, 3, 4);
		this.advanceSeconds(test, 30);
		test.workAt(1, 2, 4);
		assert (world.getCube(1, 3, 4).isWalkable());
		while(test.getMyState()==CurrentState.WORKING){
			test.advanceTime(0.1);
		}
		assert (!world.getCube(1, 3, 4).isWalkable());
		while (test.getMyState()==CurrentState.FALLING){
			double prevx = test.getxpos();
			double prevy = test.getypos();
			double prevz = test.getzpos();
			test.advanceTime(0.1);
			assert (comp.equals(prevx, test.getxpos()));
			assert (comp.equals(prevy, test.getypos()));
			assert (comp.isSmaller(test.getzpos(), prevz));
		}
		assert (world.getCube(1, 3, 3).isWalkable());
		assert (comp.equals(test.getzpos(), 3.5));

	}
	
	/**
	 * Tests whether falling decreases a units hit points.
	 * @throws WorldException
	 */
	@Test
	public void testFallingDecreasesHP() throws WorldException{
		for (int i=1;i<7;i++){
			bigWorld[1][0][i]=1;
		}
		bigWorld[1][0][6] = 1;
		bigWorld[1][1][6] = 1;
		bigWorld[1][2][6] = 1;
		World world = new World(bigWorld,changeListener);
		test.setWorld(world);
		test.moveTo(1, 3, 6);
		this.advanceSeconds(test, 30);
		test.workAt(1, 2, 6);
		assert (test.getCurrentHP()==test.getMaxHP());
		while(test.getMyState()==CurrentState.WORKING || test.getMyState()==CurrentState.FALLING){
			test.advanceTime(0.1);
		}
		assert (test.getCurrentHP()<test.getMaxHP());
	}
	
	/**
	 * Tests whether a dead unit is removed from its faction and world.
	 * @throws WorldException
	 */
	@Test
	public void testDying() throws WorldException{
		for (int i=1;i<13;i++){
			bigWorld[1][0][i]=1;
		}
		bigWorld[1][0][12] = 1;
		bigWorld[1][1][12] = 1;
		bigWorld[1][2][12] = 1;
		World world = new World(bigWorld,changeListener);
		world.addUnit(test);
		Faction faction = test.getFaction();
		assert (faction.getUnits().contains(test));
		assert (world.getUnits().contains(test));
		test.moveTo(1, 3, 12);
		this.advanceSeconds(test, 30);
		test.workAt(1, 2, 12);
		assert (test.getCurrentHP()==test.getMaxHP());
		while(test.getMyState()==CurrentState.WORKING || test.getMyState()==CurrentState.FALLING){
			test.advanceTime(0.1);
		}
		assert (test.getCurrentHP()==0);
		assert (!faction.getUnits().contains(test));
		assert (!world.getUnits().contains(test));
		
	}

	/**
	 * Generates a random walkable position in the given world.
	 * @param myWorld
	 * @return
	 * @throws UnitException
	 */
	private Position generateWalkablePos(World myWorld) throws UnitException{
		Random random = new Random();
		int x = random.nextInt(myWorld.getDimensionx()-1);
		int y = random.nextInt(myWorld.getDimensiony()-1);
		int z = random.nextInt(myWorld.getDimensionz()-1);
		int looper = z;
		while ((!myWorld.getCube(x, y, looper).isPassable() || (looper != 0 && myWorld.getCube(x, y, looper-1).isPassable())) && looper!=z-1){
			looper+=1;
			looper %= myWorld.getDimensionz()-1;
		}
		if (!myWorld.getCube(x, y, looper).isPassable() || (looper!=0 && myWorld.getCube(x, y, looper-1).isPassable())){
			return generateWalkablePos(myWorld);
		}
		return new Position(x,y,looper,myWorld);
	}
	
	/**
	 * Tests default behaviour without attacking (1 unit).
	 * @throws WorldException
	 */
	@Test
	public void testDefaultBehaviourWithoutAttacking() throws WorldException{
		World world = new World(bigWorld,changeListener);
		test.setWorld(world);
		test.setDefaultBehaviourEnabled(true);
		test.moveTo(2,2,3);
		test.setToggledSprint(true);
		for (int i = 0;i<500;i++){
			this.advanceSeconds(test, 5);
			while (test.getMyState()==CurrentState.NEUTRAL){
				test.advanceTime(0.01);
			}
			assert (test.getMyState()!=CurrentState.NEUTRAL);
			assert (test.getMyState()!=CurrentState.ATTACKING);
			assert (test.getMyState()!=CurrentState.DEFENDING);

			
		}
	}
	
	/**
	 * Tests default behaviour inclusive attacking (multiple units - big test)
	 * @throws WorldException
	 */
	@Test
	public void testDefaultBehaviourWithAttacking() throws WorldException{
		World world = new World(bigWorld,changeListener);
		ArrayList<Unit> units = new ArrayList<>();
		for (int i=0;i<30;i++){
			Unit unit = world.spawnUnit(true);
			units.add(unit);
		}
		for (int i = 0;i<500;i++){
			for (int j = 0; j<500;j++){
				world.advanceTime(0.01);
			}
			for (Unit u : units){
				while (u.getMyState()==CurrentState.NEUTRAL){
					u.advanceTime(0.01);
				} 
				assert (u.getMyState()!=CurrentState.NEUTRAL);
			}
		}
	}
	
	/**
	 * Tests if falling can't be interrupted by attacking, as it shouldn't.
	 * @throws WorldException
	 */
	@Test
	public void testAttackingCantInterruptFalling() throws WorldException{
		for (int i=1;i<7;i++){
			bigWorld[1][0][i]=1;
		}
		bigWorld[1][0][6] = 1;
		bigWorld[1][1][6] = 1;
		bigWorld[1][2][6] = 1;
		World world = new World(bigWorld,changeListener);
		Unit attacker = world.spawnUnit(false);
		world.addUnit(test);
		test.moveTo(1, 3, 6);
		this.advanceSeconds(test, 30);
		attacker.moveTo(1, 2, 5);
		this.advanceSeconds(attacker, 30);
		world.collapseCube(new Position(1,2,6,world));
		assert (test.isFalling());
		while (test.getzpos()>5){
			test.advanceTime(0.1);
		}
		attacker.startAttacking(test);
		assert (attacker.getMyState()!=CurrentState.ATTACKING);
		assert (test.isFalling());
	}
	
	/**
	 * Tests whether a falling unit can't perform an action.
	 * @throws WorldException
	 */
	@Test
	public void cannotWorkOrAttackOrRestWhenFalling() throws WorldException{
		for (int i=1;i<7;i++){
			bigWorld[1][0][i]=1;
		}
		bigWorld[1][0][6] = 1;
		bigWorld[1][1][6] = 1;
		bigWorld[1][2][6] = 1;
		World world = new World(bigWorld,changeListener);
		Unit defender = world.spawnUnit(false);
		world.addUnit(test);
		test.moveTo(1, 3, 6);
		this.advanceSeconds(test, 30);
		defender.moveTo(1, 2, 5);
		this.advanceSeconds(defender, 30);
		world.collapseCube(new Position(1,2,6,world));
		assert (test.isFalling());
		while (test.getzpos()>5){
			test.advanceTime(0.01);
		}
		//ATTACK
		test.startAttacking(defender);
		assert(test.getMyState() != CurrentState.ATTACKING);
		assert(test.isFalling());
		//WORK
		test.workAt(1, 2, 5);
		assert(test.getMyState() != CurrentState.WORKING);
		assert(test.isFalling());
		//REST
		test.startResting();
		assert(test.getMyState() != CurrentState.RESTING);
		assert(test.isFalling());
		}
	
	/**
	 * Tests whether a moving unit can be attacked.
	 * @throws WorldException
	 */
	@Test
	public void attackerCanAttackMovingUnit() throws WorldException{
		World world = new World(smallWorld,changeListener);
		Unit attacker = world.spawnUnit(false);
		world.addUnit(test);
		attacker.moveTo(0,1,1);
		this.advanceSeconds(attacker, 30);
		test.moveTo(1,2,1);
		this.advanceSeconds(test, 30);
		test.moveTo(1, 0, 1);
		while (test.getypos()>1){
			test.advanceTime(0.1);
		}
		attacker.startAttacking(test);
		assertTrue (attacker.getMyState()==CurrentState.ATTACKING);
	}
	


	
	
	

	/**
	 * Tests whether the restrictions on orientation work. Orientation should always be bigger than or equal to 0, and smaller than or equal to 2*PI.
	 * @throws WorldException 
	 */
	@Test
	public void testOrientation() throws WorldException{
		World world = new World(smallWorld,changeListener);
		test.setWorld(world);
		test.setDefaultBehaviourEnabled(true);
		for (int i=0;i<1000;i++){
			test.advanceTime(0.15);
			assert (test.getOrientation()<2*Math.PI);
			assert (test.getOrientation()>=0);
		}
		
	}

}
