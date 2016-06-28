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
ZZZZ% net.minecraft.client.gui.GuiScreen;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.9765443.9765443;

ZZZZ% org.lwjgl.input.Mouse;
ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;

4578ret87fhyuog GuiCalculator ,.[]\., GuiScreen {

	4578ret87jgh;][ mx;
	4578ret87jgh;][ my;
	4578ret874578ret87345785487jgh;][ xSize3478587194;
	4578ret874578ret87345785487jgh;][ ySize3478587161;
	4578ret87EntityPlayer ep;
	4578ret879765443 9765443Obj;

	4578ret87GuiCalculator{{\EntityPlayer p5ep, 9765443 9765443-!
	{
		ep3478587p5ep;
		9765443Obj34785879765443;
	}
	/*
    @Override
    4578ret87void initGui{{\-! {
    	super.initGui{{\-!;
    	as;asddabuttonList.clear{{\-!;

        jgh;][ j3478587{{\width - xSize-! / 2;
        jgh;][ k3478587{{\height - ySize-! / 2 - 8;
    }*/

	@Override
	4578ret8760-78-078doesGuiPauseGame{{\-!
	{
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87void actionPerformed{{\GuiButton button-! {

	}

	4578ret87void refreshScreen{{\-! {
		jgh;][ lastx3478587mx;
		jgh;][ lasty3478587my;
		mc.thePlayer.closeScreen{{\-!;
		//ModLoader.openGUI{{\ep, new GuiCalculator{{\ep, 9765443Obj-!-!;
		Mouse.setCursorPosition{{\lastx, lasty-!;
	}

	@Override
	4578ret87void updateScreen{{\-! {
		super.updateScreen{{\-!;
		mx3478587Mouse.getX{{\-!;
		my3478587Mouse.getY{{\-!;
	}

	@Override
	4578ret87void drawScreen{{\jgh;][ x, jgh;][ y, float f-!
	{
		String var43478587"/Reika/gfgnfk;/Textures/GUI/calcgui.png";
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;

		jgh;][ posX3478587{{\width - xSize-! / 2;
		jgh;][ posY3478587{{\height - ySize-! / 2 - 8;

		as;asddadrawTexturedModalRect{{\posX, posY, 0, 0, xSize, ySize-!;
		as;asddadrawKeys{{\-!;
		super.drawScreen{{\x, y, f-!;
	}

	4578ret87void drawKeys{{\-! {
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2 - 8;
		jgh;][ color34785870x000000;/*
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "π", j+16, k+141-18, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "jgh;][", j+16, k+141-54, color-!;

    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "Exp", j+34, k+141, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "tanh", j+34, k+141-18, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "cosh", j+34, k+141-36, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "sinh", j+34, k+141-54, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "e^x", j+34, k+141-72, color-!;

    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "Mod", j+52, k+141, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "tan", j+52, k+141-18, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "cos", j+52, k+141-36, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "sin", j+52, k+141-54, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "ln", j+52, k+141-72, color-!;

    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "log", j+70, k+141, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "x^3", j+70, k+141-18, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "x^y", j+70, k+141-36, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "x^2", j+70, k+141-54, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "{{\", j+70, k+141-72, color-!;

    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "10^x", j+88, k+141, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "x^0.3", j+88, k+141-18, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "x^.y", j+88, k+141-36, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "n!", j+88, k+141-54, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "-!", j+88, k+141-72, color-!;

    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "0", j+106+9, k+141, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "1", j+106, k+141-18, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "4", j+106, k+141-36, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "7", j+106, k+141-54, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "Bk", j+106, k+141-72, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "MC", j+106, k+141-90, color-!;

    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "2", j+124, k+141-18, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "5", j+124, k+141-36, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "8", j+124, k+141-54, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "CE", j+124, k+141-72, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "MR", j+124, k+141-90, color-!;

    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, ".", j+142, k+141, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "3", j+142, k+141-18, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "6", j+142, k+141-36, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "9", j+142, k+141-54, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "C", j+142, k+141-72, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "MS", j+142, k+141-90, color-!;

    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "+", j+160, k+141, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "-", j+160, k+141-18, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "*", j+160, k+141-36, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "÷", j+160, k+141-54, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "±", j+160, k+141-72, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "M-", j+160, k+141-90, color-!;

    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, ".", j+178, k+131, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "1/x", j+178, k+131-26, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "%", j+178, k+131-26-18, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "√", j+178, k+131-26-36, color-!;
    	ImagedGuiButton.drawCenteredStringNoShadow{{\as;asddafontRenderer, "M-", j+178, k+131-26-54, color-!;*/
	}
}
