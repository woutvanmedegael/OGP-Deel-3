package hillbillies.model;

import java.util.Comparator;
import java.util.TreeSet;

public class Testing {

	static class Node{
        public int hvalue;
        public String name;
        public Node(int x, String name){
            this.hvalue = x;
            this.name = name;
        }
        
        @Override 
        public String toString(){
        	return this.name;
        }
    }
    
	private static TreeSet<Node> openset = new TreeSet<Node>(new Comparator<Node>(){
		public int compare (Node node1, Node node2){
			return (int) (node1.hvalue-node2.hvalue);
		}
	});
	
     public static void main(String []args){
        openset.add(new Node(5, "eerste node"));
        System.out.println(openset);
        openset.add(new Node(6, "tweede node"));
        System.out.println(openset);
        openset.add(new Node(3, "derde node"));
        System.out.println(openset);
     }

}
