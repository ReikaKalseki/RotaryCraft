/*******************************************************************************
 * @author Reika
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
import Reika.DragonAPI.Libraries.ReikaChunkHelper;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.Base.TileEntityPiping;

public class TileEntityFlooder extends TileEntityPiping {
	//Make pick random coord in 16-block radius, find top block (solid or source block), ++y, then add liquid

	public int liquidID = -1;
	public int liquidLevel;
	public int oldLevel;



	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		tickcount++;
		this.draw(world, x, y, z);
		boolean drain = false;
		if (drain) {
			for (int i = 8; i <= 11; i++)
				ReikaChunkHelper.removeBlocksFromChunk(world, x, z, i, -1);
		}
		else
			if (liquidLevel > 0 && tickcount >= 20) {
				this.spill2(world, x, y, z);
				tickcount = 0;
			}
	}

	private void spill2(World world, int x, int y, int z) {
		if (liquidID == 9) {
			ReikaWorldHelper.recursiveFillWithBounds(world, x, y-1, z, Block.waterMoving.blockID, 0, -1, 0, x-16, 0, z-16, x+16, y-1, z+16);
			ReikaWorldHelper.recursiveFillWithBounds(world, x, y-1, z, Block.waterStill.blockID, 0, -1, 0, x-16, 0, z-16, x+16, y-1, z+16);
			ReikaWorldHelper.recursiveFillWithBounds(world, x, y-1, z, 0, Block.waterMoving.blockID, -1, 0, x-16, 0, z-16, x+16, y-1, z+16);
		}
		if (liquidID == 11) {
			ReikaWorldHelper.recursiveFillWithBounds(world, x, y-1, z, Block.lavaMoving.blockID, 0, -1, 0, x-16, 0, z-16, x+16, y-1, z+16);
			ReikaWorldHelper.recursiveFillWithBounds(world, x, y-1, z, Block.lavaStill.blockID, 0, -1, 0, x-16, 0, z-16, x+16, y-1, z+16);
			ReikaWorldHelper.recursiveFillWithBounds(world, x, y-1, z, 0, Block.lavaMoving.blockID, -1, 0, x-16, 0, z-16, x+16, y-1, z+16);
		}
		/*

		for (int i = 0; i < 16; i++)
			world.spawnParticle("splash", x+0.5, y+0.0625, z+0.5, 0.2*par5Random.nextFloat()-0.1, 0, 0.2*par5Random.nextFloat()-0.1);
		int y0 = y;
		int[] loc = new int[3];
		boolean full = false;
		int tries = 0;
		loc[0] = x;
		loc[2] = z;
		loc[1] = ReikaWorldHelper.findTopBlockBelowY(world, loc[0], loc[2], y-1);
		if (loc[1] < 0)
			full = true;
		if (loc[0] == x && loc[1] == y-1 && loc[2] == z)
			full = true;
		int id = MachineRegistry.getMachine(world, loc[0], loc[1], loc[2]);
		if (id != 0 && !ReikaWorldHelper.softBlocks(id))
			loc[1]++;
		else if (id > 7 && id < 12) {
			if (ReikaWorldHelper.isLiquidSourceBlock(world, loc[0], loc[1], loc[2]))
				loc[1]++;
		}
		while (full && tries <= 200) {
			tries++;
			x = x-8+par5Random.nextInt(17);
			z = z-8+par5Random.nextInt(17);
			ReikaChatHelper.write(tries);
			full = false;
			loc[0] = x;
			loc[2] = z;
			loc[1] = ReikaWorldHelper.findTopBlockBelowY(world, loc[0], loc[2], y0-1);
			if (loc[1] < 0)
				full = true;
			int id2 = MachineRegistry.getMachine(world, loc[0], loc[1], loc[2]);
			if (id2 != 0 && !ReikaWorldHelper.softBlocks(id2))
				loc[1]++;
			else if (id2 > 7 && id2 < 12) {
				if (ReikaWorldHelper.isLiquidSourceBlock(world, loc[0], loc[1], loc[2]))
					loc[1]++;
			}
		}
		int id3 = MachineRegistry.getMachine(world, loc[0], loc[1], loc[2]);
		if (id3 != 0 && (id3 < 8 || id3 > 11))
			return;
		if (loc[0] == x && loc[1] == y0-1 && loc[2] == z)
			return;
		ReikaChatHelper.writeBlockAtCoords(world, loc[0], loc[1], loc[2]);
		if (tries > 200)
			return;
		ReikaWorldHelper.legacySetBlockWithNotify(world, loc[0], loc[1]+1, loc[2], this.liquidID-1);
		world.markBlockForUpdate(loc[0], loc[1]+1, loc[2]);*/
	}

	private void spill(World world, int x, int y, int z) {
		int[] loc = new int[3];
		int tries = 0;
		boolean redo = false;
		do {
			redo = false;
			do {
				loc[0] = x-8+par5Random.nextInt(9); //x +/- 8
				loc[2] = z-8+par5Random.nextInt(9); //z +/- 8
			} while (loc[0] == x && loc[2] == z);
			loc[1] = ReikaWorldHelper.findTopBlockBelowY(world, loc[0], loc[2], y);
			int id = world.getBlockId(loc[0], loc[1], loc[2]);
			if (id != 0) {
				if (id < 8 || id > 11)
					redo = true;
				else {
					if (ReikaWorldHelper.isLiquidSourceBlock(world, loc[0], loc[1], loc[2]))
						redo = true;
				}
			}
			tries++;
		} while (tries <= 200);
		if (redo)
			loc[1]++;
		int id3 = world.getBlockId(loc[0], loc[1], loc[2]);
		if (id3 != 0 && !(id3 > 7 && id3 < 12))
			return;
		liquidLevel--;
		ReikaWorldHelper.legacySetBlockWithNotify(world, loc[0], loc[1]+1, loc[2], liquidID-1);
		world.markBlockForUpdate(loc[0], loc[1]+1, loc[2]);
	}

	@Override
	public void draw(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x+1, y, z);
			if (tile != null) {
				if (tile.liquidLevel > liquidLevel && (tile.liquidID == liquidID || liquidID == -1) && tile.liquidLevel > 0) {
					liquidID = tile.liquidID;
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-(tile.liquidLevel-liquidLevel)/4-1, 0, "max");
					liquidLevel = ReikaMathLibrary.extrema(liquidLevel+(oldLevel-liquidLevel)/4+1, 0, "max");
				}
			}
		}
		if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x-1, y, z);
			if (tile != null) {
				if (tile.liquidLevel > liquidLevel && (tile.liquidID == liquidID || liquidID == -1) && tile.liquidLevel > 0) {
					liquidID = tile.liquidID;
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-(tile.liquidLevel-liquidLevel)/4-1, 0, "max");
					liquidLevel = ReikaMathLibrary.extrema(liquidLevel+(oldLevel-liquidLevel)/4+1, 0, "max");
				}
			}
		}
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y+1, z);
			if (tile != null) {
				if (tile.liquidLevel > liquidLevel && (tile.liquidID == liquidID || liquidID == -1) && tile.liquidLevel > 0) {
					liquidID = tile.liquidID;
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-(tile.liquidLevel-liquidLevel)/4-1, 0, "max");
					liquidLevel = ReikaMathLibrary.extrema(liquidLevel+(oldLevel-liquidLevel)/4+1, 0, "max");
				}
			}
		}
		if (MachineRegistry.getMachine(world, x, y-1, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y-1, z);
			if (tile != null) {
				if (tile.liquidLevel > liquidLevel && (tile.liquidID == liquidID || liquidID == -1) && tile.liquidLevel > 0) {
					liquidID = tile.liquidID;
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-(tile.liquidLevel-liquidLevel)/4-1, 0, "max");
					liquidLevel = ReikaMathLibrary.extrema(liquidLevel+(oldLevel-liquidLevel)/4+1, 0, "max");
				}
			}
		}
		if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z+1);
			if (tile != null) {
				if (tile.liquidLevel > liquidLevel && (tile.liquidID == liquidID || liquidID == -1) && tile.liquidLevel > 0) {
					liquidID = tile.liquidID;
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-(tile.liquidLevel-liquidLevel)/4-1, 0, "max");
					liquidLevel = ReikaMathLibrary.extrema(liquidLevel+(oldLevel-liquidLevel)/4+1, 0, "max");
					//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d", this.fluidPressure, tile.fluidPressure));
				}
			}
		}
		if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z-1);
			if (tile != null) {
				if (tile.liquidLevel > liquidLevel && (tile.liquidID == liquidID || liquidID == -1) && tile.liquidLevel > 0) {
					liquidID = tile.liquidID;
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-(tile.liquidLevel-liquidLevel)/4-1, 0, "max");
					liquidLevel = ReikaMathLibrary.extrema(liquidLevel+(oldLevel-liquidLevel)/4+1, 0, "max");
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
	public void transfer(World world, int x, int y, int z) {
		this.spill2(world, x, y, z);
	}
}
