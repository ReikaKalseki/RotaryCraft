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
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.RotaryCraft.Auxiliary.ShaftPowerBus;
import Reika.RotaryCraft.Auxiliary.Interfaces.TransmissionReceiver;
import Reika.RotaryCraft.Base.TileEntity.PoweredLiquidReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityBusController extends PoweredLiquidReceiver implements TransmissionReceiver {

	private ShaftPowerBus bus = new ShaftPowerBus(this);

	private StepTimer timer = new StepTimer(20);

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

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

		if (tank.isEmpty()) {
			torque = 0;
			omega = 0;
		}
		else {
			if (timer.checkCap())
				tank.removeLiquid(1);
		}

		power = (long)torque*(long)omega;
		if (tickcount%10 == 0)
			bus.update();
		//ReikaJavaLibrary.pConsole(bus.getInputPower()+":"+bus.getTotalOutputSides(), Side.SERVER);
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
		case 0:
			readx = xCoord+1;
			readz = zCoord;
			ready = yCoord;
			break;
		case 1:
			readx = xCoord-1;
			readz = zCoord;
			ready = yCoord;
			break;
		case 2:
			readz = zCoord-1;
			readx = xCoord;
			ready = yCoord;
			break;
		case 3:
			readz = zCoord+1;
			readx = xCoord;
			ready = yCoord;
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
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		tank.readFromNBT(NBT);
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

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
