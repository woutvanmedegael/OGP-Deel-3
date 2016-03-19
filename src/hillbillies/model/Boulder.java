package hillbillies.model;

import java.util.Random;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
public class Boulder extends Load{
	/**
	 * Initialize this new boulder with given position and a random weight between 10 and 50.
	*/
	public Boulder(Position position,World world) throws WorldException {
		super(position,world);
		// TODO Auto-generated constructor stub
	}	
}