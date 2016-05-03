package hillbillies.model.expressions;

import java.util.Set;
import java.util.function.Predicate;

import hillbillies.model.Dijkstra;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.HillbilliesObject;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.Cube;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class EnemyExpression extends UnitExpression {

	
	
	@Override
	public Unit evaluate(World world, Unit unit, Position selectedCube) throws WorldException {
		Predicate<Cube> myPredicate = new Predicate<Cube>(){

			@Override
			public boolean test(Cube t) {
				for (HillbilliesObject h : t.getObjectsOnThisCube()){
					if (h instanceof Unit){
						if (((Unit) h).getFaction()!=unit.getFaction()){
							return true;
						}
					}
				}
				return false;
			}
			
		};
		Dijkstra mijnDijkstra = new Dijkstra(myPredicate, unit);
		Set<Unit> units = world.getUnits();
		units.stream().
			filter(u -> u.getFaction()!=unit.getFaction());
		Unit enemy = null;
		for(Unit u: units){
		   if (unit.distanceTo(enemy)>unit.distanceTo(u)){
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
