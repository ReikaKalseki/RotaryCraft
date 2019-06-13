/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Engine;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RedstoneCycleTracker;
import Reika.RotaryCraft.Auxiliary.Interfaces.MagnetizationCore;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Items.Tools.ItemEngineUpgrade.Upgrades;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityACEngine extends TileEntityEngine implements MagnetizationCore {

	private final RedstoneCycleTracker redstone = new RedstoneCycleTracker(3);

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

		redstone.update(world, x, y, z);
		boolean ac = redstone.isAlternating();

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
		torque = (int)(0.0625*ReikaMathLibrary.logbase(mag, 2)*this.getEngineType().getTorque()/dd);
		omega = (int)(0.0625*ReikaMathLibrary.logbase(mag, 2)*this.getEngineType().getSpeed()/dd/4D);
		power = (long)omega*(long)torque;
	}

	@Override
	public int getFuelLevel() {
		return 0;
	}

	@Override
	protected void affectSurroundings(World world, int x, int y, int z, int meta) {

	}

	@Override
	public int getCoreMagnetization() {
		return inv[0] != null && inv[0].stackTagCompound != null ? inv[0].stackTagCompound.getInteger("magnet") : 0;
	}

	@Override
	public void addRedstoneUpgrade() {
		redstone.addIntegrated();
	}

	@Override
	public boolean hasRedstoneUpgrade() {
		return redstone.hasIntegrated();
	}

	@Override
	public void upgrade(ItemStack is) {
		this.addRedstoneUpgrade();
	}

	@Override
	public boolean canUpgradeWith(ItemStack item) {
		return !this.hasRedstoneUpgrade() && ItemRegistry.UPGRADE.matchItem(item) && item.getItemDamage() == Upgrades.REDSTONE.ordinal();
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setBoolean("redstoneUpgrade", redstone.hasIntegrated());
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		redstone.reset();
		if (NBT.getBoolean("redstoneUpgrade"))
			redstone.addIntegrated();
	}

	@Override
	public void breakBlock() {
		super.breakBlock();
		if (this.hasRedstoneUpgrade()) {
			ReikaItemHelper.dropItem(worldObj, xCoord+0.5, yCoord+0.5, zCoord+0.5, ItemRegistry.UPGRADE.getStackOfMetadata(Upgrades.REDSTONE.ordinal()));
		}
	}

}
