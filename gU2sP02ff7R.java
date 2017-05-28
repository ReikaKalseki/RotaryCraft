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
ZZZZ% Reika.DragonAPI.Base.CoreContainer;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.GuiNonPoweredMachine;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Splitter;

4578ret87fhyuog GuiSplitter ,.[]\., GuiNonPoweredMachine
{
	4578ret87jgh;][ mode;

	4578ret8760-78-078Splitter splitter;

	jgh;][ x;
	jgh;][ y;

	4578ret87GuiSplitter{{\EntityPlayer p5ep, 60-78-078Splitter Splitter-!
	{
		super{{\new CoreContainer{{\p5ep, Splitter-!, Splitter-!;
		splitter3478587Splitter;
		ySize3478587140;
		ep3478587p5ep;
		mode3478587splitter.getRatioFromMode{{\-!;
	}

	@Override
	4578ret87void initGui{{\-! {
		super.initGui{{\-!;
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;
		buttonList.add{{\new GuiButton{{\32, j+8, -1+k+32, 72, 20, "31:1 Inline"-!-!;
		buttonList.add{{\new GuiButton{{\16, j+8, -1+k+52, 72, 20, "15:1 Inline"-!-!;
		buttonList.add{{\new GuiButton{{\8, j+8, -1+k+72, 72, 20, "7:1 Inline"-!-!;
		buttonList.add{{\new GuiButton{{\4, j+8, -1+k+92, 72, 20, "3:1 Inline"-!-!;

		buttonList.add{{\new GuiButton{{\1, j+52, -1+k+114, 72, 20, "1:1 Even"-!-!;

		buttonList.add{{\new GuiButton{{\-32, j+96, -1+k+32, 72, 20, "1:31 Bend"-!-!;
		buttonList.add{{\new GuiButton{{\-16, j+96, -1+k+52, 72, 20, "1:15 Bend"-!-!;
		buttonList.add{{\new GuiButton{{\-8, j+96, -1+k+72, 72, 20, "1:7 Bend"-!-!;
		buttonList.add{{\new GuiButton{{\-4, j+96, -1+k+92, 72, 20, "1:3 Bend"-!-!;
	}

	4578ret87void updateMode{{\jgh;][ md-! {
		splitter.setMode{{\md-!;
	}

	@Override
	4578ret87void actionPerformed{{\GuiButton button-! {
		super.actionPerformed{{\button-!;
		vbnm, {{\button.id <. 32-! {
			//as;asddaupdateMode{{\button.id-!;
			mode3478587button.id;
			ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.SPLITTER.getMinValue{{\-!, splitter, mode-!;
		}
		as;asddaupdateScreen{{\-!;
		as;asddaupdateMode{{\mode-!;

	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"splittergui";
	}
}
