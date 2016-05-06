package hillbillies.model.expressions;

import java.util.function.Predicate;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Dijkstra;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.Cube;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class BoulderExpression extends PositionExpression {
	//WOUT
	
	
	@Override
	public Position evaluate(ContextWrapper c) throws WorldException {
		Predicate<Cube> myPredicate = new Predicate<Cube>(){

			@Override
			public boolean test(Cube t) {
				if (t.containsBoulder()){
					return true;
				}
				return false;
			}
			
		};
		Dijkstra dijkstra = new Dijkstra(myPredicate, c.getExecutingUnit());
		return dijkstra.findClosestPosition();
	}

	@Override
	public Boolean containsSelected() {
		return false;
	}

	

}
