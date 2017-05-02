/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import Reika.DragonAPI.Interfaces.TileEntity.InertIInv;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
import Reika.RotaryCraft.RotaryCraft;

public abstract class InventoriedPowerReceiver extends TileEntityPowerReceiver implements ISidedInventory {

	protected ItemStack[] inv = new ItemStack[this.getSizeInventory()];

	protected void onInventoryChanged(int slot) {

	}

	public final ItemStack getStackInSlot(int par1) {
		return inv[par1];
	}

	public final void setInventorySlotContents(int par1, ItemStack is) {
		inv[par1] = is;
		this.onInventoryChanged(par1);
	}

	public final String getInventoryName() {
		return this.getMultiValuedName();
	}

	public void openInventory() {}

	public void closeInventory() {}

	@Override
	public final boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public final void markDirty() {
		blockMetadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);

		if (this.getBlockType() != Blocks.air)
		{
			worldObj.func_147453_f(xCoord, yCoord, zCoord, this.getBlockType());
		}
		//this.onInventoryChanged();
	}

	public int getInventoryStackLimit()
	{
		return 64;
	}

	public final ItemStack decrStackSize(int par1, int par2) {
		ItemStack ret = ReikaInventoryHelper.decrStackSize(this, par1, par2);
		this.onInventoryChanged(par1);
		return ret;
	}

	public final ItemStack getStackInSlotOnClosing(int par1) {
		ItemStack ret = ReikaInventoryHelper.getStackInSlotOnClosing(this, par1);
		this.onInventoryChanged(par1);
		return ret;
	}

	public final int[] getAccessibleSlotsFromSide(int var1) {
		if (this instanceof InertIInv)
			return new int[0];
		return ReikaInventoryHelper.getWholeInventoryForISided(this);
	}

	public final boolean canInsertItem(int i, ItemStack is, int side) {
		if (this instanceof InertIInv)
			return false;
		return ((IInventory)this).isItemValidForSlot(i, is);
	}

	public boolean isUseableByPlayer(EntityPlayer var1) {
		return this.isPlayerAccessible(var1);
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inv.length; i++) {
			if (inv[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setShort("Slot", (short)i);
				inv[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
				//ReikaJavaLibrary.pConsole(i+":"+inv[i]);
			}
		}

		NBT.setTag("Items", nbttaglist);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);

		NBTTagList nbttaglist = NBT.getTagList("Items", NBTTypes.COMPOUND.ID);
		inv = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			short byte0 = nbttagcompound.getShort("Slot");

			if (byte0 >= 0 && byte0 < inv.length) {
				inv[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
				//ReikaJavaLibrary.pConsole(byte0+":"+inv[byte0]);
			}
			else {
				RotaryCraft.logger.logError(this+" tried to load an inventory slot "+byte0+" from NBT!");
				//Thread.dumpStack();
			}
		}
	}
}
