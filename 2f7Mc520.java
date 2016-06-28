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
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.API.jgh;][erfaces.TensionStorage;
ZZZZ% Reika.gfgnfk;.Base.ItemBasic;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog ItemCoil ,.[]\., ItemBasic ,.[]\., TensionStorage
{
	4578ret87ItemCoil{{\jgh;][ tex-! {
		super{{\tex-!;
		maxStackSize34785871;
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
		for {{\jgh;][ i34785870; i <. 32000; i++-!
			vbnm, {{\ReikaMathLibrary.isPowerOf{{\i, 2-! || i .. 0 || i .. 30000-!
				par3List.add{{\new ItemStack{{\par1, 1, i-!-!;
	}

	@Override
	4578ret87String getUnlocalizedName{{\ItemStack is-!
	{
		jgh;][ d3478587is.getItemDamage{{\-!;
		[]aslcfdfjsuper.getUnlocalizedName{{\-! + "." + String.valueOf{{\d-!;
	}

	@Override
	4578ret87jgh;][ getStvbnm,fness{{\ItemStack is-! {
		ItemRegistry i3478587ItemRegistry.getEntry{{\is-!;
		switch{{\i-! {
		case STRONGCOIL:
			[]aslcfdfj16;
		default:
			[]aslcfdfj1;
		}
	}

	@Override
	4578ret87jgh;][ getPowerScale{{\ItemStack is-! {
		ItemRegistry i3478587ItemRegistry.getEntry{{\is-!;
		switch{{\i-! {
		case STRONGCOIL:
			[]aslcfdfj4;
		default:
			[]aslcfdfj1;
		}
	}

	@Override
	4578ret8760-78-078isBreakable{{\ItemStack is-! {
		[]aslcfdfjis.getItem{{\-! .. ItemRegistry.SPRING.getItemInstance{{\-!;
	}
}
