package hillbillies.model.statement;

import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.UnitExpression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class AttackStatement extends ActionStatement{
	//ADRIAAN
	@Override
	public Boolean execute(World world, Unit unit, Position selectedCube) throws WorldException {
		// TODO checken of attack mogelijk, als de unit er niet naast staat false returnen
		unit.startAttacking(unitExpression.evaluate(world, unit, selectedCube));
		return true;
	}
	private UnitExpression unitExpression;
	
	public AttackStatement(Expression defender) throws SyntaxException{
		if  (!( defender instanceof UnitExpression)){
			throw new SyntaxException();
		}
		unitExpression = (UnitExpression)defender;
		
	}

	@Override
	public Boolean containsSelected() {
		// TODO Auto-generated method stub
		return unitExpression.containsSelected();
	}
}
