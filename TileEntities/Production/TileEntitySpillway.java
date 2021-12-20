/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Production;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import Reika.ChromatiCraft.API.Interfaces.CrystalTank;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.Data.BlockStruct.BlockArray;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Libraries.ReikaFluidHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModRegistry.InterfaceCache;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;


public class TileEntitySpillway extends RotaryCraftTileEntity implements PipeConnector, IFluidHandler {

	public static final int CAPACITY = 8000;

	private final HybridTank tank = new HybridTank("spillway", CAPACITY);

	private BlockArray liquidPool;
	private Collection<Coordinate> forcedEmpty = new ArrayList();

	private int activeTick;

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.SPILLWAY;
	}

	@Override
	public boolean hasModelTransparency() {
		return true;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		ForgeDirection dir = this.getDrainSide(meta);
		int dx = x+dir.offsetX;
		int dy = y+dir.offsetY;
		int dz = z+dir.offsetZ;
		Block b = world.getBlock(dx, dy, dz);
		int metadata = world.getBlockMetadata(dx, dy, dz);
		Block b2 = world.getBlock(dx, dy+1, dz);
		Fluid f = ReikaFluidHelper.lookupFluidForBlock(b);
		if ((InterfaceCache.STREAM.instanceOf(b) && metadata == 0) || InterfaceCache.STREAM.instanceOf(b2)) {
			liquidPool = null;
			this.handleStream(world, x, y, z, dx, dy+1, dz);
		}
		else if (f == FluidRegistry.WATER) {
			if (ReikaWorldHelper.isLiquidAColumn(world, dx, dy+1, dz)) {
				liquidPool = null;
				tank.addLiquid((int)(50*ConfigRegistry.getFreeWaterProduction()), FluidRegistry.WATER);
				this.setActive();
			}
			else
				this.formAndDrainPool(world, x, y, z, dx, dy, dz, b);
		}
		else {
			liquidPool = null;
		}
		if (activeTick > 0)
			activeTick--;

		Block ab = world.getBlock(x, y+1, z);
		if (ReikaFluidHelper.lookupFluidForBlock(ab) == FluidRegistry.WATER)
			world.setBlock(x, y+1, z, Blocks.air);

		Object te = this.getAdjacentTileEntity(ForgeDirection.DOWN);
		if (te instanceof CrystalTank) {
			te = ((CrystalTank)te).getController();
			if (te != null) {
				int add = ((CrystalTank)te).addFluid(tank.getActualFluid(), Math.max(1, tank.getLevel()/32));
				if (add > 0)
					tank.removeLiquid(add);
			}
		}
	}

	private void formAndDrainPool(World world, int x, int y, int z, int dx, int dy, int dz, Block id) {
		if (liquidPool == null || liquidPool.isEmpty()) {
			liquidPool = new BlockArray();
			liquidPool.maxDepth = 240;
			liquidPool.clampToChunkLoad = true;
			liquidPool.recursiveAddWithBoundsMetadata(world, dx, dy, dz, id, 0, x-64, y, z-64, x+64, y+24, z+64);
			liquidPool.sortBlocksByDistance(new Coordinate(this));
			liquidPool.sortBlocksByHeight(true);
			forcedEmpty.clear();
		}
		if (liquidPool.isEmpty()) {
			liquidPool = null;
			return;
		}
		if (tank.canTakeIn(1000)) {
			Coordinate c = liquidPool.getNextAndMoveOn();
			forcedEmpty.add(c);
			tank.addLiquid(1000, FluidRegistry.WATER);
			this.setActive();
		}
		for (Coordinate c2 : forcedEmpty) {
			c2.setBlock(world, Blocks.air, 0, 2);
		}
	}

	private void handleStream(World world, int x, int y, int z, int dx, int dy, int dz) {
		//ensure perpendicular?
		boolean act = this.isActive();
		if (tank.canTakeIn(250)) {
			this.setActive();
			tank.addLiquid(250, FluidRegistry.WATER);
			if (this.getTicksExisted()%8 == 0)
				this.syncAllData(false);
		}
		if (act != this.isActive())
			this.syncAllData(false);
	}

	private void setActive() {
		boolean lastActive = activeTick > 0;
		activeTick = 4;
		if (!lastActive)
			this.syncAllData(false);
	}

	public boolean isActive() {
		return activeTick > 0;
	}

	private ForgeDirection getDrainSide(int meta) {
		switch(meta) {
			case 0:
				return ForgeDirection.EAST;
			case 1:
				return ForgeDirection.WEST;
			case 2:
				return ForgeDirection.NORTH;
			case 3:
				return ForgeDirection.SOUTH;
		}
		return ForgeDirection.UNKNOWN;
	}

	public ForgeDirection getDrainSide() {
		return this.getDrainSide(this.getBlockMetadata());
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	public int getLevel() {
		return tank.getLevel();
	}

	public Fluid getFluid() {
		return tank.getActualFluid();
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m.isStandardPipe();
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return this.canConnectToPipe(p);
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return from == ForgeDirection.DOWN && resource.getFluid() == FluidRegistry.WATER ? tank.drain(resource.amount, doDrain) : null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return from == ForgeDirection.DOWN ? tank.drain(maxDrain, doDrain) : null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return from == ForgeDirection.DOWN;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return side == ForgeDirection.DOWN ? Flow.OUTPUT : Flow.NONE;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		tank.writeToNBT(NBT);

		NBT.setInteger("active", activeTick);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		tank.readFromNBT(NBT);

		activeTick = NBT.getInteger("active");
	}

}
