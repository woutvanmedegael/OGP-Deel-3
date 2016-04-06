package hillbillies.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.Faction;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;
import hillbillies.part2.listener.DefaultTerrainChangeListener;

public class WorldTester {
	private static final int TYPE_AIR = 0;
	private static final int TYPE_ROCK = 1;
	private static final int TYPE_TREE = 2;
	private static final int TYPE_WORKSHOP = 3;
	

	
	@Test
	public void testDimensions() throws WorldException{
		World empty4By4By4 = new World(new int[4][4][4], new DefaultTerrainChangeListener());
		World empty2By3By8 = new World(new int[2][3][8], new DefaultTerrainChangeListener());
		System.out.println(new int[4][4][4]) ;
		assertTrue(empty4By4By4.getDimensionx()==4 && empty4By4By4.getDimensiony()==4 && empty4By4By4.getDimensionz()==4);
		assertTrue(empty2By3By8.getDimensionx()==2 && empty2By3By8.getDimensiony()==3 && empty2By3By8.getDimensionz()==8);
	
	}
	
	@Test
	public void testFactionsAndNumberOfUnits() throws WorldException {
		World empty4By4 = new World(new int[4][4][4], new DefaultTerrainChangeListener());
		//Testing the number of factions.
		
		empty4By4.spawnUnit(false);
		assertTrue(empty4By4.getActiveFactions().size()==1);
		empty4By4.spawnUnit(false);
		empty4By4.spawnUnit(false);
		empty4By4.spawnUnit(false);
		assertTrue(empty4By4.getActiveFactions().size()==4);
		for (int i=0;i<20;i++){
			empty4By4.spawnUnit(false);
		}
		assertTrue(empty4By4.getActiveFactions().size()==5);
		for (int i=0;i<80;i++){
			empty4By4.spawnUnit(false);
		}
		assertTrue(empty4By4.getActiveFactions().size() ==5);
		//Testing the that the number of units stays below 100 in a world.
		assertTrue(empty4By4.getUnits().size()<=100);
		//Testing that the number of units in a faction stays below 50, it is impossible for someone of the outside to 
		// add units to a specific faction (only in package world).
		for (Faction f: empty4By4.getActiveFactions()){
			assertTrue(f.getUnits().size() <=50);
		}	
	}
	
	@Test
	public void testSolidConnectedToBorder() throws WorldException{
		
		World testWorldAllConnected = createTestingWorld();
		//Test if all the solid blocks are connected to the border.
		for (int z=0;z<5;z++){	
			for (int x=0;x<4;x++){
				for (int y=0; y<4; y++){
					if (!testWorldAllConnected.getCube(x, y, z).isPassable()){
						assertTrue(testWorldAllConnected.isSolidConnectedToBorder(x, y, z));
					}
				}
			}
		}
		//Update a non crucial cube to air.
		System.out.println("de 000 heeft waarde:");
		System.out.println(testWorldAllConnected.getCube(0, 0, 0).getIntTerrainType());
		testWorldAllConnected.changeCubeType(0, 0, 0, TYPE_AIR);
		assertTrue(testWorldAllConnected.getCube(0, 0, 0).getIntTerrainType() == TYPE_AIR);
		//check if the other cubes are still connected to the border.
		for (int z=0;z<5;z++){	
			for (int x=0;x<4;x++){
				for (int y=0; y<4; y++){
					if (!testWorldAllConnected.getCube(x, y, z).isPassable()){
						assertTrue(testWorldAllConnected.isSolidConnectedToBorder(x, y, z));
					}
				}
			}
		}

			
		//Update a block to solid.First we try a block that will be connected the the border next a block that isn't connected to the border
		//Will be connected to the border.
		testWorldAllConnected.changeCubeType(2,1, 1, TYPE_ROCK);
		System.out.println(testWorldAllConnected.getCube(2, 1, 1).getIntTerrainType());
		
		System.out.println(testWorldAllConnected.getCube(2, 0, 0).getIntTerrainType());

		assertTrue(testWorldAllConnected.isSolidConnectedToBorder(2, 1, 1));
		
		
		
		testWorldAllConnected.changeCubeType(2, 1, 1, TYPE_AIR);
		
		//Update the cube with coordinates [2][0][3] to air.
		testWorldAllConnected.changeCubeType(2, 0, 3, TYPE_AIR);
		//We make sure that the 2*2 plane on level 3 isn't connected to the border anymore and the others still are.
		int stillSolidButNotconnectedToTheBorderInLevel3Counter =0;
		for (int z=0;z<5;z++){	
			for (int x=0;x<4;x++){
				for (int y=0; y<4; y++){
					if (!testWorldAllConnected.getCube(x, y, z).isPassable()){
						if (z!=3){
						assertTrue(testWorldAllConnected.isSolidConnectedToBorder(x, y, z));
						}
						else{
							assertFalse(testWorldAllConnected.isSolidConnectedToBorder(x, y, z));
							stillSolidButNotconnectedToTheBorderInLevel3Counter +=1 ;
						}
					}
				}
			}
		}
		//The only thing left in level 3 should be the 2*2 plane which is not connected to the border.
		assertEquals(stillSolidButNotconnectedToTheBorderInLevel3Counter,4);
		
		
		
	}
	/**
	 * Creates a valid 4*4*5 world in which all the cubes are connected to the border.
	 * Level zero looks like: 
	 * 		[0000]
	 * 		[0000]
	 * 		[1111]
	 * 		[1111]					
	 * Level one looks like;
	 * 		[0000]
	 * 		[0200]
	 * 		[0200]
	 * 		[0000]
	 * Level two looks like
	 * 		[0000]
	 * 		[0000]
	 * 		[0000]
	 * 		[0000]
	 * Level three looks like
	 * 		[0000]
	 * 		[0110]
	 * 		[0110]
	 * 		[0020]	
	 * Level four looks like
	 * 		[0000]
	 * 		[0000]
	 * 		[0000]
	 * 		[0000]
	 */
	private World createTestingWorld() throws WorldException {
		int[][][] w  = new int[4][4][5];
		{
		//create a world
		for (int z=0;z<5;z++)	
			for (int x=0;x<4;x++){
				for (int y=0; y<4; y++ ){
				//ground level everything air except the y coordinates smaller than 2
				if (z==0 &&  y<2){	
						w[x][y][0] = TYPE_ROCK;
						System.out.println("printing x,y,z");
						System.out.println(x);
						System.out.println(y);
						System.out.println(z);
						
						}
				// first level everything air except one line at x==1 
				if (z==1 && (y!=0) && (y!=3) && x==1){
					w[x][y][z] = TYPE_TREE;
				}
					
				//Third level with a 2x2  plane connected at the border by one cube.
				if (z==3){
					if (x==2 && y==0){
						w[x][y][z]= TYPE_TREE;
					}
					if ((y ==1 || y==2)  && (x ==1 || x==2)){
						w[x][y][z]= TYPE_ROCK;
					}
				}
				else{
					w[x][y][z]= TYPE_AIR;
				}
				
				
			}
		}
	}
	
		return  new World(w,new DefaultTerrainChangeListener());
	}
//	@Test
//	public void testLogAndBouldersOfThisWorld() throws WorldException{
//		
//	}
//	
//	@Test
//	public void testChangeCubeTypeAndCaveIn() throws WorldException{
//		
//	}
//	@Test
//	public void testIsWalkable() throws WorldException{
//		
//	}
//	@Test
//	public void testCollapseCube() throws WorldException{
//		
//	}
//	
//
//	
//	@Test
//	public void testDropLoad() throws WorldException{
//		
//	}	
//	
	
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
	

}
