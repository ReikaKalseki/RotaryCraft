/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.Interfaces;

import net.minecraft.world.World;
import Reika.RotaryCraft.API.Interfaces.PressureTile;

public interface PressureTE extends PressureTile {

	public abstract void updatePressure(World world, int x, int y, int z, int meta);

	public abstract void addPressure(int press);

	public abstract void overpressure(World world, int x, int y, int z);

}
