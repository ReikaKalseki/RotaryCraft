package Reika.RotaryCraft.Auxiliary.Interfaces;

import Reika.DragonAPI.Interfaces.TileEntity.BreakAction;

public interface RedstoneUpgradeable extends UpgradeableMachine, BreakAction {

	public void addRedstoneUpgrade();
	public boolean hasRedstoneUpgrade();

	public boolean hasRedstoneSignal();
}
