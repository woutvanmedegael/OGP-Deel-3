package hillbillies.model.expressions;

import hillbillies.model.exceptions.WorldException;
import hillbillies.model.exceptions.WrongVariableException;
import hillbillies.model.task.ContextWrapper;
import hillbillies.model.util.Position;

public class LiteralPositionExpression extends PositionExpression {

	private int x;
	private int y;
	private int z;
	Position pos;
	
	@Override
	public Position evaluatePosition(ContextWrapper c) throws WorldException, WrongVariableException {
		
		return new Position(this.x, this.y, this.z, c.getThisWorld());
	}
	
	public LiteralPositionExpression(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	

}
