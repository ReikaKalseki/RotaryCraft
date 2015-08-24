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
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.ReikaXPFluidHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.MagicCropHandler.EssenceType;
import Reika.DragonAPI.ModInteract.ItemHandlers.OreBerryBushHandler;
import Reika.DragonAPI.ModInteract.RecipeHandlers.ForestryRecipeHelper;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.API.RecipeInterface;
import Reika.RotaryCraft.API.RecipeInterface.CentrifugeManager;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class RecipesCentrifuge extends RecipeHandler implements CentrifugeManager {

	private static final RecipesCentrifuge CentrifugeBase = new RecipesCentrifuge();

	private ItemHashMap<CentrifugeRecipe> recipeList = new ItemHashMap();

	private ArrayList<ItemStack> outputs = new ArrayList();

	public static final RecipesCentrifuge getRecipes()
	{
		return CentrifugeBase;
	}

	private RecipesCentrifuge() {
		super(MachineRegistry.CENTRIFUGE);
		RecipeInterface.centrifuge = this;

		this.addRecipe(Items.magma_cream, null, RecipeLevel.PERIPHERAL, Items.slime_ball, 100, Items.blaze_powder, 100);
		this.addRecipe(Items.melon, null, RecipeLevel.PERIPHERAL, new ItemStack(Items.melon_seeds, 4), 100);
		this.addRecipe(Blocks.pumpkin, null, RecipeLevel.PERIPHERAL, new ItemStack(Items.pumpkin_seeds, 12), 100);
		this.addRecipe(Items.wheat, null, RecipeLevel.PERIPHERAL, new ItemStack(Items.wheat_seeds, 4), 100);
		this.addRecipe(Blocks.gravel, null, RecipeLevel.PERIPHERAL, new ItemStack(Items.flint), 50, new ItemStack(Blocks.sand), 75);
		this.addRecipe(ItemStacks.netherrackdust, null, RecipeLevel.PERIPHERAL, new ItemStack(Items.glowstone_dust), 25, new ItemStack(Items.gunpowder), 80, ExtractorModOres.getDustProduct(ModOreList.SULFUR), 40);
		this.addRecipe(Blocks.dirt, null, RecipeLevel.PERIPHERAL, new ItemStack(Blocks.sand), 80, new ItemStack(Blocks.clay), 10);
		this.addRecipe(Items.blaze_powder, null, RecipeLevel.PERIPHERAL, new ItemStack(Items.gunpowder), 100, ExtractorModOres.getSmeltedIngot(ModOreList.SULFUR), 75);

		this.addRecipe(ItemStacks.slipperyComb, new FluidStack(FluidRegistry.getFluid("lubricant"), 50), 60, RecipeLevel.PROTECTED, ItemStacks.slipperyPropolis, 80);
		this.addRecipe(ItemStacks.slipperyPropolis, new FluidStack(FluidRegistry.getFluid("lubricant"), 150), 100, RecipeLevel.PROTECTED);

		int amt = ReikaMathLibrary.roundUpToX(10, (int)(DifficultyEffects.CANOLA.getAverageAmount()*0.75F));
		this.addRecipe(ItemStacks.canolaHusks, new FluidStack(FluidRegistry.getFluid("lubricant"), amt), 100, RecipeLevel.CORE);
	}

	public static class CentrifugeRecipe implements MachineRecipe {

		private final ItemStack in;
		private final ChancedOutputList out;
		private final FluidOut fluid;

		private CentrifugeRecipe(ItemStack is, ChancedOutputList li, FluidOut fs) {
			in = is;
			out = li;
			fluid = fs;
		}

		@Override
		public String getUniqueID() {
			return fullID(in)+">"+out.toString()+"&"+(fluid != null ? fluid.toString() : "X");
		}

		@Override
		public String getAllInfo() {
			return "Centrifuge "+fullID(in)+" to items["+out+"] and fluid "+fluid;
		}

		@Override
		public Collection<ItemStack> getAllUsedItems() {
			ArrayList<ItemStack> li = new ArrayList(out.keySet());
			li.add(in);
			return li;
		}

	}

	private void addRecipe(ItemStack in, ChancedOutputList out, FluidOut fs, RecipeLevel rl)
	{
		out.lock();
		for (ItemStack isout : out.keySet())
			if (!ReikaItemHelper.collectionContainsItemStack(outputs, isout))
				outputs.add(isout);
		CentrifugeRecipe rec = new CentrifugeRecipe(in, out, fs);
		recipeList.put(in, rec);
		this.onAddRecipe(rec, rl);
	}

	private void addRecipe(ItemStack in, FluidOut fs, RecipeLevel rl, Object... items)
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
		this.addRecipe(in, li, fs, rl);
	}

	public void addAPIRecipe(ItemStack item, FluidStack fs, float fc, Object... items)
	{
		this.addRecipe(item, fs, fc, RecipeLevel.API, items);
	}

	private void addRecipe(ItemStack item, FluidStack fs, float fc, RecipeLevel rl, Object... items)
	{
		this.addRecipe(item, new FluidOut(fs, fc), rl, items);
	}

	private void addRecipe(Block item, FluidOut fs, RecipeLevel rl, Object... items)
	{
		this.addRecipe(new ItemStack(item), fs, rl, items);
	}

	private void addRecipe(Item item, FluidOut fs, RecipeLevel rl, Object... items) {
		this.addRecipe(new ItemStack(item), fs, rl, items);
	}

	public ChancedOutputList getRecipeResult(ItemStack item) {
		CentrifugeRecipe cr = item != null ? recipeList.get(item) : null;
		return cr != null ? cr.out : null;
	}

	private Collection<ItemStack> getRecipeOutputs(ItemStack item) {
		CentrifugeRecipe cr = item != null ? recipeList.get(item) : null;
		return cr != null ? cr.out.keySet() : null;
	}

	public float getItemChance(ItemStack in, ItemStack out) {
		ChancedOutputList c = this.getRecipeResult(in);
		return c.getItemChance(out);
	}

	private FluidOut getFluidOut(ItemStack item) {
		CentrifugeRecipe cr = item != null ? recipeList.get(item) : null;
		return cr != null ? cr.fluid : null;
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
		for (ItemStack in : recipeList.keySet()) {
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

	public Collection<ItemStack> getAllCentrifugables() {
		return Collections.unmodifiableCollection(recipeList.keySet());
	}

	@Override
	public void addPostLoadRecipes() {

		if (ModList.FORESTRY.isLoaded()) {
			Map<ItemStack, ChancedOutputList> centrifuge = ForestryRecipeHelper.getInstance().getCentrifugeRecipes();
			for (ItemStack in : centrifuge.keySet()) {
				ChancedOutputList out = centrifuge.get(in).copy();
				out.manipulateChances(new ChanceExponentiator(3));
				out.manipulateChances(new ChanceRounder());
				this.addRecipe(in, out, null, RecipeLevel.MODINTERACT);
			}
		}

		if (ReikaItemHelper.oreItemsExist("dustLead", "dustSilver")) {
			ItemStack lead = OreDictionary.getOres("dustLead").get(0);
			ItemStack silver = OreDictionary.getOres("dustSilver").get(0);
			this.addRecipe(ExtractorModOres.getSmeltedIngot(ModOreList.GALENA), null, RecipeLevel.PERIPHERAL, lead, 100, silver, 100);
		}

		if (ModList.TINKERER.isLoaded()) {
			ItemStack berry = OreBerryBushHandler.BerryTypes.XP.getStack();
			if (berry != null && ReikaXPFluidHelper.fluidsExist()) {
				FluidStack fs = ReikaXPFluidHelper.getFluid(30);
				this.addRecipe(berry, fs, 100, RecipeLevel.MODINTERACT);
			}
		}

		if (ModList.MAGICCROPS.isLoaded()) {
			//ItemStack drop = LegacyMagicCropHandler.getInstance().dropID != null ? new ItemStack(LegacyMagicCropHandler.getInstance().dropID) : null;
			//if (drop == null)
			ItemStack drop = EssenceType.XP.getEssence();
			if (drop != null && ReikaXPFluidHelper.fluidsExist()) {
				FluidStack fs = ReikaXPFluidHelper.getFluid(5);
				this.addRecipe(drop, fs, 100, RecipeLevel.MODINTERACT);
			}
		}
	}

	@Override
	protected boolean removeRecipe(MachineRecipe recipe) {
		return recipeList.removeValue((CentrifugeRecipe)recipe);
	}

	private static class ChanceRounder implements ChanceManipulator {

		private ChanceRounder() {

		}

		@Override
		public float getChance(float original) { //round up to nearest 0.5F
			return Math.round(2D*original)/2F;
		}
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

		@Override
		public final String toString() {
			return fluid.amount+" mB of "+fluid.getFluid().getName()+" ("+chance+"%)";
		}

	}
}
