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

import java.util.ArrayList;
import java.util.Collection;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Interfaces.TileEntity.GuiController;
import Reika.DragonAPI.Interfaces.TileEntity.ToggleTile;
import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.API.Power.PowerGenerator;
import Reika.RotaryCraft.API.Power.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.Interfaces.IntegratedGearboxable;
import Reika.RotaryCraft.Auxiliary.Interfaces.NBTMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.PowerSourceTracker;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Auxiliary.Interfaces.UpgradeableMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Items.Tools.ItemEngineUpgrade.Upgrades;
import Reika.RotaryCraft.Items.Tools.ItemIntegratedGearbox;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public abstract class EnergyToPowerBase extends TileEntityIOMachine implements SimpleProvider, PowerGenerator, GuiController, UpgradeableMachine,
IFluidHandler, PipeConnector, TemperatureTE, ToggleTile, NBTMachine, IntegratedGearboxable {

	private static final int MINBASE = -1;

	public static final int MAXTEMP = 500;

	public static final int TIERS = 6;

	protected int storedEnergy;

	protected int baseomega = -1;

	private ForgeDirection facingDir;

	private int temperature;

	private StepTimer tempTimer = new StepTimer(20);

	private static final boolean reika = DragonAPICore.isReikasComputer();
	private final HybridTank tank = new HybridTank("energytopower", 24000);

	private int tier;

	private RedstoneState rsState = RedstoneState.IGNORE;

	private boolean enabled = true;

	private boolean efficient = false;

	private int integratedGear = 0;

	private static final double[][] efficiencyTable = new double[2][TIERS];

	static {
		for (int i = 0; i < TIERS; i++) {
			efficiencyTable[0][i] = 0.9-i*0.08;
			efficiencyTable[1][i] = 1-Math.pow(i, 1.4)*0.04;
		}
	}

	private RedstoneState getRedstoneState() {
		return rsState != null ? rsState : RedstoneState.IGNORE;
	}

	private static double getEfficiency(int tier, boolean eff) {
		return efficiencyTable[eff ? 1 : 0][tier];
	}

	private static final long getTierPower(int tier) {
		return getGenTorque(tier)*ReikaMathLibrary.intpow2(2, getMaxSpeedBase(tier));
	}

	private static final int getGenTorque(int tier) {
		return 8*ReikaMathLibrary.intpow2(4, tier);
	}

	public static final int getMaxSpeedBase(int tier) {
		return 8+tier;
	}

	protected double getRelativeEfficiency() {
		return 1;
	}

	private final double getConsumption() {
		return 1D/this.getEfficiency();
	}

	public final double getEfficiency() {
		return getEfficiency(tier, efficient)*this.getRelativeEfficiency();
	}

	public final long getTierPower() {
		return getTierPower(tier);
	}

	public final int getGenTorque() {
		return TileEntityEngine.getIntegratedGearTorque(getGenTorque(tier), integratedGear);
	}

	public final int getMaxSpeedBase() {
		return getMaxSpeedBase(tier);
	}

	@Override
	public void updateTileEntity() {
		super.updateTileEntity();
		if (DragonAPICore.debugtest) {
			storedEnergy = this.getMaxStorage();
			tank.setContents(tank.getCapacity(), FluidRegistry.getFluid("rc liquid nitrogen"));
		}
		if (storedEnergy < 0) {
			storedEnergy = 0;
		}
		tempTimer.update();
		if (tempTimer.checkCap()) {
			this.updateTemperature(worldObj, xCoord, yCoord, zCoord, this.getBlockMetadata());
		}
	}

	public final int getTier() {
		return tier;
	}

	@Override
	public final void upgrade(ItemStack item) {
		if (item.getItemDamage() == Upgrades.EFFICIENCY.ordinal()) {
			efficient = true;
		}
		else {
			tier++;
		}
		this.syncAllData(true);
	}

	public final boolean canUpgradeWith(ItemStack item) {
		if (!efficient && item.getItemDamage() == Upgrades.EFFICIENCY.ordinal())
			return true;
		if (tier >= 5)
			return false;
		if (item.getItemDamage() == 2) {
			if (item.stackTagCompound == null)
				return false;
			if (item.stackTagCompound.getInteger("magnet") < 720)
				return false;
		}
		return ItemRegistry.UPGRADE.matchItem(item) && (item.getItemDamage() == tier+1);
	}

	protected final boolean isMuffled() {
		World world = worldObj;
		int x = xCoord;
		int y = yCoord;
		int z = zCoord;
		if (world.getBlock(x, y+1, z) == Blocks.wool && world.getBlock(x, y-1, z) == Blocks.wool) {
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

	public final int getTierFromPowerOutput(long power) {
		for (int i = 0; i < TIERS; i++) {
			long tier = this.getTierPower(i);
			if (tier >= power)
				return i;
		}
		return 0;
	}

	public abstract boolean isValidSupplier(TileEntity te);

	private static enum RedstoneState {
		IGNORE(Items.gunpowder),
		LOW(Blocks.unlit_redstone_torch),
		HI(Blocks.redstone_torch);

		private final ItemStack iconItem;

		public static final RedstoneState[] list = values();

		private RedstoneState(Item i) {
			this(new ItemStack(i));
		}

		private RedstoneState(Block i) {
			this(new ItemStack(i));
		}

		private RedstoneState(ItemStack is) {
			iconItem = is.copy();
		}

		public ItemStack getDisplayIcon() {
			return iconItem.copy();
		}

		public RedstoneState next() {
			return this.ordinal() < list.length-1 ? list[this.ordinal()+1] : list[0];
		}
	}

	public boolean isRedstoneControlEnabled() {
		return this.getRedstoneState() != RedstoneState.IGNORE;
	}

	public ItemStack getRedstoneStateIcon() {
		return this.getRedstoneState().getDisplayIcon();
	}

	public boolean isEmitting() {
		if (!enabled)
			return false;
		if (this.isRedstoneControlEnabled()) {
			RedstoneState rs = this.getRedstoneState();
			return this.hasRedstoneSignal() ? rs == RedstoneState.HI : rs == RedstoneState.LOW;
		}
		else
			return true;
	}

	public void incrementRedstoneState() {
		rsState = this.getRedstoneState().next();
	}

	public final int getStoredPower() {
		return storedEnergy;
	}

	//public final void setStoredPower(int e) {
	//	storedEnergy = e;
	//}

	public abstract int getMaxStorage();

	public final long getPowerLevel() {
		return this.isEmitting() ? this.getMaxSpeed()*(long)this.getActualTorque() : 0;
	}

	public final int getSpeed() {
		return omega;
	}

	public final int getMaxSpeed() {
		if (baseomega < 0)
			return 0;
		return TileEntityEngine.getIntegratedGearSpeed(ReikaMathLibrary.intpow2(2, baseomega), integratedGear);
	}

	protected final void updateSpeed() {
		int maxspeed = this.isEmitting() ? this.getMaxSpeed() : 0;
		float mult = 1;
		boolean accel = omega <= maxspeed && this.hasEnoughEnergy();
		if (accel) {
			if (omega < maxspeed)
				mult = 1.5F;
			omega += 4*ReikaMathLibrary.logbase(maxspeed+1, 2);
			if (omega > maxspeed)
				omega = maxspeed;
		}
		else {
			if (omega > 0) {
				omega -= omega/256+1;
			}
		}
		torque = this.getActualTorque();
		power = (long)torque*(long)omega;
		if (power > 0 && !worldObj.isRemote) {
			this.usePower(mult);
			//if (worldObj.getTotalWorldTime()%(21-4*tier) == 0) {
			//	tank.removeLiquid(1);
			//}
		}
	}

	protected void usePower(float mult) {
		storedEnergy -= this.getConsumedUnitsPerTick()*mult;
		if (storedEnergy < 0)
			storedEnergy = 0;
	}

	public final int getActualTorque() {
		return omega > 0 ? this.getGenTorque() : 0;
	}

	public final boolean hasEnoughEnergy() {
		float energy = this.getStoredPower();
		return energy > this.getConsumedUnitsPerTick();
	}

	protected abstract int getIdealConsumedUnitsPerTick();

	public final int getConsumedUnitsPerTick() {
		return MathHelper.ceiling_double_int(this.getIdealConsumedUnitsPerTick()*this.getConsumption());
	}

	public final void setDataFromItemStackTag(NBTTagCompound nbt) {
		if (nbt != null) {
			tier = nbt.getInteger("tier");
			efficient = nbt.getBoolean("efficient");
			storedEnergy = nbt.getInteger("energy");
			int c = nbt.getInteger("coolant");
			if (c > 0)
				tank.setContents(c, FluidRegistry.getFluid("rc liquid nitrogen"));
		}
	}

	@Override
	public final ArrayList<NBTTagCompound> getCreativeModeVariants() {
		ArrayList<NBTTagCompound> li = new ArrayList();
		li.add(new NBTTagCompound());
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("tier", TIERS-1);
		li.add(tag);
		return li;
	}

	@Override
	public final ArrayList<String> getDisplayTags(NBTTagCompound NBT) {
		ArrayList<String> li = new ArrayList();
		this.setDataFromItemStackTag(NBT);
		li.add(String.format("Tier %d", tier));
		if (efficient)
			li.add(EnumChatFormatting.GOLD+"Efficiency Boost");
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			int torque = this.getGenTorque();
			int speed = ReikaMathLibrary.intpow2(2, getMaxSpeedBase(tier));
			long power = (long)torque*(long)speed;
			double val = ReikaMathLibrary.getThousandBase(power);
			String exp = ReikaEngLibrary.getSIPrefix(power);
			li.add(String.format("Torque: %d Nm", torque));
			li.add(String.format("Max Speed: %d rad/s", speed));
			li.add(String.format("Max Power: %.3f%sW", val, exp));
		}
		else {
			StringBuilder sb = new StringBuilder();
			sb.append("Hold ");
			sb.append(EnumChatFormatting.GREEN.toString());
			sb.append("Shift");
			sb.append(EnumChatFormatting.GRAY.toString());
			sb.append(" for power data");
			li.add(sb.toString());
		}
		return li;
	}

	public final NBTTagCompound getTagsToWriteToStack() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("tier", tier);
		nbt.setBoolean("efficient", efficient);
		nbt.setInteger("energy", storedEnergy);
		nbt.setInteger("coolant", tank.getLevel());
		return nbt;
	}

	public final void incrementOmega() {
		if (baseomega < this.getMaxSpeedBase()) {
			baseomega++;
			while (baseomega < this.getMaxSpeedBase() && this.getMaxSpeed() == 0) {
				baseomega++;
			}
		}
	}

	public final void decrementOmega() {
		if (baseomega > MINBASE)
			baseomega--;
	}

	public final boolean setOmega(int base) {
		if (base >= MINBASE && base <= this.getMaxSpeedBase()) {
			baseomega = base;
			return true;
		}
		else {
			return false;
		}
	}

	public final int getEnergyScaled(int h) {
		return (int)(this.getPercentStorage()*h);
	}

	public final float getPercentStorage() {
		return this.getStoredPower()/(float)this.getMaxStorage();
	}

	protected final void getIOSides(World world, int x, int y, int z, int meta) {
		switch(meta) {
			case 0:
				facingDir = ForgeDirection.NORTH;
				break;
			case 1:
				facingDir = ForgeDirection.WEST;
				break;
			case 2:
				facingDir = ForgeDirection.SOUTH;
				break;
			case 3:
				facingDir = ForgeDirection.EAST;
				break;
		}
		read = facingDir;
		write = read.getOpposite();
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("storage", storedEnergy);

		NBT.setInteger("tiero", baseomega);

		NBT.setInteger("rs", this.getRedstoneState().ordinal());

		if (baseomega > this.getMaxSpeedBase(tier)) {
			baseomega = MINBASE;
		}
		NBT.setInteger("level", tier);

		tank.writeToNBT(NBT);

		NBT.setInteger("temp", temperature);

		NBT.setBoolean("t_enable", enabled);

		NBT.setBoolean("efficient", efficient);

		NBT.setInteger("gear", integratedGear);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		storedEnergy = NBT.getInteger("storage");

		rsState = RedstoneState.list[NBT.getInteger("rs")];

		tier = NBT.getInteger("level");

		if (baseomega > this.getMaxSpeedBase(tier)) {
			baseomega = MINBASE;
		}

		baseomega = NBT.getInteger("tiero");

		tank.readFromNBT(NBT);

		temperature = NBT.getInteger("temp");

		if (NBT.hasKey("t_enable"))
			enabled = NBT.getBoolean("t_enable");

		efficient = NBT.getBoolean("efficient");

		integratedGear = NBT.getInteger("gear");
	}

	@Override
	public final long getCurrentPower() {
		return power;
	}

	@Override
	public final long getMaxPower() {
		return this.getTierPower();
	}

	@Override
	public final PowerSourceList getPowerSources(PowerSourceTracker io, ShaftMerger caller) {
		return new PowerSourceList().addSource(this);
	}

	@Override
	public final boolean canProvidePower() {
		return true;
	}

	public abstract String getUnitDisplay();

	public abstract int getPowerColor();

	public final ForgeDirection getFacing() {
		return facingDir != null ? facingDir : ForgeDirection.EAST;
	}

	public final TileEntity getProvidingTileEntity() {
		int x = xCoord+this.getFacing().offsetX;
		int y = yCoord+this.getFacing().offsetY;
		int z = zCoord+this.getFacing().offsetZ;
		TileEntity te = worldObj.getTileEntity(x, y, z);
		return te;
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
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return this.canFill(from, resource.getFluid()) ? tank.fill(resource, doFill) : 0;
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
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return fluid.equals(FluidRegistry.getFluid("rc liquid nitrogen"));
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m.isStandardPipe();
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return p.isStandardPipe();
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return Flow.INPUT;
	}

	public final int getLubricant() {
		return tank.getLevel();
	}

	public final int getMaxLubricant() {
		return tank.getCapacity();
	}

	public final int getLubricantScaled(int a) {
		return tank.getLevel() * a / tank.getCapacity();
	}

	@Override
	public final void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = tank.isEmpty() ? ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z) : 25;
		if (power > 0) {
			double d = tank.getLevel() >= 50 ? 0.00275 : 0.14;
			double inc = d*Math.sqrt(power)+ReikaMathLibrary.logbase(tier+1, 2);
			//ReikaJavaLibrary.pConsole(inc);
			temperature += inc;
			if (temperature > Tamb && !tank.isEmpty()) {
				int drain = Math.max(2, 50*temperature/500);
				tank.removeLiquid(drain);
			}
		}
		if (temperature > Tamb) {
			temperature -= (temperature-Tamb)/16;
		}
		else {
			temperature += (temperature-Tamb)/16;
		}
		if (temperature - Tamb <= 16 && temperature > Tamb)
			temperature--;
		if (temperature > MAXTEMP) {
			temperature = MAXTEMP;
			if (!world.isRemote)
				this.overheat(world, x, y, z);
		}
		if (temperature < Tamb)
			temperature = Tamb;
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
	public final int getThermalDamage() {
		return 0;
	}

	@Override
	public final void overheat(World world, int x, int y, int z) {
		this.delete();
		world.newExplosion(null, x+0.5, y+0.5, z+0.5, 3, true, true);
	}

	@Override
	public final boolean canBeCooledWithFins() {
		return false;
	}

	@Override
	public boolean allowExternalHeating() {
		return false;
	}

	public final void setTemperature(int temp) {
		temperature = temp;
	}

	@Override
	public void getAllOutputs(Collection<TileEntity> c, ForgeDirection dir) {
		if (dir == read)
			c.add(this.getAdjacentTileEntity(write));
	}

	@Override
	public final int getMaxTemperature() {
		return MAXTEMP;
	}

	public static String getTiersAsString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < TIERS; i++) {
			int om = ReikaMathLibrary.intpow2(2, getMaxSpeedBase(i));
			int tq = getGenTorque(i);
			long pwr = getTierPower(i);
			double base = ReikaMathLibrary.getThousandBase(pwr);
			String eng = ReikaEngLibrary.getSIPrefix(pwr);
			String s = String.format("Tier %d - Torque: %dNm; Max Speed: %d rad/s; Power: %.3f%sW", i, tq, om, base, eng);
			sb.append(s);
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public final boolean isEnabled() {
		return enabled;
	}

	@Override
	public final void setEnabled(boolean enable) {
		enabled = enable;
		this.syncAllData(false);
	}

	public final void breakBlock() {
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

}
