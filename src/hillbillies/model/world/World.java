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
import java.util.HashSet;
/**
 * 
 * @author Wout Van Medegael & Adriaan Van Gerven
 *
 */
public class World {
	/**
	 * Variables registering the dimensions of the game world.
	 */
	private final int dimensionx;
	private final int dimensiony;
	private final int dimensionz;
	/**
	 * Variable registering the length of one cube.
	 */
	private final double lc = 1;
	/**
	 * A three dimensional list to keep track of the different cubes in the world.
	 */
	private Cube[][][] world;
	/**
	 * Variable of the ConnectedToBorder class used for checking if cubes are connected to the border.
	 */
	private final ConnectedToBorder connectedToBorder;
	/**
	 * Returns true if the cube with the given coordinates is solid and connected to the border.
	 */
	public boolean isSolidConnectedToBorder(int x,int y,int z){
		return ((!this.getCube(x, y, z).isPassable())&& this.connectedToBorder.isSolidConnectedToBorder(x, y, z));
	}
	/**
	 * A terrainChangeListener which is notified each time the terrain changes.
	 */
	private final TerrainChangeListener terrainChangeListener;
	/**
	 * A set containing all the different boulders in this world.
	 */
	private Set<Boulder> boulders = new HashSet<>();
	
	/**
	 * A set containing all the different logs in this world.
	 */
	private Set<Log> logs = new HashSet<>();
	
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
	 * The longest possible time step.
	 */
	final static double maxDT = 0.2;
	/**
	 * The longest period that it may take untill all the cubes have caved in.
	 */
	final static int secondsToCaveIn = 5;
	
	
	/**
	 * Initialize the world: 
	 * - create and fill in the 3 dimensional cube list
	 * - configure the connected to border
	 * - Setting all the cubes that are walkable to walkable
	 * - calculate the number of cubes to jump in each time step when caving in in each time step
	 * - checks if the there are cubes that need to cave in
	 */
	public World(int[][][] terrainTypes, TerrainChangeListener modelListener) throws WorldException{
		
		this.dimensionx = terrainTypes.length;
		this.dimensiony = terrainTypes[1].length;
		this.dimensionz = terrainTypes[0][1].length;
		this.world = new Cube[dimensionx][dimensiony][dimensionz];
		this.connectedToBorder = new ConnectedToBorder(this.dimensionx,dimensiony,dimensionz);
		for (int x=0;x<dimensionx;x++){
			for (int y=0;y<dimensiony;y++){
				for (int z=0;z<dimensionz;z++){
					world[x][y][z] = new Cube(terrainTypes[x][y][z]);
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
		caveInFirstTime();
		}
	
	/**
	 * Function that runs trough all the cubes in this world. If the cube is a solid cube the function checks if
	 * this cube is connected to the border. If that's not the case the cube caves in: 
	 * -the terrain type is change
	 * -a log or boulder is possible added
	 * -the surrounding cubes are changed to not walkable if needed
	 */
	private void caveInFirstTime() throws WorldException{
		for (int x= 0; x<this.dimensionx;x++){
			for (int y=0;y<this.dimensiony;y++ ){
				for (int z=0;z<this.dimensionz;z++){
					if (!this.getCube(x, y, z).isPassable() && !connectedToBorder.isSolidConnectedToBorder(x, y, z)){
						TerrainType old = this.getCube(x, y, z).getTerrainType();
						this.getCube(x, y, z).setTerrainType(0);
						terrainChangeListener.notifyTerrainChanged(x, y, z);
						maybeAddLogOrBoulder(new int[]{x,y,z},old);
						changeWalkable(x,y,z);
					}
				}
			}
		}
	}
	/**
	 * Sets all the cubes that are walkable in world to walkable.
	 */
	private void fixWalkableCubes() {
		for (int x=0;x<dimensionx;x++){
			for (int y=0;y<dimensiony;y++){
				for (int z=0;z<dimensionz;z++){
					setSurroundingCubesToWalkable(x, y, z);	
					if (z==0){
						if (getCube(x,y,z).isPassable()){
							getCube(x,y,z).setWalkable(true);
						}
					}
				}
			}
		}
	}
	/**
     * If the given cube is a solid, the passable cubes around it are set to walkable.
	 */
	private void setSurroundingCubesToWalkable(int x, int y, int z) {
		
		if (!getCube(x,y,z).isPassable()){
			getCube(x,y,z).setWalkable(false);
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
	 *Returns the cube at the given position.
	 */
	public Cube getCube(int x,int y,int z){
		return this.world[x][y][z];
	}
	
	/**
	 * Returns an arraylist of the factions in this world.
	 */
	public static ArrayList<Faction> getFactions(){
		return World.factions;
	}
	/**
	 * Returns a set of all the boulders in this world.
	 */
	public Set<Boulder> getBoulders(){
		return this.boulders;
	}
	
	/**
	 * Returns a set of all the logs in this world.
	 */
	public Set<Log> getLogs(){
		return logs;
	}
	/**
	 * Returns the dimension in the x direction.
	 */
	public int getDimensionx(){
		return this.dimensionx;
	}
	/**
	 * Returns the dimension in the y direction.
	 */
	public int getDimensiony(){
		return this.dimensiony;
	}
	/**
	 * Returns the dimension in the z direction.
	 */
	public int getDimensionz(){
		return this.dimensionz;
	}
	/**
	 * Returns the units of the given faction.
	 */
	public Set<Unit> getUnitsOfFaction(Faction faction){
		return faction.getUnits();
	}
	
	/**
	 * Returns a set of all the units in this world.
	 */
	public Set<Unit> getUnits(){
		Set<Unit> units = new HashSet<>();
		for (Faction f : factions){
			units.addAll(f.getUnits());
		}
		return units;
	}
	
	/**
	 * Changes the type of the cube on the given coordinates to the given value.
	 * If the type stays the same, nothing happens.
	 * 	If the type changes from solid to passable, the function notifies the connected to border, 
	 * 		throws a warning to the neighbouring cubes, possibly changes the status of some neighbouring cubes to not walkable, 
	 * 		lets the objects that were currently occupying that cube know that it's type has changed.
	 * 	Else if the type changes from passable to solid and the cube is not occupied by an hillbillie object, the connected to border is notified 
	 * 		and the cube is added to cubesToCheck, the surrounding cubes are set to walkable.
	 * 	In either case the terrain has changes and thus the terrainChangeListener is notified.
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
				
				
				terrainChangeListener.notifyTerrainChanged(x, y, z);
			}
		}
		
	}
	/**
	 * Returns true if the cube with the given coordinates is occupied by an Hillbilie object.
	 */
	private boolean isOccupied(int x, int y, int z){
		return this.getCube(x, y, z).getObjectsOnThisCube().size()>0;
		}
	
	/**
	 * Changes the surrounding cubes to not walkable if needed.
	 */
	private void changeWalkable(int x,int y,int z){
		int[] pos = new int[]{-1,0,1};
		for (int xpos: pos){
			for (int ypos: pos){
				for (int zpos: pos){
					
					if (Position.posWithinWorld(x+xpos, y+ ypos, z+zpos, this)){
						this.getCube(x+xpos, y+ypos, z+zpos).setWalkable(isWalkable(x+xpos,y+ypos,z+zpos));	
						}
				}
			}
		}
	}
	/**
	 * Returns true if the cube with the given coordinates has changes from solid to passable (compared to the given old terrain type ).
	 */
	private boolean changedFromSolidToPassable(int x, int y, int z,TerrainType oldTerrainType){
		return (this.getCube(x, y, z).isPassable() && (oldTerrainType == TerrainType.ROCK || oldTerrainType == TerrainType.TREE));
	}
	/**
	 * Returns true if the cube with the given coordinates has changed from passable to solid (compared to the given old terrain type ).
	 */
	private boolean changedFromPassableToSolid(int x, int y, int z,TerrainType oldTerrainType ){
		return (!this.getCube(x, y, z).isPassable() && (oldTerrainType == TerrainType.AIR  ));
	}
	
	/**
	 * Makes the  hillbillie object fall if it was somehow dependent of this cube.
	 */
	private void doSomethingIfObjectIsDependent(int x, int y, int z) throws UnitException,WorldException {
		for (int xpos = x-1; xpos<=x+1;xpos++){
			for (int ypos = y-1; ypos<=y+1;ypos++){
				for (int zpos = z-1; zpos<=z+1;zpos++){
					if (Position.posWithinWorld(xpos, ypos, zpos, this) && this.getCube(xpos,ypos,zpos).isPassable()){
						for (HillbilliesObject o : this.getCube(xpos, ypos, zpos).getObjectsOnThisCube()){
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
	 * The cubes neighboring the given positions are added to the cubes to check if they are solid.
	 */
	private void throwWarningAround(int xpos,int ypos,int zpos) throws UnitException{
		for (int x = xpos -this.cubesToJump;x<=xpos+this.cubesToJump;x++){
			for (int y = ypos -this.cubesToJump;y<=ypos+ this.cubesToJump;y++){
				for (int z =zpos -this.cubesToJump;z<=zpos + this.cubesToJump;z++){
					if (Position.posWithinWorld(x, y, z, this) && !world[x][y][z].isPassable()){
						cubesToCheck.add(new int[]{x,y,z});
					}
				}
			}
		}
	}
	
	/**
	 * If the cubes in cubes to check are solid and not connected to the border they cave in.
	 */
	private void updateCubes() throws WorldException{
		ArrayList<int[]> cubesToCaveIn = new ArrayList<int[]>();
		for (int[] p: cubesToCheck){
			if ( (!this.getCube(p[0],p[1],p[2]).isPassable()) && !connectedToBorder.isSolidConnectedToBorder(p[0],p[1],p[2]) ){
				cubesToCaveIn.add(p);
			}
		}
		this.cubesToCheck =new ArrayList<int[]>();
		caveIn(cubesToCaveIn);
		
	}
	/**
	 * Changes the cube type of the given cubes to air and maybe adds a log or boulder.
	 */
	private void caveIn(ArrayList<int[]> cubesToCaveIn) throws WorldException{
		for (int[] p: cubesToCaveIn){
			 changeCubeType(p[0],p[1],p[2],0);
			 maybeAddLogOrBoulder(p, getCube(p[0],p[1],p[2]).getTerrainType ());
			
		}
		
	}
	/**
	 * Variable used to generate random numbers.
	 */
	private final Random random = new Random();
	/**
	 * Adds a log or a boulder with probability 0.25.
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
	/**
	 * Adds a log to the middle of the cube with given coordinates.
	 * If the log is not above solid terrain, the log starts falling.
	 */
	private void addLog(int[] p) throws WorldException{
		Position pos = new Position(p[0]+lc/2,p[1]+lc/2,p[2]+lc/2,this);
		Log newLog = new Log(pos,this);
		if (p[2]!=0 && this.getCube(p[0], p[1], p[2]-1).isPassable()){
			newLog.startFalling();
		}
		logs.add(newLog);
		
	}
	/**
	 * Adds a boulder to the middle of the cube with given coordinates.
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
	 *Advances the time for each hillbillie object in this world.
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
	 * Returns true if the cube at the given position is walkable.
	 * Returns false if the cube is solid. Returns true if the unit is at the ground level.
	 * Returns true if there is a neighboring solid cube.
	 */
	public boolean isWalkable(int xpos, int ypos, int zpos){
		if (!this.getCube(xpos, ypos, zpos).isPassable()){
			return false;
		}
		
		if (xpos==0 ){
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
	
	final static String alphabet = "abcdefghijklmnopqrstuvxyzABCDEFHIJKLMNOPQRSTUVWXYZ '\"";
	final static String ALPHABET = "ABCDEFHIJKLMNOPQRSTUVWXYZ";
	/**
	 * Returns a random unit with random attributes and a random position with the given enabledDefaultBehavior. 
	 * If the total number of units is smaller than 100 the unit is added to the world.
	 * @throws WorldException 
	 */
	public Unit spawnUnit(boolean enableDefaultBehavior) throws WorldException{
//			enableDefaultBehavior = false;
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
			if (!(countUnits()>=100)){
			unit.setWorld(this);
			assignFaction(unit);
			}
			return unit;
	}
	/**
	 * Assigns the smallest faction to the given unit.
	 * @throws WorldException 
	 */
	private static void assignFaction(Unit unit) throws WorldException{
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
	 * Creates a totally random name starting with a capital.
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
	 * If the world doesn't contain more than 100 units the given unit is added to the world. 
	 * Else this request is silently rejected (nothing happens).
	 * @throws WorldException 
	 */
	public void addUnit(Unit unit) throws WorldException{
		if (!(countUnits()>=100)){
			assignFaction(unit);
			unit.setWorld(this);
			}
	}
	/**
	 * Counts all the units in this world.
	 */
	private int countUnits(){
		return this.getUnits().size();
	}
	
	/**
	 * Returns a set of the active factions, the factions that have at least 1 unit.
	 */
	public Set<Faction> getActiveFactions(){
		Set<Faction> activeFactions = new HashSet<>();
		for (Faction f: World.getFactions()){
			if (f.getUnits().size()>0){
				activeFactions.add(f);
			}
		}
		return activeFactions;
		
	}
	/**
	 * Tries to Drops the given load at the given work position, returns true if the dropping succeeded.
	 * If the workposition is passable terrain, the load is added to the world on the middle of the cube.
	 * 	If the underlying cube is passable terrain the load starts falling.
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
			if (Position.isValidPos(workPosition.getxpos(), workPosition.getypos(), workPosition.getzpos()-1, this)){
				Position pos = new Position(workPosition.getxpos(),workPosition.getypos(),workPosition.getzpos()-1,this);
				if (pos.getCube().isPassable()){
					load.startFalling();
				}
			}
			return true;		}
	  return false;
		
	}
	/**
	 * Collapses the cube at the given position leaving a log if the oldTerrainType was TREE and a boulder if it was ROCK.
	 * The log the terrainChangeListener is notified.
	 */
	public void collapseCube( Position position) throws WorldException{
		TerrainType oldTerrainType = this.getCube(position.getCubexpos(),position.getCubeypos(),position.getCubezpos()).getTerrainType();
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
	/**
	 * Removes the given log from the log list.
	 */
	public void removeLog(Log log){
		this.logs.remove(log);
	}
	/**
	 * Removes the given boulder from the boulder list.
	 */
	public void removeBoulder(Boulder boulder){
		this.boulders.remove(boulder);
	}
	
	
	
}