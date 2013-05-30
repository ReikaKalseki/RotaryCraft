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

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;
import Reika.RotaryCraft.Models.ModelVacuum;

public class TileEntityVacuum extends TileEntityInventoriedPowerReceiver implements RangedEffect, IInventory {

	public ItemStack[] inventory = new ItemStack[54];
	public int experience = 0;

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return true;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getSummativeSidedPower();
		if (worldObj.isRemote)
			return;
		power = torque*omega;
		tickcount++;
		if (power < MINPOWER)
			return;
		if (tickcount < 2)
			return;
		tickcount = 0;
		this.suck(world, x, y, z);
		this.absorb(world, x, y, z);
		//ReikaChatHelper.writeInt(this.experience);

	}

	public void spawnXP() {
		ReikaWorldHelper.splitAndSpawnXP(worldObj, xCoord-1+2*par5Random.nextFloat(), yCoord+2*par5Random.nextFloat(), zCoord-1+2*par5Random.nextFloat(), experience);
		experience = 0;
	}

	@SuppressWarnings("unused")
	public void suck(World world, int x, int y, int z) {
		AxisAlignedBB box = this.getBox(world, x, y, z);
		List inbox = world.getEntitiesWithinAABB(EntityItem.class, box);
		for (int i = 0; i < inbox.size(); i++) {
			EntityItem ent = (EntityItem)inbox.get(i);
			//Vec3 i2vac = ReikaVectorHelper.getVec2Pt(ent.posX, ent.posY, ent.posZ, x+0.5, y+0.5, z+0.5);
			//if (ReikaWorldHelper.canBlockSee(world, x, y, z, ent.posX, ent.posY, ent.posZ, this.getRange()+2)) {
			if (true || ReikaWorldHelper.canBlockSee(world, x, y, z, ent.posX, ent.posY, ent.posZ, this.getRange()+2)) {
				double dx = (x+0.5 - ent.posX);
				double dy = (y+0.5 - ent.posY);
				double dz = (z+0.5 - ent.posZ);
				double ddt = ReikaMathLibrary.py3d(dx, dy, dz);
				ent.motionX += dx/ddt/ddt/2;
				ent.motionY += dy/ddt/ddt/2;
				ent.motionZ += dz/ddt/ddt/2;
				if (ent.posY < y)
					ent.motionY += 0.1;
				if (!world.isRemote)
					ent.velocityChanged = true;
			}
		}
		List inbox2 = world.getEntitiesWithinAABB(EntityXPOrb.class, box);
		for (int i = 0; i < inbox2.size(); i++) {
			EntityXPOrb ent = (EntityXPOrb)inbox2.get(i);
			if (true || ReikaWorldHelper.canBlockSee(world, x, y, z, ent.posX, ent.posY, ent.posZ, this.getRange()+2)) {
				double dx = (x+0.5 - ent.posX);
				double dy = (y+0.5 - ent.posY);
				double dz = (z+0.5 - ent.posZ);
				double ddt = ReikaMathLibrary.py3d(dx, dy, dz);
				ent.motionX += dx/ddt/ddt/2;
				ent.motionY += dy/ddt/ddt/2;
				ent.motionZ += dz/ddt/ddt/2;
				if (ent.posY < y)
					ent.motionY += 0.1;
				if (!world.isRemote)
					ent.velocityChanged = true;
			}
		}
	}

	public void absorb(World world, int x, int y, int z) {
		if (world.isRemote)
			return;
		AxisAlignedBB close = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(0.25D, 0.25D, 0.25D);
		List closeitems = world.getEntitiesWithinAABB(EntityItem.class, close);
		for (int i = 0; i < closeitems.size(); i++) {
			EntityItem ent = (EntityItem)closeitems.get(i);
			if (ent.delayBeforeCanPickup <= 0) {
				ItemStack is = ent.getEntityItem();
				int targetslot = this.checkForStack(is);
				if (targetslot != -1) {
					if (inventory[targetslot] == null)
						inventory[targetslot] = new ItemStack(is.itemID, is.stackSize, is.getItemDamage());
					else
						inventory[targetslot].stackSize += is.stackSize;
				}
				else {
					return;
				}
				ent.setDead();
				world.playSoundEffect(x+0.5, y+0.5, z+0.5, "random.pop", 0.1F+0.5F*par5Random.nextFloat(), par5Random.nextFloat());

			}
		}
		List closeorbs = world.getEntitiesWithinAABB(EntityXPOrb.class, close);
		for (int i = 0; i < closeorbs.size(); i++) {
			EntityXPOrb xp = (EntityXPOrb)closeorbs.get(i);
			experience += xp.getXpValue();
			xp.setDead();
			world.playSoundEffect(x+0.5, y+0.5, z+0.5, "random.orb", 0.1F, 0.5F * ((par5Random.nextFloat() - par5Random.nextFloat()) * 0.7F + 1.8F));
		}
	}

	public int checkForStack(ItemStack is) {
		int target = -1;
		int id = is.itemID;
		int meta = is.getItemDamage();
		int size = is.stackSize;
		int firstempty = -1;

		for (int k = 0; k < inventory.length; k++) { //Find first empty slot
			if (inventory[k] == null) {
				firstempty = k;
				k = inventory.length;
			}
		}
		for (int j = 0; j < inventory.length; j++) {
			if (inventory[j] != null) {
				if (inventory[j].itemID == id && inventory[j].getItemDamage() == meta) {
					if (inventory[j].stackSize+size <= is.getMaxStackSize()) {
						target = j;
						j = inventory.length;
					}
					else {
						int diff = is.getMaxStackSize() - inventory[j].stackSize;
						inventory[j].stackSize += diff;
						is.stackSize -= diff;
					}
				}
			}
		}

		if (target == -1)
			target = firstempty;
		return target;
	}

	public AxisAlignedBB getBox(World world, int x, int y, int z) {
		int expand = this.getRange();
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1).expand(expand, expand, expand);
		return box;
	}

	public int getRange() {
		return ReikaMathLibrary.extrema(8+(int)(power/MINPOWER), this.getMaxRange(), "min");
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory()
	{
		return inventory.length;
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
		NBTTagList nbttaglist = NBT.getTagList("Items");
		experience = NBT.getInteger("xp");
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
		NBT.setInteger("xp", experience);
	}

	/**
	 * Returns the stack in slot i
	 */
	public ItemStack getStackInSlot(int par1)
	{
		return inventory[par1];
	}

	/**
	 * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
	 * stack.
	 */
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (inventory[par1] != null)
		{
			if (inventory[par1].stackSize <= par2)
			{
				ItemStack itemstack = inventory[par1];
				inventory[par1] = null;
				return itemstack;
			}

			ItemStack itemstack1 = inventory[par1].splitStack(par2);

			if (inventory[par1].stackSize == 0)
			{
				inventory[par1] = null;
			}

			return itemstack1;
		}
		else
		{
			return null;
		}
	}

	/**


	 */
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (inventory[par1] != null)
		{
			ItemStack itemstack = inventory[par1];
			inventory[par1] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	/**

	 */
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		inventory[par1] = par2ItemStack;

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
		return new ModelVacuum();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.VACUUM.ordinal();
	}

	@Override
	public boolean isStackValidForSlot(int slot, ItemStack is) {
		return true;
	}

	@Override
	public int getMaxRange() {
		return RotaryConfig.maxvacuumrange;
	}
}
