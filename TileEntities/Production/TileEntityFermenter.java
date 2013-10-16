/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Production;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Auxiliary.ModList;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaTreeHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.DyeTrees.API.TreeGetter;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.TemperatureTE;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityLiquidInventoryReceiver;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PlantMaterials;

public class TileEntityFermenter extends TileEntityLiquidInventoryReceiver implements TemperatureTE
{

	/** The number of ticks that the current item has been cooking for */
	public int fermenterCookTime = 0;

	public ItemStack[] slots = new ItemStack[4];

	public static final int MINUSEFULTEMP = 20;
	public static final int OPTMULTIPLYTEMP = 25;
	public static final int MAXUSEFULTEMP = 40;
	public static final int OPTFERMENTTEMP = 35;
	public static final int MAXTEMP = 60;

	public static final int CAPACITY = 3000;
	public static final int CONSUME_WATER = 50;

	public int temperature;

	public boolean idle = false;

	private int temperaturetick = 0;

	@Override
	protected int getActiveTexture() {
		return (power >= MINPOWER && omega >= MINSPEED && this.canMake() ? 1 : 0);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i == 3;
	}

	public static List<ItemStack> getAllValidPlants() {
		List<ItemStack> in = new ArrayList();
		for (int i = 0; i < PlantMaterials.plantList.length; i++) {
			if (PlantMaterials.plantList[i] == PlantMaterials.SAPLING || PlantMaterials.plantList[i] == PlantMaterials.LEAVES) {
				for (int j = 0; j < ReikaTreeHelper.treeList.length; j++) {
					ItemStack icon = PlantMaterials.plantList[i] == PlantMaterials.SAPLING ? new ItemStack(Block.sapling, 1, j) : new ItemStack(Block.leaves, 1, j);
					in.add(icon);
				}
			}
			else {
				in.add(PlantMaterials.plantList[i].getPlantItem());
			}
		}
		for (int i = 0; i < ModWoodList.woodList.length; i++) {
			if (ModWoodList.woodList[i].exists()) {
				in.add(ModWoodList.woodList[i].getCorrespondingSapling());
				in.add(ModWoodList.woodList[i].getCorrespondingLeaf());
			}
		}
		if (ModList.DYETREES.isLoaded()) {
			for (int j = 0; j < 16; j++) {
				in.add(TreeGetter.getDyeSapling(j));
				in.add(TreeGetter.getDyeLeaf(j));
			}
		}
		return in;
	}

	// Return the itemstack product from the input items.
	private ItemStack getRecipe() {
		for (int i = 0; i < 3; i++)
			if (slots[i] == null && i != 1)
				return null;
		if (slots[0].itemID == Item.sugar.itemID) {
			if (this.hasWater())
				if(slots[2].itemID == Block.dirt.blockID)
					return new ItemStack(ItemRegistry.YEAST.getShiftedID(), 1, 0);
		}
		if (slots[0].itemID == ItemRegistry.YEAST.getShiftedID()) {
			if (this.getPlantValue(slots[2]) > 0)
				if (this.hasWater())
					return new ItemStack(ItemStacks.sludge.itemID, 1, ItemStacks.sludge.getItemDamage());
		}
		return null;
	}

	private boolean hasWater() {
		return !tank.isEmpty();
	}

	public static int getPlantValue(ItemStack is) {
		if (is == null)
			return 0;
		if (ModList.DYETREES.isLoaded()) {
			if (TreeGetter.isDyeSapling(is))
				return PlantMaterials.SAPLING.getPlantValue();
			if (TreeGetter.isDyeLeaf(is))
				return PlantMaterials.LEAVES.getPlantValue();
		}
		ModWoodList sap = ModWoodList.getModWoodFromSapling(is);
		if (sap != null) {
			return PlantMaterials.SAPLING.getPlantValue()*getModSaplingValue(sap);
		}
		ModWoodList leaf = ModWoodList.getModWoodFromLeaf(is);
		if (leaf != null) {
			return PlantMaterials.LEAVES.getPlantValue()*getModSaplingValue(leaf);
		}
		PlantMaterials plant = PlantMaterials.getPlantEntry(is);
		if (plant == null)
			return 0;
		return plant.getPlantValue();
	}

	public static int getModSaplingValue(ModWoodList wood) {
		if (wood == null)
			return 0;
		if (wood == ModWoodList.SILVERWOOD)
			return 32;
		ModList mod = wood.getParentMod();
		if (mod == ModList.THAUMCRAFT)
			return 4;
		if (mod == ModList.TWILIGHT)
			return 3;
		return 1;
	}

	private float getFermentRate() {
		boolean fermenting = true;
		if (this.getRecipe() == null)
			return -1F;
		if (this.getRecipe().itemID == ItemRegistry.YEAST.getShiftedID())
			fermenting = false;
		if (temperature < MINUSEFULTEMP)
			return 1F/(MINUSEFULTEMP-temperature);
		if (temperature > MAXUSEFULTEMP)
			return 1F/(temperature-MAXUSEFULTEMP);
		float Tdiff = temperature-OPTMULTIPLYTEMP;
		if (fermenting)
			Tdiff = temperature-OPTFERMENTTEMP;
		if (Tdiff < 0)
			Tdiff = -Tdiff;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(Tdiff));
		return (16F-(Tdiff));
	}

	public void testIdle() {
		idle = (this.getRecipe() == null);
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		temperaturetick++;
		tickcount++;
		this.getIOSidesDefault(world, x, y, z, meta);
		this.getPower(false, false);
		ItemStack product = this.getRecipe();
		if (temperaturetick >= 20) {
			temperaturetick = 0;
			this.updateTemperature(world, x, y, z, meta);
		}
		if (product == null) {
			idle = true;
			return;
		}
		if (product.itemID != ItemRegistry.YEAST.getShiftedID() && !ReikaItemHelper.matchStacks(product, ItemStacks.sludge))
			return;
		boolean red = world.isBlockIndirectlyGettingPowered(x, y, z);
		if (red) {
			if (product.itemID == ItemRegistry.YEAST.getShiftedID()) {
				//return;
			}
		}
		else {
			if (ReikaItemHelper.matchStacks(product, ItemStacks.sludge)) {
				//return;
			}
		}
		if (slots[3] != null) {
			if (product.itemID != slots[3].itemID) {
				fermenterCookTime = 0;
				return;
			}
		}
		idle = false;
		if (power < MINPOWER || omega < MINSPEED)
			return;
		if (tickcount >= 2+par5Random.nextInt(18)) {
			this.testYeastKill();
			tickcount = 0;
		}
		if (slots[3] != null) {
			if (slots[3].stackSize >= slots[3].getMaxStackSize()) {
				fermenterCookTime = 0;
				return;
			}
		}
		fermenterCookTime++;
		if (fermenterCookTime*this.getFermentRate() >= this.operationTime(omega, 0)) {
			this.make(product);
			fermenterCookTime = 0;
		}

	}

	private boolean canMake() {
		ItemStack product = this.getRecipe();
		if (product == null) {
			return false;
		}
		if (product.itemID != ItemRegistry.YEAST.getShiftedID() && (product.itemID != ItemStacks.sludge.itemID || product.getItemDamage() != ItemStacks.sludge.getItemDamage()))
			return false;
		if (slots[3] != null) {
			if (slots[3].stackSize >= slots[3].getMaxStackSize()) {
				return false;
			}
			if (product.itemID != slots[3].itemID) {
				return false;
			}
		}
		return true;
	}

	private void make(ItemStack product) {
		if (product.itemID == ItemRegistry.YEAST.getShiftedID()) {
			if (slots[3] == null)
				slots[3] = new ItemStack(ItemRegistry.YEAST.getShiftedID(), 1, 0);
			else if (slots[3].itemID == ItemRegistry.YEAST.getShiftedID()) {
				if (slots[3].stackSize < slots[3].getMaxStackSize())
					slots[3].stackSize++;
				else
					return;
			}
			else {
				fermenterCookTime = 0;
				return;
			}
			ReikaInventoryHelper.decrStack(0, slots);
			if (par5Random.nextInt(4) == 0)
				ReikaInventoryHelper.decrStack(2, slots);
		}
		if (product.itemID == ItemStacks.sludge.itemID && product.getItemDamage() == ItemStacks.sludge.getItemDamage()) {
			if (slots[3] == null)
				slots[3] = new ItemStack(ItemStacks.sludge.itemID, this.getPlantValue(slots[2]), ItemStacks.sludge.getItemDamage());
			else if (slots[3].itemID == ItemStacks.sludge.itemID && slots[3].getItemDamage() == ItemStacks.sludge.getItemDamage()) {
				if (slots[3].stackSize < slots[3].getMaxStackSize())
					slots[3].stackSize += ReikaMathLibrary.extrema(this.getPlantValue(slots[2]), slots[3].getMaxStackSize()-slots[3].stackSize, "min");
				else
					return;
			}
			else {
				fermenterCookTime = 0;
				return;
			}
			ReikaInventoryHelper.decrStack(2, slots);
			if (par5Random.nextInt(2) == 0)
				ReikaInventoryHelper.decrStack(0, slots);
		}
		this.onInventoryChanged();
		tank.removeLiquid(CONSUME_WATER);
	}

	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaWorldHelper.getBiomeTemp(world, x, z);
		int waterside = ReikaWorldHelper.checkForAdjSourceBlock(world, x, y, z, Material.water);
		if (waterside != -1) {
			Tamb -= 5;
		}
		int iceside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Block.ice.blockID);
		if (iceside != -1) {
			Tamb -= 15;
		}
		int fireside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Block.fire.blockID);
		if (fireside != -1) {
			Tamb += 50;
		}
		int lavaside = ReikaWorldHelper.checkForAdjSourceBlock(world, x, y, z, Material.lava);
		if (lavaside != -1) {
			Tamb += 200;
		}
		if (temperature > Tamb)
			temperature--;
		if (temperature > Tamb*2)
			temperature--;
		if (temperature < Tamb)
			temperature++;
		if (temperature*2 < Tamb)
			temperature++;
		if (temperature > MAXTEMP)
			temperature = MAXTEMP;
	}

	public void testYeastKill() {
		if (temperature < MAXTEMP)
			return;
		int slot = ReikaInventoryHelper.locateInInventory(ItemRegistry.YEAST.getShiftedID(), slots);
		if (slot != -1) {
			ReikaInventoryHelper.decrStack(slot, slots);
			worldObj.playSoundEffect(xCoord, yCoord, zCoord, "random.fizz", 0.8F, 0.8F);
		}
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory()
	{
		return slots.length;
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
		return slots[par1];
	}

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		slots[par1] = par2ItemStack;

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
		temperature = NBT.getInteger("temperature");
		NBTTagList nbttaglist = NBT.getTagList("Items");
		slots = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < slots.length)
			{
				slots[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}

		fermenterCookTime = NBT.getShort("CookTime");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("temperature", temperature);
		NBT.setShort("CookTime", (short)fermenterCookTime);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < slots.length; i++)
		{
			if (slots[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				slots[i].writeToNBT(nbttagcompound);
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
		//ReikaChatHelper.writeInt(this.operationTime(0));
		return ((int)(fermenterCookTime * par1*this.getFermentRate()))/2 / this.operationTime(omega, 0);
	}

	public int getTemperatureScaled(int par1)
	{
		//ReikaChatHelper.writeInt(this.operationTime(0));
		return (temperature * par1) / MAXTEMP;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.FERMENTER.ordinal();
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack is) {
		boolean red = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
		if (i == 3)
			return false;
		if (red) {
			switch(i) {
			case 0:
				return is.itemID == ItemRegistry.YEAST.getShiftedID();
			case 1:
				return this.getPlantValue(is) > 0;
			case 2:
				return is.itemID == Item.bucketWater.itemID;
			}
		}
		else {
			switch(i) {
			case 0:
				return is.itemID == Item.sugar.itemID;
			case 1:
				return is.itemID == Item.bucketWater.itemID;
			case 2:
				return is.itemID == Block.dirt.blockID;
			}
		}
		return false;
	}

	@Override
	public int getThermalDamage() {
		return 0;
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.canMake())
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
	public void overheat(World world, int x, int y, int z) {

	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return true;
	}

	@Override
	public Fluid getInputFluid() {
		return FluidRegistry.WATER;
	}

	@Override
	public int getCapacity() {
		return CAPACITY;
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return true;
	}

	public void setLiquid(int amt) {
		tank.setContents(amt, FluidRegistry.WATER);
	}
}
