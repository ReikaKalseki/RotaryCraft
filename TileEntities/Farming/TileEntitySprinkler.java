/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Farming;

import Reika.DragonAPI.Libraries.Registry.ReikaCropHelper;
import Reika.DragonAPI.ModRegistry.ModCropList;
import Reika.RotaryCraft.Base.TileEntity.SprinklerBlock;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySprinkler extends SprinklerBlock {

	@Override
	public void performEffects(World world, int x, int y, int z) {
		RotaryAchievements.SPRINKLER.triggerAchievement(this.getPlacer());
		this.spawnParticles(world, x, y, z);
		this.hydrate(world, x, y, z);
	}

	public void hydrate(World world, int x, int y, int z) {
		int ytop = y-1;
		int range = this.getRange();
		for (int i = -range; i <= range; i++) {
			for (int j = -range; j <= range; j++) {
				if (!world.isRemote) {
					if (rand.nextInt(20) == 0) {
						boolean top = false;
						for (int k = y-1; k >= 0 && !top; k--) {
							Block foundid = world.getBlock(x+i, k, z+j);
							if (foundid == Blocks.fire) {
								world.playSoundEffect(x+i+0.5, k+0.5, z+j+0.5, "random.fizz", 0.6F+0.4F*rand.nextFloat(), 0.5F+0.5F*rand.nextFloat());
								world.setBlockToAir(x+i, k, z+j);
							}
							if (foundid != Blocks.air) {
								if (foundid.isOpaqueCube()) {
									top = true;
									ytop = -1;
								}
							}
						}
					}
					if (rand.nextInt(240) == 0) {
						boolean top = false;
						for (int k = y-1; k >= 0 && !top; k--) {
							Block foundid = world.getBlock(x+i, k, z+j);
							int meta2 = world.getBlockMetadata(x+i, k, z+j);
							if (rand.nextInt(15) == 0) {
								ReikaCropHelper crop = ReikaCropHelper.getCrop(foundid);
								ModCropList modcrop = ModCropList.getModCrop(foundid, meta2);
								if (crop != null && !crop.isRipe(meta2)) {
									world.setBlockMetadataWithNotify(x+i, k, z+j, meta2+1, 3);
								}
								if (modcrop != null && !modcrop.isRipe(world, x+i, k, z+j)) {
									//world.setBlockMetadataWithNotify(x+i, k, z+j, meta2+1, 3);
									foundid.updateTick(world, x+i, k, z+j, rand);
									world.markBlockForUpdate(x+i, k, z+j);
								}
							}
							if (foundid == Blocks.farmland) {
								top = true;
								ytop = k;
								//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", ytop));
							}
							if (foundid != Blocks.air) {
								if (foundid.isOpaqueCube()) {
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
						if (metaf < 8 && world.getBlock(x+i, ytop, z+j) == Blocks.farmland) //Wetness maxes at 8
							world.setBlockMetadataWithNotify(x+i, ytop, z+j, metaf+1, 3);
					}
				}
				if (world.getBlock(x+i, ytop-2, z+j) == Blocks.farmland) {
					int d = Math.max(1, 5-ConfigRegistry.SPRINKLER.getValue());
					if (rand.nextInt(d) == 0)
						world.spawnParticle("splash", x+i+0.5, ytop-0.875, z+j+0.5, 0, 0, 0);
				}
			}
		}
	}

	public void spawnParticles(World world, int x, int y, int z) {

		int d = Math.max(0, ConfigRegistry.SPRINKLER.getValue());
		double ypos = y+0.125;
		double vel;
		double r = this.getRange()/10D;

		double py = y-0.1875D+0.5;
		for (int i = 0; i < rand.nextInt(1+d); i++) {
			double px = x-1+2*rand.nextFloat();
			double pz = z-1+2*rand.nextFloat();
			world.spawnParticle("splash", px+0.5, py, pz+0.5, 0, 0, 0);
		}

		for (vel = 0; vel < r; vel += 0.1) {
			py = y-0.1875D+0.5;
			for (int i = 0; i < rand.nextInt(1+d*4); i++) {
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

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.SPRINKLER;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public int getCapacity() {
		return 180;
	}

	@Override
	public int getWaterConsumption() {
		return 3;
	}

	@Override
	public ForgeDirection getPipeDirection() {
		return ForgeDirection.UP;
	}
}