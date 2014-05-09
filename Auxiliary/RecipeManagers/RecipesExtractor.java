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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;

public class RecipesExtractor
{
	private static final RecipesExtractor ExtractorBase = new RecipesExtractor();

	/** The list of smelting results. */
	private Map metaSmeltingList = new HashMap();

	/**
	 * Used to call methods addSmelting and getSmeltingResult.
	 */
	public static final RecipesExtractor smelting()
	{
		return ExtractorBase;
	}

	private RecipesExtractor()
	{
		for (int i = 0; i < 24; i++)
			this.addSmelting(RotaryCraft.extracts.itemID, i, new ItemStack(RotaryCraft.extracts.itemID, 1, i+8), 0.7F);

		this.addSmelting(Block.oreCoal.blockID, 0, new ItemStack(RotaryCraft.extracts.itemID, 1, 0), 0F);
		this.addSmelting(Block.oreIron.blockID, 0, new ItemStack(RotaryCraft.extracts.itemID, 1, 1), 0F);
		this.addSmelting(Block.oreGold.blockID, 0, new ItemStack(RotaryCraft.extracts.itemID, 1, 2), 0F);
		this.addSmelting(Block.oreRedstone.blockID, 0, new ItemStack(RotaryCraft.extracts.itemID, 1, 3), 0F);
		this.addSmelting(Block.oreLapis.blockID, 0, new ItemStack(RotaryCraft.extracts.itemID, 1, 4), 0F);
		this.addSmelting(Block.oreDiamond.blockID, 0, new ItemStack(RotaryCraft.extracts.itemID, 1, 5), 0F);
		this.addSmelting(Block.oreEmerald.blockID, 0, new ItemStack(RotaryCraft.extracts.itemID, 1, 6), 0F);
		this.addSmelting(Block.oreNetherQuartz.blockID, 0, new ItemStack(RotaryCraft.extracts.itemID, 1, 7), 0.7F);
	}

	/**
	 * Add a metadata-sensitive furnace recipe
	 * @param itemID The Item ID
	 * @param metadata The Item Metadata
	 * @param itemstack The ItemStack for the result
	 */
	private void addSmelting(int itemID, int metadata, ItemStack itemstack, float xp)
	{
		metaSmeltingList.put(Arrays.asList(itemID, metadata), itemstack);
		//this.ExtractorExperience.put(Integer.valueOf(itemStack.itemID), Float.valueOf(xp));
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
		ItemStack ret = (ItemStack)metaSmeltingList.get(Arrays.asList(item.itemID, item.getItemDamage()));
		return ret;
	}

	public static boolean isDust(ItemStack is) {
		if (is.itemID != RotaryCraft.extracts.itemID)
			return false;
		return is.getItemDamage() < 8;
	}

	public static boolean isSlurry(ItemStack is) {
		if (is.itemID != RotaryCraft.extracts.itemID)
			return false;
		return is.getItemDamage() < 16 && is.getItemDamage() >= 8;
	}

	public static boolean isSolution(ItemStack is) {
		if (is.itemID != RotaryCraft.extracts.itemID)
			return false;
		return is.getItemDamage() < 24 && is.getItemDamage() >= 16;
	}

	public static boolean isFlakes(ItemStack is) {
		if (is.itemID != RotaryCraft.extracts.itemID)
			return false;
		return is.getItemDamage() < 32 && is.getItemDamage() >= 24 || ReikaItemHelper.matchStacks(is, ItemStacks.silverflakes);
	}

	public static ReikaOreHelper getOreFromExtract(ItemStack item) {
		return ReikaOreHelper.oreList[item.getItemDamage()%8];
	}
}
