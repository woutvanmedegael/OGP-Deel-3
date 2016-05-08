package hillbillies.model.expressions;

import java.util.ArrayList;
import java.util.function.Predicate;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Dijkstra;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.Cube;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class WorkshopExpression extends PositionExpression{
	//ADRIAAN
	@Override
	public Position evaluatePosition(ContextWrapper c) throws WorldException {
		Predicate<Cube> myPredicate = new Predicate<Cube>(){

			@Override
			public boolean test(Cube t) {
				if (t.getIntTerrainType()==3){
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
	
	@Override
	public ArrayList<Expression<?>> getExpressions() {
		return new ArrayList<Expression<?>>();
	}

	

}
