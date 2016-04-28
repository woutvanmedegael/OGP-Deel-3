package hillbillies.part2.facade;

import java.util.Set;

import hillbillies.model.hillbilliesobject.Boulder;
import hillbillies.model.hillbilliesobject.CurrentState;
import hillbillies.model.hillbilliesobject.Log;
import hillbillies.model.hillbilliesobject.unit.IllegalNameException;
import hillbillies.model.hillbilliesobject.unit.Unit;
import hillbillies.model.hillbilliesobject.unit.UnitException;
import hillbillies.model.world.Faction;
import hillbillies.model.world.World;
import hillbillies.model.world.WorldException;
import hillbillies.part2.listener.TerrainChangeListener;
import ogp.framework.util.ModelException;

public class Facade implements IFacade{

	@Override
	public Unit createUnit(String name, int[] initialPosition, int weight, int agility, int strength, int toughness,
			boolean enableDefaultBehavior) throws ModelException {
		Unit unit;
		try {
			unit = new Unit((double)initialPosition[0], (double)initialPosition[1], (double)initialPosition[2], 
					name, weight, strength, agility, toughness, enableDefaultBehavior);
		} catch (IllegalNameException e) {
			System.out.println(e.value + " is an invalid name.");
			throw new ModelException();
		} catch (UnitException e){
			throw new ModelException();
		}
		
		return unit;
	}

	@Override
	public double[] getPosition(Unit unit) throws ModelException {
		double xpos = unit.getxpos();
		double ypos= unit.getypos();
		double zpos = unit.getzpos();
		double[] pos = {xpos,ypos,zpos};
		return pos;
	}

	@Override
	public int[] getCubeCoordinate(Unit unit) throws ModelException {
		int cubeX = (int)unit.getxpos();
		int cubeY = (int)unit.getypos();
		int cubeZ = (int)unit.getzpos();
		int[] pos = {cubeX,cubeY,cubeZ};
		return pos;
	}

	@Override
	public String getName(Unit unit) throws ModelException {
		return unit.getName();
	}

	@Override
	public void setName(Unit unit, String newName) throws ModelException {
		try {
			unit.setName(newName);
		} catch (IllegalNameException e) {
			System.out.println(e.value + " is an invalid name.");
			throw new ModelException();
		}
		
	}

	@Override
	public int getWeight(Unit unit) throws ModelException {
		return unit.getWeight();
	}

	@Override
	public void setWeight(Unit unit, int newValue) throws ModelException {
		unit.setWeight(newValue);
		
	}

	@Override
	public int getStrength(Unit unit) throws ModelException {
		return unit.getStrength();
	}

	@Override
	public void setStrength(Unit unit, int newValue) throws ModelException {
		unit.setStrength(newValue);
		
	}

	@Override
	public int getAgility(Unit unit) throws ModelException {
		return unit.getAgility();
	}

	@Override
	public void setAgility(Unit unit, int newValue) throws ModelException {
		unit.setAgility(newValue);
		
	}

	@Override
	public int getToughness(Unit unit) throws ModelException {
		return unit.getToughness();
	}

	@Override
	public void setToughness(Unit unit, int newValue) throws ModelException {
		unit.setToughness(newValue);
		
	}

	@Override
	public int getMaxHitPoints(Unit unit) throws ModelException {
		return unit.getMaxHP();
	}

	@Override
	public int getCurrentHitPoints(Unit unit) throws ModelException {
		return unit.getCurrentHP();
	}

	@Override
	public int getMaxStaminaPoints(Unit unit) throws ModelException {
		return unit.getMaxSP();
	}

	@Override
	public int getCurrentStaminaPoints(Unit unit) throws ModelException {
		return unit.getCurrentSP();
	}

	@Override
	public void advanceTime(Unit unit, double dt) throws ModelException {
		try {
			unit.advanceTime(dt);
		} catch (WorldException e) {
			throw new ModelException();
		}
	}

	@Override
	public void moveToAdjacent(Unit unit, int dx, int dy, int dz) throws ModelException {
		try {
			unit.moveToAdjacent(dx, dy, dz);
		} catch (UnitException e) {
			throw new ModelException();
		}
	}

	@Override
	public double getCurrentSpeed(Unit unit) throws ModelException {
		return unit.getSpeed();
	}

	@Override
	public boolean isMoving(Unit unit) throws ModelException {
		return unit.isMoving();
	}

	@Override
	public void startSprinting(Unit unit) throws ModelException {
		try {
			unit.setToggledSprint(true);
		} catch (UnitException e) {
			throw new ModelException();
		};
	}

	@Override
	public void stopSprinting(Unit unit) throws ModelException {
		try {
			unit.setToggledSprint(false);
		} catch (UnitException e) {
			throw new ModelException();
		};
	}

	@Override
	public boolean isSprinting(Unit unit) throws ModelException {
		return unit.getToggledSprint();
	}

	@Override
	public double getOrientation(Unit unit) throws ModelException {
		return unit.getOrientation();
	}

	@Override
	public void moveTo(Unit unit, int[] cube) throws ModelException {
		try {
			unit.moveTo(cube[0], cube[1], cube[2]);
		} catch (UnitException e) {
			throw new ModelException();

		}
	}

	@Override
	public void work(Unit unit) throws ModelException {
		try {
			unit.workAt(0,0,0);
		} catch (UnitException e) {
			throw new ModelException();
		}
	}

	@Override
	public boolean isWorking(Unit unit) throws ModelException {
		return unit.getMyState()==CurrentState.WORKING;
	}

	@Override
	public void fight(Unit attacker, Unit defender) throws ModelException {
		attacker.startAttacking(defender);
	}

	@Override
	public boolean isAttacking(Unit unit) throws ModelException {
		return unit.getMyState()==CurrentState.ATTACKING;
	}

	@Override
	public void rest(Unit unit) throws ModelException {
		unit.startResting();
	}

	@Override
	public boolean isResting(Unit unit) throws ModelException {
		return unit.isResting();
	}

	@Override
	public void setDefaultBehaviorEnabled(Unit unit, boolean value) throws ModelException {
		unit.setDefaultBehaviourEnabled(value);
	}

	@Override
	public boolean isDefaultBehaviorEnabled(Unit unit) throws ModelException {
		return unit.getDefaultBehaviourEnabled();
	}

	@Override
	public World createWorld(int[][][] terrainTypes, TerrainChangeListener modelListener) throws ModelException {
		try{
			return new World(terrainTypes, modelListener);
		} catch (WorldException e){
			throw new ModelException();
		}
	}

	@Override
	public int getNbCubesX(World world) throws ModelException {
		return world.getDimensionx();
	}

	@Override
	public int getNbCubesY(World world) throws ModelException {
		return world.getDimensiony();
	}

	@Override
	public int getNbCubesZ(World world) throws ModelException {
		return world.getDimensionz();
	}

	@Override
	public void advanceTime(World world, double dt) throws ModelException {
		try{
			world.advanceTime(dt);
		} catch (WorldException e){
			throw new ModelException();
		}
	}

	@Override
	public int getCubeType(World world, int x, int y, int z) throws ModelException {
		return world.getCube(x, y, z).getIntTerrainType();
	}

	@Override
	public void setCubeType(World world, int x, int y, int z, int value) throws ModelException {
		try {
			world.changeCubeType(x, y, z, value);
		} catch (WorldException e) {
			throw new ModelException();
		}
	}

	@Override
	public boolean isSolidConnectedToBorder(World world, int x, int y, int z) throws ModelException {
		return world.isSolidConnectedToBorder(x, y, z);
	}

	@Override
	public Unit spawnUnit(World world, boolean enableDefaultBehavior) throws ModelException {
		// TODO Auto-generated method stub
		try {
			return world.spawnUnit(enableDefaultBehavior);
		} catch (WorldException e) {
			throw new ModelException();
		}
	}

	@Override
	public void addUnit(Unit unit, World world) throws ModelException {
		try {
			world.addUnit(unit);
		} catch (WorldException e) {
			throw new ModelException();
		}
	}

	@Override
	public Set<Unit> getUnits(World world) throws ModelException {
		return world.getUnits();
	}

	@Override
	public boolean isCarryingLog(Unit unit) throws ModelException {
		return unit.isCarryingLog();
	}

	@Override
	public boolean isCarryingBoulder(Unit unit) throws ModelException {
		return unit.isCarryingBoulder();
	}

	@Override
	public boolean isAlive(Unit unit) throws ModelException {
		return !(unit.getCurrentHP()==0);
	}

	@Override
	public int getExperiencePoints(Unit unit) throws ModelException {
		return unit.getExperiencePoints();
	}

	@Override
	public void workAt(Unit unit, int x, int y, int z) throws ModelException {
		try {
			unit.workAt(x,y,z);
		} catch (UnitException e) {
			throw new ModelException();
		}
		
	}

	@Override
	public Faction getFaction(Unit unit) throws ModelException {
		return unit.getFaction();
	}

	@Override
	public Set<Unit> getUnitsOfFaction(Faction faction) throws ModelException {
		// TODO Auto-generated method stub
		return faction.getUnits();
	}

	@Override
	public Set<Faction> getActiveFactions(World world) throws ModelException {
		// TODO Auto-generated method stub
		return world.getActiveFactions();
	}

	@Override
	public double[] getPosition(Boulder boulder) throws ModelException {
		return boulder.getDoublePosition();
	}

	@Override
	public Set<Boulder> getBoulders(World world) throws ModelException {
		return world.getBoulders();
	}

	@Override
	public double[] getPosition(Log log) throws ModelException {
		return log.getDoublePosition();
	}

	@Override
	public Set<Log> getLogs(World world) throws ModelException {
		return world.getLogs();
	}

}
