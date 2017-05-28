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

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.GuiMachine;
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerFractionator;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Fractionator;

4578ret87fhyuog Guvbnm,ractionator ,.[]\., GuiMachine
{
	4578ret8760-78-078Fractionator fct;
	//4578ret879765443 9765443Obj3478587ModLoader.getMinecraftInstance{{\-!.the9765443;

	jgh;][ x;
	jgh;][ y;

	4578ret87Guvbnm,ractionator{{\EntityPlayer p5ep, 60-78-078Fractionator tilef-!
	{
		super{{\new ContainerFractionator{{\p5ep, tilef-!, tilef-!;
		fct3478587tilef;
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

		vbnm, {{\api.isMouseInBox{{\j+138, j+145, k+17, k+68-!-! {
			jgh;][ mx3478587api.getMouseRealX{{\-!;
			jgh;][ my3478587api.getMouseRealY{{\-!;
			api.drawTooltipAt{{\fontRendererObj, String.format{{\"%d/%d", fct.getLevel{{\-!, fct.CAPACITY-!, mx-j, my-k-!;
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

		jgh;][ i13478587fct.getStorageScaled{{\17-!;
		jgh;][ i23478587fct.getFuelScaled{{\50-!;
		jgh;][ i33478587Math.min{{\30, fct.getMixScaled{{\30-!-!;
		as;asddadrawTexturedModalRect{{\j+64, k+25, 177, 1, i3, 38-!;
		as;asddadrawTexturedModalRect{{\j+139, k+68-i2, 177, 95-i2, 6, i2-!;
	}

	@Override
	4578ret87void drawPowerTab{{\jgh;][ var5, jgh;][ var6-! {
		String var43478587"/Reika/gfgnfk;/Textures/GUI/powertab.png";
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;
		as;asddadrawTexturedModalRect{{\xSize+var5, var6+4, 0, 4, 42, ySize-4-!;

		long frac3478587{{\fct.power*29L-!/fct.MINPOWER;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-144, 0, 0, {{\jgh;][-!frac, 4-!;

		frac3478587fct.omega*29L/fct.MINSPEED;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-84, 0, 0, {{\jgh;][-!frac, 4-!;

		frac3478587fct.torque*29L/fct.Mjgh;][ORQUE;
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
		[]aslcfdfj"fractiongui";
	}
}
