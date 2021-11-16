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
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import Reika.DragonAPI.Instantiable.Interpolation;
import Reika.DragonAPI.Instantiable.Data.KeyedItemStack;
import Reika.DragonAPI.Instantiable.Data.WeightedRandom;
import Reika.DragonAPI.Instantiable.Math.MovingAverage;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.MultiOperational;
import Reika.RotaryCraft.Auxiliary.Interfaces.PressureTE;
import Reika.RotaryCraft.Auxiliary.Interfaces.ProcessingMachine;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPoweredLiquidIO;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntityFractionator extends InventoriedPoweredLiquidIO implements MultiOperational, ProcessingMachine, PressureTE {

	public int mixTime;

	private int pressure;

	public static final int CAPACITY = 240000;
	public static final int MINTIME = 10;

	public static int MAXPRESSURE = 1000; //10atm

	public boolean idle = false;

	private static final HashMap<KeyedItemStack, Float> ingredients = new HashMap();
	private static final Interpolation yield = new Interpolation(false);

	private MovingAverage torqueInput = new MovingAverage(20);

	static {
		ingredients.put(key(Items.blaze_powder), 1.5F);
		ingredients.put(key(ItemStacks.coaldust), 1F);
		ingredients.put(key(Items.magma_cream), 0.75F);
		ingredients.put(key(ReikaItemHelper.pinkDye), 0.5F);
		ingredients.put(key(ItemStacks.netherrackdust), 2F);
		ingredients.put(key(ItemStacks.tar), 1.5F);

		yield.addPoint(0, 0.01);
		yield.addPoint(100, 0.05); //~ atm
		yield.addPoint(180, 0.1); // min torque
		yield.addPoint(500, 0.4); // microturbine is just a bit more than this
		yield.addPoint(720, 1); //~4MW if minimal-speed gearing, the breakeven point
		yield.addPoint(850, 1.5); //~6MW
		yield.addPoint(1000, 2.5); //max
	}

	private static KeyedItemStack key(Item i) {
		return key(new ItemStack(i));
	}

	private static KeyedItemStack key(ItemStack is) {
		return new KeyedItemStack(is).setSimpleHash(true).setIgnoreMetadata(!is.getHasSubtypes());
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
		return ingredients.containsKey(key(is));
	}

	public static Collection<KeyedItemStack> getIngredients() {
		return Collections.unmodifiableCollection(ingredients.keySet());
	}

	public void testIdle() {
		idle = !this.hasAllIngredients();
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	public int getFuelScaled(int par1) {
		return (output.getLevel()*par1)/this.getOutputCapacity();
	}

	public int getEthanolScaled(int par1) {
		return (input.getLevel()*par1)/this.getInputCapacity();
	}

	public int getPressureScaled(int par1) {
		return (pressure*par1)/MAXPRESSURE;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();

		this.getPowerBelow();
		power = (long)omega * (long)torque;

		torqueInput.addValue(torque);
		if (!world.isRemote && this.getTicksExisted()%20 == 0)
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
		int local = pressure;
		double Pamb = ReikaWorldHelper.getAmbientPressureAt(world, x, y, z, false);
		if (Pamb > 101.3) {
			Pamb = 101.3+0.02*(Pamb-101.3);
		}
		int dp = local-(int)Pamb;
		int sub = (int)(Math.signum(dp)*Math.max(1, Math.abs(dp/16)));

		int avg = (int)torqueInput.getAverage();

		if (avg <= 0)
			sub *= 8;//4;

		local -= sub;

		if (avg > 0)
			local += 1.8*Math.sqrt(avg);

		if (local > MAXPRESSURE) {
			this.overpressure(world, x, y, z);
			local = MAXPRESSURE;
		}

		//Gain pressure slowly, but lose it quickly
		if (pressure < local) { //ascending
			pressure += Math.max(1, Math.min(ReikaRandomHelper.getRandomPlusMinus(6, 13), (local-pressure)/4));
		}
		else {
			pressure = local;
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
		if (!worldObj.isRemote) {
			float consume = ingredients.size()*DifficultyEffects.CONSUMEFRAC.getChance();
			WeightedRandom<Integer> wr = new WeightedRandom();
			for (int i = 0; i < ingredients.size(); i++) {
				ItemStack is = inv[i];
				KeyedItemStack ks = key(is);
				float wt = ingredients.get(ks);
				wr.addEntry(i, wt);
			}
			while (consume > 0) {
				int slot = wr.getRandomEntry();
				if (consume >= 1 || ReikaRandomHelper.doWithChance(consume))
					ReikaInventoryHelper.decrStack(slot, inv);
				wr.remove(slot);
				consume -= 1;
			}
		}
		float amt = 1000*DifficultyEffects.CONSUMEFRAC.getChance();
		input.removeLiquid((int)amt);
		output.addLiquid((int)(this.getYieldRatio()*DifficultyEffects.PRODUCEFRAC.getInt()), FluidRegistry.getFluid("rc jet fuel"));
	}

	public float getYieldRatio() {
		return (float)yield.getValue(pressure);
	}

	private boolean process() {
		//ReikaJavaLibrary.pConsole(tank.getLevel()+":"+(DifficultyEffects.PRODUCEFRAC.getMaxAmount()*1000)+":"+CAPACITY);
		if (output.getLevel()+(DifficultyEffects.PRODUCEFRAC.getMaxAmount()) >= CAPACITY)
			return false;
		if (inv[ingredients.size()] == null)
			return false;
		if (inv[ingredients.size()].getItem() != Items.ghast_tear) //need a ghast tear as fuel solvent
			return false;
		if (!this.hasAllIngredients())
			return false;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(allitems));
		return true;
	}

	private boolean hasAllIngredients() {
		HashSet<KeyedItemStack> check = new HashSet(ingredients.keySet());
		for (int i = 0; i < ingredients.size(); i++) {
			if (inv[i] == null)
				return false;
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d", ingredients[i.getItem, ingredients[i].getItemDamage()));
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", i)+String.valueOf(this.haveIngredient(ingredients[i.getItem, ingredients[i].getItemDamage())));
			KeyedItemStack ks = key(inv[i]);
			if (!check.contains(ks))
				return false;
			check.remove(ks);
		}
		return input.getLevel() >= 1000*DifficultyEffects.CONSUMEFRAC.getChance();
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
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		NBTTagCompound tag = new NBTTagCompound();
		torqueInput.writeToNBT(tag);
		NBT.setTag("torquevals", tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		if (NBT.hasKey("torquevals"))
			torqueInput = MovingAverage.readFromNBT(NBT.getCompoundTag("torquevals"));
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
		return (int)(this.getYieldRatio()/yield.getFinalValue()*15);
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.FUELLINE || m == MachineRegistry.BEDPIPE;
	}

	public int getFuelLevel() {
		return output.getLevel();
	}

	@Override
	public boolean canOutputTo(ForgeDirection to) {
		return to == ForgeDirection.UP;
	}

	@Override
	public int getInputCapacity() {
		return 16000;
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

	@Override
	public Fluid getInputFluid() {
		return RotaryCraft.ethanolFluid;
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return from.offsetY == 0;
	}

	@Override
	public boolean canIntakeFromPipe(MachineRegistry p) {
		return this.canOutputToPipe(p);
	}

	@Override
	public boolean canOutputToPipe(MachineRegistry p) {
		return p == MachineRegistry.FUELLINE || p == MachineRegistry.BEDPIPE;
	}

	@Override
	public boolean hasWork() {
		return this.areConditionsMet();
	}
}
