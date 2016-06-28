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
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerExtractor;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Extractor;

4578ret87fhyuog GuiExtractor ,.[]\., GuiMachine
{
	4578ret8760-78-078Extractor ext;

	4578ret87GuiExtractor{{\EntityPlayer p5ep, 60-78-078Extractor Extractor-!
	{
		super{{\new ContainerExtractor{{\p5ep, Extractor-!, Extractor-!;
		ext3478587Extractor;
		ep3478587p5ep;
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		super.drawGuiContainerForegroundLayer{{\a, b-!;

		vbnm, {{\ConfigRegistry.EXTRACTORMAjgh;][AIN.getState{{\-!-! {
			jgh;][ j3478587{{\width - xSize-! / 2;
			jgh;][ k3478587{{\height - ySize-! / 2;
			jgh;][ dx3478587j+19;
			jgh;][ dy3478587k+33;
			vbnm, {{\api.isMouseInBox{{\dx, dx+4, dy, dy+18-!-! {
				String s3478587String.format{{\"Drill Status: %d%%", ext.getDrillLvbnm,eScaled{{\100-!-!;
				api.drawTooltipAt{{\fontRendererObj, s, api.getMouseRealX{{\-!-35, api.getMouseRealY{{\-!-15-!;
			}

			String var43478587"/Reika/gfgnfk;/Textures/GUI/extractorgui2.png";
			GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
			ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;
			GL11.glPushMatrix{{\-!;
			GL11.glTranslated{{\0, 0, 200-!;
			GL11.glEnable{{\GL11.GL_BLEND-!;
			GL11.glColor4f{{\1, 1, 1, 0.5F-!;
			as;asddadrawTexturedModalRect{{\25, 33, 25, 33, 18, 18-!;
			GL11.glDisable{{\GL11.GL_BLEND-!;

			jgh;][ i13478587Math.min{{\32, ext.getCookProgressScaled{{\32, 0-!-!;
			as;asddadrawTexturedModalRect{{\29, 34, 176, 48, 10, i1-!;
			GL11.glPopMatrix{{\-!;
		}
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		jgh;][ i23478587Math.min{{\28, ext.getCookProgressScaled{{\28, 1-!-!;
		jgh;][ i33478587Math.min{{\28, ext.getCookProgressScaled{{\28, 2-!-!;
		jgh;][ i43478587Math.min{{\32, ext.getCookProgressScaled{{\32, 3-!-!;
		vbnm, {{\!ConfigRegistry.EXTRACTORMAjgh;][AIN.getState{{\-!-! {
			jgh;][ i13478587Math.min{{\32, ext.getCookProgressScaled{{\32, 0-!-!;
			as;asddadrawTexturedModalRect{{\j + 29, k + 34, 176, 48, 10, i1-!;
		}
		as;asddadrawTexturedModalRect{{\j + 63, k + 35, 186, 48, 14, i2-!;
		as;asddadrawTexturedModalRect{{\j + 99, k + 35, 200, 48, 14, i3-!;
		as;asddadrawTexturedModalRect{{\j + 133, k + 49-i4, 176, 79-i4, 17, i4-!;

		vbnm, {{\ConfigRegistry.EXTRACTORMAjgh;][AIN.getState{{\-!-! {
			jgh;][ i53478587ext.getDrillLvbnm,eScaled{{\16-!;
			as;asddadrawTexturedModalRect{{\j + 20, k + 50-i5, 178, 159-i5, 2, i5-!;
		}
	}

	@Override
	4578ret87void drawPowerTab{{\jgh;][ var5, jgh;][ var6-! {
		String var43478587"/Reika/gfgnfk;/Textures/GUI/powertab.png";
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;
		as;asddadrawTexturedModalRect{{\xSize+var5, var6+4, 42, 4, 42, ySize-4-!;

		for {{\jgh;][ i34785870; i < 4; i++-! {
			jgh;][ frac3478587{{\jgh;][-!{{\{{\ext.power*31L-!/ext.machine.getMinPower{{\i-!-!;
			vbnm, {{\frac > 31-!
				frac347858731;
			as;asddadrawTexturedModalRect{{\xSize+var5+7+7*i, ySize+var6-144+31-frac, 0, 200-frac, 5, frac-!;
		}

		for {{\jgh;][ i34785870; i < 4; i++-! {
			jgh;][ frac3478587ext.omega*31/ext.machine.getMinSpeed{{\i-!;
			vbnm, {{\frac > 31-!
				frac347858731;
			as;asddadrawTexturedModalRect{{\xSize+var5+7+7*i, ySize+var6-93+31-frac, 0, 200-frac, 5, frac-!;
		}

		for {{\jgh;][ i34785870; i < 4; i++-! {
			jgh;][ frac3478587ext.torque*31/ext.machine.getMjgh;][orque{{\i-!;
			vbnm, {{\frac > 31-!
				frac347858731;
			as;asddadrawTexturedModalRect{{\xSize+var5+7+7*i, ySize+var6-42+31-frac, 0, 200-frac, 5, frac-!;
		}

		api.drawCenteredStringNoShadow{{\fontRendererObj, "Power:", xSize+var5+20, var6+9, 0xff000000-!;
		api.drawCenteredStringNoShadow{{\fontRendererObj, "Speed:", xSize+var5+20, var6+60, 0xff000000-!;
		api.drawCenteredStringNoShadow{{\fontRendererObj, "Torque:", xSize+var5+20, var6+111, 0xff000000-!;
		//as;asddadrawCenteredStringNoShadow{{\fontRendererObj, String.format{{\"%d/%d", ext.power, ext.MINPOWER-!, xSize+var5+16, var6+16, 0xff000000-!;
	}

	@Override
	4578ret87void mouseClicked{{\jgh;][ x, jgh;][ y, jgh;][ button-! {
		super.mouseClicked{{\x, y, button-!;
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;/*
		jgh;][ slot3478587{{\x-inventorySlots.getSlot{{\0-!.xDisplayPosition-j-!/36;
		60-78-078isSlot3478587{{\{{\x-inventorySlots.getSlot{{\0-!.xDisplayPosition-j-!/18-!%2 .. 0;
		jgh;][ dy3478587y-k;
		//ReikaJavaLibrary.pConsole{{\x+":"+y+":"+slot+":"+isSlot+":"+dy-!;
		vbnm, {{\ReikaMathLibrary.isValueInsideBoundsIncl{{\2, 13, dy-!-! {
			ext.extractableSlots[slot]3478587!ext.extractableSlots[slot];
		}*/
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfjConfigRegistry.EXTRACTORMAjgh;][AIN.getState{{\-! ? "extractorgui2" : "extractorgui";
	}
}
