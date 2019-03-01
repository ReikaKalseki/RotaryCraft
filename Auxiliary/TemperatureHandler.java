/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.EnumSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import Reika.DragonAPI.Instantiable.Data.Maps.BlockMap;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;


public class TemperatureHandler {

	public static final TemperatureHandler instance = new TemperatureHandler();

	private final BlockMap<Integer> blockTemps = new BlockMap();

	private TemperatureHandler() {
		blockTemps.put(Blocks.lava, 600);
		blockTemps.put(Blocks.flowing_lava, 600);
		blockTemps.put(Blocks.fire, 200);
		blockTemps.put(Blocks.water, 12);
		blockTemps.put(Blocks.flowing_water, 12);
		blockTemps.put(Blocks.ice, -3);
		blockTemps.put(Blocks.packed_ice, -10);
		blockTemps.put(Blocks.snow, -2);
		blockTemps.put(Blocks.snow_layer, 0);
	}

	public int getAmbientTemperature(World world, int x, int y, int z, int minAmb, int maxAmb) {
		return MathHelper.clamp_int(ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z), minAmb, maxAmb);
	}

	public int getAmbientTemperatureOffset(World world, int x, int y, int z, int temp, boolean allowHeating, boolean allowCooling) {
		return this.getAmbientTemperatureOffset(world, x, y, z, temp, allowHeating ? EnumSet.allOf(ForgeDirection.class) : null, allowCooling ? EnumSet.allOf(ForgeDirection.class) : null);
	}

	public int getAmbientTemperatureOffset(TemperatureTE te, Set<ForgeDirection> heatSides, Set<ForgeDirection> coolSides) {
		TileEntity tile = (TileEntity)te;
		return this.getAmbientTemperatureOffset(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord, te.getTemperature(), heatSides, coolSides);
	}

	public int getAmbientTemperatureOffset(World world, int x, int y, int z, int temp, Set<ForgeDirection> heatSides, Set<ForgeDirection> coolSides) {
		int ret = 0;
		if (heatSides != null) {
			for (ForgeDirection dir : heatSides) {
				int get = this.getBlockTemperature(world, x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ);
				if (get != Integer.MIN_VALUE && get > temp)
					ret += get-temp;
			}
		}
		if (coolSides != null) {
			for (ForgeDirection dir : coolSides) {
				int get = this.getBlockTemperature(world, x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ);
				if (get != Integer.MIN_VALUE && get < temp)
					ret -= temp-get;
			}
		}
		return ret;
	}

	private int getBlockTemperature(World world, int x, int y, int z) {
		Block b = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		Integer base = blockTemps.get(b, meta);
		if (base != null) {
			return base.intValue();
		}
		Fluid f = FluidRegistry.lookupFluidForBlock(b);
		if (f != null) {
			return f.getTemperature(world, x, y, z);
		}
		return Integer.MIN_VALUE;
	}

	public void applyTemperature(TemperatureTE te) {
		TileEntity tile = (TileEntity)te;
		this.applyTemperature(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord, te.getTemperature());
	}

	public void applyTemperature(World world, int x, int y, int z, int temp) {

	}

}
