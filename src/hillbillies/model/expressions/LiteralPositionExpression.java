package hillbillies.model.expressions;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.UnitException;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.WorldException;

public class LiteralPositionExpression extends PositionExpression {
	
	final Position pos;
	
	public LiteralPositionExpression(int x, int y, int z) throws UnitException{
		pos = new Position(x,y,z,null);
	}

	@Override
	public Position evaluatePosition(ContextWrapper c) throws WorldException, WrongVariableException {
		pos.setWorld(c.getThisWorld());
		return pos;
	}

	@Override
	public Boolean containsSelected() {
		return false;
	}

	@Override
	public ArrayList<Expression<?>> getExpressions() {
		// TODO Auto-generated method stub
		return null;
	}

}
