package hillbillies.model;
import static org.junit.Assert.*;
import org.junit.Test;

import hillbillies.model.hillbilliesobject.Boulder;
import hillbillies.model.hillbilliesobject.Log;
import hillbillies.model.world.Faction;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
public class WorldTester {
	private static final int TYPE_AIR = 0;
	private static final int TYPE_ROCK = 1;
	private static final int TYPE_TREE = 2;
	private static final int TYPE_WORKSHOP = 3;
	
	/**
	 * Test to check if the dimensions are correct after initializing the world.
	 */
	@Test
	public void testDimensions() throws WorldException{
		World empty4By4By4 = new World(new int[4][4][4], new DefaultTerrainChangeListener());
		World empty2By3By8 = new World(new int[2][3][8], new DefaultTerrainChangeListener());
		assertTrue(empty4By4By4.getDimensionx()==4 && empty4By4By4.getDimensiony()==4 && empty4By4By4.getDimensionz()==4);
		assertTrue(empty2By3By8.getDimensionx()==2 && empty2By3By8.getDimensiony()==3 && empty2By3By8.getDimensionz()==8);
	}
	
	/**
	 * Test to check the different properties of faction and the number of units in this world.
	 * The first test tests that the number of factions always stays below 5.
	 * The next test tests that the number of units in a world always stays below 100.
	 * The last tests makes sure that the number of units in a factions stays below 50.
	 */
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
	/**
	 * Tests to see if cubes are connected to the border.
	 * Test 1 is about a world that in which the cubes are all connected to the border. It makes sure that each solid block is connected to the border
	 * Test 2 updates a non essential cube (one on which the other cubes do not rely to be connected to the border). 
	 * 	It makes sure that the other cubes still are connected.
	 * Test 3 updates a cube to solid 3.1 updates the a cube that will be connected to the border
	 *  and 3.2 updates a cube that wont't be connected to the border.
	 * Test 4 updates a solid cube to air that was crucial for the other cubes.
	 */
	@Test
	public void testSolidConnectedToBorder() throws WorldException{
		
		World testWorldAllConnected = createTestingWorld();
		//TEST 1
		for (int z=0;z<5;z++){	
			for (int x=0;x<4;x++){
				for (int y=0; y<4; y++){
					if (!testWorldAllConnected.getCube(x, y, z).isPassable()){
						assertTrue(testWorldAllConnected.isSolidConnectedToBorder(x, y, z));
					}
				}
			}
		}
		//TEST 2
		testWorldAllConnected.changeCubeType(0, 0, 0, TYPE_AIR);
		assertTrue(testWorldAllConnected.getCube(0, 0, 0).getIntTerrainType() == TYPE_AIR);
		for (int z=0;z<5;z++){	
			for (int x=0;x<4;x++){
				for (int y=0; y<4; y++){
					if (!testWorldAllConnected.getCube(x, y, z).isPassable()){
						assertTrue(testWorldAllConnected.isSolidConnectedToBorder(x, y, z));
					}
				}
			}
		}
		//TEST 3	
		//3.1
		testWorldAllConnected.changeCubeType(2,1, 1, TYPE_ROCK);
		assertTrue(testWorldAllConnected.isSolidConnectedToBorder(2, 1, 1));
		testWorldAllConnected.changeCubeType(2, 1, 1, TYPE_AIR);
		
		World emptyTestWorld = createEmptyTestWorld();
		//3.2 (tested in an empty test world)
		emptyTestWorld.changeCubeType(2,2,2,TYPE_ROCK);
		assertTrue(emptyTestWorld.getCube(2, 2, 2).getIntTerrainType() ==1);
		assertFalse(emptyTestWorld.isSolidConnectedToBorder(2, 2, 2));
		
		//TEST 4
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
	 * Returns an empty 4By4By4 world.
	 */
	private World createEmptyTestWorld() throws WorldException{
		int[][][] w  = new int[4][4][4];
		for (int x=0;x<4;x++){
			for (int y=0;y<4;y++){
				for (int z=0;z<4;z++){
					w[x][y][z]=TYPE_AIR;
				}
				
			}
		}
		
		return  new World(w,new DefaultTerrainChangeListener());
		
		
	}
	/**
	 * Creates a 4*4*5 world in which all the cubes are connected to the border.
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
						w[x][y][z] = TYPE_ROCK;
	
						}
				// first level everything air except one line at x==1 
				else if (z==1 && (y!=0) && (y!=3) && x==1){
					w[x][y][z] = TYPE_TREE;
				}
					
				//Third level with a 2x2  plane connected at the border by one cube.
				else if (z==3){
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
	/**
	 * Test collapsing cubes of different types.
	 * @throws WorldException
	 */
	@Test
	public void testCollapseCube() throws WorldException{
		World worldWithWoodAndRock = createWorldWithWoodAndRock();
		assertTrue(worldWithWoodAndRock.getCube(0, 0, 0).getIntTerrainType()==TYPE_ROCK);
		assertTrue(worldWithWoodAndRock.getCube(1, 1, 0).getIntTerrainType()==TYPE_TREE);
		assertTrue(worldWithWoodAndRock.getCube(0, 0, 0).getObjectsOnThisCube().size()==0);
		assertTrue(worldWithWoodAndRock.getCube(1, 1, 0).getObjectsOnThisCube().size()==0);
		worldWithWoodAndRock.collapseCube(new Position(0,0,0,worldWithWoodAndRock));
		worldWithWoodAndRock.collapseCube(new Position(1,1,0,worldWithWoodAndRock));
		assertTrue(worldWithWoodAndRock.getCube(0, 0, 0).getIntTerrainType()==TYPE_AIR);
		assertTrue(worldWithWoodAndRock.getCube(1, 1, 0).getIntTerrainType()==TYPE_AIR);
		assertTrue(worldWithWoodAndRock.getCube(0, 0, 0).getObjectsOnThisCube().size()==1);
		assertTrue(worldWithWoodAndRock.getCube(1, 1, 0).getObjectsOnThisCube().size()==1);
		assertTrue(worldWithWoodAndRock.getCube(0, 0, 0).containsBoulder());
		assertTrue(worldWithWoodAndRock.getCube(1, 1, 0).containsLog());
	}
	
	/**
	 * Tests if after collapsing the logs and the boulder appear in the world.
	 */
	@Test
	public void testLogAndBouldersOfThisWorld() throws WorldException{
		World testWorld = createEmptyTestWorld();
		//basic collapsing test log
		assertTrue(testWorld.getLogs().size()==0);
		testWorld.changeCubeType(0,0,0, TYPE_TREE);
		testWorld.collapseCube(new Position(0,0,0,testWorld));
		assertTrue(testWorld.getCube(0, 0, 0).containsLog());
		assertTrue(testWorld.getLogs().size()==1);
		//basic collapsing test boulder
		assertTrue(testWorld.getBoulders().size()==0);
		testWorld.changeCubeType(1,0,0, TYPE_ROCK);
		testWorld.collapseCube(new Position(1,0,0,testWorld));
		assertTrue(testWorld.getCube(1, 0, 0).containsBoulder());
		assertTrue(testWorld.getBoulders().size()==1);
	}
	
	
	
	/**
	 * Tests the cave in process.
	 */
	@Test
	public void testChangeCubeTypeAndCaveIn() throws WorldException{
		// Cave in with initializing world with an invalid type.
		int[][][] w  = new int[4][4][4];
		for (int x=0;x<4;x++){
			for (int y=0;y<4;y++){
				for (int z=0;z<4;z++){
					
					if ((x==2)&& (y==2) && (z==2)){
						w[x][y][z] = TYPE_ROCK;
					}
					else{
						w[x][y][z]=TYPE_AIR;
					}
				}
				
			}
		}
		assertTrue(w[2][2][2]==TYPE_ROCK);
		World oneUnconnectedCubeWorld = new World(w,new DefaultTerrainChangeListener());
		assertTrue(oneUnconnectedCubeWorld.getCube(2, 2, 2).getIntTerrainType() == TYPE_AIR);
		
		
		
		//Cave in with 1 solid cube in the middle.
		World emptyWorld = createEmptyTestWorld();
		emptyWorld.changeCubeType(2, 2, 2, TYPE_ROCK);
		assertTrue(emptyWorld.getCube(2, 2, 2).getIntTerrainType()==TYPE_ROCK);
		advanceSeconds(emptyWorld,5);
		assertTrue(emptyWorld.getCube(2, 2, 2).getIntTerrainType()==TYPE_AIR);
		
		// Bigger cave in test after collapse.
		World complexWorld = createTestingWorld();
		assertTrue(complexWorld.getCube(2, 0, 3).getIntTerrainType() == TYPE_TREE );
		assertTrue(complexWorld.getCube(2, 1, 3).getIntTerrainType() == TYPE_ROCK && complexWorld.getCube(2, 2, 3).getIntTerrainType() == TYPE_ROCK
				&& complexWorld.getCube(1, 1, 3).getIntTerrainType() == TYPE_ROCK && complexWorld.getCube(1, 2, 3).getIntTerrainType() == TYPE_ROCK);
		complexWorld.collapseCube(new Position(2,0,3,complexWorld));
		assertTrue(complexWorld.getCube(2, 0, 3).getIntTerrainType() == TYPE_AIR);
		advanceSeconds(complexWorld,5);
		assertTrue(complexWorld.getCube(2, 1, 3).getIntTerrainType() == TYPE_AIR && complexWorld.getCube(2, 2, 3).getIntTerrainType() == TYPE_AIR
				&& complexWorld.getCube(1, 1, 3).getIntTerrainType() == TYPE_AIR && complexWorld.getCube(1, 2, 3).getIntTerrainType() == TYPE_AIR);
		
		// Bigger cave in test after changeCubeType.
		World newComplexWorld = createTestingWorld();
		assertTrue(newComplexWorld.getCube(2, 0, 3).getIntTerrainType() == TYPE_TREE );
		assertTrue(newComplexWorld.getCube(2, 1, 3).getIntTerrainType() == TYPE_ROCK && newComplexWorld.getCube(2, 2, 3).getIntTerrainType() == TYPE_ROCK
				&& newComplexWorld.getCube(1, 1, 3).getIntTerrainType() == TYPE_ROCK && newComplexWorld.getCube(1, 2, 3).getIntTerrainType() == TYPE_ROCK);
		newComplexWorld.changeCubeType(2, 0, 3, TYPE_AIR);
		assertTrue(newComplexWorld.getCube(2, 0, 3).getIntTerrainType() == TYPE_AIR);
		advanceSeconds(newComplexWorld,5);
		assertTrue(newComplexWorld.getCube(2, 1, 3).getIntTerrainType() == TYPE_AIR && newComplexWorld.getCube(2, 2, 3).getIntTerrainType() == TYPE_AIR
				&& newComplexWorld.getCube(1, 1, 3).getIntTerrainType() == TYPE_AIR && newComplexWorld.getCube(1, 2, 3).getIntTerrainType() == TYPE_AIR);
		//Game time test with a 50x50x50 worst case scenario world.
		World worstCaseWorld= createWorstCaseWorld();
		assertTrue(worstCaseWorld.isSolidConnectedToBorder(46, 46, 46));
		worstCaseWorld.collapseCube(new Position(0,1,1,worstCaseWorld));
		advanceSeconds(worstCaseWorld, 5);
		assertTrue(worstCaseWorld.getCube(46, 46, 46).getIntTerrainType() == TYPE_AIR);
		
	}
	/**
	 * Creates the worst case scenario world, this is a diagonal line across the world with solid cubes.
	 */
	public World createWorstCaseWorld() throws WorldException{
		int[][][] w  = new int[50][50][50];
		
		int iterator= 0;
		int[] last = new int[] {0,0,0};
		while (iterator<47){
			w[last[0]][last[1]][last[2]+1] = TYPE_ROCK;
			w[last[0]][last[1]+1][last[2]+1]= TYPE_ROCK;
			w[last[0]+1][last[1]+1][last[2]+1]= TYPE_ROCK;
			last = new int[] {last[0]+1,last[1]+1,last[2]+1};
			iterator ++;
		}
		w[0][0][0]=TYPE_ROCK;
		return new World(w,new DefaultTerrainChangeListener());
	}
	
	/**
	 * Tests the walkable property of a cube. 
	 */
	@Test
	public void testIsWalkable() throws WorldException{
		//Test ground floor empty world is walkable and the other cubes not walkable.
		World emptyWorld = createEmptyTestWorld();
		for (int x= 0; x<4;x++){
			for (int y= 0; y<4;y++){
				for (int z= 0; z<4;z++){
					if (z==0){
						assertTrue(emptyWorld.getCube(x, y, z).isWalkable());
						}
					else{
						assertFalse(emptyWorld.getCube(x, y, z).isWalkable());
					}
				}
			}
		}
	
	
		World stripeWorld = createStripeWorld();
		firstTestStripeWorld(stripeWorld);
		
		//Modify the last cube and test walkable.
		stripeWorld.changeCubeType(3, 7, 2, TYPE_AIR);
		secondTestStripeWorld(stripeWorld);
		//change it back ant test the first test again.
		stripeWorld.changeCubeType(3, 7, 2, TYPE_ROCK);
		firstTestStripeWorld(stripeWorld);		
		//Check the walkable after a small cave in starting at y=2
		stripeWorld.changeCubeType(3, 2, 2, TYPE_AIR);
		advanceSeconds(stripeWorld, 5);
		thirdTestStripeWorld(stripeWorld);
		//Check the walkable after a unit manipulation starting at y=2
		World stripeWorld1 =  createStripeWorld();
		stripeWorld1.collapseCube(new Position(3,2,2,stripeWorld1));
		advanceSeconds(stripeWorld1, 5);
		thirdTestStripeWorld(stripeWorld1);
		
	}
	/**
	 * Makes sure that all the cubes which have to be walkable in the stripeWorld are walkable.
	 * The cubes with a * should be walkable.
	 * The levels z==1 and z==3 will look the same accept the 1's will be replaced with *. 
	 * The rest of the cubes shouldn't be walkable
	 * [0000000]
	 * [0000000]
	 * [00***00]
     * [00*1*00]
     * [00*1*00]
     * [00*1*00]
     * [00*1*00]
     * [00*1*00]
     * [00*1*00]
     * [00*1*00]
     * [00*1*00]
	 */
	private void firstTestStripeWorld(World stripeWorld) {
		for (int z=0;z<5;z++){	
			for (int x=0;x<7;x++){
				for (int y=0; y<11; y++){
					//At the ground every cube should be walkable
					if (z==0){
						assertTrue(stripeWorld.getCube(x,y,z).isWalkable());
					}
					//At the top no cube is walkable.
					if (z==4){
						assertFalse(stripeWorld.getCube(x,y,z).isWalkable());
					}
					//At the levels z==1 an z==3 every cube the desired result is checked.
					if (z==1 || z==3){
						if (x==2 || x==3 || x==4){
							if (y<9){
								assertTrue(stripeWorld.getCube(x,y,z).isWalkable());
							}
							else {
								assertFalse(stripeWorld.getCube(x,y,z).isWalkable());
							}
						}
					}
					//At z== 2 the map above is checked.
					if (z==2){
						if (x==3){
							if (y<8){
								assertFalse(stripeWorld.getCube(x,y,z).isWalkable());
							}
							else if (y==8){
								assertTrue(stripeWorld.getCube(x,y,z).isWalkable());
							}
							else{
								assertFalse(stripeWorld.getCube(x,y,z).isWalkable());
							}
						}
						if (x==2 || x==4){
							if (y<9){
								assertTrue(stripeWorld.getCube(x,y,z).isWalkable());
							}
							else {
								assertFalse(stripeWorld.getCube(x,y,z).isWalkable());
							}
						}
					}
				}
			}
		}
	}
	/**
	 * Makes sure that all the cubes which have to be walkable in the stripeWorld are walkable.
	 * The cubes with a * should be walkable.
	 * The levels z==1 and z==3 will look the same accept the 1's will be replaced with *. 
	 * The rest of the cubes shouldn't be walkable
	 * [0000000]
	 * [0000000]
	 * [00***00]
     * [00*1*00]
     * [00*1*00]
     * [00*1*00]
     * [00*1*00]
     * [00*1*00]
     * [00*1*00]
     * [00*1*00]
     * [00*1*00]
	 */	
	
	/**
	 * Makes sure that all the cubes which have to be walkable in the modified stripeWorld are walkable.
	 * The cubes with a * should be walkable.
	 * The levels z==1 and z==3 will look the same accept the 1's will be replaced with *. 
	 * The rest of the cubes shouldn't be walkable.
	 * [0000000]
	 * [0000000]
	 * [0000000]
     * [00***00]
     * [00*1*00]
     * [00*1*00]
     * [00*1*00]
     * [00*1*00]
     * [00*1*00]
     * [00*1*00]
     * [00*1*00]
	 */
	private void secondTestStripeWorld(World modifiedStripeWorld){
		for (int z=0;z<5;z++){	
			for (int x=0;x<7;x++){
				for (int y=0; y<11; y++){
					//At the ground every cube should be walkable
					if (z==0){
						assertTrue(modifiedStripeWorld.getCube(x,y,z).isWalkable());
					}
					//At the top no cube is walkable.
					if (z==4){
						assertFalse(modifiedStripeWorld.getCube(x,y,z).isWalkable());
					}
					//At the levels z==1 an z==3 every cube the desired result is checked.
					if (z==1 || z==3){
						if (x==2 || x==3 || x==4){
							if (y<8){
								assertTrue(modifiedStripeWorld.getCube(x,y,z).isWalkable());
							}
							else {
								assertFalse(modifiedStripeWorld.getCube(x,y,z).isWalkable());
							}
						}
					}
					//At z== 2 the map above is checked.
					if (z==2){
						if (x==3){
							if (y<7){
								assertFalse(modifiedStripeWorld.getCube(x,y,z).isWalkable());
							}
							else if (y==7){
								assertTrue(modifiedStripeWorld.getCube(x,y,z).isWalkable());
							}
							else{
								assertFalse(modifiedStripeWorld.getCube(x,y,z).isWalkable());
							}
						}
						if (x==2 || x==4){
							if (y<8){
								assertTrue(modifiedStripeWorld.getCube(x,y,z).isWalkable());
							}
							else {
								assertFalse(modifiedStripeWorld.getCube(x,y,z).isWalkable());
							}
						}
					}
				}
			}
		}
	}
	/**
	 * Makes sure that all the cubes which have to be walkable in the given stripeWorld are walkable.
	 * The cubes with a * should be walkable.
	 * The levels z==1 and z==3 will look the same accept the 1's will be replaced with *. 
	 * The rest of the cubes shouldn't be walkable
	 * [0000000]
	 * [0000000]
     * [0000000]
     * [0000000]
     * [0000000]
     * [0000000]
     * [0000000]
     * [00***00]
     * [00*1*00]
     * [00*1*00]
	 */
	private void thirdTestStripeWorld(World stripeWorld){
		for (int z=0;z<5;z++){	
			for (int x=0;x<7;x++){
				for (int y=0; y<11; y++){
					//At the ground every cube should be walkable
					if (z==0){
						assertTrue(stripeWorld.getCube(x,y,z).isWalkable());
					}
					//At the top no cube is walkable.
					if (z==4){
						assertFalse(stripeWorld.getCube(x,y,z).isWalkable());
					}
					//At the levels z==1 an z==3 every cube the desired result is checked.
					if (z==1 || z==3){
						if (x==2 || x==3 || x==4){
							if (y<3){
								assertTrue(stripeWorld.getCube(x,y,z).isWalkable());
							}
							else {
								assertFalse(stripeWorld.getCube(x,y,z).isWalkable());
							}
						}
					}
					//At z== 2 the map above is checked.
					if (z==2){
						if (x==3){
							if (y<2){
								assertFalse(stripeWorld.getCube(x,y,z).isWalkable());
							}
							else if (y==2){
								assertTrue(stripeWorld.getCube(x,y,z).isWalkable());
							}
							else{
								assertFalse(stripeWorld.getCube(x,y,z).isWalkable());
							}
						}
						if (x==2 || x==4){
							if (y<3){
								assertTrue(stripeWorld.getCube(x,y,z).isWalkable());
							}
							else {
								assertFalse(stripeWorld.getCube(x,y,z).isWalkable());
							}
						}
					}
				}
			}
		}
	
	}
		
	/**
	 * Creates the stripe world with configuration at z==2 else where 0.
	 * 	[0000000]
	 *	[0000000]
	 *	[0000000]
	 *	[0001000]
	 *	[0001000]
	 *	[0001000]
	 *	[0001000]
	 *	[0001000]
	 *	[0001000]
	 *	[0001000]
	 *  [0001000]
	 */
	private World createStripeWorld() throws WorldException{
		int[][][] w  = new int[7][11][5];
		for (int z=0;z<5;z++){	
			for (int x=0;x<6;x++){
				for (int y=0; y<10; y++){
					if (z==2 && x==3){
						if (y<8){
							w[x][y][z]= TYPE_ROCK;
	
						}
					}
					else {
						w[x][y][z]= TYPE_AIR;
					}
				}
			}
		}
		return  new World(w,new DefaultTerrainChangeListener());
			
	}
	/**
	 * Creates a 3By3By3 world with rock at (0,0,0) and wood at (1,1,0)
	 */
	private World createWorldWithWoodAndRock() throws WorldException{
		int[][][] w  = new int[3][3][3];
		for (int z=0;z<3;z++){	
			for (int x=0;x<3;x++){
				for (int y=0; y<3; y++){
					if (z==0){
						if (x==0 && y==0){
							w[x][y][z]= TYPE_ROCK;
						}
						if (x==1 && y==1){
							w[x][y][z] = TYPE_TREE;
						}
					}
					else {
						w[x][y][z]= TYPE_AIR;
					}
				}
			}
		}
		return  new World(w,new DefaultTerrainChangeListener());
	}
	
	
	

	
	
	@Test
	public void testDropLoad() throws WorldException{
		//Test ground floor of an empty world
		World world = this.createEmptyTestWorld();
		Log log = new Log(new Position(0,0,0, world), world);
		world.removeLog(log);
		world.dropLoad(log, new Position(1,0,0, world));
		assertTrue(world.getLogs().contains(log));
		
		Boulder boulder = new Boulder( new Position(0,1,0, world), world);
		world.dropLoad(boulder, new Position(0,2,0, world));
		assertTrue(world.getBoulders().contains(boulder));
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
	
}