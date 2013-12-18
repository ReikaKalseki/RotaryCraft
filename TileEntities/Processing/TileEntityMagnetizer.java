/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Processing;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import Reika.DragonAPI.Base.OneSlotMachine;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaRedstoneHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityMagnetizer extends InventoriedPowerReceiver implements OneSlotMachine {

	public ItemStack[] inv = new ItemStack[1];
	private boolean[] lastPower = new boolean[3];

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return j == 0;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.MAGNETIZER.ordinal();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSidesDefault(world, x, y, z, meta);
		this.getPower(false, false);
		if (power < MINPOWER) {
			tickcount = 0;
			return;
		}
		if (omega < MINSPEED) {
			tickcount = 0;
			return;
		}
		if (!this.hasCore()) {
			tickcount = 0;
			return;
		}
		if (!ReikaRedstoneHelper.isGettingACRedstone(world, x, y, z, lastPower))
			return;
		tickcount++;
		if (!this.operationComplete(tickcount, 0))
			return;
		tickcount = 0;
		this.magnetize();
	}

	private boolean hasCore() {
		if (inv[0] == null) {
			return false;
		}
		if (inv[0].stackSize > 1) {
			return false;
		}
		if (inv[0].itemID != ItemStacks.shaftcore.itemID || inv[0].getItemDamage() != ItemStacks.shaftcore.getItemDamage()) {
			return false;
		}
		return true;
	}

	private void magnetize() {
		ItemStack is = inv[0];
		if (is.stackTagCompound == null) {
			is.stackTagCompound = new NBTTagCompound();
			is.stackTagCompound.setInteger("magnet", 1);
		}
		else if (is.stackTagCompound.hasKey("magnet")){
			int m = is.stackTagCompound.getInteger("magnet");
			m++;
			is.stackTagCompound.setInteger("magnet", m);
		}
		else {
			is.stackTagCompound.setInteger("magnet", 1);
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return (is.itemID == ItemStacks.shaftcore.itemID && is.getItemDamage() == ItemStacks.shaftcore.getItemDamage() && inv[0] == null);
	}

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
	}

	/**
	 * Writes a tile entity to NBT.  Maybe was not saving inv since seems to be acting like
	 * extends TileEntityPowerReceiver, NOT InventoriedPowerReceiver
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
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.hasCore())
			return 15;
		return 0;
	}

}
