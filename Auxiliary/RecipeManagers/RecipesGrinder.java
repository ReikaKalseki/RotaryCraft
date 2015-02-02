/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.RecipeManagers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
import Reika.DragonAPI.ModInteract.AppEngHandler;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.ItemRegistry;

public class RecipesGrinder {
	private static final RecipesGrinder GrinderBase = new RecipesGrinder();

	public static final int ore_rate = 3;

	private final ItemHashMap<ItemStack> recipes = new ItemHashMap().setOneWay();

	public static final RecipesGrinder getRecipes()
	{
		return GrinderBase;
	}

	private RecipesGrinder()
	{
		this.addRecipe(Blocks.stone, new ItemStack(Blocks.cobblestone));
		this.addRecipe(Blocks.cobblestone, new ItemStack(Blocks.gravel));
		this.addRecipe(Blocks.gravel, new ItemStack(Blocks.sand));
		this.addRecipe(Blocks.glass, new ItemStack(Blocks.sand));
		this.addRecipe(Blocks.sandstone, new ItemStack(Blocks.sand));
		this.addRecipe(Blocks.sandstone_stairs, new ItemStack(Blocks.sand, 6, 0));
		this.addRecipe(Blocks.glowstone, new ItemStack(Items.glowstone_dust, 4, 0));
		this.addRecipe(Blocks.stonebrick, new ItemStack(Blocks.cobblestone));
		this.addRecipe(Blocks.furnace, new ItemStack(Blocks.cobblestone, 8, 0));
		this.addRecipe(Blocks.brick_block, new ItemStack(Items.clay_ball, 4, 0));
		this.addRecipe(Blocks.brick_stairs, new ItemStack(Items.clay_ball, 6, 0));
		this.addRecipe(Items.brick, new ItemStack(Items.clay_ball));
		this.addRecipe(Blocks.stone_stairs, new ItemStack(Blocks.gravel, 2, 0));
		this.addRecipe(Blocks.stone_brick_stairs, new ItemStack(Blocks.cobblestone, 2, 0));
		this.addRecipe(Blocks.netherrack, ItemStacks.netherrackdust); //create a netherrack powder
		this.addRecipe(Blocks.soul_sand, ItemStacks.tar); //create a tar
		this.addRecipe(Items.wheat, ReikaItemHelper.getSizedItemStack(ItemStacks.flour, 4));
		this.addRecipe(ItemStacks.bedingot.copy(), ReikaItemHelper.getSizedItemStack(ItemStacks.bedrockdust, 4));
		this.addRecipe(Items.reeds, new ItemStack(Items.sugar, 3));

		this.addRecipe(Blocks.log, this.getSizedSawdust(16)); //sawdust
		this.addRecipe(Blocks.planks, this.getSizedSawdust(4));
		this.addRecipe(Blocks.noteblock, this.getSizedSawdust(32));
		this.addRecipe(Blocks.jukebox, this.getSizedSawdust(32));
		this.addRecipe(Blocks.fence, this.getSizedSawdust(4));
		this.addRecipe(Blocks.oak_stairs, this.getSizedSawdust(6));
		this.addRecipe(Blocks.birch_stairs, this.getSizedSawdust(6));
		this.addRecipe(Blocks.spruce_stairs, this.getSizedSawdust(6));
		this.addRecipe(Blocks.jungle_stairs, this.getSizedSawdust(6));
		this.addRecipe(Blocks.chest, this.getSizedSawdust(32));
		this.addRecipe(Blocks.crafting_table, this.getSizedSawdust(16));
		this.addRecipe(Blocks.ladder, this.getSizedSawdust(4));
		this.addRecipe(Blocks.wooden_pressure_plate, this.getSizedSawdust(8));
		this.addRecipe(Blocks.stone_pressure_plate, new ItemStack(Blocks.cobblestone, 2, ItemStacks.sawdust.getItemDamage()));
		this.addRecipe(Items.bowl, this.getSizedSawdust(ModList.GREGTECH.isLoaded() ? 4 : 12));
		this.addRecipe(Items.wooden_door, this.getSizedSawdust(24));
		this.addRecipe(Items.sign, this.getSizedSawdust(24));
		this.addRecipe(Items.stick, this.getSizedSawdust(2));
		this.addRecipe(Blocks.trapdoor, this.getSizedSawdust(24));
		this.addRecipe(Blocks.fence_gate, this.getSizedSawdust(16));
		this.addRecipe(Items.bone, new ItemStack(Items.dye, 9, 15));
		this.addRecipe(Items.blaze_rod, new ItemStack(Items.blaze_powder, 6, 0));

		/*
		this.addRecipe(Blocks.coal_ore, ItemRegistry.EXTRACTS.getCraftedMetadataProduct(ore_rate, 24));
		this.addRecipe(Blocks.iron_ore, ItemRegistry.EXTRACTS.getCraftedMetadataProduct(ore_rate, 25));
		this.addRecipe(Blocks.gold_ore, ItemRegistry.EXTRACTS.getCraftedMetadataProduct(ore_rate, 26));
		this.addRecipe(Blocks.redstone_ore, ItemRegistry.EXTRACTS.getCraftedMetadataProduct(ore_rate, 27));
		this.addRecipe(Blocks.lapis_ore, ItemRegistry.EXTRACTS.getCraftedMetadataProduct(ore_rate, 28));
		this.addRecipe(Blocks.diamond_ore, ItemRegistry.EXTRACTS.getCraftedMetadataProduct(ore_rate, 29));
		this.addRecipe(Blocks.emerald_ore, ItemRegistry.EXTRACTS.getCraftedMetadataProduct(ore_rate, 30));
		this.addRecipe(Blocks.quartz_ore, ItemRegistry.EXTRACTS.getCraftedMetadataProduct(ore_rate, 31), 0.7F);
		 */

		this.addRecipe(Items.coal, ItemStacks.coaldust);

		this.addRecipe(ItemRegistry.CANOLA.getStackOf(), ItemRegistry.CANOLA.getStackOfMetadata(2));
	}

	private ItemStack getSizedSawdust(int size) {
		return ReikaItemHelper.getSizedItemStack(ItemStacks.sawdust, size);
	}

	public void addModRecipes() {
		if (ModList.APPENG.isLoaded()) {
			ItemStack cry = AppEngHandler.getInstance().getCertusQuartz();
			ItemStack dust = AppEngHandler.getInstance().getCertusQuartzDust();
			if (cry != null && dust != null) {
				this.addRecipe(cry, dust);
			}
			else {
				RotaryCraft.logger.logError("Could not add certus quartz grinding; null itemstack "+cry+", "+dust);
			}
		}

		ArrayList<ItemStack> obsididust = OreDictionary.getOres("dustObsidian");
		if (!obsididust.isEmpty())
			this.addRecipe(Blocks.obsidian, ReikaItemHelper.getSizedItemStack(obsididust.get(0), 6));
	}

	public boolean isGrindable(ItemStack item) {
		return this.getGrindingResult(item) != null;
	}

	public boolean isProduct(ItemStack item) {
		return ReikaItemHelper.collectionContainsItemStack(recipes.values(), item);
	}

	public List<ItemStack> getSources(ItemStack out) {
		List<ItemStack> in = new ArrayList();
		for (ItemStack input : recipes.keySet()) {
			ItemStack is = this.getGrindingResult(input);
			if (is != null) {
				if (ReikaItemHelper.matchStacks(is, out))
					in.add(input.copy());
			}
		}
		return in;
	}

	public void addRecipe(Block b, ItemStack out) {
		this.addRecipe(new ItemStack(b), out);
	}

	public void addRecipe(Item i, ItemStack out) {
		this.addRecipe(new ItemStack(i), out);
	}

	public void addOreDictRecipe(String in, ItemStack out) {
		ArrayList<ItemStack> li = OreDictionary.getOres(in);
		for (ItemStack sin : li) {
			if (!recipes.containsKey(sin))
				this.addRecipe(sin, out);
		}
	}

	public void addRecipe(ItemStack in, ItemStack out) {
		recipes.put(in, out);
		//this.ExtractorExperience.put(Integer.valueOf(itemStack), Float.valueOf(xp));
	}

	public ItemStack getGrindingResult(ItemStack item) {
		if (item == null)
			return null;
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d  %d", Items, item.getItemDamage()));
		ItemStack ret = recipes.get(item);
		return ret != null ? ret.copy() : null;
	}

	public void addOreRecipes() {
		for (int i = 0; i < ModOreList.oreList.length; i++) {
			ModOreList ore = ModOreList.oreList[i];
			Collection<ItemStack> li = ore.getAllOreBlocks();
			for (ItemStack is : li) {
				ItemStack flake = ExtractorModOres.getFlakeProduct(ore);
				this.addRecipe(is, ReikaItemHelper.getSizedItemStack(flake, ore_rate));
				RotaryCraft.logger.log("Adding "+(ore_rate)+"x grinder recipe for "+ore+" ore "+is);
			}
		}

		for (int i = 0; i < ReikaOreHelper.oreList.length; i++) {
			ReikaOreHelper ore = ReikaOreHelper.oreList[i];
			Collection<ItemStack> li = ore.getAllOreBlocks();
			for (ItemStack is : li) {
				ItemStack flake = ItemRegistry.EXTRACTS.getCraftedMetadataProduct(ore_rate, 24+ore.ordinal());
				this.addRecipe(is, ReikaItemHelper.getSizedItemStack(flake, ore_rate));
				RotaryCraft.logger.log("Adding "+(ore_rate)+"x grinder recipe for "+ore+" ore "+is);
			}
		}
	}
}
