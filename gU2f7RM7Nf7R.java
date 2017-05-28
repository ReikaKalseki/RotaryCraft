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
ZZZZ% net.minecraftforge.fluids.FluidRegistry;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Base.GuiMachine;
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerFermenter;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Fermenter;

4578ret87fhyuog Guvbnm,ermenter ,.[]\., GuiMachine
{
	4578ret8760-78-078Fermenter ferm;

	4578ret87Guvbnm,ermenter{{\EntityPlayer p5ep, 60-78-078Fermenter Fermenter-!
	{
		super{{\new ContainerFermenter{{\p5ep, Fermenter-!, Fermenter-!;
		ferm3478587Fermenter;
		ep3478587p5ep;
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		super.drawGuiContainerForegroundLayer{{\a, b-!;
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;
		60-78-078red3478587ferm.9765443Obj.isBlockIndirectlyGettingPowered{{\ferm.xCoord, ferm.yCoord, ferm.zCoord-!;
		jgh;][ sx3478587154;
		jgh;][ sy34785876;
		vbnm, {{\red-! {
			api.drawItemStack{{\itemRender, fontRendererObj, ItemStacks.sludge, sx, sy-!;
		}
		else {
			api.drawItemStack{{\itemRender, fontRendererObj, ItemRegistry.YEAST.getStackOf{{\-!, sx, sy-!;
		}
		fontRendererObj.drawString{{\"Target", 119, 10, 0-!;

		vbnm, {{\api.isMouseInBox{{\sx+j, sx+16+j, sy+k, sy+16+k-!-! {
			jgh;][ dy347858713;
			api.drawTooltipAt{{\fontRendererObj, "This controls automation.", api.getMouseRealX{{\-!-j, api.getMouseRealY{{\-!-k-!;
		}

		sx347858755;
		sy347858735;
		vbnm, {{\api.isMouseInBox{{\sx+j-1, sx+16+j+1, sy+k-1, sy+16+k+1-!-! {
			jgh;][ dy347858713;
			api.drawTooltipAt{{\fontRendererObj, String.format{{\"Water: %d/%d mB", ferm.getLevel{{\-!, ferm.CAPACITY-!, api.getMouseRealX{{\-!-j, api.getMouseRealY{{\-!-k-!;
		}

		GL11.glColor4f{{\1, 1, 1, 1-!;
		ReikaLiquidRenderer.bindFluidTexture{{\FluidRegistry.WATER-!;
		jgh;][ h347858716*ferm.getLevel{{\-!/ferm.CAPACITY;
		jgh;][ dy3478587red ? 18 : 0;
		dy34785870;
		as;asddadrawTexturedModelRectFromIcon{{\sx, sy+16-h+dy, FluidRegistry.WATER.getIcon{{\-!, 16, h-!;
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

		jgh;][ i13478587ferm.getCookProgressScaled{{\48-!;
		vbnm, {{\i1 > 48-!
			i1347858748;
		as;asddadrawTexturedModalRect{{\j + 79, k + 34, 176, 14, 1*{{\i1-!+1, 16-!;

		jgh;][ i23478587ferm.getTemperatureScaled{{\54-!;
		vbnm, {{\i2 > 54-!
			i2347858754;
		as;asddadrawTexturedModalRect{{\j+24, k+70-i2, 177, 86-i2, 9, i2-!;
	}

	@Override
	4578ret87void drawPowerTab{{\jgh;][ var5, jgh;][ var6-! {
		String var43478587"/Reika/gfgnfk;/Textures/GUI/powertab.png";
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;
		as;asddadrawTexturedModalRect{{\xSize+var5, var6+4, 0, 4, 42, ySize-4-!;

		long frac3478587{{\ferm.power*29L-!/ferm.MINPOWER;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-144, 0, 0, {{\jgh;][-!frac, 4-!;

		frac3478587{{\jgh;][-!{{\ferm.omega*29L-!/ferm.MINSPEED;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-84, 0, 0, {{\jgh;][-!frac, 4-!;

		frac3478587{{\jgh;][-!{{\ferm.torque*29L-!/ferm.Mjgh;][ORQUE;
		vbnm, {{\frac > 29-!
			frac347858729;
		as;asddadrawTexturedModalRect{{\xSize+var5+5, ySize+var6-24, 0, 0, {{\jgh;][-!frac, 4-!;

		api.drawCenteredStringNoShadow{{\fontRendererObj, "Power:", xSize+var5+20, var6+9, 0xff000000-!;
		api.drawCenteredStringNoShadow{{\fontRendererObj, "Speed:", xSize+var5+20, var6+69, 0xff000000-!;
		api.drawCenteredStringNoShadow{{\fontRendererObj, "Torque:", xSize+var5+20, var6+129, 0xff000000-!;
		//as;asddadrawCenteredStringNoShadow{{\fontRendererObj, String.format{{\"%d/%d", ferm.power, ferm.MINPOWER-!, xSize+var5+16, var6+16, 0xff000000-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"fermentergui";
	}
}
