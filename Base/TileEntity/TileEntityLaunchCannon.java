/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;

public abstract class TileEntityLaunchCannon extends InventoriedPowerReceiver implements DiscreteFunction {

	protected ItemStack[] inventory = new ItemStack[9];

	public int velocity;
	public int phi;
	public int theta;

	public boolean targetMode = false;

	public int[] target = new int[3];

	@Override
	public final int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public final ItemStack getStackInSlot(int i) {
		return inventory[i];
	}

	@Override
	public final void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i] = itemstack;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {

	}

	protected abstract boolean fire(World world, int x, int y, int z);

	public abstract int getMaxLaunchVelocity();

	public abstract int getMaxTheta();

	public abstract double getMaxLaunchDistance();

	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		velocity = NBT.getInteger("svelocity");
		phi = NBT.getInteger("sphi");
		theta = NBT.getInteger("stheta");
		targetMode = NBT.getBoolean("istarget");
		target = NBT.getIntArray("targetxyz");
		NBTTagList nbttaglist = NBT.getTagList("Items");
		inventory = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < inventory.length)
			{
				inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("svelocity", velocity);
		NBT.setInteger("sphi", phi);
		NBT.setInteger("stheta", theta);
		NBT.setBoolean("istarget", targetMode);
		NBT.setIntArray("targetxyz", target);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inventory.length; i++)
		{
			if (inventory[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				inventory[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		NBT.setTag("Items", nbttaglist);
	}

	public int getOperationTime() {
		return 15;
	}

}
