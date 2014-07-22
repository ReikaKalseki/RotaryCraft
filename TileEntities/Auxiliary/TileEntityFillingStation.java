/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Auxiliary;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.API.Fillable;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidInOut;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityFillingStation extends InventoriedPowerLiquidInOut implements ConditionalOperation {

	public static final int CAPACITY = 32000;

	public static final int FUEL_PER_CRYSTAL = 250;

	public static final int INPUT_SLOT = 3;
	public static final int FUEL_SLOT = 1;
	public static final int OUTPUT_SLOT = 2;
	public static final int FILLING_SLOT = 0;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.getIOSidesDefault(world, x, y, z, meta);
		this.getPower(false);
		if (power < MINPOWER)
			return;

		if (world.isRemote)
			return;

		if (this.canMakeFuel()) {
			this.makeFuel();
		}

		if (this.hasFillable()) {
			if (this.canFill()) {
				this.fill();
			}
		}
		else {
			ItemStack is = inv[INPUT_SLOT];
			if (is != null && is.getItem() instanceof Fillable) {
				ReikaInventoryHelper.addOrSetStack(ReikaItemHelper.getSizedItemStack(is, 1), inv, FILLING_SLOT);
				ReikaInventoryHelper.decrStack(INPUT_SLOT, inv);
			}
		}
	}

	private boolean hasContainer() {
		return inv[FILLING_SLOT] != null && FluidContainerRegistry.isContainer(inv[FILLING_SLOT]);
	}

	private void fillContainer() {
		int maxadd = this.getFluidToAdd()*128;
		ItemStack filled = FluidContainerRegistry.fillFluidContainer(new FluidStack(tank.getActualFluid(), maxadd), inv[FILLING_SLOT]);
		if (filled != null) {
			FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(filled);
			if (fs != null && fs.amount > 0) {
				int added = fs.amount;
				tank.removeLiquid(added);
				inv[FILLING_SLOT] = filled;
			}
		}
	}

	public boolean canMakeFuel() {
		if (inv[FUEL_SLOT] == null)
			return false;
		FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(inv[FUEL_SLOT]);
		if (fs == null) {
			boolean item = inv[FUEL_SLOT].itemID == ItemRegistry.ETHANOL.getShiftedID();
			boolean space = tank.canTakeIn(FUEL_PER_CRYSTAL) && (tank.isEmpty() || tank.getActualFluid().equals(FluidRegistry.getFluid("rc ethanol")));
			return item && space;
		}
		else {
			if (tank.isEmpty())
				return true;
			return tank.canTakeIn(fs.amount) && tank.getActualFluid().equals(fs.getFluid());
		}
	}

	public void makeFuel() {
		if (inv[FUEL_SLOT].itemID == ItemRegistry.ETHANOL.getShiftedID()) {
			tank.addLiquid(FUEL_PER_CRYSTAL, FluidRegistry.getFluid("rc ethanol"));
			ReikaInventoryHelper.decrStack(1, inv);
			return;
		}
		FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(inv[FUEL_SLOT]);
		tank.addLiquid(fs.amount, fs.getFluid());
		inv[FUEL_SLOT] = new ItemStack(Item.bucketEmpty);
	}

	private void fill() {
		ItemStack is = inv[FILLING_SLOT];
		Fillable i = (Fillable)is.getItem();
		int added = i.addFluid(is, tank.getActualFluid(), this.getFluidToAdd());
		if (added > 0)
			tank.removeLiquid(added);
		if (this.canShuttleItem()) {
			if (ReikaInventoryHelper.addOrSetStack(is, inv, 2))
				inv[FILLING_SLOT] = null;
		}
	}

	private boolean canShuttleItem() {
		ItemStack is = inv[FILLING_SLOT];
		Fillable f = (Fillable)is.getItem();
		ItemStack is2 = inv[OUTPUT_SLOT];
		if (is2 == null)
			return f.isFull(is);
		if (!ReikaItemHelper.matchStacks(is, is2))
			return false;
		if (!f.isFull(is) || !f.isFull(is2))
			return false;
		if (is.stackSize+is2.stackSize > is.getMaxStackSize())
			return false;
		return true;
	}

	private int getFluidToAdd() {
		int toadd = (int)ReikaMathLibrary.logbase(omega, 2);
		return Math.min(toadd, tank.getLevel());
	}

	private boolean hasFillable() {
		return inv[0] != null && inv[0].getItem() instanceof Fillable;
	}

	private boolean canFill() {
		if (tank.isEmpty())
			return false;
		ItemStack is = inv[0];
		Fillable i = (Fillable)is.getItem();
		int current = i.getCurrentFillLevel(is);
		int max = i.getCapacity(is);
		return i.isValidFluid(tank.getActualFluid(), is) && max > current;
	}

	private boolean canIntakeFluid(Fluid f) {
		return tank.isEmpty() || tank.getActualFluid().equals(f);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i == OUTPUT_SLOT;
	}

	@Override
	public int getSizeInventory() {
		return 4;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if (i == INPUT_SLOT)
			return itemstack.getItem() instanceof Fillable;
		if (i == FUEL_SLOT) {
			boolean container = FluidContainerRegistry.isFilledContainer(itemstack);
			return container || itemstack.itemID == ItemRegistry.ETHANOL.getShiftedID();
		}
		return false;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.FUELLINE || m == MachineRegistry.PIPE;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.FILLINGSTATION;
	}

	@Override
	public boolean hasModelTransparency() {
		return true;
	}

	@Override
	public int getRedstoneOverride() {
		return this.canFill() ? 0 : 15;
	}

	public Fluid getFluid() {
		return tank.getActualFluid();
	}

	public int getLiquidScaled(int i) {
		return tank.getLevel() * i / tank.getCapacity();
	}

	@Override
	public Fluid getInputFluid() {
		return null;
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return true;
	}

	@Override
	public int getCapacity() {
		return CAPACITY;
	}

	@Override
	public boolean isValidFluid(Fluid f) {
		return true;
	}

	public ItemStack getItemForRender() {
		return inv[0] != null ? inv[0].copy() : null;
	}

	@Override
	public boolean areConditionsMet() {
		return !tank.isEmpty() && inv[0] != null && inv[0].getItem() instanceof Fillable;
	}

	@Override
	public String getOperationalStatus() {
		return tank.isEmpty() ? "No Liquid" : this.areConditionsMet() ? "Operational" : "No Fillable Items";
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return from == ForgeDirection.DOWN;
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return side == ForgeDirection.DOWN ? Flow.OUTPUT : Flow.INPUT;
	}

}
