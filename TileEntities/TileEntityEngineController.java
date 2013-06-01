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

import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;

public class TileEntityEngineController extends RotaryCraftTileEntity {
	public boolean enabled = true;

	public int fuelLevel = 0;
	public static final int FUELCAP = 300;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.drawFuel(world, x, y, z, meta);
		if (world.isBlockIndirectlyGettingPowered(x, y, z))
			enabled = false;
		else
			enabled = true;
		if (fuelLevel <= 0)
			return;
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.ENGINE)
			this.transferToEngine((TileEntityEngine)world.getBlockTileEntity(x, y+1, z));
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
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		fuelLevel = NBT.getInteger("fuel");
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.ECU.ordinal();
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}
}
