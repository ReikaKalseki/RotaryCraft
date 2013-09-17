/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Auxiliary;

import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.TemperatureTE;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityCoolingFin extends RotaryCraftTileEntity implements TemperatureTE {

	private int targetx;
	private int targety;
	private int targetz;

	public int temperature;

	public int ticks = 512;

	@Override
	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaWorldHelper.getBiomeTemp(world, x, z);
		if (ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.water) != -1)
			Tamb -= 5;
		if (ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.lava) != -1)
			Tamb = 2600;
		if (ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.ice) != -1)
			Tamb -= 15;
		if (Tamb > temperature) {
			temperature++;
		}
		else {
			temperature--;
		}
	}

	public int[] getTarget() {
		return new int[]{targetx, targety, targetz};
	}

	@Override
	public int getThermalDamage() {
		return temperature/200;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.COOLINGFIN.ordinal();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		tickcount++;
		if (ticks > 0)
			ticks -= 8;
		this.getTargetSide(world, x, y, z, meta);
		if (tickcount < 20)
			return;
		tickcount = 0;
		this.updateTemperature(world, x, y, z, meta);
		TileEntity te = world.getBlockTileEntity(targetx, targety, targetz);
		if (te instanceof TemperatureTE) {
			TemperatureTE tr = (TemperatureTE)te;
			int temp = tr.getTemperature();
			if (temp > temperature) {
				temperature++;
				tr.addTemperature(-1);
			}
		}
	}

	private void getTargetSide(World world, int x, int y, int z, int meta) {
		switch(meta) {
		case 0:
			targetx = x;
			targety = y-1;
			targetz = z;
			break;
		case 1:
			targetx = x;
			targety = y+1;
			targetz = z;
			break;
		case 2:
			targetx = x;
			targety = y;
			targetz = z-1;
			break;
		case 3:
			targetx = x-1;
			targety = y;
			targetz = z;
			break;
		case 4:
			targetx = x;
			targety = y;
			targetz = z+1;
			break;
		case 5:
			targetx = x+1;
			targety = y;
			targetz = z;
			break;
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void addTemperature(int temp) {

	}

	@Override
	public int getTemperature() {
		return temperature;
	}

	@Override
	public void overheat(World world, int x, int y, int z) {

	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("tick", ticks);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		ticks = NBT.getInteger("tick");
	}

	@Override
	public void onEMP() {}
}
