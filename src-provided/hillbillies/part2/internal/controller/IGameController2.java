package hillbillies.part2.internal.controller;

import hillbillies.model.world.Faction;
import hillbillies.model.world.World;
import hillbillies.part1.internal.controller.IGameController1;
import hillbillies.part2.facade.IFacade;
import hillbillies.part2.internal.Part2Options;
import hillbillies.part2.internal.ui.IHillbilliesView2;
import hillbilliesobject.unit.Unit;

public interface IGameController2 extends IGameController1<IHillbilliesView2> {
	
	@Override
	IFacade getFacade();
	
	public int getFactionIndex(Faction faction);
	
	public void selectNextFaction();

	World getWorld();

	void spawnUnits(int n);

	boolean isPlayerUnit(Unit u);
	
	@Override
	Part2Options getOptions();
}
