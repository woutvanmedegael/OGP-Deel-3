package hillbillies.model;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import hillbillies.model.hillbilliesobject.HillbilliesObject;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.hillbilliesobject.unit.UnitException;
import hillbillies.model.world.Cube;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class testing {

	public static void main(String[] args) throws WorldException {
		int[][][] ints = new int[10][10][10];
		ints[0][1][1] = 3;
		World world = new World(ints,null);
		Unit unit1 = world.spawnUnit(false);
		Unit unit2 = world.spawnUnit(false);
		Unit unit3 = world.spawnUnit(false);
		Unit unit4 = world.spawnUnit(false);
		Unit unit5 = world.spawnUnit(false);
		Unit unit6 = world.spawnUnit(false);

	
		System.out.println("unit6");

		System.out.println(unit6.getxpos());
		System.out.println(unit6.getypos());
		System.out.println(unit6.getzpos());
		System.out.println(unit6.getFaction()==unit1.getFaction());
		Position pos = new Position(unit1.getxpos(),unit1.getypos(),unit1.getzpos(),world);
		Predicate<Cube> condition = new Predicate<Cube>(){

			@Override
			public boolean test(Cube t) {
				for (HillbilliesObject h: t.getObjectsOnThisCube()){
					if (h instanceof Unit && ((Unit) h).getFaction()==unit1.getFaction() && h!=unit1){
						return true;
					}
				}
				return false;
			}
			
		};
		Dijkstra dijkstra = new Dijkstra(condition,pos);
		Position targetPos = dijkstra.findClosestPosition();
		System.out.println(targetPos);
		
	}

}
