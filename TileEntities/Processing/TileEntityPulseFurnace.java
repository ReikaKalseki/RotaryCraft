/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Processing;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.TemperatureEffect.TemperatureCallback;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesPulseFurnace;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesPulseFurnace.PulseJetRecipe;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityPulseFurnace extends InventoriedPowerReceiver implements TemperatureTE, PipeConnector, IFluidHandler, DiscreteFunction, ConditionalOperation, TemperatureCallback {

	public int pulseFurnaceCookTime;

	public static final int CAPACITY = 3000;
	public static final int MAXFUEL = 8000;
	public static final int MAXTEMP = 1000; //1370C = melting steel, 800C = 90% strength loss
	public static final int SMELTTICKS_BASE = 100;

	public boolean idle = false;

	public int temperature;	//damage player, start fires, etc


	private int tickcount2 = 0;
	public int smelttick = 0;

	private int soundtick = 2000;

	private final HybridTank fuel = new HybridTank("fuel", MAXFUEL);
	private final HybridTank water = new HybridTank("water", CAPACITY);
	private final HybridTank accel = new HybridTank("accel", MAXFUEL);

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i == 2;
	}

	public void testIdle() {
		idle = (this.getRecipe() == null && omega > MINSPEED);
	}

	public int getSizeInventory() {
		return 3;
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		pulseFurnaceCookTime = NBT.getShort("CookTime");

		water.readFromNBT(NBT);
		fuel.readFromNBT(NBT);
		accel.readFromNBT(NBT);

		temperature = NBT.getInteger("temp");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setShort("CookTime", (short)pulseFurnaceCookTime);

		water.writeToNBT(NBT);
		fuel.writeToNBT(NBT);
		accel.writeToNBT(NBT);

		NBT.setInteger("temp", temperature);
	}

	/**
	 * Returns an integer between 0 and the passed value representing how close the current item is to being completely
	 * cooked
	 */
	public int getCookProgressScaled(int par1)
	{
		return Math.min(par1, (pulseFurnaceCookTime * par1) / this.getOperationTime());
	}

	public int getFuelScaled(int par1)
	{
		return (fuel.getLevel() * par1) / MAXFUEL;
	}

	public int getTempScaled(int par1)
	{
		return (temperature * par1) / MAXTEMP;
	}

	public int getWaterScaled(int par1)
	{
		return (water.getLevel() * par1) / CAPACITY;
	}

	public int getFireScaled(int par1)
	{
		return Math.min(par1, (smelttick * par1) / this.getSmeltingDuration());
	}

	public int getAccelerantScaled(int a) {
		return accel.getLevel() * a / accel.getCapacity();
	}

	private void getFuel(World world, int x, int y, int z, int meta) {
		if (tickcount2 >= 100) {
			fuel.removeLiquid(100);
			tickcount2 = 0;
		}
	}

	private void heatAmbient(World world, int x, int y, int z, int meta) {
		if (fuel.getLevel() > 0 && this.canHeatUp())
			temperature += Math.max((MAXTEMP-temperature)/8, 4);

		int dT = 2;
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);

		Tamb = 300;

		if (Tamb < -40) {
			dT = 8;
		}
		else if (Tamb < -5) {
			dT = 6;
		}
		else if (Tamb < 5) {
			dT = 4;
		}

		if (Tamb >= 300 && this.canHeatUp()) {
			dT = -1;
		}
		else if (Tamb >= temperature) {
			dT = 0;
		}
		else if (Tamb > 30) {
			dT = 1;
		}

		if (water.getLevel() > 0) {
			if (rand.nextInt(3) == 0) {
				int rem = (temperature*2/MAXTEMP)*50;
				if (Tamb >= 180)
					rem *= 2;
				if (Tamb >= 90)
					rem *= 2;
				if (rem > 0)
					water.removeLiquid(rem);
			}
			if (Tamb >= 300)
				temperature -= temperature/256;
			else
				temperature -= temperature/64;
		}

		if (dT != 0) {
			if (dT > 0)
				temperature = Math.max(Tamb, temperature-dT);
			else
				temperature -= dT;
		}
	}

	private boolean canHeatUp() {
		return power >= MINPOWER && omega >= MINSPEED && !fuel.isEmpty();
	}

	public void smeltHeat() {
		//	this.temperature += 10*melttemp/980;	//980 kJ per degree kelvin
	}

	public void updateTemperature(World world, int x, int y, int z, int meta) {
		this.heatAmbient(world, x, y, z, meta);

		if (temperature > 915) {
			RotaryCraft.logger.warn("WARNING: "+this+" is reaching very high temperature!");
			world.spawnParticle("lava", x+rand.nextFloat(), y+rand.nextFloat(), z+rand.nextFloat(), 0, 0, 0);
		}
		ReikaWorldHelper.temperatureEnvironment(world, x, y, z, temperature, this);
		if (temperature > MAXTEMP) {
			this.overheat(world, x, y, z);
		}

	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.testIdle();
		soundtick++;
		if (tickcount >= 20 && !world.isRemote) {
			this.updateTemperature(world, x, y, z, meta);
			tickcount = 0;
		}
		if (soundtick >= 18 && this.canHeatUp()) {
			soundtick = 0;
			SoundRegistry.PULSEJET.playSoundAtBlock(world, x, y, z, 1, 1);
		}
		PulseJetRecipe recipe = this.getRecipe();
		if (recipe != null) {
			this.getFuel(world, x, y, z, meta);
		}

		tickcount++;
		tickcount2++;

		if (!world.isRemote) {
			int tick = 1;
			if (!fuel.isEmpty() && power > 0 && omega >= MINSPEED && accel.getLevel() > 10) {
				tick = 4;
				if (recipe != null || temperature >= 800) {
					accel.removeLiquid(10);
					if (rand.nextInt(4) == 0)
						temperature += 1;
				}
			}

			//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d  %d  %d", this.power, this.omega, this.torque));
			int duration = this.getSmeltingDuration();

			if (recipe != null)
				smelttick += tick;
			else
				smelttick = 0;

			if (recipe != null && smelttick >= duration) {
				pulseFurnaceCookTime += tick;
				//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", ReikaMathLibrary.extrema(2, 600-this.omega, "max")));
				if (pulseFurnaceCookTime >= this.getOperationTime()) {
					pulseFurnaceCookTime = 0;
					this.smeltItem(recipe);
					smelttick = 0;
					//flag2 = false;
				}
			}
			else
				pulseFurnaceCookTime = 0;
		}
	}

	private int getSmeltingDuration() {
		if (temperature >= 980) //8x speed if about to fail
			return SMELTTICKS_BASE/8;
		else if (temperature >= 950) //4x speed if running uncooled and very hot
			return SMELTTICKS_BASE/4;
		else if (temperature >= 900) //2x speed if running uncooled
			return SMELTTICKS_BASE/2;
		else
			return SMELTTICKS_BASE;
	}

	/** Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc. */
	private PulseJetRecipe getRecipe() {
		this.getPowerBelow();
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", power));
		if (power <= 0 || omega < MINSPEED)
			return null;
		if (inv[0] == null)
			return null;
		if (fuel.isEmpty())
			return null;

		PulseJetRecipe rec = RecipesPulseFurnace.getRecipes().getSmeltingResult(inv[0]);
		if (rec == null || rec.requiredTemperature > temperature)
			return null;

		if (inv[2] != null && !ReikaItemHelper.areStacksCombinable(inv[2], rec.getOutput(), this.getInventoryStackLimit()))
			return null;

		return rec;
	}

	/** Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack */
	private void smeltItem(PulseJetRecipe rec) {
		this.smeltHeat();
		ReikaInventoryHelper.addOrSetStack(rec.getOutput(), inv, 2);
		ReikaInventoryHelper.decrStack(0, inv);
	}/*

	private void smeltScrap() {
		int size = 1;
		if (inv[0].getItem == ItemStacks.scrap.itemID && inv[0].getItemDamage() == ItemStacks.scrap.getItemDamage())
			size = 9;
		inv[0].stackSize -= size;
		ItemStack i = this.getCraftedScrapIngot();
		ReikaInventoryHelper.addOrSetStack(i.itemID, 1, i.getItemDamage(), inv, 2);
		if (inv[0].stackSize <= 0)
			inv[0] = null;
		RotaryAchievements.RECYCLE.triggerAchievement(this.getPlacer());
	}*/

	private ItemStack getCraftedScrapIngot() {
		if (ReikaItemHelper.matchStacks(inv[0], ItemStacks.scrap))
			return ItemStacks.steelingot;
		if (ReikaItemHelper.matchStacks(inv[0], ItemStacks.ironscrap))
			return new ItemStack(Items.iron_ingot);
		return null;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.PULSEJET;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		if (slot != 0)
			return false;
		return RecipesPulseFurnace.getRecipes().getSmeltingResult(is) != null;
	}

	@Override
	public int getThermalDamage() {
		return (temperature)/100;
	}

	@Override
	public int getRedstoneOverride() {
		return this.getRecipe() == null ? 15 : 0;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.FUELLINE || m.isStandardPipe();
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return side.offsetY == 0;
	}

	@Override
	public void addTemperature(int temp) {
		temperature += temp;
	}

	@Override
	public int getTemperature() {
		return temperature;
	}

	public void overheat(World world, int x, int y, int z) {
		ReikaWorldHelper.overheat(world, x, y, z, ItemStacks.scrap.copy(), 0, 17, true, 1.5F, false, ConfigRegistry.BLOCKDAMAGE.getState(), 12F);
	}

	public int getAccelerant() {
		return accel.getLevel();
	}

	public Fluid getAccelerantType() {
		return accel.getActualFluid();
	}

	public int getAccelerantCapacity() {
		return accel.getCapacity();
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		Fluid fluid = resource.getFluid();
		if (!this.canFill(from, fluid))
			return 0;
		if (fluid.equals(FluidRegistry.WATER)) {
			return water.fill(resource, doFill);
		}
		if (fluid.equals(FluidRegistry.getFluid("rc jet fuel"))) {
			return fuel.fill(resource, doFill);
		}
		if (fluid.equals(FluidRegistry.getFluid("rc oxygen"))) {
			return accel.fill(resource, doFill);
		}
		if (fluid.equals(FluidRegistry.getFluid("oxygen"))) {
			return accel.fill(resource, doFill);
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
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if (fluid.equals(FluidRegistry.WATER)) {
			return from.offsetY == 0;
		}
		if (fluid.equals(FluidRegistry.getFluid("rc jet fuel"))) {
			return from.offsetY == 0;
		}
		if (fluid.equals(FluidRegistry.getFluid("rc oxygen"))) {
			return from.offsetY == 0;
		}
		if (fluid.equals(FluidRegistry.getFluid("oxygen"))) {
			return from.offsetY == 0;
		}
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{water.getInfo(), fuel.getInfo(), accel.getInfo()};
	}

	public int getWater() {
		return water.getLevel();
	}

	public int getFuel() {
		return fuel.getLevel();
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return side.offsetY == 0 ? Flow.INPUT : Flow.NONE;
	}

	public void addFuel(int amt) {
		fuel.addLiquid(amt, FluidRegistry.getFluid("rc jet fuel"));
	}

	public void addWater(int amt) {
		water.addLiquid(amt, FluidRegistry.WATER);
	}

	@Override
	public int getOperationTime() {
		return 20;
	}

	@Override
	public boolean areConditionsMet() {
		return this.getRecipe() != null && !fuel.isEmpty();
	}

	@Override
	public String getOperationalStatus() {
		return fuel.isEmpty() ? "No Fuel" : this.areConditionsMet() ? "Operational" : "Invalid or Missing Items";
	}

	public void removeFuel(int amt) {
		fuel.removeLiquid(amt);
	}

	@Override
	public boolean canBeCooledWithFins() {
		return true;
	}

	@Override
	public boolean allowHeatExtraction() {
		return true;
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
		return MAXTEMP;
	}

	@Override
	public void onApplyTemperature(World world, int x, int y, int z, int temperature) {
		if (world.getBlock(x, y, z) == Blocks.fire)
			RotaryAchievements.PULSEFIRE.triggerAchievement(this.getPlacer());
	}
}
