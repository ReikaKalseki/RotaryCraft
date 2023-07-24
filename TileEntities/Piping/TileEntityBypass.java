/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Piping;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;

import Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityBypass extends TileEntityPiping {

	private Fluid fluid;
	private int level;
	private boolean[] forcedConnection = new boolean[6];
	private boolean[] tryForcedConnection = new boolean[6];

	@Override
	public void onPlacedAgainst(ForgeDirection dir) {
		if (MachineRegistry.getMachine(worldObj, xCoord+dir.offsetX, yCoord+dir.offsetY, zCoord+dir.offsetZ) == this.getTile())
			tryForcedConnection[dir.ordinal()] = true;
	}

	@Override
	public void recomputeConnections(World world, int x, int y, int z) {
		forcedConnection = new boolean[6]; //zero out
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			if (tryForcedConnection[i]) {
				TileEntity te = this.getAdjacentTileEntity(dir);
				if (te instanceof TileEntityBypass) {
					TileEntityBypass tb = (TileEntityBypass)te;
					tb.tryForcedConnection[dir.getOpposite().ordinal()] = true;
					this.forceConnect(dir);
					tb.forceConnect(dir.getOpposite());
					tb.queueConnectionEvaluation(2);
				}
			}
		}
		super.recomputeConnections(world, x, y, z);
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m, ForgeDirection side) {
		return m.isStandardPipe() || m == MachineRegistry.FUELLINE || m == MachineRegistry.HOSE || (m == this.getTile() && forcedConnection[side.ordinal()]);
	}

	public void forceConnect(ForgeDirection dir) {
		forcedConnection[dir.ordinal()] = true;
	}

	@Override
	public IIcon getBlockIcon() {
		return Blocks.sandstone.getIcon(1, 0);
	}

	@Override
	public boolean hasLiquid() {
		return level > 0;
	}

	@Override
	public Fluid getFluidType() {
		return fluid;
	}

	@Override
	public MachineRegistry getTile() {
		return MachineRegistry.BYPASS;
	}

	@Override
	public int getFluidLevel() {
		return level;
	}

	@Override
	protected void setFluid(Fluid f) {
		fluid = f;
	}

	@Override
	protected void setLevel(int amt) {
		level = amt;
	}

	@Override
	protected boolean interactsWithMachines() {
		return false;
	}

	@Override
	protected void onIntake(TileEntity te) {

	}

	@Override
	public boolean isValidFluid(Fluid f) {
		return true;
	}

	@Override
	public boolean canReceiveFromPipeOn(ForgeDirection side) {
		return true;
	}

	@Override
	public boolean canEmitToPipeOn(ForgeDirection side) {
		return true;
	}

	@Override
	public Block getPipeBlockType() {
		return Blocks.sandstone;
	}

	@Override
	public boolean canIntakeFromIFluidHandler(ForgeDirection side) {
		return false;
	}

	@Override
	public boolean canOutputToIFluidHandler(ForgeDirection side) {
		return false;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setByte("fconn", ReikaArrayHelper.booleanToByteBitflags(forcedConnection));
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		boolean update = false;
		boolean[] old = new boolean[forcedConnection.length];
		System.arraycopy(forcedConnection, 0, old, 0, old.length);
		forcedConnection = ReikaArrayHelper.booleanFromByteBitflags(NBT.getByte("fconn"), 6);
		update = !Arrays.equals(old, forcedConnection);

		if (worldObj != null && update)
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);
		NBT.setByte("tfconn", ReikaArrayHelper.booleanToByteBitflags(tryForcedConnection));
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);
		boolean update = false;
		boolean[] old = new boolean[tryForcedConnection.length];
		System.arraycopy(tryForcedConnection, 0, old, 0, old.length);
		tryForcedConnection = ReikaArrayHelper.booleanFromByteBitflags(NBT.getByte("tfconn"), 6);
		update = !Arrays.equals(old, tryForcedConnection);
	}

}
