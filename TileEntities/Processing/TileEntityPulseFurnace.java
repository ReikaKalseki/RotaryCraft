/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Processing;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Auxiliary.ItemMaterialController;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.ItemMaterial;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesPulseFurnace;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityPulseFurnace extends InventoriedPowerReceiver implements TemperatureTE, PipeConnector, IFluidHandler, DiscreteFunction, ConditionalOperation {

	/** The number of ticks that the current item has been cooking for */
	public int pulseFurnaceCookTime;

	public static final int CAPACITY = 3000;
	public static final int MAXFUEL = 8000;
	public static final int MAXTEMP = 1000; //1370C = melting steel, 800C = 90% strength loss
	public static final int SMELTTICKS = 100;

	public boolean idle = false;

	public int temperature;	//damage player, start fires, etc


	private int tickcount2 = 0;
	public int smelttick = 0;

	private int soundtick = 2000;

	boolean flag2 = false;

	private HybridTank fuel = new HybridTank("fuel", MAXFUEL);
	private HybridTank water = new HybridTank("water", CAPACITY);
	private HybridTank accel = new HybridTank("accel", MAXFUEL);

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i == 2;
	}

	public void testIdle() {
		idle = (!this.canSmelt() && omega > MINSPEED);
	}

	public int getSizeInventory() {
		return 3;
	}

	public static boolean func_52005_b(ItemStack par0ItemStack)
	{
		return true;
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		pulseFurnaceCookTime = NBT.getShort("CookTime");

		water.readFromNBT(NBT);
		fuel.readFromNBT(NBT);

		temperature = NBT.getInteger("temp");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setShort("CookTime", (short)pulseFurnaceCookTime);

		water.writeToNBT(NBT);
		fuel.writeToNBT(NBT);

		NBT.setInteger("temp", temperature);
	}

	/**
	 * Returns an integer between 0 and the passed value representing how close the current item is to being completely
	 * cooked
	 */
	public int getCookProgressScaled(int par1)
	{
		return (pulseFurnaceCookTime * par1) / 20;
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
		return (smelttick * par1) / SMELTTICKS;
	}

	private void getFuel(World world, int x, int y, int z, int meta) {
		if (tickcount2 >= 100) {
			fuel.removeLiquid(100);
			tickcount2 = 0;
		}
	}

	public void heatAmbient(World world, int x, int y, int z, int meta) {
		if (fuel.getLevel() > 0 && this.canHeatUp())
			temperature += ReikaMathLibrary.extrema((MAXTEMP-temperature)/8, 4, "max");

		if (water.getLevel() > 0) {
			if (rand.nextInt(3) == 0) {
				int rem = (temperature*2/MAXTEMP)*50;
				if (rem > 0)
					water.removeLiquid(rem);
			}
			temperature -= temperature/64;
		}
		if (temperature < 0)
			temperature = 0;
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		if (biome == BiomeGenBase.frozenOcean || biome == BiomeGenBase.frozenRiver ||
				biome == BiomeGenBase.iceMountains || biome == BiomeGenBase.icePlains ||
				biome == BiomeGenBase.taiga || biome == BiomeGenBase.taigaHills)
			temperature -= 4;
		else if (biome == BiomeGenBase.desert || biome == BiomeGenBase.desertHills ||
				biome == BiomeGenBase.jungle || biome == BiomeGenBase.jungleHills)
			temperature -= 1;
		else if (biome != BiomeGenBase.hell) //do not cool in the nether
			temperature -= 2;
		if (temperature < Tamb)
			temperature = Tamb;
	}

	private boolean canHeatUp() {
		return power >= MINPOWER && omega >= MINSPEED && !fuel.isEmpty();
	}

	public void smeltHeat() {
		//	this.temperature += 10*melttemp/980;	//980 kJ per degree kelvin
	}

	public void updateTemperature(World world, int x, int y, int z, int meta) {
		if (temperature > 915) {
			RotaryCraft.logger.warn("WARNING: "+this+" is reaching very high temperature!");
			world.spawnParticle("lava", x+rand.nextFloat(), y+rand.nextFloat(), z+rand.nextFloat(), 0, 0, 0);
		}
		ReikaWorldHelper.temperatureEnvironment(world, x, y, z, temperature);
		if (temperature > MAXTEMP) {
			this.overheat(world, x, y, z);
		}

	}

	public int getReqTemps(ItemStack is) {
		if (is == null)
			return -1;
		if (is.itemID == Item.ingotIron.itemID)
			return 900; //steelmaking
		if (ItemMaterialController.instance.getMaterial(is) == ItemMaterial.OBSIDIAN)
			return ItemMaterialController.instance.getMeltingPoint(is);
		return ItemMaterialController.instance.getMeltingPoint(is)/2;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.testIdle();
		soundtick++;
		boolean flag1 = false;
		int reqtemp = this.getReqTemps(inv[0]);
		if (tickcount >= 20) {
			this.heatAmbient(world, x, y, z, meta);
			this.updateTemperature(world, x, y, z, meta);
			tickcount = 0;
		}
		if (soundtick >= 18 && this.canHeatUp()) {
			soundtick = 0;
			SoundRegistry.PULSEJET.playSoundAtBlock(world, x, y, z, 1, 1);
		}
		boolean canprocess = false;
		if (this.canSmelt()) {
			canprocess = true;
			if (!flag2)
				this.getFuel(world, x, y, z, meta);
		}

		tickcount++;
		tickcount2++;

		int tick = 1;
		if (!fuel.isEmpty() && power > 0 && omega >= MINSPEED && accel.getLevel() > 10) {
			tick = 4;
			accel.removeLiquid(10);
			if (canprocess && rand.nextInt(4) == 0)
				temperature += 1;
		}

		if (temperature >= reqtemp && reqtemp != -1 && this.canSmelt()) {
			smelttick += tick;
			if (temperature >= 900) //2x speed if running uncooled
				smelttick += tick;
			if (temperature >= 950) //4x speed if running uncooled and very hot
				smelttick += tick*2;
			if (temperature >= 980) //8x speed if about to fail
				smelttick += tick*4;
		}
		else
			smelttick = 0;
		if (smelttick < SMELTTICKS && !flag2)
			return;
		flag2 = true;
		smelttick = 0;
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d  %d  %d", this.power, this.omega, this.torque));
		if (!worldObj.isRemote) {
			flag1 = true;
			if (this.canSmelt()) {
				pulseFurnaceCookTime += tick;
				//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", ReikaMathLibrary.extrema(2, 600-this.omega, "max")));
				if (pulseFurnaceCookTime >= this.getOperationTime()) {
					pulseFurnaceCookTime = 0;
					this.smeltItem();
					flag1 = true;
					//flag2 = false;
				}
			}
			else
				pulseFurnaceCookTime = 0;
		}
		if (flag1)
			this.onInventoryChanged();
	}

	/** Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc. */
	private boolean canSmelt() {
		this.getPowerBelow();
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", power));
		if (power <= 0 || omega < MINSPEED)
			return false;
		if (inv[0] == null)
			return false;
		if (fuel.isEmpty())
			return false;

		int mintemp = this.getReqTemps(inv[0]);
		if (mintemp == -1 || mintemp > temperature)
			return false;

		ItemStack itemstack = RecipesPulseFurnace.smelting().getSmeltingResult(inv[0]);
		if (itemstack == null)
			return false;
		if (inv[2] == null)
			return true;
		if (!inv[2].isItemEqual(itemstack))
			return false;
		if (inv[2].stackSize < this.getInventoryStackLimit() && inv[2].stackSize < inv[2].getMaxStackSize())
			return true;
		return inv[2].stackSize < itemstack.getMaxStackSize();
	}

	/** Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack */
	public void smeltItem() {
		if (!this.canSmelt())
			return;
		flag2 = false;
		this.smeltHeat();
		ItemStack itemstack = RecipesPulseFurnace.smelting().getSmeltingResult(inv[0]);
		if (inv[2] == null)
			inv[2] = itemstack.copy();
		else if (inv[2].itemID == itemstack.itemID)
			inv[2].stackSize += itemstack.stackSize;

		inv[0].stackSize--;
		if (inv[0].stackSize <= 0)
			inv[0] = null;
	}/*

	private void smeltScrap() {
		int size = 1;
		if (inv[0].itemID == ItemStacks.scrap.itemID && inv[0].getItemDamage() == ItemStacks.scrap.getItemDamage())
			size = 9;
		inv[0].stackSize -= size;
		ItemStack i = this.getCraftedScrapIngot();
		ReikaInventoryHelper.addOrSetStack(i.itemID, 1, i.getItemDamage(), inv, 2);
		if (inv[0].stackSize <= 0)
			inv[0] = null;
		RotaryAchievements.RECYCLE.triggerAchievement(this.getPlacer());
	}*/

	private ItemStack getCraftedScrapIngot() {
		if (inv[0].itemID == ItemStacks.scrap.itemID && inv[0].getItemDamage() == ItemStacks.scrap.getItemDamage())
			return ItemStacks.steelingot;
		if (inv[0].itemID == ItemStacks.ironscrap.itemID && inv[0].getItemDamage() == ItemStacks.ironscrap.getItemDamage())
			return new ItemStack(Item.ingotIron);
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
		return RecipesPulseFurnace.smelting().getSmeltingResult(is) != null;
	}

	@Override
	public int getThermalDamage() {
		return (temperature)/100;
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.canSmelt())
			return 15;
		return 0;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.FUELLINE || m == MachineRegistry.PIPE;
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
		ReikaWorldHelper.overheat(world, x, y, z, ItemStacks.scrap.itemID, ItemStacks.scrap.getItemDamage(), 0, 17, true, 1.5F, false, ConfigRegistry.BLOCKDAMAGE.getState(), 12F);
	}

	public int getAccelerant() {
		return accel.getLevel();
	}

	public Fluid getAccelerantType() {
		return accel.getActualFluid();
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		Fluid fluid = resource.getFluid();
		if (!this.canFill(from, fluid))
			return 0;
		if (fluid.equals(FluidRegistry.WATER)) {
			return water.fill(resource, doFill);
		}
		if (fluid.equals(FluidRegistry.getFluid("jet fuel"))) {
			return fuel.fill(resource, doFill);
		}
		if (fluid.equals(FluidRegistry.getFluid("rc oxygen"))) {
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
		if (fluid.equals(FluidRegistry.getFluid("jet fuel"))) {
			return from.offsetY == 0;
		}
		if (fluid.equals(FluidRegistry.getFluid("rc oxygen"))) {
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
		return new FluidTankInfo[]{water.getInfo(), fuel.getInfo()};
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
		fuel.addLiquid(amt, FluidRegistry.getFluid("jet fuel"));
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
		return this.canSmelt() && !fuel.isEmpty();
	}

	@Override
	public String getOperationalStatus() {
		return fuel.isEmpty() ? "No Fuel" : this.areConditionsMet() ? "Operational" : "Invalid or Missing Items";
	}

	public void removeFuel(int amt) {
		fuel.removeLiquid(amt);
	}
}
