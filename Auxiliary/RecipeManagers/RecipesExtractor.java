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

import java.util.Collection;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.Auxiliary.CustomExtractLoader;
import Reika.RotaryCraft.Auxiliary.CustomExtractLoader.CustomExtractEntry;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.ExtractorModOres.ExtractorStage;
import Reika.RotaryCraft.ModInterface.ItemCustomModOre;
import Reika.RotaryCraft.Registry.ItemRegistry;

public class RecipesExtractor
{
	private static final RecipesExtractor instance = new RecipesExtractor();

	private final ItemHashMap<ItemStack> recipeList = new ItemHashMap().setOneWay();

	public static final RecipesExtractor recipes()
	{
		return instance;
	}

	private RecipesExtractor()
	{
		for (int i = 0; i < 24; i++)
			this.addRecipe(ItemRegistry.EXTRACTS.getStackOfMetadata(i), ItemRegistry.EXTRACTS.getStackOfMetadata(i+8));

		this.addRecipe(Blocks.coal_ore, 0, ItemStacks.getFlake(ReikaOreHelper.COAL));
		this.addRecipe(Blocks.iron_ore, 0, ItemStacks.getFlake(ReikaOreHelper.IRON));
		this.addRecipe(Blocks.gold_ore, 0, ItemStacks.getFlake(ReikaOreHelper.GOLD));
		this.addRecipe(Blocks.redstone_ore, 0, ItemStacks.getFlake(ReikaOreHelper.REDSTONE));
		this.addRecipe(Blocks.lapis_ore, 0, ItemStacks.getFlake(ReikaOreHelper.LAPIS));
		this.addRecipe(Blocks.diamond_ore, 0, ItemStacks.getFlake(ReikaOreHelper.DIAMOND));
		this.addRecipe(Blocks.emerald_ore, 0, ItemStacks.getFlake(ReikaOreHelper.EMERALD));
		this.addRecipe(Blocks.quartz_ore, 0, ItemStacks.getFlake(ReikaOreHelper.QUARTZ));



		this.addModRecipes();
	}

	private void addModRecipes() {
		for (int i = 0; i < ModOreList.oreList.length; i++) {
			ModOreList ore = ModOreList.oreList[i];
			Collection<ItemStack> c = ore.getAllOreBlocks();
			for (ItemStack is : c) {
				this.addRecipe(is, ExtractorModOres.getDustProduct(ore));
			}
			this.addRecipe(ExtractorModOres.getDustProduct(ore), ExtractorModOres.getSlurryProduct(ore));
			this.addRecipe(ExtractorModOres.getSlurryProduct(ore), ExtractorModOres.getSolutionProduct(ore));
			this.addRecipe(ExtractorModOres.getSolutionProduct(ore), ExtractorModOres.getFlakeProduct(ore));
		}

		List<CustomExtractEntry> li = CustomExtractLoader.instance.getEntries();
		for (int i = 0; i < li.size(); i++) {
			CustomExtractEntry e = li.get(i);
			Collection<ItemStack> c = e.getAllOreBlocks();
			for (ItemStack is : c) {
				this.addRecipe(is, ItemCustomModOre.getItem(i, ExtractorStage.DUST));
			}
			this.addRecipe(ItemCustomModOre.getItem(i, ExtractorStage.DUST), ItemCustomModOre.getItem(i, ExtractorStage.SLURRY));
			this.addRecipe(ItemCustomModOre.getItem(i, ExtractorStage.SLURRY), ItemCustomModOre.getItem(i, ExtractorStage.SOLUTION));
			this.addRecipe(ItemCustomModOre.getItem(i, ExtractorStage.SOLUTION), ItemCustomModOre.getItem(i, ExtractorStage.FLAKES));
		}
	}

	private void addRecipe(Block in, ItemStack out)
	{
		this.addRecipe(new ItemStack(in), out);
	}

	private void addRecipe(Block in, int meta, ItemStack out)
	{
		this.addRecipe(new ItemStack(in, 1, meta), out);
	}

	private void addRecipe(Item in, ItemStack out)
	{
		this.addRecipe(new ItemStack(in), out);
	}

	private void addRecipe(Item in, int dmg, ItemStack out)
	{
		this.addRecipe(new ItemStack(in, 1, dmg), out);
	}

	private void addRecipe(ItemStack in, ItemStack out)
	{
		recipeList.put(in, out);
	}

	public ItemStack getExtractionResult(ItemStack item)
	{
		if (item == null)
			return null;
		ReikaOreHelper ore = ReikaOreHelper.getEntryByOreDict(item);
		if (ore != null) {
			item = ore.getOreBlock();
		}
		ItemStack ret = recipeList.get(item);
		return ret != null ? ret.copy() : null;
	}

	public static boolean isDust(ItemStack is) {
		if (!ItemRegistry.EXTRACTS.matchItem(is))
			return false;
		int dmg = is.getItemDamage();
		return dmg < ReikaOreHelper.oreList.length;
	}

	public static boolean isSlurry(ItemStack is) {
		if (!ItemRegistry.EXTRACTS.matchItem(is))
			return false;
		int dmg = is.getItemDamage();
		return dmg < ReikaOreHelper.oreList.length*2 && dmg >= ReikaOreHelper.oreList.length;
	}

	public static boolean isSolution(ItemStack is) {
		if (!ItemRegistry.EXTRACTS.matchItem(is))
			return false;
		int dmg = is.getItemDamage();
		return dmg < ReikaOreHelper.oreList.length*3 && dmg >= ReikaOreHelper.oreList.length*2;
	}

	public static boolean isFlakes(ItemStack is) {
		if (!ItemRegistry.EXTRACTS.matchItem(is))
			return false;
		if (ReikaItemHelper.matchStacks(is, ItemStacks.silverflakes) || ReikaItemHelper.matchStacks(is, ItemStacks.tungstenflakes))
			return true;
		int dmg = is.getItemDamage();
		return dmg < ReikaOreHelper.oreList.length*4 && dmg >= ReikaOreHelper.oreList.length*3;
	}

	public static ReikaOreHelper getOreFromExtract(ItemStack item) {
		return ReikaOreHelper.oreList[item.getItemDamage()%ReikaOreHelper.oreList.length];
	}
}
