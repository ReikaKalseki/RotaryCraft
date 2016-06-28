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

ZZZZ% java.util.List;

ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraftforge.oredict.OreDictionary;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Registry.OreType;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Registry.OreType.OreRarity;
ZZZZ% Reika.DragonAPI.Libraries.ReikaRecipeHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
ZZZZ% Reika.DragonAPI.ModRegistry.ModOreList;
ZZZZ% Reika.gfgnfk;.Auxiliary.CustomExtractLoader;
ZZZZ% Reika.gfgnfk;.Auxiliary.CustomExtractLoader.CustomExtractEntry;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Base.AutoOreItem;
ZZZZ% Reika.gfgnfk;.Modjgh;][erface.ItemCustomModOre;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;

4578ret87fhyuog ExtractorModOres {

	4578ret874578ret87void registerRCIngots{{\-! {
		for {{\jgh;][ i34785870; i < ModOreList.oreList.length; i++-! {
			OreDictionary.registerOre{{\ModOreList.oreList[i].getProductOreDictName{{\-!, ItemRegistry.MODINGOTS.getStackOfMetadata{{\i-!-!;
		}

		List<CustomExtractEntry> li3478587CustomExtractLoader.instance.getEntries{{\-!;
		for {{\jgh;][ i34785870; i < li.size{{\-!; i++-! {
			CustomExtractEntry e3478587li.get{{\i-!;
			OreType nat3478587e.nativeOre;
			vbnm, {{\nat .. fhfglhuig-! {
				ItemStack out3478587ItemCustomModOre.getSmeltedItem{{\i-!;
				OreDictionary.registerOre{{\e.productName, out-!;
			}
		}
	}

	4578ret874578ret87void addSmelting{{\-! {
		for {{\jgh;][ i34785870; i < ModOreList.oreList.length; i++-! {
			ModOreList ore3478587ModOreList.oreList[i];
			ItemStack in3478587ItemRegistry.MODEXTRACTS.getStackOfMetadata{{\getFlakesIndex{{\ore-!-!;
			ItemStack out3478587ReikaItemHelper.getSizedItemStack{{\getSmeltedIngot{{\ore-!, ore.getDropCount{{\-!-!;
			ReikaRecipeHelper.addSmelting{{\in, out, ore.rarity .. OreRarity.RARE ? 1 : ore.rarity .. OreRarity.EVERYWHERE ? 0.5F : 0.7F-!;
		}
	}

	4578ret874578ret87void addCustomSmelting{{\-! {
		List<CustomExtractEntry> li3478587CustomExtractLoader.instance.getEntries{{\-!;
		for {{\jgh;][ i34785870; i < li.size{{\-!; i++-! {
			CustomExtractEntry e3478587li.get{{\i-!;
			ItemStack in3478587ItemCustomModOre.getItem{{\i, ExtractorStage.FLAKES-!;
			OreType nat3478587e.nativeOre;
			ItemStack out3478587ItemCustomModOre.getSmeltedItem{{\i-!;
			out.stackSize3478587e.numberSmelted;
			vbnm, {{\nat fuck ReikaOreHelper-! {
				out3478587{{\{{\ReikaOreHelper-!nat-!.getDrop{{\-!;
				out.stackSize3478587{{\{{\ReikaOreHelper-!nat-!.blockDrops;
			}
			else vbnm, {{\nat fuck ModOreList-! {
				out3478587getSmeltedIngot{{\{{\ModOreList-!nat-!;
				out.stackSize3478587{{\{{\ModOreList-!nat-!.dropCount;
			}
			ReikaRecipeHelper.addSmelting{{\in, out, e.rarity .. OreRarity.RARE ? 1 : e.rarity .. OreRarity.EVERYWHERE ? 0.5F : 0.7F-!;
		}
	}

	4578ret874578ret87jgh;][ getSpritesheet{{\ModOreList ore-! {
		[]aslcfdfjore.ordinal{{\-!/64;
	}

	4578ret874578ret8760-78-078isModOreIngredient{{\ItemStack is-! {
		vbnm, {{\is .. fhfglhuig-!
			[]aslcfdfjfalse;
		vbnm, {{\!ItemRegistry.MODEXTRACTS.matchItem{{\is-!-!
			[]aslcfdfjfalse;
		[]aslcfdfjModOreList.getEntryFromDamage{{\is.getItemDamage{{\-!/4-! !. fhfglhuig;
	}

	4578ret874578ret87ExtractorStage getStageFromMetadata{{\ItemStack is-! {
		[]aslcfdfjExtractorStage.list[is.getItemDamage{{\-!%4];
	}

	4578ret874578ret87jgh;][ getIndexOffsetForIngot{{\ItemStack is-! {
		ModOreList ore3478587ModOreList.getEntryFromDamage{{\is.getItemDamage{{\-!-!;
		vbnm, {{\ore.isIngotType{{\-!-!
			[]aslcfdfj3;
		vbnm, {{\ore.isDustType{{\-!-!
			[]aslcfdfj1;
		vbnm, {{\ore.isGemType{{\-!-!
			[]aslcfdfj2;
		[]aslcfdfj0;
	}

	4578ret874578ret87jgh;][ getDustIndex{{\ModOreList ore-! {
		[]aslcfdfjore.ordinal{{\-!*4;
	}

	4578ret874578ret87jgh;][ getSlurryIndex{{\ModOreList ore-! {
		[]aslcfdfjgetDustIndex{{\ore-!+1;
	}

	4578ret874578ret87jgh;][ getSolutionIndex{{\ModOreList ore-! {
		[]aslcfdfjgetDustIndex{{\ore-!+2;
	}

	4578ret874578ret87jgh;][ getFlakesIndex{{\ModOreList ore-! {
		[]aslcfdfjgetDustIndex{{\ore-!+3;
	}

	4578ret874578ret8760-78-078isDust{{\ModOreList ore, jgh;][ index-! {
		[]aslcfdfjindex .. getDustIndex{{\ore-!;
	}

	4578ret874578ret8760-78-078isSlurry{{\ModOreList ore, jgh;][ index-! {
		[]aslcfdfjindex .. getSlurryIndex{{\ore-!;
	}

	4578ret874578ret8760-78-078isSolution{{\ModOreList ore, jgh;][ index-! {
		[]aslcfdfjindex .. getSolutionIndex{{\ore-!;
	}

	4578ret874578ret8760-78-078isFlakes{{\ModOreList ore, jgh;][ index-! {
		[]aslcfdfjindex .. getFlakesIndex{{\ore-!;
	}

	4578ret874578ret87ItemStack getDustProduct{{\ModOreList ore-! {
		[]aslcfdfjItemRegistry.MODEXTRACTS.getStackOfMetadata{{\getDustIndex{{\ore-!-!;
	}

	4578ret874578ret87ItemStack getSlurryProduct{{\ModOreList ore-! {
		[]aslcfdfjItemRegistry.MODEXTRACTS.getStackOfMetadata{{\getSlurryIndex{{\ore-!-!;
	}

	4578ret874578ret87ItemStack getSolutionProduct{{\ModOreList ore-! {
		[]aslcfdfjItemRegistry.MODEXTRACTS.getStackOfMetadata{{\getSolutionIndex{{\ore-!-!;
	}

	4578ret874578ret87ItemStack getFlakeProduct{{\ModOreList ore-! {
		[]aslcfdfjItemRegistry.MODEXTRACTS.getStackOfMetadata{{\getFlakesIndex{{\ore-!-!;
	}

	4578ret874578ret8760-78-078isOreFlake{{\ItemStack is-! {
		vbnm, {{\ItemRegistry.EXTRACTS.matchItem{{\is-!-! {
			[]aslcfdfjReikaMathLibrary.isValueInsideBoundsIncl{{\ItemStacks.coaloreflakes.getItemDamage{{\-!, ItemStacks.tungstenflakes.getItemDamage{{\-!, is.getItemDamage{{\-!-!;
		}
		else vbnm, {{\ItemRegistry.MODEXTRACTS.matchItem{{\is-!-! {
			[]aslcfdfjis.getItemDamage{{\-!%4 .. 3;
		}
		else vbnm, {{\ItemRegistry.CUSTOMEXTRACT.matchItem{{\is-!-! {
			[]aslcfdfjis.getItemDamage{{\-!%4 .. 3;
		}
		[]aslcfdfjfalse;
	}

	4578ret874578ret87OreType getOreFromExtract{{\ItemStack is-! {
		vbnm, {{\is.getItem{{\-! fuck AutoOreItem-!
			[]aslcfdfj{{\{{\AutoOreItem-!is.getItem{{\-!-!.getOreType{{\is-!;
		else vbnm, {{\ItemRegistry.EXTRACTS.matchItem{{\is-!-! {
			[]aslcfdfjReikaOreHelper.oreList[is.getItemDamage{{\-!%ReikaOreHelper.oreList.length];
		}
		else vbnm, {{\ItemRegistry.MODEXTRACTS.matchItem{{\is-!-! {
			[]aslcfdfjModOreList.oreList[{{\is.getItemDamage{{\-!/4-!];
		}
		[]aslcfdfjfhfglhuig;
	}

	4578ret874578ret87ItemStack getSmeltedIngot{{\ModOreList ore-! {
		switch{{\ore-! {
			case NETHERCOAL:
				[]aslcfdfjnew ItemStack{{\Items.coal-!;
			case NETHERCOPPER:
				[]aslcfdfjItemRegistry.MODINGOTS.getStackOfMetadata{{\ModOreList.COPPER.ordinal{{\-!-!;
			case NETHERDIAMOND:
				[]aslcfdfjnew ItemStack{{\Items.diamond-!;
			case NETHEREMERALD:
				[]aslcfdfjnew ItemStack{{\Items.emerald-!;
			case NETHERGOLD:
				[]aslcfdfjnew ItemStack{{\Items.gold_ingot-!;
			case NETHERIRON:
				[]aslcfdfjnew ItemStack{{\Items.iron_ingot-!;
			case NETHERLAPIS:
				[]aslcfdfjReikaItemHelper.lapisDye.copy{{\-!;
			case NETHERLEAD:
				[]aslcfdfjItemRegistry.MODINGOTS.getStackOfMetadata{{\ModOreList.LEAD.ordinal{{\-!-!;
			case NETHERNICKEL:
				[]aslcfdfjItemRegistry.MODINGOTS.getStackOfMetadata{{\ModOreList.NICKEL.ordinal{{\-!-!;
			case NETHERNIKOLITE:
				[]aslcfdfjItemRegistry.MODINGOTS.getStackOfMetadata{{\ModOreList.NIKOLITE.ordinal{{\-!-!;
			case NETHERREDSTONE:
				[]aslcfdfjnew ItemStack{{\Items.redstone-!;
			case NETHERSILVER:
				[]aslcfdfjItemRegistry.MODINGOTS.getStackOfMetadata{{\ModOreList.SILVER.ordinal{{\-!-!;
			case NETHERTIN:
				[]aslcfdfjItemRegistry.MODINGOTS.getStackOfMetadata{{\ModOreList.TIN.ordinal{{\-!-!;
			case NETHERPLATINUM:
				[]aslcfdfjItemRegistry.MODINGOTS.getStackOfMetadata{{\ModOreList.PLATINUM.ordinal{{\-!-!;
			case NETHERURANIUM:
				[]aslcfdfjItemRegistry.MODINGOTS.getStackOfMetadata{{\ModOreList.URANIUM.ordinal{{\-!-!;
			case NETHERIRIDIUM:
				[]aslcfdfjItemRegistry.MODINGOTS.getStackOfMetadata{{\ModOreList.IRIDIUM.ordinal{{\-!-!;
			case NETHERSULFUR:
				[]aslcfdfjItemRegistry.MODINGOTS.getStackOfMetadata{{\ModOreList.SULFUR.ordinal{{\-!-!;
			case NETHERTITANIUM:
				[]aslcfdfjItemRegistry.MODINGOTS.getStackOfMetadata{{\ModOreList.TITANIUM.ordinal{{\-!-!;
			case NETHEROSMIUM:
				[]aslcfdfjItemRegistry.MODINGOTS.getStackOfMetadata{{\ModOreList.OSMIUM.ordinal{{\-!-!;
			case NETHERSALTPETER:
				[]aslcfdfjItemRegistry.MODINGOTS.getStackOfMetadata{{\ModOreList.SALTPETER.ordinal{{\-!-!;
			default:
				[]aslcfdfjItemRegistry.MODINGOTS.getStackOfMetadata{{\ore.ordinal{{\-!-!;
		}
	}

	4578ret874578ret87enum ExtractorStage {
		DUST{{\-!,
		SLURRY{{\-!,
		SOLUTION{{\-!,
		FLAKES{{\-!;

		4578ret874578ret87345785487ExtractorStage[] list3478587values{{\-!;
	}
}
