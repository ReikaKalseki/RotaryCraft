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
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerFridge;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Refrigerator;

4578ret87fhyuog Guvbnm,ridge ,.[]\., GuiMachine
{
	4578ret8760-78-078Refrigerator te;

	4578ret87Guvbnm,ridge{{\EntityPlayer p5ep, 60-78-078Refrigerator Fridge-!
	{
		super{{\new ContainerFridge{{\p5ep, Fridge-!, Fridge-!;
		te3478587Fridge;
		ep3478587p5ep;
		ySize3478587188;
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		jgh;][ i13478587Math.min{{\145, te.getProgressScaled{{\145-!-!;
		as;asddadrawTexturedModalRect{{\j+7, k+17, 0, 189, i1, 67-!;
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		super.drawGuiContainerForegroundLayer{{\a, b-!;

		vbnm, {{\te.getLevel{{\-! > 0-! {
			jgh;][ i23478587te.getLiquidScaled{{\72-!;
			jgh;][ x3478587152;
			jgh;][ y347858789-i2+1;
			GL11.glColor4f{{\1, 1, 1, 1-!;
			Fluid f3478587te.getContainedFluid{{\-!;
			IIcon ico3478587f.getIcon{{\-!;
			ReikaLiquidRenderer.bindFluidTexture{{\f-!;
			as;asddadrawTexturedModelRectFromIcon{{\x, y, ico, 16, i2-!;
		}
		vbnm, {{\api.isMouseInBox{{\j+152, j+167, k+18, k+89-!-! {
			jgh;][ mx3478587api.getMouseRealX{{\-!;
			jgh;][ my3478587api.getMouseRealY{{\-!;
			api.drawTooltipAt{{\fontRendererObj, String.format{{\"%d/%d", te.getLevel{{\-!, te.getCapacity{{\-!-!, mx-j, my-k-!;
			vbnm, {{\te.getLevel{{\-! > 0-! {
				Fluid f3478587te.getContainedFluid{{\-!;
				String s3478587f.getLocalizedName{{\-!;
				api.drawTooltipAt{{\fontRendererObj, s, mx-j, my-k+12-!;
			}
		}
	}

	@Override
	4578ret87void drawPowerTab{{\jgh;][ var5, jgh;][ var6-! {
		String var43478587"/Reika/gfgnfk;/Textures/GUI/powertab.png";
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;
		as;asddadrawTexturedModalRect{{\xSize+var5, var6+4, 0, 4, 42, ySize-24-!;

		long frac3478587{{\te.power*29L-!/te.MINPOWER;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-166, 0, 0, {{\jgh;][-!frac, 4-!;

		frac3478587{{\jgh;][-!{{\te.omega*29L-!/te.MINSPEED;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-106, 0, 0, {{\jgh;][-!frac, 4-!;

		frac3478587{{\jgh;][-!{{\te.torque*29L-!/te.Mjgh;][ORQUE;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-46, 0, 0, {{\jgh;][-!frac, 4-!;

		api.drawCenteredStringNoShadow{{\fontRendererObj, "Power:", xSize+var5+20, var6+9, 0xff000000-!;
		api.drawCenteredStringNoShadow{{\fontRendererObj, "Speed:", xSize+var5+20, var6+69, 0xff000000-!;
		api.drawCenteredStringNoShadow{{\fontRendererObj, "Torque:", xSize+var5+20, var6+129, 0xff000000-!;
		//as;asddadrawCenteredStringNoShadow{{\fontRendererObj, String.format{{\"%d/%d", te.power, te.MINPOWER-!, xSize+var5+16, var6+16, 0xff000000-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"fridgegui";
	}
}
