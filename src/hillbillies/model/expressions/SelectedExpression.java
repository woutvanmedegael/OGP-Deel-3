package hillbillies.model.expressions;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class SelectedExpression extends PositionExpression{
	//WOUT
	@Override
	public Position evaluate(ContextWrapper c) throws WorldException {
		if (c.getSelectedPos()==null){
			throw new IllegalArgumentException();
		}
		return c.getSelectedPos();
	}

	@Override
	public Boolean containsSelected() {
		return true;
	}

	

}
