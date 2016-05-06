package hillbillies.model.statement;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.UnitExpression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class AttackStatement<T extends UnitExpression> extends ActionStatement{
	
	private T unitExpression;
	
	public AttackStatement(T defender){
		unitExpression = defender;
		
	}

	@Override
	public Boolean containsSelected() {
		return unitExpression.containsSelected();
	}

	@Override
	public Boolean execute(ContextWrapper context) throws WorldException {
		context.getExecutingUnit().startAttacking(unitExpression.evaluate(context));
		return true;
	}
}
