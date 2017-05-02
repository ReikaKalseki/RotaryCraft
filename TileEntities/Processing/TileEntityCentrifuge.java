/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Processing;

import java.util.Collection;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.TemporaryInventory;
import Reika.DragonAPI.Instantiable.Data.Collections.ChancedOutputList;
import Reika.DragonAPI.Libraries.ReikaFluidHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.MultiOperational;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesCentrifuge;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityCentrifuge extends InventoriedPowerReceiver implements MultiOperational, ConditionalOperation, IFluidHandler, PipeConnector {

	private int progressTime;
	public static final int CAPACITY = 10000;
	private final HybridTank tank = new HybridTank("centrifuge", CAPACITY);

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
			int n = this.getNumberConsecutiveOperations();
			for (int i = 0; i < n; i++)
				this.doOperation(n > 1);
		}
		else {
			progressTime = 0;
		}
	}

	private void doOperation(boolean multiple) {
		ItemStack in = inv[0];
		if (in != null && RecipesCentrifuge.getRecipes().isCentrifugable(in)) {
			progressTime++;

			if (multiple || progressTime >= this.getOperationTime()) {
				ChancedOutputList out = RecipesCentrifuge.getRecipes().getRecipeResult(in);
				Collection<ItemStack> items = out.keySet();
				if (this.canMakeAllOf(items)) {
					FluidStack fs = RecipesCentrifuge.getRecipes().getFluidResult(in);
					if (fs == null || tank.canTakeIn(fs)) {
						for (ItemStack is : items) {
							//ReikaInventoryHelper.addOrSetStack(out.get(i).copy(), inv, i+1);
							double ch = out.getNormalizedItemChance(is);
							while (ch >= 1) {
								ReikaInventoryHelper.addToIInv(is, this, true, 1, this.getSizeInventory());
								ch -= 1.0D;
							}
							if (ReikaRandomHelper.doWithChance(ch)) {
								ReikaInventoryHelper.addToIInv(is, this, true, 1, this.getSizeInventory());
							}
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

	private boolean canMakeAllOf(Collection<ItemStack> out) {
		if (out.size() > 9)
			return ReikaInventoryHelper.isEmptyFrom(this, 1, 9);
		IInventory temp = new TemporaryInventory(9);
		for (int i = 0; i < 9; i++) {
			ItemStack in = inv[i+1];
			if (in != null)
				temp.setInventorySlotContents(i, in.copy());
		}
		for (ItemStack is : out) {
			if (!ReikaInventoryHelper.addToIInv(is.copy(), temp))
				return false;
		}
		return true;
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
		return i == 0 && RecipesCentrifuge.getRecipes().isCentrifugable(itemstack);
	}

	@Override
	public boolean areConditionsMet() {
		return inv[0] != null && RecipesCentrifuge.getRecipes().isCentrifugable(inv[0]);
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
	public int getNumberConsecutiveOperations() {
		return DurationRegistry.CENTRIFUGE.getNumberOperations(omega);
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
		return m.isStandardPipe() || m == MachineRegistry.HOSE;
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
		return from.offsetY == 0 && ReikaFluidHelper.isFluidDrainableFromTank(fluid, tank);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	public int getLiquidScaled(int a) {
		return a * tank.getLevel() / tank.getCapacity();
	}

	public int getProgress() {
		return progressTime;
	}

	@SideOnly(Side.CLIENT)
	public void syncProgress(int time) {
		progressTime = time;
	}

}
