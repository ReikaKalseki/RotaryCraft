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

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.MachineRegistry;

public class EntityRailGunShot extends EntityFireball {

	private int power;

	public EntityRailGunShot(World world) {
		super(world);
	}

	public EntityRailGunShot(World world, double x, double y, double z, double vx, double vy, double vz, int pw) {
		super(world, x, y, z, 0, 0, 0);
		//this.extinguish();
		//this.shootingEntity = null;
		this.setVelocity(vx, vy, vz);
		if (!world.isRemote)
			velocityChanged = true;
		power = pw;
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

	@Override
	public void onUpdate() {
		ticksExisted++;
		boolean hit = false;
		int id = worldObj.getBlockId((int)posX, (int)posY, (int)posZ);
		MachineRegistry m = MachineRegistry.getMachine(worldObj, posX, posY, posZ);
		List mobs = worldObj.getEntitiesWithinAABB(EntityLiving.class, this.getBoundingBox().expand(1, 1, 1));
		//ReikaJavaLibrary.pConsole("ID: "+id+" and "+mobs.size()+" mobs");
		hit = (mobs.size() > 0 || (m != MachineRegistry.RAILGUN && id != 0 && !ReikaWorldHelper.softBlocks(id)));
		//ReikaJavaLibrary.pConsole(hit+"   by "+id+"  or mobs "+mobs.size());
		if (ReikaWorldHelper.softBlocks(id) && !ReikaMathLibrary.isValueInsideBoundsIncl(8, 11, id))
			ReikaWorldHelper.recursiveBreakWithinSphere(worldObj, (int)posX, (int)posY, (int)posZ, id, -1, (int)posX, (int)posY, (int)posZ, 4);
		if (hit) {
			//ReikaChatHelper.write("HIT  @  "+ticksExisted+"  by "+(mobs.size() > 0));
			this.onImpact(null);
			if (power < 15)
				this.setDead();
			return;
		}
		//ReikaChatHelper.write(this.ticksExisted);
		for (float i = -0.2F; i <= 0.2; i += 0.2F)
			worldObj.createExplosion(this, posX+i, posY+i, posZ+i, 0F, false);
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
				worldObj.createExplosion(this, posX, posY, posZ, 3F, false);
				for (int var19 = 0; var19 < 4; ++var19) {
					float var18 = 0.25F;
					worldObj.spawnParticle("bubble", posX - motionX * var18, posY - motionY * var18, posZ - motionZ * var18, motionX, motionY, motionZ);
				}
			}
			this.setPosition(posX, posY, posZ);
		}
	}

	@Override
	public void onImpact(MovingObjectPosition mov) {
		if (mov != null && MachineRegistry.getMachine(worldObj, mov.blockX, mov.blockY, mov.blockZ) == MachineRegistry.RAILGUN) {
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
		world.spawnParticle("hugeexplosion", x0, y0, z0, 0, 0, 0);
		for (int i = -3; i <= 3; i++) {
			for (int j = -3; j <= 3; j++) {
				for (int k = -3; k <= 3; k++) {
					if (i*j*k < 9 && i*j*k > -9) {
						int id = world.getBlockId(x0+i, y0+j, z0+k);
						if (ReikaWorldHelper.softBlocks(id) && !ReikaWorldHelper.isLiquidSourceBlock(worldObj, x0+i, y0+j, z0+k))
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, id, -1, x0+i, y0+j, z0+k, 5);
						if (power >= 1) {
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.leaves.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.plantRed.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.plantYellow.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.reed.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.waterlily.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.mushroomBrown.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.mushroomRed.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.sapling.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.web.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.cactus.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.flowerPot.blockID, -1, x0+i, y0+j, z0+k, 5);
						}
						if (power >= 2) {
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.glass.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.thinGlass.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.glowStone.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.mushroomCapRed.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.mushroomCapBrown.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.ladder.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.signPost.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.signWall.blockID, -1, x0+i, y0+j, z0+k, 5);
						}
						if (power >= 3) {
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.wood.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.planks.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.fence.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.cloth.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.workbench.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.doorWood.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.netherrack.blockID, -1, x0+i, y0+j, z0+k, 5);
						}
						if (power >= 4) {
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.sand.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.gravel.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.blockClay.blockID, -1, x0+i, y0+j, z0+k, 5);
							ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.slowSand.blockID, -1, x0+i, y0+j, z0+k, 5);
						}
						if (power >= 3) {
							//ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, Block.grass.blockID, -1, x0+i, y0+j, z0+k, 5);
							//ReikaWorldHelper.recursiveFillWithinSphere(world, x0+i, y0+j-7, z0+k, Block.grass.blockID, -1, Block.dirt.blockID, 0, x0+i, y0+j-7, z0+k, 5);
							if (id == Block.grass.blockID) {
								if (power >= 5) {
									Block.blocksList[id].dropBlockAsItem(world, x0+i, y0+j, z0+k, 0, 0);
									ReikaWorldHelper.legacySetBlockWithNotify(world, x0+i, y0+j, z0+k, 0);
								}
								else
									ReikaWorldHelper.legacySetBlockWithNotify(world, x0+i, y0+j, z0+k, Block.dirt.blockID);
							}
							world.markBlockForUpdate(x0+i, y0+j, z0+k);
							if (id == Block.dirt.blockID) {
								int meta = world.getBlockMetadata(x0+i, y0+j, z0+k);
								if (meta >= 3 || (meta > 3-(power-6) && power > 6)) {
									Block.blocksList[id].dropBlockAsItem(world, x0+i, y0+j, z0+k, 0, 0);
									ReikaWorldHelper.legacySetBlockWithNotify(world, x0+i, y0+j, z0+k, 0);
								}
								else
									ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x0+i, y0+j, z0+k, meta+1);
							}
						}
						if (power >= 5) {
							if (id == Block.stone.blockID) {
								int meta = world.getBlockMetadata(x0+i, y0+j, z0+k);
								if (meta >= 2 || (meta > 2-(power-8) && power > 8)) {
									if (power <= 12)
										ReikaWorldHelper.legacySetBlockWithNotify(world, x0+i, y0+j, z0+k, Block.cobblestone.blockID);
									else {
										Block.blocksList[id].dropBlockAsItem(world, x0+i, y0+j, z0+k, 0, 0);
										ReikaWorldHelper.legacySetBlockWithNotify(world, x0+i, y0+j, z0+k, 0);
									}
								}
								else
									ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x0+i, y0+j, z0+k, meta+1);
							}
							if (id == Block.cobblestone.blockID || id == Block.cobblestoneWall.blockID || id == Block.cobblestoneMossy.blockID) {
								int meta = world.getBlockMetadata(x0+i, y0+j, z0+k);
								if (meta >= 3 || (meta > 3-(power-8) && power > 8)) {
									Block.blocksList[id].dropBlockAsItem(world, x0+i, y0+j, z0+k, 0, 0);
									ReikaWorldHelper.legacySetBlockWithNotify(world, x0+i, y0+j, z0+k, 0);
								}
								else
									ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x0+i, y0+j, z0+k, meta+1);
							}
						}
						if (power == 14) {
							for (int n = 1; n < Block.blocksList.length; n++) {
								if (Block.blocksList[n] != null && n != MachineRegistry.RAILGUN.getBlockID() && ReikaMathLibrary.isValueOutsideBounds(8, 11, n)) {
									ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, n, -1, x0+i, y0+j, z0+k, 3);
								}
							}
						}
						if (power == 15) {
							for (int n = 1; n < Block.blocksList.length; n++) {
								if (Block.blocksList[n] != null && n != MachineRegistry.RAILGUN.getBlockID() && ReikaMathLibrary.isValueOutsideBounds(8, 11, n)) {
									ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, n, -1, x0+i, y0+j, z0+k, 6);
								}
							}
						}

						AxisAlignedBB splash = AxisAlignedBB.getBoundingBox(x, y, z, x, y, z).expand(3, 3, 3);
						//world.createExplosion(this, x, y, z, 3F, false);
						List dmgd = world.getEntitiesWithinAABB(Entity.class, splash);
						for (int l = 0; l < dmgd.size(); l++) {
							ent = (Entity)dmgd.get(l);
							if (ent instanceof EntityLiving) {
								el = (EntityLiving)ent;
								for (int h = 0; h < 5 && !(ent instanceof EntityPlayer); h++) {
									ItemStack held = el.getCurrentItemOrArmor(h);
									el.setCurrentItemOrArmor(h, null);
									if (!world.isRemote && held != null) {
										EntityItem ei = new EntityItem(world, x, y, z, held);
										ReikaEntityHelper.addRandomDirVelocity(ei, 0.2);
										ei.delayBeforeCanPickup = 300;
										world.spawnEntityInWorld(ei);
									}
								}
								//ReikaChatHelper.writeEntity(world, el);
								if (el instanceof EntityDragon) {
									((EntityDragon) el).attackEntityFromPart(((EntityDragon) el).dragonPartHead, DamageSource.setExplosionSource(new Explosion(worldObj, this, x, y, z, 20)), this.getAttackDamage());
								}
								else
									el.attackEntityFrom(DamageSource.generic, this.getAttackDamage());
							}
							else if (ent instanceof EntityEnderCrystal || ent instanceof EntityPainting || ent instanceof EntityItemFrame) //Will not target but will destroy
								ent.attackEntityFrom(DamageSource.generic, this.getAttackDamage());
						}
					}
					for (int m = 0; m < 2; m++) {
						world.spawnParticle("lava", x-1+2*rand.nextFloat()+i, y-1.5+rand.nextFloat()+j, z-1+2*rand.nextFloat()+k, 0, 0, 0);
					}
				}
			}
		}
		this.setDead();
		//ent.attackEntityFrom(DamageSource.outOfWorld, el.getHealth()*(1+el.getTotalArmorValue()));
	}

	private int getAttackDamage() {
		if (power == 15)
			return Integer.MAX_VALUE;
		double pow = ReikaMathLibrary.intpow(4,power)/16384D;
		int dmg = (int)(1+power+pow);
		return dmg;
	}

	public int getPower() {
		return power;
	}

}
