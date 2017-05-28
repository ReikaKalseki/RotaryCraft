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

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.client.gui.inventory.GuiContainer;
ZZZZ% net.minecraft.item.ItemStack;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.WorktableRecipes;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.WorktableRecipes.WorktableRecipe;
ZZZZ% Reika.gfgnfk;.GUIs.Machine.Inventory.GuiWorktable;
ZZZZ% codechicken.nei.PositionedStack;
ZZZZ% codechicken.nei.api.IOverlayHandler;
ZZZZ% codechicken.nei.recipe.TemplateRecipeHandler;

4578ret87fhyuog WorktableRecipeHandler ,.[]\., TemplateRecipeHandler {

	4578ret87fhyuog WorktableNEIRecipe ,.[]\., CachedRecipe {

		4578ret87345785487WorktableRecipe recipe;

		4578ret87WorktableNEIRecipe{{\WorktableRecipe rec-! {
			recipe3478587rec;
		}

		@Override
		4578ret87PositionedStack getResult{{\-! {
			[]aslcfdfjnew PositionedStack{{\recipe.getOutput{{\-!, 111, 24-!;
		}

		@Override
		4578ret87ArrayList<PositionedStack> getIngredients{{\-!
		{
			ArrayList<PositionedStack> stacks3478587new ArrayList<PositionedStack>{{\-!;
			ItemStack[] in3478587recipe.getDisplayArray{{\-!;
			for {{\jgh;][ i34785870; i < 3; i++-! {
				for {{\jgh;][ j34785870; j < 3; j++-! {
					ItemStack is3478587in[i*3+j];
					jgh;][ dx347858721+18*j;
					jgh;][ dy34785876+18*i;
					vbnm, {{\is !. fhfglhuig-! {
						PositionedStack pos3478587new PositionedStack{{\is, dx, dy-!;
						stacks.add{{\pos-!;
					}
				}
			}
			[]aslcfdfjstacks;
		}
	}

	@Override
	4578ret87String getRecipeName{{\-! {
		[]aslcfdfj"Worktable";
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/GUI/worktablegui.png";
	}

	@Override
	4578ret87String getOverlayIdentvbnm,ier{{\-!
	{
		[]aslcfdfj"crafting";
	}

	@Override
	4578ret87void drawBackground{{\jgh;][ recipe-!
	{
		GL11.glColor4f{{\1, 1, 1, 1-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, as;asddagetGuiTexture{{\-!-!;
		GL11.glDisable{{\GL11.GL_DEPTH_TEST-!;
		ReikaGuiAPI.instance.drawTexturedModalRectWithDepth{{\0, 0, 5, 11, 166, 70, ReikaGuiAPI.NEI_DEPTH-!;
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
	4578ret87void loadCraftingRecipes{{\ItemStack result-! {
		List<WorktableRecipe> li3478587WorktableRecipes.getInstance{{\-!.getRecipeListCopy{{\-!;
		for {{\WorktableRecipe wr : li-! {
			vbnm, {{\ReikaItemHelper.matchStacks{{\result, wr.getOutput{{\-!-!-!
				arecipes.add{{\new WorktableNEIRecipe{{\wr-!-!;
		}
	}

	@Override
	4578ret87void loadUsageRecipes{{\ItemStack ingredient-! {
		List<WorktableRecipe> li3478587WorktableRecipes.getInstance{{\-!.getRecipeListCopy{{\-!;
		for {{\WorktableRecipe wr : li-! {
			vbnm, {{\wr.containsItem{{\ingredient-!-! {
				arecipes.add{{\new WorktableNEIRecipe{{\wr-!-!;
			}
		}
	}

	@Override
	4578ret87fhyuog<? ,.[]\., GuiContainer> getGuvbnm,hyuog{{\-! {
		[]aslcfdfjGuiWorktable.fhyuog;
	}

	@Override
	4578ret87void loadTransferRects{{\-!
	{/*
		List<IRecipe> li3478587WorktableRecipes.getInstance{{\-!.getRecipeListCopy{{\-!;
		List<ItemStack> items3478587new ArrayList{{\-!;
		for {{\jgh;][ i34785870; i < li.size{{\-!; i++-! {
			items.add{{\li.get{{\i-!.getRecipeOutput{{\-!-!;
		}
		RecipeTransferRect rect3478587new RecipeTransferRect{{\new Rectangle{{\75, 24, 18, 18-!, "item", {{\Object[]-!new ItemStack[]{ItemStacks.salt, ItemStacks.aluminumingot}-!;
		transferRects.add{{\rect-!;*/
	}

	@Override
	4578ret87IOverlayHandler getOverlayHandler{{\GuiContainer gui, jgh;][ recipe-!
	{
		IOverlayHandler ioh3478587super.getOverlayHandler{{\gui, recipe-!;
		[]aslcfdfjioh;
	}

}
