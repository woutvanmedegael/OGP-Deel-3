package hillbillies.model.world;
import java.util.Random;
import java.util.Set;

import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.Boulder;
import hillbillies.model.hillbilliesobject.HillbilliesObject;
import hillbillies.model.hillbilliesobject.Load;
import hillbillies.model.hillbilliesobject.Log;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.hillbilliesobject.unit.UnitException;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
/**
 * 
 * @author Wout Van Medegael & Adriaan Van Gerven
 *
 */

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
	/**
	 * A list to keep track of the different cubes in the world
	 */
	private Cube[][][] world;
	/**
	 * variable of the ConnectedToBorder class used for checking if cubes are connected to the border
	 */
	private ConnectedToBorder connectedToBorder;
	/**
	 * returns true if the cube with the given coordinates is solid and connected to the border
	 */
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
	 * initialize the world; creating and configuring the connected to border,
	 * setting all the walkable cubes
	 */
	public World(int[][][] terrainTypes, TerrainChangeListener modelListener) throws WorldException{
		ArrayList<ArrayList<ArrayList<Integer>>> myworld = new ArrayList<>();
		
		dimensionx = terrainTypes.length;
		dimensiony = terrainTypes[1].length;
		dimensionz = terrainTypes[0][1].length;
		for (int x=0;x<dimensionx;x++){
			for (int y=0;y<dimensiony;y++){
				System.out.println(Arrays.toString(terrainTypes[x][y]));
				}
			}
		
		
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
	 * Sets all the cubes that are walkable in world to walkable.
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
						if(Position.posWithinWorld(x+xpos, y+ypos, z+zpos,this)&& getCube(x+xpos,y+ypos,z+zpos).isPassable()){
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
	public void changeCubeType(int x, int y, int z, int value) throws WorldException{	
		TerrainType oldTerrainType = this.getCube(x,y,z).getTerrainType();
		this.getCube(x, y, z).setTerrainType(value);
		if (this.getCube(x,y,z).getTerrainType() != oldTerrainType){
			if (changedFromSolidToPassable(x,y,z,oldTerrainType)){
				connectedToBorder.changeSolidToPassable(x, y, z);
				throwWarningAround(x,y,z);
				changeWalkable(x,y,z);
				doSomethingIfObjectIsDependent(x,y,z);
				}
			else if  (changedFromPassableToSolid(x, y, z, oldTerrainType)){
				if (!isOccupied(x,y,z)){
					connectedToBorder.changePassableToSolid(x, y, z);
					cubesToCheck.add(new int[]{x,y,z});
					this.setSurroundingCubesToWalkable(x, y, z);
					}
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
					if (Position.posWithinWorld(x+xpos, x+ ypos, z+zpos, this)){
					this.getCube(x+xpos, y+ypos, z+zpos).setWalkable(isWalkable(x+xpos,y+ypos,z+zpos));	}				
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
	/**
	 * makes the  hillbillie object fall if it was somehow dependent of this cube.
	 */
	private void doSomethingIfObjectIsDependent(int x, int y, int z) throws UnitException,WorldException {
		// TODO aanvullen
		for (int xpos = x-1; xpos<=x+1;xpos++){
			for (int ypos = y-1; ypos<=y+1;ypos++){
				for (int zpos = z-1; zpos<=z+1;zpos++){
					if (Position.posWithinWorld(xpos, ypos, zpos, this) && this.getCube(xpos,ypos,zpos).isPassable()){
						for (HillbilliesObject o : this.getCube(xpos, ypos, zpos).getObjects()){
							if (o instanceof Unit && !this.getCube(xpos, ypos, zpos).isWalkable()){
								Unit u = (Unit) o;
								u.startFalling();
							} else if (o instanceof Load && xpos==x && ypos==y && zpos==z+1){
								Load l = (Load) o;
								l.startFalling();
							}
						}
					}
				}
			}
		}
		
		
	}
	
	/**
	 * notify's the neighbouring cubes.
	 */
	private void throwWarningAround(int xpos,int ypos,int zpos) throws UnitException{
		for (int x = xpos -this.cubesToJump;x<=xpos+this.cubesToJump;x++){
			for (int y = ypos -this.cubesToJump;y<=ypos+ this.cubesToJump;y++){
				for (int z =zpos -this.cubesToJump;z<=zpos + this.cubesToJump;z++){
					if (Position.posWithinWorld(x, y, z, this) && !world[x][y][z].isPassable()){
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
	 */
	private void caveIn(ArrayList<int[]> cubesToCaveIn) throws WorldException{
		if (cubesToCaveIn.size()>0){
		}
		for (int[] p: cubesToCaveIn){
			 changeCubeType(p[0],p[1],p[2],0);
			 maybeAddLogOrBoulder(p, getCube(p[0],p[1],p[2]).getTerrainType ());
			
		}
		
	}
	/**
	 * variable used te generate random numbers
	 */
	private final Random random = new Random();
	/**
	 * adds a log or a boulder with probability 0.25
	 */
	private void maybeAddLogOrBoulder(int[] p, TerrainType type) throws WorldException{
		boolean succeeded = random.nextFloat()<10000;
		if ( succeeded && type == TerrainType.TREE){
			addLog(p);
		}
		else if (succeeded){
			addBoulder(p);
		}
		
	}
	/**
	 * adds a log to the middle of the cube with given coordinates.
	 * If the log is not above solid terrain, the log starts falling.
	 */
	private void addLog(int[] p) throws WorldException{
		//TODO: wat met vallen
		Position pos = new Position(p[0]+lc/2,p[1]+lc/2,p[2]+lc/2,this);
		Log newLog = new Log(pos,this);
		if (this.getCube(p[0], p[1], p[2]-1).isPassable()){
			newLog.startFalling();
		}
		logs.add(newLog);
		
	}
	/**
	 * adds a boulder to the middle of the cube with given coordinates.
	 * If the boulder is not above solid terrain, the boulder starts falling.
	 */
	private void addBoulder(int[] p ) throws WorldException{
		Position pos = new Position(p[0]+lc/2,p[1]+lc/2,p[2]+lc/2,this);
		Boulder newBoulder = new Boulder(pos,this);
		if (p[2]!=0 && this.getCube(p[0], p[1], p[2]-1).isPassable()){
			newBoulder.startFalling();
		}
		boulders.add(newBoulder);
	}
	/**
	 *advances the time for each object in this world
	 */
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
	 * returns true if the cube at the given position is walkable.
	 */
	public boolean isWalkable(int xpos, int ypos, int zpos){
		
		if (!this.getCube(xpos, ypos, zpos).isPassable()){
			return false;
		}
		
		if (xpos==0 || ypos==0 || zpos==0){
			return true;
		}
		
		for (int x = -1;x<=1;x++){
			for (int y = -1;y<=1;y++){
				for (int z = -1;z<=1;z++){
					if (Position.posWithinWorld(xpos+x, ypos+y, zpos+z, this) && !this.getCube(xpos+x,ypos+y,zpos+z).isPassable()){
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
	/**
	 * returns a random unit with random attributes and a random position with the given enabledDefaultBehavior;
	 */
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
			if (!this.getCube(x, y, looper).isPassable() || (looper!=0 && this.getCube(x, y, looper-1).isPassable())){
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
	/**
	 * assigns the smallest faction to the given unit
	 */
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
	/**
	 * creates a totally random name starting with a capital.
	 */
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
	/**
	 * if the world doesn't contain more than 100 units the given unit is added to the world. 
	 */
	public void addUnit(Unit unit) throws UnitException{
		if (!(countUnits()>=100)){
			assignFaction(unit);
			unit.setWorld(this);
			}
	}
	/**
	 * counts all the units in this world.
	 */
	private int countUnits(){
		return this.getUnits().size();
	}
	
	/**
	 * returns a set of the active factions, the factions that have at least 1 unit
	 */
	public Set getActiveFactions(){
		Set<Faction> activeFactions = new HashSet<>();
		for (Faction f: this.getFactions()){
			if (f.getUnits().size()>0){
				activeFactions.add(f);
			}
		}
		return activeFactions;
		
	}
	/**
	 * 
	 */
	public boolean dropLoad(Load load,Position workPosition) throws WorldException{
		if (workPosition.getCube().isPassable()){
			workPosition.setToMiddleOfCube();
			load.setPosition(workPosition);
			if (load instanceof Log){
			logs.add((Log) load);
			}
			else if (load instanceof Boulder){
				boulders.add((Boulder) load);
			}
			load.setParentCube(workPosition, this);
			//workPosition.incrPosition(0, 0, -1);
			Position pos = new Position(workPosition.xpos,workPosition.ypos,workPosition.zpos-1,this);
			if (pos.getCube().isPassable()){
				load.startFalling();
			}
			return true;		}
	
		
	  return false;
		
	}
	
	public void collapseCube( Position position) throws WorldException{
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
	
	public void removeLog(Log log){
		this.logs.remove(log);
	}
	
	public void removeBoulder(Boulder boulder){
		this.boulders.remove(boulder);
	}

	
	
	

}