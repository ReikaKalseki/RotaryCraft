/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;

public class TileEntityBucketFiller extends TileEntityInventoriedPowerReceiver {

	public ItemStack[] inv = new ItemStack[18];
	public boolean filling = false;

	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	@Override
	public boolean isStackValidForSlot(int slot, ItemStack is) {
		if (filling)
			return is.itemID == Item.bucketEmpty.itemID;
		return is.itemID == Item.bucketLava.itemID || is.itemID == Item.bucketWater.itemID;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.BUCKETFILLER.ordinal();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {

	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
	}

}
