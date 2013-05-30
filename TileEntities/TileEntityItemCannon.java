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

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaItemHelper;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;

public class TileEntityItemCannon extends TileEntityInventoriedPowerReceiver {

	private ItemStack[] inv = new ItemStack[9];
	public int[] target = new int[3];

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return j == 0;
	}

	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	public ItemStack decrStackSize(int par1, int par2)
	{
		if (inv[par1] != null) {
			if (inv[par1].stackSize <= par2) {
				ItemStack itemstack = inv[par1];
				inv[par1] = null;
				return itemstack;
			}
			ItemStack itemstack1 = inv[par1].splitStack(par2);
			if (inv[par1].stackSize == 0)
				inv[par1] = null;

			return itemstack1;
		}
		else
			return null;
	}

	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (inv[par1] != null) {
			ItemStack itemstack = inv[par1];
			inv[par1] = null;
			return itemstack;
		}
		else
			return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
	}

	@Override
	public boolean isStackValidForSlot(int slot, ItemStack is) {
		return true;
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
		return MachineRegistry.ITEMCANNON.ordinal();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getPowerBelow();
		if (power < MINPOWER)
			return;
		if (tickcount < 8)
			return;
		ItemStack is = this.getFirstStack();
		if (is == null)
			return;
		if (this.getTargetTE() == null) {
			//ReikaChatHelper.write("Item Cannon At "+xCoord+", "+yCoord+", "+zCoord+" has an invalid target!");
			//ReikaChatHelper.writeBlockAtCoords(world, target[0], target[1], target[2]);
			return;
		}
		if (!ReikaInventoryHelper.hasSpaceFor(is, this.getTargetTE()))
			return;
		tickcount = 0;
		//ReikaJavaLibrary.pConsole(target[0]+"   "+target[1]+"   "+target[2]);
		this.fire(world, x, y, z);
	}

	private void fire(World world, int x, int y, int z) {
		if (world.isRemote)
			return;
		double v = 4;
		ItemStack is = this.getFirstStack();
		if (is == null)
			return;
		int slot = ReikaInventoryHelper.locateInInventory(is, inv, false);
		ReikaInventoryHelper.decrStack(slot, inv);
		EntityItem ei = new EntityItem(world, x+0.5, y+1.125, z+0.5, is);
		double dx = target[0]-x;
		double dy = target[1]-y;
		double dz = target[2]-z;
		double dd = ReikaMathLibrary.py3d(dx, dy, dz);
		ei.setVelocity(dx/dd*v, dy/dd*v, dz/dd*v);
		ei.delayBeforeCanPickup = 10;
		ei.lifespan = 5;
		if (!world.isRemote)
			world.spawnEntityInWorld(ei);
		world.playSoundEffect(x+0.5, y+0.5, z+0.5, "random.explode", 1, 1);
		TileEntityItemCannon ii = this.getTargetTE();
		if (!world.isRemote)
			ReikaInventoryHelper.addToIInv(is, this.getTargetTE());
	}

	private TileEntityItemCannon getTargetTE() {
		TileEntity te = worldObj.getBlockTileEntity(target[0], target[1], target[2]);
		if (!(te instanceof TileEntityItemCannon))
			return null;
		return (TileEntityItemCannon)te;
	}

	private ItemStack getFirstStack() {
		for (int i = 0; i < inv.length; i++) {
			if (inv[i] != null) {
				ItemStack is = inv[i].copy();
				return ReikaItemHelper.getSizedItemStack(is, 1);
			}
		}
		return null;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		target = NBT.getIntArray("targetxyz");
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
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setIntArray("targetxyz", target);
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
}
