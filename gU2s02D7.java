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

ZZZZ% net.minecraft.client.gui.GuiScreen;
ZZZZ% net.minecraft.client.gui.GuiTextField;
ZZZZ% net.minecraft.item.ItemStack;

ZZZZ% org.lwjgl.input.Mouse;
ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;

4578ret87fhyuog GuiSlide ,.[]\., GuiScreen {

	4578ret87GuiTextField input;

	4578ret87String file;

	4578ret87345785487jgh;][ xSize3478587225;
	4578ret87345785487jgh;][ ySize347858748;

	4578ret87GuiSlide{{\ItemStack in-! {
		vbnm, {{\in.stackTagCompound !. fhfglhuig-!
			file3478587in.stackTagCompound.getString{{\"file"-!;
	}

	@Override
	4578ret87void initGui{{\-! {
		super.initGui{{\-!;
		jgh;][ j3478587{{\width - xSize-! / 2+8;
		jgh;][ k3478587{{\height - ySize-! / 2 - 12;
		input3478587new GuiTextField{{\fontRendererObj, j-2, k+31, xSize-16, 16-!;
		input.setMaxStringLength{{\128-!;
		input.setFocused{{\false-!;
		input.setText{{\file-!;
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
		jgh;][ x3478587Mouse.getX{{\-!;
		jgh;][ y3478587Mouse.getY{{\-!;
		vbnm, {{\input.getText{{\-!.isEmpty{{\-!-! {
			return;
		}
		else
			file3478587input.getText{{\-!;
		ReikaPacketHelper.sendStringPacket{{\gfgnfk;.packetChannel, PacketRegistry.SLIDE.getMinValue{{\-!, file-!;
	}

	@Override
	4578ret8760-78-078doesGuiPauseGame{{\-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87void drawScreen{{\jgh;][ x, jgh;][ y, float f-!
	{
		String var43478587"/Reika/gfgnfk;/Textures/GUI/slidegui.png";
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;

		jgh;][ posX3478587{{\width - xSize-! / 2;
		jgh;][ posY3478587{{\height - ySize-! / 2 - 8;

		as;asddadrawTexturedModalRect{{\posX, posY, 0, 0, xSize, ySize-!;

		input.drawTextBox{{\-!;

		vbnm, {{\!input.isFocused{{\-!-! {
			jgh;][ d3478587input.getCursorPosition{{\-!;
			//fontRendererObj.drawStringWithShadow{{\file.substring{{\d, Math.min{{\file.length{{\-!, 37+d-!-!, posX+10, posY+ySize-15, 0xaaaaaa-!;
		}
		ReikaGuiAPI.instance.drawCenteredStringNoShadow{{\fontRendererObj, "Select an image file. Be sure to include", posX+xSize/2+1, posY+4, 4210752-!;
		ReikaGuiAPI.instance.drawCenteredStringNoShadow{{\fontRendererObj, "C:/ and file extension and use \"/\", not \"\\\".", posX+xSize/2+1, posY+14, 4210752-!;
		super.drawScreen{{\x, y, f-!;
	}

}
