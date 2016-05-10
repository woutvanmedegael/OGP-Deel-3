package hillbillies.model.expressions;

import java.util.ArrayList;
import java.util.Random;

import hillbillies.model.ContextWrapper;
import hillbillies.model.Position;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.statement.WrongVariableException;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class NextToExpression<T extends PositionExpression> extends PositionExpression{
	//WOUT
	
	private T position;
	
	public NextToExpression(T pos) throws WorldException{
		if (pos==null){
			throw new WorldException();
		}
		this.position = pos;
	}
	
	
	@Override
	public Position evaluatePosition(ContextWrapper c) throws WorldException, WrongVariableException {
		Position pos = this.position.evaluatePosition(c);
		World world = c.getThisWorld();
		ArrayList<Position> positions = new ArrayList<>();
		int xpos = (int)pos.getxpos();
		int ypos = (int)pos.getypos();
		int zpos = (int)pos.getzpos();
		for (int x = xpos-1; x<xpos+2; x++){
			for (int y = ypos-1; x<ypos+2; x++){
				for (int z = zpos-1; x<zpos+2; x++){
					if (Position.posWithinWorld(x, y, z, world) && world.getCube(x, y, z).isWalkable()){
						if (x!=0 || y!=0 || z!=0){
							positions.add(new Position(x,y,z,world));
						}
					}
				}
			}
		}
		Random rand = new Random();
		if (positions.size()==0){
			//Again, unit.interrupt()?
			return null;
		}
		int index = rand.nextInt(positions.size());
		return positions.get(index);
	}


	@Override
	public Boolean containsSelected() {
		return position.containsSelected();
	}
	
	@Override
	public ArrayList<Expression<?>> getExpressions() {
		ArrayList<Expression<?>> expressions = new ArrayList<>();
		expressions.add(this.position);
		return expressions;
	}

	

}
