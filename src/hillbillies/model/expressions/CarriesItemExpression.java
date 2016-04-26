package hillbillies.model.expressions;

import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class CarriesItemExpression extends BooleanExpression{
	
	private final UnitExpression unit;

	public CarriesItemExpression(Expression unit) throws SyntaxException{
		if (!(unit instanceof UnitExpression)){
			throw new SyntaxException();
		}
		this.unit = (UnitExpression) unit;
	}
	
	@Override
	public Boolean evaluate(World world, Unit unit) throws WorldException {
		Unit myUnit = this.unit.evaluate(world, unit);
		return (myUnit.isCarryingBoulder() || myUnit.isCarryingLog());
	}

	public UnitExpression getUnit() {
		return unit;
	}

}
