package hillbillies.model;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
	
	public boolean isSolidConnectedToBorder(int x,int y,int z){
		return ((!this.getCube(x, y, z).isPassable())&& this.connectedToBorder.isSolidConnectedToBorder(x, y, z));
	}
	private final TerrainChangeListener terrainChangeListener;
	/**
	 * A list containing all the different boudlers in this world.
	 */
	private Set<Boulder> boulders = new HashSet<>();
	/**
	 * A list containing all the different logs in this world
	 */
	private Set<Log> logs = new HashSet<>();
	//private Map<int[],HillbilliesObject[]> objectsInMap = new HashMap();
	private static ArrayList<Faction> factions = new ArrayList<>();
	static{
		Faction Targaryen = new Faction();
		Faction Lannister = new Faction();
		Faction Stark = new Faction();
		Faction Martell = new Faction();
		Faction Tyrell = new Faction();
		factions.add(Targaryen);
		factions.add(Lannister);
		factions.add(Stark);
		factions.add(Martell);
		factions.add(Tyrell);
		
	}
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
					world[x][y][z] = new Cube(terrainTypes[x][y][z],this);
					if (getCube(x,y,z).isPassable()){
						connectedToBorder.changeSolidToPassable(x, y, z);
					} 
				}	
				}
			}
		for (int x=0;x<dimension;x++){
			for (int y=0;y<dimension;y++){
				for (int z=0;z<dimension;z++){
					if (!getCube(x,y,z).isPassable()){
						int[] pos = new int[]{-1,0,1};
						for (int xpos: pos){
							for (int ypos: pos){
								for (int zpos: pos){
									if(Position.isValidCoordinate(x+xpos,this.getDimension()) && Position.isValidCoordinate(y+ypos,this.getDimension()) && Position.isValidCoordinate(z+zpos,this.getDimension()) && getCube(x+xpos,y+ypos,z+zpos).isPassable()){
										getCube(x+xpos,y+ypos,z+zpos).setWalkable(true);
									}
								}
							}
					}
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
	

	public ArrayList<Faction> getFactions(){
		return factions;
	}
	
	public Set<Boulder> getBoulders(){
		return boulders;
	}
	
	public Set<Log> getLogs(){
		return logs;
	}
	
	public int getDimension(){
		return this.dimension;
	}
	
	public Set<Unit> getUnitsOfFaction(Faction faction){
		return faction.getUnits();
	}
	
	public Set<Unit> getUnits(){
		Set<Unit> units = new HashSet<>();
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
	
	
	public void advanceTime(double dt) throws WorldException{
		updateCubes();
		for (Log log: this.logs){
			log.advanceTime(dt);
			}
		for (Boulder boulder: this.boulders){
			boulder.advanceTime(dt);
		}
		for (Unit unit: this.getUnits()){
			unit.advanceTime(dt);
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
	//#BOELAANSE LOGICA BITCH
	final static String alphabet = "abcdefghijklmnopqrstuvxyzABCDEFHIJKLMNOPQRSTUVWXYZ '\"";
	final static String ALPHABET = "ABCDEFHIJKLMNOPQRSTUVWXYZ";
	
	public Unit spawnUnit(boolean enableDefaultBehavior) throws UnitException{
			Random random = new Random();
			int x = random.nextInt(this.getDimension()-1);
			int y = random.nextInt(this.getDimension()-1);
			int z = random.nextInt(this.getDimension()-1);
			int looper = z;
			while ((!this.getCube(x, y, looper).isPassable() || (looper != 0 && this.getCube(x, y, looper-1).isPassable())) && looper !=z-1){
				looper+=1;
				looper %= 49;
			}
			if (!this.getCube(x, y, looper).isPassable() || this.getCube(x, y, looper-1).isPassable()){
				return spawnUnit(enableDefaultBehavior);
			}
			int strength = random.nextInt(75)+25;
			int agility = random.nextInt(75)+25;
			int toughness = random.nextInt(75)+25;
			int weight = random.nextInt(75)+25;
			String name = createRandomName();
			Unit unit = new Unit(x,y,looper,name,weight,strength,agility,toughness,enableDefaultBehavior);
			unit.setWorld(this);
			assignFaction(unit);
			return unit;
	}
	
	private static void assignFaction(Unit unit){
		for (int i = 4;i>0; i--){
			if (factions.get(i).getUnits().size()<factions.get(i-1).getUnits().size()){
				factions.get(i).addUnit(unit);
				unit.setFaction(factions.get(i));
				return;
			}
		}
		factions.get(0).addUnit(unit);
		unit.setFaction(factions.get(0));
		
	}
	private static String createRandomName(){
		Random random = new Random();
		String name = new String();
		int length = random.nextInt(20)+2;
		name += ALPHABET.charAt(random.nextInt(ALPHABET.length()));
		for (int i = 0; i<length;i++){
			name +=  alphabet.charAt(random.nextInt(alphabet.length()));
		}
		
		return name;
	}
	public void addUnit(Unit unit){
		assignFaction(unit);
		unit.setWorld(this);
	}
	public Set getActiveFactions(){
		Set<Faction> activeFactions = new HashSet<>();
		for (Faction f: this.getFactions()){
			if (f.getUnits().size()>0){
				activeFactions.add(f);
			}
		}
		return activeFactions;
		
	}
	
	

}
	
	
	
