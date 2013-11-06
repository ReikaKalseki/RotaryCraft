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

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;

public class TileEntitySorting extends TileEntityInventoriedPowerReceiver {

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getMachineIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasModelTransparency() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		// TODO Auto-generated method stub

	}

}
