package hillbillies.model.statement;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.expressions.Expression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class PrintStatement<T extends Expression<Object>> extends Statement{
	//ADRIAAN
	@Override
	public Boolean execute(ContextWrapper c) throws WorldException {
		// TODO Auto-generated method stub
		System.out.println(stuffToPrint.evaluate(c));
		return true;
	}

	@Override
	public Boolean containsSelected() {
		// TODO Auto-generated method stub
		return stuffToPrint.containsSelected();
	}
	private final T stuffToPrint;
	public PrintStatement(T value){
		this.stuffToPrint = value;
	}


}
