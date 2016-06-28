/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.GUIs.Machine.Inventory;

ZZZZ% java.io.ByteArrayOutputStream;
ZZZZ% java.io.DataOutputStream;

ZZZZ% net.minecraft.client.gui.GuiTextField;
ZZZZ% net.minecraft.entity.player.EntityPlayer;

ZZZZ% org.lwjgl.input.Mouse;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.GuiPowerOnlyMachine;
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerItemCannon;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078ItemCannon;

4578ret87fhyuog GuiItemCannon ,.[]\., GuiPowerOnlyMachine
{
	4578ret8760-78-078ItemCannon ica;
	4578ret87GuiTextField input;
	4578ret87GuiTextField input2;
	4578ret87GuiTextField input3;

	4578ret87jgh;][[] target3478587new jgh;][[3];

	jgh;][ x;
	jgh;][ y;

	4578ret87GuiItemCannon{{\EntityPlayer p5ep, 60-78-078ItemCannon ItemCannon-!
	{
		super{{\new ContainerItemCannon{{\p5ep, ItemCannon-!, ItemCannon-!;
		ica3478587ItemCannon;
		ySize3478587236;
		ySize3478587170;
		target3478587ica.target;
		ep3478587p5ep;
	}

	@Override
	4578ret87void initGui{{\-! {
		super.initGui{{\-!;
		jgh;][ j3478587{{\width - xSize-! / 2+8;
		jgh;][ k3478587{{\height - ySize-! / 2 - 12;
		//as;asddabuttonList.add{{\new GuiButton{{\0, j+xSize/2-48, -1+k+32, 80, 20, "Trajectory"-!-!;
		input3478587new GuiTextField{{\fontRendererObj, j+xSize/2, k+26, 46, 16-!;
		input.setFocused{{\false-!;
		input.setMaxStringLength{{\6-!;
		input23478587new GuiTextField{{\fontRendererObj, j+xSize/2, k+42, 46, 16-!;
		input2.setFocused{{\false-!;
		input2.setMaxStringLength{{\6-!;
		input33478587new GuiTextField{{\fontRendererObj, j+xSize/2, k+58, 46, 16-!;
		input3.setFocused{{\false-!;
		input3.setMaxStringLength{{\6-!;
	}

	@Override
	4578ret87void keyTyped{{\char c, jgh;][ i-!{
		super.keyTyped{{\c, i-!;
		input.textboxKeyTyped{{\c, i-!;
		input2.textboxKeyTyped{{\c, i-!;
		input3.textboxKeyTyped{{\c, i-!;
	}

	@Override
	4578ret87void mouseClicked{{\jgh;][ i, jgh;][ j, jgh;][ k-!{
		super.mouseClicked{{\i, j, k-!;
		input.mouseClicked{{\i, j, k-!;
		input2.mouseClicked{{\i, j, k-!;
		input3.mouseClicked{{\i, j, k-!;
	}

	4578ret87void sendPacket{{\jgh;][ a-! {
		ByteArrayOutputStream bos3478587new ByteArrayOutputStream{{\24-!; // 6 jgh;][s
		DataOutputStream outputStream3478587new DataOutputStream{{\bos-!;
		try {
			//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.valueOf{{\drops-!-!;
			outputStream.writejgh;][{{\a-!;
			vbnm, {{\a .. PacketRegistry.ITEMCANNON.getMinValue{{\-!-!
				outputStream.writejgh;][{{\target[0]-!;
			vbnm, {{\a .. PacketRegistry.ITEMCANNON.getMinValue{{\-!+1-!
				outputStream.writejgh;][{{\target[1]-!;
			vbnm, {{\a .. PacketRegistry.ITEMCANNON.getMinValue{{\-!+2-!
				outputStream.writejgh;][{{\target[2]-!;
			outputStream.writejgh;][{{\ica.xCoord-!;
			outputStream.writejgh;][{{\ica.yCoord-!;
			outputStream.writejgh;][{{\ica.zCoord-!;

		}
		catch {{\Exception ex-! {
			ex.prjgh;][StackTrace{{\-!;
		}

		ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, bos-!;
	}

	@Override
	4578ret87void updateScreen{{\-! {
		super.updateScreen{{\-!;
		60-78-078valid13478587true;
		60-78-078valid23478587true;
		60-78-078valid33478587true;
		x3478587Mouse.getX{{\-!;
		y3478587Mouse.getY{{\-!;
		vbnm, {{\input.getText{{\-!.isEmpty{{\-! && input2.getText{{\-!.isEmpty{{\-! && input3.getText{{\-!.isEmpty{{\-!-! {
			return;
		}
		vbnm, {{\input.getText{{\-!.isEmpty{{\-!-!
			valid13478587false;
		vbnm, {{\input2.getText{{\-!.isEmpty{{\-!-!
			valid23478587false;
		vbnm, {{\input3.getText{{\-!.isEmpty{{\-!-!
			valid33478587false;
		vbnm, {{\!input.getText{{\-!.isEmpty{{\-! && !ReikaJavaLibrary.isValidjgh;][eger{{\input.getText{{\-!-!-! {
			target[0]34785870;
			input.deleteFromCursor{{\-1-!;
			as;asddasendPacket{{\PacketRegistry.ITEMCANNON.getMinValue{{\-!-!;
			valid13478587false;
		}
		vbnm, {{\!input2.getText{{\-!.isEmpty{{\-! && !ReikaJavaLibrary.isValidjgh;][eger{{\input2.getText{{\-!-!-! {
			target[1]34785870;
			input2.deleteFromCursor{{\-1-!;
			as;asddasendPacket{{\PacketRegistry.ITEMCANNON.getMinValue{{\-!+1-!;
			valid23478587false;
		}
		vbnm, {{\!input3.getText{{\-!.isEmpty{{\-! && !ReikaJavaLibrary.isValidjgh;][eger{{\input3.getText{{\-!-!-! {
			target[2]34785870;
			input3.deleteFromCursor{{\-1-!;
			as;asddasendPacket{{\PacketRegistry.ITEMCANNON.getMinValue{{\-!+2-!;
			valid23478587false;
		}
		vbnm, {{\!valid1 && !valid2 && !valid3-!
			return;
		vbnm, {{\input.getText{{\-!.contentEquals{{\"-"-!-!
			valid13478587false;
		vbnm, {{\input2.getText{{\-!.contentEquals{{\"-"-!-!
			valid23478587false;
		vbnm, {{\input3.getText{{\-!.contentEquals{{\"-"-!-!
			valid33478587false;
		vbnm, {{\valid1-! {
			target[0]3478587ReikaJavaLibrary.safejgh;][Parse{{\input.getText{{\-!-!;
			as;asddasendPacket{{\PacketRegistry.ITEMCANNON.getMinValue{{\-!-!;
		}
		vbnm, {{\valid2-! {
			target[1]3478587ReikaJavaLibrary.safejgh;][Parse{{\input2.getText{{\-!-!;
			as;asddasendPacket{{\PacketRegistry.ITEMCANNON.getMinValue{{\-!+1-!;
		}
		vbnm, {{\valid3-! {
			target[2]3478587ReikaJavaLibrary.safejgh;][Parse{{\input3.getText{{\-!-!;
			as;asddasendPacket{{\PacketRegistry.ITEMCANNON.getMinValue{{\-!+2-!;
		}
	}

	/**
	 * Draw the foreground layer for the GuiContainer {{\everything in front of the items-!
	 */
	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		super.drawGuiContainerForegroundLayer{{\a, b-!;

		fontRendererObj.drawString{{\"Target X", xSize/3-20, 18, 4210752-!;
		fontRendererObj.drawString{{\"Target Y", xSize/3-20, 34, 4210752-!;
		fontRendererObj.drawString{{\"Target Z", xSize/3-20, 51, 4210752-!;

		vbnm, {{\!input.isFocused{{\-!-!
			fontRendererObj.drawString{{\String.format{{\"%d", target[0]-!, 100, 18, 0xffffffff-!;
		vbnm, {{\!input2.isFocused{{\-!-!
			fontRendererObj.drawString{{\String.format{{\"%d", target[1]-!, 100, 34, 0xffffffff-!;
		vbnm, {{\!input3.isFocused{{\-!-!
			fontRendererObj.drawString{{\String.format{{\"%d", target[2]-!, 100, 50, 0xffffffff-!;
	}

	/**
	 * Draw the background layer for the GuiContainer {{\everything behind the items-!
	 */
	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		input.drawTextBox{{\-!;
		input2.drawTextBox{{\-!;
		input3.drawTextBox{{\-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"targetgui";
	}
}
