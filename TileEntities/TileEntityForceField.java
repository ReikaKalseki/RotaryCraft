/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
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
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.ReikaVectorHelper;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Entities.EntityRailGunShot;
import Reika.RotaryCraft.Models.ModelForce;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityForceField extends TileEntityPowerReceiver implements GuiController, RangedEffect {

	public static final int FALLOFF = 32768;

	public int setRange;

	public int[] RGB = new int[3];

	public AxisAlignedBB getShield() {
		int range = this.getRange();
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1).expand(range, range, range);
	}

	public int getRange() {
		if (!this.isClear(worldObj, xCoord, yCoord, zCoord))
			return 0;
		if (setRange > this.getMaxRange())
			return this.getMaxRange();
		return setRange;
	}

	private boolean isClear(World world, int x, int y, int z) {
		for (int i = 1; i <= setRange; i++) {
			int id = world.getBlockId(x, y+i, z);
			if (id != 0 && Block.blocksList[id].getLightOpacity(world, x, y+i, z) > 0)
				return false;
		}
		return true;
	}

	public double[] getBoundaryCoord(double x, double y, double z) {
		double[] xyz = new double[3];
		Vec3 vec = ReikaVectorHelper.getVec2Pt(x, y, z, xCoord+0.5, yCoord+0.5, zCoord+0.5);
		xyz[0] = vec.normalize().xCoord;
		xyz[1] = vec.normalize().yCoord;
		xyz[2] = vec.normalize().zCoord;
		for (int i = 0; i < 3; i++)
			xyz[i] *= this.getRange();
		return xyz;
	}

	public int getMaxRange() {
		if (power < MINPOWER)
			return 0;
		int range = 2+(int)(power-MINPOWER)/FALLOFF;
		if (range > ConfigRegistry.FORCERANGE.getValue())
			return ConfigRegistry.FORCERANGE.getValue();
		return range;
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
		AxisAlignedBB field = this.getShield();
		List threats = world.getEntitiesWithinAABB(Entity.class, field);
		for (int i = 0; i < threats.size(); i++) {
			this.protect(world, threats, i);
		}
	}

	private void spawnParticles(World world, int x, int y, int z) {
		for (int i = 0; i < 4; i++) {
			world.spawnParticle("magicCrit", x+par5Random.nextDouble(), y+par5Random.nextDouble(), z+par5Random.nextDouble(), par5Random.nextDouble()-0.5, par5Random.nextDouble(), par5Random.nextDouble()-0.5);
		}
	}

	private void setColor(int r, int g, int b) {
		RGB[0] = r;
		RGB[1] = g;
		RGB[2] = b;
	}

	private void protect(World world, List threats, int i) {
		Entity threat = (Entity)threats.get(i);
		double x = threat.posX;
		double y = threat.posY;
		double z = threat.posZ;
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
					for (int k = 0; k < 6+par5Random.nextInt(7); k++)
						world.spawnParticle("flame", x-0.2+0.4*par5Random.nextFloat(), y-0.2+0.4*par5Random.nextFloat(), z-0.2+0.4*par5Random.nextFloat(), 0, 0, 0);
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
			}
			if (threat instanceof EntityPotion) {
				if (!threat.isDead) {
					List var2 = Item.potion.getEffects(((EntityPotion)threat).getPotionDamage());
					if (var2 != null && !var2.isEmpty()) {
						AxisAlignedBB var3 = ((EntityPotion)threat).boundingBox.expand(4.0D, 2.0D, 4.0D);
						List var4 = ((EntityPotion)threat).worldObj.getEntitiesWithinAABB(EntityLiving.class, var3);
						if (var4 != null && !var4.isEmpty()) {
							Iterator var5 = var4.iterator();
							while (var5.hasNext()) {
								EntityLiving var6 = (EntityLiving)var5.next();
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
				for (int j = 0; j < 3+par5Random.nextInt(3); j++)
					world.spawnParticle("snowballpoof", x-0.2+0.4*par5Random.nextFloat(), y-0.2+0.4*par5Random.nextFloat(), z-0.2+0.4*par5Random.nextFloat(), 0, 0, 0);
				world.playSoundEffect(x, y, z, "dig.snow", 1F, 1F);
				tickcount = 0;
			}
			if (threat instanceof EntityEgg) {
				threat.setDead();
				for (int j = 0; j < 3+par5Random.nextInt(3); j++)
					world.spawnParticle("snowballpoof", x-0.2+0.4*par5Random.nextFloat(), y-0.2+0.4*par5Random.nextFloat(), z-0.2+0.4*par5Random.nextFloat(), 0, 0, 0);
				world.playSoundEffect(x, y, z, "random.glass", 1F, 5F);
				if (!world.isRemote && par5Random.nextInt(8) == 0) {
					byte var2 = 1;
					if (par5Random.nextInt(32) == 0)
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

			}
			if (threat instanceof EntityArrow) {
				if (threat.motionX != 0 || threat.motionZ != 0)
					world.playSoundEffect(x, y, z, "random.bowhit", 1, 1);
				threat.rotationPitch = -90;
				threat.motionX = 0;
				if (threat.motionY > 0)
					threat.motionY = 0;
				threat.motionZ = 0;
			}
			if (threat instanceof EntityTNTPrimed) {
				threat.setDead();
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
				threat.rotationYaw = threat.rotationYaw - 30 + par5Random.nextInt(61);
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
					threat.rotationYaw = par5Random.nextInt(360);
					//if (!world.isRemote)
					threat.velocityChanged = true;
				}
			}
		}
	}

	private boolean isAtBorder(double x, double y, double z) {
		double dx = x-xCoord;
		double dy = y-yCoord;
		double dz = z-zCoord;
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
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("setRange", setRange);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		setRange = NBT.getInteger("setRange");
	}
	/*
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared()
    {
        return 65536D;
    }*/

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelForce();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.FORCEFIELD.ordinal();
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}
}
