/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API;

import java.util.ArrayList;

import net.minecraft.world.World;

/** Make your block or TileEntity implements this to have custom Angular Transducer display. */
public interface Transducerable {

	public ArrayList<String> getMessages(World world, int x, int y, int z, int side);

}