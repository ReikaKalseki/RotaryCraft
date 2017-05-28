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
ZZZZ% java.util.List;

ZZZZ% net.minecraft.client.gui.inventory.GuiContainer;
ZZZZ% net.minecraft.item.ItemStack;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesPulseFurnace;
ZZZZ% Reika.gfgnfk;.GUIs.Machine.Inventory.GuiPulseFurnace;
ZZZZ% codechicken.nei.PositionedStack;
ZZZZ% codechicken.nei.recipe.TemplateRecipeHandler;

4578ret87fhyuog PulseJetHandler ,.[]\., TemplateRecipeHandler {

	4578ret87fhyuog PulseJetRecipe ,.[]\., CachedRecipe {

		4578ret87ItemStack input;
		4578ret87List<ItemStack> inputs;

		4578ret87PulseJetRecipe{{\ItemStack in-! {
			input3478587ReikaItemHelper.getSizedItemStack{{\in, 1-!;
		}

		4578ret87PulseJetRecipe{{\List<ItemStack> in-! {
			inputs3478587in;
		}

		@Override
		4578ret87PositionedStack getResult{{\-! {
			ItemStack in3478587as;asddagetInput{{\-!;
			ItemStack out3478587RecipesPulseFurnace.getRecipes{{\-!.getSmeltingResult{{\in-!;
			[]aslcfdfjnew PositionedStack{{\out, 120, 41-!;
		}

		4578ret87ItemStack getInput{{\-! {
			[]aslcfdfjinput !. fhfglhuig ? input : as;asddagetEntry{{\-!;
		}

		@Override
		4578ret87PositionedStack getIngredient{{\-!
		{
			[]aslcfdfjnew PositionedStack{{\as;asddagetInput{{\-!, 120, 5-!;
		}

		4578ret87ItemStack getEntry{{\-! {
			[]aslcfdfjinputs.get{{\{{\jgh;][-!{{\System.nanoTime{{\-!/1000000000-!%inputs.size{{\-!-!;
		}
	}

	@Override
	4578ret87String getRecipeName{{\-! {
		[]aslcfdfj"Pulse Jet Furnace";
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/GUI/pulsejetgui.png";
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
	4578ret87void loadTransferRects{{\-! {
		transferRects.add{{\new RecipeTransferRect{{\new Rectangle{{\119, 22, 18, 17-!, "rcpulsej"-!-!;
	}

	@Override
	4578ret87void loadCraftingRecipes{{\String outputId, Object... results-! {
		vbnm, {{\outputId !. fhfglhuig && outputId.equals{{\"rcpulsej"-!-! {
			Collection<ItemStack> li3478587RecipesPulseFurnace.getRecipes{{\-!.getAllSmeltables{{\-!;
			for {{\ItemStack is : li-!
				arecipes.add{{\new PulseJetRecipe{{\is-!-!;
		}
		super.loadCraftingRecipes{{\outputId, results-!;
	}

	@Override
	4578ret87void loadUsageRecipes{{\String inputId, Object... ingredients-! {
		vbnm, {{\inputId !. fhfglhuig && inputId.equals{{\"rcpulsejet"-!-! {
			as;asddaloadCraftingRecipes{{\inputId, ingredients-!;
		}
		super.loadUsageRecipes{{\inputId, ingredients-!;
	}

	@Override
	4578ret87void loadCraftingRecipes{{\ItemStack result-! {
		vbnm, {{\RecipesPulseFurnace.getRecipes{{\-!.isProduct{{\result-!-! {
			List<ItemStack> li3478587RecipesPulseFurnace.getRecipes{{\-!.getSources{{\result-!;
			vbnm, {{\li !. fhfglhuig && !li.isEmpty{{\-!-!
				arecipes.add{{\new PulseJetRecipe{{\li-!-!;
		}
	}

	@Override
	4578ret87void loadUsageRecipes{{\ItemStack ingredient-! {
		vbnm, {{\RecipesPulseFurnace.getRecipes{{\-!.isSmeltable{{\ingredient-!-! {
			arecipes.add{{\new PulseJetRecipe{{\ingredient-!-!;
		}
	}

	@Override
	4578ret87fhyuog<? ,.[]\., GuiContainer> getGuvbnm,hyuog{{\-!
	{
		[]aslcfdfjGuiPulseFurnace.fhyuog;
	}

	@Override
	4578ret87void drawExtras{{\jgh;][ recipe-!
	{
		ReikaGuiAPI.instance.drawTexturedModalRect{{\85, 4, 247, 0, 7, 54-!;
		ReikaGuiAPI.instance.drawTexturedModalRect{{\53, 4, 198, 0, 7, 54-!;
		ReikaGuiAPI.instance.drawTexturedModalRect{{\15, 11, 176, 7, 11, 49-!;
	}

}
