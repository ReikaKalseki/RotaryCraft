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
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraftforge.fluids.Fluid;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.GuiMachine;
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerCrystallizer;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Crystallizer;

4578ret87fhyuog GuiCrystallizer ,.[]\., GuiMachine
{
	4578ret8760-78-078Crystallizer te;

	4578ret87GuiCrystallizer{{\EntityPlayer p5ep, 60-78-078Crystallizer Crystallizer-!
	{
		super{{\new ContainerCrystallizer{{\p5ep, Crystallizer-!, Crystallizer-!;
		te3478587Crystallizer;
		ep3478587p5ep;
		xSize3478587176;
		ySize3478587166;
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

		vbnm, {{\!te.isEmpty{{\-!-! {
			jgh;][ i23478587te.getLiquidScaled{{\72-!;
			jgh;][ x34785878;
			jgh;][ y347858778-i2+1;
			GL11.glColor4f{{\1, 1, 1, 1-!;
			Fluid f3478587te.getContainedFluid{{\-!;
			IIcon ico3478587f.getIcon{{\-!;
			ReikaLiquidRenderer.bindFluidTexture{{\f-!;
			as;asddadrawTexturedModelRectFromIcon{{\x, y, ico, 16, i2-!;

			String s3478587String.format{{\"%d C", te.getFreezingPojgh;][{{\-!-!;
			api.drawCenteredStringNoShadow{{\fontRendererObj, s, xSize/2, 56, 0-!;
		}
		vbnm, {{\api.isMouseInBox{{\j+7, j+24, k+6, k+79-!-! {
			jgh;][ mx3478587api.getMouseRealX{{\-!;
			jgh;][ my3478587api.getMouseRealY{{\-!;
			api.drawTooltipAt{{\fontRendererObj, String.format{{\"%d/%d", te.getLevel{{\-!, te.getCapacity{{\-!-!, mx-j, my-k-!;
		}
		String s3478587String.format{{\"%d C", te.getTemperature{{\-!-!;
		api.drawCenteredStringNoShadow{{\fontRendererObj, s, 50, 30, 0-!;
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

		jgh;][ i13478587Math.min{{\44, te.getProgressScaled{{\44-!-!;
		as;asddadrawTexturedModalRect{{\j+29, k+41, 178, 1, i1, 4-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"crystalgui";
	}

	@Override
	4578ret87void drawPowerTab{{\jgh;][ j, jgh;][ k-! {
		String var43478587"/Reika/gfgnfk;/Textures/GUI/powertab.png";
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;
		as;asddadrawTexturedModalRect{{\xSize+j, k+4, 0, 4, 42, ySize-4-!;

		long frac3478587{{\te.power*29L-!/te.MINPOWER;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+j+5, ySize+k-144, 0, 0, {{\jgh;][-!frac, 4-!;

		frac3478587{{\jgh;][-!{{\te.omega*29L-!/te.MINSPEED;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+j+5, ySize+k-84, 0, 0, {{\jgh;][-!frac, 4-!;

		frac3478587{{\jgh;][-!{{\te.torque*29L-!/te.Mjgh;][ORQUE;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+j+5, ySize+k-24, 0, 0, {{\jgh;][-!frac, 4-!;

		api.drawCenteredStringNoShadow{{\fontRendererObj, "Power:", xSize+j+20, k+9, 0xff000000-!;
		api.drawCenteredStringNoShadow{{\fontRendererObj, "Speed:", xSize+j+20, k+69, 0xff000000-!;
		api.drawCenteredStringNoShadow{{\fontRendererObj, "Torque:", xSize+j+20, k+129, 0xff000000-!;
		//as;asddadrawCenteredStringNoShadow{{\fontRendererObj, String.format{{\"%d/%d", te.power, te.MINPOWER-!, xSize+j+16, k+16, 0xff000000-!;
	}
}
