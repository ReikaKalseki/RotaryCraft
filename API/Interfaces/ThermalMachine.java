/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API.Interfaces;

import net.minecraft.world.World;

/** Implement this to allow the friction heater to heat your TileEntity. Temperatures can range anywhere from -100 or so to +2000. */
public interface ThermalMachine extends TemperatureTile {

	/** For overwriting the temperature */
	public void setTemperature(int T);

	/** For updating the temperature */
	public void addTemperature(int T);

	/** Actions to take on overheat */
	public void onOverheat(World world, int x, int y, int z);

	/** Can the friction heater heat this machine */
	public boolean canBeFrictionHeated();

}
