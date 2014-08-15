/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

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

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityVacuum extends InventoriedPowerReceiver implements RangedEffect/*, IFluidHandler*/ {

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
			TileEntity te = this.getAdjacentTileEntity(dir);
			if (te instanceof IInventory && !(te instanceof TileEntityVacuum)) {
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

	private boolean canSuckStacks() {
		return power/MINPOWER >= 4;
	}

	public void spawnXP() {
		ReikaWorldHelper.splitAndSpawnXP(worldObj, xCoord-1+2*rand.nextFloat(), yCoord+2*rand.nextFloat(), zCoord-1+2*rand.nextFloat(), experience);
		experience = 0;
	}

	@SuppressWarnings("unused")
	private void suck(World world, int x, int y, int z) {
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
				ent.motionX += dx/ddt/ddt/1;
				ent.motionY += dy/ddt/ddt/2;
				ent.motionZ += dz/ddt/ddt/1;
				if (ent.posY < y)
					ent.motionY += 0.125;
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

	private void absorb(World world, int x, int y, int z) {
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
					if (inv[targetslot] == null)
						inv[targetslot] = is.copy();
					else
						inv[targetslot].stackSize += is.stackSize;
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

	private int checkForStack(ItemStack is) {
		int target = -1;
		Item id = is.getItem();
		int meta = is.getItemDamage();
		int size = is.stackSize;
		int firstempty = -1;

		for (int k = 0; k < inv.length; k++) { //Find first empty slot
			if (inv[k] == null) {
				firstempty = k;
				k = inv.length;
			}
		}
		for (int j = 0; j < inv.length; j++) {
			if (inv[j] != null) {
				if (ReikaItemHelper.matchStacks(is, inv[j])) {
					if (this.areNBTTagsCombineable(is, inv[j])) {
						if (inv[j].stackSize+size <= this.getInventoryStackLimit()) {
							target = j;
							j = inv.length;
						}
						else {
							int diff = is.getMaxStackSize() - inv[j].stackSize;
							inv[j].stackSize += diff;
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
		//if (is.stackTagCompound.getName() == null || is.stackTagCompound.getName().isEmpty())
		//	is.stackTagCompound.setName("tag"); //is done by the TE's NBT functions anyways
		if (ItemStack.areItemStackTagsEqual(is, is2))
			return true;
		return false;
	}

	private AxisAlignedBB getBox(World world, int x, int y, int z) {
		int expand = this.getRange();
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1).expand(expand, expand, expand);
		return box;
	}

	public int getRange() {
		return ReikaMathLibrary.extrema(8+(int)(power*4/MINPOWER), this.getMaxRange(), "min");
	}

	public int getSizeInventory()
	{
		return 54;
	}

	public static boolean func_52005_b(ItemStack par0ItemStack)
	{
		return true;
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		experience = NBT.getInteger("xp");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("xp", experience);
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.VACUUM;
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
	public void onEMP() {}/*

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return experience > 0 && fluid.equals(FluidRegistry.getFluid("xpjuice"));
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{};
	}*/
}