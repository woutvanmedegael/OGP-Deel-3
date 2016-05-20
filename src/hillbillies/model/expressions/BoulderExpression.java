package hillbillies.model.expressions;

import java.util.function.Predicate;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Dijkstra;
import hillbillies.model.Position;
import hillbillies.model.TaskInterruptionException;
import hillbillies.model.world.Cube;
import hillbillies.model.world.WorldException;

public class BoulderExpression extends PositionExpression {
	
	
	@Override
	public Position evaluatePosition(ContextWrapper c) throws WorldException{
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
		Position pos = dijkstra.findClosestPosition();
		if (pos==null){
			throw new TaskInterruptionException();
		}
		return pos;
	}

	



	

}
