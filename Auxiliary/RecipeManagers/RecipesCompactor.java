/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.RecipeManagers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.DifficultyEffects;

public class RecipesCompactor
{
	private static final RecipesCompactor CompactorBase = new RecipesCompactor();

	/** The list of smelting results. */
	private Map metaSmeltingList = new HashMap();
	private List<ItemStack> compactables = new ArrayList();

	/**
	 * Used to call methods addSmelting and getSmeltingResult.
	 */
	public static final RecipesCompactor getRecipes()
	{
		return CompactorBase;
	}

	private RecipesCompactor()
	{
		this.addSmelting(Item.coal, new ItemStack(RotaryCraft.compacts.itemID, this.getNumberPerStep(), 0), 0.2F); //No charcoal
		this.addSmelting(ItemStacks.anthracite, new ItemStack(RotaryCraft.compacts.itemID, this.getNumberPerStep(), 1), 0.2F);
		this.addSmelting(ItemStacks.prismane, new ItemStack(RotaryCraft.compacts.itemID, this.getNumberPerStep(), 2), 0.2F);
		this.addSmelting(ItemStacks.lonsda, new ItemStack(Item.diamond.itemID, this.getNumberPerStep(), 0), 0.2F);

		this.addSmelting(Item.blazePowder, new ItemStack(Block.glowStone.blockID, 1, 0), 0.2F);
	}

	public final int getNumberPerStep() {
		return DifficultyEffects.COMPACTOR.getInt();
	}

	/**
	 * Add a metadata-sensitive furnace recipe
	 * @param itemID The Item ID
	 * @param metadata The Item Metadata
	 * @param itemstack The ItemStack for the result
	 */
	public void addSmelting(ItemStack in, ItemStack itemstack, float xp)
	{
		metaSmeltingList.put(Arrays.asList(in.itemID, in.getItemDamage()), itemstack);
		//this.ExtractorExperience.put(Integer.valueOf(itemStack.itemID), Float.valueOf(xp));
		compactables.add(in.copy());
	}

	public void addSmelting(Item in, ItemStack itemstack, float xp)
	{
		this.addSmelting(new ItemStack(in), itemstack, xp);
	}

	public void addSmelting(Block in, ItemStack itemstack, float xp)
	{
		this.addSmelting(new ItemStack(in), itemstack, xp);
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
		return ret;
	}

	public boolean isCompactable(ItemStack item) {
		return this.getSmeltingResult(item) != null;
	}

	public List<ItemStack> getCompactables() {
		return compactables;
	}

	public ItemStack getSource(ItemStack result) {
		for (int i = 0; i < compactables.size(); i++) {
			ItemStack in = compactables.get(i);
			ItemStack out = this.getSmeltingResult(in);
			if (ReikaItemHelper.matchStacks(result, out))
				return in;
		}
		return null;
	}
}
