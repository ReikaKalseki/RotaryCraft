/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Auxiliary;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorChamber;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.Interpolation;
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

	private static final Interpolation reactorTemperatureEfficiency = new Interpolation(false);

	public static enum FinSettings {
		FULL(20),
		HALF(40),
		QUARTER(80);

		public final int tickRate;
		public final float rawMultiplier;

		private static final FinSettings[] list = values();

		private FinSettings(int n) {
			tickRate = n;
			rawMultiplier = 20F/tickRate;
		}

		public FinSettings next() {
			return this.ordinal() == list.length-1 ? list[0] : list[this.ordinal()+1];
		}
	}

	static {
		reactorTemperatureEfficiency.addPoint(-273, 4);
		reactorTemperatureEfficiency.addPoint(-100, 3);
		reactorTemperatureEfficiency.addPoint(-25, 2.5);
		reactorTemperatureEfficiency.addPoint(0, 2);
		reactorTemperatureEfficiency.addPoint(15, 1.5);
		reactorTemperatureEfficiency.addPoint(25, 1.25);
		reactorTemperatureEfficiency.addPoint(50, 1);
		reactorTemperatureEfficiency.addPoint(80, 0.75);
		reactorTemperatureEfficiency.addPoint(100, 0.5);
		reactorTemperatureEfficiency.addPoint(250, 0.25);
		reactorTemperatureEfficiency.addPoint(500, 0.125);
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
			//temperature -= Math.max(1, (temperature-Tamb)/16);
			temperature = Math.max(Tamb, temperature-2);
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
		TileEntity te = world.getTileEntity(targetx, targety, targetz);
		if (!world.isRemote && ModList.IC2.isLoaded() && (te instanceof IReactor || te instanceof IReactorChamber)) {
			this.coolIC2Reactor(world, x, y, z, te);
		}
		if (tickcount < setting.tickRate)
			return;
		tickcount = 0;
		this.updateTemperature(world, x, y, z, meta);
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

	private void coolIC2Reactor(World world, int x, int y, int z, Object te) {
		if (this.getTicksExisted()%20 != 0)
			return;
		if (te instanceof IReactorChamber) {
			te = ((IReactorChamber)te).getReactor();
		}
		IReactor r = (IReactor)te;
		int h = r.getHeat();
		if (h > 0) {
			int rem = Math.max(1, (int)Math.min(20*setting.rawMultiplier*reactorTemperatureEfficiency.getValue(temperature), h));
			//ReikaJavaLibrary.pConsole(temperature+" > "+reactorTemperatureEfficiency.getValue(temperature));
			r.addHeat(-rem); //20 == 4x 1 reactor heat vent
			int net = 500*h/r.getMaxHeat();
			//ReikaJavaLibrary.pConsole(h+" of "+r.getMaxHeat()+" > "+net);
			temperature += Math.max(1, Math.min(net-temperature, rem));
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
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);
		NBT.setInteger("tick", ticks);
		NBT.setInteger("setting", setting.ordinal());
		NBT.setInteger("temp", temperature);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);
		ticks = NBT.getInteger("tick");
		setting = FinSettings.list[NBT.getInteger("setting")];
		temperature = NBT.getInteger("temp");
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
