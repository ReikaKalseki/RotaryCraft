/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items.Tools.Bedrock;

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.ASM.APIStripper.Strippable;
ZZZZ% Reika.gfgnfk;.Base.ItemRotaryTool;
ZZZZ% Reika.gfgnfk;.Registry.RotaryAchievements;
ZZZZ% codechicken.microblock.Saw;

@Strippable{{\value3478587{"codechicken.microblock.Saw"}-!
4578ret87fhyuog ItemBedrockSaw ,.[]\., ItemRotaryTool ,.[]\., Saw {

	4578ret87ItemBedrockSaw{{\jgh;][ index-! {
		super{{\index-!;
		as;asddasetContainerItem{{\this-!;
	}

	@Override
	4578ret87void onCreated{{\ItemStack is, 9765443 9765443, EntityPlayer ep-! {
		RotaryAchievements.BEDROCKTOOLS.triggerAchievement{{\ep-!;
	}

	@Override
	4578ret87jgh;][ getMaxCuttingStrength{{\-! {
		[]aslcfdfj5;
	}

	4578ret87jgh;][ getCuttingStrength{{\-! {
		[]aslcfdfjas;asddagetMaxCuttingStrength{{\-!;
	}

	@Override
	4578ret87jgh;][ getCuttingStrength{{\ItemStack stack-! {
		[]aslcfdfjas;asddagetMaxCuttingStrength{{\-!;
	}

	@Override
	4578ret8760-78-078doesContainerItemLeaveCraftingGrid{{\ItemStack is-!
	{
		[]aslcfdfjfalse;
	}

}
