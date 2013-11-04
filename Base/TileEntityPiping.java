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

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Registry.MachineRegistry;

public abstract class TileEntityPiping extends RotaryCraftTileEntity {

	public abstract void draw(World world, int x, int y, int z);
	public abstract void transfer(World world, int x, int y, int z);

	private boolean[] connections = new boolean[6];

	public abstract int getLiquidLevel();

	protected abstract void removeLiquid(int amt);

	public void dumpContents(World world, int x, int y, int z) {
		Fluid f = this.getLiquidType();
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			int dx = x+dir.offsetX;
			int dy = y+dir.offsetY;
			int dz = z+dir.offsetZ;
		}
	}

	public void intakeFluid(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			int dx = x+dir.offsetX;
			int dy = y+dir.offsetY;
			int dz = z+dir.offsetZ;
		}
	}

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
		if (dir.offsetX == 0 && MinecraftForgeClient.getRenderPass() != 1)
			dir = dir.getOpposite();
		return connections[dir.ordinal()];/*
		int dx = xCoord+dir.offsetX;
		int dy = yCoord+dir.offsetY;
		int dz = zCoord+dir.offsetZ;
		World world = worldObj;
		int id = world.getBlockId(dx, dy, dz);
		int meta = world.getBlockMetadata(dx, dy, dz);
		return*/
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

	public void recomputeConnections(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			connections[i] = this.isConnected(dirs[i]);
			world.markBlockForRenderUpdate(x+dirs[i].offsetX, y+dirs[i].offsetY, z+dirs[i].offsetZ);
		}
		world.markBlockForRenderUpdate(x, y, z);
	}

	public void deleteFromAdjacentConnections(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			int dx = x+dir.offsetX;
			int dy = x+dir.offsetY;
			int dz = x+dir.offsetZ;
			MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
			if (m == this.getMachine()) {
				TileEntityPiping te = (TileEntityPiping)world.getBlockTileEntity(dx, dy, dz);
				te.connections[dir.getOpposite().ordinal()] = false;
				world.markBlockForRenderUpdate(dx, dy, dz);
			}
		}
	}

	public void addToAdjacentConnections(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			int dx = x+dir.offsetX;
			int dy = x+dir.offsetY;
			int dz = x+dir.offsetZ;
			MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
			if (m == this.getMachine()) {
				TileEntityPiping te = (TileEntityPiping)world.getBlockTileEntity(dx, dy, dz);
				te.connections[dir.getOpposite().ordinal()] = true;
				world.markBlockForRenderUpdate(dx, dy, dz);
			}
		}
	}

	private boolean isConnected(ForgeDirection dir) {
		int x = xCoord+dir.offsetX;
		int y = yCoord+dir.offsetY;
		int z = zCoord+dir.offsetZ;
		MachineRegistry m = this.getMachine();
		MachineRegistry m2 = MachineRegistry.getMachine(worldObj, x, y, z);
		if (m == m2)
			return true;
		TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
		if (!(tile instanceof PipeConnector))
			return false;
		PipeConnector pc = (PipeConnector)tile;
		return pc.canConnectToPipe(this.getMachine()) && pc.canConnectToPipeOnSide(this.getMachine(), dir);
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);

		for (int i = 0; i < 6; i++) {
			NBT.setBoolean("conn"+i, connections[i]);
		}
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);

		for (int i = 0; i < 6; i++) {
			connections[i] = NBT.getBoolean("conn"+i);
		}
	}

	public boolean isConnectedToNonSelf(ForgeDirection dir) {
		if (!this.isConnectionValidForSide(dir))
			return false;
		if (dir.offsetX == 0 && MinecraftForgeClient.getRenderPass() != 1)
			dir = dir.getOpposite();
		int dx = xCoord+dir.offsetX;
		int dy = yCoord+dir.offsetY;
		int dz = zCoord+dir.offsetZ;
		World world = worldObj;
		int id = world.getBlockId(dx, dy, dz);
		int meta = world.getBlockMetadata(dx, dy, dz);
		return id != this.getMachine().getBlockID() || meta != this.getMachine().getMachineMetadata();
	}
}
