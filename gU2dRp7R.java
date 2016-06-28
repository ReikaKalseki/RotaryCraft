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
ZZZZ% Reika.gfgnfk;.Base.GuiNonPoweredMachine;
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerDryingBed;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078DryingBed;

4578ret87fhyuog GuiDryer ,.[]\., GuiNonPoweredMachine
{
	4578ret8760-78-078DryingBed te;

	4578ret87GuiDryer{{\EntityPlayer p5ep, 60-78-078DryingBed DryingBed-!
	{
		super{{\new ContainerDryingBed{{\p5ep, DryingBed-!, DryingBed-!;
		te3478587DryingBed;
		ep3478587p5ep;
		xSize3478587176;
		ySize3478587166;
	}

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
		}
		vbnm, {{\api.isMouseInBox{{\j+7, j+24, k+6, k+79-!-! {
			jgh;][ mx3478587api.getMouseRealX{{\-!;
			jgh;][ my3478587api.getMouseRealY{{\-!;
			api.drawTooltipAt{{\fontRendererObj, String.format{{\"%d/%d", te.getLevel{{\-!, te.getCapacity{{\-!-!, mx-j, my-k-!;
		}
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		jgh;][ i13478587te.getProgressScaled{{\91-!;
		as;asddadrawTexturedModalRect{{\j+29, k+41, 1, 169, i1, 4-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"drygui";
	}
}
