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

public interface PressureTE {

	public abstract void updatePressure(World world, int x, int y, int z, int meta);

	public abstract void addPressure(int press);

	public abstract int getPressure();

	public abstract void overpressure(World world, int x, int y, int z);

}