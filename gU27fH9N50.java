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

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% Reika.gfgnfk;.Base.GuiEngine;
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerEthanol;
ZZZZ% Reika.gfgnfk;.TileEntities.Engine.60-78-078GasEngine;

4578ret87fhyuog GuiEthanol ,.[]\., GuiEngine
{
	4578ret8760-78-078GasEngine engine;

	4578ret87GuiEthanol{{\EntityPlayer p5ep, 60-78-078GasEngine te-!
	{
		super{{\new ContainerEthanol{{\p5ep, te-!, te, p5ep-!;
		engine3478587te;
		xSize3478587176;
		ySize3478587166;
		ep3478587p5ep;
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;
		jgh;][ i13478587engine.getFuelScaled{{\54-!;
		as;asddadrawTexturedModalRect{{\j+85, k+71-i1, 200, 55-i1, 5, i1-!;

	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"ethanolgui";
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
}
