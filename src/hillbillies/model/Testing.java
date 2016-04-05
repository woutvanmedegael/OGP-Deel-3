package hillbillies.model;

import java.util.Comparator;
import java.util.TreeSet;

import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.hillbilliesobject.unit.PathFinding.Node;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class Testing {
	 private static int[][][] world1 = new int[3][3][3];
	 static
	 {
	 for (int x = 0; x<3; x++){
		 for (int y = 0; y<3; y++){
			 for (int z = 0; z<3; z++){
				 if (z==0){
					 world1[x][y][z] = 1;
				 }
			 }
		 }
		 
	 }
	 world1[2][2][1] = 1;
	 }
	 
	 private static int[][][] world2 = new int[5][5][5];
	 static
	 {
	 for (int x = 0; x<5; x++){
		 for (int y = 0; y<5; y++){
			 for (int z = 0; z<5; z++){
				
			 }
		 }
		 
	 }
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
				return "("+gvalue+","+fvalue+")";
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
     public static void main(String []pargs) throws WorldException{
    	 World world = new World(world1, null);
    	 Unit unit = world.spawnUnit(false);
    	 unit.moveTo(4, 4, 1);
    	 //advanceSeconds(unit,10);
//    	 for (int x = 0; x<5; x++){
//    		 for (int y = 0; y<5; y++){
//    			 for (int z = 0; z<5; z++){
//    				 if (z==0 && world.getCube(x,y,z).isPassable()){
//    				 }
//    				 if (z!=0 && !world.getCube(x, y, z).isPassable()){
//    				 }
//    				 if (z==1 && !world.getCube(x, y, z).isWalkable()){
//    				 }
//    				 if (z!=1 && world.getCube(x, y, z).isWalkable()){
//    				 }
//    			 }
//    		 }
//    	 }
    				 
     }
     
//	 public static void main(String []args){
//		 TreeSet<Node> openset = new TreeSet<Node>();
//		 Testing t = new Testing();
//		 Node node1 = t.new Node(null,1,5,null);
//		 Node node2 = t.new Node(null,2,3,null);
//		 Node node3 = t.new Node(null,3,6,null);
//		 openset.add(node1);

//		 openset.add(node2);

//		 openset.add(node3);

//	 }
	 
     public static void advanceSeconds(Unit unit, double time) throws WorldException{
 		int steps = (int) (time/0.01);
 		for (int i = 0; i<steps;i++){
 			unit.advanceTime(0.01);
 		}
 	}

}
