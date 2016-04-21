package hillbilliesobject.unit;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
/**
 * A class used to keep track of the time a unit is in a certain state.
 * @value
 * @author Wout Van Medegael & Adriaan Van Gerven
 */
/**
 * @invar  The attackTime of each timeState must be a valid attackTime for any
 *         timeState.
 *       | isValidAttackTime(getAttackTime())
 * @invar  The trackTimeSprint of each timeState must be a valid trackTimeSprint for any
 *         timeState.
 *       | isValidTrackTimeSprint(getTrackTimeSprint())
 * @invar  The timeRested of each timeState must be a valid timeRested for any
 *         timeState.
 *       | isValidTimeRested(getTimeRested())
 * @invar  The trackTimeRest of each timeState must be a valid trackTimeRest for any
 *         timeState.
 *       | isValidTrackTimeRest(getTrackTimeRest())
 * @invar  The timeSinceRest of each timeState must be a valid timeSinceRest for any
 *         timeState.
 *       | isValidTimeSinceRest(getTimeSinceRest())
 * @invar  The trackTimeWork of each timeState must be a valid trackTimeWork for any
 *         timeState.
 *       | isValidtrackTimeWork(gettrackTimeWork())
 */




public class TimeState {
	
/**
 * Variable registering the attackTime of this timeState.
 */
	private double attackTime = 0;

/**
 * Return the attackTime of this timeState.
 */
@Basic @Raw
public double getAttackTime() {
	return this.attackTime;
}
/**
 * Check whether the given attackTime is a valid attackTime for
 * any timeState.
 *  
 * @param  attackTime
 *         The attackTime to check.
 * @return 
 *       | result == (attackTime>=0)
*/
public static boolean isValidAttackTime(double attackTime) {
	return (attackTime>=0);
}

/**
 * Set the attackTime of this timeState to the given attackTime.
 * 
 * @param  attackTime
 *         The new attackTime for this timeState.
 * @pre    The given attackTime must be a valid attackTime for any
 *         timeState.
 *       | isValidAttackTime(attackTime)
 * @post   The attackTime of this TimeState is equal to the given
 *         attackTime.
 *       | new.getAttackTime() == attackTime
 */
@Raw
public void setAttackTime(double attackTime) {
	assert isValidAttackTime(attackTime);
	this.attackTime = attackTime;
}



/**
 * Variable registering the trackTimeSprint of this timeState.
 */
private double trackTimeSprint = 0;
/**
 * Return the trackTimeSprint of this timeState.
 */
@Basic @Raw
public double getTrackTimeSprint() {
	return this.trackTimeSprint;
}
/**
 * Check whether the given trackTimeSprint is a valid trackTimeSprint for
 * any timeState.
 *  
 * @param  trackTimeSprint
 *         The trackTimeSprint to check.
 * @return 
 *       | result == (trackTimeSprint>=0)
*/
public static boolean isValidTrackTimeSprint(double trackTimeSprint) {
	return (trackTimeSprint>=0);
}
/**
 * Set the trackTimeSprint of this timeState to the given trackTimeSprint.
 * 
 * @param  trackTimeSprint
 *         The new trackTimeSprint for this timeState.
 * @pre    The given trackTimeSprint must be a valid trackTimeSprint for any
 *         timeState.
 *       | isValidTrackTimeSprint(trackTimeSprint)
 * @post   The trackTimeSprint of this timeState is equal to the given
 *         trackTimeSprint.
 *       | new.getTrackTimeSprint() == trackTimeSprint
 */
@Raw
public void setTrackTimeSprint(double trackTimeSprint) {
	assert isValidTrackTimeSprint(trackTimeSprint);
	this.trackTimeSprint = trackTimeSprint;
}


/**
 * Variable registering the timeRested of this timeState.
 */
private double timeRested =0;
/**
 * Return the timeRested of this timeState.
 */
@Basic @Raw
public double getTimeRested() {
	return this.timeRested;
}

/**
 * Check whether the given timeRested is a valid timeRested for
 * any timeState.
 *  
 * @param  timeRested
 *         The timeRested to check.
 * @return 
 *       | result == (timeRested>=0)
*/
public static boolean isValidTimeRested(double timeRested) {
	return (timeRested>=0);
}

/**
 * Set the timeRested of this timeState to the given timeRested.
 * 
 * @param  timeRested
 *         The new timeRested for this timeState.
 * @pre    The given timeRested must be a valid timeRested for any
 *         timeState.
 *       | isValidTimeRested(timeRested)
 * @post   The timeRested of this timeState is equal to the given
 *         timeRested.
 *       | new.getTimeRested() == timeRested
 */
@Raw
public void setTimeRested(double timeRested) {
	assert isValidTimeRested(timeRested);
	this.timeRested = timeRested;
}



/**
 * Variable registering the trackTimeRest of this timeState.
 */
private double trackTimeRest=0;

/**
 * Return the trackTimeRest of this timeState.
 */
@Basic @Raw
public double getTrackTimeRest() {
	return this.trackTimeRest;
}

/**
 * Check whether the given trackTimeRest is a valid trackTimeRest for
 * any timeState.
 *  
 * @param  trackTimeRest
 *         The trackTimeRest to check.
 * @return 
 *       | result == trackTimeRest>=0
*/
public static boolean isValidTrackTimeRest(double trackTimeRest) {
	return trackTimeRest>=0;
}

/**
 * Set the trackTimeRest of this timeState to the given trackTimeRest.
 * 
 * @param  trackTimeRest
 *         The new trackTimeRest for this timeState.
 * @pre    The given trackTimeRest must be a valid trackTimeRest for any
 *         timeState.
 *       | isValidTrackTimeRest(trackTimeRest)
 * @post   The trackTimeRest of this timeState is equal to the given
 *         trackTimeRest.
 *       | new.getTrackTimeRest() == trackTimeRest
 */
@Raw
public void setTrackTimeRest(double trackTimeRest) {
	assert isValidTrackTimeRest(trackTimeRest);
	this.trackTimeRest = trackTimeRest;
}

/**
 * Variable registering the timeSinceRest of this timeState.
 */
private double timeSinceRest=0;
/**
 * Return the timeSinceRest of this timeState.
 */
@Basic @Raw
public double getTimeSinceRest() {
	return this.timeSinceRest;
}

/**
 * Check whether the given timeSinceRest is a valid timeSinceRest for
 * any timeState.
 *  
 * @param  timeSinceRest
 *         The timeSinceRest to check.
 * @return 
 *       | result == 
*/
public static boolean isValidTimeSinceRest(double timeSinceRest) {
	return (timeSinceRest>=0);
}

/**
 * Set the timeSinceRest of this timeState to the given timeSinceRest.
 * 
 * @param  timeSinceRest
 *         The new timeSinceRest for this timeState.
 * @pre    The given timeSinceRest must be a valid timeSinceRest for any
 *         timeState.
 *       | isValidTimeSinceRest(timeSinceRest)
 * @post   The timeSinceRest of this timeState is equal to the given
 *         timeSinceRest.
 *       | new.getTimeSinceRest() == timeSinceRest
 */
@Raw
public void setTimeSinceRest(double timeSinceRest) {
	assert isValidTimeSinceRest(timeSinceRest);
	this.timeSinceRest = timeSinceRest;
}

/**
 * Variable registering the trackTimeWork of this timeState.
 */
private double trackTimeWork=0;

/**
 * Return the trackTimeWork of this timeState.
 */
@Basic @Raw
public double getTrackTimeWork() {
	return this.trackTimeWork;
}

/**
 * Check whether the given trackTimeWork is a valid trackTimeWork for
 * any timeState.
 *  
 * @param  trackTimeWork
 *         The trackTimeWork to check.
 * @return 
 *       | result == trackTimeWork>=0
*/
public static boolean isValidTrackTimeWork(double trackTimeWork) {
	return trackTimeWork>=0;
}

/**
 * Set the trackTimeWork of this timeState to the given trackTimeWork.
 * 
 * @param  trackTimeWork
 *         The new trackTimeWork for this timeState.
 * @pre    The given trackTimeWork must be a valid trackTimeWork for any
 *         timeState.
 *       | isValidtrackTimeWork(trackTimeWork)
 * @post   The trackTimeWork of this timeState is equal to the given
 *         trackTimeWork.
 *       | new.gettrackTimeWork() == trackTimeWork
 */
@Raw
public void setTrackTimeWork(double trackTimeWork) {
	assert isValidTrackTimeWork(trackTimeWork);
	this.trackTimeWork = trackTimeWork;
}


}