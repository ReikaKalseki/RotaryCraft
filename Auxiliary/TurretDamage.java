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
import net.minecraft.tileentity.TileEntity;

import Reika.DragonAPI.Instantiable.CustomStringDamageSource;
import Reika.RotaryCraft.Base.TileEntity.TileEntityAimedCannon;

public class TurretDamage extends CustomStringDamageSource {

	private final EntityPlayer player;
	private final TileEntity tile;

	public TurretDamage(TileEntityAimedCannon te) {
		this(te, te.getPlacer());
	}

	public TurretDamage(TileEntityAimedCannon te, EntityPlayer ep) {
		super("found themselves in high-powered crosshairs");
		player = ep;
		tile = te;
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
