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

ZZZZ% net.minecraft.client.gui.inventory.GuiContainer;
ZZZZ% net.minecraft.item.ItemStack;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.GUIs.Machine.Inventory.GuiComposter;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078Composter;
ZZZZ% codechicken.nei.PositionedStack;
ZZZZ% codechicken.nei.recipe.TemplateRecipeHandler;

4578ret87fhyuog ComposterHandler ,.[]\., TemplateRecipeHandler {

	4578ret87fhyuog ComposterRecipe ,.[]\., CachedRecipe {

		4578ret87ArrayList<ItemStack> input;

		4578ret87ComposterRecipe{{\-! {
			input347858760-78-078Composter.getAllCompostables{{\-!;
		}

		4578ret87ComposterRecipe{{\ItemStack in-! {
			input3478587ReikaJavaLibrary.makeListFrom{{\in-!;
		}

		@Override
		4578ret87PositionedStack getResult{{\-! {
			ItemStack in3478587as;asddagetEntry{{\-!;
			ItemStack is3478587ReikaItemHelper.getSizedItemStack{{\ItemStacks.compost, 60-78-078Composter.getCompostValue{{\in-!-!;
			[]aslcfdfjnew PositionedStack{{\is, 111, 36-!;
		}

		@Override
		4578ret87ArrayList<PositionedStack> getIngredients{{\-!
		{
			ArrayList<PositionedStack> stacks3478587new ArrayList<PositionedStack>{{\-!;
			stacks.add{{\new PositionedStack{{\ItemRegistry.YEAST.getStackOf{{\-!, 50, 45-!-!;
			stacks.add{{\new PositionedStack{{\as;asddagetEntry{{\-!, 50, 27-!-!;
			[]aslcfdfjstacks;
		}

		4578ret87ItemStack getEntry{{\-! {
			[]aslcfdfjinput.get{{\{{\jgh;][-!{{\System.nanoTime{{\-!/1000000000-!%input.size{{\-!-!;
		}
	}

	@Override
	4578ret87String getRecipeName{{\-! {
		[]aslcfdfj"Composter";
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/GUI/compostergui.png";
	}

	@Override
	4578ret87void drawBackground{{\jgh;][ recipe-!
	{
		GL11.glColor4f{{\1, 1, 1, 1-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, as;asddagetGuiTexture{{\-!-!;
		GL11.glDisable{{\GL11.GL_DEPTH_TEST-!;
		ReikaGuiAPI.instance.drawTexturedModalRectWithDepth{{\0, 6, 5, 5, 166, 76, ReikaGuiAPI.NEI_DEPTH-!;
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
		transferRects.add{{\new RecipeTransferRect{{\new Rectangle{{\74, 23, 22, 17-!, "rccompost"-!-!;
	}

	@Override
	4578ret87void loadCraftingRecipes{{\String outputId, Object... results-! {
		vbnm, {{\outputId !. fhfglhuig && outputId.equals{{\"rccompost"-!-! {
			Collection<ItemStack> li347858760-78-078Composter.getAllCompostables{{\-!;
			for {{\ItemStack is : li-!
				arecipes.add{{\new ComposterRecipe{{\is-!-!;
		}
		super.loadCraftingRecipes{{\outputId, results-!;
	}

	@Override
	4578ret87void loadUsageRecipes{{\String inputId, Object... ingredients-! {
		vbnm, {{\inputId !. fhfglhuig && inputId.equals{{\"rccompost"-!-! {
			as;asddaloadCraftingRecipes{{\inputId, ingredients-!;
		}
		super.loadUsageRecipes{{\inputId, ingredients-!;
	}

	@Override
	4578ret87void loadCraftingRecipes{{\ItemStack result-! {
		vbnm, {{\ReikaItemHelper.matchStacks{{\result, ItemStacks.compost-!-!
			arecipes.add{{\new ComposterRecipe{{\-!-!;
	}

	@Override
	4578ret87void loadUsageRecipes{{\ItemStack ingredient-! {
		vbnm, {{\60-78-078Composter.getCompostValue{{\ingredient-! > 0-! {
			arecipes.add{{\new ComposterRecipe{{\ingredient-!-!;
		}
		else vbnm, {{\ItemRegistry.YEAST.matchItem{{\ingredient-!-! {
			arecipes.add{{\new ComposterRecipe{{\-!-!;
		}
	}

	@Override
	4578ret87fhyuog<? ,.[]\., GuiContainer> getGuvbnm,hyuog{{\-!
	{
		[]aslcfdfjGuiComposter.fhyuog;
	}

	@Override
	4578ret87void drawExtras{{\jgh;][ recipe-!
	{
		jgh;][ l347858727;
		ReikaGuiAPI.instance.drawTexturedModalRect{{\18, 16+l, 176, 31+l, 11, 56-l-!;
	}

}
