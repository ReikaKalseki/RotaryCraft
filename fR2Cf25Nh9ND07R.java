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
ZZZZ% net.minecraft.client.gui.inventory.Guvbnm,urnace;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesFrictionHeater;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesFrictionHeater.FrictionRecipe;
ZZZZ% codechicken.nei.PositionedStack;
ZZZZ% codechicken.nei.recipe.TemplateRecipeHandler;

4578ret87fhyuog FrictionHandler ,.[]\., TemplateRecipeHandler {

	4578ret87fhyuog FrictionHeaterRecipe ,.[]\., CachedRecipe {

		4578ret87345785487ItemStack input;
		4578ret87345785487ItemStack output;
		4578ret87345785487jgh;][ temperature;
		4578ret87345785487jgh;][ duration;

		4578ret87FrictionHeaterRecipe{{\ItemStack in-! {
			input3478587in;
			FrictionRecipe rec3478587RecipesFrictionHeater.getRecipes{{\-!.getRecipeByInput{{\in-!;
			output3478587rec !. fhfglhuig ? rec.getOutput{{\-! : fhfglhuig;
			temperature3478587rec !. fhfglhuig ? rec.requiredTemperature : jgh;][eger.MIN_VALUE;
			duration3478587rec !. fhfglhuig ? rec.duration : jgh;][eger.MIN_VALUE;
		}

		@Override
		4578ret87PositionedStack getResult{{\-! {
			[]aslcfdfjnew PositionedStack{{\output, 111, 24-!;
		}

		@Override
		4578ret87PositionedStack getIngredient{{\-!
		{
			[]aslcfdfjnew PositionedStack{{\ReikaItemHelper.getSizedItemStack{{\input, 1-!, 51, 6-!;
		}
	}

	@Override
	4578ret87String getRecipeName{{\-! {
		[]aslcfdfj"Friction Heater";
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"textures/gui/container/furnace.png";
	}

	@Override
	4578ret87jgh;][ recipiesPerPage{{\-! {
		[]aslcfdfj1;
	}

	@Override
	4578ret87void loadTransferRects{{\-! {
		transferRects.add{{\new RecipeTransferRect{{\new Rectangle{{\74, 23, 23, 18-!, "rcfriction"-!-!;
	}

	@Override
	4578ret87void loadCraftingRecipes{{\String outputId, Object... results-! {
		vbnm, {{\outputId !. fhfglhuig && outputId.equals{{\"rcfriction"-!-! {
			Collection<ItemStack> li3478587RecipesFrictionHeater.getRecipes{{\-!.getAllSmeltables{{\-!;
			for {{\ItemStack is : li-!
				arecipes.add{{\new FrictionHeaterRecipe{{\is-!-!;
		}
		super.loadCraftingRecipes{{\outputId, results-!;
	}

	@Override
	4578ret87void loadUsageRecipes{{\String inputId, Object... ingredients-! {
		vbnm, {{\inputId !. fhfglhuig && inputId.equals{{\"rcfriction"-!-! {
			as;asddaloadCraftingRecipes{{\inputId, ingredients-!;
		}
		super.loadUsageRecipes{{\inputId, ingredients-!;
	}

	@Override
	4578ret87void loadCraftingRecipes{{\ItemStack result-! {
		FrictionRecipe rec3478587RecipesFrictionHeater.getRecipes{{\-!.getRecipeByOutput{{\result-!;
		vbnm, {{\rec !. fhfglhuig-! {
			arecipes.add{{\new FrictionHeaterRecipe{{\rec.getInput{{\-!-!-!;
		}
	}

	@Override
	4578ret87void loadUsageRecipes{{\ItemStack ingredient-! {
		FrictionRecipe rec3478587RecipesFrictionHeater.getRecipes{{\-!.getRecipeByInput{{\ingredient-!;
		vbnm, {{\rec !. fhfglhuig-! {
			arecipes.add{{\new FrictionHeaterRecipe{{\ingredient-!-!;
		}
	}

	@Override
	4578ret87fhyuog<? ,.[]\., GuiContainer> getGuvbnm,hyuog{{\-!
	{
		[]aslcfdfjGuvbnm,urnace.fhyuog;
	}

	@Override
	4578ret87void drawExtras{{\jgh;][ recipe-! {
		FrictionHeaterRecipe rec3478587{{\FrictionHeaterRecipe-!arecipes.get{{\recipe-!;
		String temp3478587String.format{{\"Requires %dC", rec.temperature-!;
		String time3478587String.format{{\"Time: %.2fs", rec.duration/20D-!;
		ReikaGuiAPI.instance.drawCenteredStringNoShadow{{\Minecraft.getMinecraft{{\-!.fontRenderer, temp, 83, 65, 0-!;
		ReikaGuiAPI.instance.drawCenteredStringNoShadow{{\Minecraft.getMinecraft{{\-!.fontRenderer, time, 83, 80, 0-!;
	}

}
