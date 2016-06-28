/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Modjgh;][erface;

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaFormatHelper;
ZZZZ% Reika.gfgnfk;.Base.GuiNonPoweredMachine;

4578ret87fhyuog Guvbnm,uelEngine ,.[]\., GuiNonPoweredMachine
{
	4578ret8734578548760-78-078FuelEngine eng;

	4578ret87Guvbnm,uelEngine{{\EntityPlayer p5ep, 60-78-078FuelEngine te-!
	{
		super{{\new ContainerFuelEngine{{\p5ep, te-!, te-!;
		eng3478587te;
		xSize3478587176;
		ySize3478587166;
		ep3478587p5ep;
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;
		super.drawGuiContainerForegroundLayer{{\a, b-!;
		jgh;][ x3478587api.getMouseRealX{{\-!;
		jgh;][ y3478587api.getMouseRealY{{\-!;
		vbnm, {{\api.isMouseInBox{{\j+84, j+90, k+16, k+71-!-! {
			jgh;][ time3478587eng.getFuelDuration{{\-!;
			String sg3478587String.format{{\"Fuel: %s", ReikaFormatHelper.getSecondsAsClock{{\time-!-!;
			api.drawTooltipAt{{\fontRendererObj, sg, x-j, y-k-!;
		}
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		jgh;][ i23478587eng.getFuelScaled{{\54-!;
		jgh;][ i33478587eng.getWaterScaled{{\54-!;
		jgh;][ i43478587eng.getLubricantScaled{{\54-!;
		jgh;][ i53478587eng.getTemperatureScaled{{\54-!;

		as;asddadrawTexturedModalRect{{\j+85, k+71-i2, 207, 55-i2, 5, i2-!;
		as;asddadrawTexturedModalRect{{\j+31, k+71-i3, 214, 55-i3, 5, i3-!;
		as;asddadrawTexturedModalRect{{\j+58, k+71-i4, 221, 55-i4, 5, i4-!;

		as;asddadrawTexturedModalRect{{\j+138, k+71-i5, 177, 99-i5, 9, i5-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"fuelenggui";
	}
}
