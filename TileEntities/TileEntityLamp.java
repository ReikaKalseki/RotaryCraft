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

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import Reika.DragonAPI.Base.OneSlotMachine;
import Reika.DragonAPI.Instantiable.BlockArray;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.InertIInv;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityLamp extends RotaryCraftTileEntity implements ISidedInventory, InertIInv, RangedEffect, OneSlotMachine {

	private BlockArray light = new BlockArray();

	private boolean canlight;

	private ItemStack[] inv = new ItemStack[1];

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.LAMP.ordinal();
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
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.updateCoil();

		if (world.isRemote)
			return;

		if (!canlight) {
			this.goDark();
			return;
		}

		if (light.isEmpty()) {
			light.addSphere(world, x, y, z, 0, this.getRange());
			light.addSphere(world, x, y, z, RotaryCraft.lightblock.blockID, this.getRange());
			return;
		}
		//int[] xyz = light.getNextAndMoveOn();
		for (int n = 0; n < light.getSize(); n++) {
			int[] xyz = light.getNthBlock(n);
			if (world.getBlockId(xyz[0], xyz[1], xyz[2]) == 0)
				world.setBlock(xyz[0], xyz[1], xyz[2], RotaryCraft.lightblock.blockID, 15, 3);
			worldObj.updateAllLightTypes(xyz[0], xyz[1], xyz[2]);
		}
	}

	private void goDark() {
		for (int n = 0; n < light.getSize(); n++) {
			int[] xyz = light.getNthBlock(n);
			if (worldObj.getBlockId(xyz[0], xyz[1], xyz[2]) == RotaryCraft.lightblock.blockID)
				worldObj.setBlock(xyz[0], xyz[1], xyz[2], 0);
			worldObj.updateAllLightTypes(xyz[0], xyz[1], xyz[2]);
		}
	}

	private void updateCoil() {
		if (inv[0] == null) {
			canlight = false;
			return;
		}
		if (inv[0].itemID != ItemRegistry.SPRING.getShiftedID()) {
			canlight = false;
			return;
		}
		if (inv[0].getItemDamage() <= 0) {
			canlight = false;
			return;
		}
		tickcount++;
		int dmg = inv[0].getItemDamage();
		if (tickcount > 120) {
			ItemStack is = new ItemStack(ItemRegistry.SPRING.getShiftedID(), 1, dmg-1);
			inv[0] = is;
			tickcount = 0;
		}
		canlight = true;
	}

	@Override
	public int getRange() {
		return 4;
	}

	@Override
	public int getMaxRange() {
		return 12;
	}

	public void clearAll() {
		for (int k = 0; k < light.getSize(); k++) {
			int[] ijk = light.getNthBlock(k);
			worldObj.setBlock(ijk[0], ijk[1], ijk[2], 0);
		}
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack is) {
		return is.itemID == ItemRegistry.SPRING.getShiftedID();
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	public ItemStack decrStackSize(int par1, int par2)
	{
		return ReikaInventoryHelper.decrStackSize(this, par1, par2);
	}

	public ItemStack getStackInSlotOnClosing(int par1)
	{
		return ReikaInventoryHelper.getStackInSlotOnClosing(this, par1);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
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
	}

}
