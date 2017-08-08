/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import java.util.Collection;

import micdoodle8.mods.galacticraft.api.world.IAtmosphericGas;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModRegistry.InterfaceCache;
import Reika.RotaryCraft.API.Power.PowerGenerator;
import Reika.RotaryCraft.API.Power.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.PowerSourceTracker;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.EngineType.EngineClass;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityEngineController;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile.PipeType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Strippable(value = {"buildcraft.api.transport.IPipeConnection"})
public class TileEntityFuelEngine extends TileEntityIOMachine implements IFluidHandler, PipeConnector, SimpleProvider, PowerGenerator, IPipeConnection,
TemperatureTE {

	public static final int GEN_OMEGA = 256;
	public static final int GEN_TORQUE = 2048;

	private int temperature;

	public static final int CAPACITY = 24000;
	public static final int MAXTEMP = 750;

	private final HybridTank tank = new HybridTank("fuelengine", CAPACITY);
	private final HybridTank watertank = new HybridTank("waterfuelengine", CAPACITY);
	private final HybridTank lubetank = new HybridTank("lubefuelengine", CAPACITY);

	private StepTimer fuelTimer = new StepTimer(36); //30 min a bucket
	private StepTimer soundTick = new StepTimer(40);
	private StepTimer tempTimer = new StepTimer(20);

	private boolean canEmitPower(World world, int x, int y, int z) {
		if (tank.isEmpty())
			return false;
		if (InterfaceCache.IGALACTICWORLD.instanceOf(world.provider)) {
			IGalacticraftWorldProvider ig = (IGalacticraftWorldProvider)world.provider;
			if (!ig.hasBreathableAtmosphere() || !ig.isGasPresent(IAtmosphericGas.OXYGEN))
				return false;
		}
		if (lubetank.isEmpty())
			return false;
		if (this.hasECU()) {
			TileEntityEngineController te = this.getECU();
			return te.canProducePower();
		}
		return true;
	}

	private boolean hasECU() {
		return this.getMachine(isFlipped ? ForgeDirection.UP : ForgeDirection.DOWN) == MachineRegistry.ECU;
	}

	private TileEntityEngineController getECU() {
		return (TileEntityEngineController)this.getAdjacentTileEntity(isFlipped ? ForgeDirection.UP : ForgeDirection.DOWN);
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
		if (this.hasECU()) {
			TileEntityEngineController te = this.getECU();
			return 36*te.getFuelMultiplier(EngineClass.PISTON);
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
				tank.removeLiquid(this.getConsumedFuel());
			}
			torque = GEN_TORQUE;
			if (this.hasECU()) {
				TileEntityEngineController te = this.getECU();
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
			if (world.getTotalWorldTime()%32 == 0)
				lubetank.removeLiquid(1);
		}
	}

	private int getConsumedFuel() {
		return 2;
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
	public PowerSourceList getPowerSources(PowerSourceTracker io, ShaftMerger caller) {
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
			if (f.equals(FluidRegistry.getFluid("rc lubricant")))
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
			case UP:
				return isFlipped && f.equals(FluidRegistry.getFluid("fuel"));
			case DOWN:
				return !isFlipped && f.equals(FluidRegistry.getFluid("fuel"));
			case EAST:
			case NORTH:
			case SOUTH:
			case WEST:
				return f.equals(FluidRegistry.getFluid("rc lubricant")) || f.equals(FluidRegistry.WATER);
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
		lubetank.addLiquid(amt, FluidRegistry.getFluid("rc lubricant"));
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

		if (temperature > MAXTEMP)
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
		if (!world.isRemote) {
			world.newExplosion(null, x+0.5, y+0.5, z+0.5, 4, true, true);
			world.newExplosion(null, x+0.5, y+0.5, z+0.5, 8, true, true);
		}
	}

	@Override
	public boolean canBeCooledWithFins() {
		return false;
	}

	@Override
	public boolean allowExternalHeating() {
		return false;
	}

	public void setTemperature(int temp) {
		temperature = temp;
	}

	@Override
	public void getAllOutputs(Collection<TileEntity> c, ForgeDirection dir) {
		c.add(this.getAdjacentTileEntity(write));
	}

	@SideOnly(Side.CLIENT)
	public int getFuelScaled(int a) {
		return tank.getLevel() * a / tank.getCapacity();
	}

	@SideOnly(Side.CLIENT)
	public int getWaterScaled(int a) {
		return watertank.getLevel() * a / watertank.getCapacity();
	}

	@SideOnly(Side.CLIENT)
	public int getLubricantScaled(int a) {
		return lubetank.getLevel() * a / lubetank.getCapacity();
	}

	@SideOnly(Side.CLIENT)
	public int getTemperatureScaled(int a) {
		return temperature * a / MAXTEMP;
	}

	public int getFuelDuration() {
		return tank.getLevel()*fuelTimer.getCap()/this.getConsumedFuel()/20;
	}

	@Override
	public int getMaxTemperature() {
		return MAXTEMP;
	}

	@Override
	public final boolean canConnectToPipe(MachineRegistry m) {
		return m.isStandardPipe() || m == MachineRegistry.HOSE || m == MachineRegistry.FUELLINE;
	}

	@Override
	public final boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return this.canConnectToPipe(p) && side == ForgeDirection.DOWN;
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return side == ForgeDirection.DOWN ? Flow.INPUT : Flow.NONE;
	}

}
