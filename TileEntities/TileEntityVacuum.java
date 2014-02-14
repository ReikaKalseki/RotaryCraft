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

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.API.Event.VacuumItemAbsorbEvent;
import Reika.RotaryCraft.API.Event.VacuumXPAbsorbEvent;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityVacuum extends InventoriedPowerReceiver implements RangedEffect {

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
		if (world.isRemote)
			return;
		tickcount++;
		if (power < MINPOWER)
			return;
		power = (long)torque*(long)omega;
		if (tickcount < 2)
			return;
		tickcount = 0;
		this.suck(world, x, y, z);
		this.absorb(world, x, y, z);
		this.transfer(world, x, y, z);
		//ReikaChatHelper.writeInt(this.experience);
	}

	private void transfer(World world, int x, int y, int z) {
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			int dx = x+dir.offsetX;
			int dy = y+dir.offsetY;
			int dz = z+dir.offsetZ;
			int id = world.getBlockId(dx, dy, dz);
			int meta = world.getBlockMetadata(dx, dy, dz);
			if (id != 0 && Block.blocksList[id].hasTileEntity(meta) && !(id == MachineRegistry.VACUUM.getBlockID() && meta == MachineRegistry.VACUUM.getMachineMetadata())) {
				TileEntity te = world.getBlockTileEntity(dx, dy, dz);
				if (te instanceof IInventory) {
					IInventory ii = ((IInventory)te);
					int size = ii.getSizeInventory();
					for (int k = 0; k < size; k++) {
						ItemStack inslot = ii.getStackInSlot(k);
						if (inslot != null) {
							boolean cansuck = true;
							if (te instanceof ISidedInventory)
								cansuck = ((ISidedInventory)te).canExtractItem(k, inslot, dir.getOpposite().ordinal());
							if (inslot != null) {
								if (this.canSuckStacks()) {
									if (ReikaInventoryHelper.addToIInv(inslot.copy(), this)) {
										ii.setInventorySlotContents(k, null);
									}
								}
								else {
									int newsize = inslot.stackSize-1;
									ItemStack is2 = ReikaItemHelper.getSizedItemStack(inslot, 1);
									ItemStack is3 = ReikaItemHelper.getSizedItemStack(inslot, newsize);
									if (newsize <= 0)
										is3 = null;
									if (ReikaInventoryHelper.addToIInv(is2, this)) {
										ii.setInventorySlotContents(k, is3);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private boolean canSuckStacks() {
		return power/MINPOWER >= 4;
	}

	public void spawnXP() {
		ReikaWorldHelper.splitAndSpawnXP(worldObj, xCoord-1+2*rand.nextFloat(), yCoord+2*rand.nextFloat(), zCoord-1+2*rand.nextFloat(), experience);
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
						inventory[targetslot] = is.copy();
					else
						inventory[targetslot].stackSize += is.stackSize;
				}
				else {
					return;
				}
				ent.setDead();
				world.playSoundEffect(x+0.5, y+0.5, z+0.5, "random.pop", 0.1F+0.5F*rand.nextFloat(), rand.nextFloat());
				MinecraftForge.EVENT_BUS.post(new VacuumItemAbsorbEvent(this, is != null ? is.copy(): null));
			}
		}
		List closeorbs = world.getEntitiesWithinAABB(EntityXPOrb.class, close);
		for (int i = 0; i < closeorbs.size(); i++) {
			EntityXPOrb xp = (EntityXPOrb)closeorbs.get(i);
			int val = xp.getXpValue();
			experience += val;
			xp.setDead();
			world.playSoundEffect(x+0.5, y+0.5, z+0.5, "random.orb", 0.1F, 0.5F * ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.8F));
			MinecraftForge.EVENT_BUS.post(new VacuumXPAbsorbEvent(this, val));
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
				if (ReikaItemHelper.matchStacks(is, inventory[j])) {
					if (this.areNBTTagsCombineable(is, inventory[j])) {
						if (inventory[j].stackSize+size <= this.getInventoryStackLimit()) {
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
		}

		if (target == -1)
			target = firstempty;
		return target;
	}

	private boolean areNBTTagsCombineable(ItemStack is, ItemStack is2) {
		if ((is.stackTagCompound == null && is2.stackTagCompound == null))
			return true;
		if ((is.stackTagCompound == null || is2.stackTagCompound == null))
			return false;
		if (is.stackTagCompound.getName() == null || is.stackTagCompound.getName().isEmpty())
			is.stackTagCompound.setName("tag"); //is done by the TE's NBT functions anyways
		if (ItemStack.areItemStackTagsEqual(is, is2))
			return true;
		return false;
	}

	public AxisAlignedBB getBox(World world, int x, int y, int z) {
		int expand = this.getRange();
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1).expand(expand, expand, expand);
		return box;
	}

	public int getRange() {
		return ReikaMathLibrary.extrema(8+(int)(power*4/MINPOWER), this.getMaxRange(), "min");
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
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.VACUUM.ordinal();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return true;
	}

	@Override
	public int getMaxRange() {
		return Math.max(32, ConfigRegistry.VACUUMRANGE.getValue());
	}

	@Override
	public int getRedstoneOverride() {
		return Container.calcRedstoneFromInventory(this);
	}

	@Override
	public void onEMP() {}
}
