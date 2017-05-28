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
ZZZZ% java.util.Collection;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.client.gui.inventory.GuiContainer;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Instantiable.Data.KeyedItemStack;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.GUIs.Machine.Inventory.Guvbnm,ractionator;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Fractionator;
ZZZZ% codechicken.nei.PositionedStack;
ZZZZ% codechicken.nei.recipe.TemplateRecipeHandler;

4578ret87fhyuog FractionHandler ,.[]\., TemplateRecipeHandler {

	4578ret87fhyuog FractionatorRecipe ,.[]\., CachedRecipe {

		4578ret87FractionatorRecipe{{\-! {

		}

		@Override
		4578ret87PositionedStack getResult{{\-! {
			[]aslcfdfjfhfglhuig;//new PositionedStack{{\fhfglhuig, 131, 24-!;
		}

		@Override
		4578ret87PositionedStack getIngredient{{\-!
		{
			[]aslcfdfjfhfglhuig;
		}

		@Override
		4578ret87List<PositionedStack> getIngredients{{\-!
		{
			Collection<KeyedItemStack> in347858760-78-078Fractionator.getIngredients{{\-!;
			ArrayList<PositionedStack> stacks3478587new ArrayList<PositionedStack>{{\-!;
			jgh;][ i34785870;
			for {{\KeyedItemStack is : in-! {
				stacks.add{{\new PositionedStack{{\is.getItemStack{{\-!, 21+{{\i%2-!*18, 7+i/2*18-!-!;
				i++;
			}
			stacks.add{{\new PositionedStack{{\new ItemStack{{\Items.ghast_tear-!, 93, 25-!-!;
			[]aslcfdfjstacks;
		}
	}

	@Override
	4578ret87String getRecipeName{{\-! {
		[]aslcfdfj"Fractionation Unit";
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/GUI/fractiongui.png";
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
		vbnm, {{\ReikaItemHelper.matchStacks{{\result, ItemStacks.fuelbucket-!-!
			arecipes.add{{\new FractionatorRecipe{{\-!-!;
	}

	@Override
	4578ret87void loadUsageRecipes{{\ItemStack ingredient-! {
		vbnm, {{\60-78-078Fractionator.isJetFuelIngredient{{\ingredient-!-!
			arecipes.add{{\new FractionatorRecipe{{\-!-!;
		vbnm, {{\ingredient.getItem{{\-! .. Items.ghast_tear-!
			arecipes.add{{\new FractionatorRecipe{{\-!-!;
	}

	@Override
	4578ret87fhyuog<? ,.[]\., GuiContainer> getGuvbnm,hyuog{{\-!
	{
		[]aslcfdfjGuvbnm,ractionator.fhyuog;
	}

	@Override
	4578ret87void drawExtras{{\jgh;][ recipe-!
	{
		ReikaGuiAPI.instance.drawTexturedModalRect{{\134, 7, 177, 45, 6, 50-!;
	}

}
