package Reika.RotaryCraft.Auxiliary;

import net.minecraft.tileentity.TileEntity;

import Reika.DragonAPI.Instantiable.Event.TileUpdateEvent;
import Reika.DragonAPI.Instantiable.Event.TileUpdateEvent.TileUpdateWatcher;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityEMP;


public class EMPTileWatcher implements TileUpdateWatcher {

	public static final EMPTileWatcher instance = new EMPTileWatcher();

	private boolean isRegistered;

	private EMPTileWatcher() {

	}

	@Override
	public int watcherSortIndex() {
		return Integer.MIN_VALUE;
	}

	@Override
	public boolean interceptTileUpdate(TileEntity te) {
		if (TileEntityEMP.isShutdown(te)) {
			return true;
		}
		return false;
	}

	public void registerTileWatcher() {
		if (isRegistered)
			return;
		isRegistered = true;
		TileUpdateEvent.addWatcher(this);
	}

	public void unregisterTileWatcher() {
		if (!isRegistered)
			return;
		isRegistered = false;
		TileUpdateEvent.removeWatcher(this);
	}

}
