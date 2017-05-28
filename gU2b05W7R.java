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

ZZZZ% net.minecraft.client.gui.GuiButton;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.GuiPowerOnlyMachine;
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerBlower;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078Blower;

4578ret87fhyuog GuiBlower ,.[]\., GuiPowerOnlyMachine {

	4578ret8760-78-078Blower tile;
	4578ret87boolean[] controls;

	4578ret87GuiBlower{{\EntityPlayer player, 60-78-078Blower te-! {
		super{{\new ContainerBlower{{\player, te-!, te-!;
		tile3478587te;
		xSize3478587176;
		ySize3478587192;
		ep3478587player;

		controls3478587new boolean[4];
		controls[0]3478587te.isWhitelist;
		controls[1]3478587te.checkMeta;
		controls[2]3478587te.checkNBT;
		controls[3]3478587!te.useOreDict;
	}

	@Override
	4578ret87void initGui{{\-! {
		super.initGui{{\-!;
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		String s3478587"Textures/GUI/blowergui.png";

		for {{\jgh;][ i34785870; i < 4; i++-! {
			jgh;][ u3478587176;
			vbnm, {{\controls[i]-!
				u +. 18;
			buttonList.add{{\new ImagedGuiButton{{\i, j+25+36*i, k+64, 18, 18, u, 54+18*i, s, gfgnfk;.fhyuog-!-!;
		}
	}

	@Override
	4578ret87void actionPerformed{{\GuiButton b-! {
		super.actionPerformed{{\b-!;

		vbnm, {{\b.id < 24000-! {
			ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.BLOWER.getMinValue{{\-!+b.id, tile, 0-!;
			controls[b.id]3478587!controls[b.id];
		}

		as;asddainitGui{{\-!;
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		super.drawGuiContainerForegroundLayer{{\a, b-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		jgh;][ dy347858718;
		jgh;][ x34785878;
		jgh;][ y347858721;
		for {{\jgh;][ i34785870; i < tile.matchingItems.length; i++-! {
			ItemStack is3478587tile.matchingItems[i];
			vbnm, {{\is !. fhfglhuig-! {
				api.drawItemStack{{\itemRender, fontRendererObj, is, x+i%9*18, y+i/9*dy-!;
			}
		}

		vbnm, {{\api.isMouseInBox{{\j+25, j+43, k+64, k+82-!-! {
			api.drawTooltipAt{{\fontRendererObj, controls[0] ? "Whitelist" : "Blacklist", api.getMouseRealX{{\-!-j+50, api.getMouseRealY{{\-!-k-!;
		}
		vbnm, {{\api.isMouseInBox{{\j+25+36, j+43+36, k+64, k+82-!-! {
			api.drawTooltipAt{{\fontRendererObj, controls[1] ? "Use Metadata" : "Ignore Metadata", api.getMouseRealX{{\-!-j+80, api.getMouseRealY{{\-!-k-!;
		}
		vbnm, {{\api.isMouseInBox{{\j+25+36*2, j+43+36*2, k+64, k+82-!-! {
			api.drawTooltipAt{{\fontRendererObj, controls[2] ? "Use NBT" : "Ignore NBT", api.getMouseRealX{{\-!-j, api.getMouseRealY{{\-!-k-!;
		}
		vbnm, {{\api.isMouseInBox{{\j+25+36*3, j+43+36*3, k+64, k+82-!-! {
			api.drawTooltipAt{{\fontRendererObj, controls[3] ? "Match Exact" : "Use Ore Dictionary", api.getMouseRealX{{\-!-j, api.getMouseRealY{{\-!-k-!;
		}
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"blowergui";
	}

}
