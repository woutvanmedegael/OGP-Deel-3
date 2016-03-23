package hillbillies.model;

import java.util.Comparator;
import java.util.TreeSet;

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
	 }
     public static void main(String []args) throws WorldException{
    	 World world = new World(ints, null);
    	 Unit unit = world.spawnUnit(false);
    	 unit.setMyPosition(new Position(0,0,1));
    	 unit.moveTo(3, 3, 1);
    	 advanceSeconds(unit,10);
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
     
     public static void advanceSeconds(Unit unit, double time) throws UnitException{
 		int steps = (int) (time/0.01);
 		for (int i = 0; i<steps;i++){
 			unit.advanceTime(0.01);
 		}
 	}

}
