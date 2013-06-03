/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * 
 * Distribution of the software in any form is only allowed
 * with explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Models.ModelSprinkler;

public class TileEntitySprinkler extends RotaryCraftTileEntity implements RangedEffect {

	public static final int CAPACITY = 16;

	public int waterLevel = 0;
	public int waterPressure = 0;


	private int soundtick = 40;



	public void getLiq(World world, int x, int y, int z, int metadata) {
		int oldLevel = 0;
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y+1, z);
			if (tile != null && (tile.liquidID == 8 || tile.liquidID == 9) && tile.liquidLevel > 0) {
				if (waterLevel < CAPACITY) {
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					waterLevel = ReikaMathLibrary.extrema(waterLevel+oldLevel/4+1, 0, "max");
				}
				waterPressure = tile.fluidPressure;
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
			waterLevel--;
			tickcount = 0;
		}
		this.hydrate(world, x, y, z, meta);
		if (this.getRange() > 0 && waterLevel > 0 && soundtick >= 40) {
			world.playSoundEffect(x+0.5, y+0.5, z+0.5, "Reika.RotaryCraft.sprinkler", 1F, 1F);
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
		this.spawnParticles(world, x, y, z);

		int ytop = y-1;
		int range = this.getRange();
		for (int i = -range; i <= range; i++) {
			for (int j = -range; j <= range; j++) {
				if (par5Random.nextInt(20) == 0) {
					boolean top = false;
					for (int k = y-1; k >= 0 && !top; k--) {
						int foundid = world.getBlockId(x+i, k, z+j);
						if (foundid == Block.fire.blockID) {
							//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d %d %d", ytop));
							world.playSoundEffect(x+i+0.5, k+0.5, z+j+0.5, "random.fizz", 0.6F+0.4F*par5Random.nextFloat(), 0.5F+0.5F*par5Random.nextFloat());
							ReikaWorldHelper.legacySetBlockWithNotify(world, x+i, k, z+j, 0);
						}
						if (foundid != 0) {
							if (Block.blocksList[foundid].isOpaqueCube()) {
								top = true;
								ytop = -1;
							}
						}
					}
				}
				if (par5Random.nextInt(240) == 0) {
					boolean top = false;
					for (int k = y-1; k >= 0 && !top; k--) {
						int foundid = world.getBlockId(x+i, k, z+j);
						int meta2 = world.getBlockMetadata(x+i, k, z+j);
						if (par5Random.nextInt(20) == 0) {
							if (foundid == Block.crops.blockID || foundid == Block.potato.blockID || foundid == Block.carrot.blockID || foundid == RotaryCraft.canola.blockID) {
								if (foundid != RotaryCraft.canola.blockID && meta2 < 7)
									ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x+i, k, z+j, meta2+1);
								if (foundid == RotaryCraft.canola.blockID && meta2 < 9)
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

		if (par5Random.nextInt(1) == 0) {
			double py = y-0.1875D+0.5;
			for (int i = 0; i < 4; i++) {
				double px = x-1+2*par5Random.nextFloat();
				double pz = z-1+2*par5Random.nextFloat();
				world.spawnParticle("splash", px+0.5, py, pz+0.5, 0, 0, 0);
			}

		}


		for (vel = 0; vel < r; vel += 0.1) {
			double py = y-0.1875D+0.5;
			for (int i = 0; i < 16; i++) {
				double vx = vel*(-1+par5Random.nextFloat()*2);
				vx *= 1.05;
				double vz = vel*(-1+par5Random.nextFloat()*2);
				vz *= 1.05;
				double px = x-1+2*par5Random.nextFloat();
				double pz = z-1+2*par5Random.nextFloat();
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
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelSprinkler();
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

}
