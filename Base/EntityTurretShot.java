/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class EntityTurretShot extends EntityFireball {

	public EntityTurretShot(World world) {
		super(world);
	}

	public EntityTurretShot(World world, double x, double y, double z, double vx, double vy, double vz) {
		super(world, x, y, z, 0, 0, 0);
		motionX = vx;
		motionY = vy;
		motionZ = vz;
		if (!world.isRemote)
			velocityChanged = true;
	}

	@Override
	public abstract void onImpact(MovingObjectPosition mov);

	@Override
	public final boolean canRenderOnFire() {
		return false;
	}

	@Override
	public final AxisAlignedBB getBoundingBox() {
		return AxisAlignedBB.getBoundingBox(posX+0.4, posY+0.4, posZ+0.4, posX+0.6, posY+0.6, posZ+0.6);
	}

	protected abstract int getAttackDamage();

	protected abstract void applyAttackEffectsToEntity(World world, EntityLivingBase el);

}
