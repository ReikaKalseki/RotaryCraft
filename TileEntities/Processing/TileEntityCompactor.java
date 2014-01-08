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

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.PressureTE;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesCompactor;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityCompactor extends InventoriedPowerReceiver implements TemperatureTE, PressureTE
{
	private ItemStack inv[];

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

	public TileEntityCompactor()
	{
		inv = new ItemStack[5];
		compactorCookTime = 0;
	}

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
			int id = inv[0].itemID;
			int dmg = inv[0].getItemDamage();
			for (int i = 1; i < 4; i++) {
				if (inv[i].itemID != id || inv[i].getItemDamage() != dmg)
					invalid = true;
			}
		}
		if (!invalid) {
			List li = RecipesCompactor.getRecipes().getCompactables();
			for (int i = 0; i < li.size() && !ingred; i++) {
				if (ReikaItemHelper.listContainsItemStack(li, inv[0]))
					ingred = true;
			}
		}
		boolean full = true;
		if (inv[4] == null)
			full = false;
		else if (inv[4].stackSize < inv[4].getMaxStackSize())
			full = false;
		idle = (!ingred || full);
	}

	public boolean getReceptor(World world, int x, int y, int z, int metadata) {
		if (y == 0)
			return false;
		int id = 0;
		switch (metadata) {
		case 0:
			id = world.getBlockId(x+1, y, z);
			readx = x+1;
			readz = z;
			break;
		case 1:
			id = world.getBlockId(x-1, y, z);
			readx = x-1;
			readz = z;
			break;
		case 2:
			id = world.getBlockId(x, y, z+1);
			readx = x;
			readz = z+1;
			break;
		case 3:
			id = world.getBlockId(x, y, z-1);
			readx = x;
			readz = z-1;
			break;
		default:
			id = 0;
			break;
		}
		ready = yCoord;
		//ReikaWorldHelper.legacySetBlockWithNotify(world, powinx, y, powinz, 4);
		return true;
	}

	public void readPower() {
		if (!this.getReceptor(worldObj, xCoord, yCoord, zCoord, this.getBlockMetadata()))
			return;
		super.getPower(false, false);
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", ReikaMathLibrary.extrema(2, 1200-this.omega, "max")));
		return;
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory()
	{
		return inv.length;
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

		compactorCookTime = NBT.getShort("CookTime");
		temperature = NBT.getInteger("temperature");
		pressure = NBT.getInteger("pressure");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setShort("CookTime", (short)compactorCookTime);
		NBT.setInteger("temperature", temperature);
		NBT.setInteger("pressure", pressure);
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
		if (this.compressTime() != -1)
			return (compactorCookTime * par1) / this.operationTime(omega, this.getStage());
		else
			return 0;
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

		int item = is1.itemID;
		int meta = is1.getItemDamage();
		if (item != RotaryCraft.compacts.itemID && item != Item.coal.itemID)
			return -1;

		if (item == Item.coal.itemID)
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
			pressure += power/(1500*omega); //not direct this.torque since need omega > 0
		}

		if (pressure >= 0.8*MAXPRESSURE) {
			RotaryCraft.logger.warn("WARNING: "+this+" is reaching very high pressure!");
		}

		if (pressure > MAXPRESSURE) {
			this.overpressure(world, x, y, z);
		}
	}

	public void updateTemperature(World world, int x, int y, int z, int meta) {
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		int Tamb = ReikaWorldHelper.getBiomeTemp(biome);
		Tamb = 25;
		if (temperature > Tamb)
			temperature -= ReikaMathLibrary.extrema((temperature-Tamb)/200, 1, "max");
		if (temperature < Tamb)
			temperature += ReikaMathLibrary.extrema((Tamb-temperature)/40, 1, "max");

		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", 0));

		if (ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.lava) != null)
			temperature += 4;
		if (ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.fire) != null)
			temperature += 2;
		if (Tamb == 300)	//Fire is 50% hotter in the nether
			temperature++;

		ForgeDirection a = ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.water);
		if (a != null && temperature > 600) {
			temperature--;
			if (rand.nextInt(4000) == 0)
				ReikaWorldHelper.changeAdjBlock(world, x, y, z, a, 0, 0);
		}
		ForgeDirection iceside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Block.ice.blockID);
		if (iceside != null && temperature > 0) {
			temperature -= 2;
			if (rand.nextInt(200) == 0)
				ReikaWorldHelper.changeAdjBlock(world, x, y, z, iceside, Block.waterMoving.blockID, 0);
		}
		ForgeDirection snowside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Block.blockSnow.blockID);
		if (snowside != null && temperature > -5) {
			temperature -= 2;
			if (rand.nextInt(100) == 0)
				ReikaWorldHelper.changeAdjBlock(world, x, y, z, iceside, Block.waterMoving.blockID, 0);
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
		ReikaWorldHelper.overheat(world, x, y, z, ItemStacks.scrap.itemID, ItemStacks.scrap.getItemDamage(), 0, 17, true, 1F, false, ConfigRegistry.BLOCKDAMAGE.getState(), 2F);
		world.setBlock(x, y, z, 0);
	}

	public int getStage() {
		if (inv[0] == null)
			return -1;
		if (!RecipesCompactor.getRecipes().isCompactable(inv[0]))
			return -1;
		if (inv[0].itemID == Item.coal.itemID)
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
		this.getPower(false, false);
		if (envirotick >= 20) {
			this.updatePressure(world, x, y, z, meta);
			this.updateTemperature(world, x, y, z, meta);
			envirotick = 0;
		}
		this.testIdle();
		boolean flag1 = false;
		envirotick++;
		tickcount++;
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d  %d  %d", this.power, this.omega, this.torque));
		if (!world.isRemote)
		{
			if (this.canSmelt())
				flag1 = true;
			if (this.canSmelt()) {
				compactorCookTime++;
				//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", ReikaMathLibrary.extrema(2, 600-this.omega, "max")));
				if (this.operationComplete(compactorCookTime, this.getStage())) {
					compactorCookTime = 0;
					this.smeltItem();
					flag1 = true;
				}
			}
			else
				compactorCookTime = 0;
		}
		if (flag1)
			this.onInventoryChanged();
	}

	/**
	 * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
	 */
	private boolean canSmelt()
	{
		this.readPower();
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", power));
		if (!(power >= MINPOWER/(5-this.getStage()) && torque >= MINTORQUE))
			return false;

		if (pressure < REQPRESS || temperature < REQTEMP)
			return false;

		for (int i = 0; i < 4; i++)
			if (inv[i] == null)
				return false;

		if (inv[0].itemID != inv[1].itemID)
			return false;
		if (inv[0].itemID != inv[2].itemID)
			return false;
		if (inv[0].itemID != inv[3].itemID)
			return false;
		if (inv[0].getItemDamage() != inv[1].getItemDamage())
			return false;
		if (inv[0].getItemDamage() != inv[2].getItemDamage())
			return false;
		if (inv[0].getItemDamage() != inv[3].getItemDamage())
			return false;

		ItemStack itemstack = RecipesCompactor.getRecipes().getSmeltingResult(inv[0]);
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
		ItemStack itemstack = RecipesCompactor.getRecipes().getSmeltingResult(inv[0]);
		if (inv[4] == null)
			inv[4] = itemstack.copy();
		else if (inv[4].itemID == itemstack.itemID)
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
	public void animateWithTick(World world, int x, int y, int z) {
		if (phi < 0.5F)
			phi = 1F;
		if (!this.isInWorld()) {
			//this.phi = 1;
			return;
		}
		if (!worldObj.isRemote) {
			if (power < MINPOWER || torque < MINTORQUE)
				return;
		}
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
	public int getMachineIndex() {
		return MachineRegistry.COMPACTOR.ordinal();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		if (slot == 4)
			return false;
		return (is.itemID == Item.coal.itemID || is.itemID == RotaryCraft.compacts.itemID);
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
}
