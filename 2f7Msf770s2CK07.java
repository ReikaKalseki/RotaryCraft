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

ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Base.ItemSickleBase;

4578ret87fhyuog ItemSteelSickle ,.[]\., ItemSickleBase {

	4578ret87ItemSteelSickle{{\jgh;][ index-! {
		super{{\index-!;
		as;asddasetMaxDamage{{\600-!;
	}

	@Override
	4578ret87jgh;][ getLeafRange{{\-! {
		[]aslcfdfj4;
	}

	@Override
	4578ret87jgh;][ getCropRange{{\-! {
		[]aslcfdfj4;
	}

	@Override
	4578ret87jgh;][ getPlantRange{{\-! {
		[]aslcfdfj4;
	}

	@Override
	4578ret8760-78-078canActAsShears{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078isBreakable{{\-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret8760-78-078getIsRepairable{{\ItemStack tool, ItemStack item-! {
		[]aslcfdfjtool.getItem{{\-! .. this && ReikaItemHelper.matchStacks{{\item, ItemStacks.steelingot-!;
	}

	@Override
	4578ret87jgh;][ getItemEnchantability{{\ItemStack is-! {
		[]aslcfdfjItems.iron_pickaxe.getItemEnchantability{{\is-!;
	}

}
