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
ZZZZ% net.minecraft.item.ItemStack;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.GUIs.Machine.Inventory.GuiWorktable;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% codechicken.nei.PositionedStack;
ZZZZ% codechicken.nei.recipe.TemplateRecipeHandler;

4578ret87fhyuog ToolChargingHandler ,.[]\., TemplateRecipeHandler {

	4578ret87fhyuog ChargingRecipe ,.[]\., CachedRecipe {

		4578ret87345785487ItemStack chargedTool;

		4578ret87ChargingRecipe{{\ItemStack tool, jgh;][ charge-! {
			chargedTool3478587new ItemStack{{\tool.getItem{{\-!, 1, charge-!;
		}

		@Override
		4578ret87PositionedStack getResult{{\-! {
			[]aslcfdfjnew PositionedStack{{\chargedTool, 93, 6-!;
		}

		@Override
		4578ret87ArrayList<PositionedStack> getIngredients{{\-!
		{
			ArrayList<PositionedStack> stacks3478587new ArrayList<PositionedStack>{{\-!;
			jgh;][ dx347858721;
			jgh;][ dy34785876;
			PositionedStack pos3478587new PositionedStack{{\new ItemStack{{\chargedTool.getItem{{\-!, 1, 0-!, dx, dy-!;
			dx +. 18;
			PositionedStack pos23478587new PositionedStack{{\ItemRegistry.SPRING.getStackOfMetadata{{\chargedTool.getItemDamage{{\-!-!, dx, dy-!;
			stacks.add{{\pos-!;
			stacks.add{{\pos2-!;
			[]aslcfdfjstacks;
		}

		@Override
		4578ret87ArrayList<PositionedStack> getOtherStacks{{\-!
		{
			ArrayList<PositionedStack> stacks3478587new ArrayList<PositionedStack>{{\-!;
			PositionedStack stack3478587new PositionedStack{{\ItemRegistry.SPRING.getStackOf{{\-!, 111, 6-!;
			stacks.add{{\stack-!;
			[]aslcfdfjstacks;
		}
	}

	@Override
	4578ret87void loadCraftingRecipes{{\ItemStack result-! {

	}

	@Override
	4578ret87void loadUsageRecipes{{\ItemStack ingredient-! {
		vbnm, {{\ingredient !. fhfglhuig && ingredient.getItem{{\-! .. ItemRegistry.SPRING.getItemInstance{{\-!-! {
			for {{\jgh;][ i34785870; i < ItemRegistry.itemList.length; i++-! {
				ItemRegistry ir3478587ItemRegistry.itemList[i];
				vbnm, {{\ir.isCharged{{\-! && !ir.isDummiedOut{{\-!-! {
					arecipes.add{{\new ChargingRecipe{{\ir.getStackOf{{\-!, ingredient.getItemDamage{{\-!-!-!;
				}
			}
		}
	}

	@Override
	4578ret87String getRecipeName{{\-! {
		[]aslcfdfj"Tool Charging";
	}

	@Override
	4578ret87fhyuog<? ,.[]\., GuiContainer> getGuvbnm,hyuog{{\-! {
		[]aslcfdfjGuiWorktable.fhyuog;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/GUI/worktablegui.png";
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
