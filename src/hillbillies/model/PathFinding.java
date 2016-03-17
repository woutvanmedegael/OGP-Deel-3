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
	private TreeSet<Node> openset = new TreeSet<Node>(new Comparator<Node>(){
		public int compare (Node node1, Node node2){
			return (int) (node1.fvalue-node2.fvalue);
		}
	});
	private Map<Node,Node> parentposition = new HashMap<>();
	public PathFinding(World world){
		this.world = world;
	}
	
	public class Node{
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
	}
	
	public void CalculateFastestPath(Position start, Position target){
		openset.add(new Node(start,0,start.getEstimatedTimeTo(target),null));
		while (openset.size()>0){
			Node current = openset.first();
			openset.remove(current);
			closedset.add(current.getPosition());
			if (current.getPosition().Equals(target)){
				calculatePath(current);
				break;
			}
			for (Position pos : getNeighbours(current.getPosition())){
				if (closedset.contains(pos)){
					continue;
				}
				double tempGscore = current.getGValue()+current.getPosition().getEstimatedTimeTo(pos);
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
	
	private ArrayList<Position> getNeighbours(Position current){
		ArrayList<Position> neighbours = new ArrayList<>();
		int[] pos = new int[]{-1,1};
		for (int x: pos){
			for (int y: pos){
				for (int z: pos){
					if (world.getCube(x, y, z).isWalkable()){
						neighbours.add(current);
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
		while (currentNode!=null){
			path.add(currentNode.getPosition());
			currentNode = currentNode.getParent();
			world.getCube(x, y, z)
		}
	}
}
