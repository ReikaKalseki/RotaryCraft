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

import Reika.DragonAPI.Interfaces.XPProducer;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.FrictionHeatable;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastCrafting;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastFurnacePattern;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastRecipe;
import Reika.RotaryCraft.Base.TileEntity.InventoriedRCTileEntity;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityBlastFurnace extends InventoriedRCTileEntity implements TemperatureTE, XPProducer, FrictionHeatable, DiscreteFunction, ConditionalOperation {

	private int temperature;
	public int smeltTime = 0;

	public static final int SMELTTEMP = 600;
	public static final int BEDROCKTEMP = 1000;
	public static final int MAXTEMP = 2000;
	public static final float SMELT_XP = 0.6F;
	public static final int SLOT_1 = 0;
	public static final int SLOT_2 = 11;
	public static final int SLOT_3 = 14;

	private float xp;

	private BlastFurnacePattern pattern;

	@Override
	protected int getActiveTexture() {
		return this.getRecipe() != null || this.getCrafting() != null ? 1 : 0;
	}

	private BlastCrafting getCrafting() {
		ItemStack[] center = new ItemStack[9];
		System.arraycopy(inv, 1, center, 0, 9);
		BlastCrafting c = RecipesBlastFurnace.getRecipes().getCrafting(center, temperature);
		return c;
	}

	private BlastRecipe getRecipe() {
		ItemStack[] center = new ItemStack[9];
		System.arraycopy(inv, 1, center, 0, 9);
		BlastRecipe rec = RecipesBlastFurnace.getRecipes().getRecipe(inv[SLOT_1], inv[SLOT_2], inv[SLOT_3], center, temperature);

		if (rec == null)
			return null;

		ItemStack out = rec.outputItem();
		int num = this.getProducedFor(rec);
		out = ReikaItemHelper.getSizedItemStack(out, num);
		if (!this.checkCanMakeItem(out))
			return null;
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
			if (!ReikaItemHelper.matchStacks(in, in))
				return false;
			int sum = in.stackSize+is.stackSize;
			if (sum > in.getMaxStackSize() || sum > this.getInventoryStackLimit())
				return false;
			return ItemStack.areItemStackTagsEqual(is, in);
		}
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		tickcount++;
		if (tickcount >= 20) {
			this.updateTemperature(world, x, y, z, meta);
			tickcount = 0;
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

		for (int i = 1; i < 10; i++) {
			if (inv[i] != null)
				ReikaInventoryHelper.decrStack(i, inv);
		}
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	private void make(BlastRecipe rec) {
		smeltTime = 0;
		if (worldObj.isRemote)
			return;

		int num = this.getProducedFor(rec);
		int made = num;
		ItemStack out = rec.outputItem();

		if (rec.hasBonus) {
			double chance = DifficultyEffects.BONUSSTEEL.getDouble()*(ReikaMathLibrary.intpow(1.005, num*num)-1);
			if (ReikaRandomHelper.doWithChance(chance)) {
				num *= 1+rand.nextFloat();
			}
		}

		if (!ReikaInventoryHelper.addOrSetStack(out.getItem(), num, out.getItemDamage(), inv, 10))
			if (!ReikaInventoryHelper.addOrSetStack(out.getItem(), num, out.getItemDamage(), inv, 12))
				if (!ReikaInventoryHelper.addOrSetStack(out.getItem(), num, out.getItemDamage(), inv, 13))
					if (!this.checkSpreadFit(out, num))
						return;

		xp += rec.xp*num;

		for (int i = 0; i < rec.primary.numberToUse; i++) {
			if (ReikaRandomHelper.doWithChance(Math.min(1, rec.primary.chanceToUse*made)))
				ReikaInventoryHelper.decrStack(0, inv);
		}
		for (int i = 0; i < rec.secondary.numberToUse; i++) {
			if (ReikaRandomHelper.doWithChance(Math.min(1, rec.secondary.chanceToUse*made)))
				ReikaInventoryHelper.decrStack(11, inv);
		}
		for (int i = 0; i < rec.tertiary.numberToUse; i++) {
			if (ReikaRandomHelper.doWithChance(Math.min(1, rec.tertiary.chanceToUse*made)))
				ReikaInventoryHelper.decrStack(14, inv);
		}

		for (int i = 1; i < 10; i++) {
			if (inv[i] != null)
				ReikaInventoryHelper.decrStack(i, inv);
		}
		RotaryAchievements a = this.getAchievement(rec);
		if (a != null)
			a.triggerAchievement(this.getPlacer());

		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	private int getProducedFor(BlastRecipe rec) {
		int num = 0;
		ItemStack main = rec.mainItem();
		for (int i = 1; i < 10; i++) {
			if (inv[i] != null) {
				if (ReikaItemHelper.matchStacks(inv[i], main))
					num++;
			}
		}
		return rec.getNumberProduced(num);
	}

	private RotaryAchievements getAchievement(BlastRecipe rec) {
		if (ReikaItemHelper.matchStacks(ItemStacks.scrap, rec.mainItem()))
			return RotaryAchievements.RECYCLE;
		if (ReikaItemHelper.matchStacks(rec.outputItem(), ItemStacks.steelingot))
			return RotaryAchievements.MAKESTEEL;
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

		ForgeDirection waterside = ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.water);
		if (waterside != null) {
			Tamb /= 2;
		}
		ForgeDirection iceside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.ice);
		if (iceside != null) {
			if (Tamb > 0)
				Tamb /= 4;
			ReikaWorldHelper.changeAdjBlock(world, x, y, z, iceside, Blocks.flowing_water, 0);
		}
		ForgeDirection fireside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.fire);
		if (fireside != null) {
			Tamb += 200;
		}
		ForgeDirection lavaside = ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.lava);
		if (lavaside != null) {
			Tamb += 600;
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
		if (temperature > 100) {
			ForgeDirection side = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.snow);
			if (side != null)
				ReikaWorldHelper.changeAdjBlock(world, x, y, z, side, Blocks.air, 0);
			side = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.ice);
			if (side != null)
				ReikaWorldHelper.changeAdjBlock(world, x, y, z, side, Blocks.flowing_water, 0);
		}
		worldObj.markBlockForUpdate(x, y, z);
	}

	public int getSizeInventory() {
		return 15;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("melt", smeltTime);
		NBT.setInteger("temp", temperature);
		NBT.setFloat("exp", xp);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		smeltTime = NBT.getInteger("melt");
		temperature = NBT.getInteger("temp");
		xp = NBT.getFloat("exp");
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack is) {
		return is != null && this.getSlotForItem(i, is);
	}

	private boolean getSlotForItem(int slot, ItemStack is) {
		ArrayList<Integer> slots = ReikaInventoryHelper.getSlotsBetweenWithItemStack(is, this, 1, 9, false);
		if (!slots.isEmpty()) {
			return slots.contains(slot);
		}

		int type = RecipesBlastFurnace.getRecipes().getInputTypeForItem(is);
		switch (type) {
		case 0:
			return slot >= 1 && slot <= 9;
		case 1:
			return slot == SLOT_1;
		case 2:
			return slot == SLOT_2;
		case 3:
			return slot == SLOT_3;
		default:
			return false;
		}
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
}