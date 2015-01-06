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

import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.tileentity.TileEntity;
import Reika.DragonAPI.Instantiable.Event.TileEntityEvent;

public class FireworkLaunchEvent extends TileEntityEvent {

	public final EntityFireworkRocket entity;

	public FireworkLaunchEvent(TileEntity te, EntityFireworkRocket fw) {
		super(te);

		entity = fw;
	}
}
