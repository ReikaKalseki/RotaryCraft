/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import net.minecraft.entity.Entity;

import Reika.DragonAPI.Interfaces.Registry.EntityEnum;
import Reika.RotaryCraft.Entities.EntityCustomTNT;
import Reika.RotaryCraft.Entities.EntityDischarge;
import Reika.RotaryCraft.Entities.EntityExplosiveShell;
import Reika.RotaryCraft.Entities.EntityFlakShot;
import Reika.RotaryCraft.Entities.EntityFlameTurretShot;
import Reika.RotaryCraft.Entities.EntityFreezeGunShot;
import Reika.RotaryCraft.Entities.EntityGasMinecart;
import Reika.RotaryCraft.Entities.EntityGatlingShot;
import Reika.RotaryCraft.Entities.EntityIceBlock;
import Reika.RotaryCraft.Entities.EntityRailGunShot;
import Reika.RotaryCraft.Entities.EntitySonicShot;

public enum RotaryEntities implements EntityEnum {

	RAILGUN(EntityRailGunShot.class, "RailGun Shot"),
	FREEZEGUN(EntityFreezeGunShot.class, "Freeze Gun Shot"),
	ICE(EntityIceBlock.class, "Ice Block"),
	GASCART(EntityGasMinecart.class, "Gas Minecart"),
	EXPLOSIVE(EntityExplosiveShell.class, "Explosive Shell"),
	//LIQUIDBLOCK(EntityLiquidBlock.class, "Liquid Block"),
	SHOCKWAVE(EntitySonicShot.class, "Shock Wave"),
	DISCHARGE(EntityDischarge.class, "Discharge"),
	CUSTOMTNT(EntityCustomTNT.class, "CustomTNT"),
	//FLAMETHROWER(EntityFlameThrowerFire.class, "Flamethrower Fire"),
	FLAKSHOT(EntityFlakShot.class, "Flak Shot"),
	GATLING(EntityGatlingShot.class, "Gatling Round"),
	FLAMESHOT(EntityFlameTurretShot.class, "Burning Liquid");

	public final String entityName;
	private final Class entityClass;

	public static final RotaryEntities[] entityList = values();

	private RotaryEntities(Class<? extends Entity> c, String s) {
		entityClass = c;
		entityName = s;
	}

	@Override
	public String getBasicName() {
		return entityName;
	}

	@Override
	public boolean isDummiedOut() {
		return false;
	}

	@Override
	public Class getObjectClass() {
		return entityClass;
	}

	@Override
	public String getUnlocalizedName() {
		return entityName;
	}

	@Override
	public int getTrackingDistance() {
		return 64;
	}

	@Override
	public boolean sendsVelocityUpdates() {
		return true;
	}

	@Override
	public boolean hasSpawnEgg() {
		return false;
	}

	@Override
	public int eggColor1() {
		return 0;
	}

	@Override
	public int eggColor2() {
		return 0;
	}

}
