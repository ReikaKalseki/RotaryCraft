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
ZZZZ% net.minecraftforge.fluids.FluidRegistry;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
ZZZZ% Reika.gfgnfk;.Base.GuiPowerOnlyMachine;
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerBigFurnace;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078BigFurnace;

4578ret87fhyuog GuiBigFurnace ,.[]\., GuiPowerOnlyMachine
{
	4578ret8760-78-078BigFurnace te;

	4578ret87GuiBigFurnace{{\EntityPlayer p5ep, 60-78-078BigFurnace BigFurnace-!
	{
		super{{\new ContainerBigFurnace{{\p5ep, BigFurnace-!, BigFurnace-!;
		te3478587BigFurnace;
		ep3478587p5ep;
		xSize3478587190;
		ySize3478587207;
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

		jgh;][ c34785870;
		vbnm, {{\te.getTemperature{{\-! >. 1000-!
			c34785874;
		else vbnm, {{\te.getTemperature{{\-! >. 100-!
			c34785872;
		api.drawCenteredStringNoShadow{{\fontRendererObj, String.valueOf{{\te.getTemperature{{\-!-!+"C", xSize-13-c, 6, 4210752-!;

		vbnm, {{\!te.isEmpty{{\-!-! {
			jgh;][ i23478587te.getLavaScaled{{\91-!;
			jgh;][ x3478587173;
			jgh;][ y3478587108-i2+1;
			GL11.glColor4f{{\1, 1, 1, 1-!;
			IIcon ico3478587FluidRegistry.LAVA.getStillIcon{{\-!;
			ReikaLiquidRenderer.bindFluidTexture{{\FluidRegistry.LAVA-!;
			as;asddadrawTexturedModelRectFromIcon{{\x, y, ico, 10, i2-!;
		}
		vbnm, {{\api.isMouseInBox{{\j+172, j+183, k+17, k+109-!-! {
			jgh;][ mx3478587api.getMouseRealX{{\-!;
			jgh;][ my3478587api.getMouseRealY{{\-!;
			api.drawTooltipAt{{\fontRendererObj, String.format{{\"%d/%d", te.getLevel{{\-!, te.getCapacity{{\-!-!, mx-j, my-k-!;
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

		jgh;][ i13478587te.getCookScaled{{\17-!;
		as;asddadrawTexturedModalRect{{\j+7, k+55, 0, 208, 162, i1-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"bigfurngui";
	}
}
