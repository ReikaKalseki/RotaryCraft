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

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaCropHelper;
import Reika.DragonAPI.ModRegistry.ModCropList;
import Reika.RotaryCraft.Base.TileEntity.SprinklerBlock;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntityLawnSprinkler extends SprinklerBlock {

	private int speed;

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (this.canPerformEffects()) {
			if (speed < 24)
				speed += 1;
		}
		else {
			if (speed > 0)
				speed--;
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
			if (this.getPressure() > 300000)
				this.damageMobs(world, x, y, z);
		}
		this.spreadWater(world, x, y, z);
	}

	private void extinguishFire(World world, int x, int y, int z) {
		int r = this.getRange();
		int rx = ReikaRandomHelper.getRandomPlusMinus(x, r);
		int rz = ReikaRandomHelper.getRandomPlusMinus(z, r);
		for (int i = y; i > y-4; i--) {
			int id = world.getBlockId(rx, i, rz);
			if (id == Block.fire.blockID) {
				world.setBlock(rx, i, rz, 0);
				ReikaSoundHelper.playSoundAtBlock(world, rx, i, rz, "random.fizz");
			}
			else if (id != 0 && Block.blocksList[id].isOpaqueCube())
				i = -999;
		}
	}

	private void accelerateGrowth(World world, int x, int y, int z) {
		int r = this.getRange();
		int rx = ReikaRandomHelper.getRandomPlusMinus(x, r);
		int rz = ReikaRandomHelper.getRandomPlusMinus(z, r);
		for (int i = y; i > y-4; i--) {
			int id = world.getBlockId(rx, i, rz);
			int meta = world.getBlockMetadata(rx, i, rz);
			if (id == Block.tilledField.blockID) {
				if (meta < 8)
					world.setBlockMetadataWithNotify(rx, i, rz, meta+1, 3);
				i = -999;
			}
			else if (id != 0 && Block.blocksList[id].isOpaqueCube())
				i = -999;
			else if (rand.nextInt(15) == 0) {
				ReikaCropHelper crop = ReikaCropHelper.getCrop(id);
				ModCropList modcrop = ModCropList.getModCrop(id, meta);
				if (crop != null && !crop.isRipe(meta)) {
					world.setBlockMetadataWithNotify(rx, i, rz, meta+1, 3);
				}
				if (modcrop != null && !modcrop.isRipe(world, rx, i, rz)) {
					//world.setBlockMetadataWithNotify(rx, i, rz, meta+1, 3);
					Block b = Block.blocksList[id];
					b.updateTick(world, rx, i, rz, rand);
					world.markBlockForUpdate(rx, i, rz);
				}
			}
		}
	}

	private void damageMobs(World world, int x, int y, int z) {
		int r = this.getRange();
		AxisAlignedBB box = ReikaAABBHelper.getBlockAABB(x, y, z).offset(0, 1, 0).expand(r, 1, r);
		List<EntityLivingBase> li = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		for (int i = 0; i < li.size(); i++) {
			EntityLivingBase e = li.get(i);
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
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		NBT.setInteger("spd", speed);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		speed = NBT.getInteger("speed");
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
