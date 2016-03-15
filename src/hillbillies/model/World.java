package hillbillies.model;
import java.util.List;

import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;

import java.util.ArrayList;
import java.util.Arrays;
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
		}
	public Cube getCube(int x,int y,int z){
		return world[x][y][z];
	}
	public void setCube(int x,int y,int z,Cube cube){
		world[x][y][z] = cube;
		terrainChangeListener.notifyTerrainChanged(x, y, z);

	}
}
	
	
	

