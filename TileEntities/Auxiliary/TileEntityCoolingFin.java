/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
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
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityCoolingFin extends RotaryCraftTileEntity implements TemperatureTE {

	private int targetx;
	private int targety;
	private int targetz;

	public int temperature;

	public int ticks = 512;

	@Override
	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		if (ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.water) != null)
			Tamb -= 5;
		if (ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.lava) != null)
			Tamb = 2600;
		if (ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.ice) != null)
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
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.COOLINGFIN;
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
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("tick", ticks);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		ticks = NBT.getInteger("tick");
	}

	@Override
	public void onEMP() {}
}
