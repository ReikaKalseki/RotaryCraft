/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.ParallelTicker;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaTimeHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.Interfaces.PartialInventory;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityEngineController;

import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public abstract class TileEntityEngine extends TileEntityInventoryIOMachine implements TemperatureTE, SimpleProvider,
PipeConnector, PowerGenerator, IFluidHandler, PartialInventory {

	/** Water capacity */
	public static final int CAPACITY = 60*1000;
	public int MAXTEMP = 1000;

	/** Fuel capacity */
	public static final int FUELCAP = 240*1000;

	public static final int LUBECAP = 24*1000;

	protected final HybridTank lubricant = new HybridTank("enginelube", LUBECAP);

	protected final HybridTank water = new HybridTank("enginewater", CAPACITY);
	protected final HybridTank fuel = new HybridTank("enginefuel", FUELCAP);

	public int temperature;

	/** For timing control */
	public int soundtick = 2000;

	protected EngineType type = EngineType.DC;

	protected int backx;
	protected int backz;

	private boolean isOn;

	protected long lastpower = 0;

	protected ParallelTicker timer = new ParallelTicker().addTicker("fuel").addTicker("sound").addTicker("temperature", ReikaTimeHelper.SECOND.getDuration());

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
		return temperature*par1/MAXTEMP;
	}

	public final int getFuelScaled(int par1) {
		return this.getFuelLevel()*par1/FUELCAP;
	}

	protected abstract void consumeFuel();

	protected int getConsumedFuel() {
		return type == EngineType.JET ? 20 : 10;
	}

	protected abstract void internalizeFuel();

	protected abstract boolean getRequirements(World world, int x, int y, int z, int meta);

	protected final boolean hasAir(World world, int x, int y, int z) {
		if (this.isDrowned(world, x, y, z))
			return false;
		if (world.provider instanceof IGalacticraftWorldProvider) {
			IGalacticraftWorldProvider ig = (IGalacticraftWorldProvider)world.provider;
			if (ig.getSoundVolReductionAmount() > 1)
				return false;
		}
		return true;
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
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		//ReikaChatHelper.writeInt(temperature);
		if (temperature > Tamb && omega == 0 && torque == 0 && type == EngineType.SPORT) { //If off and hot
			if (temperature > Tamb+300)
				temperature -= (temperature-Tamb)/100;
			else if (temperature > Tamb+100)
				temperature -= (temperature-Tamb)/50;
			else if (temperature > Tamb+40)
				temperature -= (temperature-Tamb)/10;
			else if (temperature > Tamb+4)
				temperature -= (temperature-Tamb)/2;
			else
				temperature = Tamb;
		}
	}

	public void overheat(World world, int x, int y, int z) {

	}

	private void setPowerData(World world, int x, int y, int z, int meta) {
		int speed = this.getMaxSpeed(world, x, y, z, meta);
		this.updateSpeed(speed, speed >= omega);
		torque = this.getGenTorque(world, x, y, z, meta);
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
		switch (type) {
		case STEAM:
			MAXTEMP = 150;
			break;
		case SPORT:
			MAXTEMP = 240;
			break;
		default:
			break;
		}

		timer.setCap("fuel", type.getFuelUnitDuration());
		timer.setCap("sound", this.getSoundLength());

		if (timer.checkCap("temperature")) {
			this.updateTemperature(world, x, y, z, meta);
		}

		boolean on = type.isAirBreathing() ? this.hasAir(world, x, y, z) : true;

		if (this.getRequirements(world, x, y, z, meta) && on) {
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
		if (MachineRegistry.getMachine(worldObj, xCoord, yCoord-1, zCoord) == MachineRegistry.ECU) {
			TileEntityEngineController te = (TileEntityEngineController)this.getAdjacentTileEntity(ForgeDirection.DOWN);
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

	protected abstract void playSounds(World world, int x, int y, int z, float pitchMultiplier);

	protected final boolean isMuffled(World world, int x, int y, int z) {
		if (world.getBlock(x, y+1, z) == Blocks.wool) {
			if (world.getBlock(x, y-1, z) == Blocks.wool || this.getMachine(ForgeDirection.DOWN) == MachineRegistry.ECU)
				return true;
		}
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			if (dir != ForgeDirection.DOWN) {
				int dx = x+dir.offsetX;
				int dy = y+dir.offsetY;
				int dz = z+dir.offsetZ;
				if ((dir != write.getOpposite() && dir != write) || dir == ForgeDirection.UP) {
					Block b = world.getBlock(dx, dy, dz);
					if (b != Blocks.wool)
						return false;
				}
			}
		}
		return true;
	}

	@Override
	public final void updateEntity(World world, int x, int y, int z, int meta)
	{
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);

		timer.updateTicker("temperature");
		if (this.isShutdown()) {
			omega = torque = 0;
			power = 0;
		}
		else {
			this.initialize(world, x, y, z, meta);
			power = (long)torque*(long)omega;
			if (power > 0)
				this.affectSurroundings(world, x, y, z, meta);
		}

		float pitch = 1F;
		float soundfactor = 1F;
		if (type.isECUControllable()) {
			if (MachineRegistry.getMachine(world, x, y-1, z) == MachineRegistry.ECU) {
				TileEntityEngineController te = (TileEntityEngineController)this.getAdjacentTileEntity(ForgeDirection.DOWN);
				if (te != null) {
					if (te.canProducePower()) {
						if (omega >= type.getSpeed()*te.getSpeedMultiplier()) {
							//omega = (int)(omega*te.getSpeedMultiplier());
							int max = (int)(type.getSpeed()*te.getSpeedMultiplier());
							//this.updateSpeed(max, omega < max);
						}
						int fuelcap = timer.getCapOf("fuel");
						fuelcap = fuelcap*te.getFuelMultiplier();
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
					else {
						//this.updateSpeed(0, false);
						this.resetPower();
						return;
					}
				}
			}
		}

		this.basicPowerReceiver();

		timer.updateTicker("fuel");
		this.internalizeFuel();
		if (type.burnsFuel() && timer.checkCap("fuel") && this.canConsumeFuel())
			this.consumeFuel();

		if (power > 0) {
			this.playSounds(world, x, y, z, pitch);
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
			if (ReikaItemHelper.matchStacks(itemstack, ItemStacks.shaftcore)) {
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
		return (int)((fuel*type.getFuelUnitDuration()*(burnprogress))*5/factor/1000);
	}

	/** In seconds */
	public final int getFullTankDuration() {
		if (!type.burnsFuel())
			return -1;
		return this.getFuelCapacity()*type.getFuelUnitDuration()*5;
	}

	@Override
	public final boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE || m == MachineRegistry.FUELLINE || m == MachineRegistry.HOSE;
	}

	@Override
	public final boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		if (type == null)
			return false;
		if (type.isJetFueled())
			if (p == MachineRegistry.FUELLINE && side == ForgeDirection.DOWN)
				return true;
		if (type.isEthanolFueled())
			if (p == MachineRegistry.FUELLINE && side == ForgeDirection.DOWN)
				return true;
		if (type.isWaterPiped() && p == MachineRegistry.PIPE) {
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
		if (type.requiresLubricant() && p == MachineRegistry.HOSE) {
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

	@Override
	public final void onEMP() {
		if (type.isEMPImmune())
			return;
		else
			super.onEMP();
	}

	@Override
	public final PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		return new PowerSourceList().addSource(this);
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
		else if (f.equals(FluidRegistry.getFluid("lubricant"))) {
			return lubricant.fill(resource, doFill);
		}
		else {
			return fuel.fill(resource, doFill);
		}
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
		if (!type.canReceiveFluid(fluid))
			return false;
		if (fluid.equals(FluidRegistry.WATER)) {
			int dx = xCoord+from.offsetX;
			int dy = yCoord+from.offsetY;
			int dz = zCoord+from.offsetZ;
			return dx == backx && dy == yCoord && dz == backz;
		}
		else if (fluid.equals(FluidRegistry.getFluid("lubricant"))) {
			int dx = xCoord+from.offsetX;
			int dy = yCoord+from.offsetY;
			int dz = zCoord+from.offsetZ;
			return dx == backx && dy == yCoord && dz == backz;
		}
		else if (fluid.equals(FluidRegistry.getFluid("jet fuel"))) {
			return from == ForgeDirection.DOWN;
		}
		else if (fluid.equals(FluidRegistry.getFluid("rc ethanol"))) {
			return from == ForgeDirection.DOWN;
		}
		else if (fluid.equals(FluidRegistry.getFluid("bioethanol"))) {
			return from == ForgeDirection.DOWN;
		}
		return false;
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
		lubricant.addLiquid(amt, FluidRegistry.getFluid("lubricant"));
	}

	public final void removeLubricant(int amt) {
		lubricant.removeLiquid(amt);
	}

	public final void setLube(int amt) {
		lubricant.setContents(amt, FluidRegistry.getFluid("lubricant"));
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
}