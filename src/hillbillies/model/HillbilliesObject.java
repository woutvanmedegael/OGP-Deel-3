package hillbillies.model;

public abstract class HillbilliesObject {
	/**
	 * abstract method advance time that is implemented in each hillbillie objct
	 */
	public abstract void advanceTime(double dt) throws UnitException, WorldException;
	
	/**
	 * the parent cube of this object
	 */
	private Cube parentCube=null;
	/**
	 * notify's the cube that an  hillbillie object is currently occupying it. Removes the old parent cube
	 */
	public void setParentCube(Position position,World world){
		removeObjectFromOldParent();
		this.parentCube = position.getCube();
		this.parentCube.addObject(this);
	}
	/**
	 * returns the parent cube of this object
	 * @return
	 */
	public Cube getParentCube(){
		return this.parentCube;
	}
	/**
	 * removes the object from the old parent cube
	 */
	public void removeObjectFromOldParent(){
		if (parentCube != null){
		this.parentCube.deleteObject(this);}
	}
	//TODO: officieel via terminated enzo ?
	
	
}
