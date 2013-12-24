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

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.InertIInv;
import Reika.RotaryCraft.Base.TileEntity.TileEntityAimedCannon;
import Reika.RotaryCraft.Entities.EntityFreezeGunShot;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityFreezeGun extends TileEntityAimedCannon implements ISidedInventory {

	private ItemStack[] inv = new ItemStack[27];
	//public List<EntityLivingBase> frozen = new ArrayList<EntityLivingBase>();

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		if (!this.isAimingAtTarget(world, x, y, z, target))
			return;
		this.convertSnow();
		if (!this.hasAmmo())
			return;
		if (tickcount < 20)
			return;
		tickcount = 0;
		if (target[3] == 1) {
			this.fire(world, target);
		}
		//ReikaJavaLibrary.pConsole(frozen.size());
		//for (int i = 0; i < frozen.size(); i++) {
		//	EntityLivingBase ent = frozen.get(i);
		//	/**Used to reset mob age and prevent despawning (since entityAge is private); still does not prevent far-from despawn */
		//	ent.attackEntityFrom(DamageSource.generic, 0);
		//}
	}

	private void convertSnow() {
		int slot = ReikaInventoryHelper.locateInInventory(Block.blockSnow.blockID, inv);
		if (slot != -1 && ReikaInventoryHelper.canAcceptMoreOf(Item.snowball.itemID, 0, inv)) {
			ReikaInventoryHelper.decrStack(slot, inv);
			ReikaInventoryHelper.addToIInv(new ItemStack(Item.snowball.itemID, 4, 0), this);
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRange() {
		return 64;
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.FREEZEGUN.ordinal();
	}

	@Override
	public int getMaxRange() {
		return 256;
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
		if (inv[par1] != null)
		{
			if (inv[par1].stackSize <= par2)
			{
				ItemStack itemstack = inv[par1];
				inv[par1] = null;
				return itemstack;
			}

			ItemStack itemstack1 = inv[par1].splitStack(par2);

			if (inv[par1].stackSize <= 0)
			{
				inv[par1] = null;
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
		if (inv[par1] != null)
		{
			ItemStack itemstack = inv[par1];
			inv[par1] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
	}

	@Override
	public boolean hasAmmo() {
		return ReikaInventoryHelper.checkForItem(Block.ice.blockID, inv) || ReikaInventoryHelper.checkForItem(Item.snowball.itemID, inv);
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
					if (!ent.isDead && ent.getHealth() > 0 && ent.getActivePotionEffect(Potion.moveSlowdown) == null) {
						//ReikaJavaLibrary.pConsole(ent);
						double dy = -(ent.posY-y);
						double reqtheta = -90+Math.toDegrees(Math.abs(Math.acos(dy/dist)));
						if ((reqtheta <= dir*MAXLOWANGLE && dir == -1) || (reqtheta >= dir*MAXLOWANGLE && dir == 1))
							if (dist < mindist && !ent.getActivePotionEffects().contains(Potion.moveSlowdown)) {
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
		double speed = 1;
		int slot = ReikaInventoryHelper.locateInInventory(Item.snowball.itemID, -1, inv);
		//ReikaInventoryHelper.decrStack(slot, inv);
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
			EntityFreezeGunShot snow = new EntityFreezeGunShot(world, xCoord+0.5+dx, yCoord+voffset*0+0.75+dy, zCoord+0.5+dz, 3*v[0], 3*v[1], 3*v[2]);
			world.spawnEntityInWorld(snow);
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return is.itemID == Block.ice.blockID || is.itemID == Block.blockSnow.blockID || is.itemID == Item.snowball.itemID;
	}

	@Override
	protected double randomOffset() {
		return 0;
	}

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

		NBTTagList froze = NBT.getTagList("freeze");
		for (int i = 0; i < nbttaglist.tagCount(); i++)	{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
		}
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.hasAmmo())
			return 15;
		return 0;
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

	public void openChest() {}

	public void closeChest() {}

	public int getInventoryStackLimit()
	{
		return 64;
	}

	public boolean isInvNameLocalized() {
		return false;
	}

	public boolean isUseableByPlayer(EntityPlayer var1) {
		return this.isPlayerAccessible(var1);
	}
}
