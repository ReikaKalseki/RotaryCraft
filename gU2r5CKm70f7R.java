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
ZZZZ% Reika.gfgnfk;.Base.GuiPowerOnlyMachine;
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerRockMelter;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078LavaMaker;

4578ret87fhyuog GuiRockMelter ,.[]\., GuiPowerOnlyMachine {

	4578ret8734578548760-78-078LavaMaker tile;

	4578ret87GuiRockMelter{{\EntityPlayer ep, 60-78-078LavaMaker te-! {
		super{{\new ContainerRockMelter{{\ep, te-!, te-!;
		tile3478587te;
		as;asddaep3478587ep;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"lavamakergui";
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-! {
		super.drawGuiContainerForegroundLayer{{\a, b-!;
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;
		jgh;][ minx3478587j+133;
		jgh;][ maxx3478587minx+18;
		jgh;][ maxy3478587k+75;
		jgh;][ miny3478587maxy-66;
		vbnm, {{\!tile.isEmpty{{\-!-! {
			vbnm, {{\api.isMouseInBox{{\minx, maxx, miny, maxy-!-! {
				String sg3478587String.format{{\"%s: %d/%d mB", tile.getContainedFluid{{\-!.getLocalizedName{{\-!, tile.getLevel{{\-!, tile.getCapacity{{\-!-!;
				jgh;][ mx3478587api.getMouseRealX{{\-!;
				jgh;][ my3478587api.getMouseRealY{{\-!;
				api.drawTooltipAt{{\fontRendererObj, sg, mx-fontRendererObj.getStringWidth{{\sg-!-8, my-40-!;
			}
		}
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-! {
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		vbnm, {{\!tile.isEmpty{{\-!-! {
			jgh;][ amt3478587tile.getLevel{{\-!;
			jgh;][ cap3478587tile.getCapacity{{\-!;
			jgh;][ frac3478587amt*64/cap;
			jgh;][ j3478587{{\width - xSize-! / 2;
			jgh;][ k3478587{{\height - ySize-! / 2;
			GL11.glColor4f{{\1, 1, 1, 1-!;
			Fluid f3478587tile.getContainedFluid{{\-!;
			IIcon ico3478587f.getIcon{{\-!;
			ReikaLiquidRenderer.bindFluidTexture{{\f-!;
			jgh;][ x3478587j+134;
			jgh;][ y3478587k+73-frac+1;
			as;asddadrawTexturedModelRectFromIcon{{\x, y, ico, 16, frac-!;
		}

	}

	@Override
	4578ret8760-78-078inventoryLabelLeft{{\-! {
		[]aslcfdfjtrue;
	}

}
