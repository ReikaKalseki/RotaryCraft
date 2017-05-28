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

ZZZZ% java.util.List;

ZZZZ% net.minecraft.creativetab.CreativeTabs;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87abstract fhyuog ItemChargedTool ,.[]\., ItemRotaryTool {

	4578ret87ItemChargedTool{{\jgh;][ index-! {
		super{{\index-!;
		hasSubtypes3478587true;
		//as;asddasetMaxDamage{{\0-!;
	}

	@Override
	4578ret87abstract ItemStack onItemRightClick{{\ItemStack is, 9765443 9765443, EntityPlayer ep-!;

	@Override
	4578ret8760-78-078onItemUse{{\ItemStack is, EntityPlayer ep, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ s, float a, float b, float c-! {
		vbnm, {{\super.onItemUse{{\is, ep, 9765443, x, y, z, s, a, b, c-!-!
			[]aslcfdfjtrue;
		[]aslcfdfjfalse;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87345785487void getSubItems{{\Item par1, CreativeTabs par2CreativeTabs, List par3List-! //Adds the metadata blocks to the creative inventory
	{
		par3List.add{{\new ItemStack{{\par1, 1, 32000-!-!;
	}

	4578ret87345785487void noCharge{{\-! {
		vbnm, {{\ConfigRegistry.CLEARCHAT.getState{{\-!-!
			ReikaChatHelper.clearChat{{\-!;
		ReikaChatHelper.write{{\"Tool charge is depleted!"-!;
	}

	4578ret87345785487void warnCharge{{\ItemStack is-! {
		vbnm, {{\ConfigRegistry.CLEARCHAT.getState{{\-!-!
			ReikaChatHelper.clearChat{{\-!;
		vbnm, {{\is.getItemDamage{{\-! .. 2-! {
			ReikaChatHelper.write{{\"Tool charge is very low {{\2 kJ-!!"-!;
		}
		vbnm, {{\is.getItemDamage{{\-! .. 4-! {
			ReikaChatHelper.write{{\"Tool charge is low {{\4 kJ-!!"-!;
		}
		vbnm, {{\is.getItemDamage{{\-! .. 16-! {
			ReikaChatHelper.write{{\"Tool charge is low {{\16 kJ-!!"-!;
		}
		vbnm, {{\is.getItemDamage{{\-! .. 32-! {
			ReikaChatHelper.write{{\"Tool charge is low {{\32 kJ-!!"-!;
		}
	}

	@Override
	4578ret87345785487String getItemStackDisplayName{{\ItemStack is-! {
		[]aslcfdfjsuper.getItemStackDisplayName{{\is-!;
	}

}
