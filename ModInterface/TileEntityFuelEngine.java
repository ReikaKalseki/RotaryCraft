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

import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityEngineController;

import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile.PipeType;

public class TileEntityFuelEngine extends TileEntityIOMachine implements IFluidHandler, SimpleProvider, PowerGenerator, IPipeConnection,
TemperatureTE {

	public static final int GEN_OMEGA = 256;
	public static final int GEN_TORQUE = 1024;

	private int temperature;

	public static final int CAPACITY = 24000;

	private HybridTank tank = new HybridTank("fuelengine", CAPACITY);
	private HybridTank watertank = new HybridTank("waterfuelengine", CAPACITY);
	private HybridTank lubetank = new HybridTank("lubefuelengine", CAPACITY);

	private StepTimer fuelTimer = new StepTimer(36); //30 min a bucket
	private StepTimer soundTick = new StepTimer(40);
	private StepTimer tempTimer = new StepTimer(20);

	private boolean canEmitPower(World world, int x, int y, int z) {
		if (tank.isEmpty())
			return false;
		if (world.provider instanceof IGalacticraftWorldProvider) {
			IGalacticraftWorldProvider ig = (IGalacticraftWorldProvider)world.provider;
			if (ig.getSoundVolReductionAmount() > 1)
				return false;
		}
		MachineRegistry m = MachineRegistry.getMachine(world, x, y-1, z);
		if (m == MachineRegistry.ECU) {
			TileEntityEngineController te = (TileEntityEngineController)world.getTileEntity(x, y-1, z);
			return te.canProducePower();
		}
		return !lubetank.isEmpty();
	}

	private void updateSpeed(int maxspeed, boolean revup) {
		if (revup) {
			if (omega < maxspeed) {
				//ReikaJavaLibrary.pConsole(omega+"->"+(omega+2*(int)(ReikaMathLibrary.logbase(maxspeed, 2))), Side.SERVER);
				omega += 4*(int)ReikaMathLibrary.logbase(maxspeed, 2);
				tank.removeLiquid(1); //more fuel burn while spinning up
				if (omega > maxspeed)
					omega = maxspeed;
			}
		}
		else {
			if (omega > 0) {
				//ReikaJavaLibrary.pConsole(omega+"->"+(omega-omega/128-1), Side.SERVER);
				omega -= omega/256+1;
				//soundtick = 2000;
			}
		}
	}

	private int getFuelDuration(World world, int x, int y, int z) {
		MachineRegistry m = MachineRegistry.getMachine(world, x, y-1, z);
		if (m == MachineRegistry.ECU) {
			TileEntityEngineController te = (TileEntityEngineController)world.getTileEntity(x, y-1, z);
			return 36*te.getFuelMultiplier();
		}
		return 36;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		fuelTimer.setCap(this.getFuelDuration(world, x, y, z));
		int genomega = this.getGenOmega();
		tempTimer.update();
		if (tempTimer.checkCap()) {
			this.updateTemperature(world, x, y, z, meta);
		}
		if (this.canEmitPower(world, x, y, z)) {
			fuelTimer.update();
			if (fuelTimer.checkCap()) {
				tank.removeLiquid(1);
			}
			torque = GEN_TORQUE;
			MachineRegistry m = MachineRegistry.getMachine(world, x, y-1, z);
			if (m == MachineRegistry.ECU) {
				TileEntityEngineController te = (TileEntityEngineController)world.getTileEntity(x, y-1, z);
				genomega *= te.getSpeedMultiplier();
			}
		}
		else {
			genomega = 0;
			if (omega == 0) {
				torque = 0;
			}
		}
		this.updateSpeed(genomega, genomega >= omega);
		power = omega*torque;
		soundTick.update();
		if (power > 0) {
			this.makeSmoke(world, x, y, z, meta);
			if (soundTick.checkCap()) {
				SoundRegistry.DIESEL.playSoundAtBlock(world, x, y, z, RotaryAux.isMuffled(this) ? 0.3F : 1F, 0.4F);
			}
			if (world.getTotalWorldTime()%8 == 0)
				lubetank.removeLiquid(1);
		}
	}

	private int getGenOmega() {
		return temperature <= 450 ? GEN_OMEGA : Math.max(16, GEN_OMEGA+450-temperature);
	}

	private void makeSmoke(World world, int x, int y, int z, int meta) {
		if (isFlipped)
			y -= 0.5;
		switch(meta) {
		case 0:
			ReikaParticleHelper.SMOKE.spawnAt(world, x+0.6875, y+0.9375, z+0.0625);
			ReikaParticleHelper.SMOKE.spawnAt(world, x+0.6875, y+0.9375, z+0.9375);
			break;
		case 1:
			ReikaParticleHelper.SMOKE.spawnAt(world, x+0.3175, y+0.9375, z+0.0625);
			ReikaParticleHelper.SMOKE.spawnAt(world, x+0.3175, y+0.9375, z+0.9375);
			break;
		case 2:
			ReikaParticleHelper.SMOKE.spawnAt(world, x+0.0625, y+0.9375, z+0.6875);
			ReikaParticleHelper.SMOKE.spawnAt(world, x+0.9375, y+0.9375, z+0.6875);
			break;
		case 3:
			ReikaParticleHelper.SMOKE.spawnAt(world, x+0.0625, y+0.9375, z+0.3175);
			ReikaParticleHelper.SMOKE.spawnAt(world, x+0.9375, y+0.9375, z+0.3175);
			break;
		}
	}

	private void getIOSides(World world, int x, int y, int z, int meta) {
		switch(meta) {
		case 0:
			write = ForgeDirection.WEST;
			break;
		case 1:
			write = ForgeDirection.EAST;
			break;
		case 2:
			write = ForgeDirection.NORTH;
			break;
		case 3:
			write = ForgeDirection.SOUTH;
			break;
		}
	}

	@Override
	public boolean canProvidePower() {
		return !tank.isEmpty();
	}

	@Override
	public PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		return new PowerSourceList().addSource(this);
	}

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
		return MachineRegistry.FUELENGINE;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 15*tank.getLevel()/tank.getCapacity();
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		Fluid f = resource.getFluid();
		if (this.canFill(from, f)) {
			if (f.equals(FluidRegistry.getFluid("lubricant")))
				return lubetank.fill(resource, doFill);
			else if (f.equals(FluidRegistry.getFluid("fuel")))
				return tank.fill(resource, doFill);
			else if (f.equals(FluidRegistry.WATER))
				return watertank.fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid f) {
		switch(from) {
		case DOWN:
			return f.equals(FluidRegistry.getFluid("fuel"));
		case EAST:
		case NORTH:
		case SOUTH:
		case WEST:
			return f.equals(FluidRegistry.getFluid("lubricant")) || f.equals(FluidRegistry.WATER);
		default:
			return false;
		}
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo(), watertank.getInfo(), lubetank.getInfo()};
	}

	@Override
	public long getMaxPower() {
		return power;
	}

	@Override
	public long getCurrentPower() {
		return power;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		tank.writeToNBT(NBT);
		watertank.writeToNBT(NBT);
		lubetank.writeToNBT(NBT);

		NBT.setInteger("temp", temperature);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		tank.readFromNBT(NBT);
		watertank.readFromNBT(NBT);
		lubetank.readFromNBT(NBT);

		temperature = NBT.getInteger("temp");
	}

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with) {
		return type == PipeType.FLUID && with != ForgeDirection.DOWN ? ConnectOverride.CONNECT : ConnectOverride.DEFAULT;
	}

	public int getFuelLevel() {
		return tank.getLevel();
	}

	public int getWaterLevel() {
		return watertank.getLevel();
	}

	public int getLubeLevel() {
		return lubetank.getLevel();
	}

	public void addFuel(int amt) {
		tank.addLiquid(amt, FluidRegistry.getFluid("fuel"));
	}

	public void removeFuel(int amt) {
		tank.removeLiquid(amt);
	}

	public void addWater(int amt) {
		watertank.addLiquid(amt, FluidRegistry.WATER);
	}

	public void addLube(int amt) {
		lubetank.addLiquid(amt, FluidRegistry.getFluid("lubricant"));
	}

	@Override
	public int getEmittingX() {
		return xCoord+write.offsetX;
	}

	@Override
	public int getEmittingY() {
		return yCoord+write.offsetY;
	}

	@Override
	public int getEmittingZ() {
		return zCoord+write.offsetZ;
	}

	@Override
	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		int dT = temperature-Tamb;

		if (dT > 0) {
			temperature--;
			int c = (temperature-Tamb)/100;
			if (!watertank.isEmpty()) {
				temperature -= c;
				watertank.removeLiquid(20);
			}
		}

		if (power > 0) {
			temperature += 5;
		}

		if (temperature > 750)
			this.overheat(world, x, y, z);
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
		world.newExplosion(null, x+0.5, y+0.5, z+0.5, 4, true, true);
		world.newExplosion(null, x+0.5, y+0.5, z+0.5, 8, true, true);
	}

}