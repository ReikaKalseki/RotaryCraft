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

public class BedrockDigEvent extends TileEntityEvent {

	public final int breakX;
	public final int breakY;
	public final int breakZ;

	public BedrockDigEvent(TileEntity te, int x, int y, int z) {
		super(te);

		breakX = x;
		breakY = y;
		breakZ = z;
	}

}
