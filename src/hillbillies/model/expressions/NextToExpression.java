package hillbillies.model.expressions;

import java.util.ArrayList;
import java.util.Random;

import hillbillies.model.Position;
import hillbillies.model.SyntaxException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;

public class NextToExpression extends PositionExpression{
	//WOUT
	
	private PositionExpression position;
	
	public NextToExpression(Expression pos) throws SyntaxException{
		if (!(pos instanceof PositionExpression)){
			throw new SyntaxException();
		}
		this.position = (PositionExpression) pos;
	}
	
	
	@Override
	public Position evaluate(World world, Unit unit, Position selectedCube) throws WorldException {
		ArrayList<Position> positions = new ArrayList<>();
		int xpos = (int)unit.getxpos();
		int ypos = (int)unit.getypos();
		int zpos = (int)unit.getzpos();
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

	

}
