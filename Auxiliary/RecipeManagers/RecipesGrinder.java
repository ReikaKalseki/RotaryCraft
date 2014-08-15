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
import Reika.DragonAPI.Interfaces.OreType.OreRarity;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.AppEngHandler;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.ItemRegistry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipesGrinder {
	private static final RecipesGrinder GrinderBase = new RecipesGrinder();

	public static final int ore_rate = 3;

	/** The list of smelting results. */
	private ItemHashMap<ItemStack> recipes = new ItemHashMap();

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
		this.addRecipe(Blocks.stone, new ItemStack(Blocks.cobblestone, 1, 0), 0.2F);
		this.addRecipe(Blocks.cobblestone, new ItemStack(Blocks.gravel, 1, 0), 0.2F);
		this.addRecipe(Blocks.gravel, new ItemStack(Blocks.sand, 1, 0), 0.2F);
		this.addRecipe(Blocks.glass, new ItemStack(Blocks.sand, 1, 0), 0.2F);
		this.addRecipe(Blocks.sandstone, new ItemStack(Blocks.sand, 1, 0), 0.2F);
		this.addRecipe(Blocks.sandstone_stairs, new ItemStack(Blocks.sand, 6, 0), 0.2F);
		this.addRecipe(Blocks.stone, new ItemStack(Blocks.cobblestone, 1, 0), 0.2F);
		this.addRecipe(Blocks.glowstone, new ItemStack(Items.glowstone_dust, 4, 0), 0F);
		this.addRecipe(Blocks.stonebrick, new ItemStack(Blocks.cobblestone, 1, 0), 0.2F);
		this.addRecipe(Blocks.furnace, new ItemStack(Blocks.cobblestone, 8, 0), 0.2F);
		this.addRecipe(Blocks.brick_block, new ItemStack(Items.clay_ball, 4, 0), 0.2F);
		this.addRecipe(Blocks.brick_stairs, new ItemStack(Items.clay_ball, 6, 0), 0.2F);
		this.addRecipe(Items.brick, new ItemStack(Items.clay_ball, 1, 0), 0.2F);
		this.addRecipe(Blocks.stone_stairs, new ItemStack(Blocks.gravel, 2, 0), 0.2F);
		this.addRecipe(Blocks.stone_brick_stairs, new ItemStack(Blocks.cobblestone, 2, 0), 0.2F);
		this.addRecipe(Blocks.netherrack, ItemStacks.netherrackdust, 0.2F); //create a netherrack powder
		this.addRecipe(Blocks.soul_sand, ItemStacks.tar, 0.3F); //create a tar
		this.addRecipe(Items.wheat, ReikaItemHelper.getSizedItemStack(ItemStacks.flour, 4), 0.1F);
		this.addRecipe(ItemStacks.bedingot.copy(), ReikaItemHelper.getSizedItemStack(ItemStacks.bedrockdust, 4), 0.5F);
		this.addRecipe(Items.reeds, new ItemStack(Items.sugar, 3), 0.2F);

		this.addRecipe(Blocks.log, this.getSizedSawdust(16), 0.3F); //sawdust
		this.addRecipe(Blocks.planks, this.getSizedSawdust(4), 0.3F);
		this.addRecipe(Blocks.noteblock, this.getSizedSawdust(32), 0.3F);
		this.addRecipe(Blocks.jukebox, this.getSizedSawdust(32), 0.3F);
		this.addRecipe(Blocks.fence, this.getSizedSawdust(4), 0.3F);
		this.addRecipe(Blocks.oak_stairs, this.getSizedSawdust(6), 0.3F);
		this.addRecipe(Blocks.birch_stairs, this.getSizedSawdust(6), 0.3F);
		this.addRecipe(Blocks.spruce_stairs, this.getSizedSawdust(6), 0.3F);
		this.addRecipe(Blocks.jungle_stairs, this.getSizedSawdust(6), 0.3F);
		this.addRecipe(Blocks.chest, this.getSizedSawdust(32), 0.3F);
		this.addRecipe(Blocks.crafting_table, this.getSizedSawdust(16), 0.3F);
		this.addRecipe(Blocks.ladder, this.getSizedSawdust(4), 0.3F);
		this.addRecipe(Blocks.wooden_pressure_plate, this.getSizedSawdust(8), 0.3F);
		this.addRecipe(Blocks.stone_pressure_plate, new ItemStack(Blocks.cobblestone, 2, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addRecipe(Items.bowl, this.getSizedSawdust(12), 0.3F);
		this.addRecipe(Items.wooden_door, this.getSizedSawdust(24), 0.3F);
		this.addRecipe(Items.sign, this.getSizedSawdust(24), 0.3F);
		this.addRecipe(Items.stick, this.getSizedSawdust(2), 0.3F);
		this.addRecipe(Blocks.trapdoor, this.getSizedSawdust(24), 0.3F);
		this.addRecipe(Blocks.fence_gate, this.getSizedSawdust(16), 0.3F);
		this.addRecipe(Items.bone, new ItemStack(Items.dye, 9, 15), 0.3F);
		this.addRecipe(Items.blaze_rod, new ItemStack(Items.blaze_powder, 6, 0), 0.6F);

		this.addRecipe(Blocks.coal_ore, ItemRegistry.EXTRACTS.getCraftedMetadataProduct(ore_rate, 24), 0F);
		this.addRecipe(Blocks.iron_ore, ItemRegistry.EXTRACTS.getCraftedMetadataProduct(ore_rate, 25), 0F);
		this.addRecipe(Blocks.gold_ore, ItemRegistry.EXTRACTS.getCraftedMetadataProduct(ore_rate, 26), 0F);
		this.addRecipe(Blocks.redstone_ore, ItemRegistry.EXTRACTS.getCraftedMetadataProduct(ore_rate, 27), 0F);
		this.addRecipe(Blocks.lapis_ore, ItemRegistry.EXTRACTS.getCraftedMetadataProduct(ore_rate, 28), 0F);
		this.addRecipe(Blocks.diamond_ore, ItemRegistry.EXTRACTS.getCraftedMetadataProduct(ore_rate, 29), 0F);
		this.addRecipe(Blocks.emerald_ore, ItemRegistry.EXTRACTS.getCraftedMetadataProduct(ore_rate, 30), 0F);
		this.addRecipe(Blocks.quartz_ore, ItemRegistry.EXTRACTS.getCraftedMetadataProduct(ore_rate, 31), 0.7F);

		this.addRecipe(Items.coal, ItemStacks.coaldust, 0);
	}

	private ItemStack getSizedSawdust(int size) {
		return ReikaItemHelper.getSizedItemStack(ItemStacks.sawdust, size);
	}

	public void addModRecipes() {
		if (ModList.APPENG.isLoaded()) {
			ItemStack cry = AppEngHandler.getInstance().getCertusQuartz();
			ItemStack dust = AppEngHandler.getInstance().getCertusQuartzDust();
			this.addRecipe(cry, dust, 0);
		}
	}

	public boolean isGrindable(ItemStack item) {
		return this.getGrindingResult(item) != null;
	}

	public boolean isProduct(ItemStack item) {
		return ReikaItemHelper.listContainsItemStack(products, item);
	}

	public List<ItemStack> getSources(ItemStack out) {
		List<ItemStack> in = new ArrayList();
		for (int i = 0; i < ingredients.size(); i++) {
			ItemStack is = this.getGrindingResult(ingredients.get(i));
			if (is != null) {
				if (ReikaItemHelper.matchStacks(is, out))
					in.add(ingredients.get(i));
			}
		}
		return in;
	}

	public void addRecipe(Block b, ItemStack out, float xp) {
		this.addRecipe(new ItemStack(b), out, xp);
	}

	public void addRecipe(Item i, ItemStack out, float xp) {
		this.addRecipe(new ItemStack(i), out, xp);
	}

	public void addOreDictRecipe(String in, ItemStack out, float xp) {
		ArrayList<ItemStack> li = OreDictionary.getOres(in);
		for (int i = 0; i < li.size(); i++)
			this.addRecipe(li.get(i), out, xp);
	}

	public void addRecipe(ItemStack in, ItemStack out, float xp)
	{
		recipes.put(in, out);
		//this.ExtractorExperience.put(Integer.valueOf(itemStack), Float.valueOf(xp));

		products.add(out);
		ingredients.add(in);
	}

	public ItemStack getGrindingResult(ItemStack item)
	{
		if (item == null)
			return null;
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d  %d", Items, item.getItemDamage()));
		return recipes.get(item);
	}

	public void addOreRecipes() {
		for (int i = 0; i < ModOreList.oreList.length; i++) {
			ModOreList ore = ModOreList.oreList[i];
			ArrayList<ItemStack> li = ore.getAllOreBlocks();
			for (int k = 0; k < li.size(); k++) {
				ItemStack is = li.get(k);
				ItemStack flake = ExtractorModOres.getFlakeProduct(ore);
				int amt = ore.getRarity() == OreRarity.RARE ? 4 : ore.isNether() ? 2 : 1;
				this.addRecipe(is, ReikaItemHelper.getSizedItemStack(flake, ore_rate*amt), 1F);
				RotaryCraft.logger.log("Adding "+(ore_rate)+"x grinder recipe for "+ore+" ore "+is);
			}
		}
	}
}