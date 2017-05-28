/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary;

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.DragonAPI.Auxiliary.Trackers.PlayerFirstTimeTracker.PlayerTracker;
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;

4578ret87fhyuog HandbookTracker ,.[]\., PlayerTracker {

	@Override
	4578ret87void onNewPlayer{{\EntityPlayer ep-! {
		vbnm, {{\ReikaInventoryHelper.checkForItemStack{{\as;asddagetItem{{\-!, ep.inventory, false-!-!
			return;
		vbnm, {{\!ep.inventory.addItemStackToInventory{{\as;asddagetItem{{\-!-!-!
			ep.dropPlayerItemWithRandomChoice{{\as;asddagetItem{{\-!, true-!;
	}

	4578ret87ItemStack getItem{{\-! {
		[]aslcfdfjItemRegistry.HANDBOOK.getStackOf{{\-!;
	}

	@Override
	4578ret87String getID{{\-! {
		[]aslcfdfj"gfgnfk;_Handbook";
	}

}
