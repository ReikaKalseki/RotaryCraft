/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Registry.MachineRegistry;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile.PipeType;

public class TileEntitySteam extends TileEntityIOMachine implements PowerGenerator, SimpleProvider, IPipeConnection {

	private int steamLevel;

	public static final int CAPACITY = 300000;
	public static final int GEN_OMEGA = 2048;
	public static final int MAX_TORQUE = 256;

	private ForgeDirection facingDir;

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		return new PowerSourceList().addSource(this);
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.STEAMTURBINE;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		switch(meta) {
		case 0:
			facingDir = ForgeDirection.SOUTH;
			break;
		case 1:
			facingDir = ForgeDirection.EAST;
			break;
		case 2:
			facingDir = ForgeDirection.NORTH;
			break;
		case 3:
			facingDir = ForgeDirection.WEST;
			break;
		}
		int dx = x-facingDir.offsetX;
		int dy = y-facingDir.offsetY;
		int dz = z-facingDir.offsetZ;
		writex = x+facingDir.offsetX;
		writey = y+facingDir.offsetY;
		writez = z+facingDir.offsetZ;

		this.getSteam(world, dx, dy, dz);
		this.genPower();
	}

	private void genPower() {
		omega = this.getGenOmega();
		torque = Math.min(this.getGenTorque(), MAX_TORQUE);
		power = (long)omega*(long)torque;
		steamLevel -= steamLevel/200+1;
	}

	public long getGenPower() {
		return this.getGenOmega() * this.getGenTorque();
	}

	public int getGenTorque() {
		return steamLevel > 0 ? 8*steamLevel/FluidContainerRegistry.BUCKET_VOLUME : 0;
	}

	public int getGenOmega() {
		return this.getGenTorque() > 0 ? (int)(this.getGenTorque()/(double)MAX_TORQUE*GEN_OMEGA) : 0;
	}

	private void getSteam(World world, int dx, int dy, int dz) {
		if (steamLevel <= CAPACITY-FluidContainerRegistry.BUCKET_VOLUME) {
			TileEntity te = world.getBlockTileEntity(dx, dy, dz);
			if (te instanceof IFluidHandler) {
				IFluidHandler ic = (IFluidHandler)te;
				FluidStack liq = ic.drain(facingDir.getOpposite(), FluidContainerRegistry.BUCKET_VOLUME, true);
				if (liq != null && liq.amount > 0 && liq.getFluid().equals(FluidRegistry.getFluid("water")))
					steamLevel += liq.amount;
			}
		}
	}

	public int getSteam() {
		return steamLevel;
	}

	@Override
	public long getMaxPower() {
		return power;
	}

	@Override
	public long getCurrentPower() {
		return power;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("storage", steamLevel);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		steamLevel = NBT.getInteger("storage");
	}

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection dir) {
		if (facingDir == null)
			return ConnectOverride.DEFAULT;
		return dir == facingDir.getOpposite() && type == PipeType.FLUID ? ConnectOverride.CONNECT : ConnectOverride.DISCONNECT;
	}

	@Override
	public int getEmittingX() {
		return writex;
	}

	@Override
	public int getEmittingY() {
		return writey;
	}

	@Override
	public int getEmittingZ() {
		return writez;
	}

}
