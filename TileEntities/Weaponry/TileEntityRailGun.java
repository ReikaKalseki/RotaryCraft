/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Weaponry;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.InertIInv;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityAimedCannon;
import Reika.RotaryCraft.Entities.EntityExplosiveShell;
import Reika.RotaryCraft.Entities.EntityRailGunShot;
import Reika.RotaryCraft.Models.ModelRailGun;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityRailGun extends TileEntityAimedCannon implements ISidedInventory {

	public ItemStack[] ammo = new ItemStack[54];
	private boolean isExplosiveShell = false;

	public int getPowerLevel() {
		int meta = ReikaInventoryHelper.findMaxMetadataOfID(ItemRegistry.RAILGUN.getShiftedID(), ammo);
		return meta;
	}

	@Override
	public boolean hasAmmo() {
		if (ReikaInventoryHelper.checkForItem(ItemRegistry.RAILGUN.getShiftedID(), ammo)) {
			isExplosiveShell = false;
			return true;
		}
		else {
			isExplosiveShell = true;
			return ReikaInventoryHelper.checkForItem(ItemRegistry.SHELL.getShiftedID(), ammo);
		}
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		if (power < MINPOWER)
			return;
		if (!this.hasAmmo())
			return;
		if (!this.isAimingAtTarget(world, x, y, z, target))
			return;
		if (tickcount < 20)
			return;
		tickcount = 0;
		if (target[3] == 1) {
			if (!world.isRemote)
				this.fire(world, target);
		}
	}

	@Override
	protected double[] getTarget(World world, int x, int y, int z) {
		double[] xyzb = new double[4];
		AxisAlignedBB range = AxisAlignedBB.getBoundingBox(x-this.getRange(), y-this.getRange(), z-this.getRange(), x+1+this.getRange(), y+1+this.getRange(), z+1+this.getRange());
		List inrange = world.getEntitiesWithinAABB(EntityLivingBase.class, range);
		double mindist = this.getRange()+2;
		int i_at_min = -1;
		for (int i = 0; i < inrange.size(); i++) {
			EntityLivingBase ent = (EntityLivingBase)inrange.get(i);
			double dist = ReikaMathLibrary.py3d(ent.posX-x-0.5, ent.posY-y-0.5, ent.posZ-z-0.5);
			if (this.isValidTarget(ent)) {
				if (ReikaWorldHelper.canBlockSee(world, x, y, z, ent.posX, ent.posY, ent.posZ, this.getRange())) {
					if (!ent.isDead && ent.getHealth() > 0) {
						double dy = -(ent.posY-y);
						double reqtheta = -90+Math.toDegrees(Math.abs(Math.acos(dy/dist)));
						if ((reqtheta <= dir*MAXLOWANGLE && dir == -1) || (reqtheta >= dir*MAXLOWANGLE && dir == 1))
							if (dist < mindist) {
								mindist = dist;
								i_at_min = i;
							}
					}
				}
			}
		}
		if (i_at_min == -1)
			return xyzb;
		EntityLivingBase ent = (EntityLivingBase)inrange.get(i_at_min);
		closestMob = ent;
		xyzb[0] = ent.posX+this.randomOffset();
		xyzb[1] = ent.posY+ent.getEyeHeight()*0.25+this.randomOffset();
		xyzb[2] = ent.posZ+this.randomOffset();
		xyzb[3] = 1;
		return xyzb;
	}

	@Override
	public void fire(World world, double[] xyz) {
		double speed = 6;
		int maxmeta = this.getMaxThrust();
		if (isExplosiveShell) {
			int m = ReikaInventoryHelper.findMaxMetadataOfIDWithinMaximum(ItemRegistry.SHELL.getShiftedID(), ammo, maxmeta);
			int slot = ReikaInventoryHelper.locateInInventory(ItemRegistry.SHELL.getShiftedID(), m, ammo);
			ReikaInventoryHelper.decrStack(slot, ammo);
		}
		else {
			int m = ReikaInventoryHelper.findMaxMetadataOfIDWithinMaximum(ItemRegistry.RAILGUN.getShiftedID(), ammo, maxmeta);
			int slot = ReikaInventoryHelper.locateInInventory(ItemRegistry.RAILGUN.getShiftedID(), m, ammo);
			ReikaInventoryHelper.decrStack(slot, ammo);
		}
		double[] v = new double[3];
		v[0] = xyz[0]-xCoord;
		v[1] = xyz[1]-yCoord;
		v[2] = xyz[2]-zCoord;
		double dd = ReikaMathLibrary.py3d(v[0], v[1], v[2]);
		for (int i = 0; i < 3; i++)
			v[i] /= dd;
		for (int i = 0; i < 3; i++)
			v[i] *= speed;
		dd = ReikaMathLibrary.py3d(v[0], v[1], v[2]);
		double dx = v[0]/dd;
		double dy = v[1]/dd;
		double dz = v[2]/dd;
		//ReikaJavaLibrary.pConsole(dx+"  "+dy+"  "+dz);
		if (!world.isRemote) {
			if (isExplosiveShell)
				world.spawnEntityInWorld(new EntityExplosiveShell(world, xCoord+0.5+dx, yCoord+voffset*0+0.75+dy, zCoord+0.5+dz, v[0], v[1], v[2], this));
			else
				world.spawnEntityInWorld(new EntityRailGunShot(world, xCoord+0.5+dx, yCoord+voffset*0+0.75+dy, zCoord+0.5+dz, v[0], v[1], v[2], this.getPowerLevel(), this));
		}
	}

	private int getMaxThrust() {
		double m = ReikaMathLibrary.doubpow(torque/512D, 2);
		return (int)ReikaMathLibrary.logbase(m, 2);
	}

	public int getRange() {
		return 164;
	}

	public EntityLivingBase getClosestMob() {
		return closestMob;
	}

	/*
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }*/

	@Override
	protected double randomOffset() {
		//return -0.5+par5Random.nextFloat();
		return 0;
	}

	@Override
	public int getSizeInventory() {
		return ammo.length;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return ammo[var1];
	}

	/**
	 * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
	 * stack.
	 */
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (ammo[par1] != null)
		{
			if (ammo[par1].stackSize <= par2)
			{
				ItemStack itemstack = ammo[par1];
				ammo[par1] = null;
				return itemstack;
			}

			ItemStack itemstack1 = ammo[par1].splitStack(par2);

			if (ammo[par1].stackSize <= 0)
			{
				ammo[par1] = null;
			}

			return itemstack1;
		}
		else
		{
			return null;
		}
	}

	/**
	 *
	 *
	 */
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (ammo[par1] != null)
		{
			ItemStack itemstack = ammo[par1];
			ammo[par1] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	/**
	 * Sets the given item stack to the specified slot in the ammo (can be crafting or armor sections).
	 */
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		ammo[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < ammo.length; i++)
		{
			if (ammo[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				ammo[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		NBT.setTag("Items", nbttaglist);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);

		NBTTagList nbttaglist = NBT.getTagList("Items");
		ammo = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < ammo.length)
			{
				ammo[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelRailGun();
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.RAILGUN.ordinal();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return is.itemID == ItemRegistry.RAILGUN.getShiftedID();
	}

	@Override
	public int getMaxRange() {
		return 256;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return this.getMaxThrust();
	}

	@Override
	protected boolean isValidTarget(EntityLivingBase ent) {
		return this.isMobOrUnlistedPlayer(ent);
	}

	public int[] getAccessibleSlotsFromSide(int var1) {
		if (this instanceof InertIInv)
			return new int[0];
		return ReikaInventoryHelper.getWholeInventoryForISided(this);
	}

	public boolean canInsertItem(int i, ItemStack is, int side) {
		if (this instanceof InertIInv)
			return false;
		return ((IInventory)this).isItemValidForSlot(i, is);
	}

	public final String getInvName() {
		return this.getMultiValuedName();
	}

}
