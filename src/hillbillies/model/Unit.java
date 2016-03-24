package hillbillies.model;

import java.util.ArrayList;

/**
 * @author Wout Van Medegael - 2de bach B. Ir. CW-ESAT
 * @author Adriaan Van Gerven - 2de bach B. Ir. CW-ESAT
 * Github repository: https://github.com/woutvm-peno/Hillbillies.git
 */

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * @invar  The xpos of each Unit must be a valid xpos for any
 *         Unit.
 *       | getMyPosition().isValidPos(getxpos())
 * @invar  The ypos of each unit must be a valid ypos for any
 *         unit.
 *       | getMyPosition().isValidPos(getypos())
 * @invar  The zpos of each unit must be a valid zpos for any
 *         unit.
 *       | getMyPosition().isValidPos(getzpos())
 * @invar  The name of each unit must be a valid name for any
 *         unit.
 *       | isValidName(getName())
 * @invar  The weight of each unit must be a valid weight for any
 *         unit.
 *       | isValidWeight(getWeight())
 * @invar  The strength of each unit must be a valid strength for any
 *         unit.
 *       | isValidStrength(getStrength())
 * @invar  The agility of each unit must be a valid agility for any
 *         unit.
 *       | isValidAgility(getAgility p())
 * @invar  The toughness of each unit must be a valid toughness for any
 *         unit.
 *       | isValidToughness(getToughness()
 * @invar  The currentHP of each unit must be a valid currentHP for any
 *         unit.
 *       | isValidCurrentHP(getCurrentHP())
 * @invar  The currentSP of each unit must be a valid currentSP for any
 *         unit.
 *       | isValidCurrentSP(getCurrentSP())
 * @invar  The orientation of each unit must be a valid orientation for any
 *         unit.
 *       | isValidOrientation(getOrientation())
 * @invar  The speed of each unit must be a valid speed for any
 *         unit.
 *       | isValidSpeed(getSpeed())
 * @invar  The experiencePoints of each unit must be a valid experiencePoints for any
 *         unit.
 *       | isValidExcperiencePoints(getExcperiencePoints())
 * @author Adriaan en Wout
 */
public class Unit extends HillbilliesObject{

/**
 * Initialize this new Unit with given xpos, ypos, zpos ,name, weight, strength, agility, toughness
 *
 * @param  xpos
 *         The xpos for the position for this new Unit.
 * @param  ypos
 * 		   The ypos for the position for this new Unit
 * @param  zpos
 * 		   The zpos for the position for this new Unit
 * @param  name
 *         The name for this new unit.
 * @effect The xpos of the position of this new Unit is set to
 *         the given xpos.
 *       | this.setxpos(xpos)
 * @effect The ypos of the position of this new Unit is set to
 *         the given ypos.
 *       | this.setxpos(ypos)
 * @effect The zpos of the position of this new Unit is set to
 *         the given zpos.
 *       | this.setxpos(zpos)
 * @effect The name of this new unit is set to
 *         the given name.
 *       | this.setName(name)   
 * @throws UnitException
 * 		   The given xpos,ypos or zpos is not a valid pos for
 * 		   a unit.
 *       | !this.getMyPosition().isValidPos(pos)  
 * @throws IllegalNameException
 * 		   The given name is not a valid name for
 * 		   a unit.
 *       | !isValidName(name) 
 */

/**
 * @param  strength
 *         The strength for this new unit.
 * @post   If the absolute value of the given strength is a valid strength for any unit,
 *         the strength of this new unit is equal to the absolute value of the given
 *         strength. Otherwise, the strength of this new unit is equal
 *         to the closest allowed value to the absolute value of the given strength.
 *       | strength = abs( strength);   
 *       | if (isValidParamFirstTime(agility))
 *       |   then new.getStrength() == strength
 *       | else if (strength > MAXFIRST)
 *       |   then new.getStrength() == MAXFIRST
 *       | else new.getStrength() == MINFIRST
 * @param  agility
 *         The agility for this new unit.
 * @post   If the absolute value of the given agility is a valid agility for any unit,
 *         the agility of this new unit is equal to the absolute value of the given
 *         agility. Otherwise, the agility of this new unit is equal
 *         to the closest allowed value to the absolute value of the given agility.
 *       | agility = abs( agility);   
 *       | if (isValidParamFirstTime(agility))
 *       |   then new.getAgility() == agility
 *       | else if (agility > MAXFIRST)
 *       |   then new.getAgility() == MAXFIRST
 *       | else new.getAgility() == MINFIRST
 * @param  weight
 *         The weight for this new unit.
 * @post   If the absolute value of the given weight is a valid weight for any unit and the weight is higher than the average
 * 		   of strength and agility,then the weight of this new unit is equal to the absolute value of the given
 *         weight. If if the weight is higher than the average and the weight is not a valid weight, the weight of this new unit is equal
 *         to the closest allowed value to the absolute value of the weight.
 *         If the absolute value of the weight is lower than the average of the strength and agility,
 *         the weight is set at this average.
 *       | weight = abs( weight);   
 *       | if (isValidParamFirstTime(weight) && validStrengthAgilityWeight(weight,this.getStrength(),this.getAgility()) )
 *       |   then new.getWeight() == weight
 *       | else if (weight > MAXFIRST && validStrengthAgilityWeight(...))
 *       |   then new.getWeigth = MAXFIRST
 *       | else if (validStrengthAgilityWeight())
 *       |	 new.getWeight() == MINFIRST
 *       | else new.getWeigth() == (this.getStrength()+this.getAgility()) /2
 * @param  toughness
 *         The toughness for this new unit.
 * @post   If the absolute value of the given toughness is a valid toughness for any unit,
 *         the toughness of this new unit is equal to the absolute value of the given
 *         toughness. Otherwise, the toughness of this new unit is equal
 *         to the closest allowed value to the absolute value of the give toughness.
 *       | toughness = abs( toughness);   
 *       | if (isValidParamFirstTime(toughness))
 *       |   then new.gettoughness() == toughness
 *       | else if (toughness > MAXFIRST)
 *       |   then new.gettoughness() == MAXFIRST
 *       | else new.gettoughness() == MINFIRST
 */
	
/**
 * @param  currentHP
 *         The currentHP for this new unit.
 * @pre    The given currentHP must be a valid currentHP for any unit.
 *       | isValidCurrentHP(currentHP)
 * @post   The currentHP of this new unit is equal to the given
 *         currentHP.
 *       | new.getCurrentHP() == currentHP
 * @param  currentSP
 *         The currentSP for this new unit.
 * @pre    The given currentSP must be a valid currentSP for any unit.
 *       | isValidCurrentSP(currentSP)
 * @post   The currentSP of this new unit is equal to the given
 *         currentSP.
 *       | new.getCurrentSP() == currentSP
 */
	
/**
 * @effect Local target is initialized at this position
 * 		   | this.setLocalTarget(new Position(xpos+0.5,ypos+0.5,zpos+0.5));
 */
	
/**
 * @param enableDefaultBehaviour
 * 			Boolean if default behaviour is enabled.
 * @effect Default behaviour is enabled or disabled.
 * 		   | setDefaultBehaviourEnabled(enableDefaultBehaviour)
 */

public Unit(double xpos,double ypos,double zpos,String name,int weight,int strength,int agility, int toughness, boolean enableDefaultBehaviour)
		throws UnitException {
	this.setMyPosition(new Position((double)((int)xpos+0.5),(double)((int)ypos+0.5),(double)((int)zpos+0.5)));
	this.setLocalTarget(new Position((double)((int)xpos+0.5),(double)((int)ypos+0.5),(double)((int)zpos+0.5)));
	this.setName(name);
	
	
	strength = Math.abs(strength);
	if (! isValidParamFirstTime(strength))
		if (strength > 100){
			strength = 100;
		}
		else 
			strength = 25;
	setStrength(strength);
	
	agility = Math.abs(agility);
	if (! isValidParamFirstTime(agility))
		if (agility > 100){
			agility = 100;
		}
		else 
			agility = 25;
	setAgility(agility);
	
	
	weight = Math.abs(weight);
	if (! isValidParamFirstTime(weight))
		if (weight > 100){
			weight = 100;
		}
		else 
			weight = 25;
	
	if (!validStrengthAgilityWeight(strength, agility,weight)){
		weight = (strength+agility) /2;
	}
	setWeight(weight);
	
	
	toughness = Math.abs(toughness);
	if (! isValidParamFirstTime(toughness))
		if (toughness > 100){
			toughness = 100;
		}
		else 
			toughness = 25;
	setToughness(toughness);	
	
	
	this.setCurrentHP(this.getMaxHP());
	this.setCurrentSP(this.getMaxSP());
	
	setDefaultBehaviourEnabled(enableDefaultBehaviour);
}

/**
 * Check whether the given combination of weight, strength and agility is valid.
 * @param weight
 * 			The given weight.
 * @param strength
 * 			The given strength.
 * @param agility
 * 			The given agility.
 * @return True if and only if weight is equal to or bigger than the average of strength and agility.
 * 		   | result == (strength+agility)/2 <= weight
 */
private boolean validStrengthAgilityWeight(int weight, int strength, int agility){
	return ((strength+agility) /2 <= weight);
}

/**
 * Variable registering whether the default behaviour is enabled
 */
private boolean defaultBehaviourEnabled;

/**
 * Enable or disable the default behaviour for this unit.
 * @param enabled
 * 			The boolean which enables default behaviour.
 * @post The default behaviour is enabled or disabled
 * 			| new.getDefaultBehaviourEnabled() == enabled
 * 	
 */
public void setDefaultBehaviourEnabled(boolean enabled){
	this.defaultBehaviourEnabled = enabled;
}

/**
 * Returns whether the default behaviour is enabled
 */
public boolean getDefaultBehaviourEnabled(){
	return this.defaultBehaviourEnabled;
}

/**
 * Variable used for comparing different float numbers
 */
private final NbCompare nbComp = new NbCompare();


/**
 * Return the xpos of this Unit.
 */
@Basic @Raw
public double getxpos() {
	return this.getMyPosition().getxpos();
}

///**
// * Set the xpos of this Unit to the given xpos.
// * 
// * @param  xpos
// *         The new xpos for this Unit.
// * @post   The xpos of this new Unit is equal to
// *         the given xpos.
// *       | new.getxpos() == xpos
// * @throws UnitException
// *         The given xpos is not a valid xpos for the position of any
// *         Unit.
// *       | !this.getMyPosition().isValidPos(getxpos())
// */
//@Raw
//public void setxpos(double xpos) 
//		throws UnitException {
//	this.getMyPosition().setxpos(xpos);
//}



/**
 * Return the ypos of this unit.
 */
@Basic @Raw
public double getypos() {
	return this.getMyPosition().getypos();
}
///**
// * Set the ypos of this unit to the given ypos.
// * 
// * @param  ypos
// *         The new ypos for this unit.
// * @post   The ypos of this new unit is equal to
// *         the given ypos.
// *       | new.getypos() == ypos
// * @throws UnitException
// *         The given ypos is not a valid ypos for the position of any
// *         unit.
// *       | !this.getMyPosition().isValidPos(getypos())
// */
//@Raw
//public void setypos(double ypos) 
//		throws UnitException {
//	this.getMyPosition().setypos(ypos);
//}



/**
 * Return the zpos of this Unit.
 */
@Basic @Raw
public double getzpos() {
	return this.getMyPosition().getzpos();
}
///**
// * Set the zpos of this Unit to the given zpos.
// * 
// * @param  zpos
// *         The new zpos for this Unit.
// * @post   The zpos of this new Unit is equal to
// *         the given zpos.
// *       | new.getzpos() == zpos
// * @throws UnitException
// *         The given zpos is not a valid zpos for the position of any
// *         Unit.
// *       | !this.getMyPosition().isValidPos(getzpos())
// */
//@Raw
//public void setzpos(double zpos) 
//		throws UnitException {
//	this.getMyPosition().setzpos(zpos);
//}

/**
 * Variable registering the name of this unit.
 */
private String name;

/**
 * Return the name of this unit.
 */
@Basic @Raw
public String getName() {
	return this.name;
}

/**
 * Registers all valid characters for in name.
 */
private static char[] validcharacters = "abcdefghijklmnopqrstuvwxyz' \"".toCharArray();

/**
 * Check whether the given name is a valid name for
 * any unit.
 *  
 * @param  name
 *         The name to check.
 * @return  result is true if the length of the name is at least 3
 *          and the first character of the name is an uppercase 
 *          and the name only contains characters from valid charachters.
 *          | if (name.length()<3)
 *          | then result == false
 *          | if (!(name[0] is Uppercase))
 *          | then result == false
 *          | for char in name:
 *          |  if (!(char in validcharacters))
 *          |  then result == false
 *          | else result == true
*/
public static boolean isValidName(String name) {
	if (name == null){
		return false;
	} if (name.length()<2){
		return false;
	} else if (!Character.isUpperCase(name.charAt(0))){
		return false;
	} 
	 return name.matches("[a-zA-Z\\s\'\"]+");	
}
/**
 * Set the name of this unit to the given name.
 * @param  name
 *         The new name for this unit.
 * @post   The name of this new unit is equal to
 *         the given name.
 *       | new.getName() == name
 * @throws IllegalNameException
 *         The given name is not a valid name for any
 *         unit.
 *       | !isValidName(getName())
 */
@Raw
public void setName(String name) 
		throws IllegalNameException {
	if (!isValidName(name))
		throw new IllegalNameException("Invalid name");
	this.name = name;
}

/**
 * The minimum and maximum for the weight, agility, toughness and strength for this unit. 
 * The bordervalues for initialization are different from those later on.
 */
private final static int MIN = 1;
private static final int MAX = 200;
final private static int MINFIRST = 25;
static final private int MAXFIRST = 100;



/**
 * Variables registering the weight, strength, agility and toughness of this unit.
 */
private int weight;
private int strength;
private int agility;
private int toughness;


/**
 * Check whether the given params - the possible params are weight, strength,
 * agility and toughness - are valid params for
 * any unit if set for the first time.
 *  
 * @param  weight
 *         The weight to check.
 * @return 
 *       | result == (minFirst < param && param <= maxFirst)
*/
public static boolean isValidParamFirstTime(int param){
	return (MINFIRST <= param && param <=MAXFIRST);
}

/**
 * Return the weight of this unit.
 */
@Basic @Raw
public int getWeight() {
	return this.weight;
}
/**
 * Check whether the given weight is a valid weight for
 * any unit.
 *  
 * @param  weight
 *         The weight to check.
 * @return 
 *       | result == (min < weight && weight <= max)
*/
public static boolean isValidWeight(int weight) {
	return (MIN <= weight && weight<=MAX);
}
/**
 * Set the weight of this unit to the given weight.
 * @param  weight
 *         The new weight for this unit.
 * @post   If the absolute value of the given weight is a valid weight for any unit,
 *         the new weight of this unit is equal to the absolute value of the given
 *         weight. Otherwise, the weight of this new unit is equal
 *         to the closest allowed value to the absolute value of the given weight.
 *       | weight = abs( weight)   
 *       | if (isValidWeight(weight))
 *       |   then new.getWeight() == weight
 *       | else if (weight > max)
 *       |   then new.getWeight() == max
 *       | else new.getWeight() == min
 * @effect the weight is changed if needed
 * 		 | changeWeightIfNeeded()
 */
@Raw
public void setWeight(int weight) {
	weight = Math.abs(weight);
	if (isValidWeight(weight)){
		this.weight = weight;
	}
	else if (weight > MAX){
		this.weight = MAX;
	}
	else {
		this.weight = MIN;
	}
	changeWeightIfNeeded();
}

/**
 * Return the strength of this unit.
 */
@Basic @Raw
public int getStrength() {
	return this.strength;
}

/**
 * Check whether the given strength is a valid strength for
 * any unit.
 *  
 * @param  strength
 *         The strength to check.
 * @return 
 *       | result == (min <= strength && strength<=max);
*/
public static boolean isValidStrength(int strength) {
	return (MIN <= strength && strength<=MAX);
}

/**
 * Set the strength of this unit to the given strength.
 * 
 * @param  strength
 *         The new strength for this unit.
 * @post   If the absolute value of the given strength is a valid strength for any unit,
 *         the new strength of this unit is equal to the absolute value of the given
 *         strength. Otherwise, the strength of this new unit is equal
 *         to the closest allowed value to the absolute value of the given strength.
 *       | strength = abs( strength);   
 *       | if (isValidStrength(strength))
 *       |   then new.getStrength() == strength
 *       | else if (strength > max)
 *       |   then new.getStrength() == max
 *       | else new.getStrength() == min
 * @effect the weight is changed if needed
 * 		 | changeWeightIfNeeded()
 * 		
 */
@Raw
public void setStrength(int strength) {
	strength = Math.abs( strength);
	if (isValidStrength(strength)){
		this.strength = strength;
	}
	else if (strength > MAX){
		this.strength = MAX;
	}
	else {
		this.strength = MIN;
	}
	changeWeightIfNeeded();
}

/**
 * Changes the weight if needed
 * @post If the weight is not a valid weight compared to the strength and agility of this unit, the weight is set to the average of the strength and agility.
 * 		| if (!validStrengthAgilityWeight(this.weight,this.strength,this.agility)
 * 		| 	then new.getWeight = (this.getStrength()+this.getAgility()) /2
 */
@Raw
public void changeWeightIfNeeded(){
	if (!validStrengthAgilityWeight(this.getWeight(),this.getStrength(),this.getAgility())){
		this.weight = (this.getStrength()+this.getAgility()) /2;
	}
}

/**
* Return the agility of this unit.
*/
@Basic @Raw
public int getAgility() {
	return this.agility;
}

/**
* Checks whether the given agility is a valid agility for
* any unit.
*  
* @param  agility
*         The agility to check.
* @return Returns true if and only if the given agility is within the minimum and maximum (or equal to one of those).
*       | result == (min <= agility && agility<=max);
*/
public static boolean isValidAgility(int agility) {
	return (MIN <= agility && agility <=MAX);
}

/**
* Sets the agility of this unit to the given agility
* 
* @param  agility
*         The new agility for this unit.
* @post   If the absolute value of the given agility is a valid agility for any unit,
*         the new agility of this unit is equal to the absolute value of the given
*         agility. Otherwise, the agility of this new unit is equal
*         to the closest allowed value to the absolute value of the given agility.
*        | agility = abs( agility);   
*        | if (isValidAgility(agility))
*        |   then new.getAgility() == agility
         | else if (agility > max)
*        |   then new.getAgility() == max
*        | else new.getAgility() == min
* @effect The weight is changed if needed
* 		 | changeWeightIfNeeded()
*/
@Raw
public void setAgility(int agility) {
	agility = Math.abs( agility);
	
	if (isValidAgility(agility)){
		this.agility = agility;
	}
	else if (agility > MAX){
		this.agility = MAX;
	}
	else {
		this.agility = MIN;
	}
	changeWeightIfNeeded();
}

/**
* Return the toughness of this unit.
*/
@Basic @Raw
public int getToughness() {
	return this.toughness;
}

/**
* Check whether the given toughness is a valid toughness for
* any unit.
*  
* @param  toughness
*         The toughness to check.
* @return Returns true if and only if the given agility is within the minimum and maximum (or equal to one of those).
*         | result == (min <= toughness && toughness<=max);
*/
public static boolean isValidToughness(int toughness) {
	return (MIN <= toughness && toughness <=MAX);
}

/**
* Set the toughness of this unit to the given toughness
* 
* @param  toughness
*         The new toughness for this unit.
* @post   If the absolute value of the given toughness is a valid toughness for any unit,
 *        the new toughness of this unit is equal to the absolute value of the given
 *        toughness. Otherwise, the toughness of this new unit is equal
 *        to the closest allowed value to the absolute value of the given toughness.
 *      | toughness = abs( toughness);   
 *      | if (isValidToughness(toughness))
 *      |   then new.getToughness() == toughness
        | else if (toughness > max)
*       |   then new.getToughness() == max
*       | else new.getToughness() == min
*/
@Raw
public void setToughness(int toughness) {
	toughness = Math.abs( toughness);
	
	if (isValidToughness(toughness)){
		this.toughness = toughness;
	}
	else if (toughness > MAX){
		this.toughness = MAX;
	}
	else {
		this.toughness = MIN;
	}
}

/**
 * Variable registering the currentHP of this unit.
 */
private int currentHP;
private int currentSP;


/**
 * Returns the maximum stamina points.
 * @return Returns the product of weight and toughness divided by 50
 * 		   | result == this.weight/(this.toughness*50)
 */
public int getMaxSP(){
	int maxSP = (int) Math.ceil(200*this.weight/100*this.toughness/100);
	return maxSP;
}

/**
 * Returns the maximum hit points.
 * @return Returns the product of weight and toughness divided by 50
 * 		   | result == this.weight/(this.toughness*50)
 */
public int getMaxHP(){
	int maxHP = (int) Math.ceil(200*this.weight/100*this.toughness/100);
	return maxHP;
}

/**
 * Return the currentHP of this unit.
 */
@Basic @Raw
public int getCurrentHP() {
	return this.currentHP;
}

/**
 * Check whether the given currentHP is a valid currentHP for
 * any unit.
 *  
 * @param  currentHP
 *         The currentHP to check.
 * @return Returns true if and only if currentHP is bigger than or equal to 0 and smaller than or equal to the maximum number of hit points.
 *       | result == (0<=currentHP && currentHP<=this.getMaxHP())
*/
public boolean isValidCurrentHP(int currentHP) {
	return (0<=currentHP && currentHP<=this.getMaxHP());
}

/**
 * Set the currentHP of this unit to the given currentHP.
 * 
 * @param  currentHP
 *         The new currentHP for this unit.
 * @pre    The given currentHP must be a valid currentHP for any
 *         unit.
 *       | isValidCurrentHP(currentHP)
 * @post   The currentHP of this unit is equal to the given
 *         currentHP.
 *       | new.getCurrentHP() == currentHP
 */
@Raw
public void setCurrentHP(int currentHP) {
	assert isValidCurrentHP(currentHP);
	this.currentHP = currentHP;
}


/**
 * Return the currentSP of this unit.
 */
@Basic @Raw
public int getCurrentSP() {
	return this.currentSP;
}

/**
 * Check whether the given currentSP is a valid currentSP for
 * any unit.
 *  
 * @param  currentSP
 *         The currentSP to check.
 * @return Returns true if and only if currentSP is bigger than or equal to 0 and smaller than or equal to the maximum number of stamina points.
 *       | result == (0<=currentSP && currentSP<=this.getMaxSP())
*/
public boolean isValidCurrentSP(int currentSP) {
	return (0<=currentSP && currentSP<=this.getMaxSP());
}

/**
 * Set the currentSP of this unit to the given currentSP.
 * 
 * @param  currentSP
 *         The new currentSP for this unit.
 * @pre    The given currentSP must be a valid currentSP for any
 *         unit.
 *       | isValidCurrentSP(currentSP)
 * @post   The currentSP of this unit is equal to the given
 *         currentSP.
 *       | new.getCurrentSP() == currentSP
 */
@Raw
public void setCurrentSP(int currentSP) {
	assert isValidCurrentSP(currentSP);
	this.currentSP = currentSP;
}

/**
 * Variable registering the orientation of this unit, initialized at PI/2
 */
private float orientation = (float) (Math.PI/2);

/**
 * Return the orientation of this unit.
 */
@Basic @Raw
public float getOrientation() {
	return this.orientation;
}

/**
 * Check whether the given orientation is a valid orientation for
 * any unit.
 *  
 * @param  orientation
 *         The orientation to check.
 * @return Returns true if and only if the orientation is bigger than or equal to 0 and smaller than or equal to 2 times PI.
 *       | result ==  (0<= orientation && orientation<= 2*Math.PI)
*/
public static boolean isValidOrientation(float orientation) {
	return (0<= orientation && orientation<= 2*Math.PI);
}

/**
 * Set the orientation of this unit to the given orientation.
 * 
 * @param  orientation
 *         The new orientation for this unit.
 * @post   If the given orientation is a valid orientation for this unit,
 *         the orientation of this unit is equal to the given
 *         orientation. Otherwise, the orientation of this new unit is equal
 *         to the orientation modulo 2 PI expressed as a positive angle.
 *       | if (isValidOrientation(orientation))
 *       |   then new.getOrientation() == orientation
 *       | else if (orientation % (float) 2*Math.PI => 0)
 *       |   then new.getOrientation() == orientation % (float) 2*Math.PI
 *       | else new.getOrientation() == orientation % (float) 2*Math.PI + 2*Math.PI
 */
@Raw
public void setOrientation(float orientation) {
	if (! isValidOrientation(orientation))
		orientation  %= (float) 2*Math.PI;
		if (orientation<0){
			orientation+=(float)2*Math.PI;}
	this.orientation = orientation;
}

/**
 * Variable registering the myState of this unit.
 */
private CurrentState myState = CurrentState.NEUTRAL;

/**
 * Return the myState of this unit.
 */
@Basic @Raw
public CurrentState getMyState() {
	return this.myState;
}

/**
 * Set the myState of this unit to the given myState.
 * 
 * @param  myState
 *         The new myState for this unit.
 * @post   The new myState of this  unit is equal to
 *         the given myState.
 *       | new.getMyState() == myState
 */
@Raw
public void setMyState(CurrentState myState)  {
	this.myState = myState;
}


/**
 * Variable used to ease keeping track of the time that a unit conducts a certain activity
 */
private TimeState myTimeState = new TimeState();

/**
 * Return the myTimeState of this unit.
 */
@Basic @Raw
public TimeState getMyTimeState() {
	return this.myTimeState;
}

/**
 * Variable registering the localTarget of this unit.
 */
private Position localTarget;

/**
 * Return the localTarget of this unit.
 */
@Basic @Raw
public Position getLocalTarget() {
	return this.localTarget;
}

/**
 * Set the localTarget of this unit to the given localTarget.
 * 
 * @param  localTarget
 *         The new localTarget for this unit.
 * @post   The new localTarget of this unit is equal to
 *         the given localTarget.
 *       | new.getLocalTarget() == localTarget
 */
@Raw
public void setLocalTarget(Position localTarget)  {
	this.localTarget = localTarget;
}


/**
 * Variable registering the globalTarget of this unit.
 */
private Position globalTarget = null;

/**
 * Return the globalTarget of this unit.
 */
@Basic @Raw
public Position getGlobalTarget() {
	return this.globalTarget;
}

/**
 * Set the globalTarget of this unit to the given globalTarget.
 * 
 * @param  globalTarget
 *         The new globalTarget for this unit.
 * @post   The new globalTarget of this unit is equal to
 *         the given globalTarget.
 *       | new.getGlobalTarget() == globalTarget
 */
@Raw
public void setGlobalTarget(Position globalTarget)  {
	this.globalTarget = globalTarget;
}

/**
 * Variable registering if sprint is toggled on or off.
 */
private boolean toggledSprint = false;

/**
 * Return the toggledSprint of this unit.
 */
@Basic @Raw
public boolean getToggledSprint() {
	return this.toggledSprint;
}

/**
 * Set the toggledSprint of this unit to the given toggledSprint.
 * 
 * @param  toggledSprint
 *         The new toggledSprint for this unit.
 * @post   The new toggledSprint of this unit is equal to
 *         the given toggledSprint.
 *       | new.getToggledSprint() == toggledSprint
 */
@Raw
public void setToggledSprint(boolean toggledSprint)  {
	this.toggledSprint = toggledSprint;
}


/**
 * Variable registering the defender of this unit.
 */
private Unit defender;

/**
 * Return the defender of this unit.
 */
@Basic @Raw
public Unit getDefender() {
	return this.defender;
}

/**
 * Set the defender of this unit to the given defender.
 * 
 * @param  defender
 *         The new defender for this unit.
 * @post   The defender of this new unit is equal to
 *         the given defender.
 *       | new.getDefender() == defender
 */
@Raw
public void setDefender(Unit defender){
	this.defender = defender;
}

/**
 * Variable registering if the unit has rested the minimal time.
 */
private boolean hasRested = true;

/**
 * Return the hasRested of this unit.
 */
@Basic @Raw
public boolean getHasRested() {
	return this.hasRested;
}

/**
 * Set the hasRested of this unit to the given hasRested.
 * 
 * @param  hasRested
 *         The new hasRested for this unit.
 * @post   The new hasRested of this unit is equal to
 *         the given hasRested.
 *       | new.gethasRested() == hasRested
 */
@Raw
public void setHasRested(boolean hasRested)  {
	this.hasRested = hasRested;
}

/**
 * Checks whether the unit is resting.
 * @return True if and only if this unit is resting.
 * 		  | result == (this.getMyState()==CurrentState.RESTING && this.getMyPosition().Equals(this.getLocalTarget()))
 */
public boolean isResting(){
	return (this.getMyState()==CurrentState.RESTING && this.getMyPosition().Equals(this.getLocalTarget()));
}

/**
 * Returns the xpos of the cube on which the unit is standing.
 */
public int getCubeXpos(){
	return (int)this.getxpos();
}
/**
 * Returns the ypos of the cube on which the unit is standing.
 */
public int getCubeYpos(){
	return (int)this.getypos();
}

/**
 * Returns the zpos of the cube on which the unit is standing.
 */
public int getCubeZpos(){
	return (int) this.getzpos();
}


/**
 * Variable registering the speed of this unit.
 */
private double speed;
/**
 * Return the speed of this unit.
 */
@Basic @Raw
public double getSpeed() {
	return this.speed;
}
/**
 * Check whether the given speed is a valid speed for
 * any unit.
 *  
 * @param  speed
 *         The speed to check.
 * @return Returns true if and only if the speed is greater than or equal
 *       | result == (speed>=0)
*/
public static boolean isValidSpeed(double speed) {
	return speed>=0;
}
/**
 * Set the speed of this unit to the given speed.
 * 
 * @param  speed
 *         The new speed for this unit.
 * @post   The speed of this new unit is equal to
 *         the given speed.
 *       | new.getSpeed() == speed
 * @throws UnitException
 *         The given speed is not a valid speed for any
 *         unit.
 *       | !isValidSpeed(getSpeed())
 */
@Raw
public void setSpeed(double speed) 
		throws UnitException {
	if (! isValidSpeed(speed))
		throw new UnitException();
	this.speed = speed;
}

/**
 * Makes this unit move to an adjacent cube if possible.
 *
 * @param dx
 *        The amount of cubes to move in the x-direction; should be -1,
 *        0 or 1.
 * @param dy
 *        The amount of cubes to move in the y-direction; should be -1,
 *        0 or 1.
 * @param dz
 *        The amount of cubes to move in the z-direction; should be -1,
 *        0 or 1.
 * @post  If the unit isn't attacking, moving or defending and this.getHasRested is true, the CurrentState will be 
 * 		  set to moving
 * 		  | if (!(this.getMyState()==CurrentState.ATTACKING || this.getMyState()==CurrentState.MOVING || this.getMyState() == CurrentState.DEFENDING) && this.getHasRested())
 *        | then new.getMyState()==CurrentState.MOVING      
 * @effect if the unit isn't attacking, moving or defending and this.getHasRested is true, 
 * 		   the local target and speed are set with parameters dx,dy,dz.
 * 		  | if (!(this.getMyState()==CurrentState.ATTACKING || this.getMyState()==CurrentState.MOVING || this.getMyState() == CurrentState.DEFENDING) && this.getHasRested())
 *        | then setLocalTargetAndSpeed(dx,dy,dz)
 * @throws UnitException
 *         An exception is thrown if the given move is not a valid move for this unit.
 *        | !isValidMove(new in([]{dx,dy,dz}))
 */	
public void moveToAdjacent(int dx, int dy, int dz) throws UnitException{
	if (!(this.getMyState()==CurrentState.ATTACKING || this.getMyState()==CurrentState.MOVING || this.getMyState() == CurrentState.DEFENDING) && this.getHasRested()){
		this.setMyState(CurrentState.MOVING);
		if (!isValidMove(new int[]{dx,dy,dz})){
			throw new UnitException();
		}
		this.setGlobalTarget(new Position(this.getMyPosition().getxpos()+dx, this.getMyPosition().getypos()+dy, this.getMyPosition().getzpos()+dz, this.myWorld));
		setLocalTargetAndSpeed(this.getGlobalTarget());
		}
	
}

/**
 * Variable used for keeping track of the position of the unit 
 */
private Position myPosition;

/**
 * Returns the position of this unit.
 */
@Basic
private Position getMyPosition() {
	return this.myPosition;
}

/**
* Sets the position of this unit to the given position
* @note Doesn't throw an exception. Position pos has to be valid to exist. See Position class invariants.
*/
@Raw
public void setMyPosition(Position pos){
	this.myPosition = pos;
}

/**
 * 
 * Set the local target of the unit and calculate and set the speed of the unit.
 * @param dx
 *         The amount of cubes to move in the x-direction; should be -1,
 *         0 or 1.
 * @param dy
 *         The amount of cubes to move in the y-direction; should be -1,
 *         0 or 1.
 * @param dz
 *         The amount of cubes to move in the z-direction; should be -1,
 *         0 or 1.
 * @post  The variable localTarget is set to the middle of the neighbouring target cube.
 * 		 | new.getLocalTarget().getxpos()==this.getCubeXpos()+dx+0.5;
 *		 | new.getLocalTarget().getypos()==this.getCubeYpos()+dy+0.5;
 *		 | new.getLocalTarget().getzpos()==this.getCubeZpos()+dz+0.5;
 * @effect The velocity is calculated.	 
 *		 | calculateVelocity(dz)
 * @throws UnitException
 *         One of the target coordinates is invalid, or the speed is negative (IMPOSSIBLE).
 *       | !(isValidPos(this.getCubeXpos()+dx+0.5) && isValidPos(this.getCubeYpos()+dy+0.5) 
 *       | && isValidPos(this.getCubeZpos()+dz+0.5) && isValidSpeed(calculateVelocity()))
 * @note This exception won't be thrown.
 */
private void setLocalTargetAndSpeed(Position nextPos) throws UnitException{
	this.setLocalTarget(nextPos);
	int dz = (int)(this.getLocalTarget().getzpos()-this.getMyPosition().getzpos());
	calculateVelocity(dz);
}

//TODO commentaar hier

private void startFalling(){
	this.setMyState(CurrentState.FALLING);
}


/**
 * The velocity is calculated and set.
 * @param dz
 * 		 The amount of cubes to move in the z-direction. Should be -1, 0 or 1.
 * @post The base speed is 1.5*(strength+agility)/(2*weight). If the unit ascends, the speed of unit is half its base speed.
 * 		 If it descends, the speed is 1.2 times the base speed. If it stays at the same height, its speed is the base speed.
 * 		 | baseSpeed = 1.5*(strength+agility)/(2*weight)
 *       | if (dz == 1)
 * 		 | then new.getSpeed() == 0.5*baseSpeed
 * 		 | else if (dz == -1)
 * 		 | then new.getSpeed() == 1.2*baseSpeed
 * 		 | else new.getSpeed() == baseSpeed
 * @throws UnitException
 * 			Exception is thrown by setSpeed()
 * @note UnitException will never be thrown. Velocity is always positive.
 */
private void calculateVelocity(int dz) throws UnitException {
	
	float velocity = (float) ((float)1.5*(this.getStrength()+this.getAgility())/((float)2.0*this.getWeight()));
	if (dz == 1){
		velocity *= 0.5;
	}
	else if (dz == -1){
		velocity *= 1.2;
	}
	
	if (this.getToggledSprint()){
		velocity *= 2;
	}
 
	this.setSpeed(velocity);
}

/**
 * Checks whether the give move is a valid move.
 * @param move
 * 			The move{dx,dy,dz} to be made.
 * @return True if all integers in move are -1, 0 or 1 and the target cube is a valid position for a unit. 
 * 		   | result == (for coo in move: (coo==0 || coo==1 || coo==-1)) &&
 * 		   |				isValidPos(this.getCubeXpos()+move[0]) &&
 * 		   |					isValidPos(this.getCubeYpos()+move[1]) && 
 * 		   | 						isValidPos(this.getCubeZpos()+move[2])) 								
 */
private boolean isValidMove(int[] move){
	for (int el:move){
		if (el<-1 || el>1){
			return false;
		}
	}
	if (!Position.isValidPos(this.getxpos()+move[0],this.getypos()+move[1],this.getzpos()+move[2],this.myWorld)){
		return false;
	}
	return true;
	
}


/**
 * Makes the unit move to a given cube.
 * @param cubeX
 * 		  The x-coordinate of the cube where the unit needs to move to
 * @param cubeY
 * 		  The y-coordinate of the cube where the unit needs to move to 
 * @param cubeZ
 *  	  The z-coordinate of the cube where the unit needs to move to 
 * @post  If the unit was not defending or attacking and hasRested was true, it is set to moving and the globalTarget is assigned.
 * 		  | if (!(this.getMyState()==CurrentState.DEFENDING || this.getMyState() == CurrentState.ATTACKING) && this.getHasRested())
 *		  | then new.getMyState() == CurrentState.MOVING
 *		  | new.getGlobalTarget().getxpos() == cubeX+0.5
 *		  | new.getGlobalTarget().getypos() == cubeY+0.5
 *		  | new.getGlobalTarget().getzpos() == cubeZ+0.5
 * @effect The next localTarget is calculated.
 * 		  | calculateLocalTarget()
 * @throws UnitException
 * 		  An exception is thrown if any of the coordinates is an invalid position for the cube
 */
public void moveTo(int cubeX, int cubeY, int cubeZ) throws UnitException{
	if (!(this.getMyState()==CurrentState.DEFENDING || this.getMyState() == CurrentState.ATTACKING) && this.getHasRested()){
		this.setMyState(CurrentState.MOVING);
		if (!Position.isValidPos(cubeX, cubeY, cubeZ, this.myWorld)){
			throw new UnitException();
		}
		this.setGlobalTarget(new Position(cubeX+0.5, cubeY+0.5, cubeZ+0.5, this.myWorld));
		if (myWorld != null){
			myPath = new PathFinding(myWorld, this.getMyPosition(),this.getGlobalTarget());
		}
		calculateLocalTarget();
	}
}


//TODO formeel commentaar 'pathfinding' referentie

public void setWorld(World world) throws UnitException{
	myWorld = world;
	this.getMyPosition().setWorld(world);
	this.getLocalTarget().setWorld(world);
}

private PathFinding myPath;

private World myWorld;
/**
 * This methods calculates the local target - the neighbouring cube to which the unit will move next - based on the global target: the neighbouring cube to which the unit will move next.
 * @effect  The local target gets set to the neighbouring cube closest to the global target.
 *         | if (nbComp.isSmaller(this.globalTarget.getxpos(),this.getCubeXpos()+0.5)
 * 		   | then dx = -1
 *         | else if (nbComp.isBigger(this.globalTarget.getxpos(),this.getCubeXpos()+0.5))
 *         | then dx = 1
 *         | else dx = 0
 *         | if (nbComp.isSmaller(globalTarget.getypos(),this.getCubeYpos()+0.5))
 * 	       | then dy = -1
 *         | else if (nbComp.isBigger(this.globalTarget.getypos(),this.getCubeYpos()+0.5))
 *         | then dy = 1
 *         | else dy = 0
 *         | if (nbComp.isSmaller(this.globalTarget.getzpos(),this.getCubeZpos()+0.5))
 * 		   | then dz = -1
 *         | else if (nbComp.isBigger(this.globalTarget.getzpos(),this.getCubeZpos()+0.5)
 *         | then dz = 1
 *         | else dz = 0
 *         | setLocalTargetAndSpeed(dx,dy,dz)
 * @throws UnitException
 * 		 	An exception is thrown if setLocalTargetAndSpeed receives illegalArguments
 * @note UnitException will never be thrown. The parameters dx, dy and dz will always be valid.
 */
private void calculateLocalTarget() throws UnitException{
	Position nextPos = this.myPath.moveToNextPos();
	if (nextPos == null || !nextPos.isValidPos()){
		this.setMyState(CurrentState.NEUTRAL);
		this.setGlobalTarget(null);
	} else {
		setLocalTargetAndSpeed(nextPos);
	}
	
}
//TODO:commentaar

/**
 * Every three minutes, the unit starts resting.
 * If the unit isn't doing anything, and hasn't  arrived yet at it's global or local target, it starts moving towards the target.
 * 	if it has arrived, the unit tries executing default behaviour.
 * If the unit is moving, it will move for dt seconds
 * If the unit is working, it will work for dt seconds
 * If the unit is resting and hasn't yet arrived at his local target,it will move towards it's local target for dt seconds.
 * 	if it has arrived at his local target it will rest for dt seconds.
 * If the unit is attacking and has already been attacking for a second, it will execute it's attack.
 * 	if it hasn't been attacking for a second yet, it will wait to attack.
 * If the unit is waiting to attack, it will move on to the middle of a cube before starting an attack
 */
public void advanceTime(double dt) throws UnitException{
	if (this.getExperiencePoints()>=10){
		this.setExperiencePoints(this.getExperiencePoints()-10);
		improveProperty();
	}
	if (dt<=0 || dt>0.2){
		throw new UnitException();
	}
	double timeSinceRest =  (this.getMyTimeState().getTimeSinceRest()+dt);
	if (timeSinceRest>180){
		this.setMyState(CurrentState.RESTING);
		timeSinceRest -= 180;
	}
	this.getMyTimeState().setTimeSinceRest(timeSinceRest);
	
	switch (this.getMyState()){
		case NEUTRAL:
			if ((getGlobalTarget() != null && !getGlobalTarget().Equals(this.getMyPosition()))){
				this.setMyState(CurrentState.MOVING);
			} else {
				this.executeDefaultBehaviour();
			}
			break;
		case MOVING:
			this.move(dt);
			break;
		case WORKING:
			this.work(dt);
			break;
		case RESTING:
			if (!this.getMyPosition().Equals(this.getLocalTarget())){
				this.move(dt);
			} else {
				this.rest(dt);
			}
			break;
			//TODO hier this.attacking
		case ATTACKING:
			if (this.getMyTimeState().getAttackTime()>1){
				this.attack(this.getDefender());
				break;
			} else {
				double attackTime = (this.getMyTimeState().getAttackTime()+dt);
				this.getMyTimeState().setAttackTime(attackTime);
				break;
			}
		case ATTACK_PENDING:
			if (!this.getMyPosition().Equals(this.getLocalTarget())){
				this.move(dt);
				break;
			} else {
				this.startAttacking();
				break;
			}
		case FALLING:
	default:
		break;
			
	
	}
}

/**
 * The unit moves for dt seconds towards the local target.
 * @param dt
 * 		  The timestep.
 * @effect The local target is determined.
 *		   | determineLocalTarget() 		
 * @effect If the unit is sprinting and the speed is bigger than 0, sprint is processed.
 * 		   | if (this.getToggledSprint() && this.getSpeed()!=0)
 * 		   | then reduceSPForSprint(this.getMyTimeState().getTrackTimeSprint+dt)
 * @effect The location is updated with the time step dt
 *		   | if (this.getSpeed()!=0)
 * 		   | then updateLocationAndOrientation(dt)
 * @throws UnitException
 *  		An exception is thrown if either determineLocalTarget(), reduceSPForSprint() or updateLocationAndOrientation throws an exception.
 */
private void move(double dt) throws UnitException{
	determineLocalTarget();
	if (this.getSpeed()!=0){
		if (this.getToggledSprint()){
			double timeSprinted = this.getMyTimeState().getTrackTimeSprint()+dt;
			reduceSPForSprint(timeSprinted);
		}
		updateLocationAndOrientation(dt);
	}
}

/**
 * The location and the orientation of the unit  are update with time step dt
 * @param dt 
 * 		  The timestep.
 * @post If the unit hasn't arrived at its local target within dt, the position is calculated and set to
 * 			the corresponding intermediate position. Else, it is set to the local target.
 * 		| if (this.getSpeed*dt>this.getMyPosition().calculateDistance(this.getLocalTarget()))
 * 		| then new.getxpos() == this.getLocalTarget().getxpos() &&
 * 		|	new.getypos() == this.getLocalTarget().getypos() &&
 * 		|		new.getzpos() == this.getLocalTarget().getzpos()
 * 		| otherwise
 * 		|	new.getxpos() == this.getxpos()+
 * 		|			dt*this.getSpeed()*(this.getLocalTarget().getxpos()-this.getxpos())/
 * 		|				this.getMyPosition().calculateDistance(this.getLocalTarget())
 *		|	new.getypos() == this.getypos()+
 * 		|			dt*this.getSpeed()*(this.getLocalTarget().getypos()-this.getypos())/
 * 		|				this.getMyPosition().calculateDistance(this.getLocalTarget())
 * 		|	new.getzpos() == this.getzpos()+
 * 		|			dt*this.getSpeed()*(this.getLocalTarget().getzpos()-this.getzpos())/
 * 		|				this.getMyPosition().calculateDistance(this.getLocalTarget());
 * 
 * @post If the unit hasn't arrived at its local target within dt, the units orientation is set to face the local target.
 * 		| If (this.getSpeed()*dt<this.getMyPosition().calculateDistance(this.getLocalTarget()))
 * 		| then new.getOrientation() == Math.atan2(this.getSpeed()*(this.getLocalTarget().getypos()-this.getypos())/
 * 		|				this.getMyPosition().calculateDistance(this.getLocalTarget()),
 * 		|				this.getSpeed()*(this.getLocalTarget().getxpos()-this.getxpos())/
 * 		|				this.getMyPosition().calculateDistance(this.getLocalTarget()))
 * @throws UnitException
 * 			An exception is thrown if an invalid coordinate is set.
 * @note	UnitException will never be thrown.
 */
private void updateLocationAndOrientation(double dt) throws UnitException {
	double distance = this.getMyPosition().calculateDistance(this.getLocalTarget());
	boolean hasArrivedAtLocalTarget = this.getSpeed()*dt>distance;
	if (hasArrivedAtLocalTarget){
		this.getMyPosition().setPositionAt(this.getLocalTarget());
	} else {
		double velocity = this.getSpeed();
		double velocityx = velocity*(this.getLocalTarget().getxpos()-this.getxpos())/distance;
		double velocityy = velocity*(this.getLocalTarget().getypos()-this.getypos())/distance;
		double velocityz = velocity*(this.getLocalTarget().getzpos()-this.getzpos())/distance;
		this.getMyPosition().incrPosition(velocityx*dt, velocityy*dt, velocityz*dt);
		this.setOrientation((float) Math.atan2(velocityy,velocityx));
	}
}

/**
 * Processes the time unit has sprinted by reducing its stamina points.
 * @param timeSprinted
 * 			The time the unit has been sprinting and that hasn't been taken into account yet.
 * @post If timeSprinted is more than 0.1 seconds and the unit still has stamina points, the stamina points are reduced by 1 points.
 * 		 If the stamina points of unit are 0, the unit stops sprinting and its speed is divided by 2.
 * 		 | If (timeSprinted>=0.1 && this.getCurrentSP()>0)
 * 		 | then new.getCurrentSP() == this.getCurrentSP()-1
 * 		 | Otherwise 
 * 		 | 		if (timeSprinted<0.1)
 * 		 | 			then new.getMyTimeState().getTrackTimeSprint() == timeSprinted
 * 		 | 		if (this.getCurrentSP()<0.1)
 * 		 |          then !new.getToggledSprint
 * 	     |          then new.getSpeed() == this.getSpeed()/2.0
 * @effect If a unit still has stamina points, and timeSprinted is bigger than 0.1 seconds, 
 * 			the units stamina points are reduced further if possible.
 * 		 | If (timeSprinted>=0.1 && this.getCurrentSP()>0)
 * 		 | then this.reduceSPForSprint(timeSprinted-0.1)
 * @throws UnitException
 * 			An exception is thrown if an invalid speed is set.
 * @note UnitException will never be thrown. Speed/2.0 will always be valid.
 */
private void reduceSPForSprint(double timeSprinted) throws UnitException {
	if (timeSprinted>=0.1 && this.getCurrentSP()>0){
		this.setCurrentSP(this.getCurrentSP()-1);
		this.reduceSPForSprint(timeSprinted-0.1);
	} else {
		if (timeSprinted<0.1){
			this.getMyTimeState().setTrackTimeSprint(timeSprinted);
		}
		if (this.getCurrentSP()==0){
			this.setToggledSprint(false);
			this.setSpeed(this.getSpeed()/2.0);
		}
	}
}
/**
 * Assigns a new local target if necessary.
 * If the unit hasn't got a next target anymore, its state is set to neutral and its speed to 0. 
 * If it has a global target, but has arrived at its local target, the next local target is calculated.
 * @post If the unit hasn't got a next target anymore, its state is set to neutral. 
 * 		 | If ((globalTarget == null && this.getMyPosition().Equals(localTarget)) ||
 * 		 |		(globalTarget != null && this.getMyPosition().Equals(globalTarget)))
 * 		 | then new.globalTarget == null and new.getMyState == CurrentState.NEUTRAL and new.getSpeed()==0
 * @effect If it has a global target, but has arrived at its local target (not equal to global target), the next local target is calculated.
 * 		 | calculateLocalTarget()
 * @throws UnitException
 * 			UnitException is thrown if setSpeed() or calculateLocalTarget throws an exception.
 * 
 */
private void determineLocalTarget() throws UnitException{
	if ((this.getGlobalTarget() == null && this.getMyPosition().Equals(this.getLocalTarget())) ||
			((this.getGlobalTarget() != null) && this.getMyPosition().Equals(this.getGlobalTarget()))){
		System.out.println("adding xp");
		this.setExperiencePoints(this.getExperiencePoints()+1);
		this.setGlobalTarget(null);
		this.setMyState(CurrentState.NEUTRAL);
		this.setSpeed(0);
		
	} else if (this.getGlobalTarget() != null && this.getMyPosition().Equals(this.getLocalTarget())){
		System.out.println("adding xp");
		this.setExperiencePoints(this.getExperiencePoints()+1);
		calculateLocalTarget();
	}
	
}
 

/**
 * Sets the unit to work
 * @throws UnitException 
 * @post If the unit was resting and the initial recovery time is fulfilled, or the unit wasn't doing anything, it started working.
 * 		 | if ((this.getMyState() == CurrentState.RESTING && this.getHasRested()) || this.getMyState()==CurrentState.NEUTRAL)
 * 		 | then new.getMyState() == CurrentState.WORKING and new.getMyTimeState().getTrackTimeWork() == 0
 */
public void workAt(int x, int y, int z){
	if ((this.getMyState() == CurrentState.RESTING && this.getHasRested()) || this.getMyState() == CurrentState.NEUTRAL){
		try{
			this.workCube = new Position(x,y,z);
			System.out.println("check adjacnet");
			if (!this.workCube.isAdjacent(this.getMyPosition())){
				System.out.println("not adjacent");
				throw new UnitException();
			}
		} catch (UnitException e){
			return;
		}
		this.setMyState(CurrentState.WORKING);
		this.getMyTimeState().setTrackTimeWork(0);
		System.out.println(y-this.getMyPosition().getypos());
		System.out.println(x-this.getMyPosition().getxpos());
		System.out.println(Math.atan2(y-this.getMyPosition().getypos(), x-this.getMyPosition().getxpos()));
		this.setOrientation((float) Math.atan2(y-this.getMyPosition().getypos()+0.5, x-this.getMyPosition().getxpos()+0.5));
	}
}

private Position workCube;

/**
 * Makes the unit work for dt seconds
 * @param dt
 * 		The number of seconds.
 * @throws UnitException 
 * @post If the total time of work done by unit was bigger than 500/strength, the unit finished one unit of work.
 * 		| if ((dt+this.getMyTimeState())>500/this.getStrength())
 * 		| then finishwork() and new.getMyTimeState().getTrackTimeWork() == 0
 * 		| otherwise new.getMyTimeState().getTrackTimeWork() == dt+this.getMyTimeState().getTrackTimeWork()
 */
private void work(double dt) throws UnitException{
	double prev = this.getMyTimeState().getTrackTimeWork();
	prev += dt;
	if (prev>(float)500/this.getStrength()){
		this.getMyTimeState().setTrackTimeWork(0);
		finishWork();
	} else {
		this.getMyTimeState().setTrackTimeWork(prev);
	}
	
}
//TODO:Commentaar

/**
 * Finished work.
 * @throws UnitException 
 * @post Finished work and units state is back to neutral.
 * 		| new.getMyState == CurrentState.NEUTRAL
 */
private void finishWork() throws UnitException{
	this.setExperiencePoints(this.getExperiencePoints()+10);
	this.setMyState(CurrentState.NEUTRAL);
	// Fill in later
}

/**
 * Initiates resting for this unit.
 * @post If the unit wasn't fighting or already resting, the unit has started resting.
 * 		| if (this.getMyState()==CurrentState.MOVING || this.getMyState()==CurrentState.NEUTRAL || this.getMyState()==CurrentState.WORKING)
 * 		| then new.getMyState()==CurrentState.RESTING and new.getMyTimeState().getTrackTimeRest()==0 
 * 		|	and new.getHasRested()==false and new.getMyTimeState().getTimeRested()==0
 */
public void startResting(){
	if (this.getMyState()==CurrentState.MOVING || this.getMyState() == CurrentState.NEUTRAL || this.getMyState() == CurrentState.WORKING){
		this.setMyState(CurrentState.RESTING);
		this.getMyTimeState().setTrackTimeRest(0);
		this.setHasRested(false);
		this.getMyTimeState().setTimeRested(0);
		
	}
}
//TODO methode weg dan?
/**
 * Initiates this units defense against the attacker.
 * @param attacker
 * 			The unit attacking this unit.
 * @post This unit starts defending against attacker.
 * 		| new.getMyState() == CurrentState.DEFENDING and 
 * 		| new.getOrientation() == Math.atan2(attacker.getypos()-this.getypos(),attacker.getxpos()-this.getxpos())
 * 
 */
public void startDefending(Unit attacker){
	this.setMyState(CurrentState.DEFENDING);
	this.setOrientation((float) Math.atan2(attacker.getypos()-this.getypos(),attacker.getxpos()-this.getxpos()));
}

/**
 * Initiates this units attack of the defender.		
 * @post If the unit wasn't already defending or attacking and defender is within reach,
 * 		 the unit starts attacking defender and turns to him.
 * 		| if (!(this.getMyState()==CurrentState.DEFENDING || this.getMyState()==CurrentState.ATTACKING) && targetWithinReach(defender))
 * 		| then new.getMyState()==CurrentState.ATTACKING and new.getOrientation()==Math.atan2(this.getDefender().getypos()-this.getypos(),this.getDefender().getxpos()-this.getxpos())
 * 		| 	and new.getMyTimeState().getAttackTime()==0
 * @effect If this unit wasn't already defending or attacking and defender is within reach,
 * 		   defender starts defending against this unit.
 * 		| if (!(this.getMyState()==CurrentState.DEFENDING || this.getMyState()==CurrentState.ATTACKING) && targetWithinReach(this.getDefender()))
 * 		| then this.getDefender().startDefending(this)
 */
private void startAttacking(){
	if (targetWithinReach(this.getDefender())){
		this.setMyState(CurrentState.ATTACKING);
		this.setOrientation((float) Math.atan2(this.getDefender().getypos()-this.getypos(), this.getDefender().getxpos()-this.getxpos()));
		this.getMyTimeState().setAttackTime(0);
	} else {
		this.setMyState(CurrentState.MOVING);
	}
}

/**
 * Prepares this unit to start the attack of unit defender.
 * @param defender
 * 			The unit to attack.
 * @post If this unit can attack, its state is set to pending attack and its defender is set.
 * 		 | if (!(this.getMyState()==CurrentState.DEFENDING || this.getMyState()==CurrentState.ATTACKING))
 * 		 | then new.getMyState()==CurrentState.ATTACK_PENDING and new.getDefender()==defender
 */
public void initiateAttack(Unit defender){
	if (!(this.getMyState() == CurrentState.DEFENDING || this.getMyState() == CurrentState.ATTACKING)){
		this.setMyState(CurrentState.ATTACK_PENDING);
		this.setDefender(defender);
	}
}

/**
 * Checks whether defender is within reach of this unit
 * @param defender
 * 			The unit to be in reach.
 * @return True if and only if defender is on a neighbouring cube of this unit.
 * 		   | result == (!(Math.abs(this.getCubeXpos()-defender.getCubeXpos())>1) &&
 * 					 		!(Math.abs(this.getCubeYpos()-defender.getCubeYpos())>1)&&
 * 								!(Math.abs(this.getCubeZpos()-defender.getCubeZpos())>1))
 */
public boolean targetWithinReach(Unit defender){
	if (Math.abs(this.getCubeXpos()-defender.getCubeXpos())>1){
		return false;
	} else if (Math.abs(this.getCubeYpos()-defender.getCubeYpos())>1){
		return false;
	} else if (Math.abs(this.getCubeZpos()-defender.getCubeZpos())>1){
		return false;
	} else 
		return true;
}
//TODO:Commentaar
/**
 * Defends against the attacker.
 * @param attacker
 * 			The unit attacking this unit
 * @post The units state is set to neutral.
 * 		 | new.getMyState() == CurrentState.NEUTRAL
 * 		
 * @effect If this unit has dodged attackers attack, it jumped away.
 * 		 Otherwise if it hasn't blocked attackers attack, it took damage.
 * 		| if (dodge(attacker))
 * 		| then jumpAway()
 * 		| otherwise if (!blocked(attacker))
 * 		| takeDamage(attacker)
 * @throws UnitException
 * 			Throws a UnitException if the unit tries to jump away and jumpAway() throws an exception.
 * @note	UnitException can't be thrown. jumpAway() will never throw an exception.
 */
public void defend(Unit attacker) throws UnitException{
	this.setOrientation((float) Math.atan2(attacker.getypos()-this.getypos(),attacker.getxpos()-this.getxpos()));
	if (dodge(attacker)){
		this.setExperiencePoints(this.getExperiencePoints()+20);
		jumpAway();
	} else if (!blocked(attacker)){
		takeDamage(attacker);
	}
	else{
		this.setExperiencePoints(this.getExperiencePoints()+20);
	}
	this.setLocalTarget(this.getMyPosition());
	this.setGlobalTarget(null);
	this.setMyState(CurrentState.NEUTRAL);
}

/**
 * A variable to generate random numbers.
 */
private static final Random random = new Random();

/**
 * Determines whether this unit was able to dodge the attackers attack.
 * @param attacker
 * 			The unit whose attack this unit tries to dodge.
 * @return True if and only the random generated number is smaller than this units chance of dodging the attackers attack.
 * 		   | result == random.nextFloat()<this.getAgility()/attacker.getAgility()/5
 */
public boolean dodge(Unit attacker){
	float prob =  (float) ((float) this.getAgility()/attacker.getAgility()/5.0);
	return random.nextFloat()<prob;
}
/**
 * Updates the unit's position randomly in the close region(x+(-1..1) and y + (-1..1)) around the current position.
 * If that position isn't in the gaming field, the unit jumps away in the opposite direction.
 * @post  If it is possible to change the x-coordinate and y-coordinate (stays within the gaming field) with a random value dx and dy between (-1..1),
 * 				 the x-coordinate and y-coordinate are changed.
 * 		  	if that isn't possible the position is updated with -dx and -dy.
 * 		 | dx = randomNumberBetween(-1..1)
 * 		 | dy = randomNumberBetween(-1..1)
 * 		 | if (isValidPos(this.getMyPosition().getxpos()+dx) && isValidPos(this.getMyPosition().getypos()+dy))
 * 		 | then new.getMyPosition.getxpos() == this.getMyPosition.getxpos() + dx and
 * 				new.getMyPosition.getypos() == this.getMyPosition.getypos() + dy
 * 		 | otherwise new.getMyPosition.getxpos() == this.getMyPosition.getxpos() - dx and
 * 					 new.getMyPosition.getypos() == this.getMyPosition.getypos() - dy
 * @throws UnitException
 *  		Throws an exception if either setxpos() or setypos() throws an exception.
 * @note This exception will never be thrown.
 */
public void jumpAway(){
	float xrand = (random.nextFloat())*2-1;
	float yrand = (random.nextFloat())*2-1;
	try{
		this.getMyPosition().incrPosition(xrand, yrand, 0);
	} catch (UnitException e) {
		jumpAway();
	}
}
/**
 * Checks whether the Unit has blocked the attack depending on the calculated probability.
 * @param attacker
 * 		  The unit that attacks this unit.
 * @return returns true if the random generated float is smaller than the calculated probability
 * 		   | result == (randomNumberBetween(0..1)<calculateProbability(attacker))	
 */
public boolean blocked(Unit attacker){
	float prob = calculateProbability(attacker);
	return random.nextFloat()<prob;
}
/**
 * Calculates the probability to block an attack depending on the characteristics of this unit and the attacker
 * @param attacker
 * 		  The unit that attacks this unit
 * @return returns the calculated probability: the strength plus the agility of this unit divided by the strength plus the agility of the attacker, divided by four. 
 * 			|result == (this.getStrength()+this.getAgility())/(attacker.getStrength()+attacker.getAgility())/4.0)
 */
private float calculateProbability(Unit attacker) {
	return (float) ((this.getStrength()+this.getAgility())/(4*attacker.getStrength()+attacker.getAgility()));
}
//TODO:commentaar
/**
 * A method that reduces this unit's HP depending on the strength of the attacker.
 * @param attacker
 * 		  The unit that attacks this unit
 * @throws UnitException 
 * @post The delivered damage is calculated  as the attackers strength divided by 10.
 * 		 If it is not possible to deliver all the damage this unit's HP will be set to 0 
 * 	     Else this unit's HP is reduced with the delivered damage
 * 		 | damage = attacker.getStrength()/10;
 * 		 | if (damage>this.getCurrentHP) 
 * 		 |  then new.getCurrentHP() == 0
 * 		 | otherwise new.getCurrentHP()== this.getCurrentHP()-damage	
 */
public void takeDamage(Unit attacker) throws UnitException{
	attacker.attackSuccesfull();
	int damage = (int) attacker.getStrength()/10;
	if (damage>this.getCurrentHP()){
		this.setCurrentHP(0);
	} else {
		this.setCurrentHP(this.getCurrentHP()-damage);
	}
}

//TODO: commentaar
public void attackSuccesfull() throws UnitException{
	this.setExperiencePoints(this.getExperiencePoints()+20);
}
/**
 * The unit rests for a period dt, HP en SP are recovered depending on the period dt and the not yet used rest time of the 
 * unit's time state. 
 * @param dt
 * 		  The number of seconds resting
 * @post The timeRested of the unit's timeState is updated 
 * 			| new.getMyTimeState().getTimeRested() == this.getMyTimeState().getTimeRested()+dt
 * @effect This unit is checked to have rested the minimum time.
 * 		    | hasRestedMinimumTime(dt)
 * @effect If the currentHP of the unit is smaller than the max HP,the HP is healed.
 * 		    | if (this.getCurrentHP()< this.getMaxHP())
 * 			| then healHPAndCalculateNotYetUsedRestTime(..)
 * @post  If the currentHP of the unit is smaller than the max HP,the HP is healed.
 * 		    | if (this.getCurrentHP()< this.getMaxHP())
 * 			| then new.getMyTimeState().getTrackTimeRest()==healHPAndCalculateNotYetUsedRestTime()
 * @effect If the currentHP of the unit isn't smaller than the max HP, the SP is healed
 * 			| if (!this.getCurrentHP()<this.getMaxHP())
 * 			| then healSPAndCalculateNotYetUsedRestTime(..)
 * @post  If the currentHP of the unit isn't smaller than the max HP, the new rest time is assigned.
 * 			| if (!this.getCurrentHP()<this.getMaxHP())
 * 			| then new.getMyTimeState().getTrackTimeRest() == healSPAndCalculateNotYetUsedRestTime(..)
 * @effect If the units HP and SP are equal to respectively the maxHP and maxSP, this unit stops resting
 * 			| if (this.getCurrentHP() == this.getMaxHP() && this.getCurrentSP() == this.getMaxSP())
 * 			| then restingIsDone();
 */
public void rest(double dt){
	this.getMyTimeState().setTimeRested(this.getMyTimeState().getTimeRested()+dt);
	hasRestedMinimumTime(dt);
	double notYetUsedRestTime = this.getMyTimeState().getTrackTimeRest();
	notYetUsedRestTime += dt;
	if (this.getCurrentHP()< this.getMaxHP()){
		notYetUsedRestTime = healHPAndCalculateNotYetUsedRestTime(notYetUsedRestTime);
	}
	if (this.getCurrentHP()==this.getMaxHP()) {
			notYetUsedRestTime = healSPAndCalculateNotYetUsedRestTime(notYetUsedRestTime);
			if (this.getCurrentSP() == this.getMaxSP())
				restingIsDone();
			}
	
	this.getMyTimeState().setTrackTimeRest(notYetUsedRestTime);
	
}
/**
 * @param notYetUsedRestTime
 * 			The time the unit has been resting and that hasn't been taken into account yet.
 * @effect If the HP isn't fully recovered and the not yet used rest time is bigger than or equals the timeToHeal1HP.
 * 			then this.HealHPAndCalculateNotYetUsedRestTime is called with argument notYetUsedRestTime-timeToHeal1HP.
 * 			| if (this.getCurrentHP()<this.getMaxHP() && notYetUsedRestTime>=timeToHeal1HP)
 * 			| then this.healHPAndCalculateNotYetUsedRestTime(notYetUsedRestTime-timeToHeal1HP);
 * @post If the HP isn't fully recovered and the not yet used rest time is bigger than or equals the timeToHeal1HP, 1 HP is restored.
 *          | if (this.getCurrentHP()<this.getMaxHP() && notYetUsedRestTime>=timeToHeal1HP)
 *          | then new.getCurrentHP == this.getCurrentHP +1
 * @return Returns the not yet used rest time of the timeState of this unit
 * 	 	   | result == notYetUsedRestTime
 * @note Recursive structure won't be used with the current formula, maximum for toughness and
 * 		 maximum timestep dt of 0.2 seconds.
 */
private double healHPAndCalculateNotYetUsedRestTime(double notYetUsedRestTime) {
	float timeToHeal1HP = (float)40/this.getToughness();
	if (this.getCurrentHP()<this.getMaxHP() && notYetUsedRestTime>=timeToHeal1HP){
		this.setCurrentHP(this.getCurrentHP()+1);
		return this.healHPAndCalculateNotYetUsedRestTime(notYetUsedRestTime-timeToHeal1HP);
	} else {
		return notYetUsedRestTime;
	}
}
/**
 * @param notYetUsedRestTime
 * 			The time the unit has been resting and that hasn't been taken into account yet.
 * @effect If the SP isn't fully recovered and the not yet used rest time is bigger than or equals the timeToHeal1SP.
 * 			then this.HealSPAndCalculateNotYetUsedRestTime is called with argument notYetUsedRestTime-timeToHeal1SP.
 * 			| if (this.getCurrentSP()<this.getMaxSP() && notYetUsedRestTime>=timeToHeal1SP)
 * 			| then this.healSPAndCalculateNotYetUsedRestTime(notYetUsedRestTime-timeToHeal1SP);
 * @post If the SP isn't fully recovered and the not yet used rest time is bigger than or equals the timeToHeal1SP, 1 SP is restored.
 *          | if (this.getCurrentSP()<this.getMaxSP() && notYetUsedRestTime>=timeToHeal1SP)
 *          | then new.getCurrentSP == this.getCurrentSP +1
 * @return Returns the not yet used rest time of the timeState of this unit
 * 	 	   | result == notYetUsedRestTime
 */
private double healSPAndCalculateNotYetUsedRestTime(double notYetUsedRestTime) {
	float timeToHeal1SP = (float)20/this.getToughness();
	if (this.getCurrentSP()<this.getMaxSP() && notYetUsedRestTime>=timeToHeal1SP){
		this.setCurrentSP(this.getCurrentSP()+1);
		return this.healSPAndCalculateNotYetUsedRestTime(notYetUsedRestTime-timeToHeal1SP);
	} else {
		return notYetUsedRestTime;
	}
}

/**
 * This method's makes the unit stop resting
 * @post The state of the unit is set on NEUTRAL
 * 		 | new.getMyState()== CurrentState.NEUTRAL
 * @post The hasRested is set on true
 * 		 | new.getHasRested()==true
 * @post The time rested is reset to 0
 * 		 | new.getMyTimeState().getTimeRested() == 0;
 */
private void restingIsDone() {
	this.setMyState(CurrentState.NEUTRAL);
	this.setHasRested(true);
	this.getMyTimeState().setTimeRested(0);
}

/**
 * A unit must rest for a minimum period of time, this method sets hasRested on true if this is the case.
 * @param dt
 * 			The time rested.
 * @post If the unit has rested long enough, longer than 40/toughness, hasRested is set on true.
 * 		 | if (this.getMyTimeState().getTimeRested()>=40/this.getToughness())
 * 		 | then new.getHasRested()==true;
 */
private void hasRestedMinimumTime(double dt) {
	if (this.getMyTimeState().getTimeRested()>=(float) 40/this.getToughness()){
		this.setHasRested(true);
	}
}

/**
 * A final variable containing the possible states for a unit in the default behaviour.
 */
private static final List<CurrentState> DEFAULTSTATES = Arrays.asList(CurrentState.WORKING, CurrentState.MOVING, CurrentState.RESTING);

/**
 * A final variable containing the size of DEFAULTSTATES
 */
private static final int SIZE = DEFAULTSTATES.size();

/**
 * Determines what default behaviour the unit executes.
 * @post If default is enabled this function randomly puts the unit in a default state.
 * 		 | if (this.isDefaultBehaviourEnabled())
 * 		 | then new.getMyState() in DEFAULTSTATES
 * @post If the random-generated state is 'moving', a random position in the map has been selected. 
 * 		 | if (this.isDefaultBehaviourEnabled() && randomDefaultState == CurrentState.MOVING)
 * 		 | then new.globalTarget in map
 * @throws UnitException
 *			Throws an exception if Position(...) throws an exception.
 * @note UnitException won't be thrown.
 */
public void executeDefaultBehaviour() throws UnitException{
	if (this.getDefaultBehaviourEnabled()){
		this.setMyState(DEFAULTSTATES.get(random.nextInt(SIZE)));
		
		if (this.getMyState() == CurrentState.MOVING){
			this.setGlobalTarget(new Position(random.nextInt(50)+0.5,
					random.nextInt(50)+0.5,random.nextInt(50)+0.5, this.myWorld));
	}
	}
}

/**
 * This method executes the attack.
 * The current state of this unit is set back to neutral, the given defender defends the attack 
 * and the attackTime is set back to zero.
 * @param defender
 * 			the unit which this unit is attacking and thus the unit that needs to defend the attack
 * @effect The defender defends against this unit.
 * 		   | defender.defend(this)
 * @post   This unit is back to neutral state, and its attackTime is set to 0
 * 		   | new.getMyState()==CurrentState.NEUTRAL
 * 		   | new.getMyTimeState().getAttackTime()==0
 * @throws UnitException
 *			Throws an exception if defend throws an exception.
 */
public void attack(Unit defender) throws UnitException{
	defender.defend(this);
	this.setMyState(CurrentState.NEUTRAL);
	this.getMyTimeState().setAttackTime(0);
}


//PART TWO IMPLEMENTATION





/**
 * Return the experiencePoints of this unit.
 */
@Basic @Raw
public int getExperiencePoints() {
	return this.experiencePoints;
}

/**
 * Check whether the given experiencePoints is a valid experiencePoints for
 * any unit.
 *  
 * @param  experiencePoints
 *         The experiencePoints to check.
 * @return 
 *       | result == (experiencePoints>=0)
*/
public static boolean isValidExperiencePoints(int experiencePoints) {
	return (experiencePoints>=0);
}

/**
 * Set the experiencePoints of this unit to the given experiencePoints.
 * 
 * @param  experiencePoints
 *         The new experiencePoints for this unit.
 * @post   The experiencePoints of this new unit is equal to
 *         the given experiencePoints.
 *       | new.getExperiencePoints() == experiencePoints
 * @throws UnitException
 *         The given experiencePoints is not a valid experiencePoints for any
 *         unit.
 *       | ! isValidExperiencePoints(getExperiencePoints())
 */
@Raw
public void setExperiencePoints(int experiencePoints) 
		throws UnitException {
	if (! isValidExperiencePoints(experiencePoints))
		throw new UnitException();
	this.experiencePoints = experiencePoints;
}

/**
 * Variable registering the experiencePoints of this unit.
 */
private int experiencePoints =0;

//TODO: improveProperty 
public void improveProperty(){
		ArrayList<Integer> props = new ArrayList<>();
		if (this.getAgility()<200){
			props.add(0);
		}
		if (this.getStrength()<200){
			props.add(1);
		}
		if (this.getToughness()<200){
			props.add(2);
		}
		Random random = new Random();
		int nb = random.nextInt(props.size());
		System.out.println(nb);
		switch (nb){
			case 0:
				System.out.println("check 0");
				this.setAgility(this.getAgility()+1);
			case 1:
				System.out.println("check 1");
				this.setStrength(this.getStrength()+1);
			case 2:
				System.out.println("check 2");
				this.setToughness(this.getToughness()+1);
		}
				
	}

/** TO BE ADDED TO CLASS HEADING
 * @invar  The load of each unit must be a valid load for any
 *         unit.
 *       | isValidLoad(getLoad())
 */


/**
 * Initialize this new unit with given load.
 *
 * @param  load
 *         The load for this new unit.
 * @effect The load of this new unit is set to
 *         the given load.
 *       | this.setLoad(load)
 */
public Unit(Load load)
		throws UnitException {
	this.setLoad(load);
}


/**
 * Return the load of this unit.
 */
@Basic @Raw
public Load getLoad() {
	return this.load;
}
//TODO: commentaar
public boolean isCarryingLog(){
	return (this.getLoad()!=null && this.getLoad() instanceof Log);
}

//TODO: commentaar
public boolean isCarryingBoulder(){
	return (this.getLoad()!=null && this.getLoad() instanceof Boulder);
}

/**
 * Set the load of this unit to the given load.
 * 
 * @param  load
 *         The new load for this unit.
 * @post   The load of this new unit is equal to
 *         the given load.
 *       | new.getLoad() == load
 * @throws UnitException
 *         The given load is not a valid load for any
 *         unit.
 *       | ! isValidLoad(getLoad())
 */
@Raw
public void setLoad(Load load){
	this.load = load;
}

/**
 * Variable registering the load of this unit.
 */
private Load load;


public void warnCubeHasChanged(Cube cube){
	
}

private Faction myFaction;

public void setFaction(Faction faction){
	myFaction = faction;
}

public Faction getFaction(){
	return this.myFaction;
}




}















