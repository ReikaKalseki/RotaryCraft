/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import Reika.DragonAPI.Instantiable.CustomStringDamageSource;

public class GravelGunDamage extends CustomStringDamageSource {

	private EntityPlayer player;

	public GravelGunDamage(EntityPlayer ep) {
		super("was hit by supersonic flint");
		player = ep;
	}

	@Override
	public boolean isProjectile() {
		return true;
	}

	@Override
	public Entity getEntity() {
		return player;
	}

	@Override
	public boolean isUnblockable() {
		return true;
	}

}
