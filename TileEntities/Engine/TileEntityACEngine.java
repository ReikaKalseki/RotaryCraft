/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Engine;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaRedstoneHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Registry.ConfigRegistry;
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
		if (is.itemID != ItemStacks.shaftcore.itemID || is.getItemDamage() != ItemStacks.shaftcore.getItemDamage())
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
			m--;
			is.stackTagCompound.setInteger("magnet", m);
		}

		return ac;
	}

	@Override
	protected void playSounds(World world, int x, int y, int z, float pitchMultiplier) {
		soundtick++;
		if (!ConfigRegistry.ENGINESOUNDS.getState())
			return;
		float volume = 1;
		if (this.isMuffled(world, x, y, z)) {
			volume = 0.3125F;
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
