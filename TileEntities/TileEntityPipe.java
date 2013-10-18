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
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntityPiping;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Production.TileEntityLavaMaker;
import Reika.RotaryCraft.TileEntities.Production.TileEntityPump;

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
		this.drainReservoir(world, x, y, z);
		this.getFromBucketFiller(world, x, y, z);
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

	private void drainReservoir(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.RESERVOIR) {
			TileEntityReservoir tile = (TileEntityReservoir)world.getBlockTileEntity(x, y+1, z);
			if (tile != null && !tile.isEmpty()) {
				if (tile.getFluid().getBlockID() == liquidID || liquidID == -1) {
					liquidLevel += tile.getLevel()/4+1;
					tile.setLevel(tile.getLevel()-tile.getLevel()/4-1);
					liquidID = tile.getFluid().getBlockID();
				}
			}
		}
	}

	@Override
	public void draw(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.PUMP) {
			TileEntityPump tile = (TileEntityPump)world.getBlockTileEntity(x+1, y, z);
			this.transferFromPump(tile);
		}
		if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.PUMP) {
			TileEntityPump tile = (TileEntityPump)world.getBlockTileEntity(x-1, y, z);
			this.transferFromPump(tile);
		}
		if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.PUMP) {
			TileEntityPump tile = (TileEntityPump)world.getBlockTileEntity(x, y, z+1);
			this.transferFromPump(tile);
		}
		if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.PUMP) {
			TileEntityPump tile = (TileEntityPump)world.getBlockTileEntity(x, y, z-1);
			this.transferFromPump(tile);
		}
	}

	private void transferFromPump(TileEntityPump tile) {
		if (tile != null) {
			if (tile.getLevel() > liquidLevel && (tile.getLiquid().getBlockID() == liquidID || liquidID == -1) && tile.getLevel() > 0) {
				Fluid f = tile.getLiquid();
				if (f == null)
					return;
				liquidID = f.getBlockID();
				//oldLevel = tile.getLevel();
				//tile.setLiquid(ReikaMathLibrary.extrema(tile.getLevel()-tile.getLevel()/4-1, 0, "max"));
				//liquidLevel = tileReikaMathLibrary.extrema(liquidLevel+oldLevel/4+1, 0, "max");
				liquidLevel = tile.getLevel()-1;
				tile.setEmpty();
				tile.addLiquid(1, f);
			}
			fluidPressure = tile.liquidPressure;
		}
	}

	private void transferFromLavamaker(TileEntityLavaMaker tile) {
		if (tile != null) {
			if (tile.getLevel() <= 0)
				return;
			else if (tile.getLevel() > liquidLevel && (liquidID == 11 || liquidID == -1)) {
				liquidID = 11;
				oldLevel = tile.getLevel();
				tile.setEmpty();
				liquidLevel = ReikaMathLibrary.extrema(liquidLevel+oldLevel, 0, "max");
			}
		}
	}

	private void transferFromFiller(TileEntityBucketFiller tile) {
		if (tile != null && !tile.filling) {
			if (tile.getContainedFluid() == null)
				return;
			boolean water = tile.getContainedFluid().equals(FluidRegistry.WATER);
			boolean lava = tile.getContainedFluid().equals(FluidRegistry.LAVA);
			if (tile.getLevel() > liquidLevel && (liquidID == 9 || liquidID == -1) && water) {
				liquidID = 9;
				oldLevel = tile.getLevel();
				tile.setEmpty();
				liquidLevel = ReikaMathLibrary.extrema(liquidLevel+oldLevel, 0, "max");
			}
			else if (tile.getLevel() > liquidLevel && (liquidID == 11 || liquidID == -1) && lava) {
				liquidID = 11;
				oldLevel = tile.getLevel();
				tile.setEmpty();
				liquidLevel = ReikaMathLibrary.extrema(liquidLevel+oldLevel, 0, "max");
			}
		}
	}

	private void interPipe(TileEntityPipe tile) {
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

	public void getFromBucketFiller(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.BUCKETFILLER) {
			TileEntityBucketFiller tile = (TileEntityBucketFiller)world.getBlockTileEntity(x+1, y, z);
			this.transferFromFiller(tile);
		}
		if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.BUCKETFILLER) {
			TileEntityBucketFiller tile = (TileEntityBucketFiller)world.getBlockTileEntity(x-1, y, z);
			this.transferFromFiller(tile);
		}
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.BUCKETFILLER) {
			TileEntityBucketFiller tile = (TileEntityBucketFiller)world.getBlockTileEntity(x, y+1, z);
			this.transferFromFiller(tile);
		}
		if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.BUCKETFILLER) {
			TileEntityBucketFiller tile = (TileEntityBucketFiller)world.getBlockTileEntity(x, y, z+1);
			this.transferFromFiller(tile);
		}
		if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.BUCKETFILLER) {
			TileEntityBucketFiller tile = (TileEntityBucketFiller)world.getBlockTileEntity(x, y, z-1);
			this.transferFromFiller(tile);
		}
	}

	@Override
	public void transfer(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x+1, y, z);
			this.interPipe(tile);
		}
		if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x-1, y, z);
			this.interPipe(tile);
		}
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y+1, z);
			this.interPipe(tile);
		}
		if (MachineRegistry.getMachine(world, x, y-1, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y-1, z);
			this.interPipe(tile);
		}
		if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z+1);
			this.interPipe(tile);
		}
		if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z-1);
			this.interPipe(tile);
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

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE || m == MachineRegistry.VALVE || m == MachineRegistry.SPILLER;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return true;
	}

	@Override
	public Icon getBlockIcon() {
		return RotaryCraft.decoblock.getIcon(0, 0);
	}

	@Override
	public boolean hasLiquid() {
		return liquidID > 0 && liquidLevel > 0;
	}

	@Override
	public Fluid getLiquidType() {
		if (liquidID <= 0)
			return FluidRegistry.LAVA;
		return FluidRegistry.lookupFluidForBlock(Block.blocksList[liquidID]);
	}
}
