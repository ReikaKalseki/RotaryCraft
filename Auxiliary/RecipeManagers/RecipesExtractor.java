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
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Instantiable.Data.Maps.MultiMap;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.CustomExtractLoader;
import Reika.RotaryCraft.Auxiliary.CustomExtractLoader.CustomExtractEntry;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.ExtractorModOres.ExtractorStage;
import Reika.RotaryCraft.ModInterface.ItemCustomModOre;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;

public class RecipesExtractor
{
	private static final RecipesExtractor instance = new RecipesExtractor();

	private final ItemHashMap<ItemStack> recipeList = new ItemHashMap().setOneWay();
	private final MultiMap<ReikaOreHelper, String> modOres = new MultiMap();

	public static final RecipesExtractor recipes()
	{
		return instance;
	}

	private RecipesExtractor()
	{
		for (int i = 0; i < 24; i++)
			this.addRecipe(ItemRegistry.EXTRACTS.getStackOfMetadata(i), ItemRegistry.EXTRACTS.getStackOfMetadata(i+8));

		this.addRecipe(Blocks.coal_ore, 0, getFlake(ReikaOreHelper.COAL));
		this.addRecipe(Blocks.iron_ore, 0, getFlake(ReikaOreHelper.IRON));
		this.addRecipe(Blocks.gold_ore, 0, getFlake(ReikaOreHelper.GOLD));
		this.addRecipe(Blocks.redstone_ore, 0, getFlake(ReikaOreHelper.REDSTONE));
		this.addRecipe(Blocks.lapis_ore, 0, getFlake(ReikaOreHelper.LAPIS));
		this.addRecipe(Blocks.diamond_ore, 0, getFlake(ReikaOreHelper.DIAMOND));
		this.addRecipe(Blocks.emerald_ore, 0, getFlake(ReikaOreHelper.EMERALD));
		this.addRecipe(Blocks.quartz_ore, 0, getFlake(ReikaOreHelper.QUARTZ));

		if (ConfigRegistry.GREGORES.getState()) {
			modOres.addValue(ReikaOreHelper.IRON, "oreBandedIron");
			modOres.addValue(ReikaOreHelper.IRON, "oreBrownLimonite");
			modOres.addValue(ReikaOreHelper.IRON, "oreNetherrackBrownLimonite");
			modOres.addValue(ReikaOreHelper.IRON, "oreEndstoneBrownLimonite");
			modOres.addValue(ReikaOreHelper.IRON, "oreBlackgraniteBrownLimonite");
			modOres.addValue(ReikaOreHelper.IRON, "oreRedgraniteBrownLimonite");
			modOres.addValue(ReikaOreHelper.IRON, "oreYellowLimonite");
			modOres.addValue(ReikaOreHelper.IRON, "oreNetherrackYellowLimonite");
			modOres.addValue(ReikaOreHelper.IRON, "oreEndstoneYellowLimonite");
			modOres.addValue(ReikaOreHelper.IRON, "oreBlackgraniteYellowLimonite");
			modOres.addValue(ReikaOreHelper.IRON, "oreRedgraniteYellowLimonite");
			modOres.addValue(ReikaOreHelper.IRON, "oreBandedIron");
			modOres.addValue(ReikaOreHelper.IRON, "oreNetherrackBandedIron");
			modOres.addValue(ReikaOreHelper.IRON, "oreEndstoneBandedIron");
			modOres.addValue(ReikaOreHelper.IRON, "oreBlackgraniteBandedIron");
			modOres.addValue(ReikaOreHelper.IRON, "oreRedgraniteBandedIron");
		}

		this.addModRecipes();
	}

	private void addModRecipes() {
		for (ReikaOreHelper ore : modOres.keySet()) {
			Collection<String> c = modOres.get(ore);
			for (String s : c) {
				ArrayList<ItemStack> li = OreDictionary.getOres(s);
				for (ItemStack is : li) {
					this.addRecipe(is, getFlake(ore));
					RotaryCraft.logger.log("Adding mod ore "+is+" as "+ore+" because its ore type "+s+" is a subcategory of "+ore);
				}
			}
		}

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
		return is.getItemDamage() < 8;
	}

	public static boolean isSlurry(ItemStack is) {
		if (!ItemRegistry.EXTRACTS.matchItem(is))
			return false;
		return is.getItemDamage() < 16 && is.getItemDamage() >= 8;
	}

	public static boolean isSolution(ItemStack is) {
		if (!ItemRegistry.EXTRACTS.matchItem(is))
			return false;
		return is.getItemDamage() < 24 && is.getItemDamage() >= 16;
	}

	public static boolean isFlakes(ItemStack is) {
		if (!ItemRegistry.EXTRACTS.matchItem(is))
			return false;
		return is.getItemDamage() < 32 && is.getItemDamage() >= 24 || ReikaItemHelper.matchStacks(is, ItemStacks.silverflakes);
	}

	private static ItemStack getFlake(ReikaOreHelper ore) {
		return ItemRegistry.EXTRACTS.getStackOfMetadata(ore.ordinal());
	}

	public static ReikaOreHelper getOreFromExtract(ItemStack item) {
		return ReikaOreHelper.oreList[item.getItemDamage()%8];
	}
}
