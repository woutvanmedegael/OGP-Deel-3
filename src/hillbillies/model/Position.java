package hillbillies.model;

import java.util.ArrayList;

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
	
	private double diagconst = Math.sqrt(2)-1;
	
	/**
	 * A variable used to ease comparing two different numbers.
	 */
	private static final NbCompare nbComp = new NbCompare();
	public double xpos;
	public double ypos;
	public double zpos;
	public double getxpos(){
		return this.xpos;
	}
	public double getypos(){
		return this.ypos;
	}
	public double getzpos(){
		return this.zpos;
	}
	public int getCubexpos(){
		return (int)this.xpos;
	}
	public int getCubeypos(){
		return (int)this.ypos;
	}
	public int getCubezpos(){
		return (int)this.zpos;
	}
	public Cube getCube(){
		return this.world.getCube((int)xpos, (int)ypos, (int)zpos);
	}
	public void setPos(double xpos, double ypos, double zpos) throws UnitException{
		if (!isValidPos(xpos, ypos, zpos)){
			throw new UnitException();
		}
		this.xpos = xpos;
		this.ypos = ypos;
		this.zpos = zpos;
	}
	
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
		
		if (!Position.isValidPos(xpos,ypos,zpos,world)){
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
* Check whether the given pos is a valid pos for
 * any position.
 *  
 * @param  pos
 *         The pos to check.
 * @return 
 *       | result == (0<=pos && pos<50)
*/
public boolean isValidPos(double xpos, double ypos, double zpos) {

	if (world == null){
		return (xpos>=0 && ypos>=0 && zpos>=0);
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
	return true;
}

public boolean isValidPos(){
	if (this.world == null){
		return (this.getxpos()>=0 && this.getypos()>=0 && this.getzpos()>=0);
	}
	if (this.getxpos()<0 || this.getxpos() >= this.world.getDimensionx()){
		return false;
	}
	if (this.getypos()<0 || this.getypos() >= this.world.getDimensiony()){
		return false;
	}
	if (this.getzpos()<0 || this.getzpos() >= this.world.getDimensionz()){
		return false;
	}
	return true;
}

public static boolean posWithinWorld(int x, int y, int z, World world){
	try{
		world.getCube(x, y, z);
		return true;
	} catch (ArrayIndexOutOfBoundsException e){
		return false;
	}
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
	return true;
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
	time += deltaZ/zConst+deltaX+deltaY;
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
	return Math.sqrt(deltaX*deltaX+deltaY*deltaY+deltaZ*deltaZ)/zConst;
}

public void setPositionAt(Position position) throws UnitException{
	this.setPos(position.getxpos(), position.getypos(), position.getzpos());
}

private World world;

public void setToMiddleOfCube() throws UnitException{
	this.setPos((int)this.getxpos()+0.5, (int)this.getypos()+0.5, (int)this.getzpos()+0.5);
}

public void incrPosition(double dx, double dy, double dz) throws UnitException{
	this.setPos(this.getxpos()+dx, this.getypos()+dy, this.getzpos()+dz);
}

public void setWorld(World world) throws UnitException{
	if (world != null){
		this.world = world;
	}
	if (!isValidPos(this.getxpos(),this.getypos(),this.getzpos())){
		throw new UnitException();
	}
}


	
public boolean isAdjacent(Position other){
	if (Math.abs(this.getCubexpos()-other.getCubexpos())>1){
		return false;
	}
	if (Math.abs(this.getCubeypos()-other.getCubeypos())>1){
		return false;
	}
	if (Math.abs(this.getCubezpos()-other.getCubezpos())>1){
		return false;
	}
	return true;
}


@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	long temp;
	temp = Double.doubleToLongBits(xpos);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(ypos);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(zpos);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	return result;
}

public boolean isPassablePos(){
	if (!Position.posWithinWorld(getCubexpos(), getCubexpos(), getCubeypos(), getWorld())){
		return false;
	} if (!this.getCube().isPassable()){
		return false;
	}
	return true;
}






@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Position other = (Position) obj;
	if (Double.doubleToLongBits(xpos) != Double.doubleToLongBits(other.xpos))
		return false;
	if (Double.doubleToLongBits(ypos) != Double.doubleToLongBits(other.ypos))
		return false;
	if (Double.doubleToLongBits(zpos) != Double.doubleToLongBits(other.zpos))
		return false;
	return true;
}

ArrayList<Position> getNeighbours() throws UnitException{
	ArrayList<Position> neighbours = new ArrayList<>();
	int[] pos = new int[]{-1,0,1};
	for (int x: pos){
		for (int y: pos){
			for (int z: pos){
				if (Position.isValidPos(x+this.getxpos(), y+this.getypos(), z+this.getzpos(), this.world) && (x!=0 || y!=0 || z!=0)){
					Position neighbour = new Position(this.getxpos()+x,this.getypos()+y,this.getzpos()+z, this.world);
					if (neighbour.getCube().isWalkable()){
						neighbours.add(neighbour);
					}
				}
			}
		}
	}
	return neighbours;
}


public World getWorld(){
	return this.world;
}

public String toString(){
	String str1 = Double.toString(this.getxpos());
	String str2 = Double.toString(this.getypos());
	String str3 = Double.toString(this.getzpos());
	return "["+str1+","+str2+","+str3+"]";
}
}