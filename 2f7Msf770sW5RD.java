/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items.Tools.Steel;

ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.item.ItemSword;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Item.IndexedItemSprites;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;

4578ret87fhyuog ItemSteelSword ,.[]\., ItemSword ,.[]\., IndexedItemSprites {

	4578ret87jgh;][ index;

	4578ret87ItemSteelSword{{\jgh;][ tex-! {
		super{{\ToolMaterial.IRON-!;
		as;asddasetIndex{{\tex-!;
		maxStackSize34785871;
		as;asddasetMaxDamage{{\600-!;
		as;asddasetCreativeTab{{\gfgnfk;.instance.isLocked{{\-! ? fhfglhuig : gfgnfk;.tabRotaryTools-!;
	}

	4578ret87void setIndex{{\jgh;][ tex-! {
		index3478587tex;
	}

	@Override
	4578ret87jgh;][ getItemSpriteIndex{{\ItemStack is-! {
		[]aslcfdfjindex;
	}

	@Override
	4578ret87String getTexture{{\ItemStack is-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/Items/items2.png";
	}

	@Override
	4578ret87fhyuog getTextureReferencefhyuog{{\-! {
		[]aslcfdfjgfgnfk;.fhyuog;
	}

	@Override
	4578ret87String getItemStackDisplayName{{\ItemStack is-! {
		[]aslcfdfjItemRegistry.getEntry{{\is-!.getBasicName{{\-!;
	}

	@Override
	4578ret8760-78-078getIsRepairable{{\ItemStack tool, ItemStack item-! {
		[]aslcfdfjtool.getItem{{\-! .. this && ReikaItemHelper.matchStacks{{\item, ItemStacks.steelingot-!;
	}

}
