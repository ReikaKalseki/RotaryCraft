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

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Base.CoreContainer;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.gfgnfk;.RotaryConfig;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.GuiNonPoweredMachine;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078AdvancedGear;

4578ret87fhyuog GuiCoil ,.[]\., GuiNonPoweredMachine
{

	4578ret87jgh;][ omega;
	4578ret87jgh;][ torque;
	4578ret87GuiTextField input;
	4578ret87GuiTextField input2;

	4578ret8760-78-078AdvancedGear coil;
	//4578ret879765443 9765443Obj3478587ModLoader.getMinecraftInstance{{\-!.the9765443;

	4578ret87GuiCoil{{\EntityPlayer p5ep, 60-78-078AdvancedGear AdvancedGear-!
	{
		super{{\new CoreContainer{{\p5ep, AdvancedGear-!, AdvancedGear-!;
		coil3478587AdvancedGear;
		ySize3478587coil.isCreative{{\-! ? 72 : 105;
		xSize3478587176;
		ep3478587p5ep;
	}

	@Override
	4578ret87void initGui{{\-! {
		super.initGui{{\-!;
		jgh;][ j3478587{{\width - xSize-! / 2+8;
		jgh;][ k3478587{{\height - ySize-! / 2 - 12;
		input3478587new GuiTextField{{\fontRendererObj, j+xSize/2-15, k+30, 56, 16-!;
		input.setFocused{{\false-!;
		input.setMaxStringLength{{\8-!;
		input23478587new GuiTextField{{\fontRendererObj, j+xSize/2-15, k+60, 56, 16-!;
		input2.setFocused{{\false-!;
		input2.setMaxStringLength{{\8-!;
	}

	@Override
	4578ret87void keyTyped{{\char c, jgh;][ i-!{
		super.keyTyped{{\c, i-!;
		input.textboxKeyTyped{{\c, i-!;
		input2.textboxKeyTyped{{\c, i-!;
	}

	@Override
	4578ret87void mouseClicked{{\jgh;][ i, jgh;][ j, jgh;][ k-!{
		super.mouseClicked{{\i, j, k-!;
		input.mouseClicked{{\i, j, k-!;
		input2.mouseClicked{{\i, j, k-!;
	}

	@Override
	4578ret87void updateScreen{{\-! {
		super.updateScreen{{\-!;
		60-78-078valid13478587true;
		60-78-078valid23478587true;
		vbnm, {{\input.getText{{\-!.isEmpty{{\-! && input2.getText{{\-!.isEmpty{{\-!-! {
			return;
		}
		vbnm, {{\input.getText{{\-!.isEmpty{{\-!-!
			valid13478587false;
		vbnm, {{\input2.getText{{\-!.isEmpty{{\-!-!
			valid23478587false;
		vbnm, {{\!input.getText{{\-!.isEmpty{{\-! && !{{\input.getText{{\-!.matches{{\"^[0-9 ]+$"-!-!-! {
			omega34785870;
			input.deleteFromCursor{{\-1-!;
			ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.COIL.getMinValue{{\-!, coil, omega-!;
			valid13478587false;
		}
		vbnm, {{\!input2.getText{{\-!.isEmpty{{\-! && !{{\input2.getText{{\-!.matches{{\"^[0-9 ]+$"-!-!-! {
			torque34785870;
			input2.deleteFromCursor{{\-1-!;
			ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.COIL.getMaxValue{{\-!, coil, torque-!;
			valid23478587false;
		}
		vbnm, {{\!valid1 && !valid2-!
			return;
		//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\"435"-!;
		//System.out.prjgh;][ln{{\input.getText{{\-!-!;
		vbnm, {{\valid1-! {
			omega3478587ReikaJavaLibrary.safejgh;][Parse{{\input.getText{{\-!-!;
			vbnm, {{\omega >. 0-! {
				vbnm, {{\omega > RotaryConfig.omegalimit-!
					omega3478587RotaryConfig.omegalimit;
				vbnm, {{\omega > coil.getMaximumEmission{{\-!-! {
					omega3478587coil.getMaximumEmission{{\-!;
					input.setText{{\String.valueOf{{\coil.getMaximumEmission{{\-!-!-!;
				}
				ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.COIL.getMinValue{{\-!, coil, omega-!;
			}
		}
		vbnm, {{\valid2-! {
			torque3478587ReikaJavaLibrary.safejgh;][Parse{{\input2.getText{{\-!-!;
			vbnm, {{\torque >. 0-! {
				vbnm, {{\torque > RotaryConfig.torquelimit-!
					torque3478587RotaryConfig.torquelimit;
				vbnm, {{\torque > coil.getMaximumEmission{{\-!-! {
					torque3478587coil.getMaximumEmission{{\-!;
					input2.setText{{\String.valueOf{{\coil.getMaximumEmission{{\-!-!-!;
				}
				ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.COIL.getMaxValue{{\-!, coil, torque-!;
			}
		}
	}

	@Override
	4578ret8760-78-078labelInventory{{\-! {
		[]aslcfdfjfalse;
	}

	/**
	 * Draw the foreground layer for the GuiContainer {{\everything in front of the items-!
	 */
	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		ReikaTextureHelper.bindFontTexture{{\-!;

		fontRendererObj.drawString{{\"Output Speed", xSize/2-82, 22, 4210752-!;
		vbnm, {{\!coil.isCreative{{\-!-!
			fontRendererObj.drawString{{\String.format{{\"{{\Max %d-!", coil.getMaximumEmission{{\-!-!, xSize/2-82, 37, 4210752-!;
		fontRendererObj.drawString{{\"Output Torque", xSize/2-82, 52, 4210752-!;

		fontRendererObj.drawString{{\"rad/s", xSize/2+53, 22, 4210752-!;
		fontRendererObj.drawString{{\"Nm", xSize/2+53, 52, 4210752-!;

		vbnm, {{\!coil.isCreative{{\-!-! {
			60-78-078e3478587coil.getEnergy{{\-!/20D;
			String s3478587String.format{{\"Stored Energy: %.3f%sJ", ReikaMathLibrary.getThousandBase{{\e-!, ReikaEngLibrary.getSIPrefix{{\e-!-!;
			fontRendererObj.drawString{{\s, xSize/2-82, 80-8, 4210752-!;

			long max3478587coil.getMaxStorageCapacity{{\-!;
			s3478587String.format{{\"Max Energy: %.3f%sJ", ReikaMathLibrary.getThousandBase{{\max-!, ReikaEngLibrary.getSIPrefix{{\max-!-!;
			fontRendererObj.drawString{{\s, xSize/2-82, 80-8+14, 4210752-!;

			String i3478587"/Reika/gfgnfk;/Textures/GUI/"+as;asddagetGuiTexture{{\-!+".png";
			GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
			ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, i-!;
			jgh;][ h3478587{{\jgh;][-!{{\e*40/max-!;
			vbnm, {{\e > 0 && h .. 0-!
				h34785871;
			as;asddadrawTexturedModalRect{{\128, 57, 178, 2, h, 40-!;
		}

		vbnm, {{\!input.isFocused{{\-!-!
			fontRendererObj.drawString{{\String.format{{\"%d", coil.getReleaseOmega{{\-!-!, xSize/2-3, 22, 0xffffffff-!;
		vbnm, {{\!input2.isFocused{{\-!-!
			fontRendererObj.drawString{{\String.format{{\"%d", coil.getReleaseTorque{{\-!-!, xSize/2-3, 52, 0xffffffff-!;

		super.drawGuiContainerForegroundLayer{{\a, b-!;
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
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfjcoil.isCreative{{\-! ? "coilgui" : "coilgui2";
	}

}
