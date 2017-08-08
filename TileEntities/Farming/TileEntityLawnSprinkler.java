/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Farming;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Instantiable.Data.WeightedRandom;
import Reika.DragonAPI.Instantiable.Event.BlockTickEvent;
import Reika.DragonAPI.Instantiable.Event.BlockTickEvent.UpdateFlags;
import Reika.DragonAPI.Interfaces.Registry.ModCrop;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaCropHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaPlantHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModRegistry.ModCropList;
import Reika.ReactorCraft.Entities.EntityRadiation;
import Reika.RotaryCraft.Auxiliary.Interfaces.Cleanable;
import Reika.RotaryCraft.Base.TileEntity.SprinklerBlock;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntityLawnSprinkler extends SprinklerBlock {

	private static int[] GROWTH_PATTERN = {8, 5, 3, 1, 1}; //tick distribution

	private static WeightedRandom<Integer> radiusRandom = new WeightedRandom();

	static {
		for (int i = 0; i < GROWTH_PATTERN.length-1; i++) {
			radiusRandom.addEntry(i+1, GROWTH_PATTERN[i]);
		}
		radiusRandom.addEntry(-1, GROWTH_PATTERN[GROWTH_PATTERN.length-1]);
	}

	private int speed;

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	protected void doAnimations() {
		if (worldObj.isRemote) {
			if (this.canPerformEffects()) {
				if (speed < 24)
					speed += 1;
			}
			else {
				if (speed > 0)
					speed--;
			}
		}
		phi += speed;
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.LAWNSPRINKLER;
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
	public void performEffects(World world, int x, int y, int z) {
		RotaryAchievements.SPRINKLER.triggerAchievement(this.getPlacer());
		if (!world.isRemote) {
			for (int k = 0; k < 3; k++) {
				this.accelerateGrowth(world, x, y, z);
				this.extinguishFire(world, x, y, z);
			}
			if (this.getPressure() > 3000)
				this.washMachines(world, x, y, z);
			if (ModList.REACTORCRAFT.isLoaded() && rand.nextInt(2400) == 0)
				this.clearRadiation(world, x, y, z);
			if (this.getPressure() > 300000)
				this.damageMobs(world, x, y, z);
		}
		this.spreadWater(world, x, y, z);
	}

	@ModDependent(ModList.REACTORCRAFT)
	private void clearRadiation(World world, int x, int y, int z) {
		int r = this.getRange();
		AxisAlignedBB box = ReikaAABBHelper.getBlockAABB(x, y, z).offset(0, 2, 0).expand(r, 2, r);
		List<EntityRadiation> li = world.getEntitiesWithinAABB(EntityRadiation.class, box);
		for (EntityRadiation e : li) {
			e.clean();
			if (rand.nextBoolean())
				break;
		}
	}

	private void washMachines(World world, int x, int y, int z) {
		int r = this.getRange();
		int n = 3;
		for (int c = 0; c < n; c++) {
			int rx = ReikaRandomHelper.getRandomPlusMinus(x, r);
			int rz = ReikaRandomHelper.getRandomPlusMinus(z, r);
			for (int i = y; i > y-4; i--) {
				Block id = world.getBlock(rx, i, rz);
				int meta = world.getBlockMetadata(rx, i, rz);
				MachineRegistry m = MachineRegistry.getMachineFromIDandMetadata(id, meta);
				if (m != null) {
					TileEntity te = world.getTileEntity(rx, i, rz);
					if (te instanceof Cleanable) {
						((Cleanable)te).clean();
					}
				}
				else if (id != Blocks.air && id.isOpaqueCube())
					i = -999;
			}
		}
	}

	private void extinguishFire(World world, int x, int y, int z) {
		int r = this.getRange();
		int rx = ReikaRandomHelper.getRandomPlusMinus(x, r);
		int rz = ReikaRandomHelper.getRandomPlusMinus(z, r);
		for (int i = y; i > y-4; i--) {
			Block id = world.getBlock(rx, i, rz);
			if (id == Blocks.fire) {
				Block id2 = world.getBlock(rx, i-1, rz);
				if (id2 != Blocks.netherrack) {
					world.setBlockToAir(rx, i, rz);
					ReikaSoundHelper.playSoundAtBlock(world, rx, i, rz, "random.fizz");
				}
			}
			else if (id != Blocks.air && id.isOpaqueCube())
				i = -999;
		}
	}

	private void accelerateGrowth(World world, int x, int y, int z) {
		int r = this.calcRange();
		int rx = ReikaRandomHelper.getRandomPlusMinus(x, r);
		int rz = ReikaRandomHelper.getRandomPlusMinus(z, r);
		for (int i = y; i > y-4; i--) {
			Block id = world.getBlock(rx, i, rz);
			int meta = world.getBlockMetadata(rx, i, rz);
			if (id == Blocks.farmland) {
				ReikaWorldHelper.hydrateFarmland(world, rx, i, rz, false);
				i = -999;
			}
			else if (id != Blocks.air && id.isOpaqueCube()) {
				i = -999;
			}
			else if (rand.nextInt(8) == 0) {
				ReikaCropHelper crop = ReikaCropHelper.getCrop(id);
				ModCrop modcrop = ModCropList.getModCrop(id, meta);
				if (crop != null && !crop.isRipe(meta)) {
					world.setBlockMetadataWithNotify(rx, i, rz, meta+1, 3);
				}
				else if (modcrop != null && !modcrop.isRipe(world, rx, i, rz)) {
					//world.setBlockMetadataWithNotify(rx, i, rz, meta+1, 3);
					id.updateTick(world, rx, i, rz, rand);
					BlockTickEvent.fire(world, rx, i, rz, id, UpdateFlags.FORCED.flag);
					world.markBlockForUpdate(rx, i, rz);
				}
				else if (this.shouldTick(world, x, y, z, id)) {
					id.updateTick(world, rx, i, rz, rand);
				}
			}
		}
	}

	private boolean shouldTick(World world, int x, int y, int z, Block id) {
		ReikaPlantHelper p = ReikaPlantHelper.getPlant(id);
		if (p != null && p.grows())
			return true;
		return false;
	}

	private int calcRange() {
		int r = radiusRandom.getRandomEntry();
		if (r == -1)
			r = this.getMaxRange();
		return r;
	}

	private void damageMobs(World world, int x, int y, int z) {
		int r = this.getRange();
		AxisAlignedBB box = ReikaAABBHelper.getBlockAABB(x, y, z).offset(0, 1, 0).expand(r, 1, r);
		List<EntityLivingBase> li = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		for (EntityLivingBase e : li) {
			e.attackEntityFrom(DamageSource.generic, 0.5F);
		}
	}

	private void spreadWater(World world, int x, int y, int z) {
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
		for (int i = 0; i < 3; i++)
			this.createWaterStream(world, x, y, z, i*120+60);
	}

	private void createWaterStream(World world, int x, int y, int z, float offset) {
		int d = Math.max(0, ConfigRegistry.SPRINKLER.getValue());
		int r = this.getRange();
		double dx = 0.6*Math.sin(Math.toRadians(phi+offset));
		double dz = 0.6*Math.cos(Math.toRadians(phi+offset));
		for (int i = 0; i < 6*d; i++) {
			double v = rand.nextInt((1+r)*10)/72D;
			world.spawnParticle("splash", x+0.5+dx, y+0.75, z+0.5+dz, dx*v-0.025+0.05*rand.nextDouble(), 0, dz*v-0.025+0.05*rand.nextDouble());
		}
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		//NBT.setInteger("spd", speed);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		//speed = NBT.getInteger("speed");
	}

	@Override
	public int getCapacity() {
		return 5;
	}

	@Override
	public int getWaterConsumption() {
		return 1;
	}

	@Override
	public ForgeDirection getPipeDirection() {
		return ForgeDirection.DOWN;
	}

}
