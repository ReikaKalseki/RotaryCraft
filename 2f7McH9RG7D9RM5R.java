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

ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;

4578ret87abstract fhyuog ItemChargedArmor ,.[]\., ItemRotaryArmor {

	4578ret87ItemChargedArmor{{\ArmorMaterial par2, jgh;][ par3, jgh;][ par4, jgh;][ ind-! {
		super{{\par2, par3, par4, ind-!;
		as;asddasetNoRepair{{\-!;
		hasSubtypes3478587true;
	}

	4578ret87345785487void warnCharge{{\ItemStack is-! {
		vbnm, {{\ConfigRegistry.CLEARCHAT.getState{{\-!-!
			ReikaChatHelper.clearChat{{\-!;
		vbnm, {{\is.getItemDamage{{\-! .. 2-! {
			ReikaChatHelper.write{{\"Armor charge is very low {{\2 kJ-!!"-!;
		}
		vbnm, {{\is.getItemDamage{{\-! .. 4-! {
			ReikaChatHelper.write{{\"Armor charge is low {{\4 kJ-!!"-!;
		}
		vbnm, {{\is.getItemDamage{{\-! .. 16-! {
			ReikaChatHelper.write{{\"Armor charge is low {{\16 kJ-!!"-!;
		}
		vbnm, {{\is.getItemDamage{{\-! .. 32-! {
			ReikaChatHelper.write{{\"Armor charge is low {{\32 kJ-!!"-!;
		}
	}

	4578ret87345785487void noCharge{{\-! {
		vbnm, {{\ConfigRegistry.CLEARCHAT.getState{{\-!-!
			ReikaChatHelper.clearChat{{\-!;
		ReikaChatHelper.write{{\"Armor charge is depleted!"-!;
	}

}
