package hillbillies.model.expressions;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.WorldException;

public class ReadVariableExpression extends Expression<Object> implements IBooleanExpression,IPositionExpression,IUnitExpression{

	@Override
	public Boolean containsSelected() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Unit evaluateUnit(ContextWrapper c) throws WorldException, WrongVariableException {
		// TODO Auto-generated method stub
		throw new WrongVariableException();
	}
	
	@Override
	public Boolean evaluateBoolean(ContextWrapper c) throws WorldException, WrongVariableException {
		// TODO Auto-generated method stub
		throw new WrongVariableException();
	}
	
	@Override
	public Position evaluatePosition(ContextWrapper c) throws WorldException, WrongVariableException {
		// TODO Auto-generated method stub
		throw new WrongVariableException();
	}

	@Override
	public ArrayList<Expression<?>> getExpressions() {
		ArrayList<Expression<?>> list = new ArrayList<Expression<?>>();
		return list;
	}

}
