package hillbillies.model;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
public class World {
	/**
	 * variables registering the dimensions of the game world
	 */
	private final int dimension;
	/**
	 * variable registering the length of 1 cube
	 */
	private final double lc = 1;
	//TERRAIN
	/**
	 * A list to keep track of the different cubes in the world
	 */
	private Cube[][][] world;
	private ConnectedToBorder connectedToBorder;
	private final TerrainChangeListener terrainChangeListener;
	/**
	 * A list containing all the different boudlers in this world.
	 */
	private ArrayList<Boulder> boulders = new ArrayList<>();
	/**
	 * A list containing all the different logs in this world
	 */
	private ArrayList<Log> logs = new ArrayList<>();
	//private Map<int[],HillbilliesObject[]> objectsInMap = new HashMap();
	private ArrayList<Faction> factions = new ArrayList<>();
	private ArrayList<int[]> cubesToCheck = new ArrayList<int[]>();
	
	
	private int cubesToJump;
	final static double maxDT = 0.2;
	final static int secondsToCaveIn = 5;
	
	
	/**
	 * initialize the world
	 * @param terrainTypes
	 * @param modelListener
	 * @throws WorldException
	 */
	public World(int[][][] terrainTypes, TerrainChangeListener modelListener) throws WorldException{
		dimension= terrainTypes.length;
		world = new Cube[dimension][dimension][dimension];
		connectedToBorder = new ConnectedToBorder(dimension,dimension,dimension);
		for (int x=0;x<dimension;x++){
			for (int y=0;y<dimension;y++){
				for (int z=0;z<dimension;z++){
					this.setCube(x,y,z,new Cube(terrainTypes[x][y][z],this));
					if (getCube(x,y,z).isPassable()){
						connectedToBorder.changeSolidToPassable(x, y, z);
					}
				}	
				}
			}
		terrainChangeListener = modelListener;
		cubesToJump = (int) Math.ceil(dimension*maxDT/secondsToCaveIn);
		}
	public Cube getCube(int x,int y,int z){
		return world[x][y][z];
	}
	private void setCube(int x,int y,int z,Cube cube){
		world[x][y][z] = cube;
		//terrainChangeListener.notifyTerrainChanged(x, y, z);
	}
	public ArrayList<Faction> getFactions(){
		return factions;
	}
	
	public ArrayList<Boulder> getBoulders(){
		return boulders;
	}
	
	public ArrayList<Log> getLogs(){
		return logs;
	}
	
	public int getDimension(){
		return this.dimension;
	}
	
	public Set<Unit> getUnitsOfFaction(Faction faction){
		return faction.getUnits();
	}
	
	public Set<Unit> getUnits(){
		Set<Unit> units = Collections.<Unit>emptySet();
		for (Faction f : factions){
			units.addAll(f.getUnits());
		}
		return units;
		
	}
	
	
	
	//Misschien ook nog checken of het type van cube ook effectief veranderd naar AIR ofzo
	//The caving-in process starts with a call of changeCubeType. 
	// next if the cube is changed to an passable type, the connected to border is informed
	// a warning is thrown towards the neighbouring cubes
	
	public void changeCubeType(int x, int y, int z, int value) throws UnitException{	
		this.getCube(x, y, z).setTerrainType(value);
		terrainChangeListener.notifyTerrainChanged(x, y, z);
		if (this.getCube(x, y, z).isPassable()){
			doSomethingIfObjectIsDependent(x,y,z);
			connectedToBorder.changeSolidToPassable(x, y, z);
		}
		throwWarningAround(x,y,z);
		
	}
	
	//Misschien niet bij unit?
	private void doSomethingIfObjectIsDependent(int x, int y, int z) {
		// TODO aanvullen
		for (HillbilliesObject obj :this.getCube(x, y, z+1).getObjects()){
			
		}
		
		
	}
	
	//a warning is thrown arround in all directions, if we want to be smart
	/**
	 * notify's the neighbouring cubes.
	 */
	private void throwWarningAround(int xpos,int ypos,int zpos) throws UnitException{
		for (int x = xpos -this.cubesToJump;x<=xpos+this.cubesToJump;x++){
			for (int y = ypos -this.cubesToJump;y<=ypos+ this.cubesToJump;y++){
				for (int z =zpos -this.cubesToJump;z<=zpos + this.cubesToJump;z++){
					if (!world[x][y][z].isPassable()){
						//the passable cubes are added to the cubes to check
						cubesToCheck.add(new int[]{x,y,z});
					}
				}
			}
		}
	}
	

	private void updateCubes() throws WorldException{
		ArrayList<int[]> cubesToCaveIn = new ArrayList<int[]>();
		for (int[] p: cubesToCheck){
			if (!connectedToBorder.isSolidConnectedToBorder(p[0],p[1],p[2])){
				cubesToCaveIn.add(p);
			}
		}
		this.cubesToCheck =new ArrayList<int[]>();
		caveIn(cubesToCaveIn);
		
	}
	/**
	 * resets the type of the given cubes to air and possibly adds a log
	 * @param cubesToCaveIn
	 * @throws WorldException 
	 */
	private void caveIn(ArrayList<int[]> cubesToCaveIn) throws WorldException{
		for (int[] p: cubesToCaveIn){
			// Cube cubeToReplace = getCube(p[0],p[1],p[2]);
			 //change the cube to air
			 changeCubeType(p[0],p[1],p[2],0);
			 maybeAddLogOrBoulder(p, getCube(p[0],p[1],p[2]).getTerrainType ());
			
		}
	}
	private final Random random = new Random();
	private void maybeAddLogOrBoulder(int[] p, TerrainType type) throws WorldException{
		//TODO: add a log or boulder with probability 0.25
		boolean succeeded = random.nextFloat()<0.25;
		if ( succeeded && type == TerrainType.TREE){
			addLog(p);
		}
		else if (succeeded){
			addBoulder(p);
		}
		
	}
	
	public void addLog(int[] p) throws WorldException{
		Position pos = new Position(p[0],p[1],p[2]);
		Log newLog = new Log(pos,this);
		logs.add(newLog);
		
	}
	public void addBoulder(int[] p ) throws WorldException{
		Position pos = new Position(p[0],p[1],p[2]);
		Boulder newBoulder = new Boulder(pos,this);
		boulders.add(newBoulder);
	}
	//TODO:vermits we nu geen lijst meer hebben van alle objecten hoe doen we het dan ? Boulder, log en unit apart of 
	// opvragen van alle cubes?
	
	
	public void advanceTime(double dt) throws UnitException{
		updateCubes();
		for (Log log: this.logs){
			log.advanceTime(dt);
			}
		for (Boulder boulder: this.boulders){
			boulder.advanceTime(dt);
		}
		for (Unit unit: this.getUnits()){
			unit.ad
		}
		
		}
	
	
	
	public boolean isWalkable(int xpos, int ypos, int zpos){
		if (!this.getCube(xpos, ypos, zpos).isPassable()){
			return false;
		}
		for (int x = -1;x<=1;x++){
			for (int y = -1;y<=1;y++){
				for (int z = -1;z<=1;z++){
					if (!this.getCube(xpos+x,ypos+y,ypos+z).isPassable()){
						return true;
					}
				}
			}
		}
		return false;
	}

	
	

}
	
	
	

