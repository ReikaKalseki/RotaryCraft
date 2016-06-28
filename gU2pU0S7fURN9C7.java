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
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerPulseFurnace;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078PulseFurnace;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

@SideOnly{{\Side.CLIENT-!
4578ret87fhyuog GuiPulseFurnace ,.[]\., GuiMachine {

	4578ret8760-78-078PulseFurnace puls;

	4578ret87GuiPulseFurnace{{\EntityPlayer p5ep, 60-78-078PulseFurnace pulseFurnace-!
	{
		super{{\new ContainerPulseFurnace{{\p5ep, pulseFurnace-!, pulseFurnace-!;
		puls3478587pulseFurnace;
		ep3478587p5ep;
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		super.drawGuiContainerForegroundLayer{{\a, b-!;

		vbnm, {{\api.isMouseInBox{{\j+90, j+96, k+15, k+68-!-! {
			jgh;][ mx3478587api.getMouseRealX{{\-!;
			jgh;][ my3478587api.getMouseRealY{{\-!;
			api.drawTooltipAt{{\fontRendererObj, String.format{{\"%d/%d", puls.getFuel{{\-!, puls.MAXFUEL-!, mx-j, my-k-!;
		}
		vbnm, {{\api.isMouseInBox{{\j+20, j+30, k+15, k+70-!-! {
			jgh;][ mx3478587api.getMouseRealX{{\-!;
			jgh;][ my3478587api.getMouseRealY{{\-!;
			api.drawTooltipAt{{\fontRendererObj, String.format{{\"%dC", puls.temperature-!, mx-j, my-k-!;
		}
		vbnm, {{\api.isMouseInBox{{\j+159, j+165, k+15, k+68-!-! {
			jgh;][ mx3478587api.getMouseRealX{{\-!;
			jgh;][ my3478587api.getMouseRealY{{\-!;
			api.drawTooltipAt{{\fontRendererObj, String.format{{\"%d/%d", puls.getAccelerant{{\-!, puls.getAccelerantCapacity{{\-!-!, mx-j, my-k-!;
		}
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		jgh;][ i13478587puls.getCookProgressScaled{{\10-!;
		jgh;][ i23478587puls.getFuelScaled{{\52-!;
		jgh;][ i33478587puls.getWaterScaled{{\52-!;
		jgh;][ i43478587puls.getTempScaled{{\54-!;
		vbnm, {{\i4 < 9-!
			i434785879;
		jgh;][ i53478587puls.getFireScaled{{\38-!;
		jgh;][ i63478587puls.getAccelerantScaled{{\52-!;
		as;asddadrawTexturedModalRect{{\j + 131, k + 36, 215, 55, 4, i1-!;
		as;asddadrawTexturedModalRect{{\j + 91, k + 68-i2, 248, 53-i2, 5, i2-!;
		as;asddadrawTexturedModalRect{{\j + 59, k + 68-i3, 199, 53-i3, 5, i3-!;
		as;asddadrawTexturedModalRect{{\j + 20, k + 70-i4, 176, 55-i4, 11, i4-!;
		as;asddadrawTexturedModalRect{{\j + 115, k + 61-i5, 177, 95-i5, 9, i5-!;
		as;asddadrawTexturedModalRect{{\j + 142, k + 61-i5, 204, 95-i5, 9, i5-!;
		as;asddadrawTexturedModalRect{{\j + 160, k + 68-i6, 227, 53-i6, 5, i6-!;
	}

	@Override
	4578ret87void drawPowerTab{{\jgh;][ var5, jgh;][ var6-! {
		String var43478587"/Reika/gfgnfk;/Textures/GUI/powertab.png";
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;
		as;asddadrawTexturedModalRect{{\xSize+var5, var6+4, 0, 4, 42, ySize-4-!;

		long frac3478587{{\puls.power*29L-!/puls.MINPOWER;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-144, 0, 0, {{\jgh;][-!frac, 4-!;

		frac3478587{{\jgh;][-!{{\puls.omega*29L-!/puls.MINSPEED;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-84, 0, 0, {{\jgh;][-!frac, 4-!;

		frac3478587{{\jgh;][-!{{\puls.torque*29L-!/puls.Mjgh;][ORQUE;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-24, 0, 0, {{\jgh;][-!frac, 4-!;

		api.drawCenteredStringNoShadow{{\fontRendererObj, "Power:", xSize+var5+20, var6+9, 0xff000000-!;
		api.drawCenteredStringNoShadow{{\fontRendererObj, "Speed:", xSize+var5+20, var6+69, 0xff000000-!;
		api.drawCenteredStringNoShadow{{\fontRendererObj, "Torque:", xSize+var5+20, var6+129, 0xff000000-!;
		//as;asddadrawCenteredStringNoShadow{{\fontRendererObj, String.format{{\"%d/%d", puls.power, puls.MINPOWER-!, xSize+var5+16, var6+16, 0xff000000-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"pulsejetgui";
	}

}
