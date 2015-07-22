/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.Interfaces;

import net.minecraft.world.World;
import Reika.DragonAPI.Interfaces.TileEntity.ThermalTile;
import Reika.RotaryCraft.API.Interfaces.TemperatureTile;

public interface TemperatureTE extends ThermalTile, TemperatureTile {

	public abstract void updateTemperature(World world, int x, int y, int z, int meta);

	public abstract void addTemperature(int temp);

	public abstract int getThermalDamage();

	public abstract void overheat(World world, int x, int y, int z);

	public abstract boolean canBeCooledWithFins();

}
