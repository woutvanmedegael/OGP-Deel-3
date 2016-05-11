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
	
	public MultipleStatement(List<Statement> statements) throws WorldException{
		if (statements==null){
			throw new WorldException();
		}
		this.statements = statements;
	}
	
	@Override
	public Boolean executeNext(ContextWrapper c) throws WorldException, WrongVariableException {
		for (Statement s: this.statements){
			if (!s.hasBeenExecuted()){
				return (s.executeNext(c));
			}
		}
		this.setExecuted(true);
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
	
	public List<Statement> getStatements(){
		return this.statements;
	}
	
	@Override
	public void setExecuted(Boolean b){
		super.setExecuted(b);
		for (Statement s: this.statements){
			s.setExecuted(b);
		}
	}

	

}
