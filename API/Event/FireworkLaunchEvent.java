/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API.Event;

import Reika.DragonAPI.Instantiable.Event.TileEntityEvent;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityFireworkMachine;

import net.minecraft.entity.item.EntityFireworkRocket;

public class FireworkLaunchEvent extends TileEntityEvent {

	public final EntityFireworkRocket entity;

	public FireworkLaunchEvent(TileEntityFireworkMachine te, EntityFireworkRocket fw) {
		super(te);

		entity = fw;
	}
}