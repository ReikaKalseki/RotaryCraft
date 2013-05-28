/*******************************************************************************
 * @author Reika
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import net.minecraft.world.World;

public interface TemperatureTE {

	public abstract void updateTemperature(World world, int x, int y, int z, int meta);

	public abstract int getThermalDamage();

}
