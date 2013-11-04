/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Piping;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Base.TileEntityPiping;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityBucketFiller;
import Reika.RotaryCraft.TileEntities.TileEntityReservoir;
import Reika.RotaryCraft.TileEntities.Production.TileEntityFractionator;

public class TileEntityFuelLine extends TileEntityPiping {

	public int fuel = 0;
	public int oldfuel = 0;

	private Fuels fuelType = Fuels.EMPTY;

	public enum Fuels {
		EMPTY(""),
		ETHANOL("rc ethanol"),
		JETFUEL("jet fuel");

		public static final Fuels[] list = values();

		public final String fluidName;

		private Fuels(String name) {
			fluidName = name;
		}
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		//ReikaJavaLibrary.pConsole(fuel+" @ "+x);
		this.draw(world, x, y, z);
		this.transfer(world, x, y, z);
		this.transferFromFiller(world, x, y, z);
		this.drainReservoir(world, x, y, z);
		if (fuel <= 0) {
			fuel = 0;
			fuelType = Fuels.EMPTY;
		}
	}

	private void drainReservoir(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.RESERVOIR) {
			TileEntityReservoir tile = (TileEntityReservoir)world.getBlockTileEntity(x, y+1, z);
			if (tile != null && !tile.isEmpty() && this.canPullFrom(tile)) {
				if (tile.getFluid().equals(FluidRegistry.getFluid("jet fuel")))
					fuelType = Fuels.JETFUEL;
				if (tile.getFluid().equals(FluidRegistry.getFluid("rc ethanol")))
					fuelType = Fuels.ETHANOL;
				fuel += tile.getLevel()/4+1;
				tile.setLevel(tile.getLevel()-tile.getLevel()/4-1);
			}
		}
	}

	private boolean canPullFrom(TileEntityReservoir tile) {
		if (tile.isEmpty())
			return false;
		if (fuelType == Fuels.EMPTY && this.isAcceptableFuel(tile.getFluid()))
			return true;
		if (tile.getFluid().equals(FluidRegistry.getFluid(fuelType.fluidName)))
			return true;
		return false;
	}

	private boolean isAcceptableFuel(Fluid f) {
		if (f.equals(FluidRegistry.getFluid("jet fuel")))
			return true;
		if (f.equals(FluidRegistry.getFluid("rc ethanol")))
			return true;
		return false;
	}

	public boolean canTakeInFluid(Fluid f) {
		return fuelType == Fuels.EMPTY || f.equals(FluidRegistry.getFluid(fuelType.fluidName));
	}

	public boolean canIntakeFluid(String f) {
		return fuelType == Fuels.EMPTY || f.equals(fuelType.fluidName);
	}

	@Override
	public void draw(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x, y-1, z) == MachineRegistry.FRACTIONATOR) {
			TileEntityFractionator tile = (TileEntityFractionator)world.getBlockTileEntity(x, y-1, z);
			if (tile != null && this.canIntakeFluid("jet fuel")) {
				fuelType = Fuels.JETFUEL;
				fuel += tile.getFuelLevel();
				tile.setEmpty();
			}
		}
	}

	@Override
	public void transfer(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.FUELLINE) {
			TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x+1, y, z);
			this.interLine(tile);
		}
		if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.FUELLINE) {
			TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x-1, y, z);
			this.interLine(tile);
		}
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.FUELLINE) {
			TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x, y+1, z);
			this.interLine(tile);
		}
		if (MachineRegistry.getMachine(world, x, y-1, z) == MachineRegistry.FUELLINE) {
			TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x, y-1, z);
			this.interLine(tile);
		}
		if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.FUELLINE) {
			TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x, y, z+1);
			this.interLine(tile);
		}
		if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.FUELLINE) {
			TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x, y, z-1);
			this.interLine(tile);
		}
	}

	public void transferFromFiller(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.BUCKETFILLER) {
			TileEntityBucketFiller tile = (TileEntityBucketFiller)world.getBlockTileEntity(x+1, y, z);
			this.fromFiller(tile);
		}
		if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.BUCKETFILLER) {
			TileEntityBucketFiller tile = (TileEntityBucketFiller)world.getBlockTileEntity(x-1, y, z);
			this.fromFiller(tile);
		}
		if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.BUCKETFILLER) {
			TileEntityBucketFiller tile = (TileEntityBucketFiller)world.getBlockTileEntity(x, y, z+1);
			this.fromFiller(tile);
		}
		if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.BUCKETFILLER) {
			TileEntityBucketFiller tile = (TileEntityBucketFiller)world.getBlockTileEntity(x, y, z-1);
			this.fromFiller(tile);
		}
	}

	private void interLine(TileEntityFuelLine tile) {
		if (tile != null && this.canIntakeFluid(tile.fuelType.fluidName)) {
			if (tile.fuel > fuel) {
				fuelType = tile.fuelType;
				oldfuel = tile.fuel;
				tile.fuel = ReikaMathLibrary.extrema(tile.fuel-(tile.fuel-fuel)/4, 0, "max");
				fuel = ReikaMathLibrary.extrema(fuel+(oldfuel-fuel)/4, 0, "max");
			}
		}
	}

	private void fromFiller(TileEntityBucketFiller tile) {
		if (tile != null) {
			if (tile.filling)
				return;
			if (tile.getContainedFluid() != null && this.canTakeInFluid(tile.getContainedFluid())) {
				if (tile.getContainedFluid().equals(FluidRegistry.getFluid("jet fuel")))
					fuelType = Fuels.JETFUEL;
				if (tile.getContainedFluid().equals(FluidRegistry.getFluid("rc ethanol")))
					fuelType = Fuels.ETHANOL;
				oldfuel = tile.getLevel();
				tile.setEmpty();
				fuel = ReikaMathLibrary.extrema(fuel+(oldfuel-fuel), 0, "max");
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
		NBT.setInteger("fuel", fuel);

		NBT.setInteger("type", fuelType.ordinal());
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		fuel = NBT.getInteger("fuel");

		if (fuel < 0)
			fuel = 0;

		fuelType = Fuels.list[NBT.getInteger("type")];
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.FUELLINE.ordinal();
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.FUELLINE || m == MachineRegistry.VALVE || m == MachineRegistry.SEPARATION || m == MachineRegistry.BYPASS;
	}

	@Override
	public Icon getBlockIcon() {
		return Block.obsidian.getIcon(0, 0);
	}

	@Override
	public boolean hasLiquid() {
		return fuel > 0;
	}

	@Override
	public Fluid getLiquidType() {
		return this.getContainedFuel();
	}

	public Fuels getFuelType() {
		return fuelType;
	}

	public void setFuelType(Fuels f) {
		fuelType = f;
	}

	public Fluid getContainedFuel() {
		return FluidRegistry.getFluid(fuelType.fluidName);
	}
}
