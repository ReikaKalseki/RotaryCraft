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
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.ItemRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipesPulseFurnace
{
	private static final RecipesPulseFurnace PulseFurnaceBase = new RecipesPulseFurnace();

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
		this.addSmelting(Block.obsidian.blockID, new ItemStack(RotaryCraft.blastglass, 1, 0), 0.7F);
		this.addSmelting(Item.ingotIron.itemID, new ItemStack(RotaryCraft.shaftcraft, 1, 1), 0.4F);	//Iron ingot

		//RECYCLING
		this.addSmelting(Item.helmetChain.itemID, new ItemStack(Item.ingotIron.itemID, 3, 0), 0F);
		this.addSmelting(Item.helmetIron.itemID, new ItemStack(Item.ingotIron.itemID, 5, 0), 0F);
		this.addSmelting(Item.bootsChain.itemID, new ItemStack(Item.ingotIron.itemID, 2, 0), 0F);
		this.addSmelting(Item.bootsIron.itemID, new ItemStack(Item.ingotIron.itemID, 4, 0), 0F);
		this.addSmelting(Item.legsChain.itemID, new ItemStack(Item.ingotIron.itemID, 4, 0), 0F);
		this.addSmelting(Item.legsIron.itemID, new ItemStack(Item.ingotIron.itemID, 7, 0), 0F);
		this.addSmelting(Item.plateChain.itemID, new ItemStack(Item.ingotIron.itemID, 5, 0), 0F);
		this.addSmelting(Item.plateIron.itemID, new ItemStack(Item.ingotIron.itemID, 8, 0), 0F);

		this.addSmelting(Item.axeIron.itemID, new ItemStack(Item.ingotIron.itemID, 3, 0), 0F);
		this.addSmelting(Item.axeGold.itemID, new ItemStack(Item.ingotGold.itemID, 3, 0), 0F);
		this.addSmelting(Item.swordIron.itemID, new ItemStack(Item.ingotIron.itemID, 2, 0), 0F);
		this.addSmelting(Item.swordGold.itemID, new ItemStack(Item.ingotGold.itemID, 2, 0), 0F);
		this.addSmelting(Item.shovelIron.itemID, new ItemStack(Item.ingotIron.itemID, 1, 0), 0F);
		this.addSmelting(Item.shovelGold.itemID, new ItemStack(Item.ingotGold.itemID, 1, 0), 0F);
		this.addSmelting(Item.pickaxeIron.itemID, new ItemStack(Item.ingotIron.itemID, 3, 0), 0F);
		this.addSmelting(Item.pickaxeGold.itemID, new ItemStack(Item.ingotGold.itemID, 3, 0), 0F);
		this.addSmelting(Item.hoeGold.itemID, new ItemStack(Item.ingotGold.itemID, 2, 0), 0F);
		this.addSmelting(Item.hoeIron.itemID, new ItemStack(Item.ingotIron.itemID, 2, 0), 0F);

		this.addSmelting(Item.flintAndSteel.itemID, new ItemStack(Item.ingotIron.itemID, 1, 0), 0F);
		this.addSmelting(Item.bucketEmpty.itemID, new ItemStack(Item.ingotIron.itemID, 3, 0), 0F);
		this.addSmelting(Item.bucketWater.itemID, new ItemStack(Item.ingotIron.itemID, 3, 0), 0F);
		this.addSmelting(Item.bucketLava.itemID, new ItemStack(Item.ingotIron.itemID, 3, 0), 0F);
		this.addSmelting(Item.bucketMilk.itemID, new ItemStack(Item.ingotIron.itemID, 3, 0), 0F);
		this.addSmelting(Item.minecartEmpty.itemID, new ItemStack(Item.ingotIron.itemID, 5, 0), 0F);
		this.addSmelting(Item.doorIron.itemID, new ItemStack(Item.ingotIron.itemID, 6, 0), 0F);
		this.addSmelting(Item.cauldron.itemID, new ItemStack(Item.ingotIron.itemID, 7, 0), 0F);
		this.addSmelting(Item.horseArmorIron.itemID, new ItemStack(Item.ingotIron, 7), 1F);
		this.addSmelting(Item.horseArmorDiamond.itemID, new ItemStack(Item.diamond, 7), 1F);
		this.addSmelting(Item.horseArmorGold.itemID, new ItemStack(Item.ingotGold, 7), 1F);
		this.addSmelting(ItemRegistry.STEELHELMET.getShiftedID(), this.getSizedSteel(5), 0);
		this.addSmelting(ItemRegistry.STEELBOOTS.getShiftedID(), this.getSizedSteel(4), 0);
		this.addSmelting(ItemRegistry.STEELCHEST.getShiftedID(), this.getSizedSteel(8), 0);
		this.addSmelting(ItemRegistry.STEELLEGS.getShiftedID(), this.getSizedSteel(7), 0);
		this.addSmelting(ItemRegistry.STEELAXE.getShiftedID(), this.getSizedSteel(3), 0);
		this.addSmelting(ItemRegistry.STEELPICK.getShiftedID(), this.getSizedSteel(3), 0);
		this.addSmelting(ItemRegistry.STEELSHOVEL.getShiftedID(), this.getSizedSteel(1), 0);
		this.addSmelting(ItemRegistry.STEELHOE.getShiftedID(), this.getSizedSteel(2), 0);
		this.addSmelting(ItemRegistry.STEELSHEARS.getShiftedID(), this.getSizedSteel(2), 0);
		this.addSmelting(ItemRegistry.STEELSICKLE.getShiftedID(), this.getSizedSteel(3), 0);

		this.addSmelting(ItemStacks.redgolddust, ItemStacks.redgoldingot, 0.5F);

		//addSmelting(RotaryCraft.shaftcraft.itemID, 10, new ItemStack(Item.ingotIron.itemID, 1, 0), 0F);	//scrap
		//addSmelting(RotaryCraft.shaftcraft.itemID, 9, new ItemStack(RotaryCraft.shaftcraft.itemID, 1, 1), 0F);	//Iron scrap
		this.addSmelting(Block.railDetector.blockID, new ItemStack(Item.ingotIron.itemID, 1, 0), 0F);	//1 ingot per block of rail
		this.addSmelting(Block.railPowered.blockID, new ItemStack(Item.ingotGold.itemID, 1, 0), 0F);

		if (ModList.THERMALEXPANSION.isLoaded()) {
			ItemStack enderdust = GameRegistry.findItemStack(ModList.THERMALEXPANSION.modLabel, "dustEnderium", 1);
			ItemStack enderingot = GameRegistry.findItemStack(ModList.THERMALEXPANSION.modLabel, "ingotEnderium", 1);
			this.addSmelting(enderdust, enderingot, 1);
		}
	}

	private ItemStack getSizedSteel(int size) {
		return new ItemStack(ItemStacks.steelingot.itemID, size, ItemStacks.steelingot.getItemDamage());
	}

	private void addSmelting(ItemStack in, ItemStack itemstack, float xp)
	{
		this.addSmelting(in.itemID, in.getItemDamage(), itemstack, xp);
	}

	private void addSmelting(int itemID, ItemStack itemstack, float xp)
	{
		this.addSmelting(itemID, 0, itemstack, xp);
	}

	private void addSmelting(int itemID, int metadata, ItemStack itemstack, float xp)
	{
		metaSmeltingList.put(Arrays.asList(itemID, metadata), itemstack);

		inputs.add(new ItemStack(itemID, 1, metadata));
		outputs.add(itemstack);
	}

	public ItemStack getSmeltingResult(ItemStack item)
	{
		if (item == null)
			return null;
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
