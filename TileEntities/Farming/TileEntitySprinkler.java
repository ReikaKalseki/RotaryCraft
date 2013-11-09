/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Farming;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaCropHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModRegistry.ModCropList;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import Reika.RotaryCraft.Registry.SoundRegistry;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityPipe;

public class TileEntitySprinkler extends RotaryCraftTileEntity implements RangedEffect, PipeConnector {

	public static final int CAPACITY = 16000;

	private int waterLevel = 0;
	private int waterPressure = 0;

	private int soundtick = 40;

	public int getWater() {
		return waterLevel;
	}

	public int getPressure() {
		return waterPressure;
	}

	public void getLiq(World world, int x, int y, int z, int metadata) {
		int oldLevel = 0;
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y+1, z);
			if (tile != null && tile.contains(FluidRegistry.WATER) && tile.getLiquidLevel() > 0) {
				if (waterLevel < CAPACITY) {
					oldLevel = tile.getLiquidLevel();
					int toremove = tile.getLiquidLevel()/4+1;
					tile.removeLiquid(toremove);
					waterLevel = ReikaMathLibrary.extrema(waterLevel+oldLevel/4+1, 0, "max");
				}
				waterPressure = tile.getPressure();
			}
		}
		if (waterLevel > CAPACITY)
			waterLevel = CAPACITY;
	}


	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		tickcount++;
		soundtick++;
		this.getLiq(world, x, y, z, meta);
		if (waterLevel <= 0)
			return;
		if (tickcount > 300) {
			waterLevel -= RotaryConfig.MILLIBUCKET;
			tickcount = 0;
		}
		this.hydrate(world, x, y, z, meta);
		if (this.getRange() > 0 && waterLevel > 0 && soundtick >= 40) {
			SoundRegistry.playSoundAtBlock(SoundRegistry.SPRINKLER, world, x, y, z, 1, 1);
			soundtick = 0;
		}
		else {
			//SoundManager.sndSystem.stop("Reika.RotaryCraft.sprinkler");
		}
	}

	public int getRange() {
		int val = 0;
		if (waterPressure <= 0)
			return 0;
		val = waterPressure/400;
		if (val > this.getMaxRange())
			val = this.getMaxRange();
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", val));
		return val;
	}

	public void hydrate(World world, int x, int y, int z, int meta) {
		if (this.getRange() > 0)
			RotaryAchievements.SPRINKLER.triggerAchievement(this.getPlacer());
		this.spawnParticles(world, x, y, z);
		int ytop = y-1;
		int range = this.getRange();
		for (int i = -range; i <= range; i++) {
			for (int j = -range; j <= range; j++) {
				if (rand.nextInt(20) == 0) {
					boolean top = false;
					for (int k = y-1; k >= 0 && !top; k--) {
						int foundid = world.getBlockId(x+i, k, z+j);
						if (foundid == Block.fire.blockID) {
							//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d %d %d", ytop));
							world.playSoundEffect(x+i+0.5, k+0.5, z+j+0.5, "random.fizz", 0.6F+0.4F*rand.nextFloat(), 0.5F+0.5F*rand.nextFloat());
							world.setBlock(x+i, k, z+j, 0);
						}
						if (foundid != 0) {
							if (Block.blocksList[foundid].isOpaqueCube()) {
								top = true;
								ytop = -1;
							}
						}
					}
				}
				if (rand.nextInt(240) == 0) {
					boolean top = false;
					for (int k = y-1; k >= 0 && !top; k--) {
						int foundid = world.getBlockId(x+i, k, z+j);
						int meta2 = world.getBlockMetadata(x+i, k, z+j);
						if (rand.nextInt(20) == 0) {
							ReikaCropHelper crop = ReikaCropHelper.getCrop(foundid);
							ModCropList modcrop = ModCropList.getModCrop(foundid, meta2);
							if (crop != null && !crop.isRipe(meta2)) {
								ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x+i, k, z+j, meta2+1);
							}
							if (modcrop != null && !modcrop.isRipe(meta2)) {
								ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x+i, k, z+j, meta2+1);
							}
						}
						if (foundid == Block.tilledField.blockID) {
							top = true;
							ytop = k;
							//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", ytop));
						}
						if (foundid != 0) {
							if (Block.blocksList[foundid].isOpaqueCube()) {
								top = true;
								ytop = -1;
							}
						}
					}
					if (ytop == -1)
						return;
					//ReikaWorldHelper.legacySetBlockWithNotify(world, x+i, 75, z+j, 20);
					//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d %d %d", x+i, ytop, z+j));
					int metaf = world.getBlockMetadata(x+i, ytop, z+j);
					if (metaf < 8 && world.getBlockId(x+i, ytop, z+j) == Block.tilledField.blockID) //Wetness maxes at 8
						ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x+i, ytop, z+j, metaf+1);
				}
				if (world.getBlockId(x+i, ytop-2, z+j) == Block.tilledField.blockID)
					world.spawnParticle("splash", x+i+0.5, ytop-0.875, z+j+0.5, 0, 0, 0);
			}
		}
	}

	public void spawnParticles(World world, int x, int y, int z) {

		double ypos = y+0.125;
		double vel;
		double r = this.getRange()/10D;

		if (rand.nextInt(1) == 0) {
			double py = y-0.1875D+0.5;
			for (int i = 0; i < 4; i++) {
				double px = x-1+2*rand.nextFloat();
				double pz = z-1+2*rand.nextFloat();
				world.spawnParticle("splash", px+0.5, py, pz+0.5, 0, 0, 0);
			}

		}


		for (vel = 0; vel < r; vel += 0.1) {
			double py = y-0.1875D+0.5;
			for (int i = 0; i < 16; i++) {
				double vx = vel*(-1+rand.nextFloat()*2);
				vx *= 1.05;
				double vz = vel*(-1+rand.nextFloat()*2);
				vz *= 1.05;
				double px = x-1+2*rand.nextFloat();
				double pz = z-1+2*rand.nextFloat();
				world.spawnParticle("splash", px+0.5, py, pz+0.5, vx, 0, vz);
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
		NBT.setInteger("level", waterLevel);
		NBT.setInteger("pressure", waterPressure);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		waterLevel = NBT.getInteger("level");
		waterPressure = NBT.getInteger("pressure");

		if (waterLevel < 0)
		{
			waterLevel = 0;
		}
		if (waterPressure < 0)
		{
			waterPressure = 0;
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.SPRINKLER.ordinal();
	}


	@Override
	public int getMaxRange() {
		return 4;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return side == ForgeDirection.DOWN;
	}

	public boolean canFill(ForgeDirection side, Fluid f) {
		return f.equals(FluidRegistry.WATER) && side == ForgeDirection.UP;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (this.canFill(from, resource.getFluid())) {
			int space = CAPACITY-waterLevel;
			int add = Math.min(space, resource.amount);
			waterLevel += add;
			return add;
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}


	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return side == ForgeDirection.UP ? Flow.INPUT : Flow.NONE;
	}
}
