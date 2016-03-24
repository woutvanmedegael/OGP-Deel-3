package hillbillies.model;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

public class Load extends HillbilliesObject{

private final World world;
/**
 * Initialize this new load with given position and a random weight between 10 and 50.
*/
public Load(Position position,World world) throws WorldException {
	if (! canHaveAsPosition(position))
		throw new WorldException();
	this.position = position;
	Random rand = new Random();
	this.weight = rand.nextInt(40)+10;
	//ADDED
	setParentCube(position,world);
	this.world = world;
}
/**
 * Return the position of this load.
 */
@Basic @Raw @Immutable
public Position getPosition() {
	return this.position;
}

//TODO commentaar hier

public double[] getDoublePosition(){
	return new double[]{position.getxpos(),position.getypos(),position.getzpos()};
}
/**
 * Check whether this load can have the given position as its position.
 * @note A Positiontype will always be a valid position
*/
@Raw
public boolean canHaveAsPosition(Position position) {
	return true;
}


/**
 * Variable registering the position of this load.
 */
private Position position;
/**
 * Return the weight of this load.
 */
@Basic @Raw @Immutable
public int getWeight() {
	return this.weight;
}
/**
 * Check whether this load can have the given weight as its weight.
 * @note There won't be an invalid weight.
*/
@Raw
public boolean canHaveAsWeight(int weight) {
	return true;
}
/**
 * Variable registering the weight of this load.
 */
private final int weight;

private static enum LoadState{
	NEUTRAL,FALLING,CARRIED	
}
private LoadState myState = LoadState.NEUTRAL;
@Override
public void advanceTime(double dt) {
	
}
//ADDED: position niet meer final gemaakt ? Waarom was die final? 
public void setPosition(Position position){
	this.position = position;
	setParentCube(this.getPosition(),this.world);
	
}
}


