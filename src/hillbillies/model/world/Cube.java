package hillbillies.model.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hillbillies.model.hillbilliesobject.Boulder;
import hillbillies.model.hillbilliesobject.HillbilliesObject;
import hillbillies.model.hillbilliesobject.Log;
import hillbillies.model.hillbilliesobject.unit.UnitException;

/**
 * @value
 * @author Wout Van Medegael & Adriaan Van Gerven
 */
/**
 * A class to register the different properties of a cube and the different objects it contains.
 */
public class Cube {
/**
 * ArrayList used to keep track of the objects on this cube.
 */
private ArrayList<HillbilliesObject> objectsOnThisCube = new ArrayList<>();
/**
 * A map used to easily get the corresponding integer of the terrainType
 */
private static final Map<TerrainType,Integer> terrainTypeToIntegerMapper = new HashMap<TerrainType, Integer>();
static
{
	terrainTypeToIntegerMapper.put(TerrainType.AIR, 0);
	terrainTypeToIntegerMapper.put(TerrainType.ROCK, 1);
	terrainTypeToIntegerMapper.put(TerrainType.TREE, 2);
	terrainTypeToIntegerMapper.put(TerrainType.WORKSHOP, 3);
}
/**
 * Initialize this new cube with given terrainType as an integer. An exception is thrown when the given terrainnumber is an 
 * unknown terrain.
 * @throws WorldException 
 */
public Cube(int terrainNumber) throws WorldException  {
	if (terrainNumber>3 || terrainNumber<0){
		throw new WorldException();
	}
	this.setTerrainType(terrainNumber);
}
/**
 * Initialize this new cube with given terrainType as a terraintype.
 * @throws WorldException 
 */
public Cube (TerrainType type) throws WorldException{
	if (!isValidTerrainType(type)){
		throw new WorldException();
	}
	this.terrainType = type;
}
/**
 * Return the terrainType of this cube.
 */
public TerrainType getTerrainType() {
	return this.terrainType;
}
/**
 * Returns the corresponding integer of the current terrain type of this cube.
 */
public int getIntTerrainType(){
	return terrainTypeToIntegerMapper.get(this.getTerrainType());	
}	
/**
 * Check whether the given terrainType is a valid terrainType for
 * any cube.
*/
private static boolean isValidTerrainType(TerrainType terrainType) {
	return (terrainType != null);
}
/**
 * Set the terrainType of this cube to the given terrainType.
 */
public void setTerrainType(TerrainType terrainType) {
	if (isValidTerrainType(terrainType))
		this.terrainType = terrainType;
	else this.terrainType = TerrainType.AIR;
}
/**
 * Sets the terraintype of this cube to the given terrain type.
 * @throws WorldException 
 */
public void setTerrainType(int terraintype) throws WorldException{
	if (terraintype>3 || terraintype<0){
		throw new WorldException();
	}
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
 * Returns true is the cube is passable, if the terraintype is air or workshop the cube is passable.
 */
public boolean isPassable(){
	return (this.getTerrainType()==TerrainType.AIR ||this.getTerrainType()==TerrainType.WORKSHOP);
}
/**
 * Variable registering the terrainType of this cube.
 */
private TerrainType terrainType;
/**
 * Adds the given object on this cube.
 * @throws WorldException 
 */
public void addObject(HillbilliesObject obj) throws UnitException{
	if (obj == null){
		throw new UnitException();
	}
	this.objectsOnThisCube.add(obj);
}
/**
 * Deletes the object from this cube.
 */
public void deleteObject(HillbilliesObject obj){
	this.objectsOnThisCube.remove(obj);
}
/**
 * Returns the objects that are currently occupying this cube.
 */
public ArrayList<HillbilliesObject> getObjectsOnThisCube(){
	ArrayList<HillbilliesObject> objects = new ArrayList<>();
	for (HillbilliesObject h: this.objectsOnThisCube){
		objects.add(h);
	}
	return objects;
}
/**
 * A boolean registering whether or not the cube is walkable, initialized at false.
 */
private boolean walkable = false;
/**
 * Sets walkable to the given bool.
 */
public void setWalkable(boolean bool){
	this.walkable = bool;
}
/**
 * Returns whether or not the cube is walkable.
 */
public boolean isWalkable(){
	return this.walkable;
}
/**
 * Returns true if this cube contains a boulder.
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
 * Returns true if this cube contains a log.
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
 * Returns a log that was occupying this cube, if no log was occupying this cube an exception is thrown.
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
 * Returns a boulder that was occupying this cube, if no boulder was occupying this cube an exception is thrown.
 * @throws WorldException 
 */
public Boulder getABoulder() throws WorldException{
	for (HillbilliesObject b: this.getObjectsOnThisCube()){
		if (b instanceof Boulder){
			return (Boulder) b;
		}
	}
	throw new WorldException();
}
}