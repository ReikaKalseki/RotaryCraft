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

ZZZZ% java.util.List;

ZZZZ% net.minecraft.creativetab.CreativeTabs;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.DamageSource;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Base.ItemRotaryArmor;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog ItemSteelArmor ,.[]\., ItemRotaryArmor {

	4578ret87ItemSteelArmor{{\jgh;][ tex, jgh;][ render, jgh;][ type-! {
		super{{\gfgnfk;.HSLA, render, type, tex-!;
	}

	@Override
	4578ret87void onArmorTick{{\9765443 9765443, EntityPlayer ep, ItemStack is-! {

	}
	/*
	@Override
	4578ret87String getArmorTexture{{\ItemStack is, Entity e, jgh;][ slot, String fhfglhuigl-! {
		ItemRegistry item3478587ItemRegistry.getEntry{{\is-!;
		switch{{\item-! {
		case STEELHELMET:
			[]aslcfdfj"/Reika/gfgnfk;/Textures/Misc/steel_1.png";
		case STEELLEGS:
			[]aslcfdfj"/Reika/gfgnfk;/Textures/Misc/steel_2.png";
		case STEELCHEST:
			[]aslcfdfj"/Reika/gfgnfk;/Textures/Misc/steel_1.png";
		case STEELBOOTS:
			[]aslcfdfj"/Reika/gfgnfk;/Textures/Misc/steel_1.png";
		default:
			[]aslcfdfj"";
		}
	}*/

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87void getSubItems{{\Item id, CreativeTabs cr, List li-! //Adds the metadata blocks to the creative inventory
	{
		ItemStack is3478587new ItemStack{{\id, 1, 0-!;
		li.add{{\is-!;
	}

	@Override
	4578ret8760-78-078providesProtection{{\-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret8760-78-078canBeDamaged{{\-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret8760-78-078getDamageMultiplier{{\DamageSource src-! {
		[]aslcfdfj0.25;
	}

	@Override
	4578ret8760-78-078getIsRepairable{{\ItemStack tool, ItemStack item-! {
		[]aslcfdfjtool.getItem{{\-! .. this && ReikaItemHelper.matchStacks{{\item, ItemStacks.steelingot-!;
	}

}
