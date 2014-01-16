/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Transmission;

import net.minecraft.world.World;
import Reika.RotaryCraft.Auxiliary.ShaftPowerBus;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityBusController extends TileEntityPowerReceiver {

	private ShaftPowerBus bus = new ShaftPowerBus(this);

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.BUSCONTROLLER.ordinal();
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
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false, false);
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

}
