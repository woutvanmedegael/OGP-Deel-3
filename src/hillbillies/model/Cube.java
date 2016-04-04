package hillbillies.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public class Cube {
/**
 * arrayList used to keep track of the objects on this cube
 */
private ArrayList<HillbilliesObject> objectsOnThisCube = new ArrayList<>();
/**
 * set containing the units currently passing this cube
 */
private Set<Unit> passingUnits = new HashSet<>();
/**
 * TODO
 */
private Map<PathFinding,Position> passingPaths = new HashMap<>();
/**
 * the world of this cube
 */
private final World world;
/**
 * A map used to easily get the int of the terrainType 
 */
private static final Map<TerrainType,Integer> terrainTypeToIntegerMapper = new HashMap();

static
{
	terrainTypeToIntegerMapper.put(TerrainType.AIR, 0);
	terrainTypeToIntegerMapper.put(TerrainType.ROCK, 1);
	terrainTypeToIntegerMapper.put(TerrainType.TREE, 2);
	terrainTypeToIntegerMapper.put(TerrainType.WORKSHOP, 3);
}
/**
 * Initialize this new cube with given terrainType as an int.
 */
public Cube(int terrainNumber,World world)  {
	this.world = world;
	this.setTerrainType(terrainNumber);
}
/**
 * Initialize this new cube with given terrainType as a terraintype.
 */
public Cube (TerrainType type,World world){
	this.world = world;
	this.terrainType = type;
}

/**
 * Return the terrainType of this cube.
 */
@Basic @Raw
public TerrainType getTerrainType() {
	return this.terrainType;
}
/**
 * returns the int of the current terrain type of this cube
 */
public int getIntTerrainType(){
	return terrainTypeToIntegerMapper.get(this.getTerrainType());	
}	


/**
 * Check whether the given terrainType is a valid terrainType for
 * any cube.
*/
public static boolean isValidTerrainType(TerrainType terrainType) {
	return (terrainType != null);
}

/**
 * Set the terrainType of this cube to the given terrainType.
 */
@Raw
public void setTerrainType(TerrainType terrainType) {
	if (isValidTerrainType(terrainType))
		this.terrainType = terrainType;
	else this.terrainType = TerrainType.AIR;
}
//TODO dit is totaal geprogrammeerd
/**
 * sets the terraintype of this cube to the given terrain type.
 */
public void setTerrainType(int terraintype){
	switch (terraintype){
	case 0:
		terrainType = TerrainType.AIR;
		break;
	case 1:
		terrainType = TerrainType.ROCK;
		break;
	case 2:
		terrainType = TerrainType.TREE;
		break;
	case 3:
		terrainType = TerrainType.WORKSHOP;
		break;
	default:
		terrainType = TerrainType.AIR;		
	}
}
/**
 *  returns true is the cube is passable, if the terraintype is air or workshop the cube is passable
 */
public boolean isPassable(){
	return (this.getTerrainType()==TerrainType.AIR ||this.getTerrainType()==TerrainType.WORKSHOP);
}
/**
 * Variable registering the terrainType of this cube.
 */
private TerrainType terrainType;

/**
 * adds the given object on this cube
 */
public void addObject(HillbilliesObject obj){
	this.objectsOnThisCube.add(obj);
}

/**
 * deletes the object from this cube
 * @param obj
 */
public void deleteObject(HillbilliesObject obj){
	this.objectsOnThisCube.remove(obj);
}
/**
 * returns all the objects on this cube
 */
public ArrayList<HillbilliesObject> getObjects(){
	return this.objectsOnThisCube;
}
/**
 * adds a unit that is currently passing this cube. 
 */
public void addPassingUnit(Unit unit){
	this.passingUnits.add(unit);
}



//public void warnPassingUnits(){
//	for (Unit unit : this.passingUnits){
//		unit.warnCubeHasChanged(this);
//		}
//	}


/**
 * returns the objects that are currentely occupying this cube
 */
public ArrayList<HillbilliesObject> getObjectsOnThisCube(){
	return objectsOnThisCube;
}
/**
 * a boolean registering wheter or not the cube is walkable, initialized at false
 */
private boolean walkable = false;
/**
 * sets walkable to the given bool
 */
public void setWalkable(boolean bool){
	this.walkable = bool;
}
/**
 * returns whether or not the cube is walkable
 */
public boolean isWalkable(){
	return this.walkable;
}
/**
 * returns true if this cube contains a boulder
 */
public boolean containsBoulder(){
	for (HillbilliesObject b: this.getObjectsOnThisCube()){
		if (b instanceof Boulder){
			return true;
		}
	}
	return false;
}
/**
 * returns true if this cube contains a log
 */
public boolean containsLog(){
	for (HillbilliesObject l: this.getObjectsOnThisCube()){
		if (l instanceof Log){
			return true;
		}
	}
	return false;
}
/**
 * returns a log that was occupying this cube, if no log was occupying this cube an exception is thrown.
 */
public Log getALog() throws WorldException{
	for (HillbilliesObject l: this.getObjectsOnThisCube()){
		if (l instanceof Log){
			return (Log) l;
		}
	}
	throw new WorldException();
	
	
}
/**
 * returns a boulder that was occupying this cube, if no boulder was occupying this cube an exception is thrown.
 */
public Boulder getABoulder() throws UnitException{
	for (HillbilliesObject b: this.getObjectsOnThisCube()){
		if (b instanceof Boulder){
			return (Boulder) b;
		}
	}
	throw new UnitException();
}

public void registerPath(PathFinding path, Position pos){
	this.passingPaths.put(path, pos);
}

public void unregisterPath(PathFinding path){
	this.passingPaths.remove(path);
}

}
