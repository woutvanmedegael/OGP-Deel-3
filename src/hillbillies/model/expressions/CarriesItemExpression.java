package hillbillies.model.expressions;

import hillbillies.model.exceptions.WorldException;
import hillbillies.model.exceptions.WrongVariableException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.task.ContextWrapper;

public class CarriesItemExpression<T extends IUnitExpression> extends BooleanExpression{

	private final T unit;

	public CarriesItemExpression(T unit) throws WorldException{
		if (unit==null){
			throw new WorldException();
		}
		this.unit = unit;
	}
	
	@Override
	public Boolean evaluateBoolean(ContextWrapper c) throws WorldException, WrongVariableException {
		Unit myUnit = this.unit.evaluateUnit(c);
		return (myUnit.isCarryingBoulder() || myUnit.isCarryingLog());
	}

	public T getUnit() {
		return unit;
	}

	
	
	@Override
	public IUnitExpression[] getExpressions() {
		return new IUnitExpression[]{unit};
	}

}
