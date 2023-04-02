/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

import java.util.Collection;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.ParallelTicker;
import Reika.DragonAPI.Interfaces.TileEntity.PartialInventory;
import Reika.DragonAPI.Interfaces.TileEntity.PartialTank;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaTimeHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.AtmosphereHandler;
import Reika.RotaryCraft.API.Power.PowerGenerator;
import Reika.RotaryCraft.API.Power.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.Interfaces.IntegratedGearboxable;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.PowerSourceTracker;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Items.Tools.ItemIntegratedGearbox;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityEngineController;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityHydroEngine;

import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile.PipeType;

@Strippable(value = {"buildcraft.api.transport.IPipeConnection"})
public abstract class TileEntityEngine extends TileEntityInventoryIOMachine implements TemperatureTE, SimpleProvider,
PipeConnector, PowerGenerator, IFluidHandler, PartialInventory, PartialTank, IntegratedGearboxable, IPipeConnection {

	/** Water capacity */
	public static final int CAPACITY = 60*1000;

	/** Fuel capacity */
	public static final int FUELCAP = 240*1000;
	public static final int LUBECAP = 24*1000;

	protected final HybridTank lubricant = new HybridTank("enginelube", LUBECAP);

	protected final HybridTank water = new HybridTank("enginewater", CAPACITY);
	protected final HybridTank fuel = new HybridTank("enginefuel", FUELCAP);

	protected final HybridTank air = new HybridTank("engineair", 1000);

	public int temperature;

	/** For timing control */
	public int soundtick = 2000;

	protected EngineType type = EngineType.DC;

	protected int backx;
	protected int backz;

	private boolean isOn;
	private boolean hasO2Boost;

	protected long lastpower = 0;

	private int integratedGear = 0;

	protected ParallelTicker timer = new ParallelTicker().addTicker("fuel").addTicker("sound").addTicker("temperature", ReikaTimeHelper.SECOND.getDuration());

	public int getMaxTemperature() {
		return 1000;
	}

	public final EngineType getEngineType() {
		return type != null ? type : EngineType.DC;
	}

	public final void setType(ItemStack is) {
		if (ItemRegistry.ENGINE.matchItem(is)) {
			type = EngineType.engineList[is.getItemDamage()];
		}
	}

	public final int getInventoryStackLimit() {
		return type.allowInventoryStacking() ? 64 : 1;
	}

	public final int getSizeInventory() {
		return 2;
	}

	public boolean hasTemperature() {
		return type.isCooled();
	}

	@Override
	public final boolean isUseableByPlayer(EntityPlayer ep) {
		return type.hasGui() && super.isUseableByPlayer(ep);
	}

	public final int getWaterScaled(int par1) {
		return water.getLevel()*par1/CAPACITY;
	}

	public final int getTempScaled(int par1) {
		return temperature*par1/this.getMaxTemperature();
	}

	public final int getFuelScaled(int par1) {
		return this.getFuelLevel()*par1/FUELCAP;
	}

	protected abstract void consumeFuel(float scale);

	protected int getConsumedFuel() {
		return 10;
	}

	protected abstract void internalizeFuel();

	protected abstract boolean getRequirements(World world, int x, int y, int z, int meta);

	protected final boolean hasAir(World world, int x, int y, int z) {
		if (this.isDrowned(world, x, y, z))
			return false;
		if (AtmosphereHandler.isNoAtmo(world, x-this.getWriteDirection().offsetX, y, z-this.getWriteDirection().offsetZ, blockType, true))
			return false;
		return true;
	}

	public boolean hasAir() {
		return !air.isEmpty() || this.hasAir(worldObj, xCoord, yCoord, zCoord);
	}

	public final boolean isDrowned(World world, int x, int y, int z) {
		boolean flag = false;
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			int dx = x+dir.offsetX;
			int dy = y+dir.offsetY;
			int dz = z+dir.offsetZ;
			Block id = world.getBlock(dx, dy, dz);
			boolean fluid = id instanceof BlockLiquid || id instanceof BlockFluidBase;
			flag = flag || fluid;
			if (id == Blocks.air)
				return false;
			if (!fluid)
				if (ReikaWorldHelper.softBlocks(world, dx, dy, dz))
					return false;
		}
		return flag && true;
	}

	public void updateTemperature(World world, int x, int y, int z, int meta) {
		//BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		temperature = Math.max(temperature, Tamb);
		//ReikaChatHelper.writeInt(temperature);
		if (temperature > Tamb && !isOn) {
			this.offlineCooldown(world, x, y, z, Tamb);
		}
	}

	protected void offlineCooldown(World world, int x, int y, int z, int Tamb) {
		temperature -= Math.max(1, (temperature-Tamb)/256);
	}

	public final boolean isOn() {
		return isOn;
	}

	public void overheat(World world, int x, int y, int z) {

	}

	private void setPowerData(World world, int x, int y, int z, int meta) {
		int speed = this.getIntegratedGearSpeed(this.getMaxSpeed(world, x, y, z, meta), integratedGear);
		this.updateSpeed(speed, speed >= omega && (omega > 0 || this.canStart()));
		torque = this.getIntegratedGearTorque(this.getGenTorque(world, x, y, z, meta), integratedGear);
	}

	static int getIntegratedGearTorque(int torque, int gear) {
		if (gear != 0) {
			return gear > 0 ? gear*torque : -torque/gear;
		}
		return torque;
	}

	static int getIntegratedGearSpeed(int speed, int gear) {
		if (gear != 0) {
			return gear < 0 ? -gear*speed : speed/gear;
		}
		return speed;
	}

	protected boolean canStart() {
		return true;
	}

	protected int getMaxSpeed(World world, int x, int y, int z, int meta) {
		return type.getSpeed();
	}

	protected int getGenTorque(World world, int x, int y, int z, int meta) {
		return type.getTorque();
	}

	protected abstract void affectSurroundings(World world, int x, int y, int z, int meta);

	protected final int getSoundLength() {
		return this.getSoundLength(1);
	}

	protected int getSoundLength(float factor) {
		if (factor == 2.5F && type.carNoise())
			factor = 1.81F;
		if (factor == 2.5F && type.turbineNoise()) {
			factor = 2F;
		}
		if (type.jetNoise()) {
			factor += 0.0125F;
		}
		return (int)(type.getSoundLength()*factor);
	}

	private void initialize(World world, int x, int y, int z, int meta) {
		timer.setCap("sound", this.getSoundLength());

		if (timer.checkCap("temperature")) {
			this.updateTemperature(world, x, y, z, meta);
		}

		boolean on = type.isAirBreathing() ? this.hasAir(world, x, y, z) : true;
		hasO2Boost = false;

		if (!air.isEmpty() && type.isAirBreathing()) {
			on = true;
			hasO2Boost = true;
		}

		if (on && this.getRequirements(world, x, y, z, meta)) {
			isOn = true;
			this.setPowerData(world, x, y, z, meta);
		}
		else {
			isOn = false;
			this.updateSpeed(0, false);
			//omega = 0;
			if (omega == 0)
				torque = 0;
			if (soundtick == 0 && omega == 0)
				soundtick = 2000;
			//timer.resetTicker("fuel");
		}
	}

	private void updateSpeed(int maxspeed, boolean revup) {
		if (this.hasECU() && this.canBeThrottled()) {
			TileEntityEngineController te = this.getECU();
			if (te != null) {
				maxspeed *= te.getSpeedMultiplier();
			}
			if (omega > maxspeed)
				revup = false;
		}
		if (revup) {
			if (omega < maxspeed) {
				//ReikaJavaLibrary.pConsole(omega+"->"+(omega+2*(int)(ReikaMathLibrary.logbase(maxspeed, 2))), Side.SERVER);
				omega += 4*ReikaMathLibrary.logbase(maxspeed+1, 2);
				timer.setCap("fuel", Math.max(type.getFuelUnitDuration()/4, 1)); //4x fuel burn while spinning up
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

	private boolean hasECU() {
		return this.getMachine(isFlipped ? ForgeDirection.UP : ForgeDirection.DOWN) == MachineRegistry.ECU;
	}

	private TileEntityEngineController getECU() {
		return (TileEntityEngineController)this.getAdjacentTileEntity(isFlipped ? ForgeDirection.UP : ForgeDirection.DOWN);
	}

	protected abstract void playSounds(World world, int x, int y, int z, float pitchMultiplier, float vol);

	protected final boolean isMuffled(World world, int x, int y, int z) {
		if (RotaryAux.isMufflingBlock(world, x, y+1, z) || this.getMachine(ForgeDirection.UP) == MachineRegistry.ECU) {
			if (RotaryAux.isMufflingBlock(world, x, y-1, z) || this.getMachine(ForgeDirection.DOWN) == MachineRegistry.ECU)
				return true;
		}
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			if (dir != ForgeDirection.DOWN) {
				int dx = x+dir.offsetX;
				int dy = y+dir.offsetY;
				int dz = z+dir.offsetZ;
				if ((dir != write.getOpposite() && dir != write) || dir == ForgeDirection.UP) {
					if (!RotaryAux.isMufflingBlock(world, dx, dy, dz))
						return false;
				}
			}
		}
		return true;
	}

	@Override
	public final void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);

		timer.updateTicker("temperature");
		if (this.isShutdown()) {
			omega = torque = 0;
			power = 0;
		}
		else {
			if (!worldObj.isRemote || RotaryAux.getPowerOnClient) {
				timer.setCap("fuel", type.getFuelUnitDuration());
				this.initialize(world, x, y, z, meta);
			}
			power = (long)torque*(long)omega;
			if (power > 0)
				this.affectSurroundings(world, x, y, z, meta);
		}

		float pitch = 1F;
		float soundfactor = 1F;
		if (type.isECUControllable() && this.hasECU()) {
			TileEntityEngineController te = this.getECU();
			if (te != null) {
				if (te.canProducePower()) {
					if (this.canBeThrottled()) {
						int fueltime = type.getFuelUnitDuration();
						if (omega >= type.getSpeed()*te.getSpeedMultiplier()) {
							//omega = (int)(omega*te.getSpeedMultiplier());
							int max = (int)(type.getSpeed()*te.getSpeedMultiplier());
							//this.updateSpeed(max, omega < max);
						}
						else {
							fueltime = Math.max(type.getFuelUnitDuration()/4, 1);
						}
						timer.setCap("fuel", fueltime);
						int fuelcap = timer.getCapOf("fuel");
						fuelcap = fuelcap*te.getFuelMultiplier(type.type);
						timer.setCap("fuel", fuelcap);
						pitch = te.getSoundStretch();
						soundfactor = 1F/te.getSoundStretch();
						int soundcap = timer.getCapOf("sound");
						soundcap = (int)(soundcap*soundfactor);
						timer.setCap("sound", soundcap);
						int tempcap = timer.getCapOf("temperature");
						tempcap *= soundfactor;
						timer.setCap("temperature", tempcap);
					}
				}
				else {
					//this.updateSpeed(0, false);
					this.resetPower();
					soundtick = 0;
				}
			}
		}

		this.basicPowerReceiver();

		this.internalizeFuel();
		if (power > 0) {
			if (hasO2Boost)
				air.removeLiquid(1);
			timer.updateTicker("fuel");
			if (type.burnsFuel() && timer.checkCap("fuel") && this.canConsumeFuel()) {
				this.consumeFuel(hasO2Boost ? 0.8F : 1);
			}
		}

		if (power > 0) {
			this.playSounds(world, x, y, z, pitch, 1);
		}
		else if (soundtick < this.getSoundLength(soundfactor))
			soundtick = 2000;

		lastpower = power;
	}

	protected void resetPower() {
		if (omega == 0)
			torque = 0;
		power = (long)omega*(long)torque;
		soundtick = 2000;
		lastpower = power;
	}

	protected boolean canConsumeFuel() {
		return this.getFuelLevel() > 0;
	}

	protected boolean canBeThrottled() {
		return true;
	}

	public final void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
			case 0:
				write = ForgeDirection.WEST;
				backx = x+1;
				backz = z;
				break;
			case 1:
				write = ForgeDirection.EAST;
				backx = x-1;
				backz = z;
				break;
			case 2:
				write = ForgeDirection.NORTH;
				backx = x;
				backz = z+1;
				break;
			case 3:
				write = ForgeDirection.SOUTH;
				backx = x;
				backz = z-1;
				break;
		}
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		NBT.setInteger("type", type.ordinal());

		if (this.hasTemperature())
			NBT.setInteger("temperature", temperature);

		if (type.needsWater())
			water.writeToNBT(NBT);
		if (type.isEthanolFueled() || type.isJetFueled())
			fuel.writeToNBT(NBT);

		if (type.requiresLubricant())
			lubricant.writeToNBT(NBT);

		if (type.burnsFuel()) {
			NBT.setInteger("fueltimer", timer.getCapOf("fuel"));
		}

		NBT.setInteger("gear", integratedGear);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		type = EngineType.setType(NBT.getInteger("type"));

		if (this.hasTemperature())
			temperature = NBT.getInteger("temperature");

		if (type.requiresLubricant())
			lubricant.readFromNBT(NBT);

		if (type.needsWater())
			water.readFromNBT(NBT);
		if (type.isEthanolFueled() || type.isJetFueled())
			fuel.readFromNBT(NBT);

		if (NBT.hasKey("fueltimer")) {
			timer.setCap("fuel", NBT.getInteger("fueltimer"));
		}

		integratedGear = NBT.getInteger("gear");
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		timer.writeToNBT(NBT, "engine");
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		timer.readFromNBT(NBT, "engine");

		if (omega > type.getSpeed())
			omega = type.getSpeed();
		if (torque > type.getTorque())
			torque = type.getTorque();
	}

	@Override
	public final boolean isItemValidForSlot(int i, ItemStack is) {
		if (!type.isValidFuel(is))
			return false;
		if (i >= type.getSizeInventory())
			return false;
		switch(type) {
			case GAS:
			case AC:
				return true;
			case SPORT:
				return (i == 0 && is.getItem() == ItemRegistry.ETHANOL.getItemInstance()) || (i == 1 && type.isAdditive(is));
			default:
				return false;
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public final boolean canExtractItem(int i, ItemStack itemstack, int j) {
		if (type == EngineType.AC) {
			if (ReikaItemHelper.matchStacks(itemstack, ItemStacks.shaftcore) || ReikaItemHelper.matchStacks(itemstack, ItemStacks.tungstenshaftcore)) {
				if (itemstack.stackTagCompound == null)
					return true;
				if (itemstack.stackTagCompound.getInteger("magnet") == 0)
					return true;
			}
			return false;
		}
		if (type == EngineType.STEAM) {
			return itemstack.getItem() == Items.bucket;
		}
		return false;
	}

	@Override
	protected final void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		double pow = 1.05;
		double mult = 1;
		if (type == EngineType.JET)
			pow = 1.1;
		if (type == EngineType.HYDRO) {
			TileEntityHydroEngine te = (TileEntityHydroEngine)this;
			if (te.failed) {
				phi += 16;
				return;
			}
			mult = 256F/type.getSpeed();
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase((int)(mult*omega+1), 2), pow);
	}

	@Override
	public final boolean canProvidePower() {
		return true;
	}

	@Override
	public final MachineRegistry getMachine() {
		return MachineRegistry.ENGINE;
	}

	@Override
	public final int getThermalDamage() {
		if (type.canHurtPlayer() && this.hasTemperature())
			return (temperature)/100;
		return 0;
	}

	@Override
	public final int getRedstoneOverride() {
		if (type.burnsFuel()) {
			if (type.isEthanolFueled())
				return 15*fuel.getLevel()/FUELCAP;
			if (type.isJetFueled())
				return 15*fuel.getLevel()/FUELCAP;
			else
				return 15*water.getLevel()/FUELCAP;
		}
		return 0;
	}

	public abstract int getFuelLevel();

	public void setDataFromPlacer(ItemStack is) {

	}

	public final void setTemperature(int temp) {
		temperature = temp;
	}

	public final int getFuelCapacity() {
		if (type.isEthanolFueled())
			return FUELCAP;
		if (type.isJetFueled())
			return FUELCAP;
		if (type == EngineType.STEAM)
			return CAPACITY;
		return 0;
	}

	/** In seconds */
	public final int getFuelDuration() {
		if (!type.burnsFuel())
			return -1;
		int fuel = this.getFuelLevel();
		float burnprogress = 0;
		if (fuel > 0)
			burnprogress = 1F-timer.getPortionOfCap("fuel")/fuel;
		float factor = type.getFuelUnitDuration()/(float)timer.getCapOf("fuel"); //to compensate for 4x burn during spinup
		if (factor <= 0)
			return 0;
		return (int)((fuel*type.getFuelUnitDuration()*(burnprogress))*5/factor/1000*10D/this.getConsumedFuel());
	}

	/** In seconds */
	public final int getFullTankDuration() {
		if (!type.burnsFuel())
			return -1;
		return this.getFuelCapacity()*type.getFuelUnitDuration()*5;
	}

	@Override
	public final boolean canConnectToPipe(MachineRegistry m) {
		return m.isStandardPipe() || m == MachineRegistry.FUELLINE || m == MachineRegistry.HOSE;
	}

	@Override
	public final boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		if (type == null)
			return false;
		if (type.isJetFueled() || type.isEthanolFueled())
			if ((p == MachineRegistry.FUELLINE || p == MachineRegistry.BEDPIPE) && side == (isFlipped ? ForgeDirection.UP : ForgeDirection.DOWN))
				return true;
		if (type.isWaterPiped() && p.isStandardPipe()) {
			switch(side) {
				case EAST:
					return this.getBlockMetadata() == 0;
				case SOUTH:
					return this.getBlockMetadata() == 2;
				case WEST:
					return this.getBlockMetadata() == 1;
				case NORTH:
					return this.getBlockMetadata() == 3;
				default:
					return false;
			}
		}
		if (type.requiresLubricant() && (p == MachineRegistry.HOSE || p == MachineRegistry.BEDPIPE)) {
			//ReikaJavaLibrary.pConsole(this.getBlockMetadata()+":"+side.name());
			switch(side) {
				case EAST:
					return this.getBlockMetadata() == 0;
				case SOUTH:
					return this.getBlockMetadata() == 2;
				case WEST:
					return this.getBlockMetadata() == 1;
				case NORTH:
					return this.getBlockMetadata() == 3;
				default:
					return false;
			}
		}
		return false;
	}

	@Override
	public final void addTemperature(int temp) {
		temperature += temp;
	}

	@Override
	public final int getTemperature() {
		return temperature;
	}

	public boolean allowExternalHeating() {
		return false;
	}

	@Override
	public boolean allowHeatExtraction() {
		return this.canBeCooledWithFins();
	}

	@Override
	public boolean canBeCooledWithFins() {
		return false;
	}

	@Override
	public double heatEnergyPerDegree() {
		return 2*super.heatEnergyPerDegree();
	}

	@Override
	public final void onEMP() {
		if (type.isEMPImmune())
			return;
		else
			super.onEMP();
	}

	@Override
	public PowerSourceList getPowerSources(PowerSourceTracker io, ShaftMerger caller) {
		PowerSourceList psl = new PowerSourceList();
		if (power > 0)
			psl.addSource(this);
		return psl;
	}

	@Override
	public final void getAllOutputs(Collection<TileEntity> c, ForgeDirection dir) {
		c.add(this.getAdjacentTileEntity(write));
	}

	public final long getMaxPower() {
		if (type == null)
			return 0;
		return type.getPower();
	}

	public final long getCurrentPower() {
		return power;
	}

	@Override
	public final int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		Fluid f = resource.getFluid();
		if (!this.canFill(from, f))
			return 0;
		if (f.equals(FluidRegistry.WATER)) {
			return water.fill(resource, doFill);
		}
		else if (f.equals(FluidRegistry.getFluid("rc lubricant"))) {
			return lubricant.fill(resource, doFill);
		}
		else if (isAirFluid(f) && type.isAirBreathing()) {
			return air.fill(resource, doFill);
		}
		else {
			return fuel.fill(resource, doFill);
		}
	}

	public static boolean isAirFluid(Fluid f) {
		return f.equals(FluidRegistry.getFluid("air")) || f.equals(FluidRegistry.getFluid("oxygen")) || f.equals(FluidRegistry.getFluid("rc oxygen"));
	}

	@Override
	public final FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public final FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public final boolean canFill(ForgeDirection from, Fluid fluid) {
		if (isAirFluid(fluid)) {
			return type.isAirBreathing() && from == this.getFuelInputDirection();
		}
		if (!type.canReceiveFluid(fluid))
			return false;
		if (fluid.equals(FluidRegistry.WATER)) {
			int dx = xCoord+from.offsetX;
			int dy = yCoord+from.offsetY;
			int dz = zCoord+from.offsetZ;
			return dx == backx && dy == yCoord && dz == backz;
		}
		else if (fluid.equals(FluidRegistry.getFluid("rc lubricant"))) {
			int dx = xCoord+from.offsetX;
			int dy = yCoord+from.offsetY;
			int dz = zCoord+from.offsetZ;
			return dx == backx && dy == yCoord && dz == backz;
		}
		else if (fluid.equals(FluidRegistry.getFluid("rc jet fuel"))) {
			return from == this.getFuelInputDirection();
		}
		else if (fluid.equals(FluidRegistry.getFluid("rc ethanol"))) {
			return from == this.getFuelInputDirection();
		}
		else if (fluid.equals(FluidRegistry.getFluid("bioethanol"))) {
			return from == this.getFuelInputDirection();
		}
		return false;
	}

	private ForgeDirection getFuelInputDirection() {
		return isFlipped ? ForgeDirection.UP : ForgeDirection.DOWN;
	}

	@Override
	public final boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public final FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{water.getInfo(), fuel.getInfo(), lubricant.getInfo()};
	}

	public final void addFuel(int amt) {
		fuel.addLiquid(amt, type.getFuelType());
	}

	public final void addLubricant(int amt) {
		lubricant.addLiquid(amt, FluidRegistry.getFluid("rc lubricant"));
	}

	public final void removeLubricant(int amt) {
		lubricant.removeLiquid(amt);
	}

	public final void setLube(int amt) {
		lubricant.setContents(amt, FluidRegistry.getFluid("rc lubricant"));
	}

	public final int getLube() {
		return lubricant.getLevel();
	}

	public final void subtractFuel(int amt) {
		fuel.removeLiquid(amt);
	}

	public final void addWater(int amt) {
		water.addLiquid(amt, FluidRegistry.WATER);
	}

	public final int getWater() {
		return water.getLevel();
	}

	@Override
	public final Flow getFlowForSide(ForgeDirection side) {
		return Flow.INPUT;
	}

	@Override
	public final boolean hasInventory() {
		return type.hasInventory();
	}

	@Override
	public final boolean hasTank() {
		return type.usesFluids();
	}

	@Override
	public final int getEmittingX() {
		return xCoord+write.offsetX;
	}

	@Override
	public final int getEmittingY() {
		return yCoord+write.offsetY;
	}

	@Override
	public final int getEmittingZ() {
		return zCoord+write.offsetZ;
	}

	public boolean isBroken() {
		return false;
	}

	public void breakBlock() {
		if (integratedGear != 0) {
			ItemStack is = ItemIntegratedGearbox.getIntegratedGearItem(integratedGear, null);
			ReikaItemHelper.dropItem(worldObj, xCoord+rand.nextDouble(), yCoord+rand.nextDouble(), zCoord+rand.nextDouble(), is);
		}
	}

	public final boolean applyIntegratedGear(ItemStack is) {
		if (is == null || !ItemRegistry.GEARUPGRADE.matchItem(is))
			return false;
		if (integratedGear != 0)
			return false;
		if (omega > 0 || power > 0)
			return false;
		integratedGear = ItemIntegratedGearbox.getRatioFromIntegratedGearItem(is, true);
		this.syncAllData(true);
		return integratedGear != 0;
	}

	public final int getIntegratedGear() {
		return integratedGear;
	}

	@ModDependent(ModList.BCTRANSPORT)
	public final ConnectOverride overridePipeConnection(PipeType type, ForgeDirection side) {
		if (type == PipeType.FLUID)
			return this.hasATank() && this.canConnectToPipeOnSide(MachineRegistry.BEDPIPE, side) ? ConnectOverride.CONNECT : ConnectOverride.DISCONNECT;
		else if (type == PipeType.ITEM)
			return this.hasAnInventory() ? ConnectOverride.CONNECT : ConnectOverride.DISCONNECT;
		else
			return ConnectOverride.DEFAULT;
	}
}
