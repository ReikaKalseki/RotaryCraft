/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.RecipeManagers;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.Instantiable.Data.ItemHashMap;
import Reika.DragonAPI.Instantiable.Data.MultiMap;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;

public class RecipesExtractor
{
	private static final RecipesExtractor ExtractorBase = new RecipesExtractor();

	/** The list of smelting results. */
	private ItemHashMap<ItemStack> recipeList = new ItemHashMap();
	private final MultiMap<ReikaOreHelper, String> modOres = new MultiMap();

	/**
	 * Used to call methods addSmelting and getSmeltingResult.
	 */
	public static final RecipesExtractor recipes()
	{
		return ExtractorBase;
	}

	private RecipesExtractor()
	{
		for (int i = 0; i < 24; i++)
			this.addRecipe(ItemRegistry.EXTRACTS.getStackOfMetadata(i), ItemRegistry.EXTRACTS.getStackOfMetadata(i+8), 0.7F);

		this.addRecipe(Blocks.coal_ore, 0, getFlake(ReikaOreHelper.COAL), 0F);
		this.addRecipe(Blocks.iron_ore, 0, getFlake(ReikaOreHelper.IRON), 0F);
		this.addRecipe(Blocks.gold_ore, 0, getFlake(ReikaOreHelper.GOLD), 0F);
		this.addRecipe(Blocks.redstone_ore, 0, getFlake(ReikaOreHelper.REDSTONE), 0F);
		this.addRecipe(Blocks.lapis_ore, 0, getFlake(ReikaOreHelper.LAPIS), 0F);
		this.addRecipe(Blocks.diamond_ore, 0, getFlake(ReikaOreHelper.DIAMOND), 0F);
		this.addRecipe(Blocks.emerald_ore, 0, getFlake(ReikaOreHelper.EMERALD), 0F);
		this.addRecipe(Blocks.quartz_ore, 0, getFlake(ReikaOreHelper.QUARTZ), 0F);

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
	}

	public void addModRecipes() {
		for (ReikaOreHelper ore : modOres.keySet()) {
			Collection<String> c = modOres.get(ore);
			for (String s : c) {
				ArrayList<ItemStack> li = OreDictionary.getOres(s);
				for (ItemStack is : li) {
					this.addRecipe(is, getFlake(ore), 0);
					RotaryCraft.logger.log("Adding mod ore "+is+" as "+ore+" because its ore type "+s+" is a subcategory of "+ore);
				}
			}
		}
	}

	private void addRecipe(Block in, ItemStack out, float xp)
	{
		this.addRecipe(new ItemStack(in), out, xp);
	}

	private void addRecipe(Block in, int meta, ItemStack out, float xp)
	{
		this.addRecipe(new ItemStack(in, 1, meta), out, xp);
	}

	private void addRecipe(Item in, ItemStack out, float xp)
	{
		this.addRecipe(new ItemStack(in), out, xp);
	}

	private void addRecipe(Item in, int dmg, ItemStack out, float xp)
	{
		this.addRecipe(new ItemStack(in, 1, dmg), out, xp);
	}

	private void addRecipe(ItemStack in, ItemStack out, float xp)
	{
		recipeList.put(in, out);
		//this.ExtractorExperience.put(Integer.valueOf(itemstack.getItem), Float.valueOf(xp));
	}

	/**
	 * Used to get the resulting ItemStack form a source ItemStack
	 * @param item The Source ItemStack
	 * @return The result ItemStack
	 */
	public ItemStack getSmeltingResult(ItemStack item)
	{
		if (item == null)
			return null;
		ReikaOreHelper ore = ReikaOreHelper.getEntryByOreDict(item);
		if (ore != null) {
			item = ore.getOreBlock();
		}
		ItemStack ret = recipeList.get(item);
		return ret;
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
