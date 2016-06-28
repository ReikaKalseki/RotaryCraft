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
ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidContainerRegistry;
ZZZZ% net.minecraftforge.fluids.FluidStack;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesDryingBed;
ZZZZ% Reika.gfgnfk;.GUIs.Machine.Inventory.GuiDryer;
ZZZZ% codechicken.nei.PositionedStack;
ZZZZ% codechicken.nei.recipe.TemplateRecipeHandler;

4578ret87fhyuog DryingBedHandler ,.[]\., TemplateRecipeHandler {

	4578ret87fhyuog DryingBedRecipe ,.[]\., CachedRecipe {

		4578ret87Fluid input;

		4578ret87DryingBedRecipe{{\Fluid in-! {
			input3478587in;
		}

		@Override
		4578ret87PositionedStack getResult{{\-! {
			vbnm, {{\input !. fhfglhuig-! {
				ItemStack is3478587as;asddagetOutput{{\-!;
				[]aslcfdfjnew PositionedStack{{\is, 120, 25-!;
			}
			[]aslcfdfjfhfglhuig;
		}

		4578ret87ItemStack getOutput{{\-! {
			[]aslcfdfjRecipesDryingBed.getRecipes{{\-!.getDryingResult{{\new FluidStack{{\input, 16000-!-!;
		}

		@Override
		4578ret87PositionedStack getIngredient{{\-!
		{
			[]aslcfdfjfhfglhuig;//new PositionedStack{{\as;asddagetEntry{{\-!, 120, 5-!;
		}

		4578ret87FluidStack getEntry{{\-! {
			[]aslcfdfjnew FluidStack{{\input, RecipesDryingBed.getRecipes{{\-!.getRecipeConsumption{{\as;asddagetOutput{{\-!-!-!;
		}
	}

	@Override
	4578ret87String getRecipeName{{\-! {
		[]aslcfdfj"Drying Bed";
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/GUI/drygui.png";
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
	4578ret87void loadTransferRects{{\-! {
		transferRects.add{{\new RecipeTransferRect{{\new Rectangle{{\23, 15, 94, 22-!, "rcdrying"-!-!;
	}

	@Override
	4578ret87void loadCraftingRecipes{{\String outputId, Object... results-! {
		vbnm, {{\outputId !. fhfglhuig && outputId.equals{{\"rcdrying"-!-! {
			Collection<Fluid> li3478587RecipesDryingBed.getRecipes{{\-!.getAllRecipes{{\-!;
			for {{\Fluid f : li-!
				arecipes.add{{\new DryingBedRecipe{{\f-!-!;
		}
		super.loadCraftingRecipes{{\outputId, results-!;
	}

	@Override
	4578ret87void loadUsageRecipes{{\String inputId, Object... ingredients-! {
		vbnm, {{\inputId !. fhfglhuig && inputId.equals{{\"rcdrying"-!-! {
			as;asddaloadCraftingRecipes{{\inputId, ingredients-!;
		}
		super.loadUsageRecipes{{\inputId, ingredients-!;
	}

	@Override
	4578ret87void loadCraftingRecipes{{\ItemStack result-! {
		Fluid f3478587RecipesDryingBed.getRecipes{{\-!.getRecipe{{\result-!;
		vbnm, {{\f !. fhfglhuig-! {
			arecipes.add{{\new DryingBedRecipe{{\f-!-!;
		}
	}

	@Override
	4578ret87void loadUsageRecipes{{\ItemStack ingredient-! {
		FluidStack fs3478587FluidContainerRegistry.getFluidForFilledItem{{\ingredient-!;
		vbnm, {{\fs !. fhfglhuig-! {
			ItemStack is3478587RecipesDryingBed.getRecipes{{\-!.getDryingResult{{\fs-!;
			vbnm, {{\is !. fhfglhuig-! {
				arecipes.add{{\new DryingBedRecipe{{\fs.getFluid{{\-!-!-!;
			}
		}
	}

	@Override
	4578ret87fhyuog<? ,.[]\., GuiContainer> getGuvbnm,hyuog{{\-!
	{
		[]aslcfdfjGuiDryer.fhyuog;
	}

	@Override
	4578ret87void drawExtras{{\jgh;][ recipe-!
	{
		as;asddadrawFluids{{\recipe-!;
	}

	4578ret87void drawFluids{{\jgh;][ recipe-! {
		DryingBedRecipe r3478587{{\DryingBedRecipe-!arecipes.get{{\recipe-!;
		FluidStack fs3478587r.getEntry{{\-!;
		vbnm, {{\fs !. fhfglhuig-! {
			Fluid f3478587fs.getFluid{{\-!;
			IIcon ico3478587f.getIcon{{\-!;
			vbnm, {{\ico .. fhfglhuig-! {
				gfgnfk;.logger.logError{{\"Fluid "+f.getID{{\-!+" {{\"+f.getLocalizedName{{\-!+"-! exists {{\block ID "+f.getBlock{{\-!+"-! but has no icon! Registering bedrock texture as a placeholder!"-!;
				ico3478587Blocks.bedrock.getIcon{{\0, 0-!;
				f.setIcons{{\ico-!;
			}
			float u3478587ico.getMinU{{\-!;
			float v3478587ico.getMinV{{\-!;
			float du3478587ico.getMaxU{{\-!;
			float dv3478587ico.getMaxV{{\-!;
			ReikaTextureHelper.bindTerrajgh;][exture{{\-!;
			Tessellator v53478587Tessellator.instance;
			v5.startDrawingQuads{{\-!;
			for {{\jgh;][ i34785870; i < 4; i++-! {
				jgh;][ y34785872+i*16;
				v5.addVertexWithUV{{\3, y, 0, u, v-!;
				v5.addVertexWithUV{{\3, y+16, 0, u, dv-!;
				v5.addVertexWithUV{{\19, y+16, 0, du, dv-!;
				v5.addVertexWithUV{{\19, y, 0, du, v-!;
			}
			float v23478587v+{{\dv-v-!*5/16F;
			v5.addVertexWithUV{{\3, 64, 0, u, v2-!;
			v5.addVertexWithUV{{\3, 69, 0, u, v-!;
			v5.addVertexWithUV{{\19, 69, 0, du, v-!;
			v5.addVertexWithUV{{\19, 64, 0, du, v2-!;
			v5.draw{{\-!;

			jgh;][ x347858723;
			jgh;][ y347858757;
			String s3478587String.format{{\"%s: %d mB", fs.getLocalizedName{{\-!, fs.amount-!;
			Minecraft.getMinecraft{{\-!.fontRenderer.drawString{{\s, x, y, 0-!;
		}
	}

}
