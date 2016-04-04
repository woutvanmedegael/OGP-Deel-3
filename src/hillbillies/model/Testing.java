package hillbillies.model;

import java.util.Comparator;
import java.util.TreeSet;

import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.hillbilliesobject.unit.PathFinding.Node;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class Testing {
	 private static int[][][] ints = new int[5][5][5];
	 static
	 {
	 for (int x = 0; x<5; x++){
		 for (int y = 0; y<5; y++){
			 for (int z = 0; z<5; z++){
				 if (z==0){
					 ints[x][y][z] = 1;
				 }
			 }
		 }
		 
	 }
	 ints[2][2][1] = 1;
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
     public static void main(String []args) throws WorldException{
    	 World world = new World(ints, null);
    	 Unit unit = world.spawnUnit(false);
    	 unit.setMyPosition(new Position(0,0,1,world));
    	 unit.moveTo(4, 4, 1);
    	 //advanceSeconds(unit,10);
//    	 for (int x = 0; x<5; x++){
//    		 for (int y = 0; y<5; y++){
//    			 for (int z = 0; z<5; z++){
//    				 if (z==0 && world.getCube(x,y,z).isPassable()){
//    					 System.out.println("problem passable 1");
//    					 System.out.println(x);
//    					 System.out.println(y);
//    					 System.out.println(z);
//    				 }
//    				 if (z!=0 && !world.getCube(x, y, z).isPassable()){
//    					 System.out.println("problem passable 2");
//    					 System.out.println(x);
//    					 System.out.println(y);
//    					 System.out.println(z);
//    				 }
//    				 if (z==1 && !world.getCube(x, y, z).isWalkable()){
//    					 System.out.println("problem walkable 1");
//    					 System.out.println(x);
//    					 System.out.println(y);
//    					 System.out.println(z);
//    				 }
//    				 if (z!=1 && world.getCube(x, y, z).isWalkable()){
//    					 System.out.println("problem walkable 2");
//    					 System.out.println(x);
//    					 System.out.println(y);
//    					 System.out.println(z);
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
//		 System.out.println(openset);
//		 openset.add(node2);
//		 System.out.println(openset);
//		 openset.add(node3);
//		 System.out.println(openset);
//	 }
	 
     public static void advanceSeconds(Unit unit, double time) throws WorldException{
 		int steps = (int) (time/0.01);
 		for (int i = 0; i<steps;i++){
 			unit.advanceTime(0.01);
 		}
 	}

}
