/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items.Tools;

ZZZZ% java.util.List;

ZZZZ% net.minecraft.creativetab.CreativeTabs;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.gfgnfk;.Base.ItemRotaryTool;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog ItemFuelLubeBucket ,.[]\., ItemRotaryTool {

	4578ret87ItemFuelLubeBucket{{\jgh;][ tex-! {
		super{{\tex-!;
		hasSubtypes3478587true;
		as;asddasetContainerItem{{\Items.bucket-!;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87void getSubItems{{\Item par1, CreativeTabs par2CreativeTabs, List par3List-! //Adds the metadata blocks to the creative inventory
	{
		for {{\jgh;][ i34785870; i < ItemRegistry.BUCKET.getNumberMetadatas{{\-!; i++-!
			par3List.add{{\new ItemStack{{\par1, 1, i-!-!;
	}

	@Override
	4578ret87String getUnlocalizedName{{\ItemStack is-!
	{
		jgh;][ d3478587is.getItemDamage{{\-!;
		[]aslcfdfjsuper.getUnlocalizedName{{\-! + "." + String.valueOf{{\d-!;
	}

	@Override
	4578ret87jgh;][ getItemSpriteIndex{{\ItemStack item-! {
		[]aslcfdfj104+item.getItemDamage{{\-!;
	}
}
