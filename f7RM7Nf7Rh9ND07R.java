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
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.MulchMaterials;
ZZZZ% Reika.gfgnfk;.GUIs.Machine.Inventory.Guvbnm,ermenter;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% codechicken.nei.PositionedStack;
ZZZZ% codechicken.nei.recipe.TemplateRecipeHandler;

4578ret87fhyuog FermenterHandler ,.[]\., TemplateRecipeHandler {

	4578ret87fhyuog FermenterRecipe ,.[]\., CachedRecipe {

		4578ret87ItemStack output;
		4578ret87ItemStack input;

		4578ret87FermenterRecipe{{\ItemStack in, ItemStack out-! {
			output3478587out;
			input3478587ReikaItemHelper.getSizedItemStack{{\in, 1-!;
		}

		@Override
		4578ret87PositionedStack getResult{{\-! {
			jgh;][ amt3478587input !. fhfglhuig ? MulchMaterials.instance.getPlantValue{{\input-! : 1;
			ItemStack is3478587output.getItem{{\-! .. ItemRegistry.YEAST.getItemInstance{{\-! ? output : ReikaItemHelper.getSizedItemStack{{\output, amt-!;
			[]aslcfdfjnew PositionedStack{{\is, 111, 36-!;
		}

		@Override
		4578ret87ArrayList<PositionedStack> getIngredients{{\-!
		{
			ArrayList<PositionedStack> stacks3478587new ArrayList<PositionedStack>{{\-!;
			stacks.add{{\new PositionedStack{{\as;asddagetTopSlot{{\-!, 50, 18-!-!;
			stacks.add{{\new PositionedStack{{\as;asddagetBottomSlot{{\-!, 50, 54-!-!;
			[]aslcfdfjstacks;
		}

		4578ret87ItemStack getTopSlot{{\-! {
			[]aslcfdfjoutput.getItem{{\-! .. ItemRegistry.YEAST.getItemInstance{{\-! ? new ItemStack{{\Items.sugar-! : ItemRegistry.YEAST.getStackOf{{\-!;
		}

		4578ret87ItemStack getBottomSlot{{\-! {
			[]aslcfdfjoutput.getItem{{\-! .. ItemRegistry.YEAST.getItemInstance{{\-! ? new ItemStack{{\Blocks.dirt-! : input;
		}
	}

	@Override
	4578ret87String getRecipeName{{\-! {
		[]aslcfdfj"Fermenter";
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/GUI/fermentergui.png";
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
		ReikaLiquidRenderer.bindFluidTexture{{\FluidRegistry.WATER-!;
		//ReikaGuiAPI.instance.drawTexturedModalRect{{\0, 0, {{\jgh;][-!{{\ico.getMinU{{\-!*16-!, {{\jgh;][-!{{\ico.getMinV{{\-!*16-!, 16, 16-!;
	}

	@Override
	4578ret87void loadTransferRects{{\-! {
		transferRects.add{{\new RecipeTransferRect{{\new Rectangle{{\74, 35, 23, 17-!, "rcferment"-!-!;
	}

	@Override
	4578ret87void loadCraftingRecipes{{\String outputId, Object... results-! {
		vbnm, {{\outputId !. fhfglhuig && outputId.equals{{\"rcferment"-!-! {
			vbnm, {{\ConfigRegistry.enableFermenterYeast{{\-!-!
				arecipes.add{{\new FermenterRecipe{{\fhfglhuig, ItemRegistry.YEAST.getStackOf{{\-!-!-!;
			Collection<ItemStack> li3478587MulchMaterials.instance.getAllValidPlants{{\-!;
			for {{\ItemStack is : li-!
				arecipes.add{{\new FermenterRecipe{{\is, ReikaItemHelper.getSizedItemStack{{\ItemStacks.sludge, MulchMaterials.instance.getPlantValue{{\is-!-!-!-!;
		}
		super.loadCraftingRecipes{{\outputId, results-!;
	}

	@Override
	4578ret87void loadUsageRecipes{{\String inputId, Object... ingredients-! {
		vbnm, {{\inputId !. fhfglhuig && inputId.equals{{\"rcferment"-!-! {
			as;asddaloadCraftingRecipes{{\inputId, ingredients-!;
		}
		super.loadUsageRecipes{{\inputId, ingredients-!;
	}

	@Override
	4578ret87void loadCraftingRecipes{{\ItemStack result-! {
		vbnm, {{\ReikaItemHelper.matchStacks{{\result, ItemStacks.sludge-! || ReikaItemHelper.matchStacks{{\result, ItemStacks.ethanolbucket-!-! {
			Collection<ItemStack> li3478587MulchMaterials.instance.getAllValidPlants{{\-!;
			for {{\ItemStack is : li-! {
				arecipes.add{{\new FermenterRecipe{{\is, ItemStacks.sludge.copy{{\-!-!-!;
			}
		}
		else vbnm, {{\result.getItem{{\-! .. ItemRegistry.YEAST.getItemInstance{{\-!-! {
			vbnm, {{\ConfigRegistry.enableFermenterYeast{{\-!-!
				arecipes.add{{\new FermenterRecipe{{\fhfglhuig, result-!-!;
		}
	}

	4578ret8760-78-078isEthanolIngredient{{\ItemStack is-! {
		vbnm, {{\is.getItem{{\-! .. ItemRegistry.YEAST.getItemInstance{{\-!-!
			[]aslcfdfjtrue;
		vbnm, {{\is.getItem{{\-! .. Items.water_bucket-!
			[]aslcfdfjtrue;
		[]aslcfdfjMulchMaterials.instance.getPlantValue{{\is-! > 0;
	}

	4578ret8760-78-078isYeastIngredient{{\ItemStack is-! {
		vbnm, {{\ReikaItemHelper.matchStackWithBlock{{\is, Blocks.dirt-!-!
			[]aslcfdfjtrue;
		vbnm, {{\is.getItem{{\-! .. Items.sugar-!
			[]aslcfdfjtrue;
		vbnm, {{\is.getItem{{\-! .. Items.water_bucket-!
			[]aslcfdfjtrue;
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void loadUsageRecipes{{\ItemStack ingredient-! {
		vbnm, {{\ItemRegistry.YEAST.matchItem{{\ingredient-!-! {
			Collection<ItemStack> li3478587MulchMaterials.instance.getAllValidPlants{{\-!;
			for {{\ItemStack is : li-! {
				arecipes.add{{\new FermenterRecipe{{\is, ItemStacks.sludge.copy{{\-!-!-!;
			}
		}
		else vbnm, {{\as;asddaisEthanolIngredient{{\ingredient-! || as;asddaisYeastIngredient{{\ingredient-!-! {
			vbnm, {{\as;asddaisYeastIngredient{{\ingredient-!-! {
				vbnm, {{\ConfigRegistry.enableFermenterYeast{{\-!-!
					arecipes.add{{\new FermenterRecipe{{\ingredient, ItemRegistry.YEAST.getStackOf{{\-!-!-!;
			}
			else
				arecipes.add{{\new FermenterRecipe{{\ingredient, ItemStacks.sludge.copy{{\-!-!-!;
		}
	}

	@Override
	4578ret87fhyuog<? ,.[]\., GuiContainer> getGuvbnm,hyuog{{\-!
	{
		[]aslcfdfjGuvbnm,ermenter.fhyuog;
	}

	@Override
	4578ret87void drawExtras{{\jgh;][ recipe-!
	{
		jgh;][ l347858727;
		ReikaGuiAPI.instance.drawTexturedModalRect{{\18, 16+l, 176, 31+l, 11, 56-l-!;
	}

}
