package hillbillies.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class PathFinding {
	
	private World world;
	private NbCompare nbcomp = new NbCompare();
	private ArrayList<Position> path = new ArrayList<>();
	private Set<Position> closedset = new HashSet<>();
	private TreeSet<Node> openset = new TreeSet<Node>();
	private Map<Node,Node> parentposition = new HashMap<>();
	public PathFinding(World world, Position start, Position target) throws UnitException{
		this.world = world;
		start.setxpos((int)start.getxpos()+0.5);
		start.setypos((int)start.getypos()+0.5);
		start.setzpos((int)start.getzpos()+0.5);
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
			return myPos.toString();
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
	
	public void calculateFastestPath(Position start, Position target) throws UnitException{
		openset.add(new Node(start,0,start.getEstimatedTimeTo(target),null));
		while (openset.size()>0){
			Node current = openset.pollFirst();
			closedset.add(current.getPosition());
			if (current.getPosition().Equals(target)){
				calculatePath(current);
				break;
			}
			ArrayList<Position> neighbours = this.getNeighbours(current.getPosition());
			for (Position pos : getNeighbours(current.getPosition())){
				if (setContains(closedset,pos)){
					continue;
				}
				double tempGscore = current.getGValue()+current.getPosition().getExactTimeToAdjacent(pos);
				Node matchingNode = getMatchingNodeFromOpenSet(pos);
				if (matchingNode!=null && tempGscore >= matchingNode.getGValue()){
					continue;
				} else if (matchingNode == null){
					matchingNode = new Node(pos, tempGscore, tempGscore, current);
					openset.add(matchingNode);
				}
				matchingNode.setParent(current);
				matchingNode.setGValue(tempGscore);
				matchingNode.setFValue(tempGscore+pos.getEstimatedTimeTo(target));
			}
		}
	}
	
	private ArrayList<Position> getNeighbours(Position current) throws UnitException{
		ArrayList<Position> neighbours = new ArrayList<>();
		int[] pos = new int[]{-1,0,1};
		for (int x: pos){
			for (int y: pos){
				for (int z: pos){
					if (Position.isValidPos(x+current.getxpos(), y+current.getypos(), z+current.getzpos(), this.world) && (x!=0 || y!=0 || z!=0)){
						Position neighbour = new Position(current.getxpos()+x,current.getypos()+y,current.getzpos()+z, this.world);
						if (world.getCube((int)neighbour.getxpos(),(int)neighbour.getypos(),(int)neighbour.getzpos()).isWalkable()){
							neighbours.add(neighbour);
						}
					}
				}
			}
		}
		return neighbours;
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
			path.add(currentNode.getPosition());
			currentNode = currentNode.getParent();
		}
		System.out.println(path);
	}
	
	public Position moveToNextPos(){
		if (this.path.size()==0){
			return null;
		}
		Position nextPos = this.path.get(this.path.size()-1);
		this.path.remove(nextPos);
		return nextPos;
	}
	public boolean setContains(Set<Position> set, Position pos){
		for (Position position : set){
			if (position.Equals(pos)){
				return true;
			}
		}
		return false;
	}
	
}
