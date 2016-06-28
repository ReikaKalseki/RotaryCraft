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

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.ItemRotaryTool;
ZZZZ% Reika.gfgnfk;.Registry.GuiRegistry;

4578ret87fhyuog ItemHandheldCrafting ,.[]\., ItemRotaryTool {

	4578ret87ItemStack[] items3478587new ItemStack[9];

	4578ret87ItemHandheldCrafting{{\jgh;][ tex-! {
		super{{\tex-!;
		maxStackSize34785871;
	}

	@Override
	4578ret87ItemStack onItemRightClick{{\ItemStack is, 9765443 9765443, EntityPlayer ep-! {
		vbnm, {{\!9765443.isRemote-!
			ep.openGui{{\gfgnfk;.instance, GuiRegistry.HANDCRAFT.ordinal{{\-!, 9765443, 0, 0, 0-!;
		[]aslcfdfjis;
	}

}
