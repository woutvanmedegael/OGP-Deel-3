package hillbillies.model.statement;

import java.util.List;

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
	public Boolean execute(World world, Unit unit, Position selectedCube) throws WorldException {
		for (Statement s: this.statements){
			if (!s.execute(world, unit, selectedCube))
				return false;
		}
		return true;
	}

	

}
