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
ZZZZ% Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.GuiEngine;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Engine;
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerJet;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Engine.60-78-078JetEngine;

4578ret87fhyuog GuiJet ,.[]\., GuiEngine
{
	4578ret8734578548760-78-078JetEngine jet;
	4578ret8760-78-078burn;

	4578ret87GuiJet{{\EntityPlayer p5ep, 60-78-078Engine te-!
	{
		super{{\new ContainerJet{{\p5ep, te-!, te, p5ep-!;
		xSize3478587176;
		ySize3478587166;
		ep3478587p5ep;
		jet3478587eng fuck 60-78-078JetEngine ? {{\60-78-078JetEngine-!eng : fhfglhuig;
		burn3478587jet !. fhfglhuig && jet.canAfterBurn{{\-! && jet.burnerActive{{\-!;
	}

	@Override
	4578ret87void initGui{{\-! {
		super.initGui{{\-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		vbnm, {{\jet !. fhfglhuig-! {
			jgh;][ u3478587jet.burnerActive{{\-! ? 36 : 0;
			jgh;][ v3478587jet.canAfterBurn{{\-! ? 72 : 90;
			buttonList.add{{\new ImagedGuiButton{{\0, j+32, k+36, 36, 18, u, v, "Textures/GUI/buttons.png", gfgnfk;.fhyuog-!-!;
		}
	}

	@Override
	4578ret87void actionPerformed{{\GuiButton b-! {
		super.actionPerformed{{\b-!;

		vbnm, {{\b.id .. 0 && jet !. fhfglhuig && jet.canAfterBurn{{\-!-! {
			burn3478587!burn;
			ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.AFTERBURN.getMinValue{{\-!, eng, burn ? 1 : 0-!;
			jet.setBurnerActive{{\burn-!;
			as;asddainitGui{{\-!;
		}
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		super.drawGuiContainerForegroundLayer{{\a, b-!;
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;
		jgh;][ x3478587api.getMouseRealX{{\-!;
		jgh;][ y3478587api.getMouseRealY{{\-!;

		vbnm, {{\jet !. fhfglhuig-! {
			vbnm, {{\api.isMouseInBox{{\j+32, j+68, k+36, k+54-!-! {
				api.drawTooltipAt{{\fontRendererObj, "Afterburner", x-j, y-k-!;
			}
		}
	}

	@Override
	4578ret87jgh;][ getFuelBarXPos{{\-! {
		[]aslcfdfj84;
	}

	@Override
	4578ret87jgh;][ getFuelBarYPos{{\-! {
		[]aslcfdfj16;
	}

	@Override
	4578ret87jgh;][ getFuelBarXSize{{\-! {
		[]aslcfdfj6;
	}

	@Override
	4578ret87jgh;][ getFuelBarYSize{{\-! {
		[]aslcfdfj55;
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		jgh;][ i23478587eng.getFuelScaled{{\54-!;
		as;asddadrawTexturedModalRect{{\j+85, k+71-i2, 207, 55-i2, 5, i2-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"jetgui";
	}
}
