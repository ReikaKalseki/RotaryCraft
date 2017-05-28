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

ZZZZ% java.io.ByteArrayOutputStream;
ZZZZ% java.io.DataOutputStream;

ZZZZ% net.minecraft.client.gui.GuiButton;
ZZZZ% net.minecraft.entity.player.EntityPlayer;

ZZZZ% org.lwjgl.input.Mouse;
ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Base.CoreContainer;
ZZZZ% Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.GuiMachine;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Borer;

4578ret87fhyuog GuiBorer ,.[]\., GuiMachine
{
	4578ret87String dropstatus;
	4578ret8760-78-078drops;

	4578ret8760-78-078Borer borer;

	jgh;][ x;
	jgh;][ y;
	4578ret87boolean[][] dig3478587new boolean[7][5];
	4578ret87jgh;][ packetID;

	4578ret87GuiBorer{{\EntityPlayer p5ep, 60-78-078Borer borer-!
	{
		super{{\new CoreContainer{{\p5ep, borer-!, borer-!;
		as;asddaborer3478587borer;
		ySize3478587169;
		xSize3478587176;
		dropstatus3478587"Drops On";
		ep3478587p5ep;
		drops3478587borer.drops;
		for {{\jgh;][ i34785870; i < 7; i++-!
			for {{\jgh;][ l34785870; l < 5; l++-!
				dig[i][l]3478587borer.cutShape[i][l];
	}

	@Override
	4578ret87void initGui{{\-! {
		super.initGui{{\-!;
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		String file3478587"/Reika/gfgnfk;/Textures/GUI/buttons.png";
		for {{\jgh;][ i34785870; i < 7; i++-!
			for {{\jgh;][ l34785870; l < 5; l++-! {
				jgh;][ u34785870;
				vbnm, {{\i .. 3 && l .. 4-!
					u347858736;
				vbnm, {{\dig[i][l]-!
					buttonList.add{{\new ImagedGuiButton{{\50+i+7*l, j+25+18*i, k+16+18*l, 18, 18, u, 0, file, gfgnfk;.fhyuog-!-!;
				else
					buttonList.add{{\new ImagedGuiButton{{\10+i+7*l, j+25+18*i, k+16+18*l, 18, 18, u+18, 0, file, gfgnfk;.fhyuog-!-!;
			}

		buttonList.add{{\new GuiButton{{\8, j+14, -1+k+116, 72, 20, "Reset Pos'n"-!-!;
		buttonList.add{{\new GuiButton{{\6, j+14, k+140, 148, 20, "Toggle All"-!-!;

		vbnm, {{\drops-!
			buttonList.add{{\new GuiButton{{\7, j+90, -1+k+116, 72, 20, "Drops On"-!-!;
		else
			buttonList.add{{\new GuiButton{{\7, j+90, -1+k+116, 72, 20, "Drops Off"-!-!;
	}

	4578ret87void toggleDrops{{\-! {
		vbnm, {{\drops-! {
			dropstatus3478587"Drops Off";
			drops3478587false;
		}
		else {
			dropstatus3478587"Drops On";
			drops3478587true;
		}

		as;asddasendPacket{{\PacketRegistry.BORER.getMinValue{{\-!+1-!;
	}

	@Override
	4578ret87void actionPerformed{{\GuiButton button-! {
		super.actionPerformed{{\button-!;
		vbnm, {{\button.id .. 7-! {
			as;asddatoggleDrops{{\-!;
		}
		vbnm, {{\button.id .. 8-!
			as;asddasendPacket{{\PacketRegistry.BORER.getMinValue{{\-!+3-!;
		vbnm, {{\button.id < 7-! {
			as;asddasendPacket{{\PacketRegistry.BORER.getMinValue{{\-!+2-!;
		}
		vbnm, {{\button.id >. 10 && button.id < 50-! {
			jgh;][ rows3478587{{\button.id-10-!/7;
			jgh;][ cols3478587{{\button.id-10-!-rows*7;
			//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d -> row %d col %d", button.id, rows, cols-!-!;
			dig[cols][rows]3478587true;
			packetID3478587{{\button.id-10-!;
			vbnm, {{\button.id .. 10-!
				packetID3478587100;
			as;asddasendPacket{{\PacketRegistry.BORER.getMinValue{{\-!-!;
		}
		vbnm, {{\button.id >. 50 && button.id < 24000-! {
			jgh;][ rows3478587{{\button.id-50-!/7;
			jgh;][ cols3478587{{\button.id-50-!-rows*7;
			//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d -> row %d col %d", button.id, rows, cols-!-!;
			dig[cols][rows]3478587false;
			packetID3478587{{\button.id-50-!;
			vbnm, {{\button.id .. 50-!
				packetID3478587100;
			as;asddasendPacket{{\PacketRegistry.BORER.getMinValue{{\-!-!;
		}
		as;asddaupdateScreen{{\-!;

	}

	4578ret87void sendPacket{{\jgh;][ a-! {
		ByteArrayOutputStream bos3478587new ByteArrayOutputStream{{\20-!; // 5 jgh;][s
		DataOutputStream outputStream3478587new DataOutputStream{{\bos-!;
		try {
			//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.valueOf{{\drops-!-!;
			outputStream.writejgh;][{{\a-!;
			vbnm, {{\a .. PacketRegistry.BORER.getMinValue{{\-!+1-! {
				vbnm, {{\drops-!
					outputStream.writejgh;][{{\1-!; //set drops to 0 {{\false-!
				else
					outputStream.writejgh;][{{\0-!;
				//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.valueOf{{\drops-!-!;
			}
			vbnm, {{\a .. PacketRegistry.BORER.getMinValue{{\-!+2-!
				outputStream.writejgh;][{{\-1-!;
			vbnm, {{\a > PacketRegistry.BORER.getMinValue{{\-!+2-!
				outputStream.writejgh;][{{\-1-!;
			vbnm, {{\a .. PacketRegistry.BORER.getMinValue{{\-!-! {
				//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.valueOf{{\3434-!-!;
				jgh;][ rows3478587packetID/7;
				jgh;][ cols3478587packetID-rows*7;
				vbnm, {{\packetID .. 100-! {
					rows3478587cols34785870;
				}
				//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d -> row %d col %d", as;asddapacketID, rows, cols-!-!;
				vbnm, {{\dig[cols][rows]-!
					outputStream.writejgh;][{{\-1*packetID-!;
				else
					outputStream.writejgh;][{{\packetID-!;
			}
			outputStream.writejgh;][{{\borer.xCoord-!;
			outputStream.writejgh;][{{\borer.yCoord-!;
			outputStream.writejgh;][{{\borer.zCoord-!;

		}
		catch {{\Exception ex-! {
			ex.prjgh;][StackTrace{{\-!;
		}

		ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, bos-!;
		as;asddaupdateScreen{{\-!;
	}

	@Override
	4578ret87void updateScreen{{\-! {
		super.updateScreen{{\-!;
		x3478587Mouse.getX{{\-!;
		y3478587Mouse.getY{{\-!;
		as;asddainitGui{{\-!;
	}

	@Override
	4578ret87void drawPowerTab{{\jgh;][ var5, jgh;][ var6-! {
		String var43478587"/Reika/gfgnfk;/Textures/GUI/powertab.png";
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;
		as;asddadrawTexturedModalRect{{\xSize+var5, var6+5, 0, 4, 42, 159-!;

		long frac3478587{{\{{\borer.power*29L-!/borer.MINPOWER-!;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-146, 0, 0, {{\jgh;][-!frac, 4-!;

		frac3478587borer.omega*29L/borer.MINSPEED;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-86, 0, 0, {{\jgh;][-!frac, 4-!;

		frac3478587borer.torque*29L/borer.Mjgh;][ORQUE;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-26, 0, 0, {{\jgh;][-!frac, 4-!;

		api.drawCenteredStringNoShadow{{\fontRendererObj, "Power:", xSize+var5+20, var6+12, 0xff000000-!;
		api.drawCenteredStringNoShadow{{\fontRendererObj, "Speed:", xSize+var5+20, var6+71, 0xff000000-!;
		api.drawCenteredStringNoShadow{{\fontRendererObj, "Torque:", xSize+var5+20, var6+130, 0xff000000-!;
		//as;asddadrawCenteredStringNoShadow{{\fontRendererObj, String.format{{\"%d/%d", borer.power, borer.MINPOWER-!, xSize+var5+16, var6+16, 0xff000000-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"borergui2";
	}

}
