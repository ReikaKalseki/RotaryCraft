/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.ReikaRailCraftHelper;
import Reika.RotaryCraft.Auxiliary.TemperatureTE;
import Reika.RotaryCraft.Base.PoweredLiquidIO;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityPipe;

public class TileEntityBoiler extends PoweredLiquidIO implements TemperatureTE {

	private int temperature;
	private long storedEnergy;

	public static final int CAPACITY = 27000;
	public static final int MAXTEMP = 500;

	private StepTimer timer = new StepTimer(20);

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
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
	public int getMachineIndex() {
		return MachineRegistry.BOILER.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		if (output.isFull())
			return 15;
		if (input.isEmpty())
			return 15;
		return 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();

		timer.update();
		if (timer.checkCap())
			this.updateTemperature(world, x, y, z, meta);
		if (temperature > 100)
			storedEnergy += power*6000;

		this.getPipeWater(world, x, y, z);

		//ReikaJavaLibrary.pConsoleSideOnly(this.getSteam()+":"+storedEnergy+"/"+ReikaRailCraftHelper.getSteamBucketEnergy(), Side.SERVER);
		if (storedEnergy >= ReikaRailCraftHelper.getSteamBucketEnergy() && !world.isRemote)
			this.makeSteam();

		TileEntity te = world.getBlockTileEntity(x, y+1, z);
		if (te instanceof IFluidHandler) {
			IFluidHandler ic = (IFluidHandler)te;
			if (output.getFluid() != null) {
				ic.fill(ForgeDirection.DOWN, output.getFluid(), true);
				output.drain(output.getFluid().amount, true);
			}
		}
	}

	private void makeSteam() {
		if (this.getWater() >= FluidContainerRegistry.BUCKET_VOLUME && (output.isEmpty() || output.canTakeIn(1000))) {
			input.removeLiquid(FluidContainerRegistry.BUCKET_VOLUME);
			output.fill(FluidRegistry.getFluidStack("steam", FluidContainerRegistry.BUCKET_VOLUME), true);
			storedEnergy -= ReikaRailCraftHelper.getSteamBucketEnergy();
		}
	}

	public int getSteam() {
		return output.getFluid() != null ? output.getFluid().amount : 0;
	}

	public void getPipeWater(World world, int x, int y, int z) {
		int oldLevel = 0;
		if (this.getWater() < CAPACITY) {
			if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x+1, y, z);
				if (tile != null && tile.contains(FluidRegistry.WATER) && tile.liquidLevel > 0) {
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					input.addLiquid(oldLevel/4+1, FluidRegistry.WATER);
				}
			}
			if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x-1, y, z);
				if (tile != null && tile.contains(FluidRegistry.WATER) && tile.liquidLevel > 0) {
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					input.addLiquid(oldLevel/4+1, FluidRegistry.WATER);
				}
			}
			if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y+1, z);
				if (tile != null && tile.contains(FluidRegistry.WATER) && tile.liquidLevel > 0) {
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					input.addLiquid(oldLevel/4+1, FluidRegistry.WATER);
				}
			}
			if (MachineRegistry.getMachine(world, x, y-1, z) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y-1, z);
				if (tile != null && tile.contains(FluidRegistry.WATER) && tile.liquidLevel > 0) {
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					input.addLiquid(oldLevel/4+1, FluidRegistry.WATER);
				}
			}
			if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z+1);
				if (tile != null && tile.contains(FluidRegistry.WATER) && tile.liquidLevel > 0) {
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					input.addLiquid(oldLevel/4+1, FluidRegistry.WATER);
				}
			}
			if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z-1);
				if (tile != null && tile.contains(FluidRegistry.WATER) && tile.liquidLevel > 0) {
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					input.addLiquid(oldLevel/4+1, FluidRegistry.WATER);
				}
			}
		}
	}

	public int getWater() {
		return input.getLevel();
	}

	@Override
	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaWorldHelper.getBiomeTemp(world, x, z);
		if (power > 0) {
			temperature += 2.5*ReikaMathLibrary.logbase(power, 2);
		}
		if (temperature > Tamb) {
			temperature -= (temperature-Tamb)/5;
		}
		else {
			temperature += (temperature-Tamb)/5;
		}
		if (temperature - Tamb <= 4 && temperature > Tamb)
			temperature--;
		if (temperature > MAXTEMP) {
			temperature = MAXTEMP;
			this.overheat(world, x, y, z);
		}
		if (temperature > 50) {
			int side = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Block.snow.blockID);
			if (side != -1)
				ReikaWorldHelper.changeAdjBlock(world, x, y, z, side, 0, 0);
			side = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Block.ice.blockID);
			if (side != -1)
				ReikaWorldHelper.changeAdjBlock(world, x, y, z, side, Block.waterMoving.blockID, 0);
		}
	}

	@Override
	public void addTemperature(int temp) {
		temperature += temp;
	}

	@Override
	public int getTemperature() {
		return temperature;
	}

	@Override
	public int getThermalDamage() {
		return 0;
	}

	@Override
	public void overheat(World world, int x, int y, int z) {
		world.setBlock(x, y, z, 0);
		world.createExplosion(null, x+0.5, y+0.5, z+0.5, 6, true);
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("temp", temperature);
		NBT.setLong("energy", storedEnergy);

		output.writeToNBT(NBT);
		input.writeToNBT(NBT);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		temperature = NBT.getInteger("temp");
		storedEnergy = NBT.getLong("energy");

		output.readFromNBT(NBT);
		input.readFromNBT(NBT);
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public Fluid getInputFluid() {
		return FluidRegistry.WATER;
	}

	@Override
	public int getCapacity() {
		return CAPACITY;
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return from.offsetY == 0;
	}

	@Override
	public boolean canOutputTo(ForgeDirection to) {
		return to == ForgeDirection.UP;
	}

}
