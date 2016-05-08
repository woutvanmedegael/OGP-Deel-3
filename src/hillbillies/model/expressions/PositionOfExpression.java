package hillbillies.model.expressions;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class PositionOfExpression<T extends UnitExpression> extends PositionExpression{
	private final T unit;
	
	public PositionOfExpression(T unit) throws SyntaxException{
		this.unit = unit;
		
	}
	@Override
	public Position evaluatePosition(ContextWrapper c) throws WorldException, WrongVariableException {
		Unit myUnit = this.unit.evaluateUnit(c);
		return new Position(myUnit.getxpos(), myUnit.getypos(), myUnit.getzpos(), c.getThisWorld());
	}
	@Override
	public Boolean containsSelected() {
		return unit.containsSelected();
	}
	
	@Override
	public ArrayList<Expression<?>> getExpressions() {
		ArrayList<Expression<?>> expressions = new ArrayList<>();
		expressions.add(this.unit);
		return expressions;
	}


}
