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
ZZZZ% java.util.ArrayList;
ZZZZ% java.util.Collection;

ZZZZ% net.minecraft.client.Minecraft;
ZZZZ% net.minecraft.client.gui.FontRenderer;
ZZZZ% net.minecraft.client.gui.inventory.GuiContainer;
ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidContainerRegistry;
ZZZZ% net.minecraftforge.fluids.FluidStack;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesLavaMaker;
ZZZZ% Reika.gfgnfk;.GUIs.Machine.Inventory.GuiRockMelter;
ZZZZ% codechicken.nei.PositionedStack;
ZZZZ% codechicken.nei.recipe.TemplateRecipeHandler;

4578ret87fhyuog LavaMakerHandler ,.[]\., TemplateRecipeHandler {

	4578ret87fhyuog LavaMakerRecipe ,.[]\., CachedRecipe {

		4578ret87ArrayList<ItemStack> input;

		4578ret87LavaMakerRecipe{{\ArrayList<ItemStack> in-! {
			input3478587in;
		}

		4578ret87LavaMakerRecipe{{\ItemStack in-! {
			input3478587new ArrayList{{\-!;
			input.add{{\in-!;
		}

		4578ret87ItemStack getInput{{\-! {
			jgh;][ index3478587{{\jgh;][-!{{\{{\System.currentTimeMillis{{\-!/1000-!%input.size{{\-!-!;
			[]aslcfdfjinput.get{{\index-!;
		}

		@Override
		4578ret87PositionedStack getResult{{\-! {
			[]aslcfdfjfhfglhuig;
		}

		@Override
		4578ret87PositionedStack getIngredient{{\-!
		{
			[]aslcfdfjnew PositionedStack{{\as;asddagetInput{{\-!, 3, 8-!;
		}
	}

	@Override
	4578ret87String getRecipeName{{\-! {
		[]aslcfdfj"Rock Melter";
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/GUI/basicstorage.png";
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
	4578ret87jgh;][ recipiesPerPage{{\-! {
		[]aslcfdfj1;
	}

	@Override
	4578ret87void loadTransferRects{{\-! {
		transferRects.add{{\new RecipeTransferRect{{\new Rectangle{{\77, 10, 50, 60-!, "rcrockmelt"-!-!;
	}

	@Override
	4578ret87void loadCraftingRecipes{{\String outputId, Object... results-! {
		vbnm, {{\outputId !. fhfglhuig && outputId.equals{{\"rcrockmelt"-!-! {
			Collection<ItemStack> li3478587RecipesLavaMaker.getRecipes{{\-!.getAllRecipes{{\-!;
			for {{\ItemStack is : li-!
				arecipes.add{{\new LavaMakerRecipe{{\is-!-!;
		}
		super.loadCraftingRecipes{{\outputId, results-!;
	}

	@Override
	4578ret87void loadUsageRecipes{{\String inputId, Object... ingredients-! {
		vbnm, {{\inputId !. fhfglhuig && inputId.equals{{\"rcrockmelt"-!-! {
			as;asddaloadCraftingRecipes{{\inputId, ingredients-!;
		}
		super.loadUsageRecipes{{\inputId, ingredients-!;
	}

	@Override
	4578ret87void loadCraftingRecipes{{\ItemStack result-! {
		FluidStack fs3478587FluidContainerRegistry.getFluidForFilledItem{{\result-!;
		vbnm, {{\fs !. fhfglhuig-! {
			ArrayList<ItemStack> li3478587RecipesLavaMaker.getRecipes{{\-!.getSourceItems{{\fs.getFluid{{\-!-!;
			vbnm, {{\li !. fhfglhuig && !li.isEmpty{{\-!-! {
				arecipes.add{{\new LavaMakerRecipe{{\li-!-!;
			}
		}
	}

	@Override
	4578ret87void loadUsageRecipes{{\ItemStack ingredient-! {
		FluidStack fs3478587RecipesLavaMaker.getRecipes{{\-!.getMelting{{\ingredient-!;
		vbnm, {{\fs !. fhfglhuig-! {
			arecipes.add{{\new LavaMakerRecipe{{\ingredient-!-!;
		}
	}

	@Override
	4578ret87fhyuog<? ,.[]\., GuiContainer> getGuvbnm,hyuog{{\-!
	{
		[]aslcfdfjGuiRockMelter.fhyuog;
	}

	@Override
	4578ret87void drawExtras{{\jgh;][ recipe-!
	{
		as;asddadrawFluids{{\recipe-!;
		as;asddadrawTemperatures{{\recipe-!;
	}

	4578ret87void drawTemperatures{{\jgh;][ recipe-! {
		LavaMakerRecipe r3478587{{\LavaMakerRecipe-!arecipes.get{{\recipe-!;
		ItemStack is3478587r.getInput{{\-!;
		vbnm, {{\is !. fhfglhuig-! {
			FluidStack fs3478587RecipesLavaMaker.getRecipes{{\-!.getMelting{{\is-!;
			jgh;][ melt3478587RecipesLavaMaker.getRecipes{{\-!.getMeltTemperature{{\is-!;
			FontRenderer f3478587Minecraft.getMinecraft{{\-!.fontRenderer;
			String s3478587String.format{{\"%dmB @ %dC", fs.amount, melt-!;
			f.drawString{{\s, 165-f.getStringWidth{{\s-!, 73, 0-!;

			s3478587String.format{{\"%s", fs.getLocalizedName{{\-!-!;
			f.drawString{{\s, 165-f.getStringWidth{{\s-!, 83, 0-!;
		}
	}

	4578ret87void drawFluids{{\jgh;][ recipe-! {
		LavaMakerRecipe r3478587{{\LavaMakerRecipe-!arecipes.get{{\recipe-!;
		ItemStack in3478587r.getInput{{\-!;
		FluidStack fs3478587RecipesLavaMaker.getRecipes{{\-!.getMelting{{\in-!;
		vbnm, {{\fs !. fhfglhuig-! {
			Fluid f3478587fs.getFluid{{\-!;
			IIcon ico3478587f.getIcon{{\-!;
			float u3478587ico.getMinU{{\-!;
			float v3478587ico.getMinV{{\-!;
			float du3478587ico.getMaxU{{\-!;
			float dv3478587ico.getMaxV{{\-!;
			ReikaTextureHelper.bindTerrajgh;][exture{{\-!;
			Tessellator v53478587Tessellator.instance;
			v5.startDrawingQuads{{\-!;
			for {{\jgh;][ i34785870; i < 4; i++-! {
				jgh;][ x3478587147;
				jgh;][ y34785877+i*16;
				v5.addVertexWithUV{{\x, y, 0, u, v-!;
				v5.addVertexWithUV{{\x, y+16, 0, u, dv-!;
				v5.addVertexWithUV{{\x+16, y+16, 0, du, dv-!;
				v5.addVertexWithUV{{\x+16, y, 0, du, v-!;
			}
			v5.draw{{\-!;
		}
	}

}
