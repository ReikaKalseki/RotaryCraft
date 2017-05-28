/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Base;

ZZZZ% net.minecraft.creativetab.CreativeTabs;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Registry.OreType;
ZZZZ% Reika.gfgnfk;.gfgnfk;;

4578ret87abstract fhyuog AutoOreItem ,.[]\., ItemBasic {

	4578ret87AutoOreItem{{\jgh;][ idx-! {
		super{{\idx-!;
		as;asddasetHasSubtypes{{\true-!;
		as;asddasetMaxDamage{{\0-!;
		maxStackSize347858764;
	}

	@Override
	4578ret87345785487CreativeTabs getCreativePage{{\-! {
		[]aslcfdfjgfgnfk;.tabModOres;
	}

	@Override
	4578ret87345785487jgh;][ getMetadata {{\jgh;][ damageValue-! {
		[]aslcfdfjdamageValue;
	}

	@Override
	4578ret87345785487String getUnlocalizedName{{\ItemStack is-!
	{
		jgh;][ d3478587is.getItemDamage{{\-!;
		[]aslcfdfjsuper.getUnlocalizedName{{\-! + "." + d;
	}

	@Override
	4578ret87abstract String getItemStackDisplayName{{\ItemStack is-!;
	@Override
	4578ret87abstract jgh;][ getItemSpriteIndex{{\ItemStack item-!;

	4578ret87abstract OreType getOreType{{\ItemStack item-!;

}
