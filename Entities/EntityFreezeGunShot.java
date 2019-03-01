/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Entities;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.API.Interfaces.TargetEntity;
import Reika.RotaryCraft.Base.EntityTurretShot;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Weaponry.Turret.TileEntityFreezeGun;

public class EntityFreezeGunShot extends EntityTurretShot {

	public EntityFreezeGunShot(World world) {
		super(world);
	}

	public EntityFreezeGunShot(World world, double x, double y, double z, double vx, double vy, double vz, TileEntityFreezeGun te) {
		super(world, x, y, z, 0, 0, 0, te);
		motionX = vx;
		motionY = vy;
		motionZ = vz;
		if (!world.isRemote)
			velocityChanged = true;
	}

	@Override
	public void onImpact(MovingObjectPosition mov) {
		if (mov != null && MachineRegistry.getMachine(worldObj, mov.blockX, mov.blockY, mov.blockZ) == MachineRegistry.FREEZEGUN) {
			this.setDead();
			return;
		}
		if (isDead)
			return;
		World world = worldObj;
		double x = posX;
		double y = posY;
		double z = posZ;
		int x0 = (int)x;
		int y0 = (int)y;
		int z0 = (int)z;
		EntityLivingBase el;
		Entity ent;

		AxisAlignedBB splash = AxisAlignedBB.getBoundingBox(x, y, z, x, y, z).expand(2, 2, 2);
		List dmgd = world.getEntitiesWithinAABB(Entity.class, splash);
		for (int l = 0; l < dmgd.size(); l++) {
			ent = (Entity)dmgd.get(l);
			this.applyAttackEffectsToEntity(world, ent);
		}

		this.setDead();
	}

	@Override
	protected void applyAttackEffectsToEntity(World world, Entity el) {
		if (el instanceof EntityPlayer && ((EntityPlayer)el).capabilities.isCreativeMode)
			return;
		if (el instanceof TargetEntity) {
			((TargetEntity)el).onFreeze(gun);
		}
		if (el instanceof EntityLivingBase)
			((EntityLivingBase)el).addPotionEffect(TileEntityFreezeGun.getFreezeEffect(60000));
	}

	@Override
	public void onUpdate() {
		ticksExisted++;
		boolean hit = false;
		int x = MathHelper.floor_double(posX);
		int y = MathHelper.floor_double(posY);
		int z = MathHelper.floor_double(posZ);
		Block id = worldObj.getBlock(x, y, z);
		MachineRegistry m = MachineRegistry.getMachine(worldObj, posX, posY, posZ);
		List mobs = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.getBoundingBox().expand(1, 1, 1));
		hit = (mobs.size() > 0 || (m != MachineRegistry.FREEZEGUN && id != Blocks.air && !ReikaWorldHelper.softBlocks(worldObj, x, y, z)));
		if (hit) {
			this.onImpact(null);
			return;
		}
		if (!worldObj.isRemote && (shootingEntity != null && shootingEntity.isDead || !worldObj.blockExists(x, y, z)))
			this.setDead();
		else {
			if (ticksExisted > 80 && !worldObj.isRemote)
				this.onImpact(null);
			this.onEntityUpdate();
			Vec3 var15 = Vec3.createVectorHelper(posX, posY, posZ);
			Vec3 var2 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
			MovingObjectPosition var3 = worldObj.rayTraceBlocks(var15, var2);
			var15 = Vec3.createVectorHelper(posX, posY, posZ);
			var2 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
			if (var3 != null)
				var2 = Vec3.createVectorHelper(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
			Entity var4 = null;
			List var5 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;
			for (int var8 = 0; var8 < var5.size(); ++var8) {
				Entity var9 = (Entity)var5.get(var8);
				if (var9.canBeCollidedWith() && (!var9.isEntityEqual(shootingEntity))) {
					float var10 = 0.3F;
					AxisAlignedBB var11 = var9.boundingBox.expand(var10, var10, var10);
					MovingObjectPosition var12 = var11.calculateIntercept(var15, var2);
					if (var12 != null) {
						double var13 = var15.distanceTo(var12.hitVec);
						if (var13 < var6 || var6 == 0.0D) {
							var4 = var9;
							var6 = var13;
						}
					}
				}
			}
			if (var4 != null)
				var3 = new MovingObjectPosition(var4);
			if (var3 != null)
				this.onImpact(var3);
			posX += motionX;
			posY += motionY;
			posZ += motionZ;
			if (this.isInWater()) {
				this.setDead();
			}
			this.setPosition(posX, posY, posZ);
		}
	}

	@Override
	public int getAttackDamage() {
		return 0;
	}
}
