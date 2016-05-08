package hillbillies.model.expressions;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class IsEnemyExpression<T extends UnitExpression> extends BooleanExpression{
	//WOUT
	
	private T enemy;
	
	public IsEnemyExpression(T enemy){
		this.enemy = enemy;
		
	}
	
	@Override
	public Boolean evaluateBoolean(ContextWrapper c) throws WorldException, WrongVariableException{
		return (enemy.evaluateUnit(c).getFaction()!=c.getExecutingUnit().getFaction());
	}

	@Override
	public Boolean containsSelected() {
		return enemy.containsSelected();
	}
	
	@Override
	public ArrayList<Expression<?>> getExpressions() {
		ArrayList<Expression<?>> expressions = new ArrayList<>();
		expressions.add(this.enemy);
		return expressions;
	}

	

}
