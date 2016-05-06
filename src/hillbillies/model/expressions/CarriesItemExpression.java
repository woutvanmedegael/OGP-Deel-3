package hillbillies.model.expressions;

import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class CarriesItemExpression<T extends UnitExpression> extends BooleanExpression{
	
	
	
	private final T unit;

	public CarriesItemExpression(T unit) throws SyntaxException{
		this.unit = unit;
	}
	
	@Override
	public Boolean evaluate(World world, Unit unit, Position selectedCube) throws WorldException {
		Unit myUnit = this.unit.evaluate(world, unit, selectedCube);
		return (myUnit.isCarryingBoulder() || myUnit.isCarryingLog());
	}

	public T getUnit() {
		return unit;
	}

	@Override
	public Boolean containsSelected() {
		return unit.containsSelected();
	}

}
