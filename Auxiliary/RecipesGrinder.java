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
import Reika.RotaryCraft.RotaryCraft;

public class RecipesGrinder {
	private static final RecipesGrinder GrinderBase = new RecipesGrinder();

	/** The list of smelting results. */
	private Map GrinderList = new HashMap();
	private Map GrinderExperience = new HashMap();

	/**
	* Used to call methods addSmelting and getSmeltingResult.
	*/
	public static final RecipesGrinder smelting()
	{
		return GrinderBase;
	}

	private RecipesGrinder()
	{
		this.addSmelting(Block.stone.blockID, new ItemStack(Block.cobblestone.blockID, 1, 0), 0.2F);
		this.addSmelting(Block.cobblestone.blockID, new ItemStack(Block.gravel.blockID, 1, 0), 0.2F);
		this.addSmelting(Block.gravel.blockID, new ItemStack(Block.sand.blockID, 1, 0), 0.2F);
		this.addSmelting(Block.glass.blockID, new ItemStack(Block.sand.blockID, 1, 0), 0.2F);
		this.addSmelting(Block.sandStone.blockID, new ItemStack(Block.sand.blockID, 1, 0), 0.2F);
		this.addSmelting(Block.stairsSandStone.blockID, new ItemStack(Block.sand.blockID, 6, 0), 0.2F);
		this.addSmelting(Block.stone.blockID, new ItemStack(Block.cobblestone.blockID, 1, 0), 0.2F);
		this.addSmelting(Block.glowStone.blockID, new ItemStack(Item.lightStoneDust.itemID, 4, 0), 0F);
		this.addSmelting(Block.stoneBrick.blockID, new ItemStack(Block.cobblestone.blockID, 1, 0), 0.2F);
		this.addSmelting(Block.furnaceIdle.blockID, new ItemStack(Block.cobblestone.blockID, 8, 0), 0.2F);
		this.addSmelting(Block.brick.blockID, new ItemStack(Item.clay.itemID, 4, 0), 0.2F);
		this.addSmelting(Block.stairsBrick.blockID, new ItemStack(Item.clay.itemID, 24, 0), 0.2F);
		this.addSmelting(Item.brick.itemID, new ItemStack(Item.clay.itemID, 1, 0), 0.2F);
		this.addSmelting(Block.stairsCobblestone.blockID, new ItemStack(Block.gravel.blockID, 6, 0), 0.2F);
		this.addSmelting(Block.stairsStoneBrick.blockID, new ItemStack(Block.cobblestone.blockID, 1, 0), 0.2F);
		this.addSmelting(Block.netherrack.blockID, new ItemStack(RotaryCraft.powders.itemID, 1, ItemStacks.netherrackdust.getItemDamage()), 0.2F); //create a netherrack powder
		this.addSmelting(Block.slowSand.blockID, new ItemStack(RotaryCraft.powders.itemID, 1, ItemStacks.tar.getItemDamage()), 0.3F); //create a tar

		this.addSmelting(ItemStacks.anthrablock.itemID, new ItemStack(ItemStacks.anthracite.itemID, 1, ItemStacks.anthracite.getItemDamage()), 0.3F);
		this.addSmelting(ItemStacks.lonsblock.itemID, new ItemStack(ItemStacks.lonsda.itemID, 1, ItemStacks.lonsda.getItemDamage()), 0.3F);

		this.addSmelting(Block.wood.blockID, new ItemStack(RotaryCraft.powders.itemID, 16, 3), 0.3F); //sawdust
		this.addSmelting(Block.planks.blockID, new ItemStack(RotaryCraft.powders.itemID, 4, 3), 0.3F);
		this.addSmelting(Block.music.blockID, new ItemStack(RotaryCraft.powders.itemID, 32, 3), 0.3F);
		this.addSmelting(Block.jukebox.blockID, new ItemStack(RotaryCraft.powders.itemID, 32, 3), 0.3F);
		this.addSmelting(Block.fence.blockID, new ItemStack(RotaryCraft.powders.itemID, 4, 3), 0.3F);
		this.addSmelting(Block.stairsWoodOak.blockID, new ItemStack(RotaryCraft.powders.itemID, 24, 3), 0.3F);
		this.addSmelting(Block.stairsWoodBirch.blockID, new ItemStack(RotaryCraft.powders.itemID, 24, 3), 0.3F);
		this.addSmelting(Block.stairsWoodSpruce.blockID, new ItemStack(RotaryCraft.powders.itemID, 24, 3), 0.3F);
		this.addSmelting(Block.stairsWoodJungle.blockID, new ItemStack(RotaryCraft.powders.itemID, 24, 3), 0.3F);
		this.addSmelting(Block.chest.blockID, new ItemStack(RotaryCraft.powders.itemID, 32, 3), 0.3F);
		this.addSmelting(Block.workbench.blockID, new ItemStack(RotaryCraft.powders.itemID, 16, 3), 0.3F);
		this.addSmelting(Block.ladder.blockID, new ItemStack(RotaryCraft.powders.itemID, 4, 3), 0.3F);
		this.addSmelting(Block.pressurePlatePlanks.blockID, new ItemStack(RotaryCraft.powders.itemID, 8, 3), 0.3F);
		this.addSmelting(Block.pressurePlateStone.blockID, new ItemStack(Block.cobblestone.blockID, 2, 3), 0.3F);
		this.addSmelting(Item.bowlEmpty.itemID, new ItemStack(RotaryCraft.powders.itemID, 12, 3), 0.3F);
		this.addSmelting(Item.doorWood.itemID, new ItemStack(RotaryCraft.powders.itemID, 24, 3), 0.3F);
		this.addSmelting(Item.sign.itemID, new ItemStack(RotaryCraft.powders.itemID, 24, 3), 0.3F);
		this.addSmelting(Item.doorWood.itemID, new ItemStack(RotaryCraft.powders.itemID, 24, 3), 0.3F);
		this.addSmelting(Item.stick.itemID, new ItemStack(RotaryCraft.powders.itemID, 2, 3), 0.3F);
		this.addSmelting(Block.trapdoor.blockID, new ItemStack(RotaryCraft.powders.itemID, 24, 3), 0.3F);
		this.addSmelting(Block.fenceGate.blockID, new ItemStack(RotaryCraft.powders.itemID, 16, 3), 0.3F);

		//addSmelting(RotaryCraft.canolaseed.itemID, null, 0.7F);
	}

	/**
	* Adds a smelting recipe.
	*/
	public void addSmelting(int id, ItemStack itemStack, float experience)
	{
		GrinderList.put(Integer.valueOf(id), itemStack);
		GrinderExperience.put(Integer.valueOf(itemStack.itemID), Float.valueOf(experience));
	}

	/**
	* Returns the smelting result of an item.
	*/
	public ItemStack getSmeltingResult(int id)
	{
		return (ItemStack)GrinderList.get(Integer.valueOf(id));
	}

	public Map getSmeltingList()
	{
		return GrinderList;
	}

	public float getExperience(int par1)
	{
		return GrinderExperience.containsKey(Integer.valueOf(par1)) ? ((Float)GrinderExperience.get(Integer.valueOf(par1))).floatValue() : 0.0F;
	}
}
