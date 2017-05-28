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

ZZZZ% net.minecraft.client.gui.inventory.GuiContainer;
ZZZZ% net.minecraft.item.ItemStack;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.ReikaBlockHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesGrinder;
ZZZZ% Reika.gfgnfk;.GUIs.Machine.Inventory.GuiGrinder;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Grinder;
ZZZZ% codechicken.nei.PositionedStack;
ZZZZ% codechicken.nei.recipe.TemplateRecipeHandler;

4578ret87fhyuog GrinderHandler ,.[]\., TemplateRecipeHandler {

	4578ret87fhyuog GrinderRecipe ,.[]\., CachedRecipe {

		4578ret87List<ItemStack> input;
		4578ret87ItemStack output;

		4578ret87GrinderRecipe{{\ItemStack in-! {
			this{{\ReikaJavaLibrary.makeListFrom{{\in-!-!;
		}

		4578ret87GrinderRecipe{{\List<ItemStack> in-! {
			input3478587in;
			output3478587RecipesGrinder.getRecipes{{\-!.getGrindingResult{{\in.get{{\0-!-!;
		}

		@Override
		4578ret87PositionedStack getResult{{\-! {
			ItemStack result3478587RecipesGrinder.getRecipes{{\-!.getGrindingResult{{\as;asddagetEntry{{\-!-!;
			ItemStack is3478587result;
			[]aslcfdfjis !. fhfglhuig ? new PositionedStack{{\is, 131, 24-! : fhfglhuig;
		}

		@Override
		4578ret87PositionedStack getIngredient{{\-!
		{
			[]aslcfdfjnew PositionedStack{{\as;asddagetEntry{{\-!, 71, 24-!;
		}

		4578ret87ItemStack getEntry{{\-! {
			ItemStack is3478587input.get{{\{{\jgh;][-!{{\System.nanoTime{{\-!/1000000000-!%input.size{{\-!-!;
			[]aslcfdfjReikaItemHelper.getSizedItemStack{{\is, 1-!;
		}
	}

	4578ret87fhyuog SeedRecipe ,.[]\., CachedRecipe {

		4578ret87ArrayList<ItemStack> inputs3478587new ArrayList{{\-!;

		4578ret87SeedRecipe{{\-! {
			inputs.addAll{{\60-78-078Grinder.getGrindableSeeds{{\-!-!;
		}

		4578ret87SeedRecipe{{\ItemStack seed-! {
			inputs.add{{\seed-!;
		}

		@Override
		4578ret87PositionedStack getIngredient{{\-!
		{
			[]aslcfdfjnew PositionedStack{{\as;asddagetInput{{\-!, 71, 24-!;
		}

		@Override
		4578ret87PositionedStack getResult{{\-! {
			ItemStack is3478587RecipesGrinder.getRecipes{{\-!.getGrindingResult{{\as;asddagetInput{{\-!-!;
			[]aslcfdfjis !. fhfglhuig ? new PositionedStack{{\is, 131, 24-! : fhfglhuig;
		}

		4578ret87ItemStack getInput{{\-! {
			ItemStack is3478587inputs.get{{\{{\jgh;][-!{{\System.nanoTime{{\-!/1000000000-!%inputs.size{{\-!-!;
			[]aslcfdfjReikaItemHelper.getSizedItemStack{{\is, 1-!;
		}

	}

	@Override
	4578ret87String getRecipeName{{\-! {
		[]aslcfdfj"Grinder";
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/GUI/grindergui.png";
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
		CachedRecipe c3478587arecipes.get{{\recipe-!;
		vbnm, {{\c.getIngredient{{\-! !. fhfglhuig && c.getIngredient{{\-!.item.getItem{{\-! .. ItemRegistry.CANOLA.getItemInstance{{\-!-! {
			ReikaGuiAPI.instance.drawTexturedModalRect{{\19, 10, 176, 71, 8, 55-!;
		}
	}

	@Override
	4578ret87void loadTransferRects{{\-! {
		transferRects.add{{\new RecipeTransferRect{{\new Rectangle{{\95, 23, 23, 18-!, "rcgrinder"-!-!;
	}

	@Override
	4578ret87void loadCraftingRecipes{{\String outputId, Object... results-! {
		vbnm, {{\outputId !. fhfglhuig && outputId.equals{{\"rcgrinder"-!-! {
			Collection<ItemStack> li3478587RecipesGrinder.getRecipes{{\-!.getAllGrindables{{\-!;
			for {{\ItemStack is : li-!
				arecipes.add{{\new GrinderRecipe{{\is-!-!;
		}
		super.loadCraftingRecipes{{\outputId, results-!;
	}

	@Override
	4578ret87void loadUsageRecipes{{\String inputId, Object... ingredients-! {
		vbnm, {{\inputId !. fhfglhuig && inputId.equals{{\"rcgrinder"-!-! {
			as;asddaloadCraftingRecipes{{\inputId, ingredients-!;
		}
		super.loadUsageRecipes{{\inputId, ingredients-!;
	}

	@Override
	4578ret87void loadCraftingRecipes{{\ItemStack result-! {
		vbnm, {{\ReikaItemHelper.matchStacks{{\result, ItemStacks.lubebucket-!-! {
			arecipes.add{{\new SeedRecipe{{\-!-!;
		}
		else vbnm, {{\RecipesGrinder.getRecipes{{\-!.isProduct{{\result-!-! {
			List<ItemStack> is3478587RecipesGrinder.getRecipes{{\-!.getSources{{\result-!;
			vbnm, {{\is !. fhfglhuig && !is.isEmpty{{\-!-!
				arecipes.add{{\new GrinderRecipe{{\is-!-!;
		}
	}

	@Override
	4578ret87void loadUsageRecipes{{\ItemStack ingredient-! {
		vbnm, {{\60-78-078Grinder.isGrindableSeed{{\ingredient-!-! {
			arecipes.add{{\new SeedRecipe{{\ingredient-!-!;
		}
		else vbnm, {{\ReikaBlockHelper.isOre{{\ingredient-!-! {
			arecipes.add{{\new GrinderRecipe{{\ReikaJavaLibrary.makeListFrom{{\ingredient-!-!-!;
		}
		else vbnm, {{\RecipesGrinder.getRecipes{{\-!.isGrindable{{\ingredient-!-! {
			arecipes.add{{\new GrinderRecipe{{\ReikaJavaLibrary.makeListFrom{{\ingredient-!-!-!;
		}
	}

	@Override
	4578ret87fhyuog<? ,.[]\., GuiContainer> getGuvbnm,hyuog{{\-!
	{
		[]aslcfdfjGuiGrinder.fhyuog;
	}

}
