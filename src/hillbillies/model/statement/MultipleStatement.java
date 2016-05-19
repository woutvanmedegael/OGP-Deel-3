package hillbillies.model.statement;

import java.util.ArrayList;
import java.util.List;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.expressions.IBooleanExpression;
import hillbillies.model.expressions.IExpression;
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
		boolean b = true;
		for (Statement s: this.statements){
			if (!s.hasBeenExecuted()){
				b = (s.executeNext(c));
				break;
			}
		}
		if (statements.get(statements.size()-1).hasBeenExecuted()){
			this.setExecuted(true);
		}
		return b;
	}

	
	
	@Override
	public Statement[] getStatements() {
		Statement[] statementlist = statements.toArray(new Statement[statements.size()]);
		return statementlist;
	}

	@Override
	public IExpression[] getExpressions() {
		return new IExpression[]{};
	}
	
	@Override
	public void setExecuted(Boolean b){
		super.setExecuted(b);
		for (Statement s: this.statements){
			s.setExecuted(b);
		}
	}

	@Override
	public Statement copy() throws WorldException {
		ArrayList<Statement> stats = new ArrayList<>();
		for (Statement s: this.statements){
			stats.add(s.copy());
		}
		return new MultipleStatement(stats);
	}

	

}
