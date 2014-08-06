/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Production;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidProducer;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityRefrigerator extends InventoriedPowerLiquidProducer implements DiscreteFunction {

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
			timer.update();
			soundTimer.update();
			if (timer.checkCap()) {
				if (!world.isRemote)
					this.cycle();
			}
			if (soundTimer.checkCap()) {
				SoundRegistry.FRIDGE.playSoundAtBlock(world, x, y, z, RotaryAux.isMuffled(this) ? 0.25F : 1, 0.88F);
			}
		}
		else {
			timer.reset();
			soundTimer.reset();
		}
		if (!world.isRemote)
			time = timer.getTick();
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
		if (tank.isFull())
			return false;
		return inv[0].itemID == Block.ice.blockID && (inv[1] == null || inv[1].stackSize < inv[1].getMaxStackSize());
	}

	private void cycle() {
		ReikaInventoryHelper.decrStack(0, inv);
		tank.addLiquid(100, FluidRegistry.getFluid("liquid nitrogen"));
		if (rand.nextInt(5) == 0)
			ReikaInventoryHelper.addOrSetStack(ItemStacks.dryice, inv, 1);
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
		return m == MachineRegistry.PIPE;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return slot == 0 && is.itemID == Block.ice.blockID;
	}

	@Override
	public boolean canOutputTo(ForgeDirection to) {
		return true;
	}

	@Override
	public int getCapacity() {
		return 4000;
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
