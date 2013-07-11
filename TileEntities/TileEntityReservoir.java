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
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Models.ModelReservoir;
import Reika.RotaryCraft.Registry.EnumLook;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityReservoir extends RotaryCraftTileEntity implements PipeConnector {

	public int liquidID = -1;
	public int liquidLevel;

	public static final int CAPACITY = 2400;
	public static final int FUELID = -120;

	public int getLiquidScaled(int par1)
	{
		return (liquidLevel*par1)/CAPACITY;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.getLiq(world, x, y, z, meta);
		if (liquidLevel < 0)
			liquidLevel = 0;
		if (liquidLevel == 0)
			liquidID = -1;
	}

	public void getLiq(World world, int x, int y, int z, int metadata) {
		int oldLevel = 0;
		if (liquidLevel < CAPACITY) {
			if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x+1, y, z);
				if (tile != null && (tile.liquidID == liquidID || liquidID == -1) && tile.liquidLevel > 0) {
					liquidID = tile.liquidID;
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					liquidLevel = ReikaMathLibrary.extrema(liquidLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x-1, y, z);
				if (tile != null && (tile.liquidID == liquidID || liquidID == -1) && tile.liquidLevel > 0) {
					liquidID = tile.liquidID;
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					liquidLevel = ReikaMathLibrary.extrema(liquidLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x, y-1, z) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y-1, z);
				if (tile != null && (tile.liquidID == liquidID || liquidID == -1) && tile.liquidLevel > 0) {
					liquidID = tile.liquidID;
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					liquidLevel = ReikaMathLibrary.extrema(liquidLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z+1);
				if (tile != null && (tile.liquidID == liquidID || liquidID == -1) && tile.liquidLevel > 0) {
					liquidID = tile.liquidID;
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					liquidLevel = ReikaMathLibrary.extrema(liquidLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z-1);
				if (tile != null && (tile.liquidID == liquidID || liquidID == -1) && tile.liquidLevel > 0) {
					liquidID = tile.liquidID;
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					liquidLevel = ReikaMathLibrary.extrema(liquidLevel+oldLevel/4+1, 0, "max");
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
		NBT.setInteger("liquidLevel", liquidLevel);
		NBT.setInteger("liquid", liquidID);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		liquidLevel = NBT.getInteger("liquidLevel");
		liquidID = NBT.getInteger("liquid");
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
		return 15*liquidLevel/CAPACITY;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, EnumLook side) {
		return side != EnumLook.DOWN;
	}
}
