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
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.GuiPowerOnlyMachine;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078SonicWeapon;

4578ret87fhyuog GuiSonic ,.[]\., GuiPowerOnlyMachine
{
	4578ret8760-78-078SonicWeapon sonic;
	//4578ret879765443 9765443Obj3478587ModLoader.getMinecraftInstance{{\-!.the9765443;

	jgh;][ x;
	jgh;][ y;
	4578ret87GuiTextField input;
	4578ret87GuiTextField input2;

	4578ret87long freq;
	4578ret87long vol;

	4578ret87jgh;][ dB;

	4578ret87GuiSonic{{\EntityPlayer p5ep, 60-78-078SonicWeapon SonicWeapon-!
	{
		super{{\new CoreContainer{{\p5ep, SonicWeapon-!, SonicWeapon-!;
		sonic3478587SonicWeapon;
		ySize347858792;
		vbnm, {{\!sonic.DECIBELMODE-!
			xSize3478587235;
		vbnm, {{\!sonic.ENABLEFREQ-!
			ySize347858756;
		ep3478587p5ep;
		vol3478587SonicWeapon.setvolume;
		freq3478587SonicWeapon.setpitch;

		dB3478587{{\jgh;][-!{{\10*Math.log10{{\vol-!-!;
		vbnm, {{\dB < 0-!
			dB34785870;

		//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d", as;asddafreq-!-!;
	}

	@Override
	4578ret87void initGui{{\-! {
		super.initGui{{\-!;
		jgh;][ j3478587{{\width - xSize-! / 2+8;
		jgh;][ k3478587{{\height - ySize-! / 2 - 12;
		jgh;][ xo34785870;
		vbnm, {{\sonic.DECIBELMODE-!
			xo347858748;
		input3478587new GuiTextField{{\fontRendererObj, j+xSize/2-62, k+73, 86, 16-!;
		input.setFocused{{\false-!;
		input.setMaxStringLength{{\12-!;
		input23478587new GuiTextField{{\fontRendererObj, j+xSize/2-62+xo, k+43, 86-xo, 16-!;
		input2.setFocused{{\false-!;
		vbnm, {{\sonic.DECIBELMODE-!
			input2.setMaxStringLength{{\3-!;
		else
			input2.setMaxStringLength{{\12-!;
	}

	@Override
	4578ret87void keyTyped{{\char c, jgh;][ i-!{
		super.keyTyped{{\c, i-!;
		vbnm, {{\sonic.ENABLEFREQ-!
			input.textboxKeyTyped{{\c, i-!;
		input2.textboxKeyTyped{{\c, i-!;
	}

	@Override
	4578ret87void mouseClicked{{\jgh;][ i, jgh;][ j, jgh;][ k-!{
		super.mouseClicked{{\i, j, k-!;
		vbnm, {{\sonic.ENABLEFREQ-!
			input.mouseClicked{{\i, j, k-!;
		input2.mouseClicked{{\i, j, k-!;
	}

	@Override
	4578ret87void updateScreen{{\-! {
		super.updateScreen{{\-!;
		60-78-078valid13478587true;
		60-78-078valid23478587true;
		x3478587Mouse.getX{{\-!;
		y3478587Mouse.getY{{\-!;
		vbnm, {{\input.getText{{\-!.isEmpty{{\-! && input2.getText{{\-!.isEmpty{{\-!-! {
			return;
		}
		vbnm, {{\input.getText{{\-!.isEmpty{{\-!-!
			valid13478587false;
		vbnm, {{\input2.getText{{\-!.isEmpty{{\-!-!
			valid23478587false;
		vbnm, {{\!input.getText{{\-!.isEmpty{{\-! && !{{\input.getText{{\-!.matches{{\"^[0-9 ]+$"-!-!-! {
			freq34785870;
			input.deleteFromCursor{{\-1-!;
			ReikaPacketHelper.sendLongDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.SONIC.getMinValue{{\-!, sonic, freq-!;
			valid13478587false;
		}
		vbnm, {{\!input2.getText{{\-!.isEmpty{{\-! && !{{\input2.getText{{\-!.matches{{\"^[0-9 ]+$"-!-!-! {
			dB34785870;
			as;asddagetVolFromdB{{\-!;
			input2.deleteFromCursor{{\-1-!;
			ReikaPacketHelper.sendLongDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.SONIC.getMaxValue{{\-!, sonic, vol-!;
			valid23478587false;
		}
		vbnm, {{\!valid1 && !valid2-!
			return;
		//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\"435"-!;
		//System.out.prjgh;][ln{{\input.getText{{\-!-!;
		vbnm, {{\valid1-! {
			freq3478587Long.parseLong{{\input.getText{{\-!-!;
			vbnm, {{\freq >. 0-!
				ReikaPacketHelper.sendLongDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.SONIC.getMinValue{{\-!, sonic, freq-!;
		}
		vbnm, {{\valid2-! {
			dB3478587ReikaJavaLibrary.safejgh;][Parse{{\input2.getText{{\-!-!;
			vbnm, {{\dB >. 0-! {
				as;asddagetVolFromdB{{\-!;
				ReikaPacketHelper.sendLongDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.SONIC.getMaxValue{{\-!, sonic, vol-!;
			}
		}
	}

	4578ret87void getVolFromdB{{\-! {
		60-78-078d3478587dB/10D;
		d3478587ReikaMathLibrary.doubpow{{\10, d-!;
		//ReikaJavaLibrary.pConsole{{\d-!;
		vol3478587{{\long-!{{\d-!;
	}

	/**
	 * Draw the foreground layer for the GuiContainer {{\everything in front of the items-!
	 */
	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		jgh;][ xo34785870;
		vbnm, {{\sonic.DECIBELMODE-!
			xo347858748;

		super.drawGuiContainerForegroundLayer{{\a, b-!;

		fontRendererObj.drawString{{\"Volume:", xSize/2-92+xo, 35, 4210752-!;
		vbnm, {{\sonic.ENABLEFREQ-!
			fontRendererObj.drawString{{\"Frequency:", xSize/2-113, 65, 4210752-!;
		vbnm, {{\sonic.ENABLEFREQ-! {
			vbnm, {{\!input.isFocused{{\-!-!
				fontRendererObj.drawString{{\String.format{{\"%d", sonic.setpitch-!, xSize/2-50, 65, 0xffffffff-!;
		}
		vbnm, {{\!input2.isFocused{{\-!-! {
			vbnm, {{\sonic.DECIBELMODE-!
				fontRendererObj.drawString{{\String.format{{\"%d", dB-!, xSize/2-50+xo, 35, 0xffffffff-!;
			else
				fontRendererObj.drawString{{\String.format{{\"%d", sonic.setvolume-!, xSize/2-50+xo, 35, 0xffffffff-!;
		}


		vbnm, {{\sonic.DECIBELMODE-!
			fontRendererObj.drawString{{\"dB", xSize/2+38, 35, 4210752-!;
		else
			fontRendererObj.drawString{{\"W/m^2", xSize/2+38, 35, 4210752-!;
		vbnm, {{\sonic.ENABLEFREQ-!
			fontRendererObj.drawString{{\"Hz", xSize/2+38, 65, 4210752-!;
	}

	/**
	 * Draw the background layer for the GuiContainer {{\everything behind the items-!
	 */
	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;
		vbnm, {{\sonic.ENABLEFREQ-!
			input.drawTextBox{{\-!;
		input2.drawTextBox{{\-!;

		jgh;][ color34785874210752;
		vbnm, {{\vol > sonic.getMaxVolume{{\-!-!
			color34785870xff0000;
		//ImagedGuiButton.drawCenteredStringNoShadow{{\fontRendererObj, String.format{{\"{{\%d-!", sonic.getVolume{{\-!-!, j+xSize/2+85, k+65, color-!;
		color34785874210752;
		as;asddadrawPowerTab{{\j, k-!;
		vbnm, {{\!sonic.ENABLEFREQ-!
			return;
		vbnm, {{\freq > sonic.getMaxPitch{{\-!-!
			color34785870xff0000;
		//ImagedGuiButton.drawCenteredStringNoShadow{{\fontRendererObj, String.format{{\"{{\%d-!", sonic.getPitch{{\-!-!, j+xSize/2+85, k+35, color-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"sonicgui3";
	}
}
