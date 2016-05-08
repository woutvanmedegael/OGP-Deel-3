package hillbillies.model.expressions;

import java.util.ArrayList;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class IsFriendExpression<T extends UnitExpression> extends BooleanExpression{
	//WOUT
	
		private T friend;
		
		public IsFriendExpression(T friend) throws SyntaxException{
			this.friend = friend;
			
		}
		
		@Override
		public Boolean evaluateBoolean(ContextWrapper c) throws WorldException, WrongVariableException {
			return (friend.evaluateUnit(c).getFaction()==c.getExecutingUnit().getFaction());
		}

		@Override
		public Boolean containsSelected() {
			return friend.containsSelected();
		}
		
		@Override
		public ArrayList<Expression<?>> getExpressions() {
			ArrayList<Expression<?>> expressions = new ArrayList<>();
			expressions.add(this.friend);
			return expressions;
		}

	

}
