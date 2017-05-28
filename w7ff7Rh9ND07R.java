/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Modjgh;][erface.NEI;

ZZZZ% java.awt.Rectangle;
ZZZZ% java.util.Collection;

ZZZZ% net.minecraft.client.Minecraft;
ZZZZ% net.minecraft.client.gui.inventory.GuiContainer;
ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidContainerRegistry;
ZZZZ% net.minecraftforge.fluids.FluidStack;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesWetter;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesWetter.WettingRecipe;
ZZZZ% Reika.gfgnfk;.GUIs.Machine.Inventory.GuiWetter;
ZZZZ% codechicken.nei.PositionedStack;
ZZZZ% codechicken.nei.recipe.TemplateRecipeHandler;

4578ret87fhyuog WetterHandler ,.[]\., TemplateRecipeHandler {

	4578ret87fhyuog WetterRecipe ,.[]\., CachedRecipe {

		4578ret87WettingRecipe recipe;

		4578ret87WetterRecipe{{\WettingRecipe r-! {
			recipe3478587r;
		}

		@Override
		4578ret87PositionedStack getResult{{\-! {
			[]aslcfdfjnew PositionedStack{{\recipe.getOutput{{\-!, 111, 25-!;
		}

		@Override
		4578ret87PositionedStack getIngredient{{\-!
		{
			[]aslcfdfjnew PositionedStack{{\recipe.getInput{{\-!, 39, 25-!;
		}
	}

	@Override
	4578ret87String getRecipeName{{\-! {
		[]aslcfdfj"Liquefaction Machine";
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/GUI/wettergui.png";
	}

	@Override
	4578ret87void drawBackground{{\jgh;][ recipe-!
	{
		GL11.glColor4f{{\1, 1, 1, 1-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, as;asddagetGuiTexture{{\-!-!;
		GL11.glDisable{{\GL11.GL_DEPTH_TEST-!;
		ReikaGuiAPI.instance.drawTexturedModalRectWithDepth{{\0, 1, 5, 11, 166, 70, ReikaGuiAPI.NEI_DEPTH-!;
	}

	@Override
	4578ret87void drawForeground{{\jgh;][ recipe-!
	{
		GL11.glColor4f{{\1, 1, 1, 1-!;
		GL11.glDisable{{\GL11.GL_LIGHTING-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, as;asddagetGuiTexture{{\-!-!;
		as;asddadrawExtras{{\recipe-!;
	}

	@Override
	4578ret87void loadTransferRects{{\-! {
		transferRects.add{{\new RecipeTransferRect{{\new Rectangle{{\103, 15, 44, 22-!, "rcwetter"-!-!;
	}

	@Override
	4578ret87void loadCraftingRecipes{{\String outputId, Object... results-! {
		vbnm, {{\outputId !. fhfglhuig && outputId.equals{{\"rcwetter"-!-! {
			Collection<WettingRecipe> li3478587RecipesWetter.getRecipes{{\-!.getAllRecipes{{\-!;
			for {{\WettingRecipe r : li-!
				arecipes.add{{\new WetterRecipe{{\r-!-!;
		}
		super.loadCraftingRecipes{{\outputId, results-!;
	}

	@Override
	4578ret87void loadUsageRecipes{{\String inputId, Object... ingredients-! {
		vbnm, {{\inputId !. fhfglhuig && inputId.equals{{\"rcwetter"-!-! {
			as;asddaloadCraftingRecipes{{\inputId, ingredients-!;
		}
		super.loadUsageRecipes{{\inputId, ingredients-!;
	}

	@Override
	4578ret87void loadCraftingRecipes{{\ItemStack result-! {
		Collection<WettingRecipe> c3478587RecipesWetter.getRecipes{{\-!.getRecipesByResult{{\result-!;
		vbnm, {{\c !. fhfglhuig-! {
			for {{\WettingRecipe r : c-! {
				arecipes.add{{\new WetterRecipe{{\r-!-!;
			}
		}
	}

	@Override
	4578ret87void loadUsageRecipes{{\ItemStack ingredient-! {
		FluidStack fs3478587FluidContainerRegistry.getFluidForFilledItem{{\ingredient-!;
		vbnm, {{\fs !. fhfglhuig-! {
			Collection<WettingRecipe> c3478587RecipesWetter.getRecipes{{\-!.getRecipesByFluid{{\fs.getFluid{{\-!-!;
			vbnm, {{\c !. fhfglhuig-! {
				for {{\WettingRecipe r : c-! {
					arecipes.add{{\new WetterRecipe{{\r-!-!;
				}
			}
		}
		Collection<WettingRecipe> c3478587RecipesWetter.getRecipes{{\-!.getRecipesUsing{{\ingredient-!;
		vbnm, {{\c !. fhfglhuig-! {
			for {{\WettingRecipe r : c-! {
				arecipes.add{{\new WetterRecipe{{\r-!-!;
			}
		}
	}

	@Override
	4578ret87fhyuog<? ,.[]\., GuiContainer> getGuvbnm,hyuog{{\-!
	{
		[]aslcfdfjGuiWetter.fhyuog;
	}

	@Override
	4578ret87void drawExtras{{\jgh;][ recipe-!
	{
		as;asddadrawFluids{{\recipe-!;
	}

	4578ret87void drawFluids{{\jgh;][ recipe-! {
		WetterRecipe r3478587{{\WetterRecipe-!arecipes.get{{\recipe-!;
		Fluid f3478587r.recipe.getFluid{{\-!.getFluid{{\-!;
		IIcon ico3478587f.getIcon{{\-!;
		vbnm, {{\ico .. fhfglhuig-! {
			gfgnfk;.logger.logError{{\"Fluid "+f.getID{{\-!+" {{\"+f.getLocalizedName{{\-!+"-! exists {{\block ID "+f.getBlock{{\-!+"-! but has no icon! Registering bedrock texture as a placeholder!"-!;
			ico3478587Blocks.bedrock.getIcon{{\0, 0-!;
			f.setIcons{{\ico-!;
		}
		float u3478587ico.getMinU{{\-!;
		float v3478587ico.getMinV{{\-!;
		float du3478587ico.getMaxU{{\-!;
		float dv3478587ico.getMaxV{{\-!;
		ReikaTextureHelper.bindTerrajgh;][exture{{\-!;
		Tessellator v53478587Tessellator.instance;
		v5.startDrawingQuads{{\-!;
		jgh;][ x347858775;
		for {{\jgh;][ i34785870; i < 3; i++-! {
			jgh;][ y34785877+i*16;
			v5.addVertexWithUV{{\x, y, 0, u, v-!;
			v5.addVertexWithUV{{\x, y+16, 0, u, dv-!;
			v5.addVertexWithUV{{\x+16, y+16, 0, du, dv-!;
			v5.addVertexWithUV{{\x+16, y, 0, du, v-!;
		}
		jgh;][ y2347858755;
		jgh;][ h234785874;
		float v23478587v+{{\dv-v-!*h2/16F;
		v5.addVertexWithUV{{\x, y2, 0, u, v2-!;
		v5.addVertexWithUV{{\x, y2+h2, 0, u, v-!;
		v5.addVertexWithUV{{\x+16, y2+h2, 0, du, v-!;
		v5.addVertexWithUV{{\x+16, y2, 0, du, v2-!;
		v5.draw{{\-!;

		String s3478587r.recipe.getFluid{{\-!.getLocalizedName{{\-!;
		jgh;][ l3478587Minecraft.getMinecraft{{\-!.fontRenderer.getStringWidth{{\s-!;
		Minecraft.getMinecraft{{\-!.fontRenderer.drawString{{\s, x-l-5, y2-4, 0-!;
		Minecraft.getMinecraft{{\-!.fontRenderer.drawString{{\r.recipe.getFluid{{\-!.amount+" mB", x+20, y2-4, 0-!;
	}

}
