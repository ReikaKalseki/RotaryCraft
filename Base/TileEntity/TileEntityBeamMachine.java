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
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityBeamMachine extends TileEntityPowerReceiver {

	protected ForgeDirection facing = ForgeDirection.UNKNOWN;
	protected int pipemeta;

	protected abstract void makeBeam(World world, int x, int y, int z, int meta);

	public final void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
		case 0:
			read = ForgeDirection.EAST;
			facing = read.getOpposite();
			pipemeta = 0;
			break;
		case 1:
			read = ForgeDirection.WEST;
			facing = read.getOpposite();
			pipemeta = 0;
			break;
		case 2:
			read = ForgeDirection.NORTH;
			facing = read.getOpposite();
			pipemeta = 2;
			break;
		case 3:
			read = ForgeDirection.SOUTH;
			facing = read.getOpposite();
			pipemeta = 2;
			break;
		case 4:	//moving up
			read = ForgeDirection.DOWN;
			facing = read.getOpposite();
			break;
		case 5:	//moving down
			read = ForgeDirection.UP;
			facing = read.getOpposite();
			break;
		}
	}

	public final ForgeDirection getFacing() {
		return facing;
	}

}
