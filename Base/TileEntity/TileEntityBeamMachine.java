/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

import net.minecraft.world.World;

public abstract class TileEntityBeamMachine extends TileEntityPowerReceiver {

	public int xstep;
	public int ystep;
	public int zstep;
	protected int pipemeta;

	public abstract void makeBeam(World world, int x, int y, int z, int meta);

	public final void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
		case 0:
			readx = x+1;
			readz = z;
			ready = y;
			xstep = -1;
			ystep = 0;
			zstep = 0;
			pipemeta = 0;
			break;
		case 1:
			readx = x-1;
			readz = z;
			ready = y;
			xstep = 1;
			ystep = 0;
			zstep = 0;
			pipemeta = 0;
			break;
		case 2:
			readz = z-1;
			readx = x;
			ready = y;
			xstep = 0;
			ystep = 0;
			zstep = 1;
			pipemeta = 2;
			break;
		case 3:
			readz = z+1;
			readx = x;
			ready = y;
			xstep = 0;
			ystep = 0;
			zstep = -1;
			pipemeta = 2;
			break;
		case 4:	//moving up
			readx = x;
			readz = z;
			ready = y-1;
			xstep = 0;
			ystep = 1;
			zstep = 0;
			break;
		case 5:	//moving down
			readx = x;
			readz = z;
			ready = y+1;
			xstep = 0;
			ystep = -1;
			zstep = 0;
			break;
		}
	}

}
