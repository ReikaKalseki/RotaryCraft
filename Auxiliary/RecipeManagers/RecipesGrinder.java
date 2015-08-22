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
import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.AppEngHandler;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.RecipeInterface;
import Reika.RotaryCraft.API.RecipeInterface.GrinderManager;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class RecipesGrinder extends RecipeHandler implements GrinderManager {

	private static final RecipesGrinder GrinderBase = new RecipesGrinder();

	public static final int ore_rate = 3;

	private final ItemHashMap<GrinderRecipe> recipes = new ItemHashMap();

	public static final RecipesGrinder getRecipes()
	{
		return GrinderBase;
	}

	private RecipesGrinder() {
		super(MachineRegistry.GRINDER);
		RecipeInterface.grinder = this;

		this.addRecipe(Blocks.stone, new ItemStack(Blocks.cobblestone), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.cobblestone, new ItemStack(Blocks.gravel), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.gravel, new ItemStack(Blocks.sand), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.glass, new ItemStack(Blocks.sand), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.sandstone, new ItemStack(Blocks.sand), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.sandstone_stairs, new ItemStack(Blocks.sand, 6, 0), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.glowstone, new ItemStack(Items.glowstone_dust, 4, 0), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.stonebrick, new ItemStack(Blocks.cobblestone), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.furnace, new ItemStack(Blocks.cobblestone, 8, 0), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.brick_block, new ItemStack(Items.clay_ball, 4, 0), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.brick_stairs, new ItemStack(Items.clay_ball, 6, 0), RecipeLevel.PERIPHERAL);
		this.addRecipe(Items.brick, new ItemStack(Items.clay_ball), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.stone_stairs, new ItemStack(Blocks.gravel, 2, 0), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.stone_brick_stairs, new ItemStack(Blocks.cobblestone, 2, 0), RecipeLevel.PERIPHERAL);

		this.addRecipe(Blocks.netherrack, ItemStacks.netherrackdust, RecipeLevel.CORE); //create a netherrack powder
		this.addRecipe(Blocks.soul_sand, ItemStacks.tar, RecipeLevel.CORE); //create a tar

		this.addRecipe(Items.wheat, ReikaItemHelper.getSizedItemStack(ItemStacks.flour, 4), RecipeLevel.PERIPHERAL);
		this.addRecipe(ItemStacks.bedingot.copy(), ReikaItemHelper.getSizedItemStack(ItemStacks.bedrockdust, 4), RecipeLevel.CORE);

		this.addRecipe(Items.reeds, new ItemStack(Items.sugar, 3), RecipeLevel.PROTECTED);//, ReikaItemHelper.getSizedItemStack(ItemStacks.mulch, PlantMaterials.SUGARCANE.getPlantValue()));
		this.addRecipe(Items.bone, new ItemStack(Items.dye, 9, 15), RecipeLevel.PROTECTED);
		this.addRecipe(Items.blaze_rod, new ItemStack(Items.blaze_powder, 6, 0), RecipeLevel.PROTECTED);

		this.addRecipe(Blocks.log, this.getSizedSawdust(16), RecipeLevel.PERIPHERAL); //sawdust
		this.addRecipe(Blocks.planks, this.getSizedSawdust(4), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.noteblock, this.getSizedSawdust(32), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.jukebox, this.getSizedSawdust(32), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.fence, this.getSizedSawdust(4), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.oak_stairs, this.getSizedSawdust(6), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.birch_stairs, this.getSizedSawdust(6), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.spruce_stairs, this.getSizedSawdust(6), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.jungle_stairs, this.getSizedSawdust(6), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.chest, this.getSizedSawdust(32), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.crafting_table, this.getSizedSawdust(16), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.ladder, this.getSizedSawdust(4), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.wooden_pressure_plate, this.getSizedSawdust(8), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.stone_pressure_plate, new ItemStack(Blocks.cobblestone, 2, 0), RecipeLevel.PERIPHERAL);
		this.addRecipe(Items.bowl, this.getSizedSawdust(ModList.GREGTECH.isLoaded() ? 4 : 12), RecipeLevel.PERIPHERAL);
		this.addRecipe(Items.wooden_door, this.getSizedSawdust(24), RecipeLevel.PERIPHERAL);
		this.addRecipe(Items.sign, this.getSizedSawdust(24), RecipeLevel.PERIPHERAL);
		this.addRecipe(Items.stick, this.getSizedSawdust(2), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.trapdoor, this.getSizedSawdust(24), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.fence_gate, this.getSizedSawdust(16), RecipeLevel.PERIPHERAL);

		this.addRecipe(Items.coal, ItemStacks.coaldust, RecipeLevel.CORE);

		this.addRecipe(ItemStacks.canolaSeeds, ItemStacks.canolaHusks, RecipeLevel.CORE);

		this.addOreDictRecipe("plankWood", this.getSizedSawdust(4), RecipeLevel.PERIPHERAL);
		this.addOreDictRecipe("logWood", this.getSizedSawdust(16), RecipeLevel.PERIPHERAL);
	}

	private static class GrinderRecipe implements MachineRecipe {

		private final ItemStack input;
		private final ItemStack output;

		private GrinderRecipe(ItemStack in, ItemStack out1) {
			input = in;
			output = out1;
		}

		public ItemStack getOutput() {
			return output.copy();
		}

		public boolean makesItem(ItemStack is) {
			return ReikaItemHelper.matchStacks(is, output);
		}

		@Override
		public String getUniqueID() {
			return fullID(input)+">"+fullID(output);
		}

		@Override
		public String getAllInfo() {
			return "Grinding "+fullID(input)+" into "+fullID(output);
		}

		@Override
		public Collection<ItemStack> getAllUsedItems() {
			return ReikaJavaLibrary.makeListFrom(input, output);
		}

	}

	/*
	private static class GrinderRecipe {

		private final ItemStack input;
		private final ItemStack output1;
		private final ItemStack output2;

		private GrinderRecipe(ItemStack in, ItemStack out1, ItemStack out2) {
			input = in;
			output1 = out1;
			output2 = out2;
		}

		public ItemStack[] getOutputs() {
			return output2 != null ? new ItemStack[]{output1.copy(), output2.copy()} : new ItemStack[]{output1.copy()};
		}

		public boolean makesItem(ItemStack is) {
			return ReikaItemHelper.matchStacks(is, output1) || (output2 != null && ReikaItemHelper.matchStacks(is, output2));
		}

	}
	 */

	private ItemStack getSizedSawdust(int size) {
		return ReikaItemHelper.getSizedItemStack(ItemStacks.sawdust, size);
	}

	public boolean isGrindable(ItemStack item) {
		return this.getGrindingResult(item) != null;
	}

	public boolean isProduct(ItemStack item) {
		for (GrinderRecipe gr : recipes.values()) {
			if (gr.makesItem(item))
				return true;
		}
		return false;
	}

	public List<ItemStack> getSources(ItemStack out) {
		List<ItemStack> in = new ArrayList();
		for (ItemStack input : recipes.keySet()) {
			GrinderRecipe gr = recipes.get(input);
			if (gr.makesItem(out))
				in.add(input.copy());
		}
		return in;
	}

	public void addOreDictRecipe(String in, ItemStack out, RecipeLevel rl) {
		ArrayList<ItemStack> li = OreDictionary.getOres(in);
		for (ItemStack sin : li) {
			if (!recipes.containsKey(sin))
				this.addRecipe(sin, out, rl);
		}
	}

	public void addAPIRecipe(ItemStack in, ItemStack out) {
		this.addRecipe(in, out, RecipeLevel.API);
	}

	private void addRecipe(Block in, ItemStack out, RecipeLevel rl) {
		this.addRecipe(new ItemStack(in), out, rl);
	}

	private void addRecipe(Item in, ItemStack out, RecipeLevel rl) {
		this.addRecipe(new ItemStack(in), out, rl);
	}

	public void addRecipe(ItemStack in, ItemStack out) {
		this.addRecipe(in, out, RecipeLevel.CORE);
	}

	private void addRecipe(ItemStack in, ItemStack out, RecipeLevel rl) {
		GrinderRecipe rec = new GrinderRecipe(in, out);
		recipes.put(in, rec);
		this.onAddRecipe(rec, rl);
	}

	public void addCustomRecipe(ItemStack in, ItemStack out) {
		this.addRecipe(in, out, RecipeLevel.CUSTOM);
	}

	public ItemStack getGrindingResult(ItemStack item) {
		GrinderRecipe ret = item != null ? recipes.get(item) : null;
		return ret != null ? ret.output.copy() : null;
	}

	@Override
	public void addPostLoadRecipes() {
		this.addOreRecipes();
		//this.addMulchRecipes();

		if (ModList.APPENG.isLoaded()) {
			ItemStack cry = AppEngHandler.getInstance().getCertusQuartz();
			ItemStack dust = AppEngHandler.getInstance().getCertusQuartzDust();
			if (cry != null && dust != null) {
				this.addRecipe(cry, dust, RecipeLevel.MODINTERACT);
			}
			else {
				RotaryCraft.logger.logError("Could not add certus quartz grinding; null itemstack "+cry+", "+dust);
			}
		}

		ArrayList<ItemStack> obsididust = OreDictionary.getOres("dustObsidian");
		if (!obsididust.isEmpty())
			this.addRecipe(Blocks.obsidian, ReikaItemHelper.getSizedItemStack(obsididust.get(0), 6), RecipeLevel.MODINTERACT);

		ItemStack blizzDust = ReikaItemHelper.oreItemExists("dustBlizz") ? OreDictionary.getOres("dustBlizz").get(0) : null;
		if (blizzDust != null) {
			this.addOreDictRecipe("rodBlizz", ReikaItemHelper.getSizedItemStack(blizzDust, 6), RecipeLevel.MODINTERACT);
		}

		this.addOreDictRecipe("netherrack", ItemStacks.netherrackdust, RecipeLevel.CORE); //create a netherrack powder
		this.addOreDictRecipe("soulsand", ItemStacks.tar, RecipeLevel.CORE); //create a tar
	}
	/*
	private void addMulchRecipes() {
		Collection<ItemStack> mulches = MulchMaterials.instance.getAllValidPlants();
		for (ItemStack is : mulches) {
			if (is.getItem() != Items.reeds) {
				int num = MulchMaterials.instance.getPlantValue(is);
				this.addRecipe(is, ReikaItemHelper.getSizedItemStack(ItemStacks.mulch, num));
				RotaryCraft.logger.log("Adding grinder mulching recipe for "+is+", makes "+num);
			}
		}
	}
	 */

	private void addOreRecipes() {
		for (int i = 0; i < ModOreList.oreList.length; i++) {
			ModOreList ore = ModOreList.oreList[i];
			Collection<ItemStack> li = ore.getAllOreBlocks();
			for (ItemStack is : li) {
				ItemStack flake = ExtractorModOres.getFlakeProduct(ore);
				this.addRecipe(is, ReikaItemHelper.getSizedItemStack(flake, ore_rate), RecipeLevel.CORE);
				RotaryCraft.logger.log("Adding "+(ore_rate)+"x grinder recipe for "+ore+" ore "+is);
			}
		}

		for (int i = 0; i < ReikaOreHelper.oreList.length; i++) {
			ReikaOreHelper ore = ReikaOreHelper.oreList[i];
			Collection<ItemStack> li = ore.getAllOreBlocks();
			for (ItemStack is : li) {
				ItemStack flake = ItemRegistry.EXTRACTS.getCraftedMetadataProduct(ore_rate, 24+ore.ordinal());
				this.addRecipe(is, ReikaItemHelper.getSizedItemStack(flake, ore_rate), RecipeLevel.CORE);
				RotaryCraft.logger.log("Adding "+(ore_rate)+"x grinder recipe for "+ore+" ore "+is);
			}
		}
	}

	public Collection<ItemStack> getAllGrindables() {
		return Collections.unmodifiableCollection(recipes.keySet());
	}

	@Override
	protected boolean removeRecipe(MachineRecipe recipe) {
		return recipes.removeValue((GrinderRecipe)recipe);
	}
}
