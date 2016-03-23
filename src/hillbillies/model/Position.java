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
	private double diagconst = Math.sqrt(2)-1;
	
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
		if (!isValidPos(xpos,ypos,zpos)){
			throw new UnitException();
			
		}
		this.xpos = xpos;
		this.ypos = ypos;
		this.zpos = zpos;
	}
	
	public Position(double xpos, double ypos, double zpos, World world) throws UnitException{
		if (!isValidPos(xpos,ypos,zpos)){
			throw new UnitException();
			
		}
		this.xpos = xpos;
		this.ypos = ypos;
		this.zpos = zpos;
		if (world!=null){
			this.world = world;
		}
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
	if (!isValidPos(xpos,this.ypos,this.zpos))
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
	if (! isValidPos(this.xpos,ypos,this.zpos))
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
	if (! isValidPos(this.xpos,this.ypos,zpos))
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
public static boolean isValidPos(double xpos, double ypos, double zpos) {
	if (world == null){
		return true;
	}
	if (xpos<0 || xpos >= world.getDimensionx()){
		return false;
	}
	if (ypos<0 || ypos >= world.getDimensiony()){
		return false;
	}
	if (zpos<0 || zpos >= world.getDimensionz()){
		return false;
	}
	if (world == null){
		return true;
	} 
	if (world.getCube((int)xpos, (int)ypos, (int)zpos).isPassable()){
		return true;
	}
	System.out.println("not passable");
	System.out.println(xpos);
	System.out.println(ypos);
	System.out.println(zpos);
	return false;
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

public double getEstimatedTimeTo(Position other){
	double time = 0;
	double zConst = 0.5;
	int deltaX = Math.abs((int)(other.getxpos()-this.getxpos()));
	int deltaY = Math.abs((int)(other.getypos()-this.getypos()));
	int deltaZ = (int)(other.getzpos()-this.getzpos());
	if (deltaZ<0){
		zConst = 1.2;
		deltaZ*=-1;
	}
	time += deltaZ*zConst+deltaX+deltaY;
	return time;
	
}

public double getExactTimeToAdjacent(Position other){
	double zConst = 1;
	int deltaX = Math.abs((int)(other.getxpos()-this.getxpos()));
	int deltaY = Math.abs((int)(other.getypos()-this.getypos()));
	int deltaZ = (int)(other.getzpos()-this.getzpos());
	if (deltaZ<0){
		zConst = 1.2;
		deltaZ*=-1;
	}
	else if (deltaZ>0){
		zConst = 0.5;
	}
	return zConst*Math.sqrt(deltaX*deltaX+deltaY*deltaY+deltaZ*deltaZ);
	
	
}

public static boolean isValidCoordinate(double coo, int Dimension){
	return (coo>=0 && coo<Dimension);
}


@Override
public String toString(){
	String str = "[("+String.valueOf(xpos)+"),("+String.valueOf(ypos)+"),("+String.valueOf(zpos)+")]";
	return str;
}

public Double[] toDoubles(){
	return new Double[]{this.getxpos(),this.getypos(),this.getzpos()};
}

private static World world;


public void setWorld(World world){
	this.world = world;
}

public static boolean isValidPos(double xpos, double ypos, double zpos, World world){
	if (world == null){
		return true;
	}
	if (xpos<0 || xpos >= world.getDimensionx()){
		return false;
	}
	if (ypos<0 || ypos >= world.getDimensiony()){
		return false;
	}
	if (zpos<0 || zpos >= world.getDimensionz()){
		return false;
	}
	if (world.getCube((int)xpos, (int)ypos, (int)zpos).isPassable()){
		return true;
	}
	return false;
}

}