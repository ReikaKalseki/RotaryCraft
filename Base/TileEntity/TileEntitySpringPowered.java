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

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import Reika.RotaryCraft.API.TensionStorage;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;

public abstract class TileEntitySpringPowered extends InventoriedRCTileEntity implements ConditionalOperation {

	protected ItemStack[] inv = new ItemStack[this.getSizeInventory()];

	public boolean isCreativeMode;

	public abstract int getBaseDischargeTime();

	protected final int getUnwindTime() {
		if (isCreativeMode)
			return Integer.MAX_VALUE;
		ItemStack is = inv[this.getCoilSlot()];
		int base = this.getBaseDischargeTime();
		return base*((TensionStorage)is.getItem()).getStiffness(is);
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack is) {
		return is.getItem() instanceof TensionStorage && i == this.getCoilSlot();
	}

	@Override
	public final boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return itemstack.getItemDamage() == 0 && i == this.getCoilSlot();
	}

	public int getCoilSlot() {
		return 0;
	}

	protected final ItemStack getDecrementedCharged() {
		ItemStack in = inv[this.getCoilSlot()];
		if (isCreativeMode)
			return in;
		return new ItemStack(in.itemID, in.stackSize, in.getItemDamage()-1);
	}

	protected final boolean hasCoil() {
		if (isCreativeMode)
			return true;
		ItemStack is = inv[this.getCoilSlot()];
		if (is == null)
			return false;
		Item i = is.getItem();
		return is.getItemDamage() > 0 && i instanceof TensionStorage;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public final ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	@Override
	public final void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
	}

	@Override
	public final int getInventoryStackLimit() {
		return 1;
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		NBTTagList nbttaglist = NBT.getTagList("Items");
		inv = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < inv.length)
			{
				inv[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}

		isCreativeMode = NBT.getBoolean("creative");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inv.length; i++)
		{
			if (inv[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				inv[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		NBT.setTag("Items", nbttaglist);

		NBT.setBoolean("creative", isCreativeMode);
	}

	@Override
	public boolean areConditionsMet() {
		return this.hasCoil();
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Coil";
	}
}
