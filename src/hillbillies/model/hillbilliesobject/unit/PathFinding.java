package hillbillies.model.hillbilliesobject.unit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import hillbillies.model.Position;
import hillbillies.model.world.World;

public class PathFinding {
	
	private World world;
	private ArrayList<Position> path = new ArrayList<>();
	private Set<Position> closedset = new HashSet<>();
	private TreeSet<Node> openset = new TreeSet<Node>();
	public PathFinding(World world, Position start, Position target) throws UnitException{
		this.world = world;
		start.setToMiddleOfCube();
		this.calculateFastestPath(start, target);
	}
	
	public class Node implements Comparable<Node>{
		private Position myPos;
		private double gvalue;
		private double fvalue;
		private Node parent;
		public Node(Position pos, double g, double f, Node parent){
			this.myPos = pos;
			this.gvalue = g;
			this.fvalue = f;
			this.parent = parent;
		
		}
		public Position getPosition(){
			return this.myPos;
		}
		public double getGValue(){
			return this.gvalue;
		}
		public void setParent(Node parent){
			this.parent = parent;
		}
		public void setGValue(double gvalue){
			this.gvalue = gvalue;
		}
		public void setFValue(double fvalue){
			this.fvalue = fvalue;
		}
		public Node getParent(){
			return this.parent;
		}
		public double getFValue(){
			return this.fvalue;
		}
		@Override
		public String toString(){
			return "("+myPos.toString()+","+gvalue+","+fvalue;
		}

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
	
	void calculateFastestPath(Position start, Position target) throws UnitException{
		openset.add(new Node(start,0,start.getEstimatedTimeTo(target),null));
		while (openset.size()>0){
			Node current = openset.pollFirst();
			closedset.add(current.getPosition());
			if (current.getPosition().Equals(target)){
				calculatePath(current);
				break;
			}
			for (Position pos : current.getPosition().getNeighbours()){
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
	
	private Node getMatchingNodeFromOpenSet(Position pos){
		for (Node node : openset){
			if (node.getPosition().Equals(pos)){
				return node;
			}
		}
		return null;
	}
	
	
	
	private void calculatePath(Node target){
		Node currentNode = target;
		while (currentNode.getParent()!=null){
			Position pos = currentNode.getPosition();
			path.add(pos);
			currentNode = currentNode.getParent();
		}
	}
	
	Position moveToNextPos(){
		if (this.path.size()==0){
			return null;
		}
		Position nextPos = this.path.get(this.path.size()-1);
		this.path.remove(nextPos);
		return nextPos;
	}
	boolean setContains(Set<Position> set, Position pos){
		for (Position position : set){
			if (position.Equals(pos)){
				return true;
			}
		}
		return false;
	}
	
	ArrayList<Position> getPath(){
		return this.path;
	}
	
}
