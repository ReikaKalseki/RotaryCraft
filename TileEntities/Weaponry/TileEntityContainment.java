/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Weaponry;

import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Base.TileEntity.TileEntityProtectionDome;
import Reika.RotaryCraft.Registry.MachineRegistry;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class TileEntityContainment extends TileEntityProtectionDome {

	public static final int DRAGONPOWER = 2097152;
	public static final int WITHERPOWER = 524288;

	public static final int FALLOFF = 8192;

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.CONTAINMENT;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.setColor(120, 0, 150);
		this.getPowerBelow();
		if (power < MINPOWER)
			return;
		this.spawnParticles(world, x, y, z);
		List inbox = world.getEntitiesWithinAABB(EntityLivingBase.class, this.getRangedBox());
		for (int i = 0; i < inbox.size(); i++) {
			EntityLivingBase e = (EntityLivingBase)inbox.get(i);
			if (ReikaEntityHelper.isHostile(e) && !(e instanceof EntityDragon || e instanceof EntityWither)) {
				this.markNoDespawn(e);
				double dx = e.posX-x-0.5;
				double dy = e.posY-y-0.5;
				double dz = e.posZ-z-0.5;
				double dd = ReikaMathLibrary.py3d(dx, dy, dz);
				if (dd > this.getRange()-0.5) {
					e.motionX = -dx/dd/2;
					e.motionY = -dy/dd/2;
					e.motionZ = -dz/dd/2;
					if (!world.isRemote)
						e.velocityChanged = true;
				}
			}
			if (e instanceof EntityDragon && power >= DRAGONPOWER) {
				double dx = e.posX-x-0.5;
				double dy = e.posY-y-0.5;
				double dz = e.posZ-z-0.5;
				double dd = ReikaMathLibrary.py3d(dx, dy, dz);
				if (dd > this.getRange()-2) {
					e.motionX -= dx/dd;
					e.motionY -= dy/dd;
					e.motionZ -= dz/dd;
				}
			}
			if (e instanceof EntityWither && power >= WITHERPOWER) {
				double dx = e.posX-x-0.5;
				double dy = e.posY-y-0.5;
				double dz = e.posZ-z-0.5;
				double dd = ReikaMathLibrary.py3d(dx, dy, dz);
				if (dd > this.getRange()-2) {
					e.motionX -= dx/dd;
					e.motionY -= dy/dd;
					e.motionZ -= dz/dd;
				}
				int id = ((EntityWither)e).getWatchedTargetId(0);
				Entity ent = world.getEntityByID(id);
				if (ent != null) {
					double dx2 = ent.posX-x-0.5;
					double dy2 = ent.posY-y-0.5;
					double dz2 = ent.posZ-z-0.5;
					double dd2 = ReikaMathLibrary.py3d(dx2, dy2, dz2);
					if (dd2 > this.getRange())
						((EntityWither)e).func_82211_c(0, 0);
				}
			}
		}
	}

	private void markNoDespawn(EntityLivingBase e) {
		boolean pot = !e.isPotionActive(Potion.fireResistance);
		if (pot)
			e.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 1, 0));
		e.attackEntityFrom(DamageSource.onFire, 0);
		if (pot)
			e.removePotionEffect(Potion.fireResistance.id);
	}

	@Override
	public String getParticleType() {
		return "portal";
	}

	@Override
	public int getFallOff() {
		return FALLOFF;
	}

	@Override
	public int getRangeBoost() {
		return 0;
	}
}