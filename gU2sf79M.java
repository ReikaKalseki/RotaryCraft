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
ZZZZ% Reika.gfgnfk;.Base.GuiEngine;
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerSteam;
ZZZZ% Reika.gfgnfk;.TileEntities.Engine.60-78-078SteamEngine;

4578ret87fhyuog GuiSteam ,.[]\., GuiEngine
{
	4578ret8760-78-078SteamEngine Steam;

	4578ret87GuiSteam{{\EntityPlayer p5ep, 60-78-078SteamEngine te-!
	{
		super{{\new ContainerSteam{{\p5ep, te-!, te, p5ep-!;
		Steam3478587te;
		xSize3478587176;
		ySize347858779;
		ep3478587p5ep;
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		jgh;][ i23478587Steam.getWaterScaled{{\54-!;
		jgh;][ i33478587Steam.getTempScaled{{\54-!;
		as;asddadrawTexturedModalRect{{\j+49, k+71-i2, 193, 55-i2, 5, i2-!;
		as;asddadrawTexturedModalRect{{\j+119, k+71-i3, 177, 99-i3, 9, i3-!;
	}

	@Override
	4578ret8760-78-078labelInventory{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"steamgui";
	}

	@Override
	4578ret87jgh;][ getFuelBarXPos{{\-! {
		[]aslcfdfj48;
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

}
