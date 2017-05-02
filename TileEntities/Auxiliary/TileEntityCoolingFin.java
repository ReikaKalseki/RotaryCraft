/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
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

	private int temperature;

	public int ticks = 512;

	public FinSettings setting = FinSettings.FULL;

	public static enum FinSettings {
		FULL(20),
		HALF(40),
		QUARTER(80);

		public final int tickRate;

		private static final FinSettings[] list = values();

		private FinSettings(int n) {
			tickRate = n;
		}

		public FinSettings next() {
			return this.ordinal() == list.length-1 ? list[0] : list[this.ordinal()+1];
		}
	}

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
		if (tickcount < setting.tickRate)
			return;
		tickcount = 0;
		this.updateTemperature(world, x, y, z, meta);
		TileEntity te = world.getTileEntity(targetx, targety, targetz);
		if (te instanceof TemperatureTE) {
			TemperatureTE tr = (TemperatureTE)te;
			if (tr.canBeCooledWithFins()) {
				int temp = tr.getTemperature();
				if (temp > temperature) {
					temperature++;
					tr.addTemperature(-1);
				}
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
		temperature += temp;
	}

	@Override
	public int getTemperature() {
		return temperature;
	}

	@Override
	public void overheat(World world, int x, int y, int z) {

	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("tick", ticks);
		NBT.setInteger("setting", setting.ordinal());
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		ticks = NBT.getInteger("tick");
		setting = FinSettings.list[NBT.getInteger("setting")];
	}

	@Override
	public void onEMP() {}

	@Override
	public boolean canBeCooledWithFins() {
		return false;
	}

	public void setTemperature(int temp) {
		temperature = temp;
	}

	@Override
	public boolean allowExternalHeating() {
		return false;
	}

	@Override
	public int getMaxTemperature() {
		return 2500;
	}
}
