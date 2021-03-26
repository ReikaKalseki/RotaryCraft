/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API.Interfaces;

import java.util.UUID;

import net.minecraft.tileentity.TileEntity;

import Reika.RotaryCraft.API.Interfaces.RailGunAmmo.RailGunAmmoType;

/** Implement this on an entity to make the defence turrets target it. */
public interface TargetEntity {

	/** Called when a railgun projectile hits the entity. Note well that impacts are incredibly destructive, and will destroy large
	 * swaths of terrain and only heavily armored (or otherwise protected) entities should survive, as the impact energy is comparable
	 * to having a jet airliner crash on top of you. Return true to prevent further processing. */
	public boolean onRailgunImpact(TileEntity source, RailGunAmmoType type);

	/** Used to modify the knockback from a railgun impact. Do not return values less than zero or excessively large. */
	public double getKnockbackMultiplier(TileEntity source, RailGunAmmoType type);

	/** Called when the laser gun hits the entity. Usually used to set fire, can also do things like "cut". */
	public void onLaserBeam(TileEntity source);

	/** Called by the freeze gun. Use this to halt all self-powered motion if appropriate. */
	public void onFreeze(TileEntity source);

	/** Called by the anti-air gun. Use this to respond to anti-aircraft/anti-airmob fire. */
	public void flakShot(TileEntity source);

	/** Should the turret target this entity? Args: Machine, Machine owner UUID */
	public boolean shouldTarget(TileEntity source, UUID owner);

}
