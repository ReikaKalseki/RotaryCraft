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

import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.Auxiliary.EnumLook;
import Reika.RotaryCraft.TileEntities.TileEntityEngine;

public abstract class TileEntityPiping extends RotaryCraftTileEntity {

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
				m = MachineRegistry.getMachine(worldObj, xCoord, yCoord-1, zCoord);
				tile = worldObj.getBlockTileEntity(xCoord, yCoord-1, zCoord);
				break;
			case MINX:
				m = MachineRegistry.getMachine(worldObj, xCoord-1, yCoord, zCoord);
				tile = worldObj.getBlockTileEntity(xCoord-1, yCoord, zCoord);
				break;
			case MINZ:
				m = MachineRegistry.getMachine(worldObj, xCoord, yCoord, zCoord-1);
				tile = worldObj.getBlockTileEntity(xCoord, yCoord, zCoord-1);
				break;
			case PLUSX:
				m = MachineRegistry.getMachine(worldObj, xCoord+1, yCoord, zCoord);
				tile = worldObj.getBlockTileEntity(xCoord+1, yCoord, zCoord);
				break;
			case PLUSZ:
				m = MachineRegistry.getMachine(worldObj, xCoord, yCoord, zCoord+1);
				tile = worldObj.getBlockTileEntity(xCoord, yCoord, zCoord+1);
				break;
			case UP:
				m = MachineRegistry.getMachine(worldObj, xCoord, yCoord+1, zCoord);
				tile = worldObj.getBlockTileEntity(xCoord, yCoord+1, zCoord);
				break;
		}
		switch(this.getMachine()) {
			case PIPE:
				return this.pipeConnect(m, tile, look);
			case HOSE:
				return this.hoseConnect(m, tile, look);
			case FUELLINE:
				return this.fuelLineConnect(m, tile, look);
			case SPILLER:
				return this.spillerConnect(m, tile, look);
			default:
				return false;
		}
	}

	private boolean fuelLineConnect(MachineRegistry m, TileEntity tile, EnumLook look) {
		if (m == MachineRegistry.FUELLINE)
			return true;
		if (m == MachineRegistry.ECU)
			return true;
		if (m == MachineRegistry.FRACTIONATOR && look == EnumLook.DOWN)
			return true;
		if (m == MachineRegistry.ENGINE && look == EnumLook.UP) {
			TileEntityEngine te = (TileEntityEngine)tile;
			return (te.type.isJetFueled());
		}
		return false;
	}

	private boolean hoseConnect(MachineRegistry m, TileEntity tile, EnumLook look) {
		if (m == MachineRegistry.HOSE)
			return true;
		if (m == MachineRegistry.GRINDER && look != EnumLook.DOWN)
			return true;
		if (m == MachineRegistry.GEARBOX && look != EnumLook.DOWN)
			return true;
		return false;
	}

	private boolean pipeConnect(MachineRegistry m, TileEntity tile, EnumLook look) {
		if (m == MachineRegistry.PIPE)
			return true;
		if (m == MachineRegistry.SPILLER)
			return true;
		if (m == MachineRegistry.RESERVOIR)
			return true;
		if (m == MachineRegistry.PULSEJET)
			return true;
		if (m == MachineRegistry.EXTRACTOR)
			return true;
		if (m == MachineRegistry.OBSIDIAN && look != EnumLook.DOWN)
			return true;
		if (m == MachineRegistry.PUMP && look != EnumLook.UP && look != EnumLook.DOWN)
			return true;
		if (m == MachineRegistry.SPRINKLER && look == EnumLook.DOWN)
			return true;
		if (m == MachineRegistry.BUCKETFILLER && look != EnumLook.DOWN && look != EnumLook.UP)
			return true;
		return false;
	}

	private boolean spillerConnect(MachineRegistry m, TileEntity tile, EnumLook look) {
		if (m == MachineRegistry.PIPE)
			return true;
		if (m == MachineRegistry.PUMP && look != EnumLook.DOWN && look != EnumLook.UP)
			return true;
		return false;
	}

	@Override
	public final AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1);
	}
}
