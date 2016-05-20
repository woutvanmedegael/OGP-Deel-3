package hillbillies.model.expressions;

import hillbillies.model.ContextWrapper;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.WorldException;

public class IsFriendExpression<T extends IUnitExpression> extends BooleanExpression{
	
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
		public IUnitExpression[] getExpressions() {
			return new IUnitExpression[]{friend};
		}

	

}
