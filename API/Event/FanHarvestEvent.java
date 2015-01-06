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

import net.minecraft.tileentity.TileEntity;
import Reika.DragonAPI.Instantiable.Event.TileEntityEvent;

public class FanHarvestEvent extends TileEntityEvent {

	public final int x;
	public final int y;
	public final int z;

	public FanHarvestEvent(TileEntity te, int x, int y, int z) {
		super(te);

		this.x = x;
		this.y = y;
		this.z = z;
	}

}
