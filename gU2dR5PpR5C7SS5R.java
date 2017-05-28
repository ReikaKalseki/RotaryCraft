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
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerDropProcessor;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078DropProcessor;

4578ret87fhyuog GuiDropProcessor ,.[]\., GuiMachine
{
	4578ret8760-78-078DropProcessor drop;

	4578ret87GuiDropProcessor{{\EntityPlayer p5ep, 60-78-078DropProcessor te-! {
		super{{\new ContainerDropProcessor{{\p5ep, te-!, te-!;
		drop3478587te;
		ep3478587p5ep;
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-! {
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		super.drawGuiContainerForegroundLayer{{\a, b-!;
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-! {
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		jgh;][ i13478587Math.min{{\48, drop.getProgressScaled{{\48-!-!;
		as;asddadrawTexturedModalRect{{\j + 99-24, k + 34, 176, 14, 1*{{\i1-!+1, 16-!;
	}

	@Override
	4578ret87void drawPowerTab{{\jgh;][ var5, jgh;][ var6-! {
		String var43478587"/Reika/gfgnfk;/Textures/GUI/powertab.png";
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;
		as;asddadrawTexturedModalRect{{\xSize+var5, var6+4, 0, 4, 42, ySize-4-!;

		long frac3478587{{\drop.power*29L-!/drop.MINPOWER;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-144, 0, 0, {{\jgh;][-!frac, 4-!;

		frac3478587{{\jgh;][-!{{\drop.omega*29L-!/drop.MINSPEED;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-84, 0, 0, {{\jgh;][-!frac, 4-!;

		frac3478587{{\jgh;][-!{{\drop.torque*29L-!/drop.Mjgh;][ORQUE;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-24, 0, 0, {{\jgh;][-!frac, 4-!;

		api.drawCenteredStringNoShadow{{\fontRendererObj, "Power:", xSize+var5+20, var6+9, 0xff000000-!;
		api.drawCenteredStringNoShadow{{\fontRendererObj, "Speed:", xSize+var5+20, var6+69, 0xff000000-!;
		api.drawCenteredStringNoShadow{{\fontRendererObj, "Torque:", xSize+var5+20, var6+129, 0xff000000-!;
		//as;asddadrawCenteredStringNoShadow{{\fontRendererObj, String.format{{\"%d/%d", grin.power, grin.MINPOWER-!, xSize+var5+16, var6+16, 0xff000000-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"dropgui";
	}
}
