package hillbillies.model.expressions;

import java.util.function.Predicate;

import hillbillies.model.exceptions.TaskInterruptionException;
import hillbillies.model.exceptions.WorldException;
import hillbillies.model.hillbilliesobject.HillbilliesObject;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.task.ContextWrapper;
import hillbillies.model.util.Position;
import hillbillies.model.world.Cube;

public class AnyExpression extends UnitExpression{

	@Override
	public Unit evaluateUnit(ContextWrapper c) throws WorldException, TaskInterruptionException{
		Predicate<Cube> myPredicate = new Predicate<Cube>(){

			@Override
			public boolean test(Cube t) {
				for (HillbilliesObject h : t.getObjectsOnThisCube()){
					if (h instanceof Unit && h!=c.getExecutingUnit()){
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
			if (h instanceof Unit){
				return (Unit) h;
			}
		}
		throw new TaskInterruptionException();		
	}


		

	}

	


