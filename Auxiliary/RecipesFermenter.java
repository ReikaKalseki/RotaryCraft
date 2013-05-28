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

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import Reika.RotaryCraft.mod_RotaryCraft;

public class RecipesFermenter {
	private static final RecipesFermenter FermenterBase = new RecipesFermenter();
	
	/** The list of smelting results. */
	private Map FermenterList = new HashMap();
	private Map FermenterExperience = new HashMap();
	
	/**
	* Used to call methods addSmelting and getSmeltingResult.
	*/
	public static final RecipesFermenter smelting()
	{
		return FermenterBase;
	}
	
	private RecipesFermenter()
	{
		addSmelting(Item.reed.itemID, new ItemStack(mod_RotaryCraft.powders.itemID, 1, 2), 0.7F);
		addSmelting(Block.tallGrass.blockID, new ItemStack(mod_RotaryCraft.powders.itemID, 2, 2), 0.7F);
		addSmelting(Block.leaves.blockID, new ItemStack(mod_RotaryCraft.powders.itemID, 1, 2), 0.7F);
		addSmelting(Block.waterlily.blockID, new ItemStack(mod_RotaryCraft.powders.itemID, 1, 2), 0.7F);
		addSmelting(Block.sapling.blockID, new ItemStack(mod_RotaryCraft.powders.itemID, 1, 2), 0.7F);
		addSmelting(Block.vine.blockID, new ItemStack(mod_RotaryCraft.powders.itemID, 1, 2), 0.7F);
		addSmelting(Block.plantRed.blockID, new ItemStack(mod_RotaryCraft.powders.itemID, 1, 2), 0.7F);
		addSmelting(Block.plantYellow.blockID, new ItemStack(mod_RotaryCraft.powders.itemID, 1, 2), 0.7F);
	}
	
	/**
	* Adds a smelting recipe.
	*/
	public void addSmelting(int id, ItemStack itemStack, float experience)
	{
		FermenterList.put(Integer.valueOf(id), itemStack);
		this.FermenterExperience.put(Integer.valueOf(itemStack.itemID), Float.valueOf(experience));
	}
	
	/**
	* Returns the smelting result of an item.
	*/
	public ItemStack getSmeltingResult(int id)
	{
		return (ItemStack)FermenterList.get(Integer.valueOf(id));
	}
	
	public Map getSmeltingList()
	{
		return FermenterList;
	}
	
	public float getExperience(int par1)
	{
		return this.FermenterExperience.containsKey(Integer.valueOf(par1)) ? ((Float)this.FermenterExperience.get(Integer.valueOf(par1))).floatValue() : 0.0F;
	}
}
