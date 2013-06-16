/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import Reika.RotaryCraft.RotaryCraft;

public class RecipesCompactor
{
	private static final RecipesCompactor CompactorBase = new RecipesCompactor();

	/** The list of smelting results. */
	private Map smeltingList = new HashMap();
	private Map metaSmeltingList = new HashMap();

	/**
	 * Used to call methods addSmelting and getSmeltingResult.
	 */
	public static final RecipesCompactor smelting()
	{
		return CompactorBase;
	}

	private RecipesCompactor()
	{
		this.addSmelting(Item.coal.itemID, 0, new ItemStack(RotaryCraft.compacts.itemID, 2, 0), 0.2F); //No charcoal
		this.addSmelting(RotaryCraft.compacts.itemID, 0, new ItemStack(RotaryCraft.compacts.itemID, 2, 1), 0.2F);
		this.addSmelting(RotaryCraft.compacts.itemID, 1, new ItemStack(RotaryCraft.compacts.itemID, 2, 2), 0.2F);
		this.addSmelting(RotaryCraft.compacts.itemID, 2, new ItemStack(Item.diamond.itemID, 2, 0), 0.2F);
		this.addSmelting(Item.blazePowder.itemID, 0, new ItemStack(Block.glowStone.blockID, 1, 0), 0.2F);
	}

	/** Adds a smelting recipe. */
	@Deprecated
	public void addSmelting(int par1, ItemStack par2ItemStack)
	{
		smeltingList.put(Integer.valueOf(par1), par2ItemStack);
	}

	/**
	 * Returns the smelting result of an item.
	 * Deprecated in favor of a metadata sensitive version
	 */
	@Deprecated
	public ItemStack getSmeltingResult(int par1)
	{
		return (ItemStack)smeltingList.get(Integer.valueOf(par1));
	}

	public Map getSmeltingList()
	{
		return smeltingList;
	}

	/**
	 * Add a metadata-sensitive furnace recipe
	 * @param itemID The Item ID
	 * @param metadata The Item Metadata
	 * @param itemstack The ItemStack for the result
	 */
	public void addSmelting(int itemID, int metadata, ItemStack itemstack, float xp)
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
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d  %d", item.itemID, item.getItemDamage()));
		ItemStack ret = (ItemStack)metaSmeltingList.get(Arrays.asList(item.itemID, item.getItemDamage()));
		if (ret != null)
			return ret;
		return (ItemStack)smeltingList.get(Integer.valueOf(item.itemID));
	}
}
