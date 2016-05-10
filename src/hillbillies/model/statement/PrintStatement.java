package hillbillies.model.statement;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.expressions.BooleanExpression;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.IBooleanExpression;
import hillbillies.model.expressions.IPositionExpression;
import hillbillies.model.expressions.IUnitExpression;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class PrintStatement<T extends Expression<?>> extends Statement{
	//ADRIAAN
	@Override
	public Boolean executeNext(ContextWrapper c) throws WorldException {
		if (stuffToPrint instanceof IBooleanExpression){
			try{
				System.out.println(((IBooleanExpression) stuffToPrint).evaluateBoolean(c));
			} catch (WrongVariableException w){
			}
		}
		if (stuffToPrint instanceof IPositionExpression){
			try{
				System.out.println(((IPositionExpression) stuffToPrint).evaluatePosition(c));
			} catch (WrongVariableException w){
			}
		}
		if (stuffToPrint instanceof IUnitExpression){
			try{
				System.out.println(((IUnitExpression) stuffToPrint).evaluateUnit(c));
			} catch (WrongVariableException w){
			}
		}
		this.setExecuted(true);
		return true;
	}

	@Override
	public Boolean containsSelected() {
		// TODO Auto-generated method stub
		return stuffToPrint.containsSelected();
	}
	private final T stuffToPrint;
	public PrintStatement(T value) throws WorldException{
		if (value==null){
			throw new WorldException();
		}
		this.stuffToPrint = value;
	}
	
	public Expression<?> getExpression(){
		return this.stuffToPrint;
	}


}
