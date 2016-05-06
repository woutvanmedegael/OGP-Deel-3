package hillbillies.model.statement;

import java.util.List;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class MultipleStatement extends Statement{
	//WOUT
	
	private final List<Statement> statements;
	
	public MultipleStatement(List<Statement> statements){
		this.statements = statements;
	}
	
	@Override
	public Boolean execute(ContextWrapper c) throws WorldException {
		for (Statement s: this.statements){
			if (!s.execute(c))
				return false;
		}
		return true;
	}

	@Override
	public Boolean containsSelected() {
		for (Statement s: this.statements){
			if (s.containsSelected()){
				return true;
			}
		}
		return false;
	}

	

}
