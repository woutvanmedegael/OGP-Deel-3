package hillbillies.model.expressions;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class IsEnemyExpression<T extends UnitExpression> extends BooleanExpression{
	//WOUT
	
	private T enemy;
	
	public IsEnemyExpression(T enemy){
		this.enemy = enemy;
		
	}
	
	@Override
	public Boolean evaluate(ContextWrapper c) throws WorldException{
		return (enemy.evaluate(c).getFaction()!=c.getExecutingUnit().getFaction());
	}

	@Override
	public Boolean containsSelected() {
		return enemy.containsSelected();
	}

	

}
