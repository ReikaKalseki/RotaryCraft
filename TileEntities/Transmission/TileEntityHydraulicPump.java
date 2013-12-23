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
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityHydraulicLine;

public class TileEntityHydraulicPump extends TileEntityPowerReceiver {

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false, false);

		int pressure = torque*16;
		int flowrate = omega/16;

		MachineRegistry m = MachineRegistry.getMachine(world, writex, writey, writez);
		if (m == MachineRegistry.HYDRAULICLINE) {
			TileEntityHydraulicLine te = (TileEntityHydraulicLine)world.getBlockTileEntity(writex, writey, writez);
			te.setPressure(pressure);
			te.setFlowRate(flowrate);
		}
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
		case 0:
			readx = xCoord+1;
			readz = zCoord;
			ready = yCoord;
			writex = xCoord-1;
			writey = yCoord;
			writez = zCoord;
			break;
		case 1:
			readx = xCoord-1;
			readz = zCoord;
			ready = yCoord;
			writex = xCoord+1;
			writey = yCoord;
			writez = zCoord;
			break;
		case 2:
			readz = zCoord+1;
			readx = xCoord;
			ready = yCoord;
			writex = xCoord;
			writey = yCoord;
			writez = zCoord-1;
			break;
		case 3:
			readz = zCoord-1;
			readx = xCoord;
			ready = yCoord;
			writex = xCoord;
			writey = yCoord;
			writez = zCoord+1;
			break;
		}
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.HYDRAULIC.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

}
