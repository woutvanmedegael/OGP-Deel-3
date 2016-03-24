package hillbillies.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public class Cube {

private static final Map<TerrainType,Integer> fuckdezeshit = new HashMap();
private ArrayList<HillbilliesObject> objectsOnThisCube = new ArrayList<>();
private Set<Unit> passingUnits = new HashSet<>();


private final World world;
static
{
	fuckdezeshit.put(TerrainType.AIR, 0);
	fuckdezeshit.put(TerrainType.ROCK, 1);
	fuckdezeshit.put(TerrainType.TREE, 2);
	fuckdezeshit.put(TerrainType.WORKSHOP, 3);
}
/**
 * Initialize this new cube with given terrainType without given hillbillies objects.
 */
public Cube(int terrainNumber,World world)  {
	this.world = world;
	this.setTerrainType(terrainNumber);
}

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

public int getIntTerrainType(){
	return fuckdezeshit.get(this.getTerrainType());	
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
public boolean isPassable(){
	return (this.getTerrainType()==TerrainType.AIR ||this.getTerrainType()==TerrainType.WORKSHOP);
}
/**
 * Variable registering the terrainType of this cube.
 */
private TerrainType terrainType;


public void addObject(HillbilliesObject obj){
	this.objectsOnThisCube.add(obj);
}


public void deleteObject(HillbilliesObject obj){
	this.objectsOnThisCube.remove(obj);
}

public ArrayList<HillbilliesObject> getObjects(){
	return this.objectsOnThisCube;
}

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

private boolean walkable = false;

public void setWalkable(boolean bool){
	this.walkable = bool;
}

public boolean isWalkable(){
	return this.walkable;
}
public boolean containsBoulder(){
	for (HillbilliesObject b: this.getObjectsOnThisCube()){
		if (b instanceof Boulder){
			return true;
		}
	}
	return false;
}
public boolean containsLog(){
	for (HillbilliesObject l: this.getObjectsOnThisCube()){
		if (l instanceof Log){
			return true;
		}
	}
	return false;
}
public Log getALog() throws UnitException{
	for (HillbilliesObject l: this.getObjectsOnThisCube()){
		if (l instanceof Log){
			return (Log) l;
		}
	}
	throw new UnitException();
	
	
}
public Boulder getABoulder() throws UnitException{
	for (HillbilliesObject b: this.getObjectsOnThisCube()){
		if (b instanceof Boulder){
			return (Boulder) b;
		}
	}
	throw new UnitException();
}

}
