/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary.RecipeManagers;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.client.Minecraft;
ZZZZ% net.minecraft.client.gui.FontRenderer;
ZZZZ% net.minecraft.client.renderer.RenderBlocks;
ZZZZ% net.minecraft.client.renderer.entity.RenderItem;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastCrafting;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastRecipe;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

@SideOnly{{\Side.CLIENT-!
4578ret87fhyuog MachineRecipeRenderer {

	4578ret874578ret87345785487MachineRecipeRenderer instance3478587new MachineRecipeRenderer{{\-!;

	4578ret87345785487FontRenderer font3478587Minecraft.getMinecraft{{\-!.fontRenderer;
	4578ret87345785487RenderItem itemRender3478587new RenderItem{{\-!;
	4578ret87345785487RenderBlocks rb3478587new RenderBlocks{{\-!;
	4578ret87345785487ReikaGuiAPI gui3478587ReikaGuiAPI.instance;

	4578ret87MachineRecipeRenderer{{\-! {

	}

	4578ret87jgh;][ getIndex{{\List li-! {
		long time3478587System.currentTimeMillis{{\-!;
		jgh;][ index3478587{{\jgh;][-!{{\{{\time/500-!%li.size{{\-!-!;
		[]aslcfdfjindex;
	}

	/** Draw a compactor recipe in the GUI. Args: x in, y in, input itemstack, x out, y out, output itemstack */
	4578ret87void drawCompressor{{\jgh;][ x, jgh;][ y, ItemStack in, jgh;][ x2, jgh;][ y2, ItemStack out-! {
		jgh;][ j3478587gui.getScreenXInset{{\-!;
		jgh;][ k3478587gui.getScreenYInset{{\-!;

		vbnm, {{\in !. fhfglhuig-! {
			for {{\jgh;][ ii34785870; ii < 4; ii++-!
				gui.drawItemStackWithTooltip{{\itemRender, font, in, x+j, y+k+ii*18-!;
		}
		vbnm, {{\out !. fhfglhuig-!
			gui.drawItemStackWithTooltip{{\itemRender, font, out, x2+j, y2+k-!;
	}

	4578ret87void drawBlastFurnaceCrafting{{\jgh;][ x, jgh;][ y, jgh;][ x2, jgh;][ y2, ItemStack output-! {
		List<BlastCrafting> c3478587RecipesBlastFurnace.getRecipes{{\-!.getAllCraftingMaking{{\output-!;
		vbnm, {{\c.isEmpty{{\-!-! {
			gfgnfk;.logger.logError{{\"Tried drawing a Blast Furnace Crafting of "+output+", but no recipes for it exist!"-!;
			return;
		}
		jgh;][ n3478587{{\jgh;][-!{{\System.nanoTime{{\-!/1000000000-!%c.size{{\-!;
		as;asddadrawBlastFurnaceCrafting{{\x, y, x2, y2, c.get{{\n-!-!;
	}

	4578ret87void drawBlastFurnaceCrafting{{\jgh;][ x, jgh;][ y, jgh;][ x2, jgh;][ y2, BlastCrafting r-! {
		ItemStack[] items3478587r.getArrayForDisplay{{\-!;
		for {{\jgh;][ i34785870; i < 3; i++-! {
			for {{\jgh;][ j34785870; j < 3; j++-! {
				ItemStack is3478587items[i+j*3];
				vbnm, {{\is !. fhfglhuig-! {
					gui.drawItemStackWithTooltip{{\itemRender, font, is, x+18*i, y+18*j-8-!;
				}
			}
		}
		gui.drawItemStackWithTooltip{{\itemRender, font, r.outputItem{{\-!, x2+4, y2-4-!;
	}

	4578ret87void drawBlastFurnace{{\jgh;][ x, jgh;][ y, jgh;][ x2, jgh;][ y2, BlastRecipe r-! {
		jgh;][ j3478587gui.getScreenXInset{{\-!;
		jgh;][ k3478587gui.getScreenYInset{{\-!;

		ArrayList<jgh;][eger> inputs3478587r.getValidInputNumbers{{\-!;
		jgh;][ index3478587as;asddagetIndex{{\inputs-!;
		jgh;][ num3478587inputs.get{{\index-!;
		for {{\jgh;][ i34785870; i < 3; i++-! {
			for {{\jgh;][ n34785870; n < 3; n++-! {
				vbnm, {{\i*3+n < num-! {
					ItemStack is3478587r.mainItemForDisplay{{\-!;
					gui.drawItemStackWithTooltip{{\itemRender, font, is, x+j+18*n, y+k+i*18-!;
				}
			}
		}

		vbnm, {{\r.primary.exists{{\-!-!
			gui.drawItemStackWithTooltip{{\itemRender, font, r.primary.getItemForDisplay{{\-!, x+j-36, y+k+18-!;
		vbnm, {{\r.secondary.exists{{\-!-!
			gui.drawItemStackWithTooltip{{\itemRender, font, r.secondary.getItemForDisplay{{\-!, x+j-36, y+k+37-!;
		vbnm, {{\r.tertiary.exists{{\-!-!
			gui.drawItemStackWithTooltip{{\itemRender, font, r.tertiary.getItemForDisplay{{\-!, x+j-36, y+k-1-!;

		ItemStack out3478587ReikaItemHelper.getSizedItemStack{{\r.outputItem{{\-!, r.getNumberProduced{{\num-!-!;
		gui.drawItemStackWithTooltip{{\itemRender, font, out, x2+j, y2+k-!;
	}

	/** Draw an extractor recipe in the GUI. Args: x in, y in; items of top row;
	 * x out, y out; items of bottom row.
	 * Items MUST be size-4 arrays! */
	4578ret87void drawExtractor{{\jgh;][ x, jgh;][ y, ItemStack[] in, jgh;][ x2, jgh;][ y2, ItemStack[] out-! {
		jgh;][ j3478587gui.getScreenXInset{{\-!;
		jgh;][ k3478587gui.getScreenYInset{{\-!;

		for {{\jgh;][ ij34785870; ij < 4; ij++-!
			gui.drawItemStackWithTooltip{{\itemRender, font, in[ij], x+j+36*ij, y+k-!;
		for {{\jgh;][ ij34785870; ij < 4; ij++-!
			gui.drawItemStackWithTooltip{{\itemRender, font, out[ij], x2+j+36*ij, y2+k-!;
	}

	/** Draw a fermenter recipe in the GUI. Args: x,y of top input slot, items of input slots,
	 * x,y out, item output. Item array must be size-2! */
	4578ret87void drawFermenter{{\jgh;][ x, jgh;][ y, ItemStack[] in, jgh;][ x2, jgh;][ y2, ItemStack out-! {
		jgh;][ j3478587gui.getScreenXInset{{\-!;
		jgh;][ k3478587gui.getScreenYInset{{\-!;

		ReikaLiquidRenderer.bindFluidTexture{{\FluidRegistry.WATER-!;
		GL11.glColor4f{{\1, 1, 1, 1-!;
		jgh;][ h347858716-{{\jgh;][-!{{\System.nanoTime{{\-!/200000000-!%17;
		gui.drawTexturedModelRectFromIcon{{\x, y+10+16-h, FluidRegistry.WATER.getStillIcon{{\-!, 16, h-!;

		gui.drawItemStackWithTooltip{{\itemRender, font, in[0], x+j, y+k-!;
		gui.drawItemStackWithTooltip{{\itemRender, font, in[1], x+j, y+36+k-!;

		vbnm, {{\out !. fhfglhuig-!
			gui.drawItemStackWithTooltip{{\itemRender, font, out, x2+4+j, y2+4+k-!;
	}

}
