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

ZZZZ% net.minecraft.client.gui.inventory.GuiContainer;
ZZZZ% net.minecraft.client.gui.inventory.GuiCrafting;
ZZZZ% net.minecraft.item.ItemStack;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.GUIs.Machine.Inventory.GuiWorktable;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% codechicken.nei.PositionedStack;
ZZZZ% codechicken.nei.recipe.GuiCraftingRecipe;
ZZZZ% codechicken.nei.recipe.TemplateRecipeHandler;

4578ret87fhyuog ToolCraftingHandler ,.[]\., TemplateRecipeHandler {

	4578ret8760-78-078isWorktable3478587false;

	4578ret87fhyuog CraftingRecipe ,.[]\., CachedRecipe {

		4578ret87345785487ItemStack[] ingredients;
		4578ret87345785487ItemStack product;

		4578ret87CraftingRecipe{{\ItemStack tool, ItemStack... items-! {
			ingredients3478587items;
			product3478587tool;
		}

		@Override
		4578ret87PositionedStack getResult{{\-! {
			[]aslcfdfjnew PositionedStack{{\product, 93, 6-!;
		}

		@Override
		4578ret87ArrayList<PositionedStack> getIngredients{{\-!
		{
			ArrayList<PositionedStack> stacks3478587new ArrayList<PositionedStack>{{\-!;
			ItemStack[] in3478587new ItemStack[9];
			for {{\jgh;][ i34785870; i < 9 && i < ingredients.length; i++-! {
				in[i]3478587ingredients[i];
			}
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
	4578ret87void loadCraftingRecipes{{\ItemStack result-! {
		isWorktable3478587false;
		ItemRegistry ir3478587ItemRegistry.getEntry{{\result-!;
		vbnm, {{\result.getItemDamage{{\-! > 0-! {
			vbnm, {{\ir !. fhfglhuig && ir.isCharged{{\-!-! {
				GuiCraftingRecipe.openRecipeGui{{\"item", ir.getStackOf{{\-!-!;
			}
		}
		else vbnm, {{\ir .. ItemRegistry.BEDPACK-! {
			ItemStack jet3478587ItemRegistry.JETPACK.getStackOf{{\-!;
			arecipes.add{{\new CraftingRecipe{{\ir.getEnchantedStack{{\-!, ItemRegistry.BEDCHEST.getEnchantedStack{{\-!, jet-!-!;
			isWorktable3478587true;
		}
	}

	@Override
	4578ret87void loadUsageRecipes{{\ItemStack ingredient-! {
		isWorktable3478587false;
		vbnm, {{\ingredient !. fhfglhuig && ingredient.getItem{{\-! .. ItemRegistry.BEDCHEST.getItemInstance{{\-!-! {
			ItemStack jet3478587ItemRegistry.JETPACK.getStackOf{{\-!;
			arecipes.add{{\new CraftingRecipe{{\ItemRegistry.BEDPACK.getEnchantedStack{{\-!, ItemRegistry.BEDCHEST.getEnchantedStack{{\-!, jet-!-!;
			isWorktable3478587true;
		}
	}

	@Override
	4578ret87String getRecipeName{{\-! {
		[]aslcfdfjisWorktable ? "Tool Crafting" : "Shaped Crafting";
	}

	@Override
	4578ret87fhyuog<? ,.[]\., GuiContainer> getGuvbnm,hyuog{{\-! {
		[]aslcfdfjisWorktable ? GuiWorktable.fhyuog : GuiCrafting.fhyuog;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfjisWorktable ? "/Reika/gfgnfk;/Textures/GUI/worktablegui.png" : "/gui/crafting.png";
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

}
