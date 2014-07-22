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

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidProducer;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntityFractionator extends InventoriedPowerLiquidProducer implements DiscreteFunction, ConditionalOperation {

	public int mixTime;
	public int storeTime;

	public static final int CAPACITY = 240000;
	public static final int MINTIME = 10;

	public boolean idle = false;

	private static final ItemStack[] ingredients =
		{new ItemStack(Item.blazePowder), new ItemStack(Item.coal),
		ItemStacks.netherrackdust, ItemStacks.tar,
		ItemRegistry.ETHANOL.getStackOf(), new ItemStack(Item.magmaCream)};

	public static boolean isJetFuelIngredient(ItemStack is) {
		for (int i = 0; i < ingredients.length; i++) {
			if (ReikaItemHelper.matchStacks(is, ingredients[i]))
				return true;
		}
		return false;
	}

	public static ItemStack[] getIngredients() {
		ItemStack[] is = new ItemStack[ingredients.length];
		System.arraycopy(ingredients, 0, is, 0, is.length);
		return is;
	}

	public void testIdle() {
		idle = !this.getAllIngredients();
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	public int getFuelScaled(int par1)
	{
		return (tank.getLevel()*par1)/CAPACITY;
	}

	public int getStorageScaled(int par1)
	{
		return (storeTime*par1)/CAPACITY;
	}

	public int getMixScaled(int par1)
	{
		//ReikaChatHelper.writeInt(this.omega);
		return (mixTime*par1)/this.getOperationTime();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();
		power = (long)omega * (long)torque;
		if (inv[ingredients.length+1] != null && tank.getLevel() >= 1000) {
			if (inv[ingredients.length+1].itemID == Item.bucketEmpty.itemID && inv[ingredients.length+1].stackSize == 1) {
				inv[ingredients.length+1] = ItemStacks.fuelbucket.copy();
				tank.removeLiquid(1000);
			}
		}
		if (power < MINPOWER || omega < MINSPEED) {
			mixTime = 0;
			return;
		}
		this.testIdle();
		//int operationtime = ReikaMathLibrary.extrema(BASETIME-this.omega, MINTIME, "max");
		if (this.process()) {
			mixTime++;
			//ReikaChatHelper.writeInt(this.operationTime(this.omega, 0));
			if (mixTime >= this.getOperationTime()) {
				mixTime = 0;
				this.make();
			}
		}
		else {
			mixTime = 0;
		}
	}

	public void make() {
		RotaryAchievements.JETFUEL.triggerAchievement(this.getPlacer());
		for (int i = 0; i < ingredients.length; i++) {
			if (DifficultyEffects.CONSUMEFRAC.testChance() && !worldObj.isRemote)
				ReikaInventoryHelper.decrStack(i, inv);
		}
		tank.addLiquid(DifficultyEffects.PRODUCEFRAC.getInt(), FluidRegistry.getFluid("jet fuel"));
	}

	public boolean process() {
		//ReikaJavaLibrary.pConsole(tank.getLevel()+":"+(DifficultyEffects.PRODUCEFRAC.getMaxAmount()*RotaryConfig.MILLIBUCKET)+":"+CAPACITY);
		if (tank.getLevel()+(DifficultyEffects.PRODUCEFRAC.getMaxAmount()) >= CAPACITY)
			return false;
		boolean allitems = this.getAllIngredients();
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(allitems));
		if (inv[ingredients.length] == null)
			return false;
		if (inv[ingredients.length].itemID != Item.ghastTear.itemID) //need a ghast tear as fuel solvent
			return false;
		return allitems;
	}

	public boolean getAllIngredients() {
		for (int k = 0; k < ingredients.length; k++)
			if (inv[k] == null)
				return false;
		for (int i = 0; i < ingredients.length; i++) {
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d", ingredients[i].itemID, ingredients[i].getItemDamage()));
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", i)+String.valueOf(this.haveIngredient(ingredients[i].itemID, ingredients[i].getItemDamage())));
			if (!ReikaInventoryHelper.checkForItemStack(ingredients[i].itemID, ingredients[i].getItemDamage(), inv))
				return false;
		}
		return true;
	}

	public int getSizeInventory() {
		return ingredients.length+1+1;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		NBT.setInteger("mix", mixTime);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		mixTime = NBT.getInteger("mix");
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
		return MachineRegistry.FRACTIONATOR;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		if (slot == ingredients.length+1)
			return is.itemID == Item.bucketEmpty.itemID;
		if (slot == ingredients.length)
			return is.itemID == Item.ghastTear.itemID;
		return ReikaItemHelper.matchStacks(is, ingredients[slot]);
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.getAllIngredients())
			return 15;
		return 15*tank.getLevel()/CAPACITY;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.FUELLINE;
	}

	public int getFuelLevel() {
		return tank.getLevel();
	}

	public void setEmpty() {
		tank.empty();
	}

	@Override
	public boolean canOutputTo(ForgeDirection to) {
		return to == ForgeDirection.UP;
	}

	@Override
	public int getCapacity() {
		return CAPACITY;
	}

	@Override
	public int getOperationTime() {
		return DurationRegistry.FRACTIONATOR.getOperationTime(omega);
	}

	@Override
	public boolean areConditionsMet() {
		return ReikaInventoryHelper.countEmptySlots(inv) == 0;
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "Missing Ingredients";
	}
}
