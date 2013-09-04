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

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Auxiliary.EnumLook;
import Reika.DragonAPI.Instantiable.BlockArray;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.MultiBlockMachine;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.SimpleProvider;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityIOMachine;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntitySolar extends TileEntityIOMachine implements MultiBlockMachine, SimpleProvider, PipeConnector, PowerGenerator {

	private BlockArray solarBlocks = new BlockArray();
	private int numberMirrors = 0;

	private int waterLevel = 0;
	private float lightMultiplier = 0;

	public static final int GENOMEGA = 1024;

	@Override
	public boolean canProvidePower() {
		//return this.isMultiBlock(worldObj, xCoord, yCoord, zCoord) && this.getMultiBlockPosition(worldObj, xCoord, yCoord, zCoord)[1] == 0;
		return true;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.SOLARTOWER.ordinal();
	}

	public void getLiq(World world, int x, int y, int z, int metadata) {
		int oldLevel = 0;
		if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x+1, y, z);
			if (tile != null && (tile.liquidID == 8 || tile.liquidID == 9) && tile.liquidLevel > 0) {
				oldLevel = tile.liquidLevel;
				tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
				waterLevel = ReikaMathLibrary.extrema(waterLevel+oldLevel/4+1, 0, "max");
			}
		}
		if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x-1, y, z);
			if (tile != null && (tile.liquidID == 8 || tile.liquidID == 9) && tile.liquidLevel > 0) {
				oldLevel = tile.liquidLevel;
				tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
				waterLevel = ReikaMathLibrary.extrema(waterLevel+oldLevel/4+1, 0, "max");
			}
		}
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y+1, z);
			if (tile != null && (tile.liquidID == 8 || tile.liquidID == 9) && tile.liquidLevel > 0) {
				oldLevel = tile.liquidLevel;
				tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
				waterLevel = ReikaMathLibrary.extrema(waterLevel+oldLevel/4+1, 0, "max");
			}
		}
		if (MachineRegistry.getMachine(world, x, y-1, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y-1, z);
			if (tile != null && (tile.liquidID == 8 || tile.liquidID == 9) && tile.liquidLevel > 0) {
				oldLevel = tile.liquidLevel;
				tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
				waterLevel = ReikaMathLibrary.extrema(waterLevel+oldLevel/4+1, 0, "max");
			}
		}
		if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z+1);
			if (tile != null && (tile.liquidID == 8 || tile.liquidID == 9) && tile.liquidLevel > 0) {
				oldLevel = tile.liquidLevel;
				tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
				waterLevel = ReikaMathLibrary.extrema(waterLevel+oldLevel/4+1, 0, "max");
			}
		}
		if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z-1);
			if (tile != null && (tile.liquidID == 8 || tile.liquidID == 9) && tile.liquidLevel > 0) {
				oldLevel = tile.liquidLevel;
				tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
				waterLevel = ReikaMathLibrary.extrema(waterLevel+oldLevel/4+1, 0, "max");
			}
		}
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getLiq(world, x, y, z, meta);
		int temp = (int)(15*this.getArraySize()*this.getArrayOverallBrightness());
		for (int i = -3; i <= 3; i++) {
			for (int j = -3; j <= 3; j++) {
				if (ConfigRegistry.BLOCKDAMAGE.getState())
					ReikaWorldHelper.temperatureEnvironment(world, x+i, y, z+j, temp);
				AxisAlignedBB above = AxisAlignedBB.getAABBPool().getAABB(x+i, y+1, z+j, x+i+1, y+2, z+j+1);
				List in = world.getEntitiesWithinAABB(EntityLiving.class, above);
				for (int k = 0; k < in.size(); k++) {
					EntityLiving e = (EntityLiving)in.get(k);
					if (temp > 400)
						e.setFire(3);
				}
			}
		}
		if (world.getBlockId(x, y-1, z) == 0 || MachineRegistry.getMachine(world, x, y-1, z) != this.getMachine()) {
			//ReikaJavaLibrary.pConsole("TOWER: "+this.getTowerHeight()+";  SIZE: "+this.getArraySize());
			this.generatePower(world, x, y, z);
		}
		else {
			writex = writey = writez = Integer.MIN_VALUE;
		}
		if (world.getBlockId(x, y+1, z) != 0)
			return;
		if (solarBlocks.isEmpty()) {
			lightMultiplier = 0;
			solarBlocks.recursiveAdd(world, x, y, z, this.getTileEntityBlockID());
			numberMirrors = solarBlocks.getSize();
			while (solarBlocks.getSize() > 0) {
				int[] xyz = solarBlocks.getNextAndMoveOn();
				MachineRegistry m = MachineRegistry.getMachine(world, xyz[0], xyz[1], xyz[2]);
				if (m == MachineRegistry.MIRROR) {
					TileEntityMirror te = (TileEntityMirror)world.getBlockTileEntity(xyz[0], xyz[1], xyz[2]);
					te.targetloc = new int[]{x,y,z};
					float light = te.getLightLevel();
					lightMultiplier += light;
				}
				else numberMirrors--;
			}
			lightMultiplier /= 15F;
			lightMultiplier /= numberMirrors;
		}

		if (writex != Integer.MIN_VALUE && writey != Integer.MIN_VALUE && writez != Integer.MIN_VALUE) {
			this.basicPowerReceiver();
		}
	}

	private void generatePower(World world, int x, int y, int z) {
		this.getTowerWater(world, x, y, z);
		writex = x;
		writez = z;
		writey = y-1;
		//omega = 1*ReikaMathLibrary.extrema(ReikaMathLibrary.ceil2exp(this.getTowerHeight()), 8, "min")*(this.getArraySize()+1);
		omega = GENOMEGA;
		torque = (int)(2*this.getArrayOverallBrightness()*ReikaMathLibrary.extrema(ReikaMathLibrary.ceil2exp(this.getTowerHeight()), 64, "min")*(this.getArraySize()+1));
		if (this.getArraySize() <= 0 || torque == 0 || waterLevel <= 0) {
			omega = 0;
			torque = 0;
		}
		power = omega*torque;
		if (par5Random.nextInt(20) == 0)
			if (waterLevel > 0)
				waterLevel -= RotaryConfig.MILLIBUCKET;
	}

	public int getTowerHeight() {
		return this.getTopOfTower()-yCoord;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public boolean isMultiBlock(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public int[] getMultiBlockPosition(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public int[] getMultiBlockSize(World world, int x, int y, int z) {
		return null;
	}

	public int getArraySize() {
		TileEntity tile = worldObj.getBlockTileEntity(xCoord, this.getTopOfTower(), zCoord);
		if (tile == null)
			return 0;
		return ((TileEntitySolar)tile).numberMirrors;
	}

	public float getArrayOverallBrightness() {
		TileEntity tile = worldObj.getBlockTileEntity(xCoord, this.getTopOfTower(), zCoord);
		if (tile == null)
			return 0;
		return ((TileEntitySolar)tile).lightMultiplier;
	}

	public int getTopOfTower() {
		int y = yCoord;
		while (MachineRegistry.getMachine(worldObj, xCoord, y, zCoord) == MachineRegistry.SOLARTOWER) {
			y++;
		}
		return y-1;
	}

	public void getTowerWater(World world, int x, int y, int z) {
		int water = waterLevel;
		int cy = y+1;
		while (MachineRegistry.getMachine(world, x, cy, z) == MachineRegistry.SOLARTOWER) {
			TileEntitySolar tile = (TileEntitySolar)(world.getBlockTileEntity(x, cy, z));
			water += tile.waterLevel;
			tile.waterLevel = 0;
			cy++;
		}
		waterLevel = water;
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("water", waterLevel);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		waterLevel = NBT.getInteger("water");
	}

	public int getBottomOfTower() {
		int y = yCoord;
		while (MachineRegistry.getMachine(worldObj, xCoord, y, zCoord) == MachineRegistry.SOLARTOWER) {
			y--;
		}
		return y+1;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, EnumLook side) {
		return true;
	}

	@Override
	public void onEMP() {}

	@Override
	public PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		return new PowerSourceList().addSource(this);
	}

	public long getMaxPower() {
		return torque*omega;
	}

	public long getCurrentPower() {
		return power;
	}

}
