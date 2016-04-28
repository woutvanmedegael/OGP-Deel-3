package hillbillies.model.expressions;

import java.util.Set;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class EnemyExpression extends UnitExpression {

	@Override
	public Unit evaluate(World world, Unit unit, Position selectedCube) throws WorldException {
		Set<Unit> units = world.getUnits();
		units.stream().
			filter(u -> u.getFaction()!=unit.getFaction());
		Unit enemy = null;
		for(Unit u: units){
		   if ((enemy==null) || (unit.distanceTo(enemy)>unit.distanceTo(u))){
			   enemy = u;
		   }
		}
		if (enemy == null){
			throw new WorldException();
		}
		return enemy;
		
	}

	@Override
	public Boolean containsSelected() {
		return false;
	}

}
