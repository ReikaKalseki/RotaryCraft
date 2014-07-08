/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Transmission;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.RotaryCraft.Auxiliary.ShaftPowerBus;
import Reika.RotaryCraft.Auxiliary.Interfaces.TransmissionReceiver;
import Reika.RotaryCraft.Base.TileEntity.PoweredLiquidReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityBusController extends PoweredLiquidReceiver implements TransmissionReceiver {

	private ShaftPowerBus bus = new ShaftPowerBus(this);

	private StepTimer timer = new StepTimer(100);

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.BUSCONTROLLER;
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
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false);

		timer.update();

		if (DragonAPICore.debugtest)
			tank.addLiquid(5, FluidRegistry.getFluid("lubricant"));

		if (tank.isEmpty()) {
			torque = 0;
			omega = 0;
		}
		else {
			if (power > 0 && timer.checkCap())
				tank.removeLiquid(this.getLubricantUsed());
		}

		power = (long)torque*(long)omega;
		if (tickcount%10 == 0)
			bus.update();
		//ReikaJavaLibrary.pConsole(bus.getInputPower()+":"+bus.getTotalOutputSides(), Side.SERVER);
	}

	private int getLubricantUsed() {
		return 2*bus.getSize()+bus.getTotalOutputSides();
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
			read = ForgeDirection.NORTH;
			break;
		case 3:
			read = ForgeDirection.SOUTH;
			break;
		}
	}

	public ShaftPowerBus getBus() {
		return bus;
	}

	public void clear() {
		bus.clear();
		bus = null;
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		tank.readFromNBT(NBT);
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		tank.writeToNBT(NBT);
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.HOSE;
	}

	@Override
	public Fluid getInputFluid() {
		return FluidRegistry.getFluid("lubricant");
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return from.offsetY != 0;
	}

	@Override
	public int getCapacity() {
		return 8000;
	}

}
