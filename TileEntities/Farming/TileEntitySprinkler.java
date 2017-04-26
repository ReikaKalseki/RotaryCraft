/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Farming;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Auxiliary.ModularLogger;
import Reika.DragonAPI.Instantiable.Event.BlockTickEvent;
import Reika.DragonAPI.Instantiable.Event.BlockTickEvent.UpdateFlags;
import Reika.DragonAPI.Interfaces.Registry.CropType;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaCropHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaPlantHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModRegistry.ModCropList;
import Reika.ReactorCraft.Entities.EntityRadiation;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntity.SprinklerBlock;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntitySprinkler extends SprinklerBlock {

	private static final String LOGGER_ID = "sprinkler_apple";

	static {
		ModularLogger.instance.addLogger(RotaryCraft.instance, LOGGER_ID);
	}

	@Override
	public void performEffects(World world, int x, int y, int z) {
		RotaryAchievements.SPRINKLER.triggerAchievement(this.getPlacer());
		this.spawnParticles(world, x, y, z);
		this.hydrate(world, x, y, z);
		if (ModList.REACTORCRAFT.isLoaded() && rand.nextInt(2400) == 0)
			this.clearRadiation(world, x, y, z);
	}

	@ModDependent(ModList.REACTORCRAFT)
	private void clearRadiation(World world, int x, int y, int z) {
		int r = this.getRange();
		AxisAlignedBB box = ReikaAABBHelper.getBlockAABB(x, y, z).offset(0, -4, 0).expand(r, 4, r);
		List<EntityRadiation> li = world.getEntitiesWithinAABB(EntityRadiation.class, box);
		for (EntityRadiation e : li) {
			e.clean();
			if (rand.nextBoolean())
				break;
		}
	}

	public void hydrate(World world, int x, int y, int z) {
		int range = this.getRange();
		for (int i = -range; i <= range; i++) {
			for (int k = -range; k <= range; k++) {
				int dx = x+i;
				int dz = z+k;
				int dy = y;
				boolean flag = false;
				boolean drip = false;
				float f = 0.5F+1.5F*(i*i+k*k)/(range*range); //2x rate at center, 0.5x rate at edge
				while (dy > 0 && y-dy < 12 && !flag) {
					Block b = world.getBlock(dx, dy, dz);
					if (!b.isAir(world, dx, dy, dz)) {
						boolean flag2 = false;
						if (b == Blocks.fire) {
							if (!world.isRemote && Effects.FIREEXTINGUISH.doChance(rand, f)) {
								world.playSoundEffect(dx, dy, dz, "random.fizz", 0.6F+0.4F*rand.nextFloat(), 0.5F+0.5F*rand.nextFloat());
								world.setBlockToAir(dx, dy, dz);
							}
							flag2 = true;
						}
						else if (b == Blocks.farmland) {
							if (!world.isRemote && Effects.HYDRATEFARMLAND.doChance(rand, f)) {
								ReikaWorldHelper.hydrateFarmland(world, dx, dy, dz, false);
							}
							flag2 = true;
						}
						if (!flag2 && !world.isRemote && Effects.CROPTICK.doChance(rand, f)) {
							CropType crop = ReikaCropHelper.getCrop(b);
							if (crop == null)
								crop = ModCropList.getModCrop(b, world.getBlockMetadata(dx, dy, dz));
							if (crop != null) {
								b.updateTick(world, dx, dy, dz, rand);
								BlockTickEvent.fire(world, dx, dy, dz, b, UpdateFlags.FORCED.flag);
								//flag2 = true; continue downwards to hydrate farmland
							}
							else {
								ReikaPlantHelper p = ReikaPlantHelper.getPlant(b);
								if (p != null && p.grows()) {
									b.updateTick(world, dx, dy, dz, rand);
									BlockTickEvent.fire(world, dx, dy, dz, b, UpdateFlags.FORCED.flag);
									flag2 = p == ReikaPlantHelper.CACTUS || p == ReikaPlantHelper.SUGARCANE;
								}
							}
						}
						if (!flag2 && Effects.APPLETICK.doChance(rand, f)) {
							String n = b.getClass().getName().toLowerCase(Locale.ENGLISH);
							if (n.startsWith("growthcraft.apples")) {
								if (n.endsWith("leaves")) {
									if (ModularLogger.instance.isEnabled(LOGGER_ID))
										ModularLogger.instance.log(LOGGER_ID, "Found leaf @ "+dx+", "+dy+", "+dz);
									Block b2 = world.getBlock(dx, dy-1, dz);
									if (this.isOpaque(b2)) {
										flag2 = true;
									}
									else if (b2.isAir(world, dx, dy-1, dz)) { //space for an apple; only stop moving down if this
										if (!world.isRemote) {
											b.updateTick(world, dx, dy, dz, rand);
											BlockTickEvent.fire(world, dx, dy, dz, b, UpdateFlags.FORCED.flag);
											if (ModularLogger.instance.isEnabled(LOGGER_ID))
												ModularLogger.instance.log(LOGGER_ID, "Ticked apple leaf @ "+dx+", "+dy+", "+dz);
										}
										flag2 = true;
									}
									drip = b2 != b && !this.isOpaque(b2);
								}
								else if (n.endsWith("apple") && !world.isRemote) {
									b.updateTick(world, dx, dy, dz, rand);
									BlockTickEvent.fire(world, dx, dy, dz, b, UpdateFlags.FORCED.flag);
									flag2 = true;
									if (ModularLogger.instance.isEnabled(LOGGER_ID))
										ModularLogger.instance.log(LOGGER_ID, "Ticked apple block @ "+dx+", "+dy+", "+dz);
								}
								if (ModularLogger.instance.isEnabled(LOGGER_ID))
									ModularLogger.instance.log(LOGGER_ID, "Read GC block '"+n+"', flag2="+flag2);
							}
						}
						flag = this.isOpaque(b) || flag2;
					}
					if (!flag)
						dy--;
				}
				if (world.isRemote) {
					int d = Math.max(1, 5-ConfigRegistry.SPRINKLER.getValue());
					if (rand.nextInt(d) == 0)
						world.spawnParticle("splash", dx+rand.nextDouble(), dy+1, dz+rand.nextDouble(), 0, 0, 0);
					if (drip) {
						world.spawnParticle("dripWater", dx+rand.nextDouble(), dy, dz+rand.nextDouble(), 0, 0, 0);
					}
				}
			}
		}
	}

	private boolean isOpaque(Block b) {
		return b.isOpaqueCube() && !(b instanceof BlockLeavesBase);
	}

	public void spawnParticles(World world, int x, int y, int z) {

		int d = Math.max(0, ConfigRegistry.SPRINKLER.getValue());
		double ypos = y+0.125;
		double vel;
		double r = this.getRange()/10D;

		double py = y-0.1875D+0.5;
		int n = (rand.nextInt(2) == 0 ? 1 : 0)+rand.nextInt(1+d);
		for (int i = 0; i < n; i++) {
			double px = x-1+2*rand.nextFloat();
			double pz = z-1+2*rand.nextFloat();
			world.spawnParticle("splash", px+0.5, py, pz+0.5, 0, 0, 0);
		}

		for (vel = 0; vel < r; vel += 0.1) {
			py = y-0.1875D+0.5;
			n = (rand.nextInt(2) == 0 ? 1 : 0)+rand.nextInt(1+d*4);
			for (int i = 0; i < n; i++) {
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

	protected static enum Effects {
		FIREEXTINGUISH(20),
		CROPTICK(80),
		HYDRATEFARMLAND(15),
		APPLETICK(40);

		private final int randomChance;

		private Effects(int c) {
			randomChance = c;
		}

		/** Factor < 1 -> higher rate */
		protected boolean doChance(Random rand, float factor) {
			return rand.nextInt(Math.max(1, (int)(randomChance*factor))) == 0;
		}
	}
}
