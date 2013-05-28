/*******************************************************************************
 * @author Reika
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
import net.minecraft.item.ItemStack;
import Reika.RotaryCraft.RotaryCraft;

public class RecipesExtractor
{
	private static final RecipesExtractor ExtractorBase = new RecipesExtractor();
	
    /** The list of smelting results. */
    private Map smeltingList = new HashMap();
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
			addSmelting(RotaryCraft.extracts.itemID, i, new ItemStack(RotaryCraft.extracts.itemID, 1, i+8), 0.7F);
		
		addSmelting(Block.oreCoal.blockID, 0, new ItemStack(RotaryCraft.extracts.itemID, 1, 0), 0F);
		addSmelting(Block.oreIron.blockID, 0, new ItemStack(RotaryCraft.extracts.itemID, 1, 1), 0F);
		addSmelting(Block.oreGold.blockID, 0, new ItemStack(RotaryCraft.extracts.itemID, 1, 2), 0F);
		addSmelting(Block.oreRedstone.blockID, 0, new ItemStack(RotaryCraft.extracts.itemID, 1, 3), 0F);
		addSmelting(Block.oreLapis.blockID, 0, new ItemStack(RotaryCraft.extracts.itemID, 1, 4), 0F);
		addSmelting(Block.oreDiamond.blockID, 0, new ItemStack(RotaryCraft.extracts.itemID, 1, 5), 0F);
		addSmelting(Block.oreEmerald.blockID, 0, new ItemStack(RotaryCraft.extracts.itemID, 1, 6), 0F);
		//addSmelting(Block.oreNether.blockID, 0, new ItemStack(RotaryCraft.extracts.itemID, 1, 7), 0.7F);
	}
	
    /** Adds a smelting recipe. */
	@Deprecated
    public void addSmelting(int par1, ItemStack par2ItemStack)
    {
        this.smeltingList.put(Integer.valueOf(par1), par2ItemStack);
    }

    /**
     * Returns the smelting result of an item.
     * Deprecated in favor of a metadata sensitive version
     */
    @Deprecated
    public ItemStack getSmeltingResult(int par1)
    {
        return (ItemStack)this.smeltingList.get(Integer.valueOf(par1));
    }

    public Map getSmeltingList()
    {
        return this.smeltingList;
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
