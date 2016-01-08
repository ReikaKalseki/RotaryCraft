/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.World;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Instantiable.Data.BlockStruct.BlockArray;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityFlooder extends RotaryCraftTileEntity implements IFluidHandler, PipeConnector {

	public int oldLevel;

	private final HybridTank tank = new HybridTank("flooder", 4000);

	private StepTimer waterTimer = new StepTimer(5);

	private BlockArray blocks = new BlockArray();

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		if (world.isRemote)
			return;
		/*
		tickcount++;/*
		if (BlockFallingLiquid.canMoveInto(world, x, y-1, z)) {
			waterTimer.update();
			if (tank.getLevel() >= 1000 && waterTimer.checkCap()) {
				tank.removeLiquid(1000);
				world.setBlock(x, y-1, z, RotaryCraft.waterblock.blockID);
			}
		}*//*
		tank.addLiquid(1000, FluidRegistry.WATER);
		//Do with entities?
		if (tickcount > 20 && tank.getLevel() >= 1000) {
			tickcount = 0;
			if (!world.isRemote)
				world.spawnEntityInWorld(new EntityLiquidBlock(world, x, y-1, z, tank.getActualFluid(), this));
			tank.removeLiquid(1000);
		}*/
		this.legacyFunction(world, x, y, z, meta);
	}

	private void legacyFunction(World world, int x, int y, int z, int meta) {
		tickcount++;
		if (tank.getLevel() >= 1000) {
			if (blocks.isEmpty()) {
				int r = ConfigRegistry.SPILLERRANGE.getValue();
				if (r > 0) {
					blocks.recursiveAddWithBounds(world, x, y-1, z, Blocks.air, x-r, 0, z-r, x+r, y, z+r);
					blocks.recursiveAddWithBoundsNoFluidSource(world, x, y-1, z, this.getFluidID(), x-r, 0, z-r, x+r, y, z+r);
				}
				blocks.sortBlocksByHeight();
			}
			boolean drain = false;
			if (drain) {
				//for (int i = 8; i <= 11; i++)
				//	ReikaChunkHelper.removeBlocksFromChunk(world, x, z, i, -1);
			}
			else if (tickcount > 1 && !blocks.isEmpty()) {
				tickcount = 0;
				Coordinate c = blocks.getNextAndMoveOn();
				c.setBlock(world, this.getFluidID());
				//ReikaJavaLibrary.pConsole(c.xCoord+" "+c.yCoord+" "+c.zCoord);
				world.markBlockForUpdate(c.xCoord, c.yCoord, c.zCoord);
				tank.drain(1000, true);
			}
		}
		else {
			blocks.clear();
		}
	}

	private Block getFluidID() {
		return !tank.isEmpty() && tank.getActualFluid().canBePlacedInWorld() ? tank.getActualFluid().getBlock() : Blocks.air;
	}

	private boolean canTakeLiquid(Fluid f) {
		if (!f.canBePlacedInWorld())
			return false;
		if (tank.isEmpty())
			return true;
		return tank.getActualFluid().equals(f);
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		tank.writeToNBT(NBT);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		tank.readFromNBT(NBT);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.SPILLER;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return this.canConnectToPipe(p) && side != ForgeDirection.DOWN;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return this.canFill(from, resource.getFluid()) ? tank.fill(resource, doFill) : 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return from != ForgeDirection.DOWN && fluid.canBePlacedInWorld();
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return side != ForgeDirection.DOWN ? Flow.INPUT : Flow.NONE;
	}
}
