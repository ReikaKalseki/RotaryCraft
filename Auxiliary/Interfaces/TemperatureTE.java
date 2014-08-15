/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.Interfaces;

import net.minecraft.world.World;

public interface TemperatureTE {

	public abstract void updateTemperature(World world, int x, int y, int z, int meta);

	public abstract void addTemperature(int temp);

	public abstract int getTemperature();

	public abstract int getThermalDamage();

	public abstract void overheat(World world, int x, int y, int z);

}