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
import Reika.RotaryCraft.TileEntities.Production.TileEntityBedrockBreaker;

public class BedrockDigEvent extends TileEntityEvent {

	public final int breakX;
	public final int breakY;
	public final int breakZ;

	public BedrockDigEvent(TileEntityBedrockBreaker te, int x, int y, int z) {
		super(te);

		breakX = x;
		breakY = y;
		breakZ = z;
	}

}