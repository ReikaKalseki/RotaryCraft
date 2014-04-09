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
import Reika.RotaryCraft.API.Fillable;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidReceiver;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityFillingStation extends InventoriedPowerLiquidReceiver implements ConditionalOperation {

	public static final int CAPACITY = 32000;

	public static final int FUEL_PER_CRYSTAL = 250;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.getIOSidesDefault(world, x, y, z, meta);
		this.getPower(false);
		if (power < MINPOWER)
			return;

		if (this.canMakeFuel()) {
			this.makeFuel();
		}

		if (this.hasFillable()) {
			if (this.canFill()) {
				this.fill();
			}
		}
		//else if (this.hasContainer()) {
		//	this.fillContainer();
		//}

		//ReikaJavaLibrary.pConsole(this.getSide()+":"+tank);
	}

	private boolean hasContainer() {
		return inv[0] != null && FluidContainerRegistry.isContainer(inv[0]);
	}

	private void fillContainer() {
		int maxadd = this.getFluidToAdd()*128;
		ItemStack filled = FluidContainerRegistry.fillFluidContainer(new FluidStack(tank.getActualFluid(), maxadd), inv[0]);
		if (filled != null) {
			FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(filled);
			if (fs != null && fs.amount > 0) {
				int added = fs.amount;
				tank.removeLiquid(added);
				inv[0] = filled;
			}
		}
	}

	public boolean canMakeFuel() {
		if (inv[1] == null)
			return false;
		FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(inv[1]);
		if (fs == null) {
			boolean item = inv[1].itemID == ItemRegistry.ETHANOL.getShiftedID();
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
		if (inv[1].itemID == ItemRegistry.ETHANOL.getShiftedID()) {
			tank.addLiquid(FUEL_PER_CRYSTAL, FluidRegistry.getFluid("rc ethanol"));
			ReikaInventoryHelper.decrStack(1, inv);
			return;
		}
		FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(inv[1]);
		tank.addLiquid(fs.amount, fs.getFluid());
		inv[1] = new ItemStack(Item.bucketEmpty);
	}

	private void fill() {
		ItemStack is = inv[0];
		Fillable i = (Fillable)is.getItem();
		int added = i.addFluid(is, tank.getActualFluid(), this.getFluidToAdd());
		if (added > 0)
			tank.removeLiquid(added);
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
		if (i == 0) {
			if (itemstack != null && itemstack.getItem() instanceof Fillable) {
				return ((Fillable)itemstack.getItem()).isFull(itemstack);
			}
		}
		return false;
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if (i == 0)
			return itemstack.getItem() instanceof Fillable;
		if (i == 1) {
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

}
