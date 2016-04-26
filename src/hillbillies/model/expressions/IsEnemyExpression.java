package hillbillies.model.expressions;

import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class IsEnemyExpression extends BooleanExpression{
	//WOUT
	
	private UnitExpression enemy;
	
	public IsEnemyExpression(Expression enemy) throws SyntaxException{
		if (!(enemy instanceof UnitExpression)){
			throw new SyntaxException();
		}
		this.enemy = (UnitExpression) enemy;
		
	}
	
	@Override
	public Boolean evaluate(World world, Unit unit, Position selectedCube) throws WorldException {
		return (enemy.evaluate(world, unit, selectedCube).getFaction()!=unit.getFaction());
	}

	

}
