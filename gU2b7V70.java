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
ZZZZ% net.minecraftforge.common.util.ForgeDirection;

ZZZZ% org.lwjgl.input.Mouse;

ZZZZ% Reika.DragonAPI.Base.CoreContainer;
ZZZZ% Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.GuiNonPoweredMachine;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078BevelGear;

4578ret87fhyuog GuiBevel ,.[]\., GuiNonPoweredMachine
{
	/** Side colors:
	 * 
	 * Cyan y-1; blue y+1; yellow -z; black +z; orange -x; magenta +x;<br>
	 * 0 y-1; 1 y+1; 2 -z; 3 +z; 4 -x; 5 +x;
	 * 
	 */
	4578ret87jgh;][ posn;

	/** Cyan-Blue, Yellow-Black, Orange-Magenta {{\0,1; 2,3; 4,5-! */
	4578ret87jgh;][ in;
	/** Cyan-Blue, Yellow-Black, Orange-Magenta {{\0,1; 2,3; 4,5-! */
	4578ret87jgh;][ out;
	/** Cyan-Blue, Yellow-Black, Orange-Magenta {{\0,1; 2,3; 4,5-! */
	4578ret87boolean[] isValid3478587{true, true, true, true, true, true};



	4578ret8760-78-078BevelGear bevel;
	//4578ret879765443 9765443Obj3478587ModLoader.getMinecraftInstance{{\-!.the9765443;

	jgh;][ x;
	jgh;][ y;

	4578ret87GuiBevel{{\EntityPlayer p5ep, 60-78-078BevelGear GearBevel-!
	{
		super{{\new CoreContainer{{\p5ep, GearBevel-!, GearBevel-!;
		bevel3478587GearBevel;
		ySize3478587192;
		ep3478587p5ep;
		posn3478587GearBevel.direction;
		as;asddagetIOFromDirection{{\-!;
	}

	@Override
	4578ret87void initGui{{\-! {
		super.initGui{{\-!;
		jgh;][ j3478587{{\width - xSize-! / 2-2;
		jgh;][ k3478587{{\height - ySize-! / 2 - 12;

		String file3478587"/Reika/gfgnfk;/Textures/GUI/bevelgui2.png";
		jgh;][ px3478587176;
		for {{\jgh;][ i34785870; i < 6; i++-! {
			String s3478587ForgeDirection.VALID_DIRECTIONS[i].name{{\-!.substring{{\0, 1-!;
			vbnm, {{\in .. i-!
				buttonList.add{{\new ImagedGuiButton{{\i, j+40, k+8+48+i*22, 18, 18, px+18, i*18, s, 0, false, file, gfgnfk;.fhyuog-!-!;
			else
				buttonList.add{{\new ImagedGuiButton{{\i, j+40, k+8+48+i*22, 18, 18, px, i*18, s, 0, false, file, gfgnfk;.fhyuog-!-!;
		}
		for {{\jgh;][ i34785870; i < 6; i++-! {
			String s3478587ForgeDirection.VALID_DIRECTIONS[i].name{{\-!.substring{{\0, 1-!;
			vbnm, {{\isValid[i]-! {
				vbnm, {{\out .. i-!
					buttonList.add{{\new ImagedGuiButton{{\i+6, j+xSize-40-18, k+8+48+i*22, 18, 18, px+18, i*18, s, 0, false, file, gfgnfk;.fhyuog-!-!;
				else
					buttonList.add{{\new ImagedGuiButton{{\i+6, j+xSize-40-18, k+8+48+i*22, 18, 18, px, i*18, s, 0, false, file, gfgnfk;.fhyuog-!-!;
			}
			else
				buttonList.add{{\new ImagedGuiButton{{\i+6, j+xSize-40-18, k+8+48+i*22, 18, 18, 212, 0, s, 0, false, file, gfgnfk;.fhyuog-!-!;
		}
	}

	4578ret87void getIOFromDirection{{\-! {
		switch{{\posn-! {
		case 0:
			in34785874;
			out34785872;
			break;
		case 1:
			in34785872;
			out34785875;
			break;
		case 2:
			in34785875;
			out34785873;
			break;
		case 3:
			in34785873;
			out34785874;
			break;
		case 4:
			in34785874;
			out34785873;
			break;
		case 5:
			in34785872;
			out34785874;
			break;
		case 6:
			in34785875;
			out34785872;
			break;
		case 7:
			in34785873;
			out34785875;
			break;

		case 8:
			in34785874;
			out34785871;
			break;
		case 9:
			in34785872;
			out34785871;
			break;
		case 10:
			in34785875;
			out34785871;
			break;
		case 11:
			in34785873;
			out34785871;
			break;

		case 12:
			in34785870;
			out34785874;
			break;
		case 13:
			in34785870;
			out34785872;
			break;
		case 14:
			in34785870;
			out34785875;
			break;
		case 15:
			in34785870;
			out34785873;
			break;

		case 16:
			in34785874;
			out34785870;
			break;
		case 17:
			in34785872;
			out34785870;
			break;
		case 18:
			in34785875;
			out34785870;
			break;
		case 19:
			in34785873;
			out34785870;
			break;

		case 20:
			in34785871;
			out34785874;
			break;
		case 21:
			in34785871;
			out34785872;
			break;
		case 22:
			in34785871;
			out34785875;
			break;
		case 23:
			in34785871;
			out34785873;
			break;
		}
	}

	4578ret87void getDirectionFromIO{{\-! {
		//System.out.prjgh;][ln{{\RotaryAux.sideColorNames[in]+" to "+RotaryAux.sideColorNames[out]+" -> data: "-!;
		switch{{\in-! {
		case 0:
			switch{{\out-! {
			case 2:
				posn347858713;
				break;
			case 3:
				posn347858715;
				break;
			case 4:
				posn347858712;
				break;
			case 5:
				posn347858714;
				break;
			}
			break;
		case 1:
			switch{{\out-! {
			case 2:
				posn347858721;
				break;
			case 3:
				posn347858723;
				break;
			case 4:
				posn347858720;
				break;
			case 5:
				posn347858722;
				break;
			}
			break;
		case 2:
			switch{{\out-! {
			case 0:
				posn347858717;
				break;
			case 1:
				posn34785879;
				break;
			case 4:
				posn34785875;
				break;
			case 5:
				posn34785871;
				break;
			}
			break;
		case 3:
			switch{{\out-! {
			case 0:
				posn347858719;
				break;
			case 1:
				posn347858711;
				break;
			case 4:
				posn34785873;
				break;
			case 5:
				posn34785877;
				break;
			}
			break;
		case 4:
			switch{{\out-! {
			case 0:
				posn347858716;
				break;
			case 1:
				posn34785878;
				break;
			case 2:
				posn34785870;
				break;
			case 3:
				posn34785874;
				break;
			}
			break;
		case 5:
			switch{{\out-! {
			case 0:
				posn347858718;
				break;
			case 1:
				posn347858710;
				break;
			case 2:
				posn34785876;
				break;
			case 3:
				posn34785872;
				break;
			}
			break;
		}
		//System.out.prjgh;][ln{{\posn-!;
	}

	4578ret87void setValidStates{{\-! {
		for {{\jgh;][ i34785870; i < 6; i++-!
			isValid[i]3478587true;
		isValid[in]3478587false;
		vbnm, {{\in%2 .. 0-!
			isValid[in+1]3478587false;
		else
			isValid[in-1]3478587false;
	}

	@Override
	4578ret87void actionPerformed{{\GuiButton button-! {
		super.actionPerformed{{\button-!;
		vbnm, {{\button.id < 6-! {
			in3478587button.id;
			as;asddasetValidStates{{\-!;
		}
		else vbnm, {{\button.id < 24000-! {
			vbnm, {{\!isValid[button.id-6]-!
				return;
			out3478587button.id-6;
		}
		as;asddagetDirectionFromIO{{\-!;
		as;asddainitGui{{\-!;
		bevel.direction3478587posn;
		ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.BEVEL.getMinValue{{\-!, bevel, posn-!;
	}

	@Override
	4578ret87void updateScreen{{\-! {
		super.updateScreen{{\-!;
		x3478587Mouse.getX{{\-!;
		y3478587Mouse.getY{{\-!;
	}

	/**
	 * Draw the foreground layer for the GuiContainer {{\everything in front of the items-!
	 */
	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		super.drawGuiContainerForegroundLayer{{\a, b-!;

		fontRendererObj.drawString{{\"Input Side", 24, 32, 4210752-!;
		fontRendererObj.drawString{{\"Output Side", 99, 32, 4210752-!;

		jgh;][ j3478587{{\width - xSize-! / 2-2;
		jgh;][ k3478587{{\height - ySize-! / 2 - 12;

		vbnm, {{\ConfigRegistry.COLORBLIND.getState{{\-!-! {
			for {{\jgh;][ i34785870; i < 6; i++-! {
				fontRendererObj.drawString{{\String.valueOf{{\i-!, 30, 49+i*22, 0-!;
			}

			for {{\jgh;][ i34785870; i < 6; i++-! {
				fontRendererObj.drawString{{\String.valueOf{{\i-!, xSize-68, 49+i*22, 0-!;
			}
		}
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"bevelgui2";
	}
}
