/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Processing;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.FrictionHeatable;
import Reika.RotaryCraft.Auxiliary.Interfaces.MultiOperational;
import Reika.RotaryCraft.Auxiliary.Interfaces.PressureTE;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesCompactor;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityCompactor extends InventoriedPowerReceiver implements TemperatureTE, PressureTE, FrictionHeatable,
MultiOperational, ConditionalOperation {

	/** The number of ticks that the current item has been cooking for */
	public int compactorCookTime;

	public static final int MAXTEMP = 1000;
	public static final int MAXPRESSURE = 600000; //All pressures in kPa, steel yield strength = 250Mpa
	// public static final int MAXTIME = 2000;
	public static final int REQTEMP = 800; 		//real temp/2
	public static final int REQPRESS = 550000; //real pressure

	private int pressure;
	public int temperature;

	public boolean idle = false;
	private boolean animdir = false;

	private int envirotick = 0;
	private int tempTick;

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i == 4;
	}

	public void testIdle() {
		boolean ingred = false;
		boolean invalid = false;
		for (int i = 0; i < 4; i++) {
			if (inv[i] == null)
				invalid = true;
		}
		if (!invalid) {
			Item id = inv[0].getItem();
			int dmg = inv[0].getItemDamage();
			for (int i = 1; i < 4; i++) {
				if (inv[i].getItem() != id || inv[i].getItemDamage() != dmg)
					invalid = true;
			}
		}
		if (!invalid) {
			if (RecipesCompactor.getRecipes().isCompactable(inv[0]))
				ingred = true;
		}
		boolean full = true;
		if (inv[4] == null)
			full = false;
		else if (inv[4].stackSize < inv[4].getMaxStackSize())
			full = false;
		idle = (!ingred || full);
	}

	public boolean getIOSides(World world, int x, int y, int z, int metadata) {
		switch (metadata) {
			case 0:
				read = ForgeDirection.EAST;
				break;
			case 1:
				read = ForgeDirection.WEST;
				break;
			case 2:
				read = ForgeDirection.SOUTH;
				break;
			case 3:
				read = ForgeDirection.NORTH;
				break;
		}
		//ReikaWorldHelper.legacySetBlockWithNotify(world, powinx, y, powinz, 4);
		return true;
	}

	public void readPower() {
		if (!this.getIOSides(worldObj, xCoord, yCoord, zCoord, this.getBlockMetadata()))
			return;
		super.getPower(false);
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", ReikaMathLibrary.extrema(2, 1200-this.omega, "max")));
		return;
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory()
	{
		return 5;
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		compactorCookTime = NBT.getShort("CookTime");
		temperature = NBT.getInteger("temperature");
		pressure = NBT.getInteger("pressure");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setShort("CookTime", (short)compactorCookTime);
		NBT.setInteger("temperature", temperature);
		NBT.setInteger("pressure", pressure);
	}

	/**
	 * Returns an integer between 0 and the passed value representing how close the current item is to being completely
	 * cooked
	 */
	public int getCookProgressScaled(int par1)
	{
		int time = this.getOperationTime();
		return time > 0 ? (compactorCookTime * par1) / time : 0;
	}

	public int getPressureScaled(int par1)
	{
		return (pressure * par1) / MAXPRESSURE;
	}

	public int getTemperatureScaled(int par1)
	{
		return (temperature * par1) / MAXTEMP;
	}

	public int compressTime() {
		ItemStack is1 = inv[0];
		ItemStack is2 = inv[1];
		ItemStack is3 = inv[2];
		ItemStack is4 = inv[3];

		if (is1 == null || is2 == null || is3 == null || is4 == null)
			return -1;/*
    	if (!is1.equals(is2))
    		return -1;
    	if (!is1.equals(is3))
    		return -1;
    	if (!is1.equals(is4))
    		return -1;*/

		Item item = is1.getItem();
		int meta = is1.getItemDamage();
		if (item != ItemRegistry.COMPACTS.getItemInstance() && item != Items.coal)
			return -1;

		if (item == Items.coal)
			return 80;
		switch(meta) {
			case 0:
				return 160;
			case 1:
				return 320;
			case 2:
				return 640;
			default:
				return -1;
		}
	}

	public void updatePressure(World world, int x, int y, int z, int meta) {
		int Pamb = 101;
		if (world.getBiomeGenForCoords(x, z) == BiomeGenBase.hell)
			Pamb = 20000;
		if (y < 30)
			Pamb *= 1.25;
		if (y > 128)
			Pamb *= 0.8;
		if (y > 192)
			Pamb *= 0.8; //Makes a collective *0.64
		int waterpressure = 10*ReikaWorldHelper.getDepth(world, x, y, z, "water"); //10kPa per meter
		if (waterpressure != -10)
			Pamb += waterpressure;
		int lavapressure = 27*ReikaWorldHelper.getDepth(world, x, y, z, "lava"); //27kPa per meter
		if (lavapressure != -27)
			Pamb += lavapressure;

		if (pressure > Pamb && world.getBiomeGenForCoords(x, z) != BiomeGenBase.hell)
			pressure -= ReikaMathLibrary.extrema((pressure-Pamb)/200, 1, "max");
		if (pressure > Pamb && world.getBiomeGenForCoords(x, z) == BiomeGenBase.hell)
			pressure -= ReikaMathLibrary.extrema((pressure-Pamb)/600, 1, "max");
		if (pressure < Pamb)
			pressure += ReikaMathLibrary.extrema((Pamb-pressure)/40, 1, "max");

		if (omega > 0) {
			pressure += 128*ReikaMathLibrary.logbase(torque, 2);
		}

		if (pressure >= 0.8*MAXPRESSURE) {
			RotaryCraft.logger.warn("WARNING: "+this+" is reaching very high pressure!");
		}

		if (pressure > MAXPRESSURE) {
			this.overpressure(world, x, y, z);
		}
	}

	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		if (temperature > Tamb)
			temperature -= ReikaMathLibrary.extrema((temperature-Tamb)/200, 1, "max");
		if (temperature < Tamb)
			temperature += ReikaMathLibrary.extrema((Tamb-temperature)/40, 1, "max");

		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", 0));

		if (RotaryAux.isNextToLava(world, x, y, z))
			temperature += 4;
		if (RotaryAux.isNextToFire(world, x, y, z))
			temperature += 2;
		if (Tamb == 300)	//Fire is 50% hotter in the nether
			temperature++;

		ForgeDirection a = ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.water);
		if (a != null && temperature > 600) {
			temperature--;
			if (rand.nextInt(4000) == 0)
				ReikaWorldHelper.changeAdjBlock(world, x, y, z, a, Blocks.air, 0);
		}
		ForgeDirection iceside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.ice);
		if (iceside != null && temperature > 0) {
			temperature -= 2;
			if (rand.nextInt(200) == 0)
				ReikaWorldHelper.changeAdjBlock(world, x, y, z, iceside, Blocks.flowing_water, 0);
		}
		ForgeDirection snowside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.snow);
		if (snowside != null && temperature > -5) {
			temperature -= 2;
			if (rand.nextInt(100) == 0)
				ReikaWorldHelper.changeAdjBlock(world, x, y, z, iceside, Blocks.flowing_water, 0);
		}
		ReikaWorldHelper.temperatureEnvironment(world, x, y, z, temperature);

		if (temperature >= 0.9*MAXTEMP) {
			RotaryCraft.logger.warn("WARNING: "+this+" is reaching very high temperature!");
		}

		if (temperature > MAXTEMP) {
			this.overheat(world, x, y, z);
			temperature = MAXTEMP;
		}
	}

	public void overheat(World world, int x, int y, int z) {
		temperature = MAXTEMP;
		ReikaWorldHelper.overheat(world, x, y, z, ItemStacks.scrap, 0, 17, true, 1F, false, ConfigRegistry.BLOCKDAMAGE.getState(), 2F);
		world.setBlockToAir(x, y, z);
	}

	public int getStage() {
		if (inv[0] == null)
			return 1;
		if (!RecipesCompactor.getRecipes().isCompactable(inv[0]))
			return 1;
		if (inv[0].getItem() == Items.coal)
			return 1;
		if (ReikaItemHelper.matchStacks(ItemStacks.anthracite, inv[0]))
			return 2;
		if (ReikaItemHelper.matchStacks(ItemStacks.prismane, inv[0]))
			return 3;
		if (ReikaItemHelper.matchStacks(ItemStacks.lonsda, inv[0]))
			return 4;
		return 1;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false);
		if (envirotick >= 20) {
			this.updatePressure(world, x, y, z, meta);
			if (tempTick == 0)
				this.updateTemperature(world, x, y, z, meta);
			envirotick = 0;
		}
		this.testIdle();
		boolean flag1 = false;
		envirotick++;
		if (tempTick > 0)
			tempTick--;
		tickcount++;
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d  %d  %d", this.power, this.omega, this.torque));
		if (!world.isRemote) {
			int n = this.getNumberConsecutiveOperations();
			for (int i = 0; i < n; i++)
				flag1 |= this.doOperation(n > 1);
		}
		if (flag1)
			this.markDirty();
	}

	private boolean doOperation(boolean multiple) {
		if (this.canSmelt()) {
			compactorCookTime++;
			//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", ReikaMathLibrary.extrema(2, 600-this.omega, "max")));
			if (compactorCookTime >= this.getOperationTime()) {
				compactorCookTime = 0;
				this.smeltItem();
			}
			return true;
		}
		else {
			compactorCookTime = 0;
			return false;
		}
	}

	/**
	 * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
	 */
	private boolean canSmelt()
	{
		this.readPower();
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", power));
		if (power < MINPOWER || torque < MINTORQUE)
			return false;

		for (int i = 0; i < 4; i++)
			if (inv[i] == null)
				return false;

		if (inv[0].getItem() != inv[1].getItem())
			return false;
		if (inv[0].getItem() != inv[2].getItem())
			return false;
		if (inv[0].getItem() != inv[3].getItem())
			return false;
		if (inv[0].getItemDamage() != inv[1].getItemDamage())
			return false;
		if (inv[0].getItemDamage() != inv[2].getItemDamage())
			return false;
		if (inv[0].getItemDamage() != inv[3].getItemDamage())
			return false;

		if (pressure < RecipesCompactor.getRecipes().getReqPressure(inv[0]) || temperature < RecipesCompactor.getRecipes().getReqTemperature(inv[0]))
			return false;

		ItemStack itemstack = RecipesCompactor.getRecipes().getCompactingResult(inv[0]);
		if (itemstack == null)
			return false;
		if (inv[4] != null) {
			if (!inv[4].isItemEqual(itemstack))
				return false;
			if (inv[4].stackSize >= itemstack.getMaxStackSize())
				return false;
		}
		if (inv[4] == null)
			return true;
		if (inv[4].stackSize < this.getInventoryStackLimit() && inv[4].stackSize < inv[4].getMaxStackSize())
			return true;
		return false;
	}

	/**
	 * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
	 */
	public void smeltItem()
	{
		if (!this.canSmelt())
			return;
		ItemStack itemstack = RecipesCompactor.getRecipes().getCompactingResult(inv[0]);
		if (inv[4] == null)
			inv[4] = itemstack.copy();
		else if (inv[4].getItem() == itemstack.getItem())
			inv[4].stackSize += itemstack.stackSize;

		for (int i = 0; i < 4; i++) {
			//if (inv[i].getItem().func_46056_k())
			//    inv[i] = new ItemStack(inv[i].getItem().setFull3D());
			// else
			inv[i].stackSize--;

			if (inv[i].stackSize <= 0)
				inv[i] = null;
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (phi < 0.5F)
			phi = 1F;
		if (!this.isInWorld()) {
			//this.phi = 1;
			return;
		}
		if (power < MINPOWER || torque < MINTORQUE)
			return;
		if (phi >= 1.5F || phi <= 0.5F)
			if (rand.nextInt(40) > 0)
				return;
		if (animdir)
			phi += 0.03125F;
		else
			phi -= 0.03125F;
		if (phi >= 1.5F || phi <= 0.5F) {
			if (animdir)
				animdir = false;
			else
				animdir = true;
		}
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.COMPACTOR;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		if (slot == 4)
			return false;
		if (is.getItem() == Items.coal || (ItemRegistry.COMPACTS.matchItem(is) && is.getItemDamage() <= ItemStacks.lonsda.getItemDamage()))
			return true;
		return RecipesCompactor.getRecipes().isCompactable(is);
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
	public void addTemperature(int temp) {
		temperature += temp;
	}

	@Override
	public int getTemperature() {
		return temperature;
	}

	@Override
	public void addPressure(int press) {
		pressure += press;
	}

	@Override
	public int getPressure() {
		return pressure;
	}

	@Override
	public void overpressure(World world, int x, int y, int z) {
		world.createExplosion(null, x, y, z, 4, ConfigRegistry.BLOCKDAMAGE.getState());
		pressure = MAXPRESSURE;
	}

	@Override
	public int getOperationTime() {
		return DurationRegistry.COMPACTOR.getOperationTime(omega, this.getStage()-1);
	}

	@Override
	public int getNumberConsecutiveOperations() {
		return DurationRegistry.COMPACTOR.getNumberOperations(omega, this.getStage()-1);
	}

	@Override
	public boolean areConditionsMet() {
		return this.canSmelt();
	}

	@Override
	public String getOperationalStatus() {
		if (inv[0] == null)
			return "Missing Items";
		if (temperature < RecipesCompactor.getRecipes().getReqTemperature(inv[0]))
			return "Insufficient Temperature";
		if (pressure < RecipesCompactor.getRecipes().getReqPressure(inv[0]))
			return "Insufficient Pressure";
		return this.areConditionsMet() ? "Operational" : "Invalid or Missing Items";
	}

	@Override
	public void setTemperature(int T) {
		temperature = T;
	}

	@Override
	public int getMaxTemperature() {
		return MAXTEMP;
	}

	@Override
	public boolean canBeCooledWithFins() {
		return true;
	}

	@Override
	public int getMaxPressure() {
		return MAXPRESSURE;
	}

	@Override
	public void resetAmbientTemperatureTimer() {
		tempTick = 5;
	}

	@Override
	public float getMultiplier() {
		return 0.75F;
	}

	@Override
	public void onOverheat(World world, int x, int y, int z) {

	}

	@Override
	public boolean canBeFrictionHeated() {
		return true;
	}

	@Override
	public boolean allowExternalHeating() {
		return true;
	}
}
