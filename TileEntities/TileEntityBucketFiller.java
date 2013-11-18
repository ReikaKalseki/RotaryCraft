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

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityBucketFiller extends InventoriedPowerReceiver implements PipeConnector, IFluidHandler {

	private ItemStack[] inv = new ItemStack[18];
	public boolean filling = true;

	public static final int CAPACITY = 24000;

	private HybridTank tank = new HybridTank("bucketfiller", CAPACITY);

	public static final Fluid WATER = FluidRegistry.WATER;
	public static final Fluid LAVA = FluidRegistry.LAVA;
	public static final Fluid JETFUEL = FluidRegistry.getFluid("jet fuel");
	public static final Fluid LUBRICANT = FluidRegistry.getFluid("lubricant");

	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return FluidContainerRegistry.isContainer(is);
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.BUCKETFILLER.ordinal();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getSummativeSidedPower();
		if (power < MINPOWER)
			return;
		if (omega < MINSPEED)
			return;
		if (filling) {
			//ReikaJavaLibrary.pConsole(fuelLevel);
			if (!this.operationComplete(tickcount, 0))
				return;
			tickcount = 0;
			this.fillBuckets();
		}
		else {
			if (!this.operationComplete(tickcount, 0))
				return;
			tickcount = 0;
			this.emptyBuckets();
		}
	}

	private void emptyBuckets() {
		ItemStack is = new ItemStack(Item.bucketEmpty);
		for (int i = 0; i < inv.length; i++) {
			ItemStack slot = inv[i];
			if (slot != null) {
				FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(slot);
				if (fluid != null) {
					if (this.canAccept(fluid.getFluid())) {
						if (tank.getCapacity() >= fluid.amount+tank.getLevel()) {
							ReikaInventoryHelper.decrStack(i, inv);
							if (!ReikaInventoryHelper.addToIInv(is, this))
								ReikaItemHelper.dropItem(worldObj, xCoord+0.5, yCoord+1, zCoord+0.5, is);
							tank.addLiquid(fluid.amount, fluid.getFluid());
							return; //uncomment to only allow 1 bucket at a time
						}
					}
				}
			}
		}
	}

	private void fillBuckets() {
		for (int i = 0; i < inv.length; i++) {
			ItemStack slot = inv[i];
			if (slot != null && FluidContainerRegistry.isEmptyContainer(slot)) {
				ItemStack is = FluidContainerRegistry.fillFluidContainer(tank.getFluid(), slot);
				if (is != null) {
					tank.removeLiquid(FluidContainerRegistry.getFluidForFilledItem(is).amount);
					ReikaInventoryHelper.decrStack(i, inv);
					if (!ReikaInventoryHelper.addToIInv(is, this))
						ReikaItemHelper.dropItem(worldObj, xCoord+0.5, yCoord+1, zCoord+0.5, is);
				}
			}
		}
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
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);
		NBTTagList nbttaglist = NBT.getTagList("Items");
		inv = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");
			if (byte0 >= 0 && byte0 < inv.length)
				inv[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
		}

		tank.readFromNBT(NBT);
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < inv.length; i++) {
			if (inv[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				inv[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}
		NBT.setTag("Items", nbttaglist);

		tank.writeToNBT(NBT);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		if (filling)
			return j == 0 && FluidContainerRegistry.isFilledContainer(itemstack);
		return j == 0 && FluidContainerRegistry.isEmptyContainer(itemstack);
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.HOSE || m == MachineRegistry.PIPE || m == MachineRegistry.FUELLINE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return side.offsetY == 0;
	}

	public boolean canAccept(Fluid f) {
		return tank.isEmpty() || f.equals(tank.getActualFluid());
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return this.canReceiveFrom(from) && filling;
	}

	private boolean canReceiveFrom(ForgeDirection from) {
		return from.offsetY == 0;
	}

	public void setEmpty() {
		tank.empty();
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (!this.canDrain(from, resource.getFluid()))
			return null;
		return tank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (filling || from.offsetY != 0)
			return null;
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return !filling && from.offsetY == 0;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (this.canFill(from, resource.getFluid()))
			return tank.fill(resource, doFill);
		return 0;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return side.offsetY == 0 ? (filling ? Flow.INPUT : Flow.OUTPUT) : Flow.NONE;
	}

	public int getLevel() {
		return tank.getLevel();
	}

	public Fluid getContainedFluid() {
		return tank.getActualFluid();
	}
}
