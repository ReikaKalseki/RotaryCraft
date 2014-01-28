/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Auxiliary;

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
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.RotaryCraft.API.Fillable;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidReceiver;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityFillingStation extends InventoriedPowerLiquidReceiver {

	private ItemStack[] inv = new ItemStack[2];

	public static final int CAPACITY = 32000;

	public static final int FUEL_PER_CRYSTAL = 250;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.getIOSidesDefault(world, x, y, z, meta);
		this.getPower(false, false);
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

		//ReikaJavaLibrary.pConsole(this.getSide()+":"+tank);
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
		int added = i.addFluid(is, tank.getActualFluid(), 1);
		tank.removeLiquid(added);
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
		Fluid f = i.getFluidType(is);
		return (f == null || tank.getActualFluid().equals(f)) && max > current;
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
	public ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
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
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.FILLINGSTATION.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return true;
	}

	@Override
	public int getRedstoneOverride() {
		return this.canFill() ? 0 : 15;
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		NBTTagList nbttaglist = NBT.getTagList("Items");
		inv = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < inv.length)
			{
				inv[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}

		tank.readFromNBT(NBT);
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inv.length; i++)
		{
			if (inv[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				inv[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		NBT.setTag("Items", nbttaglist);

		tank.writeToNBT(NBT);
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

}
