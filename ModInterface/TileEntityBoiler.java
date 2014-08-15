/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.ReikaRailCraftHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.PoweredLiquidIO;
import Reika.RotaryCraft.Registry.MachineRegistry;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityBoiler extends PoweredLiquidIO implements TemperatureTE {

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
		if (temperature > 100)
			storedEnergy += power*200;

		//ReikaJavaLibrary.pConsole(storedEnergy/ReikaRailCraftHelper.getSteamBucketEnergy(this.getWaterTemp()), Side.SERVER);

		//ReikaJavaLibrary.pConsoleSideOnly(this.getSteam()+":"+storedEnergy+"/"+ReikaRailCraftHelper.getSteamBucketEnergy(), Side.SERVER);
		if (storedEnergy >= ReikaRailCraftHelper.getSteamBucketEnergy(this.getWaterTemp()) && !world.isRemote)
			this.makeSteam();

		TileEntity te = world.getTileEntity(x, y+1, z);
		if (te instanceof IFluidHandler) {
			IFluidHandler ic = (IFluidHandler)te;
			if (output.getFluid() != null) {
				ic.fill(ForgeDirection.DOWN, output.getFluid(), true);
				output.drain(output.getFluid().amount, true);
			}
		}
	}

	private void makeSteam() {
		if (this.getWater() >= FluidContainerRegistry.BUCKET_VOLUME && (output.isEmpty() || output.canTakeIn(1000))) {
			input.removeLiquid(FluidContainerRegistry.BUCKET_VOLUME);
			output.fill(FluidRegistry.getFluidStack("steam", FluidContainerRegistry.BUCKET_VOLUME), true);
			storedEnergy -= ReikaRailCraftHelper.getSteamBucketEnergy(this.getWaterTemp());
		}
	}

	private int getWaterTemp() {
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
		return m == MachineRegistry.PIPE;
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
		return p == MachineRegistry.PIPE;
	}

	@Override
	public boolean canOutputToPipe(MachineRegistry p) {
		return p == MachineRegistry.PIPE;
	}

}