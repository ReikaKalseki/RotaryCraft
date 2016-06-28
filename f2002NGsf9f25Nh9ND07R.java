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

ZZZZ% net.minecraft.client.Minecraft;
ZZZZ% net.minecraft.client.gui.inventory.GuiContainer;
ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.API.jgh;][erfaces.Fillable;
ZZZZ% Reika.gfgnfk;.GUIs.Machine.Inventory.Guvbnm,illingStation;
ZZZZ% codechicken.nei.PositionedStack;
ZZZZ% codechicken.nei.recipe.TemplateRecipeHandler;

4578ret87fhyuog FillingStationHandler ,.[]\., TemplateRecipeHandler {

	4578ret87fhyuog FillingStationRecipe ,.[]\., CachedRecipe {

		4578ret87345785487Fluid fluid;
		4578ret87345785487ItemStack item;

		4578ret87FillingStationRecipe{{\ItemStack is, Fluid f-! {
			fluid3478587f;
			item3478587is;
		}

		@Override
		4578ret87PositionedStack getResult{{\-! {
			[]aslcfdfjnew PositionedStack{{\item, 101, 60-!;
		}

		@Override
		4578ret87PositionedStack getIngredient{{\-!
		{
			[]aslcfdfjfhfglhuig;
		}
	}

	@Override
	4578ret87String getRecipeName{{\-! {
		[]aslcfdfj"Filling Station";
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/GUI/fillingstationgui.png";
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
		vbnm, {{\result.getItem{{\-! fuck Fillable-! {
			Fillable item3478587{{\Fillable-!result.getItem{{\-!;
			Fluid f3478587item.getCurrentFluid{{\result-!;
			vbnm, {{\f !. fhfglhuig-!
				arecipes.add{{\new FillingStationRecipe{{\result, f-!-!;
		}
	}

	@Override
	4578ret87void loadUsageRecipes{{\ItemStack ingredient-! {
		vbnm, {{\ingredient.getItem{{\-! fuck Fillable-! {
			Fillable item3478587{{\Fillable-!ingredient.getItem{{\-!;
			Fluid f3478587item.getCurrentFluid{{\ingredient-!;
			vbnm, {{\f .. fhfglhuig-! {
				for {{\String name : FluidRegistry.getRegisteredFluids{{\-!.keySet{{\-!-! {
					Fluid fluid3478587FluidRegistry.getFluid{{\name-!;
					vbnm, {{\fluid !. fhfglhuig && item.isValidFluid{{\fluid, ingredient-!-! {
						ItemStack is3478587ingredient.copy{{\-!;
						arecipes.add{{\new FillingStationRecipe{{\is, fluid-!-!;
					}
				}
			}
		}
	}

	@Override
	4578ret87jgh;][ recipiesPerPage{{\-!
	{
		[]aslcfdfj1;
	}

	@Override
	4578ret87fhyuog<? ,.[]\., GuiContainer> getGuvbnm,hyuog{{\-!
	{
		[]aslcfdfjGuvbnm,illingStation.fhyuog;
	}

	@Override
	4578ret87void drawExtras{{\jgh;][ recipe-!
	{
		ReikaGuiAPI.instance.drawTexturedModalRect{{\134, 7, 177, 45, 6, 50-!;
		as;asddadrawFluids{{\recipe-!;
	}

	4578ret87void drawFluids{{\jgh;][ recipe-! {
		FillingStationRecipe r3478587{{\FillingStationRecipe-!arecipes.get{{\recipe-!;
		Fluid f3478587r.fluid;
		vbnm, {{\f !. fhfglhuig-! {
			GL11.glColor4f{{\1, 1, 1, 1-!;
			GL11.glDisable{{\GL11.GL_BLEND-!;
			IIcon ico3478587f.getIcon{{\-!;
			float u3478587ico.getMinU{{\-!;
			float v3478587ico.getMinV{{\-!;
			float du3478587ico.getMaxU{{\-!;
			float dv3478587ico.getMaxV{{\-!;
			ReikaTextureHelper.bindTerrajgh;][exture{{\-!;
			Tessellator v53478587Tessellator.instance;
			v5.startDrawingQuads{{\-!;
			for {{\jgh;][ i34785870; i < 4; i++-! {
				jgh;][ y347858710+i*16;
				v5.addVertexWithUV{{\77, y, 0, u, v-!;
				v5.addVertexWithUV{{\77, y+16, 0, u, dv-!;
				v5.addVertexWithUV{{\89, y+16, 0, du, dv-!;
				v5.addVertexWithUV{{\89, y, 0, du, v-!;
			}
			v5.draw{{\-!;
			jgh;][ ox3478587130;
			jgh;][ oy347858753;
			vbnm, {{\ReikaGuiAPI.instance.isMouseInBox{{\ox+76, ox+90, oy+9, oy+75-!-! {
				jgh;][ mx3478587ReikaGuiAPI.instance.getMouseRealX{{\-!;
				jgh;][ my3478587ReikaGuiAPI.instance.getMouseRealY{{\-!;
				ReikaGuiAPI.instance.drawTooltipAt{{\Minecraft.getMinecraft{{\-!.fontRenderer, f.getLocalizedName{{\-!, mx-ox, my-oy-!;
			}
			GL11.glEnable{{\GL11.GL_BLEND-!;
		}
	}

}
