/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.RecipeManagers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.Data.Collections.ChancedOutputList;
import Reika.DragonAPI.Instantiable.Data.Collections.ChancedOutputList.ChanceExponentiator;
import Reika.DragonAPI.Instantiable.Data.Collections.ChancedOutputList.ChanceManipulator;
import Reika.DragonAPI.Instantiable.Data.Collections.OneWayCollections.OneWayList;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.ReikaXPFluidHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.MagicCropHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.OreBerryBushHandler;
import Reika.DragonAPI.ModInteract.RecipeHandlers.ForestryRecipeHelper;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.ItemRegistry;

public class RecipesCentrifuge
{
	private static final RecipesCentrifuge CentrifugeBase = new RecipesCentrifuge();

	private ItemHashMap<ChancedOutputList> recipeList = new ItemHashMap().setOneWay();
	private ItemHashMap<FluidOut> fluidList = new ItemHashMap().setOneWay();

	private OneWayList<ItemStack> outputs = new OneWayList();

	public static final RecipesCentrifuge recipes()
	{
		return CentrifugeBase;
	}

	private RecipesCentrifuge() {

		this.addRecipe(Items.magma_cream, null, Items.slime_ball, 100, Items.blaze_powder, 100);
		this.addRecipe(Items.melon, null, new ItemStack(Items.melon_seeds, 4), 100);
		this.addRecipe(Blocks.pumpkin, null, new ItemStack(Items.pumpkin_seeds, 12), 100);
		this.addRecipe(Items.wheat, null, new ItemStack(Items.wheat_seeds, 4), 100);
		this.addRecipe(Blocks.gravel, null, new ItemStack(Items.flint), 50, new ItemStack(Blocks.sand), 75);
		this.addRecipe(ItemStacks.netherrackdust, null, new ItemStack(Items.glowstone_dust), 25, new ItemStack(Items.gunpowder), 80, ExtractorModOres.getDustProduct(ModOreList.SULFUR), 40);
		this.addRecipe(Blocks.dirt, null, new ItemStack(Blocks.sand), 80, new ItemStack(Blocks.clay), 10);
		this.addRecipe(Items.blaze_powder, null, new ItemStack(Items.gunpowder), 100, ExtractorModOres.getDustProduct(ModOreList.SULFUR), 75);

		if (ModList.FORESTRY.isLoaded()) {
			Map<ItemStack, ChancedOutputList> centrifuge = ForestryRecipeHelper.getInstance().getCentrifugeRecipes();
			for (ItemStack in : centrifuge.keySet()) {
				ChancedOutputList out = centrifuge.get(in).copy();
				out.manipulateChances(new ChanceExponentiator(3));
				out.manipulateChances(new ChanceRounder());
				this.addRecipe(in, out, null);
			}
		}

		this.addRecipe(ItemStacks.slipperyComb, new FluidStack(FluidRegistry.getFluid("lubricant"), 50), 60, ItemStacks.slipperyPropolis, 80);
		this.addRecipe(ItemStacks.slipperyPropolis, new FluidStack(FluidRegistry.getFluid("lubricant"), 150), 100);

		if (ReikaItemHelper.oreItemsExist("dustLead", "dustSilver")) {
			ItemStack lead = OreDictionary.getOres("dustLead").get(0);
			ItemStack silver = OreDictionary.getOres("dustSilver").get(0);
			this.addRecipe(ExtractorModOres.getSmeltedIngot(ModOreList.GALENA), null, lead, 100, silver, 100);
		}

		if (ModList.TINKERER.isLoaded()) {
			ItemStack berry = OreBerryBushHandler.BerryTypes.XP.getStack();
			if (berry != null && ReikaXPFluidHelper.fluidsExist()) {
				FluidStack fs = ReikaXPFluidHelper.getFluid(30);
				this.addRecipe(berry, fs, 100);
			}
		}

		if (ModList.MAGICCROPS.isLoaded()) {
			Item drop = MagicCropHandler.getInstance().dropID;
			if (drop != null && ReikaXPFluidHelper.fluidsExist()) {
				FluidStack fs = ReikaXPFluidHelper.getFluid(30);
				this.addRecipe(drop, fs, 100);
			}
		}

		int amt = ReikaMathLibrary.roundUpToX(10, (int)(DifficultyEffects.CANOLA.getAverageAmount()*0.75F));
		this.addRecipe(ItemRegistry.CANOLA.getStackOfMetadata(2), new FluidStack(FluidRegistry.getFluid("lubricant"), amt), 100);
	}

	private static class ChanceRounder implements ChanceManipulator {

		private ChanceRounder() {

		}

		@Override
		public float getChance(float original) { //round up to nearest 0.5F
			return Math.round(2D*original)/2F;
		}


	}

	private void addRecipe(ItemStack in, ChancedOutputList out, FluidOut fs)
	{
		out.lock();
		recipeList.put(in, out);
		for (ItemStack isout : out.keySet())
			if (!ReikaItemHelper.collectionContainsItemStack(outputs, isout))
				outputs.add(isout);
		if (fs != null)
			fluidList.put(in, fs);
	}

	private void addRecipe(ItemStack in, FluidOut fs, Object... items)
	{
		ChancedOutputList li = new ChancedOutputList();
		for (int i = 0; i < items.length; i += 2) {
			Object is1 = items[i];
			if (is1 instanceof Item)
				is1 = new ItemStack((Item)is1);
			else if (is1 instanceof Block)
				is1 = new ItemStack((Block)is1);
			Object chance = items[i+1];
			if (chance instanceof Integer)
				chance = new Float((Integer)chance);
			li.addItem((ItemStack)is1, (Float)chance);
		}
		this.addRecipe(in, li, fs);
	}

	private void addRecipe(Block b, FluidOut fs, Object... items)
	{
		this.addRecipe(new ItemStack(b), fs, items);
	}

	private void addRecipe(Item item, FluidOut fs, Object... items)
	{
		this.addRecipe(new ItemStack(item), fs, items);
	}

	private void addRecipe(ItemStack item, FluidStack fs, float fc, Object... items)
	{
		this.addRecipe(item, new FluidOut(fs, fc), items);
	}

	private void addRecipe(Block item, FluidStack fs, float fc, Object... items)
	{
		this.addRecipe(item, new FluidOut(fs, fc), items);
	}

	private void addRecipe(Item item, FluidStack fs, float fc, Object... items)
	{
		this.addRecipe(item, new FluidOut(fs, fc), items);
	}

	public ChancedOutputList getRecipeResult(ItemStack item) {
		return item != null ? recipeList.get(item) : null;
	}

	private Collection<ItemStack> getRecipeOutputs(ItemStack item) {
		return item != null ? recipeList.get(item).keySet() : null;
	}

	public float getItemChance(ItemStack in, ItemStack out) {
		ChancedOutputList c = this.getRecipeResult(in);
		return c.getItemChance(out);
	}

	private FluidOut getFluidOut(ItemStack item)
	{
		return item != null ? fluidList.get(item) : null;
	}

	public FluidStack getFluidResult(ItemStack item)
	{
		FluidOut fo = this.getFluidOut(item);
		return fo != null ? fo.fluid.copy() : null;
	}

	public float getFluidChance(ItemStack item) {
		FluidOut fo = this.getFluidOut(item);
		return fo != null ? fo.chance : 0;
	}

	public ArrayList<ItemStack> getSources(ItemStack result) {
		ArrayList<ItemStack> li = new ArrayList();
		for (ItemStack in : recipeList.keySet()) {
			Collection<ItemStack> out = this.getRecipeOutputs(in);
			if (ReikaItemHelper.collectionContainsItemStack(out, result))
				li.add(in.copy());
		}
		return li;
	}

	public ArrayList<ItemStack> getSources(Fluid result) {
		ArrayList<ItemStack> li = new ArrayList();
		for (ItemStack in : fluidList.keySet()) {
			FluidStack fs = this.getFluidResult(in);
			if (fs != null && fs.getFluid().equals(result))
				li.add(in.copy());
		}
		return li;
	}

	public boolean isProduct(ItemStack result) {
		return ReikaItemHelper.collectionContainsItemStack(outputs, result);
	}

	public boolean isCentrifugable(ItemStack ingredient) {
		return this.getRecipeResult(ingredient) != null;
	}

	private static class FluidOut {

		private final FluidStack fluid;
		private final float chance;

		private FluidOut(FluidStack fs, float c) {
			fluid = fs;
			chance = c;
		}

		public int getRandomAmount(Random r) {
			return (int)(r.nextFloat()*fluid.amount);
		}

		public int getAmount() {
			return fluid.amount;
		}

	}

	public Collection<ItemStack> getAllCentrifugables() {
		return Collections.unmodifiableCollection(recipeList.keySet());
	}
}
