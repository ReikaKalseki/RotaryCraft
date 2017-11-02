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
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Instantiable.Effects.EntityFluidFX;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Base.EntityTurretShot;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Weaponry.Turret.TileEntityMultiCannon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityGatlingShot extends EntityTurretShot {

	public EntityGatlingShot(World world) {
		super(world);
	}

	public EntityGatlingShot(World world, double x, double y, double z, double vx, double vy, double vz, TileEntityMultiCannon r) {
		super(world, x, y, z, 0, 0, 0, r);
		motionX = vx;
		motionY = vy;
		motionZ = vz;
		if (!world.isRemote)
			velocityChanged = true;
		gun = r;
	}

	@Override
	public void onUpdate() {
		if (gun == null) {
			this.setDead();
			return;
		}

		if (worldObj.isRemote && this.getEntityId()%10 == 0) { //tracer round
			this.spawnTracerParticles();
		}

		ticksExisted++;
		boolean hit = false;
		World world = worldObj;
		int x = MathHelper.floor_double(posX);
		int y = MathHelper.floor_double(posY);
		int z = MathHelper.floor_double(posZ);
		Block id = worldObj.getBlock(x, y, z);

		int r = 1;
		for (int i = -r; i <= r; i++) {
			for (int j = -r; j <= r; j++) {
				for (int k = -r; k <= r; k++) {
					Block id2 = world.getBlock(x+i, y+j, z+k);
					//ReikaJavaLibrary.pConsole(ConfigRegistry.BLOCKDAMAGE.getState()+" with "+power+" on "+FMLCommonHandler.instance().getEffectiveSide());
					if (ConfigRegistry.ATTACKBLOCKS.getState()) {
						if (id2.getMaterial() == Material.glass || id2.getMaterial() == Material.ice) {
							ReikaSoundHelper.playBreakSound(world, x+i, y+j, z+k, Blocks.glass);
							world.setBlock(x+i, y+j, z+k, Blocks.air);
						}
					}
				}
			}
		}

		MachineRegistry m = MachineRegistry.getMachine(worldObj, posX, posY, posZ);
		List mobs = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.getBoundingBox().expand(1, 1, 1));
		//ReikaJavaLibrary.pConsole("ID: "+id+" and "+mobs.size()+" mobs");
		hit = (mobs.size() > 0 || (m != MachineRegistry.GATLING && id != Blocks.air && !ReikaWorldHelper.softBlocks(worldObj, x, y, z)));
		//ReikaJavaLibrary.pConsole(hit+"   by "+id+"  or mobs "+mobs.size());
		if (hit) {
			//ReikaChatHelper.write("HIT  @  "+ticksExisted+"  by "+(mobs.size() > 0));
			this.onImpact(null);
			this.setDead();
			return;
		}
		//ReikaChatHelper.write(this.ticksExisted);
		if (!worldObj.isRemote && (shootingEntity != null && shootingEntity.isDead || !worldObj.blockExists((int)posX, (int)posY, (int)posZ)))
			this.setDead();
		else {
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
				for (int var19 = 0; var19 < 4; ++var19) {
					float var18 = 0.25F;
					ReikaParticleHelper.BUBBLE.spawnAt(worldObj, posX - motionX * var18, posY - motionY * var18, posZ - motionZ * var18, motionX, motionY, motionZ);
				}
			}
			this.setPosition(posX, posY, posZ);
		}
	}

	@SideOnly(Side.CLIENT)
	private void spawnTracerParticles() {
		double l = 0.25;
		for (double d = -l; d <= l; d += 0.0625) {
			double px = posX+motionX*d;
			double py = posY+motionY*d;
			double pz = posZ+motionZ*d;
			float s = 3*(float)(l-Math.abs(d)/2D);
			EntityFX fx = new EntityFluidFX(worldObj, px, py, pz, FluidRegistry.LAVA).setLife(2)/*.setColor(0xffd0a0)*/.setScale(s);
			Minecraft.getMinecraft().effectRenderer.addEffect(fx);
		}
	}

	@Override
	public void onImpact(MovingObjectPosition mov) {
		if (mov != null && MachineRegistry.getMachine(worldObj, mov.blockX, mov.blockY, mov.blockZ) == MachineRegistry.GATLING) {
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
		EntityLivingBase el;
		Entity ent;
		int r = 0;
		for (int i = -r; i <= r; i++) {
			for (int j = -r; j <= r; j++) {
				for (int k = -r; k <= r; k++) {
					Block id = world.getBlock(x0+i, y0+j, z0+k);
					//ReikaJavaLibrary.pConsole(ConfigRegistry.BLOCKDAMAGE.getState()+" with "+power+" on "+FMLCommonHandler.instance().getEffectiveSide());
					if (ConfigRegistry.ATTACKBLOCKS.getState()) {
						if (id.getMaterial() == Material.glass || id.getMaterial() == Material.ice) {
							ReikaSoundHelper.playBreakSound(world, x0+i, y0+j, z0+k, Blocks.glass);
							world.setBlock(x0+i, y0+j, z0+k, Blocks.air);
						}
					}

					if (world.isRemote && id.getMaterial() != Material.air && MachineRegistry.getMachine(worldObj, x0+i, y0+j, z0+k) != MachineRegistry.GATLING)
						ReikaRenderHelper.spawnDropParticles(world, x0+i, y0+j, z0+k, id, world.getBlockMetadata(x0+i, y0+j, z0+k));
				}
			}
		}

		double d = 1.5;
		AxisAlignedBB splash = AxisAlignedBB.getBoundingBox(x, y, z, x, y, z).expand(d, d, d);
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

		this.setDead();
		//ent.attackEntityFrom(DamageSource.outOfWorld, el.getHealth()*(1+el.getTotalArmorValue()));
	}

	@Override
	public int getAttackDamage() {
		return 1;
	}

	@Override
	protected void applyAttackEffectsToEntity(World world, Entity ent) {
		if (ent instanceof EntitySlime) {
			int in = ent.getEntityData().getInteger(TileEntityMultiCannon.SLIME_NBT);
			ent.getEntityData().setInteger(TileEntityMultiCannon.SLIME_NBT, in+1);
			ReikaEntityHelper.knockbackEntityFromPos(gun.xCoord+0.5, gun.yCoord+0.5, gun.zCoord+0.5, ent, 1);
			ent.attackEntityFrom(this.getDamageSource(), this.getAttackDamage()/8F);
			ent.hurtResistantTime = 0;
		}
		else {
			ent.attackEntityFrom(this.getDamageSource(), this.getAttackDamage());
			ReikaEntityHelper.knockbackEntityFromPos(gun.xCoord+0.5, gun.yCoord+0.5, gun.zCoord+0.5, ent, 0.0625);
			ent.hurtResistantTime = 0;
		}
	}

}
