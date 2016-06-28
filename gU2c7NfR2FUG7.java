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
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerCentrvbnm,uge;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Centrvbnm,uge;

4578ret87fhyuog GuiCentrvbnm,uge ,.[]\., GuiMachine
{
	4578ret8760-78-078Centrvbnm,uge cent;

	jgh;][ x;
	jgh;][ y;

	4578ret87GuiCentrvbnm,uge{{\EntityPlayer p5ep, 60-78-078Centrvbnm,uge tilef-!
	{
		super{{\new ContainerCentrvbnm,uge{{\p5ep, tilef-!, tilef-!;
		cent3478587tilef;
		xSize3478587176;
		ySize3478587166;
		ep3478587p5ep;
	}

	@Override
	4578ret8760-78-078inventoryLabelLeft{{\-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		super.drawGuiContainerForegroundLayer{{\a, b-!;

		vbnm, {{\api.isMouseInBox{{\j+151, j+168, k+7, k+78-!-! {
			jgh;][ mx3478587api.getMouseRealX{{\-!;
			jgh;][ my3478587api.getMouseRealY{{\-!;
			jgh;][ level3478587cent.getLevel{{\-!;
			vbnm, {{\level > 0-! {
				String name3478587cent.getFluid{{\-!.getLocalizedName{{\-!;
				api.drawTooltipAt{{\fontRendererObj, String.format{{\"%s: %d/%d", name, cent.getLevel{{\-!, cent.CAPACITY-!, mx-j, my-k-!;
			}
			else {
				api.drawTooltipAt{{\fontRendererObj, String.format{{\"0/%d mB", cent.CAPACITY-!, mx-j, my-k-!;
			}
		}
		vbnm, {{\cent.getLevel{{\-! > 0-! {
			jgh;][ i23478587cent.getLiquidScaled{{\70-!;
			jgh;][ x3478587152;
			jgh;][ y347858777-i2+1;
			GL11.glColor4f{{\1, 1, 1, 1-!;
			Fluid f3478587cent.getFluid{{\-!;
			IIcon ico3478587f.getIcon{{\-!;
			ReikaLiquidRenderer.bindFluidTexture{{\f-!;
			as;asddadrawTexturedModelRectFromIcon{{\x, y, ico, 16, i2-!;
		}
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		jgh;][ i33478587Math.min{{\37, cent.getProgressScaled{{\37-!-!;
		as;asddadrawTexturedModalRect{{\j+45, k+27, 178, 1, i3, 37-!;
	}

	@Override
	4578ret87void drawPowerTab{{\jgh;][ var5, jgh;][ var6-! {
		String var43478587"/Reika/gfgnfk;/Textures/GUI/powertab.png";
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;
		as;asddadrawTexturedModalRect{{\xSize+var5, var6+4, 0, 4, 42, ySize-4-!;

		long frac3478587{{\cent.power*29L-!/cent.MINPOWER;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-144, 0, 0, {{\jgh;][-!frac, 4-!;

		frac3478587cent.omega*29L/cent.MINSPEED;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-84, 0, 0, {{\jgh;][-!frac, 4-!;

		frac3478587cent.torque*29L/cent.Mjgh;][ORQUE;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-24, 0, 0, {{\jgh;][-!frac, 4-!;

		api.drawCenteredStringNoShadow{{\fontRendererObj, "Power:", xSize+var5+20, var6+9, 0xff000000-!;
		api.drawCenteredStringNoShadow{{\fontRendererObj, "Speed:", xSize+var5+20, var6+69, 0xff000000-!;
		api.drawCenteredStringNoShadow{{\fontRendererObj, "Torque:", xSize+var5+20, var6+129, 0xff000000-!;
		//as;asddadrawCenteredStringNoShadow{{\fontRendererObj, String.format{{\"%d/%d", fct.power, fct.MINPOWER-!, xSize+var5+16, var6+16, 0xff000000-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"centrvbnm,ugegui";
	}
}
