package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public class Cube {


/**
 * Initialize this new cube with given terrainType.
 */
public Cube(int terrainNumber)  {
	switch (terrainNumber){
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
 * Return the terrainType of this cube.
 */
@Basic @Raw
public TerrainType getTerrainType() {
	return this.terrainType;
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
public boolean isPassable(){
	return (this.getTerrainType()==TerrainType.AIR ||this.getTerrainType()==TerrainType.WORKSHOP);
}
/**
 * Variable registering the terrainType of this cube.
 */
private TerrainType terrainType;

}
