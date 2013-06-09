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

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;

public class TileEntityBlockCannon extends TileEntityInventoriedPowerReceiver {

	public ItemStack[] blocks = new ItemStack[27];

	@Override
	public int getSizeInventory() {
		return blocks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return blocks[i];
	}

	@Override
	public boolean isStackValidForSlot(int slot, ItemStack is) {
		return is.getItem() instanceof ItemBlock; //Blocks only
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
		return MachineRegistry.BLOCKCANNON.ordinal();
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
		blocks[i] = itemstack;
	}

}
