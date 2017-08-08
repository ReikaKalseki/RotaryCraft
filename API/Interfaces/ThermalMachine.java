/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API.Interfaces;

import net.minecraft.world.World;

/** Implement this to allow the friction heater to heat your TileEntity. Temperatures can range anywhere from -100 or so to +2000. */
public interface ThermalMachine extends BasicTemperatureMachine {

	/** For overwriting the temperature */
	public void setTemperature(int T);

	/** For updating the temperature */
	public void addTemperature(int T);

	/** Actions to take on overheat */
	public void onOverheat(World world, int x, int y, int z);

	/** Can the friction heater heat this machine */
	public boolean canBeFrictionHeated();

	/** A simple multiplication factor to control the heat gain for a given power value; at the 1x default, 67MW will reach 2000C, the maximum
	 * achievable by the Friction Heater. Note that heat from power is a logarithmic function, and that nlogx = log(x^n), and thus power
	 * requirements are actually a power function (= ^1/f), so a multiplier of 0.5x will see the required power be squared, and a multiplier of
	 * 0.25 will cause the requirement to be raised to the fourth power. Multipliers greater than one are also functional, though take care
	 * not to pick a multiplier so large that the required power for a given temperature rounds to zero (temperatures are integers). */
	public float getMultiplier();

}
