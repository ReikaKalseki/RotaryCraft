/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
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
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.API.Event.RailgunImpactEvent;
import Reika.RotaryCraft.Base.EntityTurretShot;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityRailGun;

public class EntityRailGunShot extends EntityTurretShot {

	private int power;
	private TileEntityRailGun gun;

	public EntityRailGunShot(World world) {
		super(world);
	}

	public EntityRailGunShot(World world, double x, double y, double z, double vx, double vy, double vz, int pw, TileEntityRailGun r) {
		super(world, x, y, z, 0, 0, 0);
		motionX = vx;
		motionY = vy;
		motionZ = vz;
		if (!world.isRemote)
			velocityChanged = true;
		power = pw;
		gun = r;
	}

	@Override
	public void onUpdate() {
		ticksExisted++;
		boolean hit = false;
		int id = worldObj.getBlockId((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ));
		MachineRegistry m = MachineRegistry.getMachine(worldObj, posX, posY, posZ);
		List mobs = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.getBoundingBox().expand(1, 1, 1));
		//ReikaJavaLibrary.pConsole("ID: "+id+" and "+mobs.size()+" mobs");
		hit = (mobs.size() > 0 || (m != MachineRegistry.RAILGUN && id != 0 && !ReikaWorldHelper.softBlocks(worldObj, (int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ))));
		//ReikaJavaLibrary.pConsole(hit+"   by "+id+"  or mobs "+mobs.size());
		if (ReikaWorldHelper.softBlocks(id) && !ReikaMathLibrary.isValueInsideBoundsIncl(8, 11, id) && ConfigRegistry.ATTACKBLOCKS.getState())
			ReikaWorldHelper.recursiveBreakWithinSphere(worldObj, (int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ), id, -1, (int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ), 4);
		if (hit) {
			//ReikaChatHelper.write("HIT  @  "+ticksExisted+"  by "+(mobs.size() > 0));
			this.onImpact(null);
			if (power < 15 || true)
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
			MovingObjectPosition var3 = worldObj.clip(var15, var2);
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
					ReikaParticleHelper.BUBBLE.spawnAt(worldObj, posX - motionX * var18, posY - motionY * var18, posZ - motionZ * var18, motionX, motionY, motionZ);
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
		int x0 = (int)Math.floor(x);
		int y0 = (int)Math.floor(y);
		int z0 = (int)Math.floor(z);
		MinecraftForge.EVENT_BUS.post(new RailgunImpactEvent(world, x0, y0, z0, this.getPower()));
		EntityLivingBase el;
		Entity ent;
		ReikaParticleHelper.EXPLODE.spawnAt(world, x0, y0, z0);
		for (int i = -3; i <= 3; i++) {
			for (int j = -3; j <= 3; j++) {
				for (int k = -3; k <= 3; k++) {
					if (i*j*k < 9 && i*j*k > -9) {
						//ReikaJavaLibrary.pConsole(ConfigRegistry.BLOCKDAMAGE.getState()+" with "+power+" on "+FMLCommonHandler.instance().getEffectiveSide());
						if (ConfigRegistry.ATTACKBLOCKS.getState() && ConfigRegistry.RAILGUNDAMAGE.getState()) {
							int id = world.getBlockId(x0+i, y0+j, z0+k);
							if (ReikaWorldHelper.softBlocks(world, x0+i, y0+j, z0+k) && !ReikaWorldHelper.isLiquidSourceBlock(worldObj, x0+i, y0+j, z0+k))
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
										int meta = world.getBlockMetadata(x0+i, y0+j, z0+k);
										ReikaItemHelper.dropItems(world, x0+i, y0+j, z0+k, Block.blocksList[id].getBlockDropped(world, x0+i, y0+j, z0+k, meta, 0));
										world.setBlock(x0+i, y0+j, z0+k, 0);
									}
									else
										world.setBlock(x0+i, y0+j, z0+k, Block.dirt.blockID);
								}
								world.markBlockForUpdate(x0+i, y0+j, z0+k);
								if (id == Block.dirt.blockID) {
									int meta = world.getBlockMetadata(x0+i, y0+j, z0+k);
									if (meta >= 3 || (meta > 3-(power-6) && power > 6)) {
										ReikaItemHelper.dropItems(world, x0+i, y0+j, z0+k, Block.blocksList[id].getBlockDropped(world, x0+i, y0+j, z0+k, meta, 0));
										world.setBlock(x0+i, y0+j, z0+k, 0);
									}
									else
										world.setBlockMetadataWithNotify(x0+i, y0+j, z0+k, meta+1, 3);
								}
							}
							if (power >= 5) {
								if (id == Block.stone.blockID) {
									int meta = world.getBlockMetadata(x0+i, y0+j, z0+k);
									if (meta >= 2 || (meta > 2-(power-8) && power > 8)) {
										if (power <= 12)
											world.setBlock(x0+i, y0+j, z0+k, Block.cobblestone.blockID);
										else {
											ReikaItemHelper.dropItems(world, x0+i, y0+j, z0+k, Block.blocksList[id].getBlockDropped(world, x0+i, y0+j, z0+k, meta, 0));
											//Block.blocksList[id].dropBlockAsItem(world, x0+i, y0+j, z0+k, meta, 0);
											world.setBlock(x0+i, y0+j, z0+k, 0);
										}
									}
									else
										world.setBlockMetadataWithNotify(x0+i, y0+j, z0+k, meta+1, 3);
								}
								if (id == Block.cobblestone.blockID || id == Block.cobblestoneWall.blockID || id == Block.cobblestoneMossy.blockID) {
									int meta = world.getBlockMetadata(x0+i, y0+j, z0+k);
									if (meta >= 3 || (meta > 3-(power-8) && power > 8)) {
										ReikaItemHelper.dropItems(world, x0+i, y0+j, z0+k, Block.blocksList[id].getBlockDropped(world, x0+i, y0+j, z0+k, meta, 0));
										world.setBlock(x0+i, y0+j, z0+k, 0);
									}
									else
										world.setBlockMetadataWithNotify(x0+i, y0+j, z0+k, meta+1, 3);
								}
							}
							if (power == 14) {
								int n = id;
								if (Block.blocksList[n] != null && n != MachineRegistry.RAILGUN.getBlockID() && ReikaMathLibrary.isValueOutsideBounds(7, 11, n)) {
									ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, n, -1, x0+i, y0+j, z0+k, 3);
								}
							}
							if (power == 15) {
								int n = id;
								if (Block.blocksList[n] != null && n != MachineRegistry.RAILGUN.getBlockID() && ReikaMathLibrary.isValueOutsideBounds(7, 11, n)) {
									ReikaWorldHelper.recursiveBreakWithinSphere(world, x0+i, y0+j, z0+k, n, -1, x0+i, y0+j, z0+k, 6);
								}
							}
						}

						AxisAlignedBB splash = AxisAlignedBB.getBoundingBox(x, y, z, x, y, z).expand(6, 6, 6);
						//world.createExplosion(this, x, y, z, 3F, false);
						List dmgd = world.getEntitiesWithinAABB(Entity.class, splash);
						for (int l = 0; l < dmgd.size(); l++) {
							ent = (Entity)dmgd.get(l);
							if (ent instanceof EntityLivingBase) {
								el = (EntityLivingBase)ent;
								this.applyAttackEffectsToEntity(world, el);
							}
							else if (ent instanceof EntityEnderCrystal || ent instanceof EntityPainting || ent instanceof EntityItemFrame) //Will not target but will destroy
								ent.attackEntityFrom(DamageSource.generic, this.getAttackDamage());
						}
					}
					for (int m = 0; m < 2; m++) {
						ReikaParticleHelper.LAVA.spawnAt(world, x-1+2*rand.nextFloat()+i, y-1.5+rand.nextFloat()+j, z-1+2*rand.nextFloat()+k);
					}

				}
			}
		}
		this.setDead();
		//ent.attackEntityFrom(DamageSource.outOfWorld, el.getHealth()*(1+el.getTotalArmorValue()));
	}

	@Override
	public int getAttackDamage() {
		if (power == 15)
			return Integer.MAX_VALUE;
		double pow = ReikaMathLibrary.intpow(4,power)/16384D;
		int dmg = (int)(1+power+pow);
		return dmg;
	}

	public int getPower() {
		return power;
	}

	@Override
	protected void applyAttackEffectsToEntity(World world, EntityLivingBase el) {
		double x = el.posX;
		double y = el.posY;
		double z = el.posZ;
		el.clearActivePotions();
		for (int h = 0; h < 5 && !(el instanceof EntityPlayer); h++) {
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
			if (el.isDead || el.getHealth() <= 0) {
				RotaryAchievements.RAILDRAGON.triggerAchievement(gun.getPlacer());
			}
		}
		else
			el.attackEntityFrom(DamageSource.generic, this.getAttackDamage());
		if (el instanceof EntityPlayer) {
			if (el.isDead || el.getHealth() <= 0)
				RotaryAchievements.RAILKILLED.triggerAchievement((EntityPlayer) el);
		}
		el.motionX = motionX*power/15F;
		el.motionY = motionY*power/15F;
		el.motionZ = motionZ*power/15F;
	}

}
