package hillbillies.model.expressions;

import java.util.function.Predicate;

import hillbillies.model.exceptions.TaskInterruptionException;
import hillbillies.model.exceptions.WorldException;
import hillbillies.model.task.ContextWrapper;
import hillbillies.model.util.Position;
import hillbillies.model.world.Cube;

public class LogExpression extends PositionExpression{
	
	@Override
	public Position evaluatePosition(ContextWrapper c) throws WorldException {
		Predicate<Cube> myPredicate = new Predicate<Cube>(){

			@Override
			public boolean test(Cube t) {
				if (t.containsLog()){
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
