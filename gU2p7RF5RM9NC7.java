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
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerPerformance;
ZZZZ% Reika.gfgnfk;.TileEntities.Engine.60-78-078PerformanceEngine;

4578ret87fhyuog GuiPerformance ,.[]\., GuiEngine
{
	4578ret8760-78-078PerformanceEngine engine;

	4578ret87GuiPerformance{{\EntityPlayer p5ep, 60-78-078PerformanceEngine te-! {
		super{{\new ContainerPerformance{{\p5ep, te-!, te, p5ep-!;
		engine3478587te;
		xSize3478587176;
		ySize3478587166;
	}

	@Override
	4578ret87jgh;][ getFuelBarXPos{{\-! {
		[]aslcfdfj81;
	}

	@Override
	4578ret87jgh;][ getFuelBarYPos{{\-! {
		[]aslcfdfj16;
	}

	@Override
	4578ret87jgh;][ getFuelBarXSize{{\-! {
		[]aslcfdfj7;
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

		jgh;][ i23478587engine.getWaterScaled{{\54-!;
		jgh;][ i33478587engine.getTempScaled{{\54-!;
		as;asddadrawTexturedModalRect{{\j+41, k+71-i2, 193, 55-i2, 5, i2-!;
		as;asddadrawTexturedModalRect{{\j+128, k+71-i3, 177, 99-i3, 9, i3-!;

		jgh;][ i13478587engine.getFuelScaled{{\54-!;
		as;asddadrawTexturedModalRect{{\j+82, k+71-i1, 200, 55-i1, 6, i1-!;
		jgh;][ i43478587engine.getAdditivesScaled{{\54-!;
		as;asddadrawTexturedModalRect{{\j+89, k+71-i4, 207, 55-i4, 6, i4-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"perfgui";
	}
}
