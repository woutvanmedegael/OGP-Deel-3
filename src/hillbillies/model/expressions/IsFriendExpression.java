package hillbillies.model.expressions;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class IsFriendExpression<T extends IUnitExpression> extends BooleanExpression{
	//WOUT
	
		private T friend;
		
		public IsFriendExpression(T friend) throws WorldException{
			if (friend==null){
				throw new WorldException();
			}
			this.friend = friend;
			
		}
		
		@Override
		public Boolean evaluateBoolean(ContextWrapper c) throws WorldException, WrongVariableException {
			return (friend.evaluateUnit(c).getFaction()==c.getExecutingUnit().getFaction());
		}

		
		@Override
		public ArrayList<IExpression> getExpressions() {
			ArrayList<IExpression> expressions = new ArrayList<>();
			expressions.add(this.friend);
			return expressions;
		}

	

}
