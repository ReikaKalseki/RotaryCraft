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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import Reika.DragonAPI.Base.OneSlotMachine;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.ItemRegistry;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;
import Reika.RotaryCraft.Models.ModelWinder;

public class TileEntityWinder extends TileEntityInventoriedPowerReceiver implements OneSlotMachine {

	public ItemStack[] inslot = new ItemStack[1];

	//Whether in wind or unwind mode
	public boolean winding = true;
	public static final int UNWINDTORQUE = 4;
	public static final int UNWINDSPEED = 2048;

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return j == 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getIOSidesDefault(world, x, y, z, meta);
		if (inslot[0] == null) {
			if (!winding) {
				torque = 0;
				omega = 0;
			}
			return;
		}
		if (inslot[0].itemID != ItemRegistry.SPRING.getID()) {
			if (!winding) {
				torque = 0;
				omega = 0;
			}
			return;
		}
		if (winding) {
			this.getPower(false, false);
			if (tickcount < this.getWindTime())
				return;
			tickcount = 0;
			if (inslot[0].getItemDamage() >= this.getMaxWind())
				return;
			inslot[0] = new ItemStack(ItemRegistry.SPRING.getID(), 1, inslot[0].getItemDamage()+1);
			if (par5Random.nextInt((65536-inslot[0].getItemDamage())) == 0) {
				inslot[0] = null;
				world.playSoundEffect(x, y, z, "random.break", 1F, 1F);
			}
		}
		else {
			if (inslot[0].getItemDamage() <= 0) {
				omega = 0;
				torque = 0;
				power = 0;
				return;
			}
			if (tickcount < 20)
				return;
			tickcount = 0;
			inslot[0] = new ItemStack(ItemRegistry.SPRING.getID(), 1, inslot[0].getItemDamage()-1);
			omega = UNWINDSPEED;
			torque = UNWINDTORQUE;
			power = omega;
			writex = readx;
			writez = readz;
			writey = yCoord;
		}

	}

	private int getWindTime() {
		int base = (int)ReikaMathLibrary.logbase(inslot[0].getItemDamage(), 2);
		int factor = this.operationTime(omega, 0);
		return base*factor/8;
	}

	public int getMaxWind() {
		int max = torque;
		if (max > 65535) //metadata limit
			return 65535;
		return max;
	}

	public int getSizeInventory() {
		return 1;
	}

	public static boolean func_52005_b(ItemStack par0ItemStack)
	{
		return true;
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		winding = NBT.getBoolean("winding");
		NBTTagList nbttaglist = NBT.getTagList("Items");
		inslot = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < inslot.length)
			{
				inslot[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
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
		NBT.setBoolean("winding", winding);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inslot.length; i++)
		{
			if (inslot[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				inslot[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		NBT.setTag("Items", nbttaglist);
	}

	/**
	 * Returns the stack in slot i
	 */
	public ItemStack getStackInSlot(int par1)
	{
		return inslot[par1];
	}

	/**
	 * Sets the given item stack to the specified slot in the inslotentory (can be crafting or armor sections).
	 */
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		inslot[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelWinder();
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
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.WINDER.ordinal();
	}

	@Override
	public boolean isStackValidForSlot(int slot, ItemStack is) {
		return is.itemID == ItemRegistry.SPRING.getID();
	}

	@Override
	public int getRedstoneOverride() {
		if (inslot[0] == null)
			return 15;
		if (inslot[0].itemID != ItemRegistry.SPRING.getID())
			return 15;
		if (inslot[0].getItemDamage() >= torque && winding)
			return 15;
		return 0;
	}
}
