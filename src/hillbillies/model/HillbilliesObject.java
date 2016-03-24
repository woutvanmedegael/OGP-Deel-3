package hillbillies.model;

public abstract class HillbilliesObject {
	public abstract void advanceTime(double dt) throws UnitException, WorldException;
	
	
	private Cube parentCube;
	/**
	 * notify's the cube that an  hillbillie object is currently occupying it. Removes the old parent cube
	 */
	public void setParentCube(Position position,World world){
		removeObjectFromOldParent();
		this.parentCube = position.getCube();
		this.parentCube.addObject(this);
	}
	public Cube getParentCube(){
		return this.parentCube;
	}
	public void removeObjectFromOldParent(){
		if (parentCube != null){
		this.parentCube.deleteObject(this);}
	}
	//TODO: officieel via terminated enzo ?
	
	
}
