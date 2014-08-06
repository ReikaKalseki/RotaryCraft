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
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Interfaces.OreType.OreRarity;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.AppEngHandler;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;

public class RecipesGrinder {
	private static final RecipesGrinder GrinderBase = new RecipesGrinder();

	public static final int ore_rate = 3;

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
		this.addRecipe(Block.stone, new ItemStack(Block.cobblestone.blockID, 1, 0), 0.2F);
		this.addRecipe(Block.cobblestone, new ItemStack(Block.gravel.blockID, 1, 0), 0.2F);
		this.addRecipe(Block.gravel, new ItemStack(Block.sand.blockID, 1, 0), 0.2F);
		this.addRecipe(Block.glass, new ItemStack(Block.sand.blockID, 1, 0), 0.2F);
		this.addRecipe(Block.sandStone, new ItemStack(Block.sand.blockID, 1, 0), 0.2F);
		this.addRecipe(Block.stairsSandStone, new ItemStack(Block.sand.blockID, 6, 0), 0.2F);
		this.addRecipe(Block.stone, new ItemStack(Block.cobblestone.blockID, 1, 0), 0.2F);
		this.addRecipe(Block.glowStone, new ItemStack(Item.glowstone.itemID, 4, 0), 0F);
		this.addRecipe(Block.stoneBrick, new ItemStack(Block.cobblestone.blockID, 1, 0), 0.2F);
		this.addRecipe(Block.furnaceIdle, new ItemStack(Block.cobblestone.blockID, 8, 0), 0.2F);
		this.addRecipe(Block.brick, new ItemStack(Item.clay.itemID, 4, 0), 0.2F);
		this.addRecipe(Block.stairsBrick, new ItemStack(Item.clay.itemID, 6, 0), 0.2F);
		this.addRecipe(Item.brick, new ItemStack(Item.clay.itemID, 1, 0), 0.2F);
		this.addRecipe(Block.stairsCobblestone, new ItemStack(Block.gravel.blockID, 2, 0), 0.2F);
		this.addRecipe(Block.stairsStoneBrick, new ItemStack(Block.cobblestone.blockID, 2, 0), 0.2F);
		this.addRecipe(Block.netherrack, new ItemStack(RotaryCraft.powders.itemID, 1, ItemStacks.netherrackdust.getItemDamage()), 0.2F); //create a netherrack powder
		this.addRecipe(Block.slowSand, new ItemStack(RotaryCraft.powders.itemID, 1, ItemStacks.tar.getItemDamage()), 0.3F); //create a tar
		this.addRecipe(Item.wheat, new ItemStack(ItemStacks.flour.itemID, 4, ItemStacks.flour.getItemDamage()), 0.1F);
		this.addRecipe(ItemStacks.bedingot.copy(), new ItemStack(ItemStacks.bedrockdust.itemID, 4, ItemStacks.bedrockdust.getItemDamage()), 0.5F);
		this.addRecipe(Item.reed, new ItemStack(Item.sugar, 3), 0.2F);

		this.addRecipe(Block.wood, new ItemStack(ItemStacks.sawdust.itemID, 16, ItemStacks.sawdust.getItemDamage()), 0.3F); //sawdust
		this.addRecipe(Block.planks, new ItemStack(ItemStacks.sawdust.itemID, 4, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addRecipe(Block.music, new ItemStack(ItemStacks.sawdust.itemID, 32, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addRecipe(Block.jukebox, new ItemStack(ItemStacks.sawdust.itemID, 32, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addRecipe(Block.fence, new ItemStack(ItemStacks.sawdust.itemID, 4, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addRecipe(Block.stairsWoodOak, new ItemStack(ItemStacks.sawdust.itemID, 6, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addRecipe(Block.stairsWoodBirch, new ItemStack(ItemStacks.sawdust.itemID, 6, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addRecipe(Block.stairsWoodSpruce, new ItemStack(ItemStacks.sawdust.itemID, 6, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addRecipe(Block.stairsWoodJungle, new ItemStack(ItemStacks.sawdust.itemID, 6, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addRecipe(Block.chest, new ItemStack(ItemStacks.sawdust.itemID, 32, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addRecipe(Block.workbench, new ItemStack(ItemStacks.sawdust.itemID, 16, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addRecipe(Block.ladder, new ItemStack(ItemStacks.sawdust.itemID, 4, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addRecipe(Block.pressurePlatePlanks, new ItemStack(ItemStacks.sawdust.itemID, 8, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addRecipe(Block.pressurePlateStone, new ItemStack(Block.cobblestone.blockID, 2, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addRecipe(Item.bowlEmpty, new ItemStack(ItemStacks.sawdust.itemID, 12, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addRecipe(Item.doorWood, new ItemStack(ItemStacks.sawdust.itemID, 24, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addRecipe(Item.sign, new ItemStack(ItemStacks.sawdust.itemID, 24, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addRecipe(Item.doorWood, new ItemStack(ItemStacks.sawdust.itemID, 24, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addRecipe(Item.stick, new ItemStack(ItemStacks.sawdust.itemID, 2, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addRecipe(Block.trapdoor, new ItemStack(ItemStacks.sawdust.itemID, 24, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addRecipe(Block.fenceGate, new ItemStack(ItemStacks.sawdust.itemID, 16, ItemStacks.sawdust.getItemDamage()), 0.3F);
		this.addRecipe(Item.bone, new ItemStack(Item.dyePowder.itemID, 9, 15), 0.3F);
		this.addRecipe(Item.blazeRod, new ItemStack(Item.blazePowder.itemID, 6, 0), 0.6F);

		this.addRecipe(Block.oreCoal, new ItemStack(RotaryCraft.extracts.itemID, ore_rate, 24), 0F);
		this.addRecipe(Block.oreIron, new ItemStack(RotaryCraft.extracts.itemID, ore_rate, 25), 0F);
		this.addRecipe(Block.oreGold, new ItemStack(RotaryCraft.extracts.itemID, ore_rate, 26), 0F);
		this.addRecipe(Block.oreRedstone, new ItemStack(RotaryCraft.extracts.itemID, ore_rate, 27), 0F);
		this.addRecipe(Block.oreLapis, new ItemStack(RotaryCraft.extracts.itemID, ore_rate, 28), 0F);
		this.addRecipe(Block.oreDiamond, new ItemStack(RotaryCraft.extracts.itemID, ore_rate, 29), 0F);
		this.addRecipe(Block.oreEmerald, new ItemStack(RotaryCraft.extracts.itemID, ore_rate, 30), 0F);
		this.addRecipe(Block.oreNetherQuartz, new ItemStack(RotaryCraft.extracts.itemID, ore_rate, 31), 0.7F);

		this.addRecipe(Item.coal, ItemStacks.coaldust, 0);
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
		List<Integer> arr = new ArrayList();
		arr.add(out.itemID);
		arr.add(out.getItemDamage());
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
		metaSmeltingList.put(Arrays.asList(in.itemID, in.getItemDamage()), out);
		//this.ExtractorExperience.put(Integer.valueOf(itemStack.itemID), Float.valueOf(xp));

		products.add(out);
		ingredients.add(in);
		List<Integer> arr = new ArrayList();
		arr.add(out.itemID);
		arr.add(out.getItemDamage());
	}

	public ItemStack getGrindingResult(ItemStack item)
	{
		if (item == null)
			return null;
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d  %d", item.itemID, item.getItemDamage()));
		ItemStack ret = (ItemStack)metaSmeltingList.get(Arrays.asList(item.itemID, item.getItemDamage()));
		if (ret != null)
			return ret;
		return (ItemStack)metaSmeltingList.get(Integer.valueOf(item.itemID));
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
