package hillbillies.model.expressions;

import hillbillies.model.exceptions.TaskInterruptionException;
import hillbillies.model.exceptions.WorldException;
import hillbillies.model.task.ContextWrapper;
import hillbillies.model.util.Position;

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
