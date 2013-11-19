/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityReservoir extends RotaryCraftTileEntity implements PipeConnector, IFluidHandler {

	public static final int CAPACITY = 64000;

	private HybridTank tank = new HybridTank("reservoir", CAPACITY);

	public int getLiquidScaled(int par1)
	{
		return (tank.getLevel()*par1)/CAPACITY;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.transferBetween(world, x, y, z);

		if (world.isRaining() && worldObj.canBlockSeeTheSky(x, y+1, z)) {
			if (this.isEmpty() || (this.getFluid().equals(FluidRegistry.WATER) && this.getLevel() < CAPACITY)) {
				this.addLiquid(25, FluidRegistry.WATER);
			}
		}

		if (tank.getActualFluid() == null || this.getLevel() <= 0)
			tank.empty();
	}

	private void transferBetween(World world, int x, int y, int z) {
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			int dx = x+dir.offsetX;
			int dy = y+dir.offsetY;
			int dz = z+dir.offsetZ;
			if (tank.getLevel() < CAPACITY) {
				MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
				if (m == MachineRegistry.RESERVOIR) {
					TileEntityReservoir tile = (TileEntityReservoir)world.getBlockTileEntity(dx, dy, dz);
					if (this.canMixWith(tile)) {
						int diff = tile.getLevel()-this.getLevel();
						tile.tank.removeLiquid(diff/2);
						tank.addLiquid(diff/2, tile.getFluid());
					}
				}
			}
		}
	}

	private boolean canMixWith(TileEntityReservoir tile) {
		if (tile.getFluid() == null)
			return false;
		if (tank.isEmpty() || this.getFluid().equals(tile.getFluid())) {
			return true;
		}
		return false;
	}


	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);

		tank.writeToNBT(NBT);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);

		tank.readFromNBT(NBT);
	}

	@Override
	public boolean hasModelTransparency() {
		return true;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.RESERVOIR.ordinal();
	}

	@Override
	public int getRedstoneOverride() {
		return (int)(15*tank.getFraction());
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE || m == MachineRegistry.HOSE || m == MachineRegistry.FUELLINE || m == MachineRegistry.VALVE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return (side != ForgeDirection.DOWN && p != MachineRegistry.VALVE) || (side == ForgeDirection.UP && p == MachineRegistry.VALVE);
	}

	@Override
	public void onEMP() {}

	public AxisAlignedBB getHitbox() {
		if (this.isEdgePiece(worldObj, xCoord, yCoord, zCoord))
			return ReikaAABBHelper.getBlockAABB(xCoord, yCoord, zCoord);
		return AxisAlignedBB.getAABBPool().getAABB(xCoord, yCoord, zCoord, xCoord+1, yCoord+0.0625, zCoord+1);
	}

	private boolean isEdgePiece(World world, int x, int y, int z) {
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			int dx = x+dir.offsetX;
			int dy = y+dir.offsetY;
			int dz = z+dir.offsetZ;
			MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
			if (m != MachineRegistry.RESERVOIR)
				return true;
		}
		return false;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (from == ForgeDirection.UP)
			return 0;
		return tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (from == ForgeDirection.UP)
			return null;
		return tank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (from == ForgeDirection.UP)
			return null;
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return from != ForgeDirection.UP;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return from != ForgeDirection.UP;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	public boolean canAcceptFluid(Fluid f) {
		return tank.isEmpty() || f.equals(tank.getActualFluid());
	}

	public int getLevel() {
		return tank.getLevel();
	}

	public Fluid getFluid() {
		return tank.getActualFluid();
	}

	public void setLevel(int amt, Fluid f) {
		tank.setContents(amt, f);
	}

	public void removeLiquid(int amt) {
		tank.removeLiquid(amt);
	}

	public void addLiquid(int amt, Fluid f) {
		tank.addLiquid(amt, f);
	}

	public boolean isEmpty() {
		return tank.isEmpty();
	}

	public FluidStack getContents() {
		return tank.getFluid();
	}

	public void setLevel(int level) {
		if (this.isEmpty())
			return;
		tank.setContents(level, this.getFluid());
	}

	public boolean isConnectedOnSide(ForgeDirection dir) {
		int dx = xCoord+dir.offsetX;
		int dy = yCoord+dir.offsetY;
		int dz = zCoord+dir.offsetZ;
		MachineRegistry m = MachineRegistry.getMachine(worldObj, dx, dy, dz);
		if (m == MachineRegistry.RESERVOIR) {
			TileEntityReservoir te = (TileEntityReservoir)worldObj.getBlockTileEntity(dx, dy, dz);
			return te.isEmpty() || this.isEmpty() || te.getFluid().equals(this.getFluid());
		}
		return false;
	}

	public void setEmpty() {
		tank.empty();
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return side == ForgeDirection.DOWN ? Flow.OUTPUT: Flow.INPUT;
	}
}
