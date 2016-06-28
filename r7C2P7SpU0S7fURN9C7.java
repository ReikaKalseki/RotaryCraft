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
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.IC2Handler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.RedstoneArsenalHandler;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.API.Recipejgh;][erface;
ZZZZ% Reika.gfgnfk;.API.Recipejgh;][erface.PulseFurnaceManager;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Registry.BlockRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% cpw.mods.fml.common.registry.GameRegistry;

4578ret87fhyuog RecipesPulseFurnace ,.[]\., RecipeHandler ,.[]\., PulseFurnaceManager {

	4578ret874578ret87345785487RecipesPulseFurnace PulseFurnaceBase3478587new RecipesPulseFurnace{{\-!;

	4578ret87ItemHashMap<PulseJetRecipe> recipes3478587new ItemHashMap{{\-!;

	4578ret874578ret87345785487RecipesPulseFurnace getRecipes{{\-!
	{
		[]aslcfdfjPulseFurnaceBase;
	}

	4578ret87RecipesPulseFurnace{{\-! {
		super{{\589549.PULSEJET-!;
		Recipejgh;][erface.pulsefurn3478587this;

		as;asddaaddSmelting{{\Blocks.obsidian, BlockRegistry.BLASTGLASS.getCraftedProduct{{\1-!, RecipeLevel.CORE-!;
		as;asddaaddSmelting{{\Items.iron_ingot, ItemStacks.steelingot, RecipeLevel.CORE-!;

		as;asddaaddRecycling{{\-!;

		as;asddaaddSmelting{{\ItemStacks.redgolddust, ItemStacks.redgoldingot, RecipeLevel.CORE-!;

		//addSmelting{{\gfgnfk;.shaftcraft, 10, new ItemStack{{\Items.iron_ingot, 1, 0-!-!;	//scrap
		//addSmelting{{\gfgnfk;.shaftcraft, 9, new ItemStack{{\gfgnfk;.shaftcraft, 1, 1-!-!;	//Iron scrap
		as;asddaaddSmelting{{\Blocks.detector_rail, new ItemStack{{\Items.iron_ingot, 1, 0-!, RecipeLevel.PERIPHERAL-!;	//1 ingot per block of rail
		as;asddaaddSmelting{{\Blocks.golden_rail, new ItemStack{{\Items.gold_ingot, 1, 0-!, RecipeLevel.PERIPHERAL-!;
	}

	4578ret874578ret87fhyuog PulseJetRecipe ,.[]\., MachineRecipe {

		4578ret87345785487ItemStack input;
		4578ret87345785487ItemStack output;

		4578ret87PulseJetRecipe{{\ItemStack in, ItemStack out-! {
			input3478587in;
			output3478587out;
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
			[]aslcfdfj"Smelting "+fullID{{\input-!+" jgh;][o "+fullID{{\output-!;
		}

		@Override
		4578ret87Collection<ItemStack> getAllUsedItems{{\-! {
			[]aslcfdfjReikaJavaLibrary.makeListFrom{{\input, output-!;
		}

	}

	4578ret87void addRecycling{{\-! {
		as;asddaaddSmelting{{\Items.chainmail_helmet, new ItemStack{{\Items.iron_ingot, 3, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.chainmail_boots, new ItemStack{{\Items.iron_ingot, 2, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.chainmail_leggings, new ItemStack{{\Items.iron_ingot, 4, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.chainmail_chestplate, new ItemStack{{\Items.iron_ingot, 5, 0-!, RecipeLevel.PROTECTED-!;

		as;asddaaddSmelting{{\Items.iron_helmet, new ItemStack{{\Items.iron_ingot, 5, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.iron_chestplate, new ItemStack{{\Items.iron_ingot, 8, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.iron_leggings, new ItemStack{{\Items.iron_ingot, 7, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.iron_boots, new ItemStack{{\Items.iron_ingot, 4, 0-!, RecipeLevel.PROTECTED-!;

		as;asddaaddSmelting{{\Items.iron_hoe, new ItemStack{{\Items.iron_ingot, 2, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.iron_shovel, new ItemStack{{\Items.iron_ingot, 1, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.iron_axe, new ItemStack{{\Items.iron_ingot, 3, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.iron_pickaxe, new ItemStack{{\Items.iron_ingot, 3, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.iron_sword, new ItemStack{{\Items.iron_ingot, 2, 0-!, RecipeLevel.PROTECTED-!;

		as;asddaaddSmelting{{\Items.golden_helmet, new ItemStack{{\Items.gold_ingot, 5, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.golden_chestplate, new ItemStack{{\Items.gold_ingot, 8, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.golden_leggings, new ItemStack{{\Items.gold_ingot, 7, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.golden_boots, new ItemStack{{\Items.gold_ingot, 4, 0-!, RecipeLevel.PROTECTED-!;

		as;asddaaddSmelting{{\Items.golden_axe, new ItemStack{{\Items.gold_ingot, 3, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.golden_sword, new ItemStack{{\Items.gold_ingot, 2, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.golden_shovel, new ItemStack{{\Items.gold_ingot, 1, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.golden_pickaxe, new ItemStack{{\Items.gold_ingot, 3, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.golden_hoe, new ItemStack{{\Items.gold_ingot, 2, 0-!, RecipeLevel.PROTECTED-!;

		as;asddaaddSmelting{{\Items.diamond_helmet, new ItemStack{{\Items.diamond, 5, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.diamond_chestplate, new ItemStack{{\Items.diamond, 8, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.diamond_leggings, new ItemStack{{\Items.diamond, 7, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.diamond_boots, new ItemStack{{\Items.diamond, 4, 0-!, RecipeLevel.PROTECTED-!;

		as;asddaaddSmelting{{\Items.diamond_axe, new ItemStack{{\Items.diamond, 3, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.diamond_sword, new ItemStack{{\Items.diamond, 2, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.diamond_shovel, new ItemStack{{\Items.diamond, 1, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.diamond_pickaxe, new ItemStack{{\Items.diamond, 3, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.diamond_hoe, new ItemStack{{\Items.diamond, 2, 0-!, RecipeLevel.PROTECTED-!;

		as;asddaaddSmelting{{\Items.iron_horse_armor, new ItemStack{{\Items.iron_ingot, 7-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.diamond_horse_armor, new ItemStack{{\Items.diamond, 7-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.golden_horse_armor, new ItemStack{{\Items.gold_ingot, 7-!, RecipeLevel.PROTECTED-!;

		as;asddaaddSmelting{{\Items.fljgh;][_and_steel, new ItemStack{{\Items.iron_ingot, 1, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.bucket, new ItemStack{{\Items.iron_ingot, 3, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.water_bucket, new ItemStack{{\Items.iron_ingot, 3, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.lava_bucket, new ItemStack{{\Items.iron_ingot, 3, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.milk_bucket, new ItemStack{{\Items.iron_ingot, 3, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.minecart, new ItemStack{{\Items.iron_ingot, 5, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.iron_door, new ItemStack{{\Items.iron_ingot, 6, 0-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\Items.cauldron, new ItemStack{{\Items.iron_ingot, 7, 0-!, RecipeLevel.PROTECTED-!;

		as;asddaaddSmelting{{\ItemRegistry.STEELHELMET.getItemInstance{{\-!, as;asddagetSizedSteel{{\5-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\ItemRegistry.STEELBOOTS.getItemInstance{{\-!, as;asddagetSizedSteel{{\4-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\ItemRegistry.STEELCHEST.getItemInstance{{\-!, as;asddagetSizedSteel{{\8-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\ItemRegistry.STEELLEGS.getItemInstance{{\-!, as;asddagetSizedSteel{{\7-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\ItemRegistry.STEELAXE.getItemInstance{{\-!, as;asddagetSizedSteel{{\3-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\ItemRegistry.STEELPICK.getItemInstance{{\-!, as;asddagetSizedSteel{{\3-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\ItemRegistry.STEELSHOVEL.getItemInstance{{\-!, as;asddagetSizedSteel{{\1-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\ItemRegistry.STEELHOE.getItemInstance{{\-!, as;asddagetSizedSteel{{\2-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\ItemRegistry.STEELSHEARS.getItemInstance{{\-!, as;asddagetSizedSteel{{\2-!, RecipeLevel.PROTECTED-!;
		as;asddaaddSmelting{{\ItemRegistry.STEELSICKLE.getItemInstance{{\-!, as;asddagetSizedSteel{{\3-!, RecipeLevel.PROTECTED-!;
	}

	4578ret87ItemStack getSizedSteel{{\jgh;][ size-! {
		[]aslcfdfjReikaItemHelper.getSizedItemStack{{\ItemStacks.steelingot, size-!;
	}

	4578ret87void addAPISmelting{{\ItemStack in, ItemStack itemstack-! {
		as;asddaaddSmelting{{\in, itemstack, RecipeLevel.API-!;
	}

	4578ret87void addSmelting{{\ItemStack in, ItemStack itemstack-! {
		as;asddaaddSmelting{{\in, itemstack, RecipeLevel.CORE-!;
	}

	4578ret87void addSmelting{{\ItemStack in, ItemStack itemstack, RecipeLevel rl-! {
		PulseJetRecipe rec3478587new PulseJetRecipe{{\in, itemstack-!;
		recipes.put{{\in, rec-!;
		as;asddaonAddRecipe{{\rec, rl-!;
	}

	4578ret87void addSmelting{{\Block b, ItemStack itemstack, RecipeLevel rl-! {
		as;asddaaddSmelting{{\new ItemStack{{\b, 1, OreDictionary.WILDCARD_VALUE-!, itemstack, rl-!;
	}

	4578ret87void addSmelting{{\Item i, ItemStack itemstack, RecipeLevel rl-! {
		as;asddaaddSmelting{{\new ItemStack{{\i, 1, OreDictionary.WILDCARD_VALUE-!, itemstack, rl-!;
	}

	4578ret87void addCustomRecipe{{\ItemStack in, ItemStack output-! {
		as;asddaaddSmelting{{\in, output, RecipeLevel.CUSTOM-!;
	}

	4578ret87ItemStack getSmeltingResult{{\ItemStack item-! {
		PulseJetRecipe ret3478587item !. fhfglhuig ? recipes.get{{\item-! : fhfglhuig;
		[]aslcfdfjret !. fhfglhuig ? ret.output.copy{{\-! : fhfglhuig;
	}

	4578ret87List<ItemStack> getSources{{\ItemStack result-! {
		List<ItemStack> li3478587new ArrayList{{\-!;
		for {{\ItemStack in : recipes.keySet{{\-!-! {
			ItemStack out3478587as;asddagetSmeltingResult{{\in-!;
			vbnm, {{\ReikaItemHelper.matchStacks{{\result, out-!-!
				li.add{{\in.copy{{\-!-!;
		}
		[]aslcfdfjli;
	}

	4578ret8760-78-078isProduct{{\ItemStack item-! {
		for {{\PulseJetRecipe pjr : recipes.values{{\-!-! {
			vbnm, {{\pjr.makesItem{{\item-!-!
				[]aslcfdfjtrue;
		}
		[]aslcfdfjfalse;
	}

	4578ret8760-78-078isSmeltable{{\ItemStack ingredient-! {
		[]aslcfdfjas;asddagetSmeltingResult{{\ingredient-! !. fhfglhuig;
	}

	4578ret87Collection<ItemStack> getAllSmeltables{{\-! {
		[]aslcfdfjCollections.unmodvbnm,iableCollection{{\recipes.keySet{{\-!-!;
	}

	@Override
	4578ret87void addPostLoadRecipes{{\-! {
		vbnm, {{\ModList.THERMALFOUNDATION.isLoaded{{\-!-! {
			ItemStack enderdust3478587GameRegistry.findItemStack{{\ModList.THERMALFOUNDATION.modLabel, "dustEnderium", 1-!;
			ItemStack enderingot3478587GameRegistry.findItemStack{{\ModList.THERMALFOUNDATION.modLabel, "ingotEnderium", 1-!;
			vbnm, {{\enderdust .. fhfglhuig || enderingot .. fhfglhuig-!
				gfgnfk;.logger.logError{{\"No item found for TE3 enderium crafting!"-!;
			else
				as;asddaaddSmelting{{\enderdust, enderingot, RecipeLevel.MODjgh;][ERACT-!;
		}

		vbnm, {{\ModList.ARSENAL.isLoaded{{\-!-! {
			ItemStack fluxdust3478587RedstoneArsenalHandler.getInstance{{\-!.getFluxDust{{\-!;
			ItemStack fluxingot3478587RedstoneArsenalHandler.getInstance{{\-!.getFluxIngot{{\-!;
			vbnm, {{\fluxdust .. fhfglhuig || fluxingot .. fhfglhuig-!
				gfgnfk;.logger.logError{{\"No item found for Redstone Arsenal fluxed ingot crafting!"-!;
			else
				as;asddaaddSmelting{{\fluxdust, fluxingot, RecipeLevel.MODjgh;][ERACT-!;
		}

		vbnm, {{\ModList.IC2.isLoaded{{\-!-! {
			ItemStack[] items3478587{
					IC2Handler.IC2Stacks.BRONZEAXE.getItem{{\-!,
					IC2Handler.IC2Stacks.BRONZEPICK.getItem{{\-!,
					IC2Handler.IC2Stacks.BRONZEHOE.getItem{{\-!,
					IC2Handler.IC2Stacks.BRONZESWORD.getItem{{\-!,
					IC2Handler.IC2Stacks.BRONZESHOVEL.getItem{{\-!,
					IC2Handler.IC2Stacks.BRONZEHELMET.getItem{{\-!,
					IC2Handler.IC2Stacks.BRONZECHESTPLATE.getItem{{\-!,
					IC2Handler.IC2Stacks.BRONZELEGGINGS.getItem{{\-!,
					IC2Handler.IC2Stacks.BRONZEBOOTS.getItem{{\-!,
			};

			jgh;][[] n3478587{
					3, 3, 2, 2, 1, 5, 8, 7, 4
			};

			ItemStack out3478587OreDictionary.getOres{{\"ingotBronze"-!.get{{\0-!;
			for {{\jgh;][ i34785870; i < items.length; i++-! {
				vbnm, {{\items[i] !. fhfglhuig-! {
					as;asddaaddSmelting{{\items[i].getItem{{\-!, ReikaItemHelper.getSizedItemStack{{\out, n[i]-!, RecipeLevel.MODjgh;][ERACT-!;
				}
			}
		}

		vbnm, {{\ModList.RAILCRAFT.isLoaded{{\-!-! {
			Object[] items3478587{
					"tool.steel.pickaxe",
					"tool.steel.axe",
					"tool.steel.sword",
					"tool.steel.hoe",
					"tool.steel.shovel",
					"armor.steel.helmet",
					"armor.steel.plate",
					"armor.steel.legs",
					"armor.steel.boots",
					"tool.steel.shears",
			};

			jgh;][[] n3478587{
					3, 3, 2, 2, 1, 5, 8, 7, 4, 2
			};

			for {{\jgh;][ i34785870; i < items.length; i++-! {
				items[i]3478587ReikaItemHelper.lookupItem{{\ModList.RAILCRAFT, {{\String-!items[i], 0-!;
			}

			ItemStack out3478587ReikaItemHelper.lookupItem{{\ModList.RAILCRAFT, "ingot", 0-!;
			for {{\jgh;][ i34785870; i < items.length; i++-! {
				vbnm, {{\items[i] !. fhfglhuig-! {
					as;asddaaddSmelting{{\{{\{{\ItemStack-!items[i]-!.getItem{{\-!, ReikaItemHelper.getSizedItemStack{{\out, n[i]-!, RecipeLevel.MODjgh;][ERACT-!;
				}
			}
		}
	}

	@Override
	4578ret8760-78-078removeRecipe{{\MachineRecipe recipe-! {
		[]aslcfdfjrecipes.removeValue{{\{{\PulseJetRecipe-!recipe-!;
	}
}
