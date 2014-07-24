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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.Interfaces.UpgradeableMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityGasEngine extends TileEntityEngine implements UpgradeableMachine {

	@Override
	public void upgrade() {
		NBTTagCompound NBT = new NBTTagCompound();
		type = EngineType.SPORT;
		this.writeToNBT(NBT);
		worldObj.setBlock(xCoord, yCoord, zCoord, 0);
		worldObj.setBlock(xCoord, yCoord, zCoord, this.getTileEntityBlockID(), type.ordinal(), 3);
		TileEntityEngine te = (TileEntityEngine)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord);
		te.readFromNBT(NBT);
		this.syncAllData(true);
		te.syncAllData(true);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public boolean canUpgradeWith(ItemStack item) {
		return item.itemID == ItemRegistry.UPGRADE.getShiftedID() && item.getItemDamage() == 0;
	}

	@Override
	protected void consumeFuel() {
		fuel.removeLiquid(this.getConsumedFuel());
	}

	@Override
	protected void internalizeFuel() {
		if (inv[0] != null && fuel.getLevel()+FluidContainerRegistry.BUCKET_VOLUME <= FUELCAP) {
			if (inv[0].itemID == ItemRegistry.ETHANOL.getShiftedID()) {
				ReikaInventoryHelper.decrStack(0, inv);
				fuel.addLiquid(1000, RotaryCraft.ethanolFluid);
			}
		}
	}

	@Override
	protected boolean getRequirements(World world, int x, int y, int z, int meta) {
		if (fuel.isEmpty())
			return false;
		return true;
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

		SoundRegistry.CAR.playSoundAtBlock(world, x, y, z, 0.33F*volume, 0.9F*pitchMultiplier);
	}

	@Override
	public int getFuelLevel() {
		return fuel.getLevel();
	}

	@Override
	protected void affectSurroundings(World world, int x, int y, int z, int meta) {

	}

}
