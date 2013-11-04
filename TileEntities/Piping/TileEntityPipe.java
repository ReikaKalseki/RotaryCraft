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

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntityPiping;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityBucketFiller;
import Reika.RotaryCraft.TileEntities.TileEntityReservoir;
import Reika.RotaryCraft.TileEntities.Production.TileEntityLavaMaker;
import Reika.RotaryCraft.TileEntities.Production.TileEntityPump;

public class TileEntityPipe extends TileEntityPiping {

	private Fluid liquid;
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
		fluidrho = this.getDensity();
		this.transfer(world, x, y, z);
		this.drainReservoir(world, x, y, z);
		this.getFromBucketFiller(world, x, y, z);
		this.drainLavaMaker(world, x, y, z);
		if (liquidLevel < 0)
			liquidLevel = 0;
		if (liquidLevel == 0)
			liquid = null;
		if (fluidPressure > 0 && tickcount > 40) {
			fluidPressure--;
			tickcount = 0;
		}
		if (fluidPressure < 0)
			fluidPressure = 0;
	}

	public int getDensity() {
		if (FluidRegistry.LAVA.equals(liquid))
			return (int)ReikaEngLibrary.rholava/100;
		if (FluidRegistry.WATER.equals(liquid))
			return (int)ReikaEngLibrary.rhowater/100;
		return liquid != null ? liquid.getDensity() : 0;
	}

	private void drainReservoir(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.RESERVOIR) {
			TileEntityReservoir tile = (TileEntityReservoir)world.getBlockTileEntity(x, y+1, z);
			if (tile != null && !tile.isEmpty()) {
				if (this.canTakeInFluid(tile.getFluid())) {
					liquidLevel += tile.getLevel()/4+1;
					tile.setLevel(tile.getLevel()-tile.getLevel()/4-1);
					liquid = tile.getFluid();
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

	public boolean canTakeInFluid(Fluid f) {
		if (f == null)
			return false;
		if (f.equals(FluidRegistry.getFluid("jet fuel")))
			return false;
		return liquid == null || liquid.equals(f);
	}

	private void transferFromPump(TileEntityPump tile) {
		if (tile != null) {
			Fluid f = tile.getLiquid();
			if (f == null)
				return;
			if (tile.getLevel() > liquidLevel && this.canTakeInFluid(f) && tile.getLevel() > 0) {
				liquid = f;
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

	private void drainLavaMaker(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			if (dir != ForgeDirection.DOWN) {
				int dx = x+dir.offsetX;
				int dy = y+dir.offsetY;
				int dz = z+dir.offsetZ;
				if (this.canTakeInFluid(FluidRegistry.LAVA)) {
					if (MachineRegistry.getMachine(world, dx, dy, dz) == MachineRegistry.LAVAMAKER) {
						TileEntityLavaMaker tile = (TileEntityLavaMaker)world.getBlockTileEntity(dx, dy, dz);
						if (tile != null && tile.getLevel() > 0) {
							oldLevel = tile.getLevel();
							liquid = FluidRegistry.LAVA;
							tile.removeLava(tile.getLevel()/4+1);
							liquidLevel += oldLevel/4+1;
						}
					}
				}
			}
		}
	}

	private void transferFromFiller(TileEntityBucketFiller tile) {
		if (tile != null && !tile.filling) {
			if (tile.getContainedFluid() == null)
				return;
			boolean water = tile.getContainedFluid().equals(FluidRegistry.WATER);
			boolean lava = tile.getContainedFluid().equals(FluidRegistry.LAVA);
			if (tile.getLevel() > liquidLevel && this.canTakeInFluid(FluidRegistry.WATER) && water) {
				liquid = FluidRegistry.WATER;
				oldLevel = tile.getLevel();
				tile.setEmpty();
				liquidLevel = ReikaMathLibrary.extrema(liquidLevel+oldLevel, 0, "max");
			}
			else if (tile.getLevel() > liquidLevel && this.canTakeInFluid(FluidRegistry.LAVA) && lava) {
				liquid = FluidRegistry.LAVA;
				oldLevel = tile.getLevel();
				tile.setEmpty();
				liquidLevel = ReikaMathLibrary.extrema(liquidLevel+oldLevel, 0, "max");
			}
		}
	}

	private void interPipe(TileEntityPipe tile) {
		if (tile != null) {
			if (tile.liquidLevel > liquidLevel && this.canTakeInFluid(tile.liquid) && tile.liquidLevel > 0) {
				liquid = tile.liquid;
				oldLevel = tile.liquidLevel;
				tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-(tile.liquidLevel-liquidLevel)/4-1, 0, "max");
				liquidLevel = ReikaMathLibrary.extrema(liquidLevel+(oldLevel-liquidLevel)/4+1, 0, "max");
			}
			if (tile.fluidPressure > fluidPressure && this.canTakeInFluid(tile.liquid) && tile.liquidLevel > 0) {
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

		NBT.setInteger("pressure", fluidPressure);

		ReikaNBTHelper.writeFluidToNBT(NBT, liquid);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		liquidLevel = NBT.getInteger("level");
		fluidPressure = NBT.getInteger("pressure");

		if (liquidLevel < 0) {
			liquidLevel = 0;
		}

		liquid = ReikaNBTHelper.getFluidFromNBT(NBT);
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
		return m == MachineRegistry.PIPE || m == MachineRegistry.VALVE || m == MachineRegistry.SPILLER || m == MachineRegistry.SEPARATION || m == MachineRegistry.BYPASS;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return this.canConnectToPipe(p);
	}

	@Override
	public Icon getBlockIcon() {
		return RotaryCraft.decoblock.getIcon(0, 0);
	}

	@Override
	public boolean hasLiquid() {
		return liquid != null && liquidLevel > 0;
	}

	@Override
	public Fluid getLiquidType() {
		return liquid;
	}

	public boolean contains(Fluid f) {
		return f.equals(liquid);
	}

	public void setFluid(Fluid f) {
		liquid = f;
	}

	@Override
	public int getLiquidLevel() {
		return liquidLevel;
	}

	@Override
	protected void removeLiquid(int amt) {
		liquidLevel -= amt;
	}
}
