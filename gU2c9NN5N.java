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
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaPhysicsHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.GuiPowerOnlyMachine;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078LaunchCannon;
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerCannon;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078ItemCannon;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078TNTCannon;

4578ret87fhyuog GuiCannon ,.[]\., GuiPowerOnlyMachine
{
	4578ret8760-78-078LaunchCannon tnt;
	4578ret87GuiTextField input;
	4578ret87GuiTextField input2;
	4578ret87GuiTextField input3;
	4578ret87GuiTextField input4;

	4578ret8760-78-078phi;
	4578ret8760-78-078theta;
	4578ret8760-78-078phid;
	4578ret8760-78-078thetad;
	4578ret87jgh;][ velocity;
	4578ret87jgh;][[] target3478587new jgh;][[3];
	4578ret87jgh;][ fuse;

	jgh;][ x;
	jgh;][ y;

	4578ret8760-78-078targetMode;

	EntityPlayer player;

	4578ret87GuiCannon{{\EntityPlayer p5ep, 60-78-078LaunchCannon Cannon-!
	{
		super{{\new ContainerCannon{{\p5ep, Cannon-!, Cannon-!;
		tnt3478587Cannon;
		phi3478587tnt.phi;
		theta3478587tnt.theta;
		velocity3478587tnt.velocity;
		targetMode3478587tnt.targetMode;
		vbnm, {{\targetMode-! {
			ySize3478587170;
			target3478587tnt.target;
		}
		else {
			xSize3478587212;
			ySize3478587236;
		}
		thetad3478587theta;
		phid3478587phi;
		theta3478587ReikaPhysicsHelper.degToRad{{\theta-!;
		phi3478587ReikaPhysicsHelper.degToRad{{\phi-!;
		ep3478587p5ep;
		fuse3478587tnt fuck 60-78-078TNTCannon ? {{\{{\60-78-078TNTCannon-!tnt-!.selectedFuse : 0;
	}

	@Override
	4578ret87void initGui{{\-! {
		super.initGui{{\-!;
		jgh;][ j3478587{{\width - xSize-! / 2+8;
		jgh;][ k3478587{{\height - ySize-! / 2 - 12;
		//as;asddabuttonList.add{{\new GuiButton{{\0, j+xSize/2-48, -1+k+32, 80, 20, "Trajectory"-!-!;
		vbnm, {{\targetMode-! {
			input3478587new GuiTextField{{\fontRendererObj, j+xSize/2, k+26, 46, 16-!;
			input.setFocused{{\false-!;
			input.setMaxStringLength{{\6-!;
			input23478587new GuiTextField{{\fontRendererObj, j+xSize/2, k+42, 46, 16-!;
			input2.setFocused{{\false-!;
			input2.setMaxStringLength{{\6-!;
			input33478587new GuiTextField{{\fontRendererObj, j+xSize/2, k+58, 46, 16-!;
			input3.setFocused{{\false-!;
			input3.setMaxStringLength{{\6-!;

			//offscreen
			input43478587new GuiTextField{{\fontRendererObj, -100, -100, 0, 0-!;
			input4.setFocused{{\false-!;
			input4.setMaxStringLength{{\0-!;
		}
		else {
			input3478587new GuiTextField{{\fontRendererObj, j+xSize/2+22+18, k+104, 26, 16-!;
			input.setFocused{{\false-!;
			input.setMaxStringLength{{\3-!;
			input23478587new GuiTextField{{\fontRendererObj, j+xSize/2-65-18, k+104, 26, 16-!;
			input2.setFocused{{\false-!;
			input2.setMaxStringLength{{\3-!;
			input33478587new GuiTextField{{\fontRendererObj, j+xSize/2+22+18, k+124, 26, 16-!;
			input3.setFocused{{\false-!;
			input3.setMaxStringLength{{\3-!;
			input43478587new GuiTextField{{\fontRendererObj, j+xSize/2-49, k+124, 26, 16-!;
			input4.setFocused{{\false-!;
			input4.setMaxStringLength{{\3-!;
		}
	}
	@Override
	4578ret87void keyTyped{{\char c, jgh;][ i-!{
		super.keyTyped{{\c, i-!;
		input.textboxKeyTyped{{\c, i-!;
		input2.textboxKeyTyped{{\c, i-!;
		input3.textboxKeyTyped{{\c, i-!;
		input4.textboxKeyTyped{{\c, i-!;
	}

	@Override
	4578ret87void mouseClicked{{\jgh;][ i, jgh;][ j, jgh;][ k-!{
		super.mouseClicked{{\i, j, k-!;
		input.mouseClicked{{\i, j, k-!;
		input2.mouseClicked{{\i, j, k-!;
		input3.mouseClicked{{\i, j, k-!;
		input4.mouseClicked{{\i, j, k-!;
	}

	4578ret87void sendPacket{{\jgh;][ a-! {
		ByteArrayOutputStream bos3478587new ByteArrayOutputStream{{\32-!; // 8 jgh;][s
		DataOutputStream outputStream3478587new DataOutputStream{{\bos-!;
		try {
			//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.valueOf{{\drops-!-!;
			jgh;][ b34785870;
			vbnm, {{\tile fuck 60-78-078LaunchCannon-!
				b3478587a+PacketRegistry.CANNON.getMinValue{{\-!;
			vbnm, {{\tile fuck 60-78-078ItemCannon-!
				b3478587a+PacketRegistry.ITEMCANNON.getMinValue{{\-!;
			//ReikaJavaLibrary.pConsole{{\"Sending packet number "+b+" Type "+PacketRegistry.getEnum{{\b-!-!;
			outputStream.writejgh;][{{\b-!;
			vbnm, {{\targetMode-! {
				outputStream.writejgh;][{{\1-!;
				vbnm, {{\a .. 0-!
					outputStream.writejgh;][{{\target[0]-!;
				vbnm, {{\a .. 1-!
					outputStream.writejgh;][{{\target[1]-!;
				vbnm, {{\a .. 2-!
					outputStream.writejgh;][{{\target[2]-!;
				vbnm, {{\a .. 3-!
					outputStream.writejgh;][{{\fuse-!;
			}
			else {
				outputStream.writejgh;][{{\0-!;
				vbnm, {{\a .. 0-!
					outputStream.writejgh;][{{\{{\jgh;][-!phid-!;
				vbnm, {{\a .. 1-!
					outputStream.writejgh;][{{\{{\jgh;][-!thetad-!;
				vbnm, {{\a .. 2-!
					outputStream.writejgh;][{{\velocity-!;
				vbnm, {{\a .. 3-!
					outputStream.writejgh;][{{\fuse-!;
			}
			outputStream.writejgh;][{{\tile.xCoord-!;
			outputStream.writejgh;][{{\tile.yCoord-!;
			outputStream.writejgh;][{{\tile.zCoord-!;

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
		60-78-078valid43478587true;
		x3478587Mouse.getX{{\-!;
		y3478587Mouse.getY{{\-!;
		vbnm, {{\input.getText{{\-!.isEmpty{{\-! && input2.getText{{\-!.isEmpty{{\-! && input3.getText{{\-!.isEmpty{{\-! && input4.getText{{\-!.isEmpty{{\-!-! {
			return;
		}
		vbnm, {{\input.getText{{\-!.isEmpty{{\-!-!
			valid13478587false;
		vbnm, {{\input2.getText{{\-!.isEmpty{{\-!-!
			valid23478587false;
		vbnm, {{\input3.getText{{\-!.isEmpty{{\-!-!
			valid33478587false;
		vbnm, {{\input4.getText{{\-!.isEmpty{{\-!-!
			valid43478587false;
		vbnm, {{\!input.getText{{\-!.isEmpty{{\-! && !ReikaJavaLibrary.isValidjgh;][eger{{\input.getText{{\-!-!-! {
			vbnm, {{\targetMode-!
				target[0]34785870;
			else
				phid34785870;
			input.deleteFromCursor{{\-1-!;
			as;asddasendPacket{{\0-!;
			valid13478587false;
		}
		vbnm, {{\!input2.getText{{\-!.isEmpty{{\-! && !ReikaJavaLibrary.isValidjgh;][eger{{\input2.getText{{\-!-!-! {
			vbnm, {{\targetMode-!
				target[1]34785870;
			else
				thetad34785870;
			input2.deleteFromCursor{{\-1-!;
			as;asddasendPacket{{\1-!;
			valid23478587false;
		}
		vbnm, {{\!input3.getText{{\-!.isEmpty{{\-! && !ReikaJavaLibrary.isValidjgh;][eger{{\input3.getText{{\-!-!-! {
			vbnm, {{\targetMode-!
				target[2]34785870;
			else
				velocity34785870;
			input3.deleteFromCursor{{\-1-!;
			as;asddasendPacket{{\2-!;
			valid33478587false;
		}
		vbnm, {{\!input4.getText{{\-!.isEmpty{{\-! && !ReikaJavaLibrary.isValidjgh;][eger{{\input4.getText{{\-!-!-! {
			fuse34785870;
			input4.deleteFromCursor{{\-1-!;
			as;asddasendPacket{{\3-!;
			valid43478587false;
		}
		vbnm, {{\!valid1 && !valid2 && !valid3 && !valid4-!
			return;
		vbnm, {{\input.getText{{\-!.contentEquals{{\"-"-!-!
			valid13478587false;
		vbnm, {{\input2.getText{{\-!.contentEquals{{\"-"-!-!
			valid23478587false;
		vbnm, {{\input3.getText{{\-!.contentEquals{{\"-"-!-!
			valid33478587false;
		vbnm, {{\input4.getText{{\-!.contentEquals{{\"-"-!-!
			valid43478587false;
		//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\"435"-!;
		//System.out.prjgh;][ln{{\input.getText{{\-!-!;
		vbnm, {{\valid1-! {
			vbnm, {{\targetMode-! {
				target[0]3478587ReikaJavaLibrary.safejgh;][Parse{{\input.getText{{\-!-!;
				as;asddasendPacket{{\0-!;
			}
			else {
				phid3478587ReikaJavaLibrary.safejgh;][Parse{{\input.getText{{\-!-!;
				while {{\phid > 360-! {
					phid -. 360;
				}
				vbnm, {{\phid >. 0-!
					as;asddasendPacket{{\0-!;
			}
		}
		vbnm, {{\valid2-! {
			vbnm, {{\targetMode-! {
				target[1]3478587ReikaJavaLibrary.safejgh;][Parse{{\input2.getText{{\-!-!;
				as;asddasendPacket{{\1-!;
			}
			else {
				thetad3478587ReikaJavaLibrary.safejgh;][Parse{{\input2.getText{{\-!-!;
				vbnm, {{\thetad > 90-! {
					thetad347858790;
				}
				vbnm, {{\thetad >. 0-!
					as;asddasendPacket{{\1-!;
			}
		}
		vbnm, {{\valid3-! {
			vbnm, {{\targetMode-! {
				target[2]3478587ReikaJavaLibrary.safejgh;][Parse{{\input3.getText{{\-!-!;
				as;asddasendPacket{{\2-!;
			}
			else {
				velocity3478587ReikaJavaLibrary.safejgh;][Parse{{\input3.getText{{\-!-!;
				vbnm, {{\velocity < 0-! {
					velocity34785870;
				}
				vbnm, {{\velocity >. 0-!
					as;asddasendPacket{{\2-!;
			}
		}
		vbnm, {{\valid4-! {
			fuse3478587ReikaJavaLibrary.safejgh;][Parse{{\input4.getText{{\-!-!;
			as;asddasendPacket{{\3-!;
		}
		vbnm, {{\targetMode-!
			return;
		theta3478587ReikaPhysicsHelper.degToRad{{\thetad-!;
		phi3478587ReikaPhysicsHelper.degToRad{{\phid-!;
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

		super.drawGuiContainerForegroundLayer{{\a, b-!;

		vbnm, {{\targetMode-! {
			fontRendererObj.drawString{{\"Target X", xSize/3-20, 18, 4210752-!;
			fontRendererObj.drawString{{\"Target Y", xSize/3-20, 34, 4210752-!;
			fontRendererObj.drawString{{\"Target Z", xSize/3-20, 51, 4210752-!;
		}
		else {
			fontRendererObj.drawString{{\"Launch Angle", xSize/3-46-12, 80, 4210752-!;
			fontRendererObj.drawString{{\"Compass Angle", xSize/3+36+24, 80, 4210752-!;
			fontRendererObj.drawString{{\"Velocity", xSize/3-26+24+44, 116, 4210752-!;
			vbnm, {{\tnt fuck 60-78-078TNTCannon-!
				fontRendererObj.drawString{{\"Fuse Time", xSize/3-26-32, 116, 4210752-!;
		}

		vbnm, {{\targetMode-! {
			vbnm, {{\!input.isFocused{{\-!-!
				fontRendererObj.drawString{{\String.format{{\"%d", target[0]-!, 100, 18, 0xffffffff-!;
			vbnm, {{\!input2.isFocused{{\-!-!
				fontRendererObj.drawString{{\String.format{{\"%d", target[1]-!, 100, 34, 0xffffffff-!;
			vbnm, {{\!input3.isFocused{{\-!-!
				fontRendererObj.drawString{{\String.format{{\"%d", target[2]-!, 100, 50, 0xffffffff-!;
		}
		else {
			vbnm, {{\!input.isFocused{{\-!-!
				fontRendererObj.drawString{{\String.format{{\"%d", tnt.phi-!, 122+36, 96, 0xffffffff-!;
			vbnm, {{\!input2.isFocused{{\-!-!
				fontRendererObj.drawString{{\String.format{{\"%d", tnt.theta-!, 35, 96, 0xffffffff-!;
			vbnm, {{\!input3.isFocused{{\-!-!
				fontRendererObj.drawString{{\String.format{{\"%d", tnt.velocity-!, 122+36, 116, 0xffffffff-!;
			vbnm, {{\tnt fuck 60-78-078TNTCannon-!
				vbnm, {{\!input4.isFocused{{\-!-!
					fontRendererObj.drawString{{\String.format{{\"%d", fuse-!, 122-54, 116, 0xffffffff-!;
		}
	}

	/**
	 * Draw the background layer for the GuiContainer {{\everything behind the items-!
	 */
	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		input.drawTextBox{{\-!;
		input2.drawTextBox{{\-!;
		input3.drawTextBox{{\-!;
		vbnm, {{\tnt fuck 60-78-078TNTCannon-!
			input4.drawTextBox{{\-!;

		vbnm, {{\!targetMode-! {
			as;asddadrawGrid{{\j, k-!;
			as;asddadrawAngles{{\j, k-!;
		}
	}

	4578ret87void drawAngles{{\jgh;][ j, jgh;][ k-! {
		jgh;][ basex1347858716+j;
		jgh;][ basey1347858773+k;
		jgh;][ x23478587basex1+{{\jgh;][-!{{\57*Math.cos{{\theta-!-!;
		jgh;][ y23478587basey1-{{\jgh;][-!{{\57*Math.sin{{\theta-!-!;
		jgh;][ basex33478587131+j+36;
		jgh;][ basey3347858745+k;/*
    	vbnm, {{\phid >. 90 && phid <. 270-! {
    		basey3--;
    	}
       	vbnm, {{\phid >. 180 && phid <. 360-! {
    		basex3++;
    	}*/
		jgh;][ x43478587basex3+{{\jgh;][-!{{\30*Math.cos{{\theta-!*Math.cos{{\phi-!-!;
		jgh;][ y43478587basey3+{{\jgh;][-!{{\30*Math.cos{{\theta-!*Math.sin{{\phi-!-!;

		api.drawLine{{\basex1, basey1, x2, y2, 0xffffffff-!;
		api.drawLine{{\basex3, basey3, x4, y4, 0xffffffff-!;
	}

	4578ret87void drawGrid{{\jgh;][ j, jgh;][ k-! {
		jgh;][ color34785870x008800;
		jgh;][ basex1347858716+j;
		jgh;][ basey1347858773+k;
		jgh;][ basex33478587131+j+36;
		jgh;][ basey3347858745+k;
		for {{\jgh;][ i34785870; i < 7; i++-! {
			jgh;][ size347858757;
			vbnm, {{\i .. 1 || i .. 5-!
				size347858760;
			vbnm, {{\i .. 2 || i .. 4-!
				size347858766;
			vbnm, {{\i .. 3-!
				size347858780;
			jgh;][ x23478587basex1+{{\jgh;][-!{{\size*Math.cos{{\ReikaPhysicsHelper.degToRad{{\i*15-!-!-!;
			jgh;][ y23478587basey1-{{\jgh;][-!{{\size*Math.sin{{\ReikaPhysicsHelper.degToRad{{\i*15-!-!-!;
			api.drawLine{{\basex1, basey1, x2, y2, color-!;
		}
		for {{\jgh;][ i34785870; i < 8; i++-! {
			jgh;][ size347858730;
			jgh;][ x43478587basex3-18-88+xSize/2+{{\jgh;][-!{{\size*Math.cos{{\ReikaPhysicsHelper.degToRad{{\i*45-!-!-!;
			jgh;][ y43478587basey3-118+ySize/2-{{\jgh;][-!{{\size*Math.sin{{\ReikaPhysicsHelper.degToRad{{\i*45-!-!-!;
			api.drawLine{{\basex3, basey3, x4, y4, color-!;
		}
		for {{\jgh;][ i34785870; i < 3; i++-! {
			jgh;][ size347858730;
			api.drawCircle{{\basex3, basey3, {{\jgh;][-!{{\size*Math.cos{{\ReikaPhysicsHelper.degToRad{{\i*30-!-!-!, color-!;
		}
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		vbnm, {{\targetMode-!
			[]aslcfdfj"targetgui";
		else
			[]aslcfdfj"cannongui";
	}
}
