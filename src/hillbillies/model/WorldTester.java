package hillbillies.model;
import static org.junit.Assert.*;
import org.junit.Test;
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
		World emptyTestWorld = createEmptyTestWorld();
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
		//Test :Update a non crucial cube to air and assert that the others are still solid and  connected to the border.
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
			
		//Update a cube to solid.First we try a cube that will be connected the the border next a cube that isn't connected to the border
		//Update a cube that is connected to the border.
		testWorldAllConnected.changeCubeType(2,1, 1, TYPE_ROCK);
		assertTrue(testWorldAllConnected.isSolidConnectedToBorder(2, 1, 1));
		testWorldAllConnected.changeCubeType(2, 1, 1, TYPE_AIR);
		
		//Updating a cube that isn't connected to the border will be tested in a different world.
		emptyTestWorld.changeCubeType(2,2,2,TYPE_ROCK);
		assertTrue(emptyTestWorld.getCube(2, 2, 2).getIntTerrainType() ==1);
		assertFalse(emptyTestWorld.isSolidConnectedToBorder(2, 2, 2));
		
		//TEST: update a cube that connects other cubes to the border.
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
	@Test
	public void testLogAndBouldersOfThisWorld() throws WorldException{
		World testWorld = createEmptyTestWorld();
		//basic collapsing test log
		assertTrue(testWorld.getLogs().size()==0);
		testWorld.changeCubeType(0,0,0, TYPE_TREE);
		testWorld.collapseCube(new Position(0,0,0));
		assertTrue(testWorld.getCube(0, 0, 0).containsLog());
		
		//basic collapsing test boulder
		assertTrue(testWorld.getBoulders().size()==0);
		testWorld.changeCubeType(1,0,0, TYPE_ROCK);
		testWorld.collapseCube(new Position(1,0,0));
		assertTrue(testWorld.getCube(1, 0, 0).containsBoulder());
		
	}
	
	@Test
	public void testChangeCubeTypeAndCaveIn() throws WorldException{
		// cave in with initializing world.
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
		complexWorld.collapseCube(new Position(2,0,3));
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
		//Game time test with a 100x100x100 worst case scenario world.
		World worstCaseWorld= createWorstCaseWorld();
		assertTrue(worstCaseWorld.isSolidConnectedToBorder(46, 46, 46));
		worstCaseWorld.collapseCube(new Position(0,1,1,worstCaseWorld));
		advanceSeconds(worstCaseWorld, 100);
		assertTrue(worstCaseWorld.getCube(46, 46, 46).getIntTerrainType() == TYPE_AIR);
		
	}
	
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
	
		//Test walkable arround a cube,the used World is a 5 by 10 by 5 world with at z==2 the following construction. The
		// * cubes should be walkable.
		// The levels z==1 and z==3 will look the same accept the ones will be replaced with *.
		//[0000000]
		//[0000000]
		//[00***00]
		//[00*1*00]
		//[00*1*00]
		//[00*1*00]
		//[00*1*00]
		//[00*1*00]
		//[00*1*00]
		//[00*1*00]
		//[00*1*00]
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
		stripeWorld1.collapseCube(new Position(3,2,2));
		advanceSeconds(stripeWorld1, 5);
		thirdTestStripeWorld(stripeWorld1);
		
	}
	
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
		
	private void secondTestStripeWorld(World stripeWorld){
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
							if (y<8){
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
							if (y<7){
								assertFalse(stripeWorld.getCube(x,y,z).isWalkable());
							}
							else if (y==7){
								assertTrue(stripeWorld.getCube(x,y,z).isWalkable());
							}
							else{
								assertFalse(stripeWorld.getCube(x,y,z).isWalkable());
							}
						}
						if (x==2 || x==4){
							if (y<8){
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
	@Test
	public void testCollapseCube() throws WorldException{
		World worldWithWoodAndRock = createWorldWithWoodAndRock();
		assertTrue(worldWithWoodAndRock.getCube(0, 0, 0).getIntTerrainType()==TYPE_ROCK);
		assertTrue(worldWithWoodAndRock.getCube(1, 1, 0).getIntTerrainType()==TYPE_TREE);
		assertTrue(worldWithWoodAndRock.getCube(0, 0, 0).getObjectsOnThisCube().size()==0);
		assertTrue(worldWithWoodAndRock.getCube(1, 1, 0).getObjectsOnThisCube().size()==0);
		worldWithWoodAndRock.collapseCube(new Position(0,0,0));
		worldWithWoodAndRock.collapseCube(new Position(1,1,0));
		assertTrue(worldWithWoodAndRock.getCube(0, 0, 0).getIntTerrainType()==TYPE_AIR);
		assertTrue(worldWithWoodAndRock.getCube(1, 1, 0).getIntTerrainType()==TYPE_AIR);
		assertTrue(worldWithWoodAndRock.getCube(0, 0, 0).getObjectsOnThisCube().size()==1);
		assertTrue(worldWithWoodAndRock.getCube(1, 1, 0).getObjectsOnThisCube().size()==1);
		assertTrue(worldWithWoodAndRock.getCube(0, 0, 0).getABoulder()!=null);
		assertTrue(worldWithWoodAndRock.getCube(1, 1, 0).getALog()!=null);
	}
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
	
	
	
	//TODO: is die nodig ?
	@Test
	public void testDropLoad() throws WorldException{
		
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