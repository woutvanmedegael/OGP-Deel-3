package hillbillies.model.hillbilliesobject.unit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import hillbillies.model.Position;
import hillbillies.model.world.Cube;
import hillbillies.model.world.World;
/**
 * @value
 * @author Wout Van Medegael & Adriaan Van Gerven
 */
/**
 * Class to calculate the path from position start to position target through the given world.
 */
public class PathFinding {
	
	/**
	 * Variable registering the world.
	 */
	private World world;
	
	/**
	 * List registering the path to take.
	 */
	private ArrayList<Position> path = new ArrayList<>();
	
	/**
	 * A set keeping all considered positions.
	 */
	private Set<Position> closedset = new HashSet<>();
	
	/**
	 * A automatically sorted set keeping all nodes yet to consider.
	 */
	private TreeSet<Node> openset = new TreeSet<Node>();
	
	/**
	 * Sets the world, and starts the pathfinding algorithm.
	 */
	public PathFinding(World world, Position start, Position target, Boolean followPath) throws UnitException{
		this.world = world;
		Position pos = new Position(start.getxpos(),start.getypos(),start.getzpos(),world);
		pos.setToMiddleOfCube();
		Position targ = new Position(target.getxpos(),target.getypos(),target.getzpos(),target.getWorld());
		targ.setToMiddleOfCube();
		this.calculateFastestPath(pos, targ);
		if (followPath && !this.path.isEmpty()){
			this.path.remove(0);
		}
	}
	
	/**
	 * Helping class to keep positions and corresponding values.
	 */
	public class Node implements Comparable<Node>{
		
		/**
		 * Variable registering the position associated with this node.
		 */
		private Position myPos;
		
		/**
		 * Variable registering the time to go from the starting position to the position associated with this node.
		 */
		private double gvalue;
		
		/**
		 * Variable registering the estimated time to go from the position associated with this node to the target position added to the gvalue.
		 */
		private double fvalue;
		
		/**
		 * Variable registering the positionnode preceding the position associated with this node in the path.
		 */
		private Node parent;
		
		/**
		 * Initializes this node with the given position, gvalue, fvalue and parentnode.
		 */
		public Node(Position pos, double g, double f, Node parent){
			this.myPos = pos;
			this.gvalue = g;
			this.fvalue = f;
			this.parent = parent;
		
		}
		
		/**
		 * Returns the position associated with this node.
		 */
		public Position getPosition(){
			return this.myPos;
		}
		
		/**
		 * Returns the gvalue.
		 */
		public double getGValue(){
			return this.gvalue;
		}
		
		/**
		 * Returns the parentnode.
		 */
		public Node getParent(){
			return this.parent;
		}
		
		/**
		 * Returns the fvalue.
		 */
		public double getFValue(){
			return this.fvalue;
		}

		/**
		 * Sets a comparing method based on the fvalue.
		 */
		@Override
		public int compareTo(Node o) {
			if (this.equals(o)){
				return 0;
			}
			if (this.getFValue()<o.getFValue()){
				return -1;
			} else return 1;
		}
	}
	
	/**
	 * Calculates the fastest path from position start to position target within this world.
	 */
	void calculateFastestPath(Position start, Position target) throws UnitException{
		openset.add(new Node(start,0,start.getEstimatedTimeTo(target),null));
		while (openset.size()>0){
			Node current = openset.pollFirst();
			closedset.add(current.getPosition());
			if (current.getPosition().Equals(target)){
				calculatePath(current);
				break;
			}
			for (Position pos : current.getPosition().getNeighbours(c -> c.isWalkable())){
				if (closedset.contains(pos)){
					continue;
				}
				double tempGscore = current.getGValue()+current.getPosition().getExactTimeToAdjacent(pos);
				Node matchingNode = getMatchingNodeFromOpenSet(pos);
				if (matchingNode!=null && tempGscore >= matchingNode.getGValue()){
					continue;
				} else if (matchingNode != null){
					openset.remove(matchingNode);
				}
				matchingNode = new Node(pos, tempGscore, tempGscore+pos.getEstimatedTimeTo(target), current);
				openset.add(matchingNode);
			}
		}
	}
	
	/**
	 * Returns the node associated with the given position in the openset if there is one, otherwise returns null.
	 */
	private Node getMatchingNodeFromOpenSet(Position pos){
		for (Node node : openset){
			if (node.getPosition().Equals(pos)){
				return node;
			}
		}
		return null;
	}
	
	
	/**
	 * Rebuilds the path from target to the start.
	 */
	private void calculatePath(Node target){
		Node currentNode = target;
		while (currentNode.getParent()!=null){
			Position pos = currentNode.getPosition();
			path.add(pos);
			currentNode = currentNode.getParent();
		}
	}
	
	/**
	 * Returns the position of the next cube to move to and removes it from the path. Returns null if the path is empty.
	 */
	Position moveToNextPos(){
		if (this.path.size()==0){
			return null;
		}
		Position nextPos = this.path.get(this.path.size()-1);
		this.path.remove(nextPos);
		return nextPos;
	}
	
	/**
	 * Returns the full calculated path.
	 */
	ArrayList<Position> getPath(){
		return this.path;
	}
	
	Boolean hasArrived(){
		return this.path.isEmpty();
	}
	
	
}
