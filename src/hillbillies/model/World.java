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
	private final int dimensionx;
	private final int dimensiony;
	private final int dimensionz;
	/**
	 * variable registering the length of 1 cube
	 */
	private final double lc = 1;
	//TERRAIN
	/**
	 * A list to keep track of the different cubes in the world
	 */
	private Cube[][][] world;
	/**
	 * TODO: class conncted to border aanpassen
	 */
	private ConnectedToBorder connectedToBorder;
	
	public boolean isSolidConnectedToBorder(int x,int y,int z){
		return ((!this.getCube(x, y, z).isPassable())&& this.connectedToBorder.isSolidConnectedToBorder(x, y, z));
	}
	private final TerrainChangeListener terrainChangeListener;
	/**
	 * A set containing all the different boudlers in this world.
	 */
	private Set<Boulder> boulders = new HashSet<>();
	/**
	 * A set containing all the different logs in this world.
	 */
	private Set<Log> logs = new HashSet<>();
	
	//private Map<int[],HillbilliesObject[]> objectsInMap = new HashMap();
	/**
	 * An arrayList containing all the factions of this world.
	 */
	private static ArrayList<Faction> factions = new ArrayList<>();
	/**
	 * The 5 different factions are created.
	 */
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
	/**
	 * A variable used to keep track of the cubes who might have to cave in.
	 */
	private ArrayList<int[]> cubesToCheck = new ArrayList<int[]>();
	
	/**
	 * A variable registering the number of cubes to jump when checking which cubes have to cave in
	 */
	private final int cubesToJump;
	/**
	 * the longest possible time step
	 */
	final static double maxDT = 0.2;
	/**
	 * the longest period that it may take untill all the cubes have caved in
	 */
	final static int secondsToCaveIn = 5;
	
	
	/**
	 * initialize the world
	 */
	public World(int[][][] terrainTypes, TerrainChangeListener modelListener) throws WorldException{
		dimensionx = terrainTypes.length;
		dimensiony = terrainTypes[1].length;
		dimensionz = terrainTypes[0][1].length;

		world = new Cube[dimensionx][dimensiony][dimensionz];
		connectedToBorder = new ConnectedToBorder(dimensionx,dimensiony,dimensionz);
		for (int x=0;x<dimensionx;x++){
			for (int y=0;y<dimensiony;y++){
				for (int z=0;z<dimensionz;z++){
					world[x][y][z] = new Cube(terrainTypes[x][y][z],this);
					if (getCube(x,y,z).isPassable()){
						connectedToBorder.changeSolidToPassable(x, y, z);
					} 
				}	
				}
			}
		fixWalkableCubes();
		terrainChangeListener = modelListener;
		int dimensionMax = Math.max(dimensionx,Math.max(dimensiony, dimensionz));
		cubesToJump = (int) Math.ceil(dimensionMax*maxDT/secondsToCaveIn);
		}
	/**
	 * sets all the cubes that are walkable in world to walkable
	 */
	private void fixWalkableCubes() {
		for (int x=0;x<dimensionx;x++){
			for (int y=0;y<dimensiony;y++){
				for (int z=0;z<dimensionz;z++){
					setSurroundingCubesToWalkable(x, y, z);
			}
		}
		}
	}
	/**
     * If the given cube is a solid, the passable cubes arround it are put to walkable
	 */
	private void setSurroundingCubesToWalkable(int x, int y, int z) {
		if (!getCube(x,y,z).isPassable()){
			int[] pos = new int[]{-1,0,1};
			for (int xpos: pos){
				for (int ypos: pos){
					for (int zpos: pos){
						if(Position.isValidPos(x+xpos, y+ypos, z+zpos,this)&& getCube(x+xpos,y+ypos,z+zpos).isPassable()){
							getCube(x+xpos,y+ypos,z+zpos).setWalkable(true);
						}
					}
				}
		}
}
	}
	/**
	 *returns the cube at the given position
	 */
	public Cube getCube(int x,int y,int z){
		return this.world[x][y][z];
	}
	
	/**
	 * returns an arraylist of the factions in this world
	 */
	public static ArrayList<Faction> getFactions(){
		return World.factions;
	}
	/**
	 * returns a set of all the boulders in this world
	 */
	public Set<Boulder> getBoulders(){
		return this.boulders;
	}
	
	/**
	 * returns a set of all the logs in this world
	 */
	public Set<Log> getLogs(){
		return logs;
	}
	/**
	 * returns the dimension in the x direction
	 */
	public int getDimensionx(){
		return this.dimensionx;
	}
	/**
	 * returns the dimension in the y direction
	 */
	public int getDimensiony(){
		return this.dimensiony;
	}
	/**
	 * returns the dimension in the z direction
	 */
	public int getDimensionz(){
		return this.dimensionz;
	}
	/**
	 * returns the units of the given faction
	 */
	public Set<Unit> getUnitsOfFaction(Faction faction){
		return faction.getUnits();
	}
	
	/**
	 * returns a set of all the units in this world
	 */
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
	/**
	 * Changes the type of the cube on the given coordinates to the given value
	 * if the type stays the same, nothing happens
	 * if the type changes from solid to passable, we first check if there were objects dependent of this cube
	 * 	next we notify the connected to border that something has changed
	 * 	next we throw a warning arround towards the surrounding cubes in this world 
	 * 	we also change the surrounding cubes to not walkable if needed
	 * else if the type changes from passable to solid
	 * 		if the cube was ocuppied by an object nothing happens, we ignore the given command
	 * 		else the connectedToBorder is notified, then we check if the cube needs to fall if that isn't the case we set the surrounding cubes 
	 * 		to walkable 
	 * 		
	 * 		
	 */
	public void changeCubeType(int x, int y, int z, int value) throws UnitException{	
		TerrainType oldTerrainType = this.getCube(x,y,z).getTerrainType();
		this.getCube(x, y, z).setTerrainType(value);
		if (this.getCube(x,y,z).getTerrainType() != oldTerrainType){
			if (changedFromSolidToPassable(x,y,z,oldTerrainType)){
				doSomethingIfObjectIsDependent(x,y,z);
				connectedToBorder.changeSolidToPassable(x, y, z);
				throwWarningAround(x,y,z);
				changeWalkable(x,y,z);
				}
			else if  (changedFromPassableToSolid(x, y, z, oldTerrainType)){
				//TODO: ignore or throw exception?
				if (!isOccupied(x,y,z)){
					connectedToBorder.changePassableToSolid(x, y, z);
					cubesToCheck.add(new int[]{x,y,z});
					this.setSurroundingCubesToWalkable(x, y, z);
					}
				//i know tis lelijk ma ja
				else {
					this.getCube(x, y, z).setTerrainType(oldTerrainType);
				}
				
				
			
			}
			terrainChangeListener.notifyTerrainChanged(x, y, z);

		}
		
	}
	/**
	 * returns true if the cube with the given coordinates is occupied by an Hillbilieobject
	 */
	private boolean isOccupied(int x, int y, int z){
		return this.getCube(x, y, z).getObjectsOnThisCube().size()>0;
		}
	
	/**
	 * changes the surrounding cubes to not walkable if needed
	 */
	private void changeWalkable(int x,int y,int z){
		int[] pos = new int[]{-1,0,1};
		for (int xpos: pos){
			for (int ypos: pos){
				for (int zpos: pos){
					this.getCube(xpos, ypos, zpos).setWalkable(isWalkable(xpos,ypos,zpos));					
				}
			}
		
		}
		
	}

	/**
	 * returns true if the cube with the given coordinates has changes from solid to passable (compared to the given old terrain type )
	 */
	private boolean changedFromSolidToPassable(int x, int y, int z,TerrainType oldTerrainType){
		return (this.getCube(x, y, z).isPassable() && (oldTerrainType == TerrainType.ROCK || oldTerrainType == TerrainType.TREE));
	}
	//TODO: hoe zit het hier met workshop ? 
	/**
	 * returns true if the cube with the given coordinates has changes from passable to solid (compared to the given old terrain type )
	 */
	private boolean changedFromPassableToSolid(int x, int y, int z,TerrainType oldTerrainType ){
		return (!this.getCube(x, y, z).isPassable() && (oldTerrainType == TerrainType.AIR  ));
	}
	
	//Misschien niet bij unit?
	private void doSomethingIfObjectIsDependent(int x, int y, int z) {
		// TODO aanvullen
		for (HillbilliesObject obj :this.getCube(x, y, z+1).getObjects()){
			
		}
		
		
	}
	
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
	
	/**
	 * the cubes to check are checked and they cave in if they are not connected to the worder
	 */
	private void updateCubes() throws WorldException{
		ArrayList<int[]> cubesToCaveIn = new ArrayList<int[]>();
		for (int[] p: cubesToCheck){
			if (!connectedToBorder.isSolidConnectedToBorder(p[0],p[1],p[2]) && (!this.getCube(p[0],p[1],p[2]).isPassable())){
				cubesToCaveIn.add(p);
			}
		}
		this.cubesToCheck =new ArrayList<int[]>();
		caveIn(cubesToCaveIn);
		
	}
	/**
	 * resets the type of the given cubes to air and possibly adds a log or a boulder
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
	/**
	 * adds a log or a boulder with probability 0.25
	 */
	private void maybeAddLogOrBoulder(int[] p, TerrainType type) throws WorldException{
		boolean succeeded = random.nextFloat()<0.25;
		if ( succeeded && type == TerrainType.TREE){
			addLog(p);
		}
		else if (succeeded){
			addBoulder(p);
		}
		
	}
	
	public void addLog(int[] p) throws WorldException{
		//TODO: wat met vallen
		Position pos = new Position(p[0]+lc/2,p[1]+lc/2,p[2]+lc/2);
		Log newLog = new Log(pos,this);
		logs.add(newLog);
		
	}
	public void addBoulder(int[] p ) throws WorldException{
		//wat met vallen
		Position pos = new Position(p[0]+lc/2,p[1]+lc/2,p[2]+lc/2);
		Boulder newBoulder = new Boulder(pos,this);
		boulders.add(newBoulder);
	}
	
	//TODO: Wat denkt gij, houden we eerder een lijst bij van de logs waarvoor effectief iets veranderd is ? 
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
	
	
	/**
	 * returns true if the cube at the given position is walkable
	 */
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
			int x = random.nextInt(this.getDimensionx()-1);
			int y = random.nextInt(this.getDimensiony()-1);
			int z = random.nextInt(this.getDimensionz()-1);
			int looper = z;
			while ((!this.getCube(x, y, looper).isPassable() || (looper != 0 && this.getCube(x, y, looper-1).isPassable())) && looper !=z-1){
				looper+=1;
				looper %= dimensionz-1;
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
	
	private static void assignFaction(Unit unit) throws UnitException{
		for (int i = 4;i>0; i--){
			if (factions.get(i).getUnits().size()<factions.get(i-1).getUnits().size() && factions.get(i).getUnits().size()<50){
				factions.get(i).addUnit(unit);
				unit.setFaction(factions.get(i));
				return;
			}
		}
		if (factions.get(0).getUnits().size()<50){
			factions.get(0).addUnit(unit);
			unit.setFaction(factions.get(0));}
		
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
	public void addUnit(Unit unit) throws UnitException{
		if (!(countUnits()>=100)){
			assignFaction(unit);
			unit.setWorld(this);
			}
	}
	//TODO: are they still alive ? do i need to check it
	private int countUnits(){
		return this.getUnits().size();
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
	
	public boolean dropLoad(Load load,Position workPosition){
		if (workPosition.getCube().isPassable()){
			if (load instanceof Log){
			logs.add((Log) load);
			}
			else if (load instanceof Boulder){
				boulders.add((Boulder) load);
			}
			load.setParentCube(workPosition, this);
			return true;		}
	
		
	  return false;
		
	}
	
	public void collapseCube( Position position) throws WorldException{
		//ADD LOG OR BOULDER
		//START CAVE IN PROCES
		TerrainType oldTerrainType = position.getCube().getTerrainType();
		changeCubeType(position.getCubexpos(),position.getCubeypos(),position.getCubezpos(),0);
		if (oldTerrainType == TerrainType.TREE){
			addLog(new int [] {position.getCubexpos(),position.getCubeypos(),position.getCubezpos()});
			terrainChangeListener.notifyTerrainChanged(position.getCubexpos(), position.getCubeypos(), position.getCubezpos());
			
					}
		else if (oldTerrainType== TerrainType.ROCK){
			addBoulder(new int [] {position.getCubexpos(),position.getCubeypos(),position.getCubezpos()});
			terrainChangeListener.notifyTerrainChanged(position.getCubexpos(), position.getCubeypos(), position.getCubezpos());

		}
		
		
		
	}
	
	

}