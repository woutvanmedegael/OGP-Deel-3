package hillbillies.model.expressions;

import java.util.ArrayList;
import java.util.Random;

import hillbillies.model.exceptions.TaskInterruptionException;
import hillbillies.model.exceptions.WorldException;
import hillbillies.model.exceptions.WrongVariableException;
import hillbillies.model.task.ContextWrapper;
import hillbillies.model.util.Position;
import hillbillies.model.world.World;

public class NextToExpression<T extends IPositionExpression> extends PositionExpression{
	
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
			for (int y = ypos-1; y<ypos+2; y++){
				for (int z = zpos-1; z<zpos+2; z++){
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
			throw new TaskInterruptionException();
		}
		int index = rand.nextInt(positions.size());
		return positions.get(index);
	}


	
	
	@Override
	public IPositionExpression[] getExpressions() {
		return new IPositionExpression[]{position};
	}

	

}
