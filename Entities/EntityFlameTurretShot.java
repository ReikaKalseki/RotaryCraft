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
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Base.EntityTurretShot;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Weaponry.Turret.TileEntityFlameTurret;

public class EntityFlameTurretShot extends EntityTurretShot {

	private boolean jetfuel;

	public EntityFlameTurretShot(World world) {
		super(world);
	}

	public EntityFlameTurretShot(World world, double x, double y, double z, double vx, double vy, double vz, TileEntityFlameTurret r, boolean jet) {
		super(world, x, y, z, 0, 0, 0, r);
		motionX = vx;
		motionY = vy;
		motionZ = vz;
		if (!world.isRemote)
			velocityChanged = true;
		gun = r;
		jetfuel = jet;
	}

	@Override
	public void onUpdate() {
		if (gun == null) {
			this.setDead();
			return;
		}

		ticksExisted++;
		motionY -= 0.005;
		velocityChanged = true;

		boolean hit = false;
		World world = worldObj;
		int x = MathHelper.floor_double(posX);
		int y = MathHelper.floor_double(posY);
		int z = MathHelper.floor_double(posZ);
		Block id = worldObj.getBlock(x, y, z);

		MachineRegistry m = MachineRegistry.getMachine(worldObj, posX, posY, posZ);
		//ReikaJavaLibrary.pConsole("ID: "+id+" and "+mobs.size()+" mobs");
		hit = ticksExisted > 5 && m != MachineRegistry.FLAMETURRET && id != Blocks.air && !ReikaWorldHelper.softBlocks(worldObj, x, y, z);
		//ReikaJavaLibrary.pConsole(hit+"   by "+id+"  or mobs "+mobs.size());
		if (hit) {
			//ReikaChatHelper.write("HIT  @  "+ticksExisted+"  by "+(mobs.size() > 0));
			this.onImpact(null);
			this.setDead();
			return;
		}
		//ReikaChatHelper.write(this.ticksExisted);
		if (!worldObj.isRemote && (ticksExisted > 240 || shootingEntity != null && shootingEntity.isDead || !worldObj.blockExists((int)posX, (int)posY, (int)posZ))) {
			this.setDead();
		}
		else {
			this.onEntityUpdate();
			Vec3 var15 = Vec3.createVectorHelper(posX, posY, posZ);
			Vec3 var2 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
			MovingObjectPosition var3 = worldObj.rayTraceBlocks(var15, var2);
			if (var3 != null && var3.typeOfHit == MovingObjectType.BLOCK && MachineRegistry.getMachine(world, var3.blockX, var3.blockY, var3.blockZ) != MachineRegistry.FLAMETURRET)
				this.onImpact(var3);
			posX += motionX;
			posY += motionY;
			posZ += motionZ;
			if (this.isInWater()) {
				if (rand.nextInt(6) == 0)
					ReikaSoundHelper.playSoundAtEntity(world, this, "random.fizz", 0.4F, rand.nextFloat()*0.5F+0.5F);
				this.setDead();
			}
			this.setPosition(posX, posY, posZ);
		}
	}

	@Override
	public void onImpact(MovingObjectPosition mov) {
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
		if (world.getBlock(x0, y0, z0) == Blocks.air)
			world.setBlock(x0, y0, z0, Blocks.fire);
		ReikaWorldHelper.ignite(world, x0, y0, z0);

		double d = 1.5;
		AxisAlignedBB splash = AxisAlignedBB.getBoundingBox(x, y, z, x, y, z).expand(d, d, d);
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
		return jetfuel ? 8 : 4;
	}

	@Override
	protected void applyAttackEffectsToEntity(World world, Entity ent) {
		ent.attackEntityFrom(this.getDamageSource().setFireDamage(), this.getAttackDamage());
		ent.setFire(4);
	}

	@Override
	public boolean shouldRenderInPass(int pass) {
		return pass == 1;
	}

}
