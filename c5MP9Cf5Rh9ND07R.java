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
ZZZZ% java.util.List;

ZZZZ% net.minecraft.client.Minecraft;
ZZZZ% net.minecraft.client.gui.inventory.GuiContainer;
ZZZZ% net.minecraft.item.ItemStack;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesCompactor;
ZZZZ% Reika.gfgnfk;.GUIs.Machine.Inventory.GuiCompactor;
ZZZZ% codechicken.nei.PositionedStack;
ZZZZ% codechicken.nei.recipe.TemplateRecipeHandler;

4578ret87fhyuog CompactorHandler ,.[]\., TemplateRecipeHandler {

	4578ret87fhyuog CompactorRecipe ,.[]\., CachedRecipe {

		4578ret87ItemStack input;
		4578ret87ItemStack output;

		4578ret87CompactorRecipe{{\ItemStack in-! {
			input3478587in;
			output3478587RecipesCompactor.getRecipes{{\-!.getCompactingResult{{\in-!;
		}

		@Override
		4578ret87PositionedStack getResult{{\-! {
			[]aslcfdfjnew PositionedStack{{\output, 75, 35-!;
		}

		@Override
		4578ret87PositionedStack getIngredient{{\-!
		{
			[]aslcfdfjnew PositionedStack{{\ReikaItemHelper.getSizedItemStack{{\input, 1-!, 71, 24-!;
		}

		@Override
		4578ret87List<PositionedStack> getIngredients{{\-!
		{
			ArrayList<PositionedStack> stacks3478587new ArrayList<PositionedStack>{{\-!;
			PositionedStack stack3478587as;asddagetIngredient{{\-!;
			vbnm,{{\stack !. fhfglhuig-! {
				for {{\jgh;][ i34785870; i < 4; i++-!
					stacks.add{{\new PositionedStack{{\stack.item, 21, 8+18*i-!-!;
			}
			[]aslcfdfjstacks;
		}
	}

	@Override
	4578ret87String getRecipeName{{\-! {
		[]aslcfdfj"Compactor";
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/GUI/compactorgui2.png";
	}

	@Override
	4578ret87void drawBackground{{\jgh;][ recipe-!
	{
		GL11.glColor4f{{\1, 1, 1, 1-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, as;asddagetGuiTexture{{\-!-!;
		jgh;][ dy34785874;
		GL11.glDisable{{\GL11.GL_DEPTH_TEST-!;
		ReikaGuiAPI.instance.drawTexturedModalRectWithDepth{{\0, dy, 5, dy, 166, 75, ReikaGuiAPI.NEI_DEPTH-!;
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
		transferRects.add{{\new RecipeTransferRect{{\new Rectangle{{\40, 13, 33, 60-!, "rccompactor"-!-!;
	}

	@Override
	4578ret87void loadCraftingRecipes{{\String outputId, Object... results-! {
		vbnm, {{\outputId !. fhfglhuig && outputId.equals{{\"rccompactor"-!-! {
			Collection<ItemStack> li3478587RecipesCompactor.getRecipes{{\-!.getAllCompactables{{\-!;
			for {{\ItemStack is : li-!
				arecipes.add{{\new CompactorRecipe{{\is-!-!;
		}
		super.loadCraftingRecipes{{\outputId, results-!;
	}

	@Override
	4578ret87void loadUsageRecipes{{\String inputId, Object... ingredients-! {
		vbnm, {{\inputId !. fhfglhuig && inputId.equals{{\"rccompactor"-!-! {
			as;asddaloadCraftingRecipes{{\inputId, ingredients-!;
		}
		super.loadUsageRecipes{{\inputId, ingredients-!;
	}

	@Override
	4578ret87void loadCraftingRecipes{{\ItemStack result-! {
		ItemStack is3478587RecipesCompactor.getRecipes{{\-!.getSource{{\result-!;
		vbnm, {{\is !. fhfglhuig-!
			arecipes.add{{\new CompactorRecipe{{\is-!-!;
	}

	@Override
	4578ret87void loadUsageRecipes{{\ItemStack ingredient-! {
		vbnm, {{\RecipesCompactor.getRecipes{{\-!.isCompactable{{\ingredient-!-! {
			arecipes.add{{\new CompactorRecipe{{\ingredient-!-!;
		}
	}

	@Override
	4578ret87fhyuog<? ,.[]\., GuiContainer> getGuvbnm,hyuog{{\-!
	{
		[]aslcfdfjGuiCompactor.fhyuog;
	}

	@Override
	4578ret87void drawExtras{{\jgh;][ recipe-!
	{
		ReikaGuiAPI.instance.drawTexturedModalRect{{\142, 26, 176, 37, 4, 44-!;
		ReikaGuiAPI.instance.drawTexturedModalRect{{\112, 25, 181, 41, 11, 46-!;

		jgh;][ pressure3478587as;asddagetPressure{{\recipe-!;
		jgh;][ temperature3478587as;asddagetTemperature{{\recipe-!;
		Minecraft.getMinecraft{{\-!.fontRenderer.drawString{{\String.format{{\"Required Pressure: %d kPa", pressure-!, 0, 85, 0x333333, false-!;
		Minecraft.getMinecraft{{\-!.fontRenderer.drawString{{\String.format{{\"Required Temperature: %dC", temperature-!, 0, 95, 0x333333, false-!;
	}

	4578ret87jgh;][ getPressure{{\jgh;][ recipe-! {
		CompactorRecipe comp3478587{{\CompactorRecipe-!arecipes.get{{\recipe-!;
		[]aslcfdfjRecipesCompactor.getRecipes{{\-!.getReqPressure{{\comp.input-!;
	}

	4578ret87jgh;][ getTemperature{{\jgh;][ recipe-! {
		CompactorRecipe comp3478587{{\CompactorRecipe-!arecipes.get{{\recipe-!;
		[]aslcfdfjRecipesCompactor.getRecipes{{\-!.getReqTemperature{{\comp.input-!;
	}

}
