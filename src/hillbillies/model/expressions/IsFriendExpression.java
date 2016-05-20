package hillbillies.model.expressions;

import hillbillies.model.exceptions.WorldException;
import hillbillies.model.exceptions.WrongVariableException;
import hillbillies.model.task.ContextWrapper;

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
