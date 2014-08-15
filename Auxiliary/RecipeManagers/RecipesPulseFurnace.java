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

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.Data.ItemHashMap;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipesPulseFurnace
{
	private static final RecipesPulseFurnace PulseFurnaceBase = new RecipesPulseFurnace();

	private ItemHashMap<ItemStack> recipes = new ItemHashMap();
	private ItemHashMap<Float> experience = new ItemHashMap();

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
		this.addSmelting(Blocks.obsidian, BlockRegistry.BLASTGLASS.getCraftedProduct(1), 0.7F);
		this.addSmelting(Items.iron_ingot, ItemStacks.steelingot, 0.4F);	//Iron ingot

		//RECYCLING
		this.addSmelting(Items.chainmail_helmet, new ItemStack(Items.iron_ingot, 3, 0), 0F);
		this.addSmelting(Items.iron_helmet, new ItemStack(Items.iron_ingot, 5, 0), 0F);
		this.addSmelting(Items.chainmail_boots, new ItemStack(Items.iron_ingot, 2, 0), 0F);
		this.addSmelting(Items.iron_boots, new ItemStack(Items.iron_ingot, 4, 0), 0F);
		this.addSmelting(Items.chainmail_leggings, new ItemStack(Items.iron_ingot, 4, 0), 0F);
		this.addSmelting(Items.iron_leggings, new ItemStack(Items.iron_ingot, 7, 0), 0F);
		this.addSmelting(Items.chainmail_chestplate, new ItemStack(Items.iron_ingot, 5, 0), 0F);
		this.addSmelting(Items.iron_chestplate, new ItemStack(Items.iron_ingot, 8, 0), 0F);

		this.addSmelting(Items.iron_axe, new ItemStack(Items.iron_ingot, 3, 0), 0F);
		this.addSmelting(Items.golden_axe, new ItemStack(Items.gold_ingot, 3, 0), 0F);
		this.addSmelting(Items.iron_sword, new ItemStack(Items.iron_ingot, 2, 0), 0F);
		this.addSmelting(Items.golden_sword, new ItemStack(Items.gold_ingot, 2, 0), 0F);
		this.addSmelting(Items.iron_shovel, new ItemStack(Items.iron_ingot, 1, 0), 0F);
		this.addSmelting(Items.golden_shovel, new ItemStack(Items.gold_ingot, 1, 0), 0F);
		this.addSmelting(Items.iron_pickaxe, new ItemStack(Items.iron_ingot, 3, 0), 0F);
		this.addSmelting(Items.golden_pickaxe, new ItemStack(Items.gold_ingot, 3, 0), 0F);
		this.addSmelting(Items.golden_hoe, new ItemStack(Items.gold_ingot, 2, 0), 0F);
		this.addSmelting(Items.iron_hoe, new ItemStack(Items.iron_ingot, 2, 0), 0F);

		this.addSmelting(Items.flint_and_steel, new ItemStack(Items.iron_ingot, 1, 0), 0F);
		this.addSmelting(Items.bucket, new ItemStack(Items.iron_ingot, 3, 0), 0F);
		this.addSmelting(Items.water_bucket, new ItemStack(Items.iron_ingot, 3, 0), 0F);
		this.addSmelting(Items.lava_bucket, new ItemStack(Items.iron_ingot, 3, 0), 0F);
		this.addSmelting(Items.milk_bucket, new ItemStack(Items.iron_ingot, 3, 0), 0F);
		this.addSmelting(Items.minecart, new ItemStack(Items.iron_ingot, 5, 0), 0F);
		this.addSmelting(Items.iron_door, new ItemStack(Items.iron_ingot, 6, 0), 0F);
		this.addSmelting(Items.cauldron, new ItemStack(Items.iron_ingot, 7, 0), 0F);
		this.addSmelting(Items.iron_horse_armor, new ItemStack(Items.iron_ingot, 7), 1F);
		this.addSmelting(Items.diamond_horse_armor, new ItemStack(Items.diamond, 7), 1F);
		this.addSmelting(Items.golden_horse_armor, new ItemStack(Items.gold_ingot, 7), 1F);
		this.addSmelting(ItemRegistry.STEELHELMET.getItemInstance(), this.getSizedSteel(5), 0);
		this.addSmelting(ItemRegistry.STEELBOOTS.getItemInstance(), this.getSizedSteel(4), 0);
		this.addSmelting(ItemRegistry.STEELCHEST.getItemInstance(), this.getSizedSteel(8), 0);
		this.addSmelting(ItemRegistry.STEELLEGS.getItemInstance(), this.getSizedSteel(7), 0);
		this.addSmelting(ItemRegistry.STEELAXE.getItemInstance(), this.getSizedSteel(3), 0);
		this.addSmelting(ItemRegistry.STEELPICK.getItemInstance(), this.getSizedSteel(3), 0);
		this.addSmelting(ItemRegistry.STEELSHOVEL.getItemInstance(), this.getSizedSteel(1), 0);
		this.addSmelting(ItemRegistry.STEELHOE.getItemInstance(), this.getSizedSteel(2), 0);
		this.addSmelting(ItemRegistry.STEELSHEARS.getItemInstance(), this.getSizedSteel(2), 0);
		this.addSmelting(ItemRegistry.STEELSICKLE.getItemInstance(), this.getSizedSteel(3), 0);

		this.addSmelting(ItemStacks.redgolddust, ItemStacks.redgoldingot, 0.5F);

		//addSmelting(RotaryCraft.shaftcraft, 10, new ItemStack(Items.iron_ingot, 1, 0), 0F);	//scrap
		//addSmelting(RotaryCraft.shaftcraft, 9, new ItemStack(RotaryCraft.shaftcraft, 1, 1), 0F);	//Iron scrap
		this.addSmelting(Blocks.detector_rail, new ItemStack(Items.iron_ingot, 1, 0), 0F);	//1 ingot per block of rail
		this.addSmelting(Blocks.golden_rail, new ItemStack(Items.gold_ingot, 1, 0), 0F);

		if (ModList.THERMALEXPANSION.isLoaded()) {
			ItemStack enderdust = GameRegistry.findItemStack(ModList.THERMALEXPANSION.modLabel, "dustEnderium", 1);
			ItemStack enderingot = GameRegistry.findItemStack(ModList.THERMALEXPANSION.modLabel, "ingotEnderium", 1);
			this.addSmelting(enderdust, enderingot, 1);
		}
	}

	private ItemStack getSizedSteel(int size) {
		return ReikaItemHelper.getSizedItemStack(ItemStacks.steelingot, size);
	}

	private void addSmelting(ItemStack in, ItemStack itemstack, float xp) {
		recipes.put(in, itemstack);
		experience.put(in, xp);

		inputs.add(in);
		outputs.add(itemstack);
	}

	private void addSmelting(Block b, ItemStack itemstack, float xp)
	{
		this.addSmelting(b, 0, itemstack, xp);
	}

	private void addSmelting(Block b, int metadata, ItemStack itemstack, float xp)
	{
		this.addSmelting(new ItemStack(b, metadata), itemstack, xp);
	}

	private void addSmelting(Item i, ItemStack itemstack, float xp)
	{
		this.addSmelting(i, 0, itemstack, xp);
	}

	private void addSmelting(Item i, int metadata, ItemStack itemstack, float xp)
	{
		this.addSmelting(new ItemStack(i, metadata), itemstack, xp);
	}

	public ItemStack getSmeltingResult(ItemStack item)
	{
		if (item == null)
			return null;
		return recipes.get(item);
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