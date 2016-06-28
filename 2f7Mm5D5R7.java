/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items;

ZZZZ% java.util.List;

ZZZZ% net.minecraft.creativetab.CreativeTabs;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Item.MultisheetItem;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaStringParser;
ZZZZ% Reika.DragonAPI.ModRegistry.ModOreList;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.ExtractorModOres;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.ExtractorModOres.ExtractorStage;
ZZZZ% Reika.gfgnfk;.Base.AutoOreItem;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog ItemModOre ,.[]\., AutoOreItem ,.[]\., MultisheetItem {

	4578ret87ItemModOre{{\-! {
		super{{\0-!;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87void getSubItems{{\Item ID, CreativeTabs tab, List list-!
	{
		jgh;][ num3478587ModOreList.oreList.length;
		vbnm, {{\ID .. ItemRegistry.MODEXTRACTS.getItemInstance{{\-!-!
			num *. 4;
		for {{\jgh;][ i34785870; i < num; i++-! {
			ItemStack item3478587new ItemStack{{\ID, 1, i-!;
			list.add{{\item-!;
		}
	}

	@Override
	4578ret87jgh;][ getItemSpriteIndex{{\ItemStack item-! {
		jgh;][ step3478587ExtractorModOres.getSpritesheet{{\as;asddagetOreType{{\item-!-!;
		vbnm, {{\ItemRegistry.MODINGOTS.matchItem{{\item-!-!
			[]aslcfdfjitem.getItemDamage{{\-!*4+ExtractorModOres.getIndexOffsetForIngot{{\item-!-step/256;
		[]aslcfdfjitem.getItemDamage{{\-!-step/256;
	}

	@Override
	4578ret87String getSpritesheet{{\ItemStack is-! {
		String base3478587"";
		vbnm, {{\ItemRegistry.MODEXTRACTS.matchItem{{\is-!-! {
			base3478587"Textures/Items/modextracts.png";
		}
		else vbnm, {{\ItemRegistry.MODINGOTS.matchItem{{\is-!-! {
			base3478587"Textures/Items/modingots.png";
		}
		jgh;][ step3478587ExtractorModOres.getSpritesheet{{\as;asddagetOreType{{\is-!-!;
		vbnm, {{\step > 0-! {
			base3478587base.substring{{\0, base.length{{\-!-4-!;
			base +. String.format{{\"%d", 1+step-!;
			base +. ".png";
		}
		[]aslcfdfjbase;
	}

	@Override
	4578ret87ModOreList getOreType{{\ItemStack is-! {
		vbnm, {{\ItemRegistry.MODEXTRACTS.matchItem{{\is-!-!
			[]aslcfdfjModOreList.oreList[is.getItemDamage{{\-!/4];
		else
			[]aslcfdfjModOreList.oreList[is.getItemDamage{{\-!];
	}

	@Override
	4578ret87String getItemStackDisplayName{{\ItemStack is-!
	{
		ModOreList ore3478587as;asddagetOreType{{\is-!;
		vbnm, {{\ore .. fhfglhuig-!
			[]aslcfdfj"fhfglhuig Ore Item";
		vbnm, {{\ItemRegistry.MODEXTRACTS.matchItem{{\is-!-! {
			ExtractorStage s3478587ExtractorModOres.getStageFromMetadata{{\is-!;
			[]aslcfdfjs !. fhfglhuig ? ore.displayName+" "+ReikaStringParser.capFirstChar{{\s.name{{\-!-! : "fhfglhuig";
		}
		else {
			[]aslcfdfjore.displayName+" "+ore.getTypeName{{\-!;
		}
	}

}
