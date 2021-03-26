/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.Conversion;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidHandler;

import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.Power.ReikaRailCraftHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.RCToModConverter;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.PoweredLiquidIO;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityBoiler extends PoweredLiquidIO implements TemperatureTE, RCToModConverter {

	private int temperature;
	private long storedEnergy;

	public static final int CAPACITY = 27000;
	public static final int MAXTEMP = 500;

	private StepTimer timer = new StepTimer(20);

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.BOILER;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		if (output.isFull())
			return 15;
		if (input.isEmpty())
			return 15;
		return 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();

		timer.update();
		if (timer.checkCap())
			this.updateTemperature(world, x, y, z, meta);
		if (this.acceptEnergy())
			storedEnergy += power*200*ConfigRegistry.getConverterEfficiency();

		//ReikaJavaLibrary.pConsole(storedEnergy/ReikaRailCraftHelper.getSteamBucketEnergy(this.getWaterTemp()), Side.SERVER);

		//ReikaJavaLibrary.pConsoleSideOnly(this.getSteam()+":"+storedEnergy+"/"+ReikaRailCraftHelper.getSteamBucketEnergy(), Side.SERVER);
		if (!world.isRemote) {
			int space = output.getRemainingSpace();
			if (space > 0) {
				int mB = Math.min(space, Math.min(input.getLevel(), ReikaRailCraftHelper.getAmountConvertibleSteam(this.getInitTemp(), storedEnergy)));
				if (mB > 0)
					this.makeSteam(mB);
			}
		}

		TileEntity te = world.getTileEntity(x, y+1, z);
		if (te instanceof IFluidHandler) {
			IFluidHandler ic = (IFluidHandler)te;
			if (output.getFluid() != null) {
				int amt = ic.fill(ForgeDirection.DOWN, output.getFluid(), true);
				if (amt > 0)
					output.removeLiquid(amt);
			}
		}
	}

	private boolean acceptEnergy() {
		return temperature > 100 && !input.isEmpty() && !output.isFull();
	}

	private void makeSteam(int mB) {
		input.removeLiquid(mB);
		output.addLiquid(this.getProducedSteam(mB), FluidRegistry.getFluid("steam"));
		storedEnergy -= ReikaRailCraftHelper.getSteamEnergy(this.getInitTemp(), mB);
	}

	private int getProducedSteam(int mB) {
		return 8*mB;
	}

	private int getInitTemp() {
		return ReikaWorldHelper.getAmbientTemperatureAt(worldObj, xCoord, yCoord, zCoord);
	}

	public int getSteam() {
		return output.getFluid() != null ? output.getFluid().amount : 0;
	}

	public int getWater() {
		return input.getLevel();
	}

	@Override
	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		if (power > 0) {
			temperature += 0.3125*ReikaMathLibrary.logbase(power, 2);
		}
		if (temperature > Tamb) {
			temperature -= (temperature-Tamb)/40;
		}
		else {
			temperature += (temperature-Tamb)/40;
		}
		if (temperature - Tamb <= 40 && temperature > Tamb)
			temperature--;
		if (temperature > MAXTEMP) {
			temperature = MAXTEMP;
			this.overheat(world, x, y, z);
		}
		if (temperature > 50) {
			ForgeDirection side = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.snow);
			if (side != null)
				ReikaWorldHelper.changeAdjBlock(world, x, y, z, side, Blocks.air, 0);
			side = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.ice);
			if (side != null)
				ReikaWorldHelper.changeAdjBlock(world, x, y, z, side, Blocks.flowing_water, 0);
		}
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
	public int getThermalDamage() {
		return 0;
	}

	@Override
	public void overheat(World world, int x, int y, int z) {
		world.setBlockToAir(x, y, z);
		world.createExplosion(null, x+0.5, y+0.5, z+0.5, 6, true);
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("temp", temperature);
		NBT.setLong("energy", storedEnergy);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		temperature = NBT.getInteger("temp");
		storedEnergy = NBT.getLong("energy");
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m.isStandardPipe();
	}

	@Override
	public Fluid getInputFluid() {
		return FluidRegistry.WATER;
	}

	@Override
	public int getCapacity() {
		return CAPACITY;
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return from.offsetY == 0;
	}

	@Override
	public boolean canOutputTo(ForgeDirection to) {
		return to == ForgeDirection.UP;
	}

	@Override
	public boolean canIntakeFromPipe(MachineRegistry p) {
		return p.isStandardPipe();
	}

	@Override
	public boolean canOutputToPipe(MachineRegistry p) {
		return p.isStandardPipe();
	}

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
	public boolean allowHeatExtraction() {
		return false;
	}

	@Override
	public int getMaxTemperature() {
		return MAXTEMP;
	}

	@Override
	public int getGeneratedUnitsPerTick() {
		return this.getProducedSteam(this.getWater());
	}

	@Override
	public String getUnitDisplay() {
		return "Steam";
	}

}
