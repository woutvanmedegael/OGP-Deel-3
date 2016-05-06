package hillbillies.model.expressions;

import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class IsEnemyExpression<T extends UnitExpression> extends BooleanExpression{
	//WOUT
	
	private T enemy;
	
	public IsEnemyExpression(T enemy) throws SyntaxException{
		this.enemy = enemy;
		
	}
	
	@Override
	public Boolean evaluate(World world, Unit unit, Position selectedCube) throws WorldException {
		return (enemy.evaluate(world, unit, selectedCube).getFaction()!=unit.getFaction());
	}

	@Override
	public Boolean containsSelected() {
		return enemy.containsSelected();
	}

	

}
