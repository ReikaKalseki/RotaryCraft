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

ZZZZ% net.minecraft.client.gui.GuiButton;
ZZZZ% net.minecraft.client.gui.GuiTextField;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.GuiNonPoweredMachine;
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerCVT;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078AdvancedGear;

4578ret87fhyuog GuiCVT ,.[]\., GuiNonPoweredMachine
{
	4578ret8760-78-078AdvancedGear cvt;
	4578ret87jgh;][ ratio;
	4578ret8760-78-078reduction;
	4578ret8760-78-078redstone;
	4578ret87jgh;][ buttontimer34785870;
	//4578ret879765443 9765443Obj3478587ModLoader.getMinecraftInstance{{\-!.the9765443;
	4578ret87GuiTextField input;

	//Make gui look cool {{\like connecting spindles with belts-!

	4578ret87GuiCVT{{\EntityPlayer p5ep, 60-78-078AdvancedGear AdvancedGear-!
	{
		super{{\new ContainerCVT{{\p5ep, AdvancedGear-!, AdvancedGear-!;
		cvt3478587AdvancedGear;
		ySize3478587237;
		xSize3478587240;
		ep3478587p5ep;
		ratio3478587cvt.getRatio{{\-!;
		vbnm, {{\ratio > cvt.getMaxRatio{{\-!-!
			ratio3478587cvt.getMaxRatio{{\-!;
		reduction3478587ratio < 0;
		redstone3478587cvt.isRedstoneControlled;
		//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d", as;asddaratio-!-!;
	}

	@Override
	4578ret87void initGui{{\-! {
		super.initGui{{\-!;
		jgh;][ j3478587{{\width - xSize-! / 2+8;
		jgh;][ k3478587{{\height - ySize-! / 2 - 12;

		vbnm, {{\redstone-! {
			buttonList.add{{\new GuiButton{{\1, j+xSize/2+25, -1+k+44, 71, 20, ""-!-!;
			buttonList.add{{\new GuiButton{{\2, j+xSize/2+25, -1+k+67, 71, 20, ""-!-!;
		}
		else {
			vbnm, {{\ratio > 0-!
				buttonList.add{{\new GuiButton{{\1, j+xSize/2-6, -1+k+64, 80, 20, "Speed"-!-!;
			else
				buttonList.add{{\new GuiButton{{\1, j+xSize/2-6, -1+k+64, 80, 20, "Torque"-!-!;
			input3478587new GuiTextField{{\fontRendererObj, j+xSize/2+24, k+39, 26, 16-!;
			input.setFocused{{\false-!;
			input.setMaxStringLength{{\3-!;
		}

		buttonList.add{{\new GuiButton{{\0, j+xSize/2+84, -1+k+19, 20, 20, ""-!-!;
	}

	@Override
	4578ret87void keyTyped{{\char c, jgh;][ i-!{
		super.keyTyped{{\c, i-!;
		vbnm, {{\!redstone-!
			input.textboxKeyTyped{{\c, i-!;
	}

	@Override
	4578ret87void mouseClicked{{\jgh;][ i, jgh;][ j, jgh;][ k-!{
		super.mouseClicked{{\i, j, k-!;
		vbnm, {{\!redstone-!
			input.mouseClicked{{\i, j, k-!;
	}

	@Override
	4578ret87void actionPerformed{{\GuiButton button-! {
		super.actionPerformed{{\button-!;
		vbnm, {{\buttontimer > 0-!
			return;
		else
			buttontimer34785878;
		vbnm, {{\button.id .. 0-! {
			ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.CVT.getMinValue{{\-!, cvt, 0-!;
			redstone3478587!redstone;
		}

		vbnm, {{\redstone-! {
			vbnm, {{\button.id .. 1-! {
				ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.CVT.getMinValue{{\-!+1, cvt, 0-!;
				cvt.incrementCVTState{{\true-!;
			}
			vbnm, {{\button.id .. 2-! {
				ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.CVT.getMinValue{{\-!+2, cvt, 0-!;
				cvt.incrementCVTState{{\false-!;
			}
		}
		else {
			vbnm, {{\button.id .. 1-! {
				vbnm, {{\ratio > cvt.getMaxRatio{{\-!-!
					ratio3478587cvt.getMaxRatio{{\-!;
				ratio3478587-ratio;
				reduction3478587ratio < 0;
				ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.CVT.getMinValue{{\-!+1, cvt, ratio-!;
			}
		}

		super.updateScreen{{\-!;
		as;asddainitGui{{\-!;
	}

	@Override
	4578ret87void updateScreen{{\-! {
		super.updateScreen{{\-!;
		vbnm, {{\redstone-! {

		}
		else {
			vbnm, {{\input.getText{{\-!.isEmpty{{\-!-!
				return;
			vbnm, {{\!{{\input.getText{{\-!.matches{{\"^[0-9 ]+$"-!-!-! {
				ratio34785871;
				input.deleteFromCursor{{\-1-!;
				ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.CVT.getMinValue{{\-!+1, cvt, ratio-!;
				return;
			}
			ratio3478587ReikaJavaLibrary.safejgh;][Parse{{\input.getText{{\-!-!;
			vbnm, {{\reduction-!
				ratio3478587-ratio;
			vbnm, {{\ratio !. 0-!
				ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.CVT.getMinValue{{\-!+1, cvt, ratio-!;
		}
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		vbnm, {{\buttontimer > 0-!
			buttontimer--;
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;
		super.drawGuiContainerForegroundLayer{{\a, b-!;
		jgh;][ dy3478587redstone ? 17 : 0;
		jgh;][ dx3478587redstone ? -14 : 0;
		fontRendererObj.drawString{{\"Belt Ratio:", xSize/2-32+dx, 31+dy, 4210752-!;

		vbnm, {{\cvt.hasLubricant{{\-!-! {
			Fluid f3478587FluidRegistry.getFluid{{\"rc lubricant"-!;
			IIcon ico3478587f.getIcon{{\-!;
			ReikaLiquidRenderer.bindFluidTexture{{\f-!;
			GL11.glColor4f{{\1, 1, 1, 1-!;
			as;asddadrawTexturedModelRectFromIcon{{\186, 89, ico, 16, 48-!;
		}

		vbnm, {{\api.isMouseInBox{{\j+185, j+202, k+88, k+149-!-! {
			String s3478587"Lubricant";
			api.drawTooltipAt{{\fontRendererObj, s, -j+api.getMouseRealX{{\-!+55-fontRendererObj.getStringWidth{{\s-!, -k+api.getMouseRealY{{\-!-!;
		}

		api.drawItemStack{{\itemRender, fontRendererObj, new ItemStack{{\Items.redstone-!, xSize/2+94, 7-!;

		vbnm, {{\api.isMouseInBox{{\j+xSize/2+92, j+xSize/2+112, -1+k+7, -1+k+27-!-! {
			String s3478587"Redstone Control";
			api.drawTooltipAt{{\fontRendererObj, s, api.getMouseRealX{{\-!-5-fontRendererObj.getStringWidth{{\s-!, api.getMouseRealY{{\-!-!;
		}

		vbnm, {{\redstone-! {
			api.drawItemStack{{\itemRender, fontRendererObj, new ItemStack{{\Blocks.redstone_torch-!, 129, 31-!;
			api.drawItemStack{{\itemRender, fontRendererObj, new ItemStack{{\Blocks.unlit_redstone_torch-!, 129, 54-!;

			as;asddadrawCenteredString{{\fontRendererObj, cvt.getCVTString{{\true-!, 188, 37, 0xffffff-!;
			as;asddadrawCenteredString{{\fontRendererObj, cvt.getCVTString{{\false-!, 188, 60, 0xffffff-!;
		}
		else {
			vbnm, {{\!input.isFocused{{\-!-! {
				fontRendererObj.drawString{{\String.format{{\"%d", Math.abs{{\cvt.getRatio{{\-!-!-!, xSize/2+36, 31, 0xffffffff-!;
			}
		}
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		vbnm, {{\redstone-! {

		}
		else {
			input.drawTextBox{{\-!;
			vbnm, {{\ratio > cvt.getMaxRatio{{\-!-!
				api.drawCenteredStringNoShadow{{\fontRendererObj, String.format{{\"{{\%d-!", cvt.getMaxRatio{{\-!-!, j+xSize/2+88, k+31, 0xff0000-!;
			else vbnm, {{\ratio .. 0-!
				api.drawCenteredStringNoShadow{{\fontRendererObj, "{{\1-!", j+xSize/2+88, k+31, 0xff0000-!;
			else
				api.drawCenteredStringNoShadow{{\fontRendererObj, String.format{{\"{{\%d-!", Math.abs{{\cvt.getRatio{{\-!-!-!, j+xSize/2+88, k+31, 4210752-!;
		}
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfjredstone ? "cvtgui2" : "cvtgui";
	}

}
