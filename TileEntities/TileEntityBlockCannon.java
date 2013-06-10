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

import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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
		super.updateTileEntity();
		tickcount++;
		if (tickcount < 1)
			return;
		tickcount = 0;
		this.getSummativeSidedPower();
		int id = 7;
		EntityFallingSand e = new EntityFallingSand(world, x+0.5, y+1+0.5, z+0.5, id);
		e.setVelocity(-1+2*par5Random.nextDouble(), 1+par5Random.nextDouble(), -1+2*par5Random.nextDouble());
		//e.shouldDropItem = false;
		e.fallTime = -1000;
		if (!world.isRemote)
			world.spawnEntityInWorld(e);
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

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);
		NBTTagList nbttaglist = NBT.getTagList("Items");
		blocks = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");
			if (byte0 >= 0 && byte0 < blocks.length)
				blocks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				blocks[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}
		NBT.setTag("Items", nbttaglist);
	}

}
