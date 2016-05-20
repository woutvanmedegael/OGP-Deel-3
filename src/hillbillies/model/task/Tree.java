package hillbillies.model.task;
import java.util.ArrayList;

public class Tree{
	
	private ArrayList<SubType> subTypes = new ArrayList<>();	
	
	void addSubType(SubType subType){
		this.subTypes.add(subType);
	}
	
	ArrayList<SubType> getSubTypes(){
		return this.subTypes;
	}
	
}
