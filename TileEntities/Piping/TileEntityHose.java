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
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntityPiping;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityBucketFiller;
import Reika.RotaryCraft.TileEntities.TileEntityReservoir;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityGrinder;

public class TileEntityHose extends TileEntityPiping {

	public int lubricant = 0;
	public int oldlube = 0;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.draw(world, x, y, z);
		this.transfer(world, x, y, z);
		this.transferFromFiller(world, x, y, z);
		this.drainReservoir(world, x, y, z);
		if (lubricant < 0)
			lubricant = 0;
	}

	private void drainReservoir(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.RESERVOIR) {
			TileEntityReservoir tile = (TileEntityReservoir)world.getBlockTileEntity(x, y+1, z);
			if (tile != null && !tile.isEmpty() && tile.getFluid().equals(FluidRegistry.getFluid("lubricant"))) {
				lubricant += tile.getLevel()/4+1;
				tile.setLevel(tile.getLevel()-tile.getLevel()/4-1);
			}
		}
	}

	@Override
	public void draw(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.GRINDER) {
			TileEntityGrinder tile = (TileEntityGrinder)world.getBlockTileEntity(x+1, y, z);
			this.getFromGrinder(tile);
		}
		if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.GRINDER) {
			TileEntityGrinder tile = (TileEntityGrinder)world.getBlockTileEntity(x-1, y, z);
			this.getFromGrinder(tile);
		}
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.GRINDER) {
			TileEntityGrinder tile = (TileEntityGrinder)world.getBlockTileEntity(x, y+1, z);
			this.getFromGrinder(tile);
		}
		if (MachineRegistry.getMachine(world, x, y-1, z) == MachineRegistry.GRINDER) {
			TileEntityGrinder tile = (TileEntityGrinder)world.getBlockTileEntity(x, y-1, z);
			this.getFromGrinder(tile);
		}
		if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.GRINDER) {
			TileEntityGrinder tile = (TileEntityGrinder)world.getBlockTileEntity(x, y, z+1);
			this.getFromGrinder(tile);
		}
		if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.GRINDER) {
			TileEntityGrinder tile = (TileEntityGrinder)world.getBlockTileEntity(x, y, z-1);
			this.getFromGrinder(tile);
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

	private void fromFiller(TileEntityBucketFiller tile) {
		if (tile != null) {
			if (tile.filling)
				return;
			if (tile.getContainedFluid() != null && tile.getContainedFluid().equals(FluidRegistry.getFluid("lubricant")) && tile.getLevel() > lubricant) {
				oldlube = tile.getLevel();
				tile.setEmpty();
				lubricant = ReikaMathLibrary.extrema(lubricant+(oldlube-lubricant), 0, "max");
			}
		}
	}

	private void getFromGrinder(TileEntityGrinder tile) {
		if (tile != null) {
			if (tile.getLevel() > lubricant) {
				oldlube = tile.getLevel();
				tile.removeLiquid(oldlube/4);
				lubricant = ReikaMathLibrary.extrema(lubricant+oldlube/4, 0, "max");
			}
		}
	}

	@Override
	public void transfer(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.HOSE) {
			TileEntityHose tile = (TileEntityHose)world.getBlockTileEntity(x+1, y, z);
			this.interHose(tile);
		}
		if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.HOSE) {
			TileEntityHose tile = (TileEntityHose)world.getBlockTileEntity(x-1, y, z);
			this.interHose(tile);
		}
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.HOSE) {
			TileEntityHose tile = (TileEntityHose)world.getBlockTileEntity(x, y+1, z);
			this.interHose(tile);
		}
		if (MachineRegistry.getMachine(world, x, y-1, z) == MachineRegistry.HOSE) {
			TileEntityHose tile = (TileEntityHose)world.getBlockTileEntity(x, y-1, z);
			this.interHose(tile);
		}
		if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.HOSE) {
			TileEntityHose tile = (TileEntityHose)world.getBlockTileEntity(x, y, z+1);
			this.interHose(tile);
		}
		if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.HOSE) {
			TileEntityHose tile = (TileEntityHose)world.getBlockTileEntity(x, y, z-1);
			this.interHose(tile);
		}
	}

	private void interHose(TileEntityHose tile) {
		if (tile != null) {
			if (tile.lubricant > lubricant) {
				oldlube = tile.lubricant;
				tile.lubricant = ReikaMathLibrary.extrema(tile.lubricant-(tile.lubricant-lubricant)/4, 0, "max");
				lubricant = ReikaMathLibrary.extrema(lubricant+(oldlube-lubricant)/4, 0, "max");
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
		NBT.setInteger("lube", lubricant);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		lubricant = NBT.getInteger("lube");

		if (lubricant < 0)
		{
			lubricant = 0;
		}
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.HOSE.ordinal();
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.HOSE || m == MachineRegistry.VALVE || m == MachineRegistry.SEPARATION;
	}

	@Override
	public Icon getBlockIcon() {
		return Block.planks.getIcon(0, 0);
	}

	@Override
	public boolean hasLiquid() {
		return lubricant > 0;
	}

	@Override
	public Fluid getLiquidType() {
		return RotaryCraft.lubeFluid;
	}
}
