/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Production;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.Interfaces.MultiOperational;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidProducer;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityRefrigerator extends InventoriedPowerLiquidProducer implements MultiOperational {

	public int time;
	private StepTimer timer = new StepTimer(20);
	private StepTimer soundTimer = new StepTimer(20);

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false);
		timer.setCap(this.getOperationTime());

		if (this.canProgress()) {
			soundTimer.update();
			if (soundTimer.checkCap()) {
				SoundRegistry.FRIDGE.playSoundAtBlock(world, x, y, z, RotaryAux.isMuffled(this) ? 0.25F : 1, 0.88F);
			}
		}
		else {
			soundTimer.reset();
		}

		int n = this.getNumberConsecutiveOperations();
		for (int i = 0; i < n; i++)
			this.doOperation(n > 1);

		if (!world.isRemote)
			time = timer.getTick();
	}

	private void doOperation(boolean multiple) {
		if (this.canProgress()) {
			timer.update();
			if (multiple || timer.checkCap()) {
				if (!worldObj.isRemote)
					this.cycle();
			}
		}
		else {
			timer.reset();
		}
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
			case 0:
				read = ForgeDirection.EAST;
				break;
			case 1:
				read = ForgeDirection.WEST;
				break;
			case 2:
				read = ForgeDirection.SOUTH;
				break;
			case 3:
				read = ForgeDirection.NORTH;
				break;
		}
	}

	private boolean canProgress() {
		if (power < MINPOWER || torque < MINTORQUE)
			return false;
		if (inv[0] == null)
			return false;
		if (!tank.canTakeIn(this.getProducedLN2()))
			return false;
		return ReikaItemHelper.matchStackWithBlock(inv[0], Blocks.ice) && (inv[1] == null || inv[1].stackSize < inv[1].getMaxStackSize());
	}

	private void cycle() {
		ReikaInventoryHelper.decrStack(0, inv);
		tank.addLiquid(this.getProducedLN2(), FluidRegistry.getFluid("rc liquid nitrogen"));
		if (rand.nextInt(4) == 0)
			ReikaInventoryHelper.addOrSetStack(ItemStacks.dryice, inv, 1);
	}

	private int getProducedLN2() {
		int over = torque/MINTORQUE;
		return Math.min(2000, 100*over*over);
	}

	public void setLevel(int lvl) {
		if (!tank.isEmpty())
			tank.setContents(lvl, tank.getActualFluid());
	}

	public int getProgressScaled(int a) {
		return time * a / this.getOperationTime();
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i == 1;
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m.isStandardPipe();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return slot == 0 && ReikaItemHelper.matchStackWithBlock(is, Blocks.ice);
	}

	@Override
	public boolean canOutputTo(ForgeDirection to) {
		return true;
	}

	@Override
	public int getCapacity() {
		return 12000;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.REFRIGERATOR;
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
	public int getOperationTime() {
		return DurationRegistry.REFRIGERATOR.getOperationTime(omega);
	}

	@Override
	public int getNumberConsecutiveOperations() {
		return DurationRegistry.REFRIGERATOR.getNumberOperations(omega);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		time = NBT.getInteger("timer");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setInteger("timer", time);
	}

	public int getLiquidScaled(int i) {
		return tank.getLevel() * i / tank.getCapacity();
	}

}
