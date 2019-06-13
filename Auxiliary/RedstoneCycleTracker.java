package Reika.RotaryCraft.Auxiliary;

import net.minecraft.world.World;

import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.World.ReikaRedstoneHelper;

public class RedstoneCycleTracker {

	private final boolean[] lastPower;
	private boolean integratedRedstone;

	private boolean alternating;

	private boolean lastSound;

	public RedstoneCycleTracker(int l) {
		lastPower = new boolean[l];
	}

	public void update(World world, int x, int y, int z) {
		alternating = this.hasIntegrated() || ReikaRedstoneHelper.isGettingACRedstone(world, x, y, z, lastPower);
		if (this.hasIntegrated()) {
			if (world.getTotalWorldTime()%6 == 0) {
				float f = RotaryAux.isMuffled(world, x,  y, z) ? 0.05F : 0.25F;
				ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.click", f, 0.66F);
			}
		}
	}

	public boolean isAlternating() {
		return alternating;
	}

	public void addIntegrated() {
		integratedRedstone = true;
	}

	public boolean hasIntegrated() {
		return integratedRedstone;
	}

	public void reset() {
		integratedRedstone = false;
	}

}
