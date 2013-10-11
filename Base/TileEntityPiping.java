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
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import Reika.RotaryCraft.Auxiliary.PipeConnector;

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
		return false;
	}

	/** Direction is relative to the piping block (so DOWN means the block is below the pipe) */
	public boolean isConnectionValidForSide(ForgeDirection dir) {
		TileEntity tile = worldObj.getBlockTileEntity(xCoord+dir.offsetX, yCoord+dir.offsetY, zCoord+dir.offsetZ);
		if (!(tile instanceof PipeConnector))
			return false;
		PipeConnector pc = (PipeConnector)tile;
		return pc.canConnectToPipe(this.getMachine()) && pc.canConnectToPipeOnSide(this.getMachine(), dir);
	}

	@Override
	public final AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1);
	}

	@Override
	public final void onEMP() {}

	public abstract Icon getBlockIcon();

	public abstract boolean hasLiquid();

	public abstract Fluid getLiquidType();
}
