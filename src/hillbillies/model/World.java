package hillbillies.model;
import java.util.Map;
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
	private Cube[][][] world;
	private ConnectedToBorder connectedToBorder;
	private final TerrainChangeListener terrainChangeListener;
	private ArrayList<Boulder> boulders = new ArrayList<>();
	private ArrayList<Log> logs = new ArrayList<>();
	private Map<int[],HillbilliesObject[]> objectsInMap = new HashMap();
	private ArrayList<Faction> factions = new ArrayList<>();
	private ArrayList<int[]> changedCubes = new ArrayList<int[]>();
	private int cubesToJump;
	final static double maxDT = 0.2;
	final static int secondsToCaveIn = 5;
	
	
	
	public World(int[][][] terrainTypes, TerrainChangeListener modelListener) throws WorldException{
		dimension= terrainTypes.length;
		world = new Cube[dimension][dimension][dimension];
		connectedToBorder = new ConnectedToBorder(dimension,dimension,dimension);
		for (int x=0;x<dimension;x++){
			for (int y=0;y<dimension;y++){
				for (int z=0;z<dimension;z++){
					this.setCube(x,y,z,new Cube(terrainTypes[x][y][z]));
					
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
	
	public void changeCubeType(int x, int y, int z, int value) throws UnitException{
		this.getCube(x, y, z).setTerrainType(value);
		terrainChangeListener.notifyTerrainChanged(x, y, z);
		if (this.getCube(x, y, z).isPassable()){
			connectedToBorder.changeSolidToPassable(x, y, z);
		}
		throwWarningAround(x,y,z);
		
	}
	/**
	 * notify's the neighbouring cubes.
	 */
	private void throwWarningAround(int xpos,int ypos,int zpos) throws UnitException{
		for (int x = xpos -this.cubesToJump;x<=xpos+this.cubesToJump;x++){
			for (int y = ypos -this.cubesToJump;y<=ypos+ this.cubesToJump;y++){
				for (int z =zpos -this.cubesToJump;z<=zpos + this.cubesToJump;z++){
					if (!world[x][y][z].isPassable()){
						//the passable cubes are added to the possible changed 
						changedCubes.add(new int[]{x,y,z});
					}
				}
			}
		}
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
	
	private void updateCubes(){
		ArrayList<int[]> cubesToCaveIn = new ArrayList<int[]>();
		for (int[] p: changedCubes){
			if (!connectedToBorder.isSolidConnectedToBorder(p[0],p[1],p[2])){
				cubesToCaveIn.add(p);
			}
		}
		this.changedCubes =new ArrayList<int[]>();
	}
	/**
	 * resets the type of the given cubes to air and possibly adds a log
	 * @param cubesToCaveIn
	 */
	private void caveIn(ArrayList<int[]> cubesToCaveIn){
		for (int[] p: cubesToCaveIn){
			 Cube cubeToReplace = getCube(p[0],p[1],p[2]);
			 setCube(p[0],p[1],p[2],new Cube(TerrainType.AIR));
			 maybeAddLogOrBoulder(p, getCube(p[0],p[1],p[2]).getTerrainType ());
			
		}
	}
	
	private void maybeAddLogOrBoulder(int[] p, TerrainType type){
		//TODO: add a log or boulder with probability 0.25
		if (type == TerrainType.TREE){
			
		}
		else {
		
		}
		
	}
	
	public void advanceTime(double dt) throws UnitException{
		updateCubes();
		for (HillbilliesObject[] objs : objectsInMap.values()){
			for (HillbilliesObject obj : objs){
				obj.advanceTime(dt);
			}
		}
	}
}
	
	
	

