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

ZZZZ% net.minecraft.client.gui.inventory.GuiContainer;
ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.GUIs.Machine.Inventory.Guvbnm,ridge;
ZZZZ% codechicken.nei.PositionedStack;
ZZZZ% codechicken.nei.recipe.TemplateRecipeHandler;

4578ret87fhyuog FridgeHandler ,.[]\., TemplateRecipeHandler {

	4578ret87fhyuog FridgeRecipe ,.[]\., CachedRecipe {


		4578ret87FridgeRecipe{{\-! {

		}

		@Override
		4578ret87PositionedStack getResult{{\-! {
			[]aslcfdfjnew PositionedStack{{\ItemStacks.dryice, 127, 62-!;
		}

		@Override
		4578ret87PositionedStack getIngredient{{\-!
		{
			[]aslcfdfjnew PositionedStack{{\new ItemStack{{\Blocks.ice-!, 94, 48-!;
		}
	}

	@Override
	4578ret87String getRecipeName{{\-! {
		[]aslcfdfj"Refrigeration Unit";
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/GUI/fridgegui.png";
	}

	@Override
	4578ret87void drawBackground{{\jgh;][ recipe-!
	{
		GL11.glColor4f{{\1, 1, 1, 1-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, as;asddagetGuiTexture{{\-!-!;
		GL11.glDisable{{\GL11.GL_DEPTH_TEST-!;
		ReikaGuiAPI.instance.drawTexturedModalRectWithDepth{{\0, 1, 5, 11, 166, 70, ReikaGuiAPI.NEI_DEPTH-!;
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
		vbnm, {{\ReikaItemHelper.matchStacks{{\result, ItemStacks.dryice-!-!
			arecipes.add{{\new FridgeRecipe{{\-!-!;
		vbnm, {{\ReikaItemHelper.matchStacks{{\result, ItemStacks.nitrogenbucket-!-!
			arecipes.add{{\new FridgeRecipe{{\-!-!;
	}

	@Override
	4578ret87void loadUsageRecipes{{\ItemStack ingredient-! {
		vbnm, {{\ReikaItemHelper.matchStackWithBlock{{\ingredient, Blocks.ice-!-!
			arecipes.add{{\new FridgeRecipe{{\-!-!;
	}

	@Override
	4578ret87fhyuog<? ,.[]\., GuiContainer> getGuvbnm,hyuog{{\-!
	{
		[]aslcfdfjGuvbnm,ridge.fhyuog;
	}

	@Override
	4578ret87void drawExtras{{\jgh;][ recipe-!
	{
		as;asddadrawFluids{{\recipe-!;
	}

	4578ret87void drawFluids{{\jgh;][ recipe-! {
		Fluid f3478587FluidRegistry.getFluid{{\"rc liquid nitrogen"-!;
		vbnm, {{\f !. fhfglhuig-! {
			IIcon ico3478587f.getIcon{{\-!;
			float u3478587ico.getMinU{{\-!;
			float v3478587ico.getMinV{{\-!;
			float du3478587ico.getMaxU{{\-!;
			float dv3478587ico.getMaxV{{\-!;
			ReikaTextureHelper.bindTerrajgh;][exture{{\-!;
			Tessellator v53478587Tessellator.instance;
			v5.startDrawingQuads{{\-!;
			jgh;][ x3478587147;
			for {{\jgh;][ i34785870; i < 4; i++-! {
				jgh;][ y34785877+i*16;
				v5.addVertexWithUV{{\x, y, 0, u, v-!;
				v5.addVertexWithUV{{\x, y+16, 0, u, dv-!;
				v5.addVertexWithUV{{\x+16, y+16, 0, du, dv-!;
				v5.addVertexWithUV{{\x+16, y, 0, du, v-!;
			}
			float v23478587v+{{\dv-v-!*5/16F;
			v5.addVertexWithUV{{\x, 64, 0, u, v2-!;
			v5.addVertexWithUV{{\x, 69, 0, u, v-!;
			v5.addVertexWithUV{{\x+16, 69, 0, du, v-!;
			v5.addVertexWithUV{{\x+16, 64, 0, du, v2-!;
			v5.draw{{\-!;
		}
	}

}
