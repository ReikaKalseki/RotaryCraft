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

import java.util.HashSet;
import java.util.Set;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Interfaces.TileEntity.XPProducer;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.FrictionHeatable;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastCrafting;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastFurnacePattern;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastRecipe;
import Reika.RotaryCraft.Base.TileEntity.InventoriedRCTileEntity;
import Reika.RotaryCraft.Items.Tools.ItemCraftPattern;
import Reika.RotaryCraft.Items.Tools.ItemCraftPattern.RecipeMode;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntityBlastFurnace extends InventoriedRCTileEntity implements TemperatureTE, XPProducer, FrictionHeatable, DiscreteFunction, ConditionalOperation {

	private int temperature;
	public int smeltTime = 0;

	public static final int SMELTTEMP = 600;
	public static final int BEDROCKTEMP = 1450;//1400;//1000;//1150;
	public static final int MAXTEMP = 2000;
	public static final float SMELT_XP = 0.6F;

	public static final int CENTER_ADDITIVE = 0;
	public static final int LOWER_ADDITIVE = 11;
	public static final int UPPER_ADDITIVE = 14;
	public static final int PATTERN_SLOT = 15;

	public static final int OUTPUT_CENTER = 10;
	public static final int OUTPUT_UPPER = 12;
	public static final int OUTPUT_LOWER = 13;

	private float xp;

	private BlastFurnacePattern pattern;

	private final StepTimer tempTimer = new StepTimer(20);

	public boolean[] lockedSlots = new boolean[inv.length];
	public boolean leaveLastItem;

	@Override
	protected int getActiveTexture() {
		return this.getRecipe() != null || this.getCrafting() != null ? 1 : 0;
	}

	private BlastCrafting getCrafting() {
		ItemStack[] center = new ItemStack[9];
		System.arraycopy(inv, 1, center, 0, 9);
		BlastCrafting c = RecipesBlastFurnace.getRecipes().getCrafting(center, temperature);

		if (c != null && leaveLastItem) {
			for (int i = 1; i <= 9; i++) {
				if (inv[i] != null && inv[i].stackSize == 1)
					return null;
			}
		}

		return c;
	}

	private BlastRecipe getRecipe() {
		ItemStack[] center = new ItemStack[9];
		System.arraycopy(inv, 1, center, 0, 9);
		BlastRecipe rec = RecipesBlastFurnace.getRecipes().getRecipe(inv[CENTER_ADDITIVE], inv[LOWER_ADDITIVE], inv[UPPER_ADDITIVE], center, temperature);

		if (rec == null)
			return null;

		if (rec.requiresEmptyOutput()) {
			if (inv[10] != null || inv[13] != null || inv[12] != null)
				return null;
		}

		ItemStack out = rec.outputItem();
		int num = this.getProducedFor(rec);
		out = ReikaItemHelper.getSizedItemStack(out, num);
		if (!this.checkCanMakeItem(out))
			return null;

		if (leaveLastItem) {
			for (int i = 1; i <= 9; i++) {
				if (rec.usesSlot(i-1) && inv[i] != null && inv[i].stackSize == 1)
					return null;
			}
		}

		return rec;
	}

	private boolean checkCanMakeItem(ItemStack out) {
		return this.canAdd(out, 10) || this.canAdd(out, 13) || this.canAdd(out, 12);
	}

	private boolean canAdd(ItemStack is, int slot) {
		if (inv[slot] == null)
			return true;
		else {
			ItemStack in = inv[slot];
			return ReikaItemHelper.areStacksCombinable(is, in, this.getInventoryStackLimit());
		}
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		tempTimer.update();
		if (tempTimer.checkCap()) {
			this.updateTemperature(world, x, y, z, meta);
		}

		BlastRecipe rec = this.getRecipe();
		BlastCrafting bc = this.getCrafting();
		if (bc != null) {
			pattern = bc;
			if (bc.speed <= 1 || worldObj.getTotalWorldTime()%bc.speed == 0)
				smeltTime++;
			if (smeltTime >= this.getOperationTime()) {
				this.craft(bc);
			}
		}
		else if (rec != null) {
			pattern = rec;
			smeltTime++;
			if (smeltTime >= this.getOperationTime()) {
				this.make(rec);
			}
		}
		else {
			pattern = null;
			smeltTime = 0;
			return;
		}
	}

	private void craft(BlastCrafting bc) {
		smeltTime = 0;
		if (worldObj.isRemote)
			return;

		ItemStack out = bc.outputItem();

		if (!ReikaInventoryHelper.addOrSetStack(out, inv, 10))
			if (!ReikaInventoryHelper.addOrSetStack(out, inv, 12))
				if (!ReikaInventoryHelper.addOrSetStack(out, inv, 13))
					if (!this.checkSpreadFit(out, out.stackSize))
						return;

		xp += out.stackSize*bc.xp;
		if (this.getPlacer() != null) {
			out.onCrafting(worldObj, this.getPlacer(), out.stackSize);
		}

		for (int i = 1; i < 10; i++) {
			if (inv[i] != null)
				ReikaInventoryHelper.decrStack(i, inv);
		}
	}

	private void make(BlastRecipe rec) {
		smeltTime = 0;
		if (worldObj.isRemote)
			return;

		int num = this.getProducedFor(rec);
		int made = num;
		ItemStack out = rec.outputItem();

		if (rec.bonusYield > 0) {
			double chance = DifficultyEffects.BONUSSTEEL.getDouble()*(ReikaMathLibrary.intpow(1.005, num*num)-1);
			if (ReikaRandomHelper.doWithChance(chance)) {
				num *= 1+rand.nextFloat()*rec.bonusYield;
			}
		}

		if (ReikaItemHelper.matchStacks(out, ItemStacks.steelblock)) {
			if (!ReikaInventoryHelper.addOrSetStack(ReikaItemHelper.getSizedItemStack(ItemStacks.steelingot, 2*num), inv, 10))
				if (!ReikaInventoryHelper.addOrSetStack(ReikaItemHelper.getSizedItemStack(ItemStacks.steelingot, 2*num), inv, 12))
					if (!ReikaInventoryHelper.addOrSetStack(ReikaItemHelper.getSizedItemStack(ItemStacks.steelingot, 2*num), inv, 13))
						return;
			if (!ReikaInventoryHelper.addOrSetStack(ReikaItemHelper.getSizedItemStack(ReikaItemHelper.charcoal, 3*num), inv, 10))
				if (!ReikaInventoryHelper.addOrSetStack(ReikaItemHelper.getSizedItemStack(ReikaItemHelper.charcoal, 3*num), inv, 12))
					if (!ReikaInventoryHelper.addOrSetStack(ReikaItemHelper.getSizedItemStack(ReikaItemHelper.charcoal, 3*num), inv, 13))
						return;
			if (!ReikaInventoryHelper.addOrSetStack(new ItemStack(Items.iron_ingot, 5*num, 0), inv, 10))
				if (!ReikaInventoryHelper.addOrSetStack(new ItemStack(Items.iron_ingot, 5*num, 0), inv, 12))
					if (!ReikaInventoryHelper.addOrSetStack(new ItemStack(Items.iron_ingot, 5*num, 0), inv, 13))
						return;
		}
		else {
			if (!ReikaInventoryHelper.addOrSetStack(out.getItem(), num, out.getItemDamage(), inv, 10))
				if (!ReikaInventoryHelper.addOrSetStack(out.getItem(), num, out.getItemDamage(), inv, 12))
					if (!ReikaInventoryHelper.addOrSetStack(out.getItem(), num, out.getItemDamage(), inv, 13))
						if (!this.checkSpreadFit(out, num))
							return;
		}

		xp += rec.xp*num;

		for (int i = 0; i < rec.primary.numberToUse; i++) {
			if (ReikaRandomHelper.doWithChance(this.getConsumptionFactor(rec.primary.chanceToUse, made)))
				ReikaInventoryHelper.decrStack(0, inv);
		}
		for (int i = 0; i < rec.secondary.numberToUse; i++) {
			if (ReikaRandomHelper.doWithChance(this.getConsumptionFactor(rec.secondary.chanceToUse, made)))
				ReikaInventoryHelper.decrStack(11, inv);
		}
		for (int i = 0; i < rec.tertiary.numberToUse; i++) {
			if (ReikaRandomHelper.doWithChance(this.getConsumptionFactor(rec.tertiary.chanceToUse, made)))
				ReikaInventoryHelper.decrStack(14, inv);
		}

		for (int i = 1; i < 10; i++) {
			if (inv[i] != null)
				ReikaInventoryHelper.decrStack(i, inv);
		}
		RotaryAchievements a = this.getAchievement(rec);
		if (a != null)
			a.triggerAchievement(this.getPlacer());

	}

	private float getConsumptionFactor(float base, int made) {
		return MathHelper.clamp_float(base*made*DifficultyEffects.BLASTCONSUME.getChance(), base, 1);
	}

	private int getProducedFor(BlastRecipe rec) {
		int num = 0;
		for (int i = 1; i < 10; i++) {
			if (inv[i] != null) {
				if (rec.isValidMainItem(inv[i]))
					num++;
			}
		}
		return rec.getNumberProduced(num);
	}

	private RotaryAchievements getAchievement(BlastRecipe rec) {
		if (rec.isValidMainItem(ItemStacks.scrap))
			return RotaryAchievements.RECYCLE;
		if (ReikaItemHelper.matchStacks(rec.outputItem(), ItemStacks.steelingot))
			return RotaryAchievements.MAKESTEEL;
		if (ReikaItemHelper.matchStacks(rec.outputItem(), ItemStacks.steelblock))
			return RotaryAchievements.FAILSTEEL;
		return null;
	}

	public int getTemperatureScaled(int p1) {
		return ((p1*temperature)/MAXTEMP);
	}

	public void dropXP() {
		ReikaWorldHelper.splitAndSpawnXP(worldObj, xCoord+rand.nextFloat(), yCoord+1.25F, zCoord+rand.nextFloat(), (int)xp);
		xp = 0;
	}

	public float getXP() {
		return xp;
	}

	public void clearXP() {
		xp = 0;
	}

	private boolean checkSpreadFit(ItemStack is, int num) {
		int maxfit = 0;
		int f1 = is.getMaxStackSize()-inv[10].stackSize;
		int f2 = is.getMaxStackSize()-inv[12].stackSize;
		int f3 = is.getMaxStackSize()-inv[13].stackSize;
		maxfit = f1+f2+f3;
		if (num > maxfit)
			return false;
		if (f1 > num) {
			inv[10].stackSize += num;
			return true;
		}
		else {
			inv[10].stackSize = inv[10].getMaxStackSize();
			num -= f1;
		}
		if (f2 > num) {
			inv[12].stackSize += num;
			return true;
		}
		else {
			inv[12].stackSize = inv[12].getMaxStackSize();
			num -= f2;
		}
		if (f3 > num) {
			inv[12].stackSize += num;
			return true;
		}
		else {
			inv[13].stackSize = inv[13].getMaxStackSize();
			num -= f3;
		}
		return true;
	}

	public int getOperationTime() {
		int time = 2*((1500-(temperature-SMELTTEMP))/12); //1500 was MAXTEMP
		if (time < 1)
			return 1;
		return time;
	}

	public int getCookScaled(int p1) {
		return ((p1*smeltTime)/this.getOperationTime());
	}

	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);

		if (RotaryAux.isNextToWater(world, x, y, z)) {
			Tamb /= 2;
		}
		ForgeDirection iceside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.ice);
		if (iceside == null)
			iceside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.packed_ice);
		if (iceside != null) {
			if (Tamb > 0)
				Tamb /= 4;
			ReikaWorldHelper.changeAdjBlock(world, x, y, z, iceside, Blocks.flowing_water, 0);
		}
		int Tadd = 0;
		if (RotaryAux.isNextToFire(world, x, y, z)) {
			Tadd += Tamb >= 100 ? 100 : 200;
		}
		if (RotaryAux.isNextToLava(world, x, y, z)) {
			Tadd += Tamb >= 100 ? 400 : 600;
		}
		Tamb += Tadd;

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
		if (temperature > 100) {
			ForgeDirection side = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.snow);
			if (side == null)
				side = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.snow_layer);
			if (side != null)
				ReikaWorldHelper.changeAdjBlock(world, x, y, z, side, Blocks.air, 0);
			side = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.ice);
			if (side != null)
				ReikaWorldHelper.changeAdjBlock(world, x, y, z, side, Blocks.flowing_water, 0);
		}
	}

	public int getSizeInventory() {
		return 16;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);
		NBT.setInteger("melt", smeltTime);
		NBT.setInteger("temp", temperature);
		NBT.setFloat("exp", xp);

		NBT.setInteger("locks", ReikaArrayHelper.booleanToBitflags(lockedSlots));
		NBT.setBoolean("last", leaveLastItem);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);
		smeltTime = NBT.getInteger("melt");
		temperature = NBT.getInteger("temp");
		xp = NBT.getFloat("exp");

		lockedSlots = ReikaArrayHelper.booleanFromBitflags(NBT.getInteger("locks"), inv.length);
		leaveLastItem = NBT.getBoolean("last");
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack is) {
		return is != null && this.getSlotForItem(i, is);
	}

	private boolean getSlotForItem(int slot, ItemStack is) {
		ItemStack patt = inv[PATTERN_SLOT];
		if (ItemRegistry.CRAFTPATTERN.matchItem(patt) && slot >= 1 && slot <= 9) {
			return ItemCraftPattern.checkPatternForMatch(this, RecipeMode.BLASTFURN, slot, slot-1, is, patt);
		}
		//ReikaJavaLibrary.pConsole(slot+": "+lockedSlots[slot]);
		if (lockedSlots[slot])
			return false;
		HashSet<Integer> slots = ReikaInventoryHelper.getSlotsBetweenWithItemStack(is, this, 1, 9, false);
		if (!slots.isEmpty()) {
			return slots.contains(slot);
		}

		Set<Integer> types = RecipesBlastFurnace.getRecipes().getInputTypesForItem(is);
		if (slot == CENTER_ADDITIVE)
			return types.contains(1);
		if (slot == LOWER_ADDITIVE)
			return types.contains(2);
		if (slot == UPPER_ADDITIVE)
			return types.contains(3);
		if (slot >= 1 && slot <= 9)
			return types.contains(0); //check this last, since there are fewer variants that go in additives
		return false;
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
		return MachineRegistry.BLASTFURNACE;
	}

	@Override
	public int getThermalDamage() {
		return 0;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i == 10 || i == 12 || i == 13;
	}

	@Override
	public int getRedstoneOverride() {
		return this.getRecipe() == null ? 15 : 0;
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
	public void onEMP() {}

	@Override
	public int getMaxTemperature() {
		return MAXTEMP;
	}

	@Override
	public boolean areConditionsMet() {
		return this.getRecipe() != null;
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "Insufficient Temperature or Invalid or Missing Items";
	}

	@Override
	public boolean canBeCooledWithFins() {
		return false;
	}

	@Override
	public boolean allowHeatExtraction() {
		return true;
	}

	public void setTemperature(int temp) {
		temperature = temp;
	}

	@Override
	public void resetAmbientTemperatureTimer() {
		tempTimer.reset();
	}

	@Override
	public float getMultiplier() {
		return 1;
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
