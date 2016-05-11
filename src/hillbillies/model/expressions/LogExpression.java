package hillbillies.model.expressions;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Dijkstra;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.Log;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.Cube;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class LogExpression extends PositionExpression{
	//ADRIAAN
	
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
			c.getExecutingUnit().interrupt();
			throw new WorldException();
		}
		return pos;
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
