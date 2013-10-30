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
import java.util.Arrays;
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
	private Map metaSmeltingList = new HashMap();

	private ArrayList<ItemStack> products = new ArrayList();
	private ArrayList<ItemStack> ingredients = new ArrayList();

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
		this.addSmelting(Block.glowStone, new ItemStack(Item.glowstone.itemID, 4, 0), 0F);
		this.addSmelting(Block.stoneBrick, new ItemStack(Block.cobblestone.blockID, 1, 0), 0.2F);
		this.addSmelting(Block.furnaceIdle, new ItemStack(Block.cobblestone.blockID, 8, 0), 0.2F);
		this.addSmelting(Block.brick, new ItemStack(Item.clay.itemID, 4, 0), 0.2F);
		this.addSmelting(Block.stairsBrick, new ItemStack(Item.clay.itemID, 24, 0), 0.2F);
		this.addSmelting(Item.brick, new ItemStack(Item.clay.itemID, 1, 0), 0.2F);
		this.addSmelting(Block.stairsCobblestone, new ItemStack(Block.gravel.blockID, 6, 0), 0.2F);
		this.addSmelting(Block.stairsStoneBrick, new ItemStack(Block.cobblestone.blockID, 1, 0), 0.2F);
		this.addSmelting(Block.netherrack, new ItemStack(RotaryCraft.powders.itemID, 1, ItemStacks.netherrackdust.getItemDamage()), 0.2F); //create a netherrack powder
		this.addSmelting(Block.slowSand, new ItemStack(RotaryCraft.powders.itemID, 1, ItemStacks.tar.getItemDamage()), 0.3F); //create a tar

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

	public boolean isGrindable(ItemStack item) {
		return this.getSmeltingResult(item) != null;
	}

	public boolean isProduct(ItemStack item) {
		return ReikaItemHelper.listContainsItemStack(products, item);
	}

	public List<ItemStack> getSources(ItemStack out) {
		List<Integer> arr = new ArrayList();
		arr.add(out.itemID);
		arr.add(out.getItemDamage());
		List<ItemStack> in = new ArrayList();
		for (int i = 0; i < ingredients.size(); i++) {
			ItemStack is = this.getSmeltingResult(ingredients.get(i));
			if (is != null) {
				if (ReikaItemHelper.matchStacks(is, out))
					in.add(ingredients.get(i));
			}
		}
		return in;
	}

	public Map getSmeltingList()
	{
		return metaSmeltingList;
	}

	public void addSmelting(Block b, ItemStack out, float xp) {
		this.addSmelting(new ItemStack(b), out, xp);
	}

	public void addSmelting(Item i, ItemStack out, float xp) {
		this.addSmelting(new ItemStack(i), out, xp);
	}

	public void addSmelting(ItemStack in, ItemStack out, float xp)
	{
		metaSmeltingList.put(Arrays.asList(in.itemID, in.getItemDamage()), out);
		//this.ExtractorExperience.put(Integer.valueOf(itemStack.itemID), Float.valueOf(xp));

		products.add(out);
		ingredients.add(in);
		List<Integer> arr = new ArrayList();
		arr.add(out.itemID);
		arr.add(out.getItemDamage());
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
		return (ItemStack)metaSmeltingList.get(Integer.valueOf(item.itemID));
	}
}
