/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Engine;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaRedstoneHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityACEngine extends TileEntityEngine {

	/** Used in acPower */
	private boolean[] lastPower = new boolean[3];

	@Override
	protected void consumeFuel() {

	}

	@Override
	protected void internalizeFuel() {

	}

	@Override
	protected boolean getRequirements(World world, int x, int y, int z, int meta) {
		ItemStack is = inv[0];
		if (is == null)
			return false;
		if (!ReikaItemHelper.matchStacks(is, ItemStacks.shaftcore))
			return false;
		if (is.stackTagCompound == null)
			return false;
		if (!is.stackTagCompound.hasKey("magnet"))
			return false;
		if (is.stackTagCompound.getInteger("magnet") <= 0)
			return false;

		boolean ac = ReikaRedstoneHelper.isGettingACRedstone(world, x, y, z, lastPower);

		if (!world.isRemote && ac && timer.checkCap("fuel")) {
			int m = is.stackTagCompound.getInteger("magnet");
			this.magnetize(is, m-1);
		}

		return ac;
	}

	private void magnetize(ItemStack is, int amt) {
		if (amt > 0)
			is.stackTagCompound.setInteger("magnet", amt);
		else {
			is.stackTagCompound.removeTag("magnet");
			if (is.stackTagCompound.hasNoTags())
				is.stackTagCompound = null;
		}
	}

	@Override
	protected void playSounds(World world, int x, int y, int z, float pitchMultiplier, float volume) {
		soundtick++;
		if (this.isMuffled(world, x, y, z)) {
			volume *= 0.3125F;
		}

		if (soundtick < this.getSoundLength(1F/pitchMultiplier) && soundtick < 2000)
			return;
		soundtick = 0;

		SoundRegistry.ELECTRIC.playSoundAtBlock(world, x, y, z, 0.125F*volume, 1F*pitchMultiplier);
	}

	public void magneticInterference(int mag, double dd) {
		torque = (int)(0.125*ReikaMathLibrary.logbase(mag, 2)*this.getEngineType().getTorque()/dd);
		omega = (int)(0.125*ReikaMathLibrary.logbase(mag, 2)*this.getEngineType().getSpeed()/dd/4D);
		power = (long)omega*(long)torque;
	}

	@Override
	public int getFuelLevel() {
		return 0;
	}

	@Override
	protected void affectSurroundings(World world, int x, int y, int z, int meta) {

	}

}
