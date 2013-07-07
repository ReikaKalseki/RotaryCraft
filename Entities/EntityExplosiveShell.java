/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import Reika.RotaryCraft.Base.EntityTurretShot;

public class EntityExplosiveShell extends EntityTurretShot {

	public EntityExplosiveShell(World world) {
		super(world);
	}

	@Override
	public void onImpact(MovingObjectPosition mov) {

	}

	@Override
	protected int getAttackDamage() {
		return 0;
	}

	@Override
	protected void applyAttackEffectsToEntity(World world, EntityLiving el) {

	}

}
