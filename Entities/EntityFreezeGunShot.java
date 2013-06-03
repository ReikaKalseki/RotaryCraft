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
package Reika.RotaryCraft.Entities;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.MachineRegistry;

public class EntityFreezeGunShot extends EntityFireball {

	private int power;
	private List frozen;

	public EntityFreezeGunShot(World world) {
		super(world);
	}

	public EntityFreezeGunShot(World world, double x, double y, double z, double vx, double vy, double vz, int pwr, List fr) {
		super(world, x, y, z, 0, 0, 0);
		this.setVelocity(vx, vy, vz);
		if (!world.isRemote)
			velocityChanged = true;
		power = pwr;
		frozen = fr;
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
		EntityLiving el;
		Entity ent;
		//ReikaChatHelper.writeCoords(world, x, y, z);
		//ReikaChatHelper.writeBlockAtCoords(world, x0, y0, z0);

		AxisAlignedBB splash = AxisAlignedBB.getBoundingBox(x, y, z, x, y, z).expand(2, 2, 2);
		//world.createExplosion(this, x, y, z, 3F, false);
		List dmgd = world.getEntitiesWithinAABB(Entity.class, splash);
		for (int l = 0; l < dmgd.size(); l++) {
			ent = (Entity)dmgd.get(l);
			if (ent instanceof EntityLiving) {
				el = (EntityLiving)ent;
				//ReikaChatHelper.writeEntity(world, el);
				if (el instanceof EntityDragon) {
					el.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 60000, 27));
					el.addPotionEffect(new PotionEffect(Potion.jump.id, 60000, -29));
				}
				else if (el instanceof EntityWither) {
					el.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 60000, 27));
					el.addPotionEffect(new PotionEffect(Potion.jump.id, 60000, -29));
				}
				else {
					el.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 60000, 7));
					el.addPotionEffect(new PotionEffect(Potion.jump.id, 60000, -9));
				}
				if (el != null) {
					if (frozen != null)
						frozen.add(el);
					EntityIceBlock ice = new EntityIceBlock(world, el);
					world.spawnEntityInWorld(ice);
				}
			}
		}

		this.setDead();
		//ent.attackEntityFrom(DamageSource.outOfWorld, el.getHealth()*(1+el.getTotalArmorValue()));
	}

	@Override
	public void onUpdate() {
		ticksExisted++;
		boolean hit = false;
		int id = worldObj.getBlockId((int)posX, (int)posY, (int)posZ);
		MachineRegistry m = MachineRegistry.getMachine(worldObj, posX, posY, posZ);
		List mobs = worldObj.getEntitiesWithinAABB(EntityLiving.class, this.getBoundingBox().expand(1, 1, 1));
		//ReikaJavaLibrary.pConsole("ID: "+id+" and "+mobs.size()+" mobs");
		hit = (mobs.size() > 0 || (m != MachineRegistry.FREEZEGUN && id != 0 && !ReikaWorldHelper.softBlocks(id)));
		//ReikaJavaLibrary.pConsole(hit+"   by "+id+"  or mobs "+mobs.size());
		if (hit) {
			//ReikaChatHelper.write("HIT  @  "+ticksExisted+"  by "+(mobs.size() > 0));
			this.onImpact(null);
			if (power < 15)
				this.setDead();
			return;
		}
		//ReikaChatHelper.write(this.ticksExisted);
		//worldObj.spawnParticle("hugeexplosion", posX, posY, posZ, 0, 0, 0);
		if (!worldObj.isRemote && (shootingEntity != null && shootingEntity.isDead || !worldObj.blockExists((int)posX, (int)posY, (int)posZ)))
			this.setDead();
		else {
			if (ticksExisted > 80 && !worldObj.isRemote)
				this.onImpact(null);
			this.onEntityUpdate();
			Vec3 var15 = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
			Vec3 var2 = worldObj.getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);
			MovingObjectPosition var3 = worldObj.rayTraceBlocks(var15, var2);
			var15 = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
			var2 = worldObj.getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);
			if (var3 != null)
				var2 = worldObj.getWorldVec3Pool().getVecFromPool(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
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
	public boolean canRenderOnFire() {
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox()
	{
		return AxisAlignedBB.getBoundingBox(posX+0.4, posY+0.4, posZ+0.4, posX+0.6, posY+0.6, posZ+0.6);
	}

}
