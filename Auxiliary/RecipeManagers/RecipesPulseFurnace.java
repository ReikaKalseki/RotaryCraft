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
import Reika.RotaryCraft.Registry.ItemRegistry;

public class RecipesPulseFurnace
{
	private static final RecipesPulseFurnace PulseFurnaceBase = new RecipesPulseFurnace();

	/** The list of smelting results. */
	private Map metaSmeltingList = new HashMap();

	private List<ItemStack> outputs = new ArrayList();
	private List<ItemStack> inputs = new ArrayList();

	/**
	 * Used to call methods addSmelting and getSmeltingResult.
	 */
	public static final RecipesPulseFurnace smelting()
	{
		return PulseFurnaceBase;
	}

	private RecipesPulseFurnace()
	{
		this.addSmelting(Block.obsidian.blockID, 0, new ItemStack(RotaryCraft.obsidianglass, 1, 0), 0.7F);
		this.addSmelting(Item.ingotIron.itemID, 0, new ItemStack(RotaryCraft.shaftcraft, 1, 1), 0.4F);	//Iron ingot

		//RECYCLING
		this.addSmelting(Item.helmetChain.itemID, 0, new ItemStack(Item.ingotIron.itemID, 3, 0), 0F);
		this.addSmelting(Item.helmetIron.itemID, 0, new ItemStack(Item.ingotIron.itemID, 5, 0), 0F);
		this.addSmelting(Item.bootsChain.itemID, 0, new ItemStack(Item.ingotIron.itemID, 2, 0), 0F);
		this.addSmelting(Item.bootsIron.itemID, 0, new ItemStack(Item.ingotIron.itemID, 4, 0), 0F);
		this.addSmelting(Item.legsChain.itemID, 0, new ItemStack(Item.ingotIron.itemID, 4, 0), 0F);
		this.addSmelting(Item.legsIron.itemID, 0, new ItemStack(Item.ingotIron.itemID, 7, 0), 0F);
		this.addSmelting(Item.plateChain.itemID, 0, new ItemStack(Item.ingotIron.itemID, 5, 0), 0F);
		this.addSmelting(Item.plateIron.itemID, 0, new ItemStack(Item.ingotIron.itemID, 8, 0), 0F);

		this.addSmelting(Item.axeIron.itemID, 0, new ItemStack(Item.ingotIron.itemID, 3, 0), 0F);
		this.addSmelting(Item.axeGold.itemID, 0, new ItemStack(Item.ingotGold.itemID, 3, 0), 0F);
		this.addSmelting(Item.swordIron.itemID, 0, new ItemStack(Item.ingotIron.itemID, 2, 0), 0F);
		this.addSmelting(Item.swordGold.itemID, 0, new ItemStack(Item.ingotGold.itemID, 2, 0), 0F);
		this.addSmelting(Item.shovelIron.itemID, 0, new ItemStack(Item.ingotIron.itemID, 1, 0), 0F);
		this.addSmelting(Item.shovelGold.itemID, 0, new ItemStack(Item.ingotGold.itemID, 1, 0), 0F);
		this.addSmelting(Item.pickaxeIron.itemID, 0, new ItemStack(Item.ingotIron.itemID, 3, 0), 0F);
		this.addSmelting(Item.pickaxeGold.itemID, 0, new ItemStack(Item.ingotGold.itemID, 3, 0), 0F);
		this.addSmelting(Item.hoeGold.itemID, 0, new ItemStack(Item.ingotGold.itemID, 2, 0), 0F);
		this.addSmelting(Item.hoeIron.itemID, 0, new ItemStack(Item.ingotIron.itemID, 2, 0), 0F);

		this.addSmelting(Item.flintAndSteel.itemID, 0, new ItemStack(Item.ingotIron.itemID, 1, 0), 0F);
		this.addSmelting(Item.bucketEmpty.itemID, 0, new ItemStack(Item.ingotIron.itemID, 3, 0), 0F);
		this.addSmelting(Item.bucketWater.itemID, 0, new ItemStack(Item.ingotIron.itemID, 3, 0), 0F);
		this.addSmelting(Item.bucketLava.itemID, 0, new ItemStack(Item.ingotIron.itemID, 3, 0), 0F);
		this.addSmelting(Item.bucketMilk.itemID, 0, new ItemStack(Item.ingotIron.itemID, 3, 0), 0F);
		this.addSmelting(Item.minecartEmpty.itemID, 0, new ItemStack(Item.ingotIron.itemID, 5, 0), 0F);
		this.addSmelting(Item.doorIron.itemID, 0, new ItemStack(Item.ingotIron.itemID, 6, 0), 0F);
		this.addSmelting(Item.cauldron.itemID, 0, new ItemStack(Item.ingotIron.itemID, 7, 0), 0F);
		this.addSmelting(Item.horseArmorIron.itemID, 0, new ItemStack(Item.ingotIron, 7), 1F);
		this.addSmelting(Item.horseArmorDiamond.itemID, 0, new ItemStack(Item.diamond, 7), 1F);
		this.addSmelting(Item.horseArmorGold.itemID, 0, new ItemStack(Item.ingotGold, 7), 1F);
		this.addSmelting(ItemRegistry.STEELHELMET.getShiftedID(), 0, this.getSizedSteel(5), 0);
		this.addSmelting(ItemRegistry.STEELBOOTS.getShiftedID(), 0, this.getSizedSteel(4), 0);
		this.addSmelting(ItemRegistry.STEELCHEST.getShiftedID(), 0, this.getSizedSteel(8), 0);
		this.addSmelting(ItemRegistry.STEELLEGS.getShiftedID(), 0, this.getSizedSteel(7), 0);
		this.addSmelting(ItemRegistry.STEELAXE.getShiftedID(), 0, this.getSizedSteel(3), 0);
		this.addSmelting(ItemRegistry.STEELPICK.getShiftedID(), 0, this.getSizedSteel(3), 0);
		this.addSmelting(ItemRegistry.STEELSHOVEL.getShiftedID(), 0, this.getSizedSteel(1), 0);


		//addSmelting(RotaryCraft.shaftcraft.itemID, 10, new ItemStack(Item.ingotIron.itemID, 1, 0), 0F);	//scrap
		//addSmelting(RotaryCraft.shaftcraft.itemID, 9, new ItemStack(RotaryCraft.shaftcraft.itemID, 1, 1), 0F);	//Iron scrap
		this.addSmelting(Block.railDetector.blockID, 0, new ItemStack(Item.ingotIron.itemID, 1, 0), 0F);	//1 ingot per block of rail
		this.addSmelting(Block.railPowered.blockID, 0, new ItemStack(Item.ingotGold.itemID, 1, 0), 0F);
	}

	private ItemStack getSizedSteel(int size) {
		return new ItemStack(ItemStacks.steelingot.itemID, size, ItemStacks.steelingot.getItemDamage());
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

		inputs.add(new ItemStack(itemID, 1, metadata));
		outputs.add(itemstack);
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
		return (ItemStack)metaSmeltingList.get(Arrays.asList(item.itemID, item.getItemDamage()));
	}

	public List<ItemStack> getSources(ItemStack result) {
		List<ItemStack> li = new ArrayList();
		for (int i = 0; i < inputs.size(); i++) {
			ItemStack in = inputs.get(i);
			ItemStack out = this.getSmeltingResult(in);
			if (ReikaItemHelper.matchStacks(result, out))
				li.add(in);
		}
		return li;
	}

	public boolean isProduct(ItemStack result) {
		return ReikaItemHelper.listContainsItemStack(outputs, result);
	}

	public boolean isSmeltable(ItemStack ingredient) {
		return this.getSmeltingResult(ingredient) != null;
	}
}
