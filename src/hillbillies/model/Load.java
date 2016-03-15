package hillbillies.model;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

public class Load {
	
/**
 * Initialize this new load with given position and a random weight between 10 and 50.
*/
public Load(Position position) throws WorldException {
	if (! canHaveAsPosition(position))
		throw new WorldException();
	this.position = position;
	Random rand = new Random();
	this.weight = rand.nextInt(40)+10;
}
/**
 * Return the position of this load.
 */
@Basic @Raw @Immutable
public Position getPosition() {
	return this.position;
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
private final Position position;
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
}


