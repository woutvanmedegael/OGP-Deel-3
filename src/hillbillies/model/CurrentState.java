package hillbillies.model;
/**
 * @value
 */

/**
 * An enumeration of the different states a unit can be in.
 *    In its current definition, the class distinguishes between seven different states.
 *    MOVING: the unit is moving
 *    WORKING: the unit is executing one unit of work
 *    ATTACKING: the unit is executing one attack
 *    RESTING: the unit is resting
 *    NEUTRAL: the unit is doing nothing
 *    ATTACK_PENDING: the unit finishes its move before attacking
 * @author Wout Van Medegael & Adriaan Van Gerven
 *
 */
public enum CurrentState {
	MOVING, WORKING, ATTACKING, RESTING, DEFENDING, NEUTRAL, ATTACK_PENDING, FALLING;
}