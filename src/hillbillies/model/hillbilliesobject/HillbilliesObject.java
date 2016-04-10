package hillbillies.model.hillbilliesobject;

import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.UnitException;
import hillbillies.model.world.Cube;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;
/**
 * @value
 * @author Wout Van Medegael & Adriaan Van Gerven
 */
public abstract class HillbilliesObject {
	/**
	 * Abstract method advance time that is implemented in each hillbilliesobject.
	 */
	public abstract void advanceTime(double dt) throws UnitException, WorldException;
	
	/**
	 * The parent cube of this object.
	 */
	private Cube parentCube=null;
	/**
	 * Notify's the cube that a hillbilliesobject is currently occupying it. Removes the old parent cube.
	 * @throws WorldException 
	 */
	public void setParentCube(Position position,World world) throws WorldException{
		removeObjectFromOldParent();
		this.parentCube = position.getCube();
		this.parentCube.addObject(this);
	}
	/**
	 * Returns the parent cube of this object.
	 */
	public Cube getParentCube(){
		return this.parentCube;
	}
	/**
	 * Removes the object from the old parent cube.
	 */
	public void removeObjectFromOldParent(){
		if (parentCube != null){
		this.parentCube.deleteObject(this);}
	}
	//TODO: officieel via terminated enzo ?
	
	
}
