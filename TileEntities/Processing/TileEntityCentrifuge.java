/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Processing;

import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesCentrifuge;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityCentrifuge extends InventoriedPowerReceiver implements DiscreteFunction, ConditionalOperation, IFluidHandler, PipeConnector {

	private int progressTime;
	public static final int CAPACITY = 10000;
	private HybridTank tank = new HybridTank("centrifuge", CAPACITY);

	public int getProgressScaled(int l) {
		return l * progressTime / this.getOperationTime();
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		phi += omega;
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.CENTRIFUGE;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();

		if (power >= MINPOWER && omega >= MINSPEED) {
			ItemStack in = inv[0];
			if (in != null && RecipesCentrifuge.recipes().isCentrifugable(in)) {
				progressTime++;

				if (progressTime >= this.getOperationTime()) {
					ArrayList<ItemStack> out = RecipesCentrifuge.recipes().getRecipeResult(in);
					if (this.canMakeAllOf(out)) {
						FluidStack fs = RecipesCentrifuge.recipes().getFluidResult(in);
						if (fs == null || tank.canTakeIn(fs)) {
							for (int i = 0; i < out.size(); i++) {
								//ReikaInventoryHelper.addOrSetStack(out.get(i).copy(), inv, i+1);
								ReikaInventoryHelper.putStackInInventory(out.get(i), this, true);
							}
							if (fs != null)
								tank.addLiquid(fs.amount, fs.getFluid());
							ReikaInventoryHelper.decrStack(0, inv);
						}
					}
					progressTime = 0;
				}
			}
			else {
				progressTime = 0;
			}
		}
		else {
			progressTime = 0;
		}
	}

	private boolean canMakeAllOf(ArrayList<ItemStack> out) {
		List<ItemStack> copy = ReikaJavaLibrary.copyList(out);
		for (int i = 0; i < out.size(); i++) {
			ItemStack is = out.get(i);
			for (int k = 1; k < inv.length; k++) {
				ItemStack is2 = inv[k];
				if (ReikaItemHelper.matchStacks(is, is2)) {
					int sum = is2.stackSize+is.stackSize;
					if (sum <= is.getMaxStackSize() && sum <= this.getInventoryStackLimit()) {
						copy.remove(is);
					}
				}
			}
		}
		return ReikaInventoryHelper.countEmptySlots(inv) >= copy.size();
	}

	public int getLevel() {
		return tank.getLevel();
	}

	public Fluid getFluid() {
		return tank.getActualFluid();
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i != 0;
	}

	@Override
	public int getSizeInventory() {
		return 10;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return i == 0 && RecipesCentrifuge.recipes().isCentrifugable(itemstack);
	}

	@Override
	public boolean areConditionsMet() {
		return inv[0] != null && RecipesCentrifuge.recipes().isCentrifugable(inv[0]);
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "Missing Items";
	}

	@Override
	public int getOperationTime() {
		return DurationRegistry.CENTRIFUGE.getOperationTime(omega);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		progressTime = NBT.getInteger("CookTime");
		tank.readFromNBT(NBT);
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("CookTime", progressTime);
		tank.writeToNBT(NBT);
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return this.canConnectToPipe(p) && side.offsetY == 0;
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return side.offsetY == 0 ? Flow.OUTPUT : Flow.NONE;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return this.canDrain(from, resource.getFluid()) ? tank.drain(resource.amount, doDrain) : null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return this.canDrain(from, null) ? tank.drain(maxDrain, doDrain) : null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return from.offsetY == 0;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	public int getLiquidScaled(int a) {
		return a * tank.getLevel() / tank.getCapacity();
	}

}