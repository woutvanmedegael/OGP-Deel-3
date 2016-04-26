package hillbillies.model.expressions;

import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class IsFriendExpression extends BooleanExpression{
	//WOUT
	
		private UnitExpression friend;
		
		public IsFriendExpression(Expression friend) throws SyntaxException{
			if (!(friend instanceof UnitExpression)){
				throw new SyntaxException();
			}
			this.friend = (UnitExpression) friend;
			
		}
		
		@Override
		public Boolean evaluate(World world, Unit unit, Position selectedCube) throws WorldException {
			return (friend.evaluate(world, unit, selectedCube).getFaction()==unit.getFaction());
		}

	

}
