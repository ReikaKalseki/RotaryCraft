/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Processing;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesPulseFurnace;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityPulseFurnace extends InventoriedPowerReceiver implements TemperatureTE, PipeConnector, IFluidHandler, DiscreteFunction {

	private ItemStack inv[] = new ItemStack[3];

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

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i == 2;
	}

	public void testIdle() {
		idle = (!this.canSmelt() && omega > MINSPEED);
	}

	public int getSmeltNumber(ItemStack is) {
		int num = is.stackSize;
		if (is.itemID != RotaryCraft.shaftcraft.itemID || is.getItemDamage() != 1) //if not making steel
			return 1;/*
	    	int a = par5Random.nextInt(2);
	    	int b = par5Random.nextInt(2);
	    	if (a == 0 && b == 0)
	    		return 1;
	    	if (num > 62)
	    		return 1;
	    	else
	    		return 2;*/
		return ReikaInventoryHelper.addUpToStack(is, 1, 5);
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory()
	{
		return inv.length;
	}

	public static boolean func_52005_b(ItemStack par0ItemStack)
	{
		return true;
	}

	/**
	 * Returns the stack in slot i
	 */
	public ItemStack getStackInSlot(int par1)
	{
		return inv[par1];
	}

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		inv[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		NBTTagList nbttaglist = NBT.getTagList("Items");
		inv = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < inv.length)
			{
				inv[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}

		pulseFurnaceCookTime = NBT.getShort("CookTime");

		water.readFromNBT(NBT);
		fuel.readFromNBT(NBT);

		temperature = NBT.getInteger("temp");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setShort("CookTime", (short)pulseFurnaceCookTime);

		water.writeToNBT(NBT);
		fuel.writeToNBT(NBT);

		NBT.setInteger("temp", temperature);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inv.length; i++)
		{
			if (inv[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				inv[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		NBT.setTag("Items", nbttaglist);
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

	public void getFuel(World world, int x, int y, int z, int meta) {
		if (tickcount2 >= 100) {
			fuel.removeLiquid(100);
			tickcount2 = 0;
		}
	}

	public void heatAmbient(World world, int x, int y, int z, int meta) {
		if (fuel.getLevel() > 0 && this.canSmelt())
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
		int Tamb = ReikaWorldHelper.getBiomeTemp(biome);
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
			tickcount = 0;
		}
		if (this.canSmelt()) {
			if (soundtick >= 18 && temperature > reqtemp && reqtemp != -1) {
				soundtick = 0;
				SoundRegistry.PULSEJET.playSoundAtBlock(world, x, y, z, 1, 1);
			}
			if (!flag2)
				this.getFuel(world, x, y, z, meta);
			this.updateTemperature(world, x, y, z, meta);
		}

		tickcount++;
		tickcount2++;
		if (temperature > reqtemp && reqtemp != -1)
			smelttick++;
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
				pulseFurnaceCookTime++;
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
		if (this.hasScrap()) {
			if (inv[0].stackSize < 9 && inv[0].getItemDamage() == ItemStacks.scrap.getItemDamage() && inv[0].itemID == ItemStacks.scrap.itemID)
				return false;
			if (inv[2] != null) {
				if (inv[2].itemID != this.getCraftedScrapIngot().itemID || inv[2].getItemDamage() != this.getCraftedScrapIngot().getItemDamage())
					return false;
				if (inv[2].stackSize >= this.getInventoryStackLimit() || inv[2].stackSize >= inv[2].getMaxStackSize())
					return false;
			}
			return true;
		}
		//if (this.fuelLevel <= 0)
		//return false;
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
		if (this.hasScrap()) {
			this.smeltScrap();
			return;
		}
		flag2 = false;
		this.smeltHeat();
		ItemStack itemstack = RecipesPulseFurnace.smelting().getSmeltingResult(inv[0]);
		if (inv[2] == null)
			inv[2] = itemstack.copy();
		else if (inv[2].itemID == itemstack.itemID)
			inv[2].stackSize += this.getSmeltNumber(inv[2]);
		//  if (inv[0].getItem().func_46056_k())
		//   inv[0] = new ItemStack(inv[0].getItem().setFull3D());
		//  else
		inv[0].stackSize--;
		if (inv[0].stackSize <= 0)
			inv[0] = null;
	}

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
	}

	private ItemStack getCraftedScrapIngot() {
		if (inv[0].itemID == ItemStacks.scrap.itemID && inv[0].getItemDamage() == ItemStacks.scrap.getItemDamage())
			return ItemStacks.steelingot;
		if (inv[0].itemID == ItemStacks.ironscrap.itemID && inv[0].getItemDamage() == ItemStacks.ironscrap.getItemDamage())
			return new ItemStack(Item.ingotIron);
		return null;
	}

	private boolean hasScrap() {
		if (inv[0].itemID == ItemStacks.scrap.itemID && inv[0].getItemDamage() == ItemStacks.scrap.getItemDamage()) {
			return true;
		}
		if ((inv[0].itemID == ItemStacks.ironscrap.itemID && inv[0].getItemDamage() == ItemStacks.ironscrap.getItemDamage())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.PULSEJET.ordinal();
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
}
