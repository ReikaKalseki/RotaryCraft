/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
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
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Exception.MisuseException;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Instantiable.IO.CustomRecipeList;
import Reika.DragonAPI.Instantiable.IO.LuaBlock;
import Reika.DragonAPI.Interfaces.Registry.OreType;
import Reika.DragonAPI.Interfaces.Registry.OreType.OreRarity;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaTreeHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.AppEngHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.GregOreHandler.CounterpartOres;
import Reika.DragonAPI.ModInteract.ItemHandlers.HexcraftHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.HexcraftHandler.BasicHexColor;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.RecipeInterface;
import Reika.RotaryCraft.API.RecipeInterface.GrinderManager;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import cpw.mods.fml.common.registry.GameRegistry;

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
		this.addRecipe(Blocks.clay, new ItemStack(Items.clay_ball, 4, 0), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.brick_stairs, new ItemStack(Items.clay_ball, 6, 0), RecipeLevel.PERIPHERAL);
		this.addRecipe(Items.brick, new ItemStack(Items.clay_ball), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.stone_stairs, new ItemStack(Blocks.gravel, 2, 0), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.stone_brick_stairs, new ItemStack(Blocks.cobblestone, 2, 0), RecipeLevel.PERIPHERAL);

		this.addRecipe(Blocks.netherrack, ItemStacks.netherrackdust, RecipeLevel.CORE);
		this.addRecipe(Blocks.soul_sand, ItemStacks.tar, RecipeLevel.CORE);

		this.addRecipe(Items.wheat, ReikaItemHelper.getSizedItemStack(ItemStacks.flour, 3), RecipeLevel.PERIPHERAL);

		this.addRecipe(ItemStacks.bedingot.copy(), ReikaItemHelper.getSizedItemStack(ItemStacks.bedrockdust, 4), RecipeLevel.PROTECTED);
		this.addRecipe(ItemStacks.aluminumingot.copy(), ItemStacks.aluminumpowder.copy(), RecipeLevel.PROTECTED);

		this.addRecipe(Items.reeds, new ItemStack(Items.sugar, 3), RecipeLevel.PROTECTED);//, ReikaItemHelper.getSizedItemStack(ItemStacks.mulch, PlantMaterials.SUGARCANE.getPlantValue()));
		this.addRecipe(Items.bone, new ItemStack(Items.dye, 9, 15), RecipeLevel.PROTECTED);
		this.addRecipe(Items.blaze_rod, new ItemStack(Items.blaze_powder, 6, 0), RecipeLevel.PROTECTED);
		this.addRecipe(Blocks.ice, new ItemStack(Items.snowball, 4, 0), RecipeLevel.PROTECTED);

		for (int i = 0; i < ReikaTreeHelper.treeList.length; i++) {
			ReikaTreeHelper tree = ReikaTreeHelper.treeList[i];
			this.addRecipe(tree.getLog().asItemStack(), this.getSizedSawdust(16), RecipeLevel.PERIPHERAL);
			this.addRecipe(new ItemStack(Blocks.planks, 1, tree.ordinal()), this.getSizedSawdust(4), RecipeLevel.PERIPHERAL);
		}
		this.addRecipe(Blocks.noteblock, this.getSizedSawdust(32), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.jukebox, this.getSizedSawdust(32), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.fence, this.getSizedSawdust(4), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.oak_stairs, this.getSizedSawdust(6), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.birch_stairs, this.getSizedSawdust(6), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.spruce_stairs, this.getSizedSawdust(6), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.jungle_stairs, this.getSizedSawdust(6), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.acacia_stairs, this.getSizedSawdust(6), RecipeLevel.PERIPHERAL);
		this.addRecipe(Blocks.dark_oak_stairs, this.getSizedSawdust(6), RecipeLevel.PERIPHERAL);
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

		this.addFlowerDyes();

		this.addRecipe(Items.coal, ItemStacks.coaldust, RecipeLevel.CORE);

		this.addRecipe(ItemStacks.canolaSeeds, ItemStacks.canolaHusks, RecipeLevel.CORE);

		this.addOreDictRecipe("plankWood", this.getSizedSawdust(4), RecipeLevel.PERIPHERAL);
		this.addOreDictRecipe("logWood", this.getSizedSawdust(16), RecipeLevel.PERIPHERAL);
	}

	private void addFlowerDyes() {
		Object[][] recipes = {
				{Blocks.yellow_flower, 0, ReikaItemHelper.yellowDye},
				{Blocks.red_flower, 0, ReikaItemHelper.redDye},
				{Blocks.red_flower, 1, ReikaItemHelper.lblueDye},
				{Blocks.red_flower, 2, ReikaItemHelper.magentaDye},
				{Blocks.red_flower, 3, ReikaItemHelper.lgrayDye},
				{Blocks.red_flower, 4, ReikaItemHelper.redDye},
				{Blocks.red_flower, 5, ReikaItemHelper.orangeDye},
				{Blocks.red_flower, 6, ReikaItemHelper.lgrayDye},
				{Blocks.red_flower, 7, ReikaItemHelper.pinkDye},
				{Blocks.red_flower, 8, ReikaItemHelper.lgrayDye},
				{Blocks.double_plant, 1, ReikaItemHelper.magentaDye},
				{Blocks.double_plant, 4, ReikaItemHelper.redDye},
				{Blocks.double_plant, 5, ReikaItemHelper.pinkDye},
		};

		for (int i = 0; i < recipes.length; i++) {
			Object[] r = recipes[i];
			ItemStack is = new ItemStack((Block)r[0], 1, (Integer)r[1]);
			ItemStack out = (ItemStack)r[2];
			this.addRecipe(is, ReikaItemHelper.getSizedItemStack(out, 6));
		}
	}

	private static class GrinderRecipe implements MachineRecipe {

		private final ItemStack input;
		private final ItemStack output;

		private GrinderRecipe(ItemStack in, ItemStack out1) {
			input = in;
			if (in == null)
				throw new MisuseException("You cannot grind null!");
			if (in.getItem() == null)
				throw new MisuseException("You cannot grind null-item!");
			output = out1;
			if (out1 == null)
				throw new MisuseException("You cannot grind to null!");
			if (out1.getItem() == null)
				throw new MisuseException("You cannot grind to null-item!");
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

	public void addDualOreDictRecipe(String in, String out, int numout, RecipeLevel rl) {
		ItemStack isout = ReikaItemHelper.oreItemExists(out) ? OreDictionary.getOres(out).get(0) : null;
		if (isout != null) {
			this.addOreDictRecipe(in, ReikaItemHelper.getSizedItemStack(isout, numout), rl);
		}
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
		GrinderRecipe ret = item != null && item.getItem() != null ? recipes.get(item) : null;
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

			ItemStack fluix = AppEngHandler.getInstance().getFluixCrystal();
			ItemStack fluixdust = AppEngHandler.getInstance().getFluixDust();
			if (fluix != null && fluixdust != null) {
				this.addRecipe(fluix, fluixdust, RecipeLevel.MODINTERACT);
			}
			else {
				RotaryCraft.logger.logError("Could not add certus quartz grinding; null itemstack "+fluix+", "+fluixdust);
			}
		}

		ArrayList<ItemStack> obsididust = OreDictionary.getOres("dustObsidian");
		if (!obsididust.isEmpty())
			this.addRecipe(Blocks.obsidian, ReikaItemHelper.getSizedItemStack(obsididust.get(0), 6), RecipeLevel.MODINTERACT);

		this.addDualOreDictRecipe("rodBlizz", "dustBlizz", 6, RecipeLevel.MODINTERACT);
		this.addDualOreDictRecipe("rodBlitz", "dustBlitz", 6, RecipeLevel.MODINTERACT);
		this.addDualOreDictRecipe("rodBasalz", "dustBasalz", 6, RecipeLevel.MODINTERACT);

		this.addOreDictRecipe("netherrack", ItemStacks.netherrackdust, RecipeLevel.CORE);
		this.addOreDictRecipe("soulsand", ItemStacks.tar, RecipeLevel.CORE);

		if (ModList.BOTANIA.isLoaded()) {
			Item petal = GameRegistry.findItem(ModList.BOTANIA.modLabel, "petal");
			Item dye = GameRegistry.findItem(ModList.BOTANIA.modLabel, "dye");
			Block flower = GameRegistry.findBlock(ModList.BOTANIA.modLabel, "flower");
			Block tallflower1 = GameRegistry.findBlock(ModList.BOTANIA.modLabel, "doubleFlower1");
			Block tallflower2 = GameRegistry.findBlock(ModList.BOTANIA.modLabel, "doubleFlower2");
			for (int i = 0; i < 16; i++) {
				Block tall = i >= 8 ? tallflower2 : tallflower1;
				int tallm = i%8;
				this.addRecipe(new ItemStack(flower, 1, i), new ItemStack(petal, 6, i), RecipeLevel.MODINTERACT);
				this.addRecipe(new ItemStack(tall, 1, tallm), new ItemStack(petal, 12, i), RecipeLevel.MODINTERACT);
				this.addRecipe(new ItemStack(petal, 1, i), new ItemStack(dye, 3, i), RecipeLevel.MODINTERACT);
			}
		}

		ItemStack dust = ReikaItemHelper.lookupItem("exnihilo:dust");
		if (dust != null) {
			this.addRecipe(Blocks.sand, dust, RecipeLevel.MODINTERACT);
		}

		ItemStack endDust = ReikaItemHelper.lookupItem("exnihilo:exnihilo.gravel_ender");
		if (endDust != null) {
			this.addRecipe(Blocks.end_stone, endDust, RecipeLevel.MODINTERACT);
		}

		if (ReikaItemHelper.oreItemExists("dustEnderPearl"))
			this.addRecipe(Items.ender_pearl, OreDictionary.getOres("dustEnderPearl").get(0), RecipeLevel.MODINTERACT);
		else if (ReikaItemHelper.oreItemExists("dustEnder"))
			this.addRecipe(Items.ender_pearl, OreDictionary.getOres("dustEnder").get(0), RecipeLevel.MODINTERACT);

		if (ModList.HARVESTCRAFT.isLoaded()) {
			ItemStack corn = ReikaItemHelper.lookupItem("harvestcraft:cornItem");
			ItemStack fla = ReikaItemHelper.lookupItem("harvestcraft:cornflakesItem");
			if (corn != null && fla != null)
				this.addRecipe(corn, ReikaItemHelper.getSizedItemStack(fla, 3), RecipeLevel.MODINTERACT);
		}

		if (ModList.HEXCRAFT.isLoaded()) {
			for (BasicHexColor c : HexcraftHandler.getActiveHandler().getColors()) {
				Block in = c.getMonolith(false);
				Block in2 = c.getMonolith(true);
				Item i = c.getCrystal();
				this.addRecipe(in, new ItemStack(i, 9), RecipeLevel.MODINTERACT);
				this.addRecipe(in2, new ItemStack(i, 12), RecipeLevel.MODINTERACT);
			}
		}

		if (ModList.GEOSTRATA.isLoaded()) {
			this.addOreDictRecipe("seedCreepvine", ItemStacks.compost, RecipeLevel.MODINTERACT);
		}

		this.addOreDictRecipe("cropCinderpearl", new ItemStack(Items.blaze_powder, 3, 0), RecipeLevel.MODINTERACT);
		this.addOreDictRecipe("cropShimmerleaf", ReikaItemHelper.getSizedItemStack(ExtractorModOres.getSmeltedIngot(ModOreList.CINNABAR), 3), RecipeLevel.MODINTERACT);
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

		for (int i = 0; i < ReikaOreHelper.oreList.length; i++) {
			ReikaOreHelper ore = ReikaOreHelper.oreList[i];
			Collection<ItemStack> li = ore.getAllOreBlocks();
			for (ItemStack is : li) {
				if (recipes.containsKey(is)) {
					ReikaOreHelper mod = ReikaOreHelper.oreList[recipes.get(is).output.getItemDamage()];
					RotaryCraft.logger.log("Ore "+is.getDisplayName()+" is being skipped for grinder registration as "+ore+" as it is already registered to "+mod);
				}
				else {
					ItemStack flake = ItemRegistry.EXTRACTS.getCraftedMetadataProduct(ore_rate, 24+ore.ordinal());
					this.addRecipe(is, ReikaItemHelper.getSizedItemStack(flake, ore_rate), RecipeLevel.CORE);
					int n = ore_rate;
					if (ore.getRarity() == OreRarity.RARE)
						n *= 3;
					RotaryCraft.logger.log("Adding "+n+"x grinder recipe for "+ore+" ore "+is);
				}
			}
		}

		for (int i = 0; i < ModOreList.oreList.length; i++) {
			ModOreList ore = ModOreList.oreList[i];
			Collection<ItemStack> li = ore.getAllOreBlocks();
			int n = ore_rate;
			if (ore.isNetherOres())
				n *= 2;
			else if (ore.getRarity() == OreRarity.RARE)
				n *= 3;
			for (ItemStack is : li) {
				if (recipes.containsKey(is)) {
					OreType mod = ExtractorModOres.getOreFromExtract(recipes.get(is).output);
					RotaryCraft.logger.log("Ore "+is.getDisplayName()+" is being skipped for grinder registration as "+ore+" as it is already registered to "+mod);
				}
				else {
					ItemStack flake = ExtractorModOres.getFlakeProduct(ore);
					this.addRecipe(is, ReikaItemHelper.getSizedItemStack(flake, n), RecipeLevel.CORE);
					RotaryCraft.logger.log("Adding "+(n)+"x grinder recipe for "+ore+" ore "+is);
				}
			}
		}

		if (ModList.GREGTECH.isLoaded()) {
			this.loadGTOres();
		}
	}

	@ModDependent(ModList.GREGTECH)
	private void loadGTOres() {
		for (CounterpartOres ore : CounterpartOres.oreList) {
			for (ItemStack is : ore.getAllOreBlocks()) {
				int n = Math.max(1, (int)Math.round(ore_rate*ore.yieldFraction));
				OreType base = ore.counterpart;
				ItemStack flake = base instanceof ReikaOreHelper ? ItemRegistry.EXTRACTS.getCraftedMetadataProduct(n, 24+base.ordinal()) : ExtractorModOres.getFlakeProduct((ModOreList)base);
				flake.stackSize = n;
				this.addRecipe(is, ReikaItemHelper.getSizedItemStack(flake, n), RecipeLevel.CORE);
				RotaryCraft.logger.log("Adding "+(n)+"x grinder recipe for GT ore "+ore);
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

	@Override
	protected boolean addCustomRecipe(String n, LuaBlock lb, CustomRecipeList crl) throws Exception {
		ItemStack out = crl.parseItemString(lb.getString("output"), lb.getChild("output_nbt"), false);
		this.verifyOutputItem(out);
		String ore = lb.containsKey("ore_input") ? lb.getString("ore_input") : null;
		if (ore != null && !ore.isEmpty() && !ore.startsWith("[NULL")) {
			Collection<ItemStack> c = OreDictionary.getOres(ore);
			if (c.isEmpty()) {
				throw new IllegalArgumentException("Ore tag '"+ore+"' does not map to any existing OreDict tag!");
			}
			for (ItemStack in : c) {
				this.addCustomRecipe(in, out);
			}
			return true;
		}
		ItemStack in = crl.parseItemString(lb.getString("input"), lb.getChild("input_nbt"), false);
		this.addCustomRecipe(in, out);
		return true;
	}
}
