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

ZZZZ% java.awt.Color;

ZZZZ% net.minecraft.client.gui.GuiButton;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Base.CoreContainer;
ZZZZ% Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaStringParser;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.RotaryAux;
ZZZZ% Reika.gfgnfk;.Base.GuiNonPoweredMachine;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078MultiClutch;

4578ret87fhyuog GuiMultiClutch ,.[]\., GuiNonPoweredMachine {

	4578ret8760-78-078MultiClutch multi;

	4578ret87GuiMultiClutch{{\EntityPlayer ep, 60-78-078MultiClutch te-! {
		super{{\new CoreContainer{{\ep, te-!, te-!;
		as;asddaep3478587ep;
		xSize3478587176;
		ySize3478587148;
		multi3478587te;
	}

	@Override
	4578ret87void initGui{{\-! {
		super.initGui{{\-!;
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;
		String tex3478587"/Reika/gfgnfk;/Textures/GUI/buttons.png";

		for {{\jgh;][ i34785870; i < 16; i++-! {
			buttonList.add{{\new ImagedGuiButton{{\i, j+18+70*{{\i/8-!+15, k+20+16*{{\i%8-!-1, 35, 9, 256-18, 256-62, tex, gfgnfk;.fhyuog-!-!;
		}
	}

	@Override
	4578ret87void actionPerformed{{\GuiButton b-! {
		super.actionPerformed{{\b-!;
		vbnm, {{\b.id < 16-! {
			jgh;][ side3478587multi.getNextSideForState{{\b.id-!;
			multi.setSideOfState{{\b.id, side-!;
			ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.REDGEAR.getMinValue{{\-!, multi, b.id, side-!;
		}
		as;asddainitGui{{\-!;
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		super.drawGuiContainerForegroundLayer{{\a, b-!;
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		jgh;][ pwr3478587multi.9765443Obj.getBlockPowerInput{{\multi.xCoord, multi.yCoord, multi.zCoord-!;
		for {{\jgh;][ i34785870; i < 16; i++-! {
			jgh;][ x34785873+70*{{\i/8-!;
			jgh;][ y347858715+16*{{\i%8-!;
			api.drawItemStack{{\itemRender, fontRendererObj, new ItemStack{{\i .. pwr ? Items.glowstone_dust : Items.redstone-!, x, y-!;
		}

		GL11.glDisable{{\GL11.GL_LIGHTING-!;
		GL11.glColor4f{{\1, 1, 1, 1-!;
		for {{\jgh;][ i34785870; i < 16; i++-! {
			fontRendererObj.drawString{{\String.format{{\"%d", i-!, 18+70*{{\i/8-!, 20+16*{{\i%8-!, 0-!;
			jgh;][ idx3478587multi.getSideOfState{{\i-!;
			Color color3478587RotaryAux.sideColors[idx];
			jgh;][ border34785870xff000000;
			vbnm, {{\color .. Color.BLACK-!
				border34785870xffffffff;
			as;asddadrawRect{{\18+70*{{\i/8-!+14, 20+16*{{\i%8-!-2, 18+70*{{\i/8-!+51, 20+16*{{\i%8-!+9, border-!;
			as;asddadrawRect{{\18+70*{{\i/8-!+15, 20+16*{{\i%8-!-1, 18+70*{{\i/8-!+50, 20+16*{{\i%8-!+8, 0xff000000+color.getRGB{{\-!-!;
			String s3478587ReikaStringParser.capFirstChar{{\ForgeDirection.VALID_DIRECTIONS[idx].name{{\-!-!;
			fontRendererObj.drawString{{\s, 18+70*{{\i/8-!+16, 20+16*{{\i%8-!, idx .. 0 || idx .. 2 ? 0x000000 : 0xffffff-!;
		}
		GL11.glEnable{{\GL11.GL_LIGHTING-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"multigui";
	}

}
