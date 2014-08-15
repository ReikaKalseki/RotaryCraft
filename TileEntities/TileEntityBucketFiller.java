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

import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityBucketFiller extends InventoriedPowerReceiver implements PipeConnector, IFluidHandler, DiscreteFunction, ConditionalOperation {

	public boolean filling = true;

	public static final int CAPACITY = 24000;

	private HybridTank tank = new HybridTank("bucketfiller", CAPACITY);

	public static final Fluid WATER = FluidRegistry.WATER;
	public static final Fluid LAVA = FluidRegistry.LAVA;
	public static final Fluid JETFUEL = FluidRegistry.getFluid("jet fuel");
	public static final Fluid LUBRICANT = FluidRegistry.getFluid("lubricant");

	@Override
	public int getSizeInventory() {
		return 18;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return FluidContainerRegistry.isContainer(is);
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.BUCKETFILLER;
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
			if (tickcount <= this.getOperationTime())
				return;
			tickcount = 0;
			this.fillBuckets();
		}
		else {
			if (tickcount <= this.getOperationTime())
				return;
			tickcount = 0;
			this.emptyBuckets();
		}
	}

	private void emptyBuckets() {
		ItemStack is = new ItemStack(Items.bucket);
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
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		tank.readFromNBT(NBT);
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

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

	@Override
	public int getOperationTime() {
		return DurationRegistry.BUCKETFILLER.getOperationTime(omega);
	}

	@Override
	public boolean areConditionsMet() {
		return !ReikaInventoryHelper.isEmpty(inv);
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Buckets";
	}
}