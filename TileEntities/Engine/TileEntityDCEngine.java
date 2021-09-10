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

import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.RedstoneUpgradeable;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Items.Tools.ItemEngineUpgrade.Upgrades;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import Reika.RotaryCraft.Registry.SoundRegistry;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityExtractor;

public class TileEntityDCEngine extends TileEntityEngine implements RedstoneUpgradeable {

	private boolean hasUpgrade;

	@Override
	protected void consumeFuel(float scale) {

	}

	@Override
	protected void internalizeFuel() {

	}

	@Override
	protected boolean getRequirements(World world, int x, int y, int z, int meta) {
		return hasUpgrade || this.hasRedstoneSignal();
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

	@Override
	public int getFuelLevel() {
		return 0;
	}

	@Override
	protected void affectSurroundings(World world, int x, int y, int z, int meta) {
		if (!world.isRemote) {
			if (this.getAdjacentTileEntity(write) instanceof TileEntityExtractor) {
				RotaryAchievements.DUMBEXTRACTOR.triggerAchievement(this.getPlacer());
			}
		}
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
	public void addRedstoneUpgrade() {
		hasUpgrade = true;
	}

	@Override
	public boolean hasRedstoneUpgrade() {
		return hasUpgrade;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setBoolean("redstoneUpgrade", hasUpgrade);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		hasUpgrade = NBT.getBoolean("redstoneUpgrade");
	}

	@Override
	public void breakBlock() {
		super.breakBlock();
		if (this.hasRedstoneUpgrade()) {
			ReikaItemHelper.dropItem(worldObj, xCoord+0.5, yCoord+0.5, zCoord+0.5, ItemRegistry.UPGRADE.getStackOfMetadata(Upgrades.REDSTONE.ordinal()));
		}
	}

}
