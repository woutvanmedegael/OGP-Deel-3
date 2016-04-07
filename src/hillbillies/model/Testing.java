package hillbillies.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;

import hillbillies.model.hillbilliesobject.CurrentState;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.hillbilliesobject.unit.UnitException;
import hillbillies.model.hillbilliesobject.unit.PathFinding.Node;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;
import hillbillies.part2.listener.TerrainChangeListener;

public class Testing {
	 private static int[][][] world1 = new int[3][3][3];
	 private static int[][][] bigWorld = new int[15][15][15];
	 static NbCompare comp = new NbCompare();

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
	 }
	 {
	 for (int x=0;x<15;x++){
		 for (int y=0;y<15;y++){
			 for (int z=0;z<15;z++){
				 if (2*z-y-x<0){
					 bigWorld[x][y][z] = 1;
				 }
				 else {
					 bigWorld[x][y][z] = 0;
				 }
			 }
		 }
	 }
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
	 
	 private class ChangeListener implements TerrainChangeListener{

			@Override
			public void notifyTerrainChanged(int x, int y, int z) {
				
			}
			
		}
	 
	 private static ChangeListener changeListener;
	 
     public static void main(String []pargs) throws WorldException{
    	World world = new World(bigWorld,changeListener);
		Unit test = new Unit(0,0,1,"Adri'aan en W\"out", 50, 50, 50, 50, false);
 		test.setWorld(world);
 		Position prevTarget = new Position(test.getxpos(),test.getypos(),test.getzpos(),world);
 		for (int i=0;i<100;i++){
 			Position target = generateWalkablePos(world);
 			test.moveTo(target.getCubexpos(), target.getCubeypos(), target.getCubezpos());
 			double est = prevTarget.getEstimatedTimeTo(target);
 			advanceSeconds(test, est*1.2);
 			assert comp.equals(test.getxpos()-0.5, target.getxpos());
 			assert comp.equals(test.getypos()-0.5, target.getypos());
 			assert comp.equals(test.getzpos()-0.5, target.getzpos());
 			prevTarget.setPositionAt(target);
 		}
     }
     public static void advanceSeconds(Unit unit, double time) throws WorldException{
 		int steps = (int) (time/0.01);
 		for (int i = 0; i<steps;i++){
 			unit.advanceTime(0.01);
 		}
 	}
     
     
    private static Position generateWalkablePos(World myWorld) throws UnitException{
 		Random random = new Random();
 		int x = random.nextInt(myWorld.getDimensionx()-1);
 		int y = random.nextInt(myWorld.getDimensiony()-1);
 		int z = random.nextInt(myWorld.getDimensionz()-1);
 		int looper = z;
 		while ((!myWorld.getCube(x, y, looper).isPassable() || (looper != 0 && myWorld.getCube(x, y, looper-1).isPassable())) && looper!=z-1){
 			looper+=1;
 			looper %= myWorld.getDimensionz()-1;
 		}
 		System.out.println(x);
 		System.out.println(y);
 		System.out.println(z);
 		System.out.println(looper);
 		if (!myWorld.getCube(x, y, looper).isPassable() || myWorld.getCube(x, y, looper-1).isPassable()){
 			return generateWalkablePos(myWorld);
 		}
 		return new Position(x,y,looper);
 	}

}
