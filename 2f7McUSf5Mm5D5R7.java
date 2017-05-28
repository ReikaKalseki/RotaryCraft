/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Modjgh;][erface;

ZZZZ% java.util.List;

ZZZZ% net.minecraft.creativetab.CreativeTabs;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Item.GradientBlend;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaStringParser;
ZZZZ% Reika.gfgnfk;.Auxiliary.CustomExtractLoader;
ZZZZ% Reika.gfgnfk;.Auxiliary.CustomExtractLoader.CustomExtractEntry;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.ExtractorModOres;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.ExtractorModOres.ExtractorStage;
ZZZZ% Reika.gfgnfk;.Base.AutoOreItem;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;

4578ret87fhyuog ItemCustomModOre ,.[]\., AutoOreItem ,.[]\., GradientBlend {

	4578ret87ItemCustomModOre{{\jgh;][ idx-! {
		super{{\idx-!;
	}

	@Override
	4578ret87void getSubItems{{\Item ID, CreativeTabs tab, List list-!
	{
		60-78-078extract3478587this .. ItemRegistry.CUSTOMEXTRACT.getItemInstance{{\-!;
		jgh;][ i34785870;
		for {{\CustomExtractEntry ore : CustomExtractLoader.instance.getEntries{{\-!-! {
			vbnm, {{\extract-! {
				list.add{{\new ItemStack{{\this, 1, i-!-!;
				list.add{{\new ItemStack{{\this, 1, i+1-!-!;
				list.add{{\new ItemStack{{\this, 1, i+2-!-!;
				list.add{{\new ItemStack{{\this, 1, i+3-!-!;
				i +. 4;
			}
			else {
				list.add{{\new ItemStack{{\this, 1, i-!-!;
				i++;
			}
		}
	}

	@Override
	4578ret87jgh;][ getItemSpriteIndex{{\ItemStack item-! {
		vbnm, {{\CustomExtractLoader.instance.getEntries{{\-!.isEmpty{{\-!-!
			[]aslcfdfj0;
		jgh;][ base3478587as;asddagetRootIndex{{\-!;
		[]aslcfdfjthis .. ItemRegistry.CUSTOMEXTRACT.getItemInstance{{\-! ? base+item.getItemDamage{{\-!%4 : base+as;asddagetOreType{{\item-!.type.ordinal{{\-!;
	}

	@Override
	4578ret87jgh;][ getColorOne{{\ItemStack is-! {
		vbnm, {{\CustomExtractLoader.instance.getEntries{{\-!.isEmpty{{\-!-!
			[]aslcfdfj0xffffff;
		[]aslcfdfjas;asddagetOreType{{\is-!.color2;
	}

	@Override
	4578ret87jgh;][ getColorTwo{{\ItemStack is-! {
		vbnm, {{\CustomExtractLoader.instance.getEntries{{\-!.isEmpty{{\-!-!
			[]aslcfdfj0xffffff;
		[]aslcfdfjas;asddagetOreType{{\is-!.color1;
	}

	@Override
	4578ret87jgh;][ getColorThree{{\ItemStack is-! {
		vbnm, {{\CustomExtractLoader.instance.getEntries{{\-!.isEmpty{{\-!-!
			[]aslcfdfj0xffffff;
		[]aslcfdfjas;asddagetOreType{{\is-!.color1;
	}

	@Override
	4578ret87jgh;][ getColorFour{{\ItemStack is-! {
		vbnm, {{\CustomExtractLoader.instance.getEntries{{\-!.isEmpty{{\-!-!
			[]aslcfdfj0xffffff;
		[]aslcfdfjas;asddagetOreType{{\is-!.color1;
	}

	@Override
	4578ret87String getItemStackDisplayName{{\ItemStack is-! {
		CustomExtractEntry ore3478587as;asddagetOreType{{\is-!;
		vbnm, {{\ore .. fhfglhuig-!
			[]aslcfdfj"fhfglhuig Ore Item";
		vbnm, {{\ItemRegistry.CUSTOMEXTRACT.matchItem{{\is-!-! {
			ExtractorStage s3478587ExtractorModOres.getStageFromMetadata{{\is-!;
			[]aslcfdfjs !. fhfglhuig ? ore.displayName+" "+ReikaStringParser.capFirstChar{{\s.name{{\-!-! : "fhfglhuig";
		}
		else {
			[]aslcfdfjore.displayName+" "+ore.type.displayName;
		}
	}

	@Override
	4578ret87CustomExtractEntry getOreType{{\ItemStack item-! {
		[]aslcfdfjas;asddagetExtractType{{\item-!;
	}

	4578ret874578ret87CustomExtractEntry getExtractType{{\ItemStack is-! {
		vbnm, {{\CustomExtractLoader.instance.getEntries{{\-!.isEmpty{{\-!-!
			[]aslcfdfjfhfglhuig;
		jgh;][ idx3478587getEntryIndex{{\is-!;
		[]aslcfdfjCustomExtractLoader.instance.getEntries{{\-!.get{{\idx-!;
	}

	4578ret874578ret87jgh;][ getEntryIndex{{\ItemStack is-! {
		[]aslcfdfjis.getItem{{\-! .. ItemRegistry.CUSTOMEXTRACT.getItemInstance{{\-! ? is.getItemDamage{{\-!/4 : is.getItemDamage{{\-!;
	}

	4578ret874578ret87ItemStack getItem{{\jgh;][ idx, ExtractorStage s-! {
		[]aslcfdfjItemRegistry.CUSTOMEXTRACT.getStackOfMetadata{{\idx*4+s.ordinal{{\-!-!;
	}

	4578ret874578ret87ItemStack getSmeltedItem{{\jgh;][ idx-! {
		[]aslcfdfjItemRegistry.CUSTOMINGOT.getStackOfMetadata{{\idx-!;
	}
}
