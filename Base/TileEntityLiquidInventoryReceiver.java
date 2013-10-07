package Reika.RotaryCraft.Base;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.RotaryCraft.Auxiliary.PipeConnector;

public abstract class TileEntityLiquidInventoryReceiver extends TileEntityLiquidPowered implements IFluidHandler, PipeConnector, ISidedInventory {

	public void openChest() {

	}

	public void closeChest() {

	}

	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	public abstract boolean isItemValidForSlot(int slot, ItemStack is);

	public final ItemStack decrStackSize(int par1, int par2) {
		return ReikaInventoryHelper.decrStackSize(this, par1, par2);
	}

	public final ItemStack getStackInSlotOnClosing(int par1) {
		return ReikaInventoryHelper.getStackInSlotOnClosing(this, par1);
	}

}
