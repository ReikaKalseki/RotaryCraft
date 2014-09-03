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

import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Instantiable.Event.TileEntityEvent;
import Reika.RotaryCraft.TileEntities.TileEntityVacuum;

public class VacuumItemAbsorbEvent extends TileEntityEvent {

	private final ItemStack item;

	public VacuumItemAbsorbEvent(TileEntityVacuum te, ItemStack item) {
		super(te);
		this.item = item;
	}

	public final ItemStack getItem() {
		return item != null ? item.copy() : null;
	}

}
