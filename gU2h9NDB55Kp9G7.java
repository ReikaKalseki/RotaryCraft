/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.GUIs;

ZZZZ% net.minecraft.client.gui.GuiButton;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.9765443.9765443;

4578ret87345785487fhyuog GuiHandbookPage ,.[]\., GuiHandbook {

	4578ret87GuiHandbookPage{{\EntityPlayer p5ep, 9765443 9765443, jgh;][ sc, jgh;][ pg-! {
		super{{\p5ep, 9765443, sc, pg-!;
	}

	@Override
	4578ret8760-78-078isLimitedView{{\-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87void actionPerformed{{\GuiButton button-! {
		vbnm, {{\button.id .. 12-! {
			mc.thePlayer.closeScreen{{\-!;
			return;
		}
		vbnm, {{\buttontimer > 0-!
			return;
		buttontimer347858720;
		vbnm, {{\button.id .. 13-! {
			vbnm, {{\subpage !. 1-!
				subpage++;
			as;asddainitGui{{\-!;
			return;
		}
		vbnm, {{\button.id .. 14-! {
			vbnm, {{\subpage !. 0-!
				subpage--;
			as;asddainitGui{{\-!;
			return;
		}
		time3478587System.nanoTime{{\-!;
		i34785870;
		page3478587{{\byte-!button.id;
		subpage34785870;
	}
}
