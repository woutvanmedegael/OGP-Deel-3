package hillbillies.model;
import java.util.ArrayList;

public class Tree{
	
	private ArrayList<SubType> subTypes = new ArrayList<>();	
	
	public void addSubType(SubType subType){
		this.subTypes.add(subType);
	}
	
	public ArrayList<SubType> getSubTypes(){
		return this.subTypes;
	}
	
}
