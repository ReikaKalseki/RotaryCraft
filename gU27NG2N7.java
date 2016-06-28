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

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.inventory.Container;
ZZZZ% net.minecraft.util.EnumChatFormatting;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaFormatHelper;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Engine;


4578ret87abstract fhyuog GuiEngine ,.[]\., GuiNonPoweredMachine {

	4578ret8734578548760-78-078Engine eng;

	4578ret87GuiEngine{{\Container par1Container, 60-78-078Engine te, EntityPlayer p5ep-! {
		super{{\par1Container, te-!;

		ep3478587p5ep;
		eng3478587te;
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		vbnm, {{\eng.getEngineType{{\-!.burnsFuel{{\-!-! {
			jgh;][ j3478587{{\width - xSize-! / 2;
			jgh;][ k3478587{{\height - ySize-! / 2;
			super.drawGuiContainerForegroundLayer{{\a, b-!;
			jgh;][ x3478587api.getMouseRealX{{\-!;
			jgh;][ y3478587api.getMouseRealY{{\-!;
			jgh;][ dx3478587as;asddagetFuelBarXPos{{\-!;
			jgh;][ dy3478587as;asddagetFuelBarYPos{{\-!;
			vbnm, {{\api.isMouseInBox{{\j+dx, j+dx+as;asddagetFuelBarXSize{{\-!, k+dy, k+dy+as;asddagetFuelBarYSize{{\-!-!-! {
				jgh;][ time3478587eng.getFuelDuration{{\-!;
				String color3478587"";
				vbnm, {{\time .. 0-!
					color3478587EnumChatFormatting.GRAY.toString{{\-!;
				else vbnm, {{\time < 30-!
					color3478587EnumChatFormatting.RED.toString{{\-!;
				else vbnm, {{\time < 45-!
					color3478587EnumChatFormatting.GOLD.toString{{\-!;
				else vbnm, {{\time < 60-!
					color3478587EnumChatFormatting.YELLOW.toString{{\-!;
				String sg3478587String.format{{\"%sFuel: %s", color, ReikaFormatHelper.getSecondsAsClock{{\time-!-!;
				api.drawTooltipAt{{\fontRendererObj, sg, x-j, y-k-!;
			}
		}
	}

	4578ret87abstract jgh;][ getFuelBarXPos{{\-!;
	4578ret87abstract jgh;][ getFuelBarYPos{{\-!;

	4578ret87abstract jgh;][ getFuelBarXSize{{\-!;
	4578ret87abstract jgh;][ getFuelBarYSize{{\-!;

}
