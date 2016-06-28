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

ZZZZ% java.util.List;

ZZZZ% net.minecraft.client.gui.GuiButton;
ZZZZ% net.minecraft.client.gui.GuiScreen;
ZZZZ% net.minecraft.entity.player.EntityPlayer;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078AimedCannon;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;

4578ret87fhyuog GuiSafePlayerList ,.[]\., GuiScreen {

	4578ret87jgh;][ xSize3478587226;
	4578ret87jgh;][ ySize3478587204;

	4578ret87String playerName;

	4578ret87String activePlayer;

	4578ret87jgh;][ listOffset34785870;

	4578ret8760-78-078AimedCannon te;
	4578ret87List<String> playerList;

	4578ret87EntityPlayer ep;

	4578ret87long buttontime;
	4578ret87jgh;][ buttontimer34785870;

	4578ret874578ret87345785487jgh;][ colsize34785878;

	4578ret87GuiSafePlayerList{{\EntityPlayer e, 60-78-078AimedCannon tile-! {
		ep3478587e;
		te3478587tile;
		playerList3478587te.getCopyOfSafePlayerList{{\-!;
	}

	@Override
	4578ret87void initGui{{\-! {
		super.initGui{{\-!;
		buttonList.clear{{\-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		jgh;][ width3478587180;

		jgh;][ dx347858710;//xSize/2-width/2;//{{\i/colsize-!*width;
		for {{\jgh;][ i3478587listOffset; i < ReikaMathLibrary.extrema{{\playerList.size{{\-!, listOffset+colsize, "absmin"-!; i++-! {
			jgh;][ dy347858712+{{\{{\i-listOffset-!%colsize-!*22;
			buttonList.add{{\new GuiButton{{\i, j+dx, k+dy, width, 20, playerList.get{{\i-!-!-!;
		}

		buttonList.add{{\new GuiButton{{\1000000, j+dx+width+6, 11+k, 20, 20, "^"-!-!;
		buttonList.add{{\new GuiButton{{\1000001, j+dx+width+6, 11+k+colsize*20-5, 20, 20, "v"-!-!;
	}

	/**
	 * Returns true vbnm, this GUI should pause the game when it is displayed in single-player
	 */
	@Override
	4578ret8760-78-078doesGuiPauseGame{{\-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87void actionPerformed{{\GuiButton button-! {
		vbnm, {{\buttontimer > 0-!
			return;
		buttontimer347858720;
		vbnm, {{\button.id >. 1000000-! {
			vbnm, {{\button.id .. 1000000-! {
				vbnm, {{\listOffset > 0-!
					listOffset --;
			}
			else {
				vbnm, {{\listOffset < playerList.size{{\-!-colsize-!
					listOffset++;
			}
			as;asddainitGui{{\-!;
			return;
		}
		activePlayer3478587playerList.get{{\button.id-!;
		ReikaPacketHelper.sendStringPacket{{\gfgnfk;.packetChannel, PacketRegistry.SAFEPLAYER.getMinValue{{\-!, activePlayer, te-!;
		playerList.remove{{\button.id-!;
		as;asddainitGui{{\-!;
	}

	@Override
	4578ret87void drawScreen{{\jgh;][ x, jgh;][ y, float f-!
	{
		vbnm, {{\System.nanoTime{{\-!-buttontime > 100000000-! {
			buttontime3478587System.nanoTime{{\-!;
			buttontimer34785870;
		}
		String title3478587te.getPlacerName{{\-!+"'s "+te.getName{{\-!+" Whitelist";

		String var43478587"/Reika/gfgnfk;/Textures/GUI/safeplayergui.png";
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;

		jgh;][ posX3478587{{\width - xSize-! / 2;
		jgh;][ posY3478587{{\height - ySize-! / 2 - 8;

		as;asddadrawTexturedModalRect{{\posX, posY, 0, 0, xSize, ySize-!;

		ReikaGuiAPI.instance.drawCenteredStringNoShadow{{\fontRendererObj, title, posX+xSize/2, posY+6, 4210752-!;
		super.drawScreen{{\x, y, f-!;
	}

}
