/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Processing;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesDryingBed;
import Reika.RotaryCraft.Base.TileEntity.InventoriedRCFluidReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityDryingBed extends InventoriedRCFluidReceiver {

	private StepTimer timer = new StepTimer(400);
	public int progress;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		ItemStack is = tank.isEmpty() ? null : RecipesDryingBed.getRecipes().getDryingResult(tank.getFluid());
		if (this.canMake(is)) {
			timer.update();
			if (timer.checkCap()) {
				while (this.canMake(is)) {
					this.make(is);
					is = tank.isEmpty() ? null : RecipesDryingBed.getRecipes().getDryingResult(tank.getFluid());
				}
			}
		}
		else {
			timer.reset();
		}
		progress = timer.getTick();
	}

	private boolean canMake(ItemStack is) {
		return is != null && this.canMakeMore(is);
	}

	private void make(ItemStack is) {
		ReikaInventoryHelper.addOrSetStack(is, inv, 0);
		int amt = RecipesDryingBed.getRecipes().getRecipeConsumption(is);
		tank.removeLiquid(amt);
	}

	private boolean canMakeMore(ItemStack is) {
		if (inv[0] == null)
			return true;
		return ReikaItemHelper.matchStacks(is, inv[0]) && inv[0].stackSize+is.stackSize <= inv[0].getMaxStackSize();
	}

	@Override
	public boolean canExtractItem(int i, ItemStack is, int s) {
		return true;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack is) {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.DRYING;
	}

	@Override
	public boolean hasModelTransparency() {
		return true;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m.isStandardPipe() || m == MachineRegistry.HOSE || m == MachineRegistry.FUELLINE;
	}

	@Override
	public int getCapacity() {
		return 2000;
	}

	@Override
	public Fluid getInputFluid() {
		return null;
	}

	@Override
	public boolean isValidFluid(Fluid f) {
		return RecipesDryingBed.getRecipes().isValidFluid(f);
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return from != ForgeDirection.DOWN;
	}

	public int getLiquidScaled(int a) {
		return a * tank.getLevel() / tank.getCapacity();
	}

	public int getProgressScaled(int a) {
		return a * progress / timer.getCap();
	}

	public Fluid getFluid() {
		return tank.getActualFluid();
	}

	public boolean hasFluid() {
		return !tank.isEmpty();
	}

}
