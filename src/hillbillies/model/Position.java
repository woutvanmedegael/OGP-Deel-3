package hillbillies.model;

/**
 * @value
 */
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * @invar  The xpos of each Position must be a valid xpos for any
 *         Position
 *       | this.isValidPos(getxpos())
 * @invar  The xpos of each Position must be a valid ypos for any
 *         Position
 *       | this.isValidPos(getypos())
 * @invar  The xpos of each Position must be a valid zpos for any
 *         Position
 *       | this.isValidPos(getzpos())      
 */

public class Position {
	/**
	 * variables registering the xpos, ypos and zpos this position
	 */
	private double xpos;
	private double ypos;
	private double zpos;
	/**
	 * A variable used to ease comparing two different numbers.
	 */
	private static final NbCompare nbComp = new NbCompare();
	/**
	 * 
	 * @param xpos
	 * 			| the xpos of this position
	 * @param ypos
	 * 			| the ypos of this position
	 * @param zpos
	 * 			| the zpos of this position
	 * @effect The xpos of this new position is set to
     *         the given xpos.
     *       | this.setxpos(xpos)
	 * @effect The ypos of this new position is set to
     *         the given ypos.
     *       | this.setypos(ypos)
	 * @effect The zpos of this new position is set to
     *         the given xpos.
     *       | this.setxpos(zpos)
	 * @throws UnitException
	 * 			An exception is thrown if one of the arguments, -xpos,ypos or zpos- are not in the playing field.
	 * 			|!isValidPos(xpos) OR !isValidPos(ypos)  OR !isValidPos(zpos)
	 * 			
	 */
	public Position(double xpos, double ypos, double zpos) throws UnitException{
		if (!isValidPos(xpos)){
			throw new UnitException();
			
		}
		if (!isValidPos(ypos)){
			throw new UnitException();
			
		}
		if (!isValidPos(zpos)){
			throw new UnitException();
			
		}
		this.setxpos(xpos);
		this.setypos(ypos);
		this.setzpos(zpos);
	}

/**
 * Return the xpos of this position.
 */
@Basic @Raw
public double getxpos() {
	return this.xpos;
}
/**
 * Set the xpos of this position to the given xpos.
 * 
 * @param  xpos
 *         The new xpos for this position
 * @post   The xpos of this new position is equal to
 *         the given xpos.
 *       | new.getxpos() == xpos
 * @throws UnitException
 *         The given xpos is not a valid xpos for any
 *         position.
 *       | ! isValidpos(getxpos())
 */
@Raw
public void setxpos(double xpos) 
		throws UnitException {
	if (! isValidPos(xpos))
		throw new UnitException();
	this.xpos = xpos;
}

/**
 * Return the ypos of this position.
 */
@Basic @Raw
public double getypos() {
	return this.ypos;
}
/**
 * Set the ypos of this position to the given ypos.
 * 
 * @param  ypos
 *         The new ypos for this position.
 * @post   The ypos of this new position is equal to
 *         the given ypos.
 *       | new.getypos() == ypos
 * @throws UnitException
 *         The given ypos is not a valid ypos for any
 *         position.
 *       | ! isValidpos(getypos())
 */
@Raw
public void setypos(double ypos) 
		throws UnitException {
	if (! isValidPos(ypos))
		throw new UnitException();
	this.ypos = ypos;
}

/**
 * Return the zpos of this position.
 */
@Basic @Raw
public double getzpos() {
	return this.zpos;
}
/**
 * Set the zpos of this position to the given zpos.
 * 
 * @param  zpos
 *         The new zpos for this position.
 * @post   The zpos of this new position is equal to
 *         the given zpos.
 *       | new.getzpos() == zpos
 * @throws UnitException
 *         The given zpos is not a valid zpos for any
 *         position.
 *       | ! isValidpos(getzpos())
 */
@Raw
public void setzpos(double zpos) 
		throws UnitException {
	if (! isValidPos(zpos))
		throw new UnitException();
	this.zpos = zpos;
}

/**
* Check whether the given pos is a valid pos for
 * any position.
 *  
 * @param  pos
 *         The pos to check.
 * @return 
 *       | result == (0<=pos && pos<50)
*/
public static boolean isValidPos(double pos) {
	return (0<=pos && pos<50);
}
/**
 *Calculates the distance between this position and another position
 * @param other
 * 			| the other position
 * @return returns the distance
 * 			| result == sqrt((this.getxpos()-pos.getxpos())^2+(this.getypos()-pos.getypos())^2+(this.getzpos()-pos.getzpos())^2)
 */
public double calculateDistance(Position other){
	return Math.sqrt(Math.pow(this.getxpos()-other.getxpos(), 2)+Math.pow(this.getypos()-other.getypos(),2)+Math.pow(this.getzpos()-other.getzpos(),2));
}
/**
 * checks if this position is equal to another position
 * @param other
 * 		| the other position
 * @return returns false if one of the coordinates -x,y,z- of this position is different from the given position
 * 		|result == (this.getxpos()==other.getxpos && this.getypos()==other.getypos  && this.getzpos()==other.getzpos) 
 * 		
 */
public boolean Equals(Position other){
	if (!nbComp.equals(this.getxpos(), other.getxpos())){
		return false;
	} else if (!nbComp.equals(this.getypos(), other.getypos())){
		return false;
	} else if (!nbComp.equals(this.getzpos(), other.getzpos())){
		return false;
	} else
		return true;
	
}

public float getEstimatedTimeTo(Position other){
	
}

}