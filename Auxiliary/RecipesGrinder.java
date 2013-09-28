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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;

public class RecipesGrinder {
	private static final RecipesGrinder GrinderBase = new RecipesGrinder();

	/** The list of smelting results. */
	private Map GrinderList = new HashMap();
	private Map GrinderExperience = new HashMap();
	private ArrayList<ItemStack> products = new ArrayList();
	private HashMap<List<Integer>, ItemStack> recipes = new HashMap();

	/**
	 * Used to call methods addSmelting and getSmeltingResult.
	 */
	public static final RecipesGrinder getRecipes()
	{
		return GrinderBase;
	}

	private RecipesGrinder()
	{
		this.addSmelting(Block.stone, new ItemStack(Block.cobblestone.blockID, 1, 0), 0.2F);
		this.addSmelting(Block.cobblestone, new ItemStack(Block.gravel.blockID, 1, 0), 0.2F);
		this.addSmelting(Block.gravel, new ItemStack(Block.sand.blockID, 1, 0), 0.2F);
		this.addSmelting(Block.glass, new ItemStack(Block.sand.blockID, 1, 0), 0.2F);
		this.addSmelting(Block.sandStone, new ItemStack(Block.sand.blockID, 1, 0), 0.2F);
		this.addSmelting(Block.stairsSandStone, new ItemStack(Block.sand.blockID, 6, 0), 0.2F);
		this.addSmelting(Block.stone, new ItemStack(Block.cobblestone.blockID, 1, 0), 0.2F);
		this.addSmelting(Block.glowStone, new ItemStack(Item.lightStoneDust.itemID, 4, 0), 0F);
		this.addSmelting(Block.stoneBrick, new ItemStack(Block.cobblestone.blockID, 1, 0), 0.2F);
		this.addSmelting(Block.furnaceIdle, new ItemStack(Block.cobblestone.blockID, 8, 0), 0.2F);
		this.addSmelting(Block.brick, new ItemStack(Item.clay.itemID, 4, 0), 0.2F);
		this.addSmelting(Block.stairsBrick, new ItemStack(Item.clay.itemID, 24, 0), 0.2F);
		this.addSmelting(Item.brick, new ItemStack(Item.clay.itemID, 1, 0), 0.2F);
		this.addSmelting(Block.stairsCobblestone, new ItemStack(Block.gravel.blockID, 6, 0), 0.2F);
		this.addSmelting(Block.stairsStoneBrick, new ItemStack(Block.cobblestone.blockID, 1, 0), 0.2F);
		this.addSmelting(Block.netherrack, new ItemStack(RotaryCraft.powders.itemID, 1, ItemStacks.netherrackdust.getItemDamage()), 0.2F); //create a netherrack powder
		this.addSmelting(Block.slowSand, new ItemStack(RotaryCraft.powders.itemID, 1, ItemStacks.tar.getItemDamage()), 0.3F); //create a tar

		this.addSmelting(ItemStacks.anthrablock, new ItemStack(ItemStacks.anthracite.itemID, 1, ItemStacks.anthracite.getItemDamage()), 0.3F);
		this.addSmelting(ItemStacks.lonsblock, new ItemStack(ItemStacks.lonsda.itemID, 1, ItemStacks.lonsda.getItemDamage()), 0.3F);

		this.addSmelting(Block.wood, new ItemStack(ItemStacks.sawdust.itemID, 16, ItemStacks.sawdust.getItemDamage()), 0.3F); //sawdust
		this.addSmelting(Block.planks, new ItemStack(ItemStacks.sawdust.itemID, 4, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addSmelting(Block.music, new ItemStack(ItemStacks.sawdust.itemID, 32, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addSmelting(Block.jukebox, new ItemStack(ItemStacks.sawdust.itemID, 32, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addSmelting(Block.fence, new ItemStack(ItemStacks.sawdust.itemID, 4, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addSmelting(Block.stairsWoodOak, new ItemStack(ItemStacks.sawdust.itemID, 24, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addSmelting(Block.stairsWoodBirch, new ItemStack(ItemStacks.sawdust.itemID, 24, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addSmelting(Block.stairsWoodSpruce, new ItemStack(ItemStacks.sawdust.itemID, 24, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addSmelting(Block.stairsWoodJungle, new ItemStack(ItemStacks.sawdust.itemID, 24, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addSmelting(Block.chest, new ItemStack(ItemStacks.sawdust.itemID, 32, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addSmelting(Block.workbench, new ItemStack(ItemStacks.sawdust.itemID, 16, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addSmelting(Block.ladder, new ItemStack(ItemStacks.sawdust.itemID, 4, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addSmelting(Block.pressurePlatePlanks, new ItemStack(ItemStacks.sawdust.itemID, 8, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addSmelting(Block.pressurePlateStone, new ItemStack(Block.cobblestone.blockID, 2, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addSmelting(Item.bowlEmpty, new ItemStack(ItemStacks.sawdust.itemID, 12, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addSmelting(Item.doorWood, new ItemStack(ItemStacks.sawdust.itemID, 24, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addSmelting(Item.sign, new ItemStack(ItemStacks.sawdust.itemID, 24, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addSmelting(Item.doorWood, new ItemStack(ItemStacks.sawdust.itemID, 24, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addSmelting(Item.stick, new ItemStack(ItemStacks.sawdust.itemID, 2, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addSmelting(Block.trapdoor, new ItemStack(ItemStacks.sawdust.itemID, 24, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addSmelting(Block.fenceGate, new ItemStack(ItemStacks.sawdust.itemID, 16, ItemStacks.sawdust.getItemDamage()), 0.3F);
	}

	/**
	 * Adds a smelting recipe.
	 */
	public void addSmelting(ItemStack in, ItemStack itemStack, float experience)
	{
		GrinderList.put(Integer.valueOf(in.itemID), itemStack);
		GrinderExperience.put(Integer.valueOf(itemStack.itemID), Float.valueOf(experience));
		products.add(itemStack);
		List<Integer> arr = new ArrayList();
		arr.add(itemStack.itemID);
		arr.add(itemStack.getItemDamage());
		recipes.put(arr, in.copy());
	}

	private void addSmelting(Block in, ItemStack itemStack, float experience)
	{
		this.addSmelting(new ItemStack(in), itemStack, experience);
	}

	private void addSmelting(Item in, ItemStack itemStack, float experience)
	{
		this.addSmelting(new ItemStack(in), itemStack, experience);
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

	public boolean isGrindable(ItemStack item) {
		return this.getSmeltingResult(item.itemID) != null;
	}

	public boolean isProduct(ItemStack item) {
		return ReikaItemHelper.listContainsItemStack(products, item);
	}

	public ItemStack getSources(ItemStack out) {
		List<Integer> arr = new ArrayList();
		arr.add(out.itemID);
		arr.add(out.getItemDamage());
		return recipes.get(arr);
	}
}
