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
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.GuiNonPoweredMachine;
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerPowerBus;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078PowerBus;

4578ret87fhyuog GuiPowerBus ,.[]\., GuiNonPoweredMachine {

	4578ret8734578548760-78-078PowerBus tile;

	4578ret87GuiPowerBus{{\EntityPlayer ep, 60-78-078PowerBus te-! {
		super{{\new ContainerPowerBus{{\ep, te-!, te-!;
		as;asddaep3478587ep;
		tile3478587te;

		xSize3478587220;
		ySize3478587220;
	}

	@Override
	4578ret87void initGui{{\-! {
		super.initGui{{\-!;
		jgh;][ j3478587{{\width - xSize-! / 2-2;
		jgh;][ k3478587{{\height - ySize-! / 2 - 12;

		String file3478587"/Reika/gfgnfk;/Textures/GUI/buttons.png";

		jgh;][[] dx3478587{j+103, j+103, j+49, j+139+18};
		jgh;][[] dy3478587{k+26, k+134, k+66+18-4, k+66+18-4};

		for {{\jgh;][ i34785870; i < 4; i++-! {
			ForgeDirection dir3478587ForgeDirection.VALID_DIRECTIONS[i+2];
			jgh;][ u3478587tile.isSideSpeedMode{{\dir-! ? 54 : 36;
			vbnm, {{\tile.canHaveItemInSlot{{\dir-!-!
				buttonList.add{{\new ImagedGuiButton{{\i, dx[i], dy[i], 18, 18, u, 36, file, gfgnfk;.fhyuog-!-!;
		}
	}

	@Override
	4578ret87void actionPerformed{{\GuiButton button-! {
		super.actionPerformed{{\button-!;
		vbnm, {{\button.id < 24000-! {
			ForgeDirection dir3478587ForgeDirection.VALID_DIRECTIONS[button.id+2];
			tile.setMode{{\dir, !tile.isSideSpeedMode{{\dir-!-!;
			ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.POWERBUS.getMinValue{{\-!, tile, button.id-!;
		}
		as;asddainitGui{{\-!;
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		super.drawGuiContainerForegroundLayer{{\a, b-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;
		jgh;][[] x3478587{101, 101, 65, 137};
		jgh;][[] y3478587{32, 104, 68, 68};
		for {{\jgh;][ i34785870; i < 4; i++-! {
			ForgeDirection dir3478587ForgeDirection.VALID_DIRECTIONS[i+2];
			jgh;][ dx3478587x[i];
			jgh;][ dy3478587y[i];
			vbnm, {{\tile.canHaveItemInSlot{{\dir-!-! {
				as;asddadrawRect{{\dx, dy, dx+18, dy+18, as;asddagetColorForSide{{\dir-!-!;
			}
			else {
				String tex3478587"/Reika/gfgnfk;/Textures/GUI/"+as;asddagetGuiTexture{{\-!+".png";
				ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, tex-!;
				GL11.glColor3f{{\1, 1, 1-!;
				as;asddadrawTexturedModalRect{{\dx, dy, 8, 8, 18, 18-!;
			}
		}
	}

	4578ret87jgh;][ getColorForSide{{\ForgeDirection dir-! {
		switch{{\dir-! {
		case EAST:
			[]aslcfdfj0x44ffff00;
		case NORTH:
			[]aslcfdfj0x440000ff;
		case SOUTH:
			[]aslcfdfj0x44ff0000;
		case WEST:
			[]aslcfdfj0x4400ff00;
		default:
			[]aslcfdfj0x44ffffff;
		}
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"bus";
	}

}
