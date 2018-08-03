/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Production;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Instantiable.Interpolation;
import Reika.DragonAPI.Instantiable.Data.KeyedItemStack;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.MultiOperational;
import Reika.RotaryCraft.Auxiliary.Interfaces.PressureTE;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidProducer;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntityFractionator extends InventoriedPowerLiquidProducer implements MultiOperational, ConditionalOperation, PressureTE {

	public int mixTime;

	private int pressure;

	public static final int CAPACITY = 240000;
	public static final int MINTIME = 10;

	public static int MAXPRESSURE = 1000; //10atm

	public boolean idle = false;

	private static final HashSet<KeyedItemStack> ingredients = new HashSet();
	private static final Interpolation yield = new Interpolation(false);

	static {
		ingredients.add(new KeyedItemStack(Items.blaze_powder).setSimpleHash(true));
		ingredients.add(new KeyedItemStack(new ItemStack(Items.coal, 1, 0)).setIgnoreMetadata(false).setSimpleHash(true));
		ingredients.add(new KeyedItemStack(Items.magma_cream).setSimpleHash(true));
		ingredients.add(new KeyedItemStack(ItemRegistry.ETHANOL.getStackOf()).setIgnoreMetadata(false).setSimpleHash(true));
		ingredients.add(new KeyedItemStack(ItemStacks.netherrackdust).setSimpleHash(true));
		ingredients.add(new KeyedItemStack(ItemStacks.tar).setSimpleHash(true));

		yield.addPoint(0, 0.01);
		yield.addPoint(100, 0.05); //~ atm
		yield.addPoint(180, 0.1); // min torque
		yield.addPoint(500, 0.4); // microturbine is just a bit more than this
		yield.addPoint(720, 1); //~4MW if minimal-speed gearing, the breakeven point
		yield.addPoint(850, 1.5); //~6MW
		yield.addPoint(1000, 2.5); //max
	}

	/*
	private static final ItemStack[] ingredients =
		{
		new ItemStack(Items.blaze_powder), new ItemStack(Items.coal),
		ItemStacks.netherrackdust, ItemStacks.tar,
		ItemRegistry.ETHANOL.getStackOf(), new ItemStack(Items.magma_cream)
		};
	 */

	public static boolean isJetFuelIngredient(ItemStack is) {
		return ingredients.contains(new KeyedItemStack(is).setSimpleHash(true));
	}

	public static Collection<KeyedItemStack> getIngredients() {
		return Collections.unmodifiableCollection(ingredients);
	}

	public void testIdle() {
		idle = !this.getAllIngredients();
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	public int getFuelScaled(int par1) {
		return (tank.getLevel()*par1)/CAPACITY;
	}

	public int getMixScaled(int par1) {
		//ReikaChatHelper.writeInt(this.omega);
		return (mixTime*par1)/this.getOperationTime();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();

		this.getPowerBelow();
		power = (long)omega * (long)torque;
		if (inv[ingredients.size()+1] != null && tank.getLevel() >= 1000) {
			if (inv[ingredients.size()+1].getItem() == Items.bucket && inv[ingredients.size()+1].stackSize == 1) {
				inv[ingredients.size()+1] = ItemStacks.fuelbucket.copy();
				tank.removeLiquid(1000);
			}
		}
		if (this.getTicksExisted()%20 == 0)
			this.updatePressure(world, x, y, z, meta);
		this.testIdle();
		if (power < MINPOWER || omega < MINSPEED) {
			mixTime = 0;
			return;
		}
		//int operationtime = ReikaMathLibrary.extrema(BASETIME-this.omega, MINTIME, "max");

		int n = this.getNumberConsecutiveOperations();
		for (int i = 0; i < n; i++)
			this.doOperation(n > 1);
	}

	public void updatePressure(World world, int x, int y, int z, int meta) {
		int dp = pressure-(int)ReikaWorldHelper.getAmbientPressureAt(world, x, y, z, false);
		pressure -= Math.signum(dp)*Math.max(1, Math.abs(dp/16));
		if (torque > 0)
			pressure += 1.8*Math.sqrt(torque);
		if (pressure > MAXPRESSURE) {
			this.overpressure(world, x, y, z);
			pressure = MAXPRESSURE;
		}
	}

	private void doOperation(boolean multiple) {
		if (this.process()) {
			mixTime++;
			//ReikaChatHelper.writeInt(this.operationTime(this.omega, 0));
			if (multiple || mixTime >= this.getOperationTime()) {
				mixTime = 0;
				this.make();
			}
		}
		else {
			mixTime = 0;
		}
	}

	private void make() {
		RotaryAchievements.JETFUEL.triggerAchievement(this.getPlacer());
		for (int i = 0; i < ingredients.size(); i++) {
			if (DifficultyEffects.CONSUMEFRAC.testChance() && !worldObj.isRemote)
				ReikaInventoryHelper.decrStack(i, inv);
		}
		if (DifficultyEffects.FRACTIONTEAR.testChance())
			ReikaInventoryHelper.decrStack(ingredients.size(), inv);
		tank.addLiquid((int)(this.getYieldRatio()*DifficultyEffects.PRODUCEFRAC.getInt()), FluidRegistry.getFluid("rc jet fuel"));
	}

	public float getYieldRatio() {
		return (float)yield.getValue(pressure);
	}

	private boolean process() {
		//ReikaJavaLibrary.pConsole(tank.getLevel()+":"+(DifficultyEffects.PRODUCEFRAC.getMaxAmount()*1000)+":"+CAPACITY);
		if (tank.getLevel()+(DifficultyEffects.PRODUCEFRAC.getMaxAmount()) >= CAPACITY)
			return false;
		if (inv[ingredients.size()] == null)
			return false;
		if (inv[ingredients.size()].getItem() != Items.ghast_tear) //need a ghast tear as fuel solvent
			return false;
		if (!this.getAllIngredients())
			return false;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(allitems));
		return true;
	}

	private boolean getAllIngredients() {
		HashSet<KeyedItemStack> check = new HashSet(ingredients);
		for (int i = 0; i < ingredients.size(); i++) {
			if (inv[i] == null)
				return false;
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d", ingredients[i.getItem, ingredients[i].getItemDamage()));
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", i)+String.valueOf(this.haveIngredient(ingredients[i.getItem, ingredients[i].getItemDamage())));
			KeyedItemStack ks = new KeyedItemStack(inv[i]).setSimpleHash(true);
			if (!check.contains(ks))
				return false;
			check.remove(ks);
		}
		return true;
	}

	public int getSizeInventory() {
		return ingredients.size()+1+1;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setInteger("mix", mixTime);

		NBT.setInteger("press", pressure);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		mixTime = NBT.getInteger("mix");

		pressure = NBT.getInteger("press");
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
		if (slot == ingredients.size()+1)
			return is.getItem() == Items.bucket;
		if (slot == ingredients.size())
			return is.getItem() == Items.ghast_tear;
		if (!this.isJetFuelIngredient(is))
			return false;
		HashSet<Integer> slots = ReikaInventoryHelper.getSlotsBetweenWithItemStack(is, this, 0, ingredients.size()-1, false);
		return slots.isEmpty() || slots.contains(slot);
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.getAllIngredients())
			return 15;
		return 15*tank.getLevel()/CAPACITY;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.FUELLINE || m == MachineRegistry.BEDPIPE;
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
	public int getNumberConsecutiveOperations() {
		return DurationRegistry.FRACTIONATOR.getNumberOperations(omega);
	}

	@Override
	public boolean areConditionsMet() {
		int nslots = ReikaInventoryHelper.countEmptySlots(inv);
		return nslots == 0 || (nslots == 1 && inv[7] == null);
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "Missing Ingredients";
	}

	@Override
	public int getPressure() {
		return pressure;
	}

	@Override
	public int getMaxPressure() {
		return MAXPRESSURE;
	}

	@Override
	public void addPressure(int press) {
		pressure += press;
	}

	@Override
	public void overpressure(World world, int x, int y, int z) {

	}
}
