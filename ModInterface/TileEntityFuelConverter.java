/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPoweredLiquidIO;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.MachineRegistry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TileEntityFuelConverter extends InventoriedPoweredLiquidIO {

	public static final Fluid BC_FUEL = FluidRegistry.getFluid("fuel");
	public static final Fluid JET_FUEL = FluidRegistry.getFluid("jet fuel");

	public static final int CAPACITY = 5*FluidContainerRegistry.BUCKET_VOLUME;

	private static final ItemStack[] ingredients = {
		new ItemStack(Items.blaze_powder), ItemStacks.netherrackdust, ItemStacks.tar, new ItemStack(Items.magma_cream)
	};

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.FUELENHANCER;
	}

	@Override
	public boolean hasModelTransparency() {
		return true;
	}

	@Override
	public int getRedstoneOverride() {
		if (input.isEmpty())
			return 15;
		if (output.isFull())
			return 15;
		return 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getPowerBelow();

		//ReikaJavaLibrary.pConsole(input+":"+output);

		int factor = 5;

		//ReikaJavaLibrary.pConsoleSideOnly("BC: "+this.getBCFuel()+"    JET: "+this.getJetFuel(), Side.CLIENT);

		boolean convert = true;

		if (power < MINPOWER)
			convert = false;
		if (omega < MINSPEED)
			convert = false;

		if (convert && input.getFluid() != null && input.getFluid().amount >= 2*factor && this.hasItems()) {
			FluidStack drain = input.drain(2*factor, true);
			output.fill(FluidRegistry.getFluidStack("jet fuel", factor), true);
			if (!world.isRemote)
				this.consumeItems();
		}
	}

	private boolean hasItems() {
		for (int i = 0; i < ingredients.length; i++) {
			if (!ReikaInventoryHelper.checkForItemStack(ingredients[i], inv, false)) {
				return false;
			}
		}
		return true;
	}

	private void consumeItems() {
		for (int i = 0; i < ingredients.length; i++) {
			if (ReikaRandomHelper.doWithChance(DifficultyEffects.CONSUMEFRAC.getChance()/32D/100D))
				ReikaInventoryHelper.decrStack(ReikaInventoryHelper.locateInInventory(ingredients[i], inv, false), inv);
		}
	}

	@Override
	public int getSizeInventory() {
		return 9;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return ReikaInventoryHelper.checkForItemStack(is, ingredients, false);
	}

	public int getFuel(FluidStack liquid) {
		if (liquid.getFluid().equals(BC_FUEL))
			return input.getLevel();
		else if (liquid.getFluid().equals(JET_FUEL))
			return output.getLevel();
		return 0;
	}

	public double getLiquidModelOffset(FluidStack liquid) {
		if (liquid.getFluid().equals(BC_FUEL))
			return 10/16D;
		else if (liquid.getFluid().equals(JET_FUEL))
			return 1/16D;
		return 0;
	}

	public int getBCFuel() {
		return input.getLevel();
	}

	public int getJetFuel() {
		return output.getLevel();
	}

	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.FUELLINE || m == MachineRegistry.PIPE;
	}

	@Override
	public Fluid getInputFluid() {
		return BC_FUEL;
	}

	@Override
	public boolean canOutputTo(ForgeDirection to) {
		return to.offsetY == 0;
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return from == ForgeDirection.UP;
	}

	@Override
	public int getCapacity() {
		return CAPACITY;
	}

	@Override
	public boolean canIntakeFromPipe(MachineRegistry p) {
		return p == MachineRegistry.PIPE;
	}

	@Override
	public boolean canOutputToPipe(MachineRegistry p) {
		return p == MachineRegistry.FUELLINE;
	}

}