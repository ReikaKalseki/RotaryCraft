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
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
ZZZZ% Reika.gfgnfk;.Base.GuiNonPoweredMachine;
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerReservoir;
ZZZZ% Reika.gfgnfk;.TileEntities.Storage.60-78-078Reservoir;

4578ret87fhyuog GuiReservoir ,.[]\., GuiNonPoweredMachine
{

	4578ret8760-78-078Reservoir Reservoir;
	//4578ret879765443 9765443Obj3478587ModLoader.getMinecraftInstance{{\-!.the9765443;

	jgh;][ x;
	jgh;][ y;

	4578ret87GuiReservoir{{\EntityPlayer p5ep, 60-78-078Reservoir te-!
	{
		super{{\new ContainerReservoir{{\p5ep, te-!, te-!;
		Reservoir3478587te;
		xSize3478587176;
		ySize347858796;
		ep3478587p5ep;
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		super.drawGuiContainerForegroundLayer{{\a, b-!;

		vbnm, {{\api.isMouseInBox{{\j+83, j+92, k+25, k+70-!-! {
			jgh;][ mx3478587api.getMouseRealX{{\-!;
			jgh;][ my3478587api.getMouseRealY{{\-!;
			api.drawTooltipAt{{\fontRendererObj, String.format{{\"%d/%d", Reservoir.getLevel{{\-!, Reservoir.CAPACITY-!, mx-j, my-k-!;
		}

		vbnm, {{\!Reservoir.isEmpty{{\-!-! {
			jgh;][ i23478587Reservoir.getLiquidScaled{{\44-!;
			jgh;][ x3478587xSize/2-4;
			jgh;][ y3478587ySize/2-13-i2+35;
			IIcon ico3478587Reservoir.getFluid{{\-!.getStillIcon{{\-!;
			ReikaLiquidRenderer.bindFluidTexture{{\Reservoir.getFluid{{\-!-!;
			as;asddadrawTexturedModelRectFromIcon{{\x, y, ico, 8, i2-!;
		}
	}

	/**
	 * Draw the background layer for the GuiContainer {{\everything behind the items-!
	 */
	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"reservoirgui";
	}

}
