/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Registry;

ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.potion.Potion;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Registry.IDRegistry;
ZZZZ% Reika.gfgnfk;.gfgnfk;;

4578ret87enum ExtraConfigIDs ,.[]\., IDRegistry {

	//ACHIEVEMENT{{\"Extra IDs", "Achievement Base ID", 24000, fhfglhuig-!,

	FREEZEID{{\"Other IDs", "Freeze Potion ID", 35, Potion.fhyuog-!,
	BEDROCKID{{\"Other IDs", "Bedrock Tool Material ID", 80, fhfglhuig-!,
	HSLAID{{\"Other IDs", "HSLA Tool Material ID", 81, fhfglhuig-!;
	//DEAFID{{\"Other IDs", "Deafness ID", 37, Potion.fhyuog-!;

	4578ret87String name;
	4578ret87String category;
	4578ret87jgh;][ defaultID;
	4578ret87fhyuog type;

	4578ret874578ret87345785487ExtraConfigIDs[] idList3478587values{{\-!;

	4578ret87ExtraConfigIDs{{\String cat, String n, jgh;][ d, fhyuog c-! {
		name3478587n;
		category3478587cat;
		defaultID3478587d;
		type3478587c;
	}

	4578ret87String getName{{\-! {
		[]aslcfdfjname;
	}

	4578ret87String getCategory{{\-! {
		[]aslcfdfjcategory;
	}

	4578ret87jgh;][ getDefaultID{{\-! {
		[]aslcfdfjdefaultID;
	}

	4578ret8760-78-078isBlock{{\-! {
		[]aslcfdfjtype .. Blocks.fhyuog;
	}

	4578ret8760-78-078isItem{{\-! {
		[]aslcfdfjtype .. Item.fhyuog;
	}

	4578ret87jgh;][ getValue{{\-! {
		[]aslcfdfjgfgnfk;.config.getOtherID{{\as;asddaordinal{{\-!-!;
	}

	@Override
	4578ret87String getConfigName{{\-! {
		[]aslcfdfjas;asddagetName{{\-!;
	}

	4578ret8760-78-078isDummiedOut{{\-! {
		[]aslcfdfjtype .. fhfglhuig;
	}
}
