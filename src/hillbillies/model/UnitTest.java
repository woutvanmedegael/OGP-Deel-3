package hillbillies.model;

import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Test;

import hillbillies.model.hillbilliesobject.CurrentState;
import hillbillies.model.hillbilliesobject.unit.IllegalNameException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.hillbilliesobject.unit.UnitException;
import hillbillies.model.world.WorldException;
import hillbillies.tests.util.PositionAsserts;

public class UnitTest {
	
	/**
	 * Object to compare floating-point numbers.
	 */
	NbCompare comp = new NbCompare();
	
	/**
	 * Tests whether all restrictions of the name property work. A unit created with a valid name should return that name upon checking.
	 * A unit with an invalid name should throw an IllegalNameException. 
	 * @throws UnitException
	 * 			If the creation of a unit throws an exception (not IllegalNameException in last 2 creations).
	 */
	@Test
	public void testName() throws UnitException {
		Unit test = new Unit(5,5,5,"Adri'aan en W\"out", 50, 50, 50, 50, false);
		assert(test.getName() == "Adri'aan en W\"out");
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
	 * Tests whether all restrictions on coordinates work.
	 * A unit created with valid coordinates should return the middle of the cube the unit is created on.
	 * Invalid coordinates should throw a UnitException.
	 * 
	 * @throws UnitException
	 * 			If the first created unit throws an exception.
	 */
	@Test
	public void testCoordinates() throws UnitException{
		Unit validCoordinates = new Unit(0,13.293935,49.999999,"Adri'aan en W\"out", 50, 50, 50, 50, false);
		PositionAsserts.assertDoublePositionEquals(0.5, 13.5, 49.5, new double[] 
				{validCoordinates.getxpos(),validCoordinates.getypos(),validCoordinates.getzpos()});
		PositionAsserts.assertIntegerPositionEquals(0, 13, 49, new int[] 
				{validCoordinates.getCubeXpos(),validCoordinates.getCubeYpos(),validCoordinates.getCubeZpos()});
		try{
			Unit invalidCoordinates = new Unit(0,-5,0,"Adri'aan en W\"out", 50, 50, 50, 50, false);
			fail("UnitException not thrown");
		} catch (UnitException e){
			  //Great
		}
		try{
			Unit invalidCoordinates = new Unit(50,0,0,"Adri'aan en W\"out", 50, 50, 50, 50, false);
			fail("UnitException not thrown");
		} catch (UnitException e){
			  //Great
		}
		try{
			Unit invalidCoordinates = new Unit(0,0,500,"Adri'aan en W\"out", 50, 50, 50, 50, false);
			fail("UnitException not thrown");
		} catch (UnitException e){
			  //Great
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
	 * Tests whether the restrictions on orientation work. Orientation should always be bigger than or equal to 0, and smaller than or equal to 2*PI.
	 * @throws UnitException
	 */
	@Test
	public void testOrientation() throws UnitException{
		Unit or = new Unit(0,13.293935,49.999999,"Adri'aan en W\"out", 25, 110, -10, 50, false);
		assert (comp.equals(or.getOrientation(), Math.PI/2.0));
		Random rand = new Random();
		or.setOrientation(1);
		assert (or.getOrientation()==1);
		or.setOrientation((float) (1+10*Math.PI));
		assert (comp.equals(or.getOrientation(), 1));
		or.setOrientation((float) (1-10*Math.PI));
		assert (comp.equals(or.getOrientation(), 1));
		for (int i=0; i<20;i++){
			float orient = rand.nextFloat()*200-100;
			or.setOrientation(orient);
			assert (0<=or.getOrientation() && Math.PI*2>=or.getOrientation());
		}
		or.setOrientation(0);
		assert (0<=or.getOrientation() && Math.PI*2>=or.getOrientation());
		or.setOrientation((float) (Math.PI*2));
		assert (0<=or.getOrientation() && Math.PI*2>=or.getOrientation());
		
	}
	
	/**
	 * Tests whether the advance time method returns exceptions if invalid arguments are given.
	 * @throws WorldException 
	 */
	@Test
	public void testAdvanceTime() throws WorldException{
		Unit test = new Unit(5,5,5,"Adri'aan en W\"out", 50, 50, 50, 50, false);
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
	 * Tests whether all restrictions on MoveToAdjacent work.
	 * @throws WorldException 
	 */
	@Test
	public void testMoveToAdjacent() throws WorldException{
		Unit test = new Unit(5,5,5,"Adri'aan en W\"out", 50, 50, 50, 50, false);
		assert (test.getMyState() == CurrentState.NEUTRAL);
		test.moveToAdjacent(1, 1, 1);
		assert (test.getMyState() == CurrentState.MOVING);
		PositionAsserts.assertDoublePositionEquals(5.5, 5.5, 5.5, new double[] 
				{test.getxpos(),test.getypos(),test.getzpos()});
		assert (test.getSpeed()==0.75);
		this.advanceSeconds(test, 3);
		assert (test.getMyState() == CurrentState.NEUTRAL);
		PositionAsserts.assertDoublePositionEquals(6.5, 6.5, 6.5, new double[] 
				{test.getxpos(),test.getypos(),test.getzpos()});
		test.setWeight(75);
		test.moveToAdjacent(1, 0, 0);
		assert (comp.equals(test.getSpeed(), 1));
		this.advanceSeconds(test, 0.5);
		PositionAsserts.assertDoublePositionEquals(7, 6.5, 6.5, new double[] 
				{test.getxpos(),test.getypos(),test.getzpos()});
		test.moveToAdjacent(0, 1, 0);
		this.advanceSeconds(test, 2);
		PositionAsserts.assertDoublePositionEquals(7.5, 6.5, 6.5, new double[] 
				{test.getxpos(),test.getypos(),test.getzpos()});
		test.setOrientation(0);
		test.moveToAdjacent(1, 1, 0);
		assert (comp.equals(test.getSpeed(),1));
		this.advanceSeconds(test, 0.1);
		assert (comp.equals(test.getOrientation(), Math.PI/4));
		test.workAt(0,0,0);
		assert (test.getMyState() == CurrentState.MOVING);
		test.startResting();
		assert(!test.isResting());
		double x1 = test.getxpos();
		this.advanceSeconds(test, 0.05);
		double x2 = test.getxpos();
		assert (x1!=x2);
		test.setCurrentHP(test.getMaxHP());
		test.setCurrentSP(test.getMaxSP());
		this.advanceSeconds(test, 3);
		System.out.println(test);
		assert (test.getMyState()==CurrentState.NEUTRAL);
		test.moveToAdjacent(1, 1, -1);
		test.startDefending(new Unit(5,5,5,"Adri'aan en W\"out", 50, 50, 50, 50, false));
		assert (test.getMyState() == CurrentState.DEFENDING);
		this.advanceSeconds(test, 1);
		assert (test.getMyState() == CurrentState.DEFENDING);
		test.defend(new Unit(5,5,5,"Adri'aan en W\"out", 50, 50, 50, 50, false));
		this.advanceSeconds(test, 0.1);
		assert (test.getMyState()==CurrentState.MOVING);
		assert (comp.equals(test.getSpeed(), 1.2));
		this.advanceSeconds(test, 1);
		assert (test.getxpos()!=9.5);
		assert (test.getxpos()!=8.5);
		this.advanceSeconds(test, 1);
		PositionAsserts.assertDoublePositionEquals(9.5, 8.5, 5.5, new double[] 
				{test.getxpos(),test.getypos(),test.getzpos()});
	}
	
	/**
	 * Tests the method moveTo on its restrictions.
	 * @throws WorldException 
	 */
	@Test
	public void testMoveTo() throws WorldException{
		Unit test = new Unit(5,5,5,"Adri'aan en W\"out", 50, 50, 50, 50, false);
		test.moveTo(0, 0, 0);
		assert (test.getMyState() == CurrentState.MOVING);
		this.advanceSeconds(test, 6);
		PositionAsserts.assertDoublePositionEquals(0.5, 0.5, 0.5, new double[] 
				{test.getxpos(),test.getypos(),test.getzpos()});
		test.moveTo(5, 5, 0);
		this.advanceSeconds(test, 0.5);
		test.setCurrentSP(45);
		test.startResting();
		assert (test.getMyState()==CurrentState.RESTING);
		this.advanceSeconds(test, 2.5);
		assert (test.getMyState() == CurrentState.MOVING);
		this.advanceSeconds(test, 4);
		PositionAsserts.assertDoublePositionEquals(5.5, 5.5, 0.5, new double[] 
				{test.getxpos(),test.getypos(),test.getzpos()});
		assert (test.getMyState() == CurrentState.NEUTRAL);
		test.moveTo(0, 0, 0);
		this.advanceSeconds(test, 1);
		test.startDefending(new Unit(5,5,5,"Adri'aan en W\"out", 50, 50, 50, 50, false));
		assert (test.getMyState() == CurrentState.DEFENDING);
		this.advanceSeconds(test, 1);
		test.defend(new Unit(5,5,5,"Adri'aan en W\"out", 50, 50, 50, 50, false));
		this.advanceSeconds(test, 0.05);
		assert (test.getMyState() == CurrentState.MOVING);
		this.advanceSeconds(test, 6);
		PositionAsserts.assertDoublePositionEquals(0.5, 0.5, 0.5, new double[] 
				{test.getxpos(),test.getypos(),test.getzpos()});
		test.moveTo(3, 0, 0);
		this.advanceSeconds(test, 1);
		test.moveTo(0, 0, 0);
		this.advanceSeconds(test, 5);
		PositionAsserts.assertDoublePositionEquals(0.5, 0.5, 0.5, new double[] 
				{test.getxpos(),test.getypos(),test.getzpos()});
	}
	
	/**
	 * Tests the method work() on all restrictions.
	 * @throws WorldException 
	 */
	@Test
	public void testWork() throws WorldException{
		Unit test = new Unit(5,5,5,"Adri'aan en W\"out", 50, 50, 50, 50, false);
		test.workAt(0,0,0);
		assert (test.getMyState()==CurrentState.WORKING);
		this.advanceSeconds(test, 9);
		assert (test.getMyState()==CurrentState.WORKING);
		this.advanceSeconds(test, 2);
		assert (!(test.getMyState()==CurrentState.WORKING));
	}
	
	/**
	 * Checks whether fighting units satisfy the conditions.
	 * @throws WorldException 
	 */
	@Test
	public void testFighting() throws WorldException{
		Unit defender = new Unit(5,5,5,"Adri'aan en W\"out", 50, 50, 50, 50, false);
		Unit attacker = new Unit(6,6,5,"Adri'aan en W\"out", 50, 50, 50, 50, false);
		defender.startDefending(attacker);
		attacker.initiateAttack(defender);
		this.advanceSeconds(attacker,0.05);
		assert (comp.equals(defender.getOrientation(), Math.PI/4));
		assert (comp.equals(attacker.getOrientation(), 5*Math.PI/4));
		assert (defender.getMyState() == CurrentState.DEFENDING);
		assert (attacker.getMyState() == CurrentState.ATTACKING);
		this.advanceSeconds(defender, 0.9);
		this.advanceSeconds(attacker, 0.9);
		assert (defender.getMyState() == CurrentState.DEFENDING);
		assert (attacker.getMyState() == CurrentState.ATTACKING);
		this.advanceSeconds(attacker, 0.15);
		assert (!(attacker.getMyState() == CurrentState.ATTACKING));
		attacker.setAgility(1);
		defender.setAgility(5);
		defender.setMyPosition(new Position(5.5,5.5,5.5));
		assert (defender.getxpos()==5.5);
		assert (defender.getypos()==5.5);
		assert (defender.getzpos()==5.5);
		defender.defend(attacker);
		assert (defender.getxpos()!=5.5);
		assert (defender.getypos()!=5.5);
		assert (defender.getzpos()==5.5);
		defender.setCurrentHP(40);
		defender.blocked(attacker);
		assert (defender.getCurrentHP() == 40);
		defender.takeDamage(attacker);
		assert (defender.getCurrentHP() == 35);
	}
	
	/**
	 * Tests whether units satisfy all conditions concerning resting.
	 * @throws WorldException 
	 */
	@Test
	public void testResting() throws WorldException{
		Unit test = new Unit(5,5,5,"Adri'aan en W\"out", 50, 50, 50, 50, false);
		test.setCurrentHP(10);
		test.setCurrentSP(10);
		test.startResting();
		assert (test.getMyState()==CurrentState.RESTING);
		this.advanceSeconds(test, 8.01);
		assert (test.getCurrentHP() == 20);
		assert (test.getCurrentSP() == 10);
		this.advanceSeconds(test, 28);
		assert (test.getCurrentHP() == 50);
		assert (test.getCurrentSP() == 20);
		this.advanceSeconds(test, 12.02);
		assert (test.getCurrentHP() == 50);
		assert (test.getCurrentSP() == 50);
		test.setCurrentHP(30);
		test.setCurrentSP(30);
		assert (!(test.getMyState()==CurrentState.RESTING));
		this.advanceSeconds(test, 180-8-12-28);
		assert (test.getMyState()==CurrentState.RESTING);
		
	}
	
	/**
	 * Tests a units default behaviour.
	 * @throws WorldException 
	 */
	@Test
	public void testDefaultBehaviour() throws WorldException{
		Unit test = new Unit(5,5,5,"Adri'aan en W\"out", 50, 50, 50, 50, false);
		test.setStrength(200);
		test.setDefaultBehaviourEnabled(true);
		for (int i=0;i<40;i++){
			this.advanceSeconds(test, 3);
			assert (test.getMyState()==CurrentState.WORKING || test.getMyState() == CurrentState.MOVING || test.getMyState()==CurrentState.RESTING);
		}
	}
	
	
	/**
	 * Tests a units sprinting mode.
	 * @throws WorldException 
	 */
	@Test
	public void testSprint() throws WorldException{
		Unit test = new Unit(5,5,5,"Adri'aan en W\"out", 50, 50, 50, 50, false);
		assert (test.getToggledSprint() == false);
		test.setToggledSprint(true);
		assert (test.getToggledSprint() == true);
		test.moveTo(2, 5, 5);
		test.setToggledSprint(true);
		assert (test.getToggledSprint() == true);
		assert (test.getCurrentSP() == 50);
		this.advanceSeconds(test, 1.05);
		assert (test.getToggledSprint() == true);
		assert (test.getCurrentSP() == 40);
		assert (test.getMyState() == CurrentState.NEUTRAL);
		this.advanceSeconds(test, 1);
		assert (test.getToggledSprint() == true);
		assert (test.getCurrentSP() == 40);
		test.moveTo(5, 5, 5);
		this.advanceSeconds(test,0.2);
		assert (test.getCurrentSP()==38);
		test.setToggledSprint(false);
		this.advanceSeconds(test, 2);
		assert (test.getToggledSprint() == false);
		assert (test.getCurrentSP()==38);
		test.setCurrentSP(1);
		test.moveTo(0,5,5);
		test.setToggledSprint(true);
		this.advanceSeconds(test, 0.11);
		assert (test.getToggledSprint() == false);
		assert (test.getCurrentSP() == 0);
		
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
	
	
	
}
