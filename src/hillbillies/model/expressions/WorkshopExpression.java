package hillbillies.model.expressions;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Dijkstra;
import hillbillies.model.Position;
import hillbillies.model.TaskInterruptionException;
import hillbillies.model.world.WorldException;

public class WorkshopExpression extends PositionExpression{
	
	@Override
	public Position evaluatePosition(ContextWrapper c) throws WorldException {
		
		Dijkstra dijkstra = new Dijkstra(t->t.getIntTerrainType()==3, c.getExecutingUnit());
		Position pos = dijkstra.findClosestPosition();
		if (pos==null){
			throw new TaskInterruptionException();
		}
		return pos;
	}

}
