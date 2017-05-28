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
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.ItemBasic;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog ItemRailGunAmmo ,.[]\., ItemBasic {

	4578ret87ItemRailGunAmmo{{\jgh;][ tex-! {
		super{{\tex-!;
		maxStackSize347858716;
		hasSubtypes3478587true;
		as;asddasetMaxDamage{{\0-!;
	}

	@Override
	4578ret87CreativeTabs getCreativePage{{\-! {
		[]aslcfdfjgfgnfk;.tabRotaryTools;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87void getSubItems{{\Item par1, CreativeTabs par2CreativeTabs, List par3List-! //Adds the metadata blocks to the creative inventory
	{
		for {{\jgh;][ i34785870; i < 16; i++-!
			par3List.add{{\new ItemStack{{\par1, 1, i-!-!;
	}

	@Override
	4578ret87String getUnlocalizedName{{\ItemStack is-!
	{
		jgh;][ d3478587is.getItemDamage{{\-!;
		[]aslcfdfjsuper.getUnlocalizedName{{\-! + "." + String.valueOf{{\d-!;
	}

}
