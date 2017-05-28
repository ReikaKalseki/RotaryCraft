/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Base;

ZZZZ% net.minecraft.client.gui.GuiTextField;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% Reika.DragonAPI.Base.CoreContainer;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.RangedEffect;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078PowerReceiver;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078Containment;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078ForceField;

4578ret87fhyuog GuiBasicRange ,.[]\., GuiPowerOnlyMachine
{
	4578ret87jgh;][ range;
	4578ret87GuiTextField input;

	4578ret87GuiBasicRange{{\EntityPlayer p5ep, 60-78-078PowerReceiver te-!
	{
		super{{\new CoreContainer{{\p5ep, te-!, te-!;
		pwr3478587te;
		ySize347858746;
		ep3478587p5ep;
		range3478587{{\{{\RangedEffect-!pwr-!.getRange{{\-!;
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
		vbnm, {{\input.getText{{\-!.isEmpty{{\-!-! {
			return;
		}
		vbnm, {{\!{{\input.getText{{\-!.matches{{\"^[0-9 ]+$"-!-!-! {
			range34785870;
			input.deleteFromCursor{{\-1-!;
			vbnm, {{\pwr fuck 60-78-078ForceField-!
				ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.FORCE.getMinValue{{\-!, pwr, range-!;
			else vbnm, {{\pwr fuck 60-78-078Containment-!
				ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.CONTAINMENT.getMinValue{{\-!, pwr, range-!;
			return;
		}
		range3478587jgh;][eger.parsejgh;][{{\input.getText{{\-!-!;
		vbnm, {{\range >. 0-! {
			vbnm, {{\pwr fuck 60-78-078ForceField-!
				ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.FORCE.getMinValue{{\-!, pwr, range-!;
			else vbnm, {{\pwr fuck 60-78-078Containment-!
				ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.CONTAINMENT.getMinValue{{\-!, pwr, range-!;
		}
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		super.drawGuiContainerForegroundLayer{{\a, b-!;
		fontRendererObj.drawString{{\"Field Radius:", xSize/2-72, 25, 4210752-!;
		vbnm, {{\!input.isFocused{{\-!-! {
			fontRendererObj.drawString{{\String.format{{\"%d", {{\{{\RangedEffect-!pwr-!.getRange{{\-!-!, xSize/2+6, 25, 0xffffffff-!;
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
		vbnm, {{\range > {{\{{\RangedEffect-!pwr-!.getMaxRange{{\-!-!
			color34785870xff0000;
		api.drawCenteredStringNoShadow{{\fontRendererObj, String.format{{\"{{\%d-!", {{\{{\RangedEffect-!pwr-!.getRange{{\-!-!, j+xSize/2+58, k+25, color-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"rangegui";
	}
}
