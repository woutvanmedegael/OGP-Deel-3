package hillbillies.model;
import java.util.ArrayList;
/**
 * @value
 */
import hillbillies.model.hillbilliesobject.unit.UnitException;
import hillbillies.model.world.Cube;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;
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
	 * A variable used to ease comparing two different numbers.
	 */
	private static final NbCompare nbComp = new NbCompare();
	/**
	 * The 3 coordinates: xpos the x coordinate, ypos the y coordinate, zpos the z coordinate.
	 */
	public double xpos;
	public double ypos;
	public double zpos;
	/**
	 * Returns the xpos of this position.
	 */
	public double getxpos(){
		return this.xpos;
	}
	/**
	 * Returns the ypos of this position.
	 */
	public double getypos(){
		return this.ypos;
	}
	/**
	 * Returns the zpos of this position.
	 */
	public double getzpos(){
		return this.zpos;
	}
	/**
	 * Returns the x coordinate of the cube on which this position is located.
	 */
	public int getCubexpos(){
		return (int)this.xpos;
	}
	/**
	 * Returns the y coordinate of the cube on which this position is located.
	 */
	public int getCubeypos(){
		return (int)this.ypos;
	}
	/**
	 * Returns the z coordinate of the cube on which this position is located.
	 */
	public int getCubezpos(){
		return (int)this.zpos;
	}
	/**
	 * Returns the cube on which this position is located.
	 */
	public Cube getCube(){
		return this.world.getCube(this.getCubexpos(), this.getCubeypos(), this.getCubezpos());
	}
	/**
	 * Sets this position to the given coordinates. If this position isn't a valid position an exception is thrown.
	 */
	public void setPos(double xpos, double ypos, double zpos) throws UnitException{
		if (!isValidPos(xpos, ypos, zpos)){
			throw new UnitException();
		}
		this.xpos = xpos;
		this.ypos = ypos;
		this.zpos = zpos;
	}
	
//	/**
//	 * Creates a position with the given coordinates.If the given cooridinates cannot form a valid position an exception is thrown.
//	 * @param xpos
//	 * 			| the xpos of this position
//	 * @param ypos
//	 * 			| the ypos of this position
//	 * @param zpos
//	 * 			| the zpos of this position
//	 * @effect The xpos of this new position is set to
//     *         the given xpos.
//     *       | this.setxpos(xpos)
//	 * @effect The ypos of this new position is set to
//     *         the given ypos.
//     *       | this.setypos(ypos)
//	 * @effect The zpos of this new position is set to
//     *         the given xpos.
//     *       | this.setxpos(zpos)
//	 * @throws UnitException
//	 * 			An exception is thrown if one of the arguments, -xpos,ypos or zpos- are not in the playing field.
//	 * 			|!isValidPos(xpos) OR !isValidPos(ypos)  OR !isValidPos(zpos)
//	 * 			
//	 */
//	public Position(double xpos, double ypos, double zpos) throws UnitException{
//		if (!isValidPos(xpos,ypos,zpos)){
//			throw new UnitException();
//		}
//		this.xpos = xpos;
//		this.ypos = ypos;
//		this.zpos = zpos;
//	}
	
	/**
	 *Creates a position with the given coordinates and world. If this position isn't a valid pos for this world an exception is thrown.
	 *If the world isn't null, the world is setted to the given world.
	 */
	public Position(double xpos, double ypos, double zpos, World world) throws UnitException{
		if (!Position.isValidPos(xpos,ypos,zpos,world)){
			throw new UnitException();
		}
		this.xpos = xpos;
		this.ypos = ypos;
		this.zpos = zpos;
		this.world = world;
	}
	
/**
 * Check whether the given pos are valid  for
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
/**
 *Returns true if this position is a valid position.
 */
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
/**
 * Returns true if the position with given coordinates is a valid position in the given world.
 */
public static boolean posWithinWorld(int x, int y, int z, World world){
	try{
		world.getCube(x, y, z);
		return true;
	} catch (ArrayIndexOutOfBoundsException e){
		return false;
	}
}
/**
 * Returns true if the position with given coordinates is a valid position for the given world.
 */
public static boolean isValidPos(double xpos, double ypos, double zpos, World world){
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
/**
 * Returns an estimation for the time it will take to travel from this position towards the given other position. 
 */
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
/**
 * Returns the
 * @param other
 * TODO
 * @return
 */
public double getExactTimeToAdjacent(Position other) throws UnitException{
	if (!this.isAdjacent(other)){
		throw new UnitException();
	}
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
/**
 * Sets this position to the coordinates from the given position.
 */
public void setPositionAt(Position position) throws UnitException{
	this.setPos(position.getxpos(), position.getypos(), position.getzpos());
}
/**
 * Variable registering the world of this position.
 */
private World world;
/**
 * Updates the coordinates of this position to be in the center of the corresponding cube.
 */
public void setToMiddleOfCube() throws UnitException{
	this.setPos((int)this.getxpos()+0.5, (int)this.getypos()+0.5, (int)this.getzpos()+0.5);
}
/**
 * Updates the coordinates of this position with the given parameters.
 */
public void incrPosition(double dx, double dy, double dz) throws UnitException{
	
	this.setPos(this.getxpos()+dx, this.getypos()+dy, this.getzpos()+dz);
}
/**
 * Sets the world from this position to the given world.
 */
public void setWorld(World world) throws UnitException{
	if (world != null){
		this.world = world;
	}
	if (!isValidPos(this.getxpos(),this.getypos(),this.getzpos())){
		throw new UnitException();
	}
}
/**
 * Returns true if the corresponding cube of the given position is directly adjacent to the cube of this position.
 */
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
//TODO: commentaar
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
/**
 * Returns true if this position is a passable pos. This is if this position is within world and the corresponding cube is passable.
 */
public boolean isPassablePos(){
	if (!Position.posWithinWorld(getCubexpos(), getCubexpos(), getCubeypos(), getWorld())){
		return false;
	} if (!this.getCube().isPassable()){
		return false;
	}
	return true;
}
//TODO: commentaar
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
/**
 * 
 * @return
 * @throws UnitException
 */
//TODO: rename method
public ArrayList<Position> getNeighbours() throws UnitException{
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
/**
 * Returns the world of this position.
 */
public World getWorld(){
	return this.world;
}
/**
 * Returns the string representation of this position.
 */
public String toString(){
	String str1 = Double.toString(this.getxpos());
	String str2 = Double.toString(this.getypos());
	String str3 = Double.toString(this.getzpos());
	return "["+str1+","+str2+","+str3+"]";
}
}