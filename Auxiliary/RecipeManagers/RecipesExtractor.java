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

import Reika.DragonAPI.Instantiable.Data.ItemHashMap;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.ItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipesExtractor
{
	private static final RecipesExtractor ExtractorBase = new RecipesExtractor();

	/** The list of smelting results. */
	private ItemHashMap<ItemStack> recipeList = new ItemHashMap();

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

		this.addRecipe(Blocks.coal_ore, 0, ItemRegistry.EXTRACTS.getStackOfMetadata(0), 0F);
		this.addRecipe(Blocks.iron_ore, 0, ItemRegistry.EXTRACTS.getStackOfMetadata(1), 0F);
		this.addRecipe(Blocks.gold_ore, 0, ItemRegistry.EXTRACTS.getStackOfMetadata(2), 0F);
		this.addRecipe(Blocks.redstone_ore, 0, ItemRegistry.EXTRACTS.getStackOfMetadata(3), 0F);
		this.addRecipe(Blocks.lapis_ore, 0, ItemRegistry.EXTRACTS.getStackOfMetadata(4), 0F);
		this.addRecipe(Blocks.diamond_ore, 0, ItemRegistry.EXTRACTS.getStackOfMetadata(5), 0F);
		this.addRecipe(Blocks.emerald_ore, 0, ItemRegistry.EXTRACTS.getStackOfMetadata(6), 0F);
		this.addRecipe(Blocks.quartz_ore, 0, ItemRegistry.EXTRACTS.getStackOfMetadata(7), 0.7F);
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

	public static ReikaOreHelper getOreFromExtract(ItemStack item) {
		return ReikaOreHelper.oreList[item.getItemDamage()%8];
	}
}