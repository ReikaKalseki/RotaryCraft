/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Auxiliary.EnumLook;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Registry.MachineRegistry;

public abstract class TileEntityPiping extends RotaryCraftTileEntity implements PipeConnector {

	public abstract void draw(World world, int x, int y, int z);
	public abstract void transfer(World world, int x, int y, int z);

	@Override
	public final RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public final void animateWithTick(World world, int x, int y, int z) {}

	@Override
	public final boolean hasModelTransparency() {
		return true;
	}

	/** Look is relative to the piping block (so DOWN means the block is below the pipe) */
	public boolean isConnectionValidForIDAndSide(EnumLook look) {
		MachineRegistry m = null; TileEntity tile = null;
		switch(look) {
		case DOWN:
			tile = worldObj.getBlockTileEntity(xCoord, yCoord-1, zCoord);
			break;
		case MINX:
			tile = worldObj.getBlockTileEntity(xCoord-1, yCoord, zCoord);
			break;
		case MINZ:
			tile = worldObj.getBlockTileEntity(xCoord, yCoord, zCoord-1);
			break;
		case PLUSX:
			tile = worldObj.getBlockTileEntity(xCoord+1, yCoord, zCoord);
			break;
		case PLUSZ:
			tile = worldObj.getBlockTileEntity(xCoord, yCoord, zCoord+1);
			break;
		case UP:
			tile = worldObj.getBlockTileEntity(xCoord, yCoord+1, zCoord);
			break;
		}

		if (!(tile instanceof PipeConnector))
			return false;
		PipeConnector pc = (PipeConnector)tile;
		return pc.canConnectToPipe(this.getMachine()) && pc.canConnectToPipeOnSide(this.getMachine(), look);
	}

	@Override
	public final AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1);
	}
}
