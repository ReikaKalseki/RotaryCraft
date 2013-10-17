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

import net.minecraft.block.Block;
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
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Models.ModelReservoir;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityReservoir extends RotaryCraftTileEntity implements PipeConnector, IFluidHandler {

	public static final int CAPACITY = 64*RotaryConfig.MILLIBUCKET;

	private HybridTank tank = new HybridTank("reservoir", CAPACITY);

	public int getLiquidScaled(int par1)
	{
		return (tank.getLevel()*par1)/CAPACITY;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.getLiq(world, x, y, z, meta);
		this.transferBetween(world, x, y, z);

		if (world.isRaining() && worldObj.canBlockSeeTheSky(x, y+1, z)) {
			if (this.isEmpty() || (this.getFluid().equals(FluidRegistry.WATER) && this.getLevel() < CAPACITY)) {
				this.addLiquid(25, FluidRegistry.WATER);
			}
		}
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

	public void getLiq(World world, int x, int y, int z, int metadata) {
		int oldLevel = 0;
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			int dx = x+dir.offsetX;
			int dy = y+dir.offsetY;
			int dz = z+dir.offsetZ;
			if (tank.getLevel() < CAPACITY) {
				MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
				if (m == MachineRegistry.PIPE) {
					TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(dx, dy, dz);
					if (tile != null) {
						if (tile.liquidID == 11 && this.canAcceptFluid(FluidRegistry.LAVA) && tile.liquidLevel > 0) {
							oldLevel = tile.liquidLevel;
							tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4, 0, "max");
							tank.addLiquid(oldLevel/4, FluidRegistry.LAVA);
						}
						else if (tile.liquidID == 9 && this.canAcceptFluid(FluidRegistry.WATER) && tile.liquidLevel > 0) {
							oldLevel = tile.liquidLevel;
							tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4, 0, "max");
							tank.addLiquid(oldLevel/4, FluidRegistry.WATER);
						}
						else if (tile.liquidID > 0) {
							Fluid f = FluidRegistry.lookupFluidForBlock(Block.blocksList[tile.liquidID]);
							if (f != null && this.canAcceptFluid(f)) {
								tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4, 0, "max");
								tank.addLiquid(oldLevel/4, f);
							}
						}
					}
				}
				if (m == MachineRegistry.HOSE) {
					TileEntityHose tile = (TileEntityHose)world.getBlockTileEntity(dx, dy, dz);
					if (tile != null) {
						oldLevel = tile.lubricant;
						tile.lubricant = ReikaMathLibrary.extrema(tile.lubricant-tile.lubricant/4, 0, "max");
						tank.addLiquid(oldLevel/4, FluidRegistry.getFluid("lubricant"));
					}
				}
				if (m == MachineRegistry.FUELLINE) {
					TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(dx, dy, dz);
					if (tile != null) {
						oldLevel = tile.fuel;
						tile.fuel = ReikaMathLibrary.extrema(tile.fuel-tile.fuel/4, 0, "max");
						tank.addLiquid(oldLevel/4, FluidRegistry.getFluid("jet fuel"));
					}
				}
			}
		}
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
	public boolean hasModelTransparency() {
		return true;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelReservoir();
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
		return m == MachineRegistry.PIPE || m == MachineRegistry.HOSE || m == MachineRegistry.FUELLINE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return side != ForgeDirection.DOWN;
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
		if (from == ForgeDirection.DOWN)
			return 0;
		return tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (from == ForgeDirection.DOWN)
			return null;
		return tank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (from == ForgeDirection.DOWN)
			return null;
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return from != ForgeDirection.DOWN;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return from != ForgeDirection.DOWN;
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
}
