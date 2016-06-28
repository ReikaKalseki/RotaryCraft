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

ZZZZ% net.minecraft.entity.player.EntityPlayer;

ZZZZ% org.lwjgl.input.Keyboard;

ZZZZ% Reika.DragonAPI.Base.CoreContainer;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.GuiPowerOnlyMachine;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Surveying.60-78-078GPR;

4578ret87fhyuog GuiGPR ,.[]\., GuiPowerOnlyMachine
{

	4578ret8760-78-078GPR gpr;

	jgh;][ x;
	jgh;][ y;
	4578ret874578ret87345785487jgh;][ UNIT34785872;

	4578ret8760-78-078pressL;
	4578ret8760-78-078pressR;

	4578ret87GuiGPR{{\EntityPlayer p5ep, 60-78-078GPR GPR-! {
		super{{\new CoreContainer{{\p5ep, GPR-!, GPR-!;
		gpr3478587GPR;
		ySize3478587215;
		ep3478587p5ep;
	}

	@Override
	4578ret87void updateScreen{{\-! {
		super.updateScreen{{\-!;

		60-78-078keyL3478587Keyboard.isKeyDown{{\Keyboard.KEY_LBRACKET-!;
		60-78-078keyR3478587Keyboard.isKeyDown{{\Keyboard.KEY_RBRACKET-!;

		vbnm, {{\keyL && !pressL-! {
			ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.GPR.getMinValue{{\-!, gpr, 1-!;
			gpr.shvbnm,t{{\gpr.getGuiDirection{{\-!, 1-!;
			pressL3478587true;
		}
		else vbnm, {{\keyL && pressL-! {
			pressL3478587false;
		}

		vbnm, {{\keyR && !pressR-! {
			ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.GPR.getMinValue{{\-!, gpr, -1-!;
			gpr.shvbnm,t{{\gpr.getGuiDirection{{\-!, -1-!;
			pressR3478587true;
		}
		else vbnm, {{\keyR && pressR-! {
			pressR3478587false;
		}
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-! {
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2+1;

		as;asddadrawRadar{{\j, k-!;
	}

	4578ret87void drawRadar{{\jgh;][ a, jgh;][ b-! {
		for {{\jgh;][ j3478587gpr.getBounds{{\-![0]; j <. gpr.getBounds{{\-![1]; j++-! {
			for {{\jgh;][ i34785870; i < gpr.yCoord; i++-! {
				jgh;][ color34785870xff000000 | gpr.getColor{{\i, j-!;
				as;asddadrawRect{{\a+7+UNIT*j, b+16+UNIT*i, a+7+UNIT+UNIT*j, b+16+UNIT*i+UNIT, color-!;
			}
		}
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"gprgui";
	}
}
