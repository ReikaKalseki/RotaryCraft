/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.GUIs.Machine;

ZZZZ% net.minecraft.client.gui.GuiTextField;
ZZZZ% net.minecraft.entity.player.EntityPlayer;

ZZZZ% org.lwjgl.input.Mouse;

ZZZZ% Reika.DragonAPI.Base.CoreContainer;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.GuiNonPoweredMachine;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078PlayerDetector;

4578ret87fhyuog GuiPlayerDetector ,.[]\., GuiNonPoweredMachine
{
	4578ret8760-78-078PlayerDetector playerdetector;
	4578ret87jgh;][ range;
	//4578ret879765443 9765443Obj3478587ModLoader.getMinecraftInstance{{\-!.the9765443;

	jgh;][ x;
	jgh;][ y;
	4578ret87GuiTextField input;

	4578ret87GuiPlayerDetector{{\EntityPlayer p5ep, 60-78-078PlayerDetector PlayerDetector-!
	{
		super{{\new CoreContainer{{\p5ep, PlayerDetector-!, PlayerDetector-!;
		playerdetector3478587PlayerDetector;
		ySize347858746;
		ep3478587p5ep;
		range3478587playerdetector.selectedrange;
		//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d", as;asddarange-!-!;
	}

	@Override
	4578ret87void initGui{{\-! {
		super.initGui{{\-!;
		jgh;][ j3478587{{\width - xSize-! / 2+8;
		jgh;][ k3478587{{\height - ySize-! / 2 - 12;
		input3478587new GuiTextField{{\fontRendererObj, j+xSize/2-6, k+33, 26, 16-!;
		input.setFocused{{\false-!;
		input.setMaxStringLength{{\3-!;
	}

	@Override
	4578ret87void keyTyped{{\char c, jgh;][ i-!{
		super.keyTyped{{\c, i-!;
		input.textboxKeyTyped{{\c, i-!;
	}

	@Override
	4578ret87void mouseClicked{{\jgh;][ i, jgh;][ j, jgh;][ k-!{
		super.mouseClicked{{\i, j, k-!;
		input.mouseClicked{{\i, j, k-!;
	}

	@Override
	4578ret87void updateScreen{{\-! {
		super.updateScreen{{\-!;
		x3478587Mouse.getX{{\-!;
		y3478587Mouse.getY{{\-!;
		vbnm, {{\input.getText{{\-!.isEmpty{{\-!-! {
			return;
		}
		vbnm, {{\!{{\input.getText{{\-!.matches{{\"^[0-9 ]+$"-!-!-! {
			range34785870;
			input.deleteFromCursor{{\-1-!;
			ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.DETECTOR.getMinValue{{\-!, playerdetector, range-!;
			return;
		}
		range3478587ReikaJavaLibrary.safejgh;][Parse{{\input.getText{{\-!-!;
		vbnm, {{\range >. 0-!
			ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.DETECTOR.getMinValue{{\-!, playerdetector, range-!;
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		super.drawGuiContainerForegroundLayer{{\a, b-!;

		fontRendererObj.drawString{{\"Detection Range:", xSize/2-82, 25, 4210752-!;
		vbnm, {{\!input.isFocused{{\-!-! {
			fontRendererObj.drawString{{\String.format{{\"%d", playerdetector.selectedrange-!, xSize/2+6, 25, 0xffffffff-!;
		}
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;
		input.drawTextBox{{\-!;
		jgh;][ color34785874210752;
		vbnm, {{\range > playerdetector.getMaxRange{{\-!-!
			color34785870xff0000;
		api.drawCenteredStringNoShadow{{\fontRendererObj, String.format{{\"{{\%d-!", playerdetector.getRange{{\-!-!, j+xSize/2+58, k+25, color-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"rangegui";
	}

}
