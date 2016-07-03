/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.util.List;

import net.minecraft.entity.Entity;
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
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Interfaces.TileEntity.BreakAction;
import Reika.DragonAPI.Interfaces.TileEntity.XPProducer;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.ReikaXPFluidHelper;
import Reika.RotaryCraft.API.Event.VacuumItemAbsorbEvent;
import Reika.RotaryCraft.API.Event.VacuumXPAbsorbEvent;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityVacuum extends InventoriedPowerReceiver implements RangedEffect, BreakAction, IFluidHandler {

	private int experience = 0;
	public boolean equidistant = true;
	public boolean suckIfFull = true;

	private boolean isFull = false;

	public int getExperience() {
		return experience;
	}

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
		if (suckIfFull || !isFull) {
			this.suck(world, x, y, z);
			this.absorb(world, x, y, z);
			this.transfer(world, x, y, z);
		}
	}

	@Override
	protected void onInventoryChanged() {
		isFull = false;
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
						if (cansuck) {
							if (this.canSuckStacks() && ReikaInventoryHelper.addToIInv(inslot.copy(), this)) {
								ii.setInventorySlotContents(k, null);
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
			if (te instanceof XPProducer) {
				XPProducer xpm = (XPProducer)te;
				experience += xpm.getXP();
				xpm.clearXP();
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

	private void suck(World world, int x, int y, int z) {
		AxisAlignedBB box = this.getBox(world, x, y, z);

		///Do not merge these, they have slightly different code!
		List<Entity> inbox = world.selectEntitiesWithinAABB(Entity.class, box, ReikaEntityHelper.itemOrXPSelector);
		double v = Math.max(1, power/1048576D);
		for (Entity ent : inbox) {
			if (ent.ticksExisted > 5) {
				//Vec3 i2vac = ReikaVectorHelper.getVec2Pt(ent.posX, ent.posY, ent.posZ, x+0.5, y+0.5, z+0.5);
				//if (ReikaWorldHelper.canBlockSee(world, x, y, z, ent.posX, ent.posY, ent.posZ, this.getRange()+2)) {
				if (true || ReikaWorldHelper.canBlockSee(world, x, y, z, ent.posX, ent.posY, ent.posZ, this.getRange()+2)) {
					double dx = (x+0.5 - ent.posX);
					double dy = (y+0.5 - ent.posY);
					double dz = (z+0.5 - ent.posZ);
					double ddt = ReikaMathLibrary.py3d(dx, dy, dz);
					if (ent.ticksExisted > 50 && ddt > 1.5 && ent.ticksExisted%400 < 80) { //For routing around objects
						double t = this.getTicksExisted()/25D;
						double r = 2.875;//+1*ReikaMathLibrary.cosInterpolation(0, 1, Math.sin(t/2));
						dx += r*Math.cos(t);
						dz += r*Math.sin(t);
					}
					double vx = v*dx/ddt/ddt;
					double vy = v*dy/ddt/ddt/2;
					double vz = v*dz/ddt/ddt;
					double vmax = 0.125;
					vx = MathHelper.clamp_double(vx, -vmax, vmax);
					vy = MathHelper.clamp_double(vy, -vmax, vmax);
					vz = MathHelper.clamp_double(vz, -vmax, vmax);
					ent.motionX += vx;
					ent.motionY += vy;
					ent.motionZ += vz;
					if (ent.posY < y)
						ent.motionY += 0.125;
					if (!world.isRemote)
						ent.velocityChanged = true;
				}
			}
		}

	}

	private void absorb(World world, int x, int y, int z) {
		if (world.isRemote)
			return;
		boolean suck = false;
		AxisAlignedBB close = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(0.25D, 0.25D, 0.25D);
		List<EntityItem> closeitems = world.getEntitiesWithinAABB(EntityItem.class, close);
		for (EntityItem ent : closeitems) {
			if (ent.delayBeforeCanPickup <= 0) {
				ItemStack is = ent.getEntityItem();
				int targetslot = this.checkForStack(is);
				if (targetslot != -1) {
					if (inv[targetslot] == null)
						inv[targetslot] = is.copy();
					else
						inv[targetslot].stackSize += is.stackSize;
					suck = true;
				}
				else {
					return;
				}
				ent.setDead();
				world.playSoundEffect(x+0.5, y+0.5, z+0.5, "random.pop", 0.1F+0.5F*rand.nextFloat(), rand.nextFloat());
				MinecraftForge.EVENT_BUS.post(new VacuumItemAbsorbEvent(this, is != null ? is.copy(): null));
			}
			else {
				suck = true;
			}
		}
		isFull = !suck;
		List<EntityXPOrb> closeorbs = world.getEntitiesWithinAABB(EntityXPOrb.class, close);
		for (EntityXPOrb xp : closeorbs) {
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
					if (ItemStack.areItemStackTagsEqual(is, inv[j])) {
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

	private AxisAlignedBB getBox(World world, int x, int y, int z) {
		int expand = this.getRange();
		AxisAlignedBB base = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1);
		return equidistant ? base.expand(expand, expand, expand) : base.expand(expand, 2, expand);
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
		equidistant = NBT.getBoolean("equi");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("xp", experience);
		NBT.setBoolean("equi", equidistant);
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
	public void onEMP() {}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return resource.getFluidID() == ReikaXPFluidHelper.getFluid().getFluidID() ? this.drain(from, resource.amount, doDrain) : null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		FluidStack fs = experience > 0 ? ReikaXPFluidHelper.getFluid(experience) : null;
		if (fs != null) {
			if (fs.amount > maxDrain)
				fs.amount = maxDrain;
			if (doDrain)
				experience -= ReikaXPFluidHelper.getXPForAmount(fs.amount);
		}
		return fs;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return ReikaXPFluidHelper.fluidsExist() && experience > 0 && fluid.equals(ReikaXPFluidHelper.getFluidType());
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{};
	}

	@Override
	public void breakBlock() {
		ReikaWorldHelper.splitAndSpawnXP(worldObj, xCoord+rand.nextFloat(), yCoord+rand.nextFloat(), zCoord+rand.nextFloat(), experience);
	}
}
