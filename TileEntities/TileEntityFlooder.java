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
import net.minecraft.world.World;

import Reika.DragonAPI.BlockArray;
import Reika.DragonAPI.Libraries.ReikaChunkHelper;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.Base.TileEntityPiping;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityFlooder extends TileEntityPiping {
	//Make pick random coord in 16-block radius, find top block (solid or source block), ++y, then add liquid

	public int liquidID = -1;
	public int liquidLevel;
	public int oldLevel;

	private BlockArray blocks = new BlockArray();

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		tickcount++;
		this.draw(world, x, y, z);
		if (blocks.isEmpty() || liquidLevel == 0) {
			if (liquidID == 9) {
				blocks.recursiveFillWithBounds(world, x, y-1, z, 0, x-16, 0, z-16, x+16, y-1, z+16);
				blocks.recursiveFillWithBounds(world, x, y-1, z, Block.waterMoving.blockID, x-16, 0, z-16, x+16, y-1, z+16);
				blocks.recursiveFillWithBounds(world, x, y-1, z, Block.waterStill.blockID, x-16, 0, z-16, x+16, y-1, z+16);
			}
			if (liquidID == 11) {
				blocks.recursiveFillWithBounds(world, x, y-1, z, 0, x-16, 0, z-16, x+16, y-1, z+16);
				blocks.recursiveFillWithBounds(world, x, y-1, z, Block.lavaMoving.blockID, x-16, 0, z-16, x+16, y-1, z+16);
				blocks.recursiveFillWithBounds(world, x, y-1, z, Block.lavaStill.blockID, x-16, 0, z-16, x+16, y-1, z+16);
			}
			blocks.sortBlocksByHeight();
		}
		boolean drain = false;
		if (drain) {
			for (int i = 8; i <= 11; i++)
				ReikaChunkHelper.removeBlocksFromChunk(world, x, z, i, -1);
		}/*
		else
			if (liquidLevel > 0 && tickcount >= 20) {
				this.spill2(world, x, y, z);
				tickcount = 0;
			}*/
		else if (tickcount > 1 && liquidLevel > 0) {
			tickcount = 0;
			int[] coord = blocks.getNextAndMoveOn();
			world.setBlock(coord[0], coord[1], coord[2], liquidID);
			//ReikaJavaLibrary.pConsole(coord[0]+"   "+coord[1]+"   "+coord[2]);
			world.markBlockForUpdate(coord[0], coord[1], coord[2]);
			liquidLevel--;
		}
	}

	private void getFromPipe(TileEntityPipe tile) {
		if (tile != null) {
			if (tile.liquidLevel > liquidLevel && (tile.liquidID == liquidID || liquidID == -1) && tile.liquidLevel > 0) {
				liquidID = tile.liquidID;
				oldLevel = tile.liquidLevel;
				tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-(tile.liquidLevel-liquidLevel)/4-1, 0, "max");
				liquidLevel = ReikaMathLibrary.extrema(liquidLevel+(oldLevel-liquidLevel)/4+1, 0, "max");
			}
		}
	}

	@Override
	public void draw(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x+1, y, z);
			this.getFromPipe(tile);
		}
		if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x-1, y, z);
			this.getFromPipe(tile);
		}
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y+1, z);
			this.getFromPipe(tile);
		}
		if (MachineRegistry.getMachine(world, x, y-1, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y-1, z);
			this.getFromPipe(tile);
		}
		if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z+1);
			this.getFromPipe(tile);
		}
		if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z-1);
			this.getFromPipe(tile);
		}
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("level", liquidLevel);
		NBT.setInteger("liq", liquidID);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		liquidID = NBT.getInteger("liq");
		liquidLevel = NBT.getInteger("level");

		if (liquidLevel < 0)
		{
			liquidLevel = 0;
		}
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.SPILLER.ordinal();
	}

	@Override
	public void transfer(World world, int x, int y, int z) {}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}
}
