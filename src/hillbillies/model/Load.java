
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
	this.LocalTarget = position;
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

/**
 * returns the position in the double type
 */
public double[] getDoublePosition(){
	return new double[]{position.getxpos(),position.getypos(),position.getzpos()};
}
/**
 * Check whether this load can have the given position as its position.
 * @note A Positiontype will always be a valid position
*/
@Raw
public boolean canHaveAsPosition(Position position) {
	return position.isValidPos();
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
/**
 * an enum of the different states a load can be in.
 */
private static enum LoadState{
	FALLING,NEUTRAL
}
/**
 * Variable registering the state of this load
 */
private LoadState myState = LoadState.NEUTRAL;
/**
 * advances the time for this object. If the state is neutral nothing happens, if the states is falling the falls.
 */
@Override
public void advanceTime(double dt) throws WorldException {
	switch (this.getMyState()){
		case NEUTRAL:
			break;
		case FALLING:
			this.fall(dt);
			break;
	}
	
}
//ADDED: position niet meer final gemaakt ? Waarom was die final? 
/**
 * sets the position and parent cube of this load an
 */
public void setPosition(Position position){
	this.position = position;
	setParentCube(this.getPosition(),this.world);	
}
/**
 * Makes the Load fall. The state is set to falling, the local target is set at the underlying cube. 
 */
public void startFalling() throws WorldException{
	this.setMyState(LoadState.FALLING);
	this.getLocalTarget().setPositionAt(this.getPosition());
	this.getLocalTarget().incrPosition(0, 0, -1);
}
/**
 * variable registering the local target of this load.
 */
private Position LocalTarget;
/**
 * returns the local target of this loac
 */
public Position getLocalTarget(){
	return this.LocalTarget;
}
/**
 * the speed to fall
 */
private final double speed = 3;
/**
 * sets the state of this load to the given state
 */
public void setMyState(LoadState state){
	this.myState = state;
}
/**
 * returns the state of this load
 */
public LoadState getMyState(){
	return this.myState;
}
/**
 * makes the load fall and stop when needed
 */
public void fall(double dt) throws WorldException{
	double distance = this.getPosition().calculateDistance(this.getLocalTarget());
	boolean hasArrivedAtLocalTarget = this.speed*dt>distance;
	if (hasArrivedAtLocalTarget){
		this.getPosition().setPositionAt(this.getLocalTarget());
		if (this.world.getCube(this.getPosition().getCubexpos(), this.getPosition().getCubeypos(), this.getPosition().getCubezpos()-1).isPassable()){
			startFalling();
		}
		else{
			this.setMyState(LoadState.NEUTRAL);
		}
	} else {
		this.getPosition().incrPosition(0, 0, -speed*dt);
	}
	if (this.getPosition().getCube()!=this.getParentCube()){
		this.setParentCube(this.getPosition(), this.world);
	}
}
}


