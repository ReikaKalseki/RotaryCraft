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

	protected int xstep;
	protected int ystep;
	protected int zstep;
	protected int pipemeta;

	public abstract void makeBeam(World world, int x, int y, int z, int meta);

	public final void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
		case 0:
			read = ForgeDirection.EAST;
			xstep = -1;
			ystep = 0;
			zstep = 0;
			pipemeta = 0;
			break;
		case 1:
			read = ForgeDirection.WEST;
			xstep = 1;
			ystep = 0;
			zstep = 0;
			pipemeta = 0;
			break;
		case 2:
			read = ForgeDirection.NORTH;
			xstep = 0;
			ystep = 0;
			zstep = 1;
			pipemeta = 2;
			break;
		case 3:
			read = ForgeDirection.SOUTH;
			xstep = 0;
			ystep = 0;
			zstep = -1;
			pipemeta = 2;
			break;
		case 4:	//moving up
			read = ForgeDirection.DOWN;
			xstep = 0;
			ystep = 1;
			zstep = 0;
			break;
		case 5:	//moving down
			read = ForgeDirection.UP;
			xstep = 0;
			ystep = -1;
			zstep = 0;
			break;
		}
	}

	public final int getXStep() {
		return xstep;
	}

	public final int getYStep() {
		return ystep;
	}

	public final int getZStep() {
		return zstep;
	}

}