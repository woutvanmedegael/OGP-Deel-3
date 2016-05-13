package hillbillies.model.expressions;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Dijkstra;
import hillbillies.model.Position;
import hillbillies.model.TaskInterruptionException;
import hillbillies.model.hillbilliesobject.HillbilliesObject;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.Cube;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class EnemyExpression extends UnitExpression {

	
	
	@Override
	public Unit evaluateUnit(ContextWrapper c) throws WorldException {
		Predicate<Cube> myPredicate = new Predicate<Cube>(){

			@Override
			public boolean test(Cube t) {
				for (HillbilliesObject h : t.getObjectsOnThisCube()){
					if (h instanceof Unit && ((Unit) h).getFaction()!=c.getExecutingUnit().getFaction()){
						return true;
					}
				}
				return false;
			}
			
		};
		Dijkstra dijkstra = new Dijkstra(myPredicate, c.getExecutingUnit());
		Position pos = dijkstra.findClosestPosition();
		if (pos==null){
			throw new TaskInterruptionException();
		}
		for (HillbilliesObject h : pos.getCube().getObjectsOnThisCube()){
			if (h instanceof Unit && ((Unit) h).getFaction()!=c.getExecutingUnit().getFaction()){
				return (Unit) h;
			}
		}
		throw new TaskInterruptionException();
		
	}

	@Override
	public Boolean containsSelected() {
		return false;
	}
	
	@Override
	public ArrayList<Expression<?>> getExpressions() {
		return new ArrayList<Expression<?>>();
	}

}
