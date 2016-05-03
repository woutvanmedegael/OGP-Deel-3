package hillbillies.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.hillbilliesobject.unit.UnitException;
import hillbillies.model.world.Cube;
import hillbillies.model.world.World;

public class Dijkstra {
	
	private final Predicate<Cube> condition;
	private final Position startPos = new Position(0, 0, 0, null);
	private final World world;
	private TreeSet<Position> openset = new TreeSet<Position>(new Comparator<Position>(){

		@Override
		public int compare(Position o1, Position o2) {
			if (o1.equals(o2))
				return 0;
			if (euclidianSquareDistanceToStartPos(o1)<euclidianSquareDistanceToStartPos(o2))
				return -1;
			return 1;
		}
		
	});

	private Set<Position> closedset = new HashSet<>();
	
	
	public Dijkstra(Predicate<Cube> condition, Unit unit) throws UnitException{
		if (condition==null){
			throw new IllegalArgumentException();
		}
		Position pos = new Position(unit.getxpos(),unit.getypos(),unit.getzpos(),unit.getWorld());
		this.condition = condition;
		this.startPos.setPositionAt(startPos);
		this.startPos.setToMiddleOfCube();
		this.startPos.setWorld(startPos.getWorld());
		if (!startPos.getCube().isPassable()){
			throw new IllegalArgumentException();
		}
		this.world = startPos.getWorld();
	}
	
	public Position findClosestPosition() throws UnitException{
		this.openset.add(this.startPos);
		while (this.openset.size()>0){
			Position currentpos = openset.pollFirst();
			this.closedset.add(currentpos);
			if (condition.test(currentpos.getCube())){
				return currentpos;
			}
			ArrayList<Position> neighbours = currentpos.getNeighbours(c->c.isPassable());
			for (Position pos: neighbours){
				if (!(this.closedset.contains(pos) || this.openset.contains(pos))){
					if (condition.test(pos.getCube())){
						return pos;
					}
					if (pos.getCube().isWalkable()){
						openset.add(pos);
					}
					else {
						closedset.add(pos);
					}
				}
			}
			
		}
		return null;
	}
	
	private int euclidianSquareDistanceToStartPos(Position pos){
		return (int) (Math.pow((pos.getxpos()-this.startPos.getxpos()), 2)+
				Math.pow((pos.getypos()-this.startPos.getypos()), 2)+
				Math.pow((pos.getzpos()-this.startPos.getzpos()), 2));
	}
}
