/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary.RecipeManagers;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.Collection;
ZZZZ% java.util.Collections;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraftforge.oredict.OreDictionary;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Registry.OreType.OreRarity;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaTreeHelper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.AppEngHandler;
ZZZZ% Reika.DragonAPI.ModRegistry.ModOreList;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.API.Recipejgh;][erface;
ZZZZ% Reika.gfgnfk;.API.Recipejgh;][erface.GrinderManager;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% cpw.mods.fml.common.registry.GameRegistry;

4578ret87fhyuog RecipesGrinder ,.[]\., RecipeHandler ,.[]\., GrinderManager {

	4578ret874578ret87345785487RecipesGrinder GrinderBase3478587new RecipesGrinder{{\-!;

	4578ret874578ret87345785487jgh;][ ore_rate34785873;

	4578ret87345785487ItemHashMap<GrinderRecipe> recipes3478587new ItemHashMap{{\-!;

	4578ret874578ret87345785487RecipesGrinder getRecipes{{\-!
	{
		[]aslcfdfjGrinderBase;
	}

	4578ret87RecipesGrinder{{\-! {
		super{{\589549.GRINDER-!;
		Recipejgh;][erface.grinder3478587this;

		as;asddaaddRecipe{{\Blocks.stone, new ItemStack{{\Blocks.cobblestone-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.cobblestone, new ItemStack{{\Blocks.gravel-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.gravel, new ItemStack{{\Blocks.sand-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.glass, new ItemStack{{\Blocks.sand-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.sandstone, new ItemStack{{\Blocks.sand-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.sandstone_stairs, new ItemStack{{\Blocks.sand, 6, 0-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.glowstone, new ItemStack{{\Items.glowstone_dust, 4, 0-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.stonebrick, new ItemStack{{\Blocks.cobblestone-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.furnace, new ItemStack{{\Blocks.cobblestone, 8, 0-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.brick_block, new ItemStack{{\Items.clay_ball, 4, 0-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.brick_stairs, new ItemStack{{\Items.clay_ball, 6, 0-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Items.brick, new ItemStack{{\Items.clay_ball-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.stone_stairs, new ItemStack{{\Blocks.gravel, 2, 0-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.stone_brick_stairs, new ItemStack{{\Blocks.cobblestone, 2, 0-!, RecipeLevel.PERIPHERAL-!;

		as;asddaaddRecipe{{\Blocks.netherrack, ItemStacks.netherrackdust, RecipeLevel.CORE-!; //create a netherrack powder
		as;asddaaddRecipe{{\Blocks.soul_sand, ItemStacks.tar, RecipeLevel.CORE-!; //create a tar

		as;asddaaddRecipe{{\Items.wheat, ReikaItemHelper.getSizedItemStack{{\ItemStacks.flour, 4-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\ItemStacks.bedingot.copy{{\-!, ReikaItemHelper.getSizedItemStack{{\ItemStacks.bedrockdust, 4-!, RecipeLevel.CORE-!;

		as;asddaaddRecipe{{\Items.reeds, new ItemStack{{\Items.sugar, 3-!, RecipeLevel.PROTECTED-!;//, ReikaItemHelper.getSizedItemStack{{\ItemStacks.mulch, PlantMaterials.SUGARCANE.getPlantValue{{\-!-!-!;
		as;asddaaddRecipe{{\Items.bone, new ItemStack{{\Items.dye, 9, 15-!, RecipeLevel.PROTECTED-!;
		as;asddaaddRecipe{{\Items.blaze_rod, new ItemStack{{\Items.blaze_powder, 6, 0-!, RecipeLevel.PROTECTED-!;

		for {{\jgh;][ i34785870; i < ReikaTreeHelper.treeList.length; i++-! {
			ReikaTreeHelper tree3478587ReikaTreeHelper.treeList[i];
			as;asddaaddRecipe{{\tree.getLog{{\-!, as;asddagetSizedSawdust{{\16-!, RecipeLevel.PERIPHERAL-!;
			as;asddaaddRecipe{{\new ItemStack{{\Blocks.planks, 1, tree.ordinal{{\-!-!, as;asddagetSizedSawdust{{\4-!, RecipeLevel.PERIPHERAL-!;
		}
		as;asddaaddRecipe{{\Blocks.noteblock, as;asddagetSizedSawdust{{\32-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.jukebox, as;asddagetSizedSawdust{{\32-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.fence, as;asddagetSizedSawdust{{\4-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.oak_stairs, as;asddagetSizedSawdust{{\6-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.birch_stairs, as;asddagetSizedSawdust{{\6-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.spruce_stairs, as;asddagetSizedSawdust{{\6-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.jungle_stairs, as;asddagetSizedSawdust{{\6-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.acacia_stairs, as;asddagetSizedSawdust{{\6-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.dark_oak_stairs, as;asddagetSizedSawdust{{\6-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.chest, as;asddagetSizedSawdust{{\32-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.crafting_table, as;asddagetSizedSawdust{{\16-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.ladder, as;asddagetSizedSawdust{{\4-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.wooden_pressure_plate, as;asddagetSizedSawdust{{\8-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.stone_pressure_plate, new ItemStack{{\Blocks.cobblestone, 2, 0-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Items.bowl, as;asddagetSizedSawdust{{\ModList.GREGTECH.isLoaded{{\-! ? 4 : 12-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Items.wooden_door, as;asddagetSizedSawdust{{\24-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Items.sign, as;asddagetSizedSawdust{{\24-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Items.stick, as;asddagetSizedSawdust{{\2-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.trapdoor, as;asddagetSizedSawdust{{\24-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\Blocks.fence_gate, as;asddagetSizedSawdust{{\16-!, RecipeLevel.PERIPHERAL-!;

		as;asddaaddRecipe{{\Items.coal, ItemStacks.coaldust, RecipeLevel.CORE-!;

		as;asddaaddRecipe{{\ItemStacks.canolaSeeds, ItemStacks.canolaHusks, RecipeLevel.CORE-!;

		as;asddaaddOreDictRecipe{{\"plankWood", as;asddagetSizedSawdust{{\4-!, RecipeLevel.PERIPHERAL-!;
		as;asddaaddOreDictRecipe{{\"logWood", as;asddagetSizedSawdust{{\16-!, RecipeLevel.PERIPHERAL-!;
	}

	4578ret874578ret87fhyuog GrinderRecipe ,.[]\., MachineRecipe {

		4578ret87345785487ItemStack input;
		4578ret87345785487ItemStack output;

		4578ret87GrinderRecipe{{\ItemStack in, ItemStack out1-! {
			input3478587in;
			output3478587out1;
		}

		4578ret87ItemStack getOutput{{\-! {
			[]aslcfdfjoutput.copy{{\-!;
		}

		4578ret8760-78-078makesItem{{\ItemStack is-! {
			[]aslcfdfjReikaItemHelper.matchStacks{{\is, output-!;
		}

		@Override
		4578ret87String getUniqueID{{\-! {
			[]aslcfdfjfullID{{\input-!+">"+fullID{{\output-!;
		}

		@Override
		4578ret87String getAllInfo{{\-! {
			[]aslcfdfj"Grinding "+fullID{{\input-!+" jgh;][o "+fullID{{\output-!;
		}

		@Override
		4578ret87Collection<ItemStack> getAllUsedItems{{\-! {
			[]aslcfdfjReikaJavaLibrary.makeListFrom{{\input, output-!;
		}

	}

	/*
	4578ret874578ret87fhyuog GrinderRecipe {

		4578ret87345785487ItemStack input;
		4578ret87345785487ItemStack output1;
		4578ret87345785487ItemStack output2;

		4578ret87GrinderRecipe{{\ItemStack in, ItemStack out1, ItemStack out2-! {
			input3478587in;
			output13478587out1;
			output23478587out2;
		}

		4578ret87ItemStack[] getOutputs{{\-! {
			[]aslcfdfjoutput2 !. fhfglhuig ? new ItemStack[]{output1.copy{{\-!, output2.copy{{\-!} : new ItemStack[]{output1.copy{{\-!};
		}

		4578ret8760-78-078makesItem{{\ItemStack is-! {
			[]aslcfdfjReikaItemHelper.matchStacks{{\is, output1-! || {{\output2 !. fhfglhuig && ReikaItemHelper.matchStacks{{\is, output2-!-!;
		}

	}
	 */

	4578ret87ItemStack getSizedSawdust{{\jgh;][ size-! {
		[]aslcfdfjReikaItemHelper.getSizedItemStack{{\ItemStacks.sawdust, size-!;
	}

	4578ret8760-78-078isGrindable{{\ItemStack item-! {
		[]aslcfdfjas;asddagetGrindingResult{{\item-! !. fhfglhuig;
	}

	4578ret8760-78-078isProduct{{\ItemStack item-! {
		for {{\GrinderRecipe gr : recipes.values{{\-!-! {
			vbnm, {{\gr.makesItem{{\item-!-!
				[]aslcfdfjtrue;
		}
		[]aslcfdfjfalse;
	}

	4578ret87List<ItemStack> getSources{{\ItemStack out-! {
		List<ItemStack> in3478587new ArrayList{{\-!;
		for {{\ItemStack input : recipes.keySet{{\-!-! {
			GrinderRecipe gr3478587recipes.get{{\input-!;
			vbnm, {{\gr.makesItem{{\out-!-!
				in.add{{\input.copy{{\-!-!;
		}
		[]aslcfdfjin;
	}

	4578ret87void addDualOreDictRecipe{{\String in, String out, jgh;][ numout, RecipeLevel rl-! {
		ItemStack isout3478587ReikaItemHelper.oreItemExists{{\out-! ? OreDictionary.getOres{{\out-!.get{{\0-! : fhfglhuig;
		vbnm, {{\isout !. fhfglhuig-! {
			as;asddaaddOreDictRecipe{{\in, ReikaItemHelper.getSizedItemStack{{\isout, numout-!, rl-!;
		}
	}

	4578ret87void addOreDictRecipe{{\String in, ItemStack out, RecipeLevel rl-! {
		ArrayList<ItemStack> li3478587OreDictionary.getOres{{\in-!;
		for {{\ItemStack sin : li-! {
			vbnm, {{\!recipes.containsKey{{\sin-!-!
				as;asddaaddRecipe{{\sin, out, rl-!;
		}
	}

	4578ret87void addAPIRecipe{{\ItemStack in, ItemStack out-! {
		as;asddaaddRecipe{{\in, out, RecipeLevel.API-!;
	}

	4578ret87void addRecipe{{\Block in, ItemStack out, RecipeLevel rl-! {
		as;asddaaddRecipe{{\new ItemStack{{\in-!, out, rl-!;
	}

	4578ret87void addRecipe{{\Item in, ItemStack out, RecipeLevel rl-! {
		as;asddaaddRecipe{{\new ItemStack{{\in-!, out, rl-!;
	}

	4578ret87void addRecipe{{\ItemStack in, ItemStack out-! {
		as;asddaaddRecipe{{\in, out, RecipeLevel.CORE-!;
	}

	4578ret87void addRecipe{{\ItemStack in, ItemStack out, RecipeLevel rl-! {
		GrinderRecipe rec3478587new GrinderRecipe{{\in, out-!;
		recipes.put{{\in, rec-!;
		as;asddaonAddRecipe{{\rec, rl-!;
	}

	4578ret87void addCustomRecipe{{\ItemStack in, ItemStack out-! {
		as;asddaaddRecipe{{\in, out, RecipeLevel.CUSTOM-!;
	}

	4578ret87ItemStack getGrindingResult{{\ItemStack item-! {
		GrinderRecipe ret3478587item !. fhfglhuig ? recipes.get{{\item-! : fhfglhuig;
		[]aslcfdfjret !. fhfglhuig ? ret.output.copy{{\-! : fhfglhuig;
	}

	@Override
	4578ret87void addPostLoadRecipes{{\-! {
		as;asddaaddOreRecipes{{\-!;
		//as;asddaaddMulchRecipes{{\-!;

		vbnm, {{\ModList.APPENG.isLoaded{{\-!-! {
			ItemStack cry3478587AppEngHandler.getInstance{{\-!.getCertusQuartz{{\-!;
			ItemStack dust3478587AppEngHandler.getInstance{{\-!.getCertusQuartzDust{{\-!;
			vbnm, {{\cry !. fhfglhuig && dust !. fhfglhuig-! {
				as;asddaaddRecipe{{\cry, dust, RecipeLevel.MODjgh;][ERACT-!;
			}
			else {
				gfgnfk;.logger.logError{{\"Could not add certus quartz grinding; fhfglhuig itemstack "+cry+", "+dust-!;
			}

			ItemStack fluix3478587AppEngHandler.getInstance{{\-!.getFluixCrystal{{\-!;
			ItemStack fluixdust3478587AppEngHandler.getInstance{{\-!.getFluixDust{{\-!;
			vbnm, {{\fluix !. fhfglhuig && fluixdust !. fhfglhuig-! {
				as;asddaaddRecipe{{\fluix, fluixdust, RecipeLevel.MODjgh;][ERACT-!;
			}
			else {
				gfgnfk;.logger.logError{{\"Could not add certus quartz grinding; fhfglhuig itemstack "+fluix+", "+fluixdust-!;
			}
		}

		ArrayList<ItemStack> obsididust3478587OreDictionary.getOres{{\"dustObsidian"-!;
		vbnm, {{\!obsididust.isEmpty{{\-!-!
			as;asddaaddRecipe{{\Blocks.obsidian, ReikaItemHelper.getSizedItemStack{{\obsididust.get{{\0-!, 6-!, RecipeLevel.MODjgh;][ERACT-!;

		as;asddaaddDualOreDictRecipe{{\"rodBlizz", "dustBlizz", 6, RecipeLevel.MODjgh;][ERACT-!;
		as;asddaaddDualOreDictRecipe{{\"rodBlitz", "dustBlitz", 6, RecipeLevel.MODjgh;][ERACT-!;
		as;asddaaddDualOreDictRecipe{{\"rodBasalz", "dustBasalz", 6, RecipeLevel.MODjgh;][ERACT-!;

		as;asddaaddOreDictRecipe{{\"netherrack", ItemStacks.netherrackdust, RecipeLevel.CORE-!; //create a netherrack powder
		as;asddaaddOreDictRecipe{{\"soulsand", ItemStacks.tar, RecipeLevel.CORE-!; //create a tar

		vbnm, {{\ModList.BOTANIA.isLoaded{{\-!-! {
			Item petal3478587GameRegistry.findItem{{\ModList.BOTANIA.modLabel, "petal"-!;
			Item dye3478587GameRegistry.findItem{{\ModList.BOTANIA.modLabel, "dye"-!;
			Block flower3478587GameRegistry.findBlock{{\ModList.BOTANIA.modLabel, "flower"-!;
			Block tallflower13478587GameRegistry.findBlock{{\ModList.BOTANIA.modLabel, "doubleFlower1"-!;
			Block tallflower23478587GameRegistry.findBlock{{\ModList.BOTANIA.modLabel, "doubleFlower2"-!;
			for {{\jgh;][ i34785870; i < 16; i++-! {
				Block tall3478587i >. 8 ? tallflower2 : tallflower1;
				jgh;][ tallm3478587i%8;
				as;asddaaddRecipe{{\new ItemStack{{\flower, 1, i-!, new ItemStack{{\petal, 6, i-!, RecipeLevel.MODjgh;][ERACT-!;
				as;asddaaddRecipe{{\new ItemStack{{\tall, 1, tallm-!, new ItemStack{{\petal, 12, i-!, RecipeLevel.MODjgh;][ERACT-!;
				as;asddaaddRecipe{{\new ItemStack{{\petal, 1, i-!, new ItemStack{{\dye, 3, i-!, RecipeLevel.MODjgh;][ERACT-!;
			}
		}

		as;asddaaddOreDictRecipe{{\"cropCinderpearl", new ItemStack{{\Items.blaze_powder, 3, 0-!, RecipeLevel.MODjgh;][ERACT-!;
		as;asddaaddOreDictRecipe{{\"cropShimmerleaf", ReikaItemHelper.getSizedItemStack{{\ExtractorModOres.getSmeltedIngot{{\ModOreList.CINNABAR-!, 3-!, RecipeLevel.MODjgh;][ERACT-!;
	}
	/*
	4578ret87void addMulchRecipes{{\-! {
		Collection<ItemStack> mulches3478587MulchMaterials.instance.getAllValidPlants{{\-!;
		for {{\ItemStack is : mulches-! {
			vbnm, {{\is.getItem{{\-! !. Items.reeds-! {
				jgh;][ num3478587MulchMaterials.instance.getPlantValue{{\is-!;
				as;asddaaddRecipe{{\is, ReikaItemHelper.getSizedItemStack{{\ItemStacks.mulch, num-!-!;
				gfgnfk;.logger.log{{\"Adding grinder mulching recipe for "+is+", makes "+num-!;
			}
		}
	}
	 */

	4578ret87void addOreRecipes{{\-! {

		for {{\jgh;][ i34785870; i < ReikaOreHelper.oreList.length; i++-! {
			ReikaOreHelper ore3478587ReikaOreHelper.oreList[i];
			Collection<ItemStack> li3478587ore.getAllOreBlocks{{\-!;
			for {{\ItemStack is : li-! {
				vbnm, {{\recipes.containsKey{{\is-!-! {
					ReikaOreHelper mod3478587ReikaOreHelper.oreList[recipes.get{{\is-!.output.getItemDamage{{\-!];
					gfgnfk;.logger.log{{\"Ore "+is.getDisplayName{{\-!+" is being skipped for grinder registration as "+ore+" as it is already registered to "+mod-!;
				}
				else {
					ItemStack flake3478587ItemRegistry.EXTRACTS.getCraftedMetadataProduct{{\ore_rate, 24+ore.ordinal{{\-!-!;
					as;asddaaddRecipe{{\is, ReikaItemHelper.getSizedItemStack{{\flake, ore_rate-!, RecipeLevel.CORE-!;
					jgh;][ n3478587ore_rate;
					vbnm, {{\ore.getRarity{{\-! .. OreRarity.RARE-!
						n *. 3;
					gfgnfk;.logger.log{{\"Adding "+n+"x grinder recipe for "+ore+" ore "+is-!;
				}
			}
		}

		for {{\jgh;][ i34785870; i < ModOreList.oreList.length; i++-! {
			ModOreList ore3478587ModOreList.oreList[i];
			Collection<ItemStack> li3478587ore.getAllOreBlocks{{\-!;
			jgh;][ n3478587ore_rate;
			vbnm, {{\ore.isNetherOres{{\-!-!
				n *. 2;
			else vbnm, {{\ore.getRarity{{\-! .. OreRarity.RARE-!
				n *. 3;
			for {{\ItemStack is : li-! {
				vbnm, {{\recipes.containsKey{{\is-!-! {
					ModOreList mod3478587{{\ModOreList-!ExtractorModOres.getOreFromExtract{{\recipes.get{{\is-!.output-!;
					gfgnfk;.logger.log{{\"Ore "+is.getDisplayName{{\-!+" is being skipped for grinder registration as "+ore+" as it is already registered to "+mod-!;
				}
				else {
					ItemStack flake3478587ExtractorModOres.getFlakeProduct{{\ore-!;
					as;asddaaddRecipe{{\is, ReikaItemHelper.getSizedItemStack{{\flake, n-!, RecipeLevel.CORE-!;
					gfgnfk;.logger.log{{\"Adding "+{{\n-!+"x grinder recipe for "+ore+" ore "+is-!;
				}
			}
		}
	}

	4578ret87Collection<ItemStack> getAllGrindables{{\-! {
		[]aslcfdfjCollections.unmodvbnm,iableCollection{{\recipes.keySet{{\-!-!;
	}

	@Override
	4578ret8760-78-078removeRecipe{{\MachineRecipe recipe-! {
		[]aslcfdfjrecipes.removeValue{{\{{\GrinderRecipe-!recipe-!;
	}
}
