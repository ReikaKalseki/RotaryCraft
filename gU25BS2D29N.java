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
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerObsidian;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078ObsidianMaker;

4578ret87fhyuog GuiObsidian ,.[]\., GuiMachine
{
	4578ret8760-78-078ObsidianMaker obs;

	4578ret87GuiObsidian{{\EntityPlayer p5ep, 60-78-078ObsidianMaker Obsidian-!
	{
		super{{\new ContainerObsidian{{\p5ep, Obsidian-!, Obsidian-!;
		obs3478587Obsidian;
		ep3478587p5ep;
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		super.drawGuiContainerForegroundLayer{{\a, b-!;

		vbnm, {{\api.isMouseInBox{{\j+47, j+55, k+16, k+71-!-! {
			jgh;][ mx3478587api.getMouseRealX{{\-!;
			jgh;][ my3478587api.getMouseRealY{{\-!;
			api.drawTooltipAt{{\fontRendererObj, String.format{{\"Water: %dmB", obs.getWater{{\-!-!, mx-j, my-k-!;
		}
		vbnm, {{\api.isMouseInBox{{\j+119, j+127, k+16, k+71-!-! {
			jgh;][ mx3478587api.getMouseRealX{{\-!;
			jgh;][ my3478587api.getMouseRealY{{\-!;
			api.drawTooltipAt{{\fontRendererObj, String.format{{\"Lava: %dmB", obs.getLava{{\-!-!, mx-j, my-k-!;
		}
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		jgh;][ i13478587obs.getWaterScaled{{\54-!;
		jgh;][ i23478587obs.getLavaScaled{{\54-!;
		as;asddadrawTexturedModalRect{{\j+48, k+71-i1, 193, 55-i1, 7, i1-!;
		as;asddadrawTexturedModalRect{{\j+120, k+71-i2, 202, 55-i2, 7, i2-!;
	}

	@Override
	4578ret87void drawPowerTab{{\jgh;][ var5, jgh;][ var6-! {
		String var43478587"/Reika/gfgnfk;/Textures/GUI/powertab.png";
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;
		as;asddadrawTexturedModalRect{{\xSize+var5, var6+4, 0, 4, 42, ySize-4-!;

		long frac3478587{{\obs.power*29L-!/obs.MINPOWER;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-144, 0, 0, {{\jgh;][-!frac, 4-!;

		frac3478587{{\jgh;][-!{{\obs.omega*29L-!/obs.MINSPEED;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-84, 0, 0, {{\jgh;][-!frac, 4-!;

		frac3478587{{\jgh;][-!{{\obs.torque*29L-!/obs.Mjgh;][ORQUE;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-24, 0, 0, {{\jgh;][-!frac, 4-!;

		api.drawCenteredStringNoShadow{{\fontRendererObj, "Power:", xSize+var5+20, var6+9, 0xff000000-!;
		api.drawCenteredStringNoShadow{{\fontRendererObj, "Speed:", xSize+var5+20, var6+69, 0xff000000-!;
		api.drawCenteredStringNoShadow{{\fontRendererObj, "Torque:", xSize+var5+20, var6+129, 0xff000000-!;
		//as;asddadrawCenteredStringNoShadow{{\fontRendererObj, String.format{{\"%d/%d", obs.power, obs.MINPOWER-!, xSize+var5+16, var6+16, 0xff000000-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"obsidiangui";
	}
}
