/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API.Event;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import Reika.DragonAPI.Instantiable.Event.TileEntityEvent;
import cpw.mods.fml.common.eventhandler.Event.HasResult;

@HasResult
/** You can set result to ALLOW to force normal handling, i.e. a setDead() and an explosion. */
public class ForceFieldEvent extends TileEntityEvent {

	public final Entity targetEntity;

	public ForceFieldEvent(TileEntity te, Entity e) {
		super(te);
		targetEntity = e;
	}

	public double getRange() {
		return targetEntity.getDistance(this.getTileX()+0.5, this.getTileY()+0.5, this.getTileZ()+0.5);
	}

}
