/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Weaponry;

import java.util.Iterator;
import java.util.List;

import com.builtbroken.icbm.api.missile.IMissileEntity;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaVectorHelper;
import Reika.DragonAPI.ModInteract.ModExplosiveHandler;
import Reika.DragonAPI.ModRegistry.InterfaceCache;
import Reika.MeteorCraft.API.MeteorEntity;
import Reika.RotaryCraft.API.Event.ForceFieldEvent;
import Reika.RotaryCraft.Auxiliary.MachineEnchantmentHandler;
import Reika.RotaryCraft.Auxiliary.Interfaces.EnchantableMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityProtectionDome;
import Reika.RotaryCraft.Entities.EntityRailGunShot;
import Reika.RotaryCraft.Registry.MachineRegistry;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.Event.Result;

public class TileEntityForceField extends TileEntityProtectionDome implements EnchantableMachine {

	private final MachineEnchantmentHandler enchantments = new MachineEnchantmentHandler().addFilter(Enchantment.protection);

	public static final int FALLOFF = 32768;

	public double[] getBoundaryCoord(double x, double y, double z) {
		double[] xyz = new double[3];
		Vec3 vec = ReikaVectorHelper.getVec2Pt(xCoord+0.5, yCoord+0.5, zCoord+0.5, x, y, z);
		xyz[0] = vec.normalize().xCoord;
		xyz[1] = vec.normalize().yCoord;
		xyz[2] = vec.normalize().zCoord;
		for (int i = 0; i < 3; i++)
			xyz[i] *= this.getRange();
		return xyz;
	}

	@Override
	public int getRangeBoost() {
		return 8*enchantments.getEnchantment(Enchantment.protection);
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getPowerBelow();
		if (power < MINPOWER)
			return;
		this.spawnParticles(world, x, y, z);
		this.setColor(64*4/tickcount, 128+128*4/tickcount, 255);
		AxisAlignedBB field = this.getRangedBox();
		List<Entity> threats = world.getEntitiesWithinAABB(Entity.class, field);
		for (Entity e : threats) {
			this.protect(world, e);
		}
	}

	private void protect(World world, Entity threat) {
		double x = threat.posX;
		double y = threat.posY;
		double z = threat.posZ;
		if (ModList.METEORCRAFT.isLoaded()) {
			if (threat instanceof MeteorEntity) {
				((MeteorEntity)threat).destroy();
			}
		}
		if (this.isAtBorder(x, y, z) || threat instanceof EntityArrow) {
			if (threat instanceof EntityWitherSkull) {
				((EntityWitherSkull)threat).setDead();
				if (!world.isRemote)
					world.createExplosion(null, x, y, z, 1F, false);
				tickcount = 0;
			}
			if (threat instanceof EntityFireball) {
				if (((EntityFireball)threat).shootingEntity instanceof EntityGhast) {
					if (!world.isRemote)
						world.createExplosion(null, x, y, z, 2F, true);
				}
				if (((EntityFireball) threat).shootingEntity instanceof EntityBlaze) {
					for (int k = 0; k < 6+rand.nextInt(7); k++)
						world.spawnParticle("flame", x-0.2+0.4*rand.nextFloat(), y-0.2+0.4*rand.nextFloat(), z-0.2+0.4*rand.nextFloat(), 0, 0, 0);
					world.playAuxSFX(1008, (int)x, (int)y, (int)z, 1);
				}
				if (((EntityFireball)threat).shootingEntity instanceof EntityPlayer) {
					if (!world.isRemote)
						world.createExplosion(null, x, y, z, 2F, true);
				}
				threat.setDead();
				tickcount = 0;
			}
			if (threat instanceof EntityRailGunShot) {
				EntityRailGunShot e = (EntityRailGunShot)threat;
				e.onImpact(null);
				tickcount = 0;
			}
			if (threat instanceof EntityPotion) {
				if (!threat.isDead) {
					List var2 = Items.potionitem.getEffects(((EntityPotion)threat).getPotionDamage());
					if (var2 != null && !var2.isEmpty()) {
						AxisAlignedBB var3 = ((EntityPotion)threat).boundingBox.expand(4.0D, 2.0D, 4.0D);
						List<EntityPotion> var4 = ((EntityPotion)threat).worldObj.getEntitiesWithinAABB(EntityLivingBase.class, var3);
						if (var4 != null && !var4.isEmpty()) {
							Iterator var5 = var4.iterator();
							while (var5.hasNext()) {
								EntityLivingBase var6 = (EntityLivingBase)var5.next();
								double var7 = ((EntityPotion)threat).getDistanceSqToEntity(var6);
								if (var7 < 16.0D) {
									double var9 = 1.0D - Math.sqrt(var7) / 4.0D;
									Iterator var11 = var2.iterator();
									while (var11.hasNext()) {
										PotionEffect var12 = (PotionEffect)var11.next();
										int var13 = var12.getPotionID();
										if (Potion.potionTypes[var13].isInstant())
											Potion.potionTypes[var13].affectEntity(((EntityPotion)threat).getThrower(), var6, var12.getAmplifier(), var9);
										else {
											int var14 = (int)(var9 * var12.getDuration() + 0.5D);
											if (var14 > 20)
												var6.addPotionEffect(new PotionEffect(var13, var14, var12.getAmplifier()));
										}
									}
								}
							}
						}
					}
					((EntityPotion)threat).worldObj.playAuxSFX(2002, (int)Math.round(((EntityPotion)threat).posX), (int)Math.round(((EntityPotion)threat).posY), (int)Math.round(((EntityPotion)threat).posZ), ((EntityPotion)threat).getPotionDamage());
					((EntityPotion)threat).setDead();
				}
				tickcount = 0;
			}
			if (threat instanceof EntitySnowball) {
				threat.setDead();
				for (int j = 0; j < 3+rand.nextInt(3); j++)
					world.spawnParticle("snowballpoof", x-0.2+0.4*rand.nextFloat(), y-0.2+0.4*rand.nextFloat(), z-0.2+0.4*rand.nextFloat(), 0, 0, 0);
				world.playSoundEffect(x, y, z, "dig.snow", 1F, 1F);
				tickcount = 0;
			}
			if (threat instanceof EntityEgg) {
				threat.setDead();
				for (int j = 0; j < 3+rand.nextInt(3); j++)
					world.spawnParticle("snowballpoof", x-0.2+0.4*rand.nextFloat(), y-0.2+0.4*rand.nextFloat(), z-0.2+0.4*rand.nextFloat(), 0, 0, 0);
				world.playSoundEffect(x, y, z, "random.glass", 1F, 5F);
				if (!world.isRemote && rand.nextInt(8) == 0) {
					byte var2 = 1;
					if (rand.nextInt(32) == 0)
						var2 = 4;
					for (int var3 = 0; var3 < var2; ++var3) {
						EntityChicken var4 = new EntityChicken(world);
						var4.setGrowingAge(-24000);
						var4.setLocationAndAngles(x, y, z, threat.rotationYaw, 0);
						worldObj.spawnEntityInWorld(var4);
					}
				}
				tickcount = 0;
			}
			if (threat instanceof EntityPlayer) {
				if (!threat.getUniqueID().equals(placerUUID)) {
					double dx = threat.posX-xCoord-0.5;
					double dy = threat.posY-yCoord-1;
					double dz = threat.posZ-zCoord-0.5;
					double dd = ReikaMathLibrary.py3d(dx, dy, dz);
					if (dd >= this.getRange()-1.5) {
						double v = 1;
						threat.motionX = v*dx/dd;
						threat.motionY = v*dy/dd;
						threat.motionZ = v*dz/dd;
						threat.velocityChanged = true;
						tickcount = 0;
					}
				}
			}
			if (threat instanceof EntityArrow) {
				if (threat.motionX != 0 || threat.motionZ != 0)
					world.playSoundEffect(x, y, z, "random.bowhit", 1, 1);
				threat.rotationPitch = -90;
				threat.motionX = 0;
				if (threat.motionY > 0)
					threat.motionY = 0;
				threat.motionZ = 0;
				tickcount = 0;
			}
			if (threat instanceof EntityTNTPrimed) {
				threat.setDead();
				if (!world.isRemote)
					world.createExplosion(null, x, y, z, 4F, true);
				tickcount = 0;
			}
			if (ModExplosiveHandler.getInstance().isModExplosive(threat)) {
				threat.setDead();
				if (!world.isRemote)
					world.createExplosion(null, x, y, z, 4F, true);
				tickcount = 0;
			}
			if (InterfaceCache.IMISSILE.instanceOf(threat)) {
				((IMissileEntity)threat).destroyMissile(this, null, 1, false, true, true);
				if (!world.isRemote)
					world.createExplosion(null, x, y, z, 4F, true);
				tickcount = 0;
			}
			if (threat instanceof EntityMob || threat instanceof EntityGhast || threat instanceof EntitySlime) {
				int mult = 1;
				//if (this.isInside(x, y, z))
				//	mult = -1;
				double dx = x-xCoord;
				double dy = y-yCoord;
				double dz = z-zCoord;
				double dist = ReikaMathLibrary.py3d(dx, dy, dz);
				threat.motionX = mult*dx/dist/10;
				if (threat.onGround)
					threat.motionY = mult*dy/dist/10;
				threat.motionZ = mult*dz/dist/10;
				threat.rotationYaw = threat.rotationYaw - 30 + rand.nextInt(61);
				//if (!world.isRemote)
				threat.velocityChanged = true;
			}
			if (threat instanceof EntityWolf) {
				if (((EntityWolf)threat).isAngry()) {
					int mult = 1;
					if (this.isInside(x, y, z))
						mult = -1;
					double dx = x-xCoord;
					double dy = y-yCoord;
					double dz = z-zCoord;
					double dist = ReikaMathLibrary.py3d(dx, dy, dz);
					threat.motionX = mult*dx/dist/15;
					if (threat.onGround)
						threat.motionY = mult*dy/dist/15;
					threat.motionZ = mult*dz/dist/15;
					threat.rotationYaw = rand.nextInt(360);
					//if (!world.isRemote)
					threat.velocityChanged = true;
				}
			}
			Event evt = new ForceFieldEvent(this, threat);
			MinecraftForge.EVENT_BUS.post(evt);

			if (evt.getResult() == Result.ALLOW) {
				threat.setDead();
				world.createExplosion(null, x, y, z, 4F, true);
			}
		}
	}

	private boolean isAtBorder(double x, double y, double z) {
		double dx = x-xCoord-0.5;
		double dy = y-yCoord-0.5;
		double dz = z-zCoord-0.5;
		double dist = ReikaMathLibrary.py3d(dx, dy, dz);
		if (dist > this.getRange())
			return false;
		return (ReikaMathLibrary.approxr(dist, this.getRange(), 3));
	}

	private boolean isInside(double x, double y, double z) {
		double dx = x-xCoord;
		double dy = y-yCoord;
		double dz = z-zCoord;
		double dist = ReikaMathLibrary.py3d(dx, dy, dz);
		if (dist > this.getRange())
			;//return false;
		return (ReikaMathLibrary.approxr(dist, this.getRange(), 3));
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);
		NBT.setInteger("setRange", setRange);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);
		setRange = NBT.getInteger("setRange");
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		NBT.setTag("enchants", enchantments.writeToNBT());
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		enchantments.readFromNBT(NBT.getTagList("enchants", NBTTypes.COMPOUND.ID));
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.FORCEFIELD;
	}

	@Override
	public String getParticleType() {
		return "magicCrit";
	}

	@Override
	public int getFallOff() {
		return FALLOFF;
	}

	@Override
	public MachineEnchantmentHandler getEnchantmentHandler() {
		return enchantments;
	}
}
