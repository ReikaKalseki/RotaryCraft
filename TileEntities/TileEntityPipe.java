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

import Reika.DragonAPI.Libraries.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.Base.TileEntityPiping;

public class TileEntityPipe extends TileEntityPiping {

	public int liquidID = -1;
	public int liquidLevel = 0;
	public int oldLevel = 0;
	public int oldPressure = 0;
	public int fluidPressure = 0;
	public int fluidrho;



	public static final int HORIZLOSS = 1*0;	// all are 1(friction)+g (10m) * delta h (0 or 1m)
	public static final int UPLOSS = 1*0;
	public static final int DOWNLOSS = -1*0;

	public static final int UPPRESSURE = 40;
	public static final int HORIZPRESSURE = 20;
	public static final int DOWNPRESSURE = 0;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.draw(world, x, y, z);
		this.getDensity();
		this.transfer(world, x, y, z);
		if (liquidLevel < 0)
			liquidLevel = 0;
		if (liquidLevel == 0)
			liquidID = -1;
		if (fluidPressure > 0 && tickcount > 40) {
			fluidPressure--;
			tickcount = 0;
		}
		if (fluidPressure < 0)
			fluidPressure = 0;
	}

	public void getDensity() {
		if (liquidID == 10 || liquidID == 11)
			fluidrho = (int)ReikaEngLibrary.rholava/100;
		if (liquidID == 8 || liquidID == 9)
			fluidrho = (int)ReikaEngLibrary.rhowater/100;
		if (liquidID == -1)
			fluidrho = 0;
	}

	@Override
	public void draw(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.PUMP) {
			TileEntityPump tile = (TileEntityPump)world.getBlockTileEntity(x+1, y, z);
			if (tile != null) {
				if (tile.liquidLevel > liquidLevel && (tile.liquidID == liquidID || liquidID == -1) && tile.liquidLevel > 0) {
					liquidID = tile.liquidID;
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					liquidLevel = ReikaMathLibrary.extrema(liquidLevel+oldLevel/4+1, 0, "max");
				}
				fluidPressure = tile.liquidPressure;
			}
		}
		if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.PUMP) {
			TileEntityPump tile = (TileEntityPump)world.getBlockTileEntity(x-1, y, z);
			if (tile != null) {
				if (tile.liquidLevel > liquidLevel && (tile.liquidID == liquidID || liquidID == -1) && tile.liquidLevel > 0) {
					liquidID = tile.liquidID;
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					liquidLevel = ReikaMathLibrary.extrema(liquidLevel+oldLevel/4+1, 0, "max");
				}
				fluidPressure = tile.liquidPressure;
			}
		}
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.PUMP) {
			TileEntityPump tile = (TileEntityPump)world.getBlockTileEntity(x, y+1, z);
			if (tile != null) {
				if (tile.liquidLevel > liquidLevel && (tile.liquidID == liquidID || liquidID == -1) && tile.liquidLevel > 0) {
					liquidID = tile.liquidID;
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					liquidLevel = ReikaMathLibrary.extrema(liquidLevel+oldLevel/4+1, 0, "max");
				}
				fluidPressure = tile.liquidPressure;
			}
		}
		if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.PUMP) {
			TileEntityPump tile = (TileEntityPump)world.getBlockTileEntity(x, y, z+1);
			if (tile != null) {
				if (tile.liquidLevel > liquidLevel && (tile.liquidID == liquidID || liquidID == -1) && tile.liquidLevel > 0) {
					liquidID = tile.liquidID;
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					liquidLevel = ReikaMathLibrary.extrema(liquidLevel+oldLevel/4+1, 0, "max");
				}
				fluidPressure = tile.liquidPressure;
			}
		}
		if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.PUMP) {
			TileEntityPump tile = (TileEntityPump)world.getBlockTileEntity(x, y, z-1);
			if (tile != null) {
				if (tile.liquidLevel > liquidLevel && (tile.liquidID == liquidID || liquidID == -1) && tile.liquidLevel > 0) {
					liquidID = tile.liquidID;
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					liquidLevel = ReikaMathLibrary.extrema(liquidLevel+oldLevel/4+1, 0, "max");
				}
				fluidPressure = tile.liquidPressure;
			}
		}
	}

	@Override
	public void transfer(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x+1, y, z);
			if (tile != null) {
				if (tile.liquidLevel > liquidLevel && (tile.liquidID == liquidID || liquidID == -1) && tile.liquidLevel > 0) {
					liquidID = tile.liquidID;
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-(tile.liquidLevel-liquidLevel)/4-1, 0, "max");
					liquidLevel = ReikaMathLibrary.extrema(liquidLevel+(oldLevel-liquidLevel)/4+1, 0, "max");
				}
				if (tile.fluidPressure > fluidPressure && tile.liquidID == liquidID && tile.liquidLevel > 0) {
					oldPressure = tile.fluidPressure;
					tile.fluidPressure = ReikaMathLibrary.extrema(tile.fluidPressure-(tile.fluidPressure-fluidPressure)/4-1, 0, "max");
					fluidPressure = ReikaMathLibrary.extrema(fluidPressure+(oldPressure-fluidPressure)/4+1, 0, "max");
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
				if (tile.fluidPressure > fluidPressure && tile.liquidID == liquidID && tile.liquidLevel > 0) {
					oldPressure = tile.fluidPressure;
					tile.fluidPressure = ReikaMathLibrary.extrema(tile.fluidPressure-(tile.fluidPressure-fluidPressure)/4-1, 0, "max");
					fluidPressure = ReikaMathLibrary.extrema(fluidPressure+(oldPressure-fluidPressure)/4+1, 0, "max");
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
				if (tile.fluidPressure > fluidPressure && tile.liquidID == liquidID && tile.liquidLevel > 0) {
					oldPressure = tile.fluidPressure;
					tile.fluidPressure = ReikaMathLibrary.extrema(tile.fluidPressure-(tile.fluidPressure-fluidPressure)/4-1, 0, "max");
					fluidPressure = ReikaMathLibrary.extrema(fluidPressure+(oldPressure-fluidPressure)/4+1, 0, "max");
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
				if (tile.fluidPressure > fluidPressure && tile.liquidID == liquidID && tile.liquidLevel > 0) {
					oldPressure = tile.fluidPressure;
					tile.fluidPressure = ReikaMathLibrary.extrema(tile.fluidPressure-(tile.fluidPressure-fluidPressure)/4-1, 0, "max");
					fluidPressure = ReikaMathLibrary.extrema(fluidPressure+(oldPressure-fluidPressure)/4+1, 0, "max");
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
				if (tile.fluidPressure > fluidPressure && tile.liquidID == liquidID && tile.liquidLevel > 0) {
					oldPressure = tile.fluidPressure;
					tile.fluidPressure = ReikaMathLibrary.extrema(tile.fluidPressure-(tile.fluidPressure-fluidPressure)/4-1, 0, "max");
					fluidPressure = ReikaMathLibrary.extrema(fluidPressure+(oldPressure-fluidPressure)/4+1, 0, "max");
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
				if (tile.fluidPressure > fluidPressure && tile.liquidID == liquidID && tile.liquidLevel > 0) {
					oldPressure = tile.fluidPressure;
					tile.fluidPressure = ReikaMathLibrary.extrema(tile.fluidPressure-(tile.fluidPressure-fluidPressure)/4-1, 0, "max");
					fluidPressure = ReikaMathLibrary.extrema(fluidPressure+(oldPressure-fluidPressure)/4+1, 0, "max");
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
		NBT.setInteger("pressure", fluidPressure);
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
		fluidPressure = NBT.getInteger("pressure");

		if (liquidLevel < 0)
		{
			liquidLevel = 0;
		}
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.PIPE.ordinal();
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}
}
