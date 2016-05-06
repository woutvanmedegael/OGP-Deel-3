package hillbillies.model.expressions;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class IsFriendExpression<T extends UnitExpression> extends BooleanExpression{
	//WOUT
	
		private T friend;
		
		public IsFriendExpression(T friend) throws SyntaxException{
			this.friend = friend;
			
		}
		
		@Override
		public Boolean evaluate(ContextWrapper c) throws WorldException {
			return (friend.evaluate(c).getFaction()==c.getExecutingUnit().getFaction());
		}

		@Override
		public Boolean containsSelected() {
			return friend.containsSelected();
		}

	

}
