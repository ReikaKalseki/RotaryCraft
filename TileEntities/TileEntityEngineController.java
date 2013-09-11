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
import Reika.DragonAPI.Auxiliary.EnumLook;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityEngineController extends RotaryCraftTileEntity implements PipeConnector {

	public int fuelLevel = 0;
	public static final int FUELCAP = 300;

	private EngineSettings setting = EngineSettings.FULL;

	private enum EngineSettings {
		SHUTDOWN(0, 0),
		STANDBY(16, 64),
		LOW(4, 8),
		MEDIUM(2, 2),
		FULL(1, 1);

		public final int speedFactor;
		public final int fuelFactor;

		public static final EngineSettings[] list = values();

		private EngineSettings(int speed, int fuel) {
			speedFactor = speed;
			fuelFactor = fuel;
		}
	}

	public boolean consumeFuel() {
		return setting.fuelFactor != 0;
	}

	public boolean canProducePower() {
		return setting.speedFactor != 0;
	}

	public boolean playSound() {
		return this.canProducePower();
	}

	public float getSpeedMultiplier() {
		if (this.canProducePower())
			return 1F/setting.speedFactor;
		return 0;
	}

	public int getFuelMultiplier() {
		return setting.fuelFactor;
	}

	public float getSoundStretch() {
		switch(setting) {
		case FULL:
			return 1F;
		case LOW:
			return 0.6F;
		case MEDIUM:
			return 0.8F;
		case SHUTDOWN:
			return 0F;
		case STANDBY:
			return 0.4F;
		default:
			return 1F;
		}
	}

	public void increment() {
		int l = EngineSettings.list.length;
		int o = setting.ordinal();
		o++;
		if (o >= l)
			o = 0;
		setting = EngineSettings.list[o];

	}

	public int getSettingNumber() {
		return setting.ordinal();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.drawFuel(world, x, y, z, meta);
		if (fuelLevel <= 0)
			return;
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.ENGINE)
			this.transferToEngine((TileEntityEngine)world.getBlockTileEntity(x, y+1, z));
if (world.isBlockIndirectlyGettingPowered(x, y, z))
this.setting = EngineSettings.SHUTDOWN;
	}

	private void transferToEngine(TileEntityEngine te) {
		int currentFuel = fuelLevel;
		if (!te.type.isJetFueled())
			return;
		if (te.jetfuels >= te.FUELCAP)
			return;
		te.jetfuels += currentFuel/4+1;
		fuelLevel -= currentFuel/4+1;
	}

	private void drawFuel(World world, int x, int y, int z, int metadata) {
		int oldfuel;
		if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.FUELLINE) {
			TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x+1, y, z);
			if (tile != null) {
				if (tile.fuel > fuelLevel) {
					oldfuel = tile.fuel;
					tile.fuel = ReikaMathLibrary.extrema(tile.fuel-tile.fuel, 0, "max");
					fuelLevel = ReikaMathLibrary.extrema(fuelLevel+oldfuel, 0, "max");
				}
			}
		}
		if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.FUELLINE) {
			TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x-1, y, z);
			if (tile != null) {
				if (tile.fuel > fuelLevel) {
					oldfuel = tile.fuel;
					tile.fuel = ReikaMathLibrary.extrema(tile.fuel-tile.fuel, 0, "max");
					fuelLevel = ReikaMathLibrary.extrema(fuelLevel+oldfuel, 0, "max");
				}
			}
		}
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.FUELLINE) {
			TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x, y+1, z);
			if (tile != null) {
				if (tile.fuel > fuelLevel) {
					oldfuel = tile.fuel;
					tile.fuel = ReikaMathLibrary.extrema(tile.fuel-tile.fuel, 0, "max");
					fuelLevel = ReikaMathLibrary.extrema(fuelLevel+oldfuel, 0, "max");
				}
			}
		}
		if (MachineRegistry.getMachine(world, x, y-1, z) == MachineRegistry.FUELLINE) {
			TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x, y-1, z);
			if (tile != null) {
				if (tile.fuel > fuelLevel) {
					oldfuel = tile.fuel;
					tile.fuel = ReikaMathLibrary.extrema(tile.fuel-tile.fuel, 0, "max");
					fuelLevel = ReikaMathLibrary.extrema(fuelLevel+oldfuel, 0, "max");
				}
			}
		}
		if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.FUELLINE) {
			TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x, y, z+1);
			if (tile != null) {
				if (tile.fuel > fuelLevel) {
					oldfuel = tile.fuel;
					tile.fuel = ReikaMathLibrary.extrema(tile.fuel-tile.fuel, 0, "max");
					fuelLevel = ReikaMathLibrary.extrema(fuelLevel+oldfuel, 0, "max");
				}
			}
		}
		if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.FUELLINE) {
			TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x, y, z-1);
			if (tile != null) {
				if (tile.fuel > fuelLevel) {
					oldfuel = tile.fuel;
					tile.fuel = ReikaMathLibrary.extrema(tile.fuel-tile.fuel, 0, "max");
					fuelLevel = ReikaMathLibrary.extrema(fuelLevel+oldfuel, 0, "max");
				}
			}
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("fuel", fuelLevel);
		NBT.setInteger("lvl", setting.ordinal());
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		fuelLevel = NBT.getInteger("fuel");
		setting = EngineSettings.list[NBT.getInteger("lvl")];
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.ECU.ordinal();
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.FUELLINE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, EnumLook side) {
		return true;
	}
}
