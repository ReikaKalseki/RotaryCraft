/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
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
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaTreeHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.ForestryHandler;
import Reika.DragonAPI.ModRegistry.ModCropList;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.DyeTrees.API.TreeGetter;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidReceiver;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PlantMaterials;

public class TileEntityFermenter extends InventoriedPowerLiquidReceiver implements TemperatureTE, DiscreteFunction, ConditionalOperation
{

	/** The number of ticks that the current item has been cooking for */
	public int fermenterCookTime = 0;

	public static final int MINUSEFULTEMP = 20;
	public static final int OPTMULTIPLYTEMP = 25;
	public static final int MAXUSEFULTEMP = 40;
	public static final int OPTFERMENTTEMP = 35;
	public static final int MAXTEMP = 60;

	public static final int CAPACITY = 4000;
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
		return i == 2;
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
				in.add(TreeGetter.getHeldDyeLeaf(j));
			}
			in.add(TreeGetter.getRainbowLeaf());
			in.add(TreeGetter.getRainbowSapling());
		}
		if (ModList.EMASHER.isLoaded()) {
			in.add(new ItemStack(ModCropList.ALGAE.blockID, 1, 0));
		}
		return in;
	}

	// Return the itemstack product from the input items.
	private ItemStack getRecipe() {
		for (int i = 0; i < 2; i++)
			if (inv[i] == null)
				return null;
		if (inv[0].itemID == Item.sugar.itemID) {
			if (this.hasWater())
				if(inv[1].itemID == Block.dirt.blockID)
					return new ItemStack(ItemRegistry.YEAST.getShiftedID(), 1, 0);
		}
		if (inv[0].itemID == ItemRegistry.YEAST.getShiftedID()) {
			if (this.getPlantValue(inv[1]) > 0)
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
			if (TreeGetter.isRainbowLeaf(is))
				return 32;
			if (TreeGetter.isRainbowSapling(is))
				return 16;
		}
		if (ModList.FORESTRY.isLoaded() && is.itemID == ForestryHandler.getInstance().saplingID)
			return 2;
		if (ModList.EMASHER.isLoaded() && is.itemID == ModCropList.ALGAE.blockID)
			return 3;
		ModWoodList sap = ModWoodList.getModWoodFromSapling(is);
		if (sap != null) {
			return PlantMaterials.SAPLING.getPlantValue()*getModWoodValue(sap);
		}
		ModWoodList leaf = ModWoodList.getModWoodFromLeaf(is);
		if (leaf != null) {
			return PlantMaterials.LEAVES.getPlantValue()*getModWoodValue(leaf);
		}
		PlantMaterials plant = PlantMaterials.getPlantEntry(is);
		if (plant == null)
			return 0;
		return plant.getPlantValue();
	}

	public static int getModWoodValue(ModWoodList wood) {
		if (wood == null)
			return 0;
		if (wood.isRareTree())
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
		this.getPower(false);
		ItemStack product = this.getRecipe();
		if (temperaturetick >= 20) {
			temperaturetick = 0;
			this.updateTemperature(world, x, y, z, meta);
		}

		if (tickcount >= 2+rand.nextInt(18)) {
			this.testYeastKill();
			tickcount = 0;
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
		if (inv[2] != null) {
			if (product.itemID != inv[2].itemID) {
				fermenterCookTime = 0;
				return;
			}
		}
		idle = false;
		if (power < MINPOWER || omega < MINSPEED)
			return;
		if (inv[2] != null) {
			if (inv[2].stackSize >= inv[2].getMaxStackSize()) {
				fermenterCookTime = 0;
				return;
			}
		}
		fermenterCookTime++;
		if (fermenterCookTime >= this.getOperationTime()) {
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
		if (inv[2] != null) {
			if (inv[2].stackSize >= inv[2].getMaxStackSize()) {
				return false;
			}
			if (product.itemID != inv[2].itemID) {
				return false;
			}
		}
		return true;
	}

	private void make(ItemStack product) {
		if (product.itemID == ItemRegistry.YEAST.getShiftedID()) {
			if (inv[2] == null)
				inv[2] = new ItemStack(ItemRegistry.YEAST.getShiftedID(), 1, 0);
			else if (inv[2].itemID == ItemRegistry.YEAST.getShiftedID()) {
				if (inv[2].stackSize < inv[2].getMaxStackSize())
					inv[2].stackSize++;
				else
					return;
			}
			else {
				fermenterCookTime = 0;
				return;
			}
			ReikaInventoryHelper.decrStack(0, inv);
			if (rand.nextInt(4) == 0)
				ReikaInventoryHelper.decrStack(1, inv);
		}
		if (product.itemID == ItemStacks.sludge.itemID && product.getItemDamage() == ItemStacks.sludge.getItemDamage()) {
			if (inv[2] == null)
				inv[2] = new ItemStack(ItemStacks.sludge.itemID, this.getPlantValue(inv[1]), ItemStacks.sludge.getItemDamage());
			else if (inv[2].itemID == ItemStacks.sludge.itemID && inv[2].getItemDamage() == ItemStacks.sludge.getItemDamage()) {
				if (inv[2].stackSize < inv[2].getMaxStackSize())
					inv[2].stackSize += ReikaMathLibrary.extrema(this.getPlantValue(inv[1]), inv[2].getMaxStackSize()-inv[2].stackSize, "min");
				else
					return;
			}
			else {
				fermenterCookTime = 0;
				return;
			}
			ReikaInventoryHelper.decrStack(1, inv);
			if (rand.nextInt(2) == 0)
				ReikaInventoryHelper.decrStack(0, inv);
		}
		this.onInventoryChanged();
		tank.removeLiquid(CONSUME_WATER);
	}

	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		ForgeDirection waterside = ReikaWorldHelper.checkForAdjSourceBlock(world, x, y, z, Material.water);
		if (waterside != null) {
			Tamb -= 5;
		}
		ForgeDirection iceside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Block.ice.blockID);
		if (iceside != null) {
			Tamb -= 15;
		}
		ForgeDirection fireside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Block.fire.blockID);
		if (fireside != null) {
			Tamb += 50;
		}
		ForgeDirection lavaside = ReikaWorldHelper.checkForAdjSourceBlock(world, x, y, z, Material.lava);
		if (lavaside != null) {
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
		int slot = ReikaInventoryHelper.locateInInventory(ItemRegistry.YEAST.getShiftedID(), inv);
		if (slot != -1) {
			ReikaInventoryHelper.decrStack(slot, inv);
			worldObj.playSoundEffect(xCoord, yCoord, zCoord, "random.fizz", 0.8F, 0.8F);
		}
	}

	public int getSizeInventory() {
		return 3;
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		temperature = NBT.getInteger("temperature");

		fermenterCookTime = NBT.getShort("CookTime");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("temperature", temperature);
		NBT.setShort("CookTime", (short)fermenterCookTime);
	}

	public int getCookProgressScaled(int par1)
	{
		//ReikaChatHelper.writeInt(this.operationTime(0));
		return (fermenterCookTime * par1)/2 / this.getOperationTime();
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
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.FERMENTER;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack is) {
		boolean red = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
		if (i >= 2)
			return false;
		if (red) {
			switch(i) {
			case 0:
				return is.itemID == ItemRegistry.YEAST.getShiftedID();
			case 1:
				return this.getPlantValue(is) > 0;
			}
		}
		else {
			switch(i) {
			case 0:
				return is.itemID == Item.sugar.itemID;
			case 1:
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

	@Override
	public int getOperationTime() {
		int base = DurationRegistry.FERMENTER.getOperationTime(omega);
		return Math.max(1, (int)(base/this.getFermentRate()));
	}

	@Override
	public boolean areConditionsMet() {
		return this.canMake();
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "Invalid or Missing Items";
	}
}
