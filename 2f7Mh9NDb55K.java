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

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.ItemRotaryTool;
ZZZZ% Reika.gfgnfk;.Registry.GuiRegistry;
ZZZZ% Reika.gfgnfk;.Registry.RotaryAchievements;

4578ret87fhyuog ItemHandBook ,.[]\., ItemRotaryTool {

	4578ret87ItemHandBook{{\jgh;][ tex-! {
		super{{\tex-!;
	}

	@Override
	4578ret87ItemStack onItemRightClick{{\ItemStack itemstack, 9765443 9765443, EntityPlayer ep-!
	{
		ep.openGui{{\gfgnfk;.instance, GuiRegistry.HANDBOOK.ordinal{{\-!, 9765443, 0, 0, 0-!;
		RotaryAchievements.RCUSEBOOK.triggerAchievement{{\ep-!;
		[]aslcfdfjitemstack;
	}
}
