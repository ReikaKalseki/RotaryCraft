/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;

public abstract class TileEntity1DTransmitter extends TileEntityTransmissionMachine implements SimpleProvider {

	protected int ratio;

	public final int getRatio() {
		return ratio;
	}

	public void getIOSides(World world, int x, int y, int z, int meta, boolean hasVertical) {
		switch(meta){
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
		case 4:
			if (hasVertical) {
				read = ForgeDirection.DOWN;
			}
			break;
		case 5:
			if (hasVertical) {
				read = ForgeDirection.UP;
			}
			break;
		}
		write = read.getOpposite();
	}

	public abstract void transferPower(World world, int x, int y, int z, int meta);

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		if (read == null)
			return new PowerSourceList();
		return PowerSourceList.getAllFrom(worldObj, xCoord+read.offsetX, yCoord+read.offsetY, zCoord+read.offsetZ, this, caller);
	}
}
