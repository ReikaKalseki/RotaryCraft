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
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Entities.EntityLiquidBlock;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityFlooder extends RotaryCraftTileEntity implements IFluidHandler, PipeConnector {
	//Make pick random coord in 16-block radius, find top block (solid or source block), ++y, then add liquid

	public int oldLevel;

	private HybridTank tank = new HybridTank("flooder", 4000);

	private StepTimer waterTimer = new StepTimer(5);

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		tickcount++;/*
		if (BlockFallingLiquid.canMoveInto(world, x, y-1, z)) {
			waterTimer.update();
			if (tank.getLevel() >= 1000 && waterTimer.checkCap()) {
				tank.removeLiquid(1000);
				world.setBlock(x, y-1, z, RotaryCraft.waterblock.blockID);
			}
		}*/
		tank.addLiquid(1000, FluidRegistry.WATER);
		//Do with entities?
		if (tickcount > 20 && tank.getLevel() >= 1000) {
			tickcount = 0;
			if (!world.isRemote)
				world.spawnEntityInWorld(new EntityLiquidBlock(world, x, y-1, z, tank.getActualFluid(), this));
			tank.removeLiquid(1000);
			world.setBlock(x, y, z, 0);
		}
	}

	private boolean canTakeLiquid(Fluid f) {
		if (!f.canBePlacedInWorld())
			return false;
		if (tank.isEmpty())
			return true;
		return tank.getActualFluid().equals(f);
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

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);

		tank.readFromNBT(NBT);
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.SPILLER.ordinal();
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
	public void animateWithTick(World world, int x, int y, int z) {

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
