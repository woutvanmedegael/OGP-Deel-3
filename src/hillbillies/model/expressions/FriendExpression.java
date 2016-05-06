package hillbillies.model.expressions;

import java.util.Set;
import java.util.function.Predicate;

import hillbillies.model.Dijkstra;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.HillbilliesObject;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.Cube;
import hillbillies.model.world.Faction;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class FriendExpression extends UnitExpression{
	//WOUT
	@Override
	public Unit evaluate(World world, Unit unit, Position selectedCube) throws WorldException {
		Predicate<Cube> myPredicate = new Predicate<Cube>(){

			@Override
			public boolean test(Cube t) {
				for (HillbilliesObject h : t.getObjectsOnThisCube()){
					if (h instanceof Unit && ((Unit) h).getFaction()==unit.getFaction()){
						return true;
					}
				}
				return false;
			}
			
		};
		Dijkstra dijkstra = new Dijkstra(myPredicate, unit);
		Position pos = dijkstra.findClosestPosition();
		if (pos==null){
			return null;
		}
		for (HillbilliesObject h : pos.getCube().getObjectsOnThisCube()){
			if (h instanceof Unit && ((Unit) h).getFaction()==unit.getFaction()){
				return (Unit) h;
			}
		}
		throw new WorldException();
		
	}

	@Override
	public Boolean containsSelected() {
		return false;
	}

	

}
