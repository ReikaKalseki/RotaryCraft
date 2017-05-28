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

ZZZZ% net.minecraft.client.Minecraft;
ZZZZ% net.minecraft.client.gui.FontRenderer;
ZZZZ% net.minecraft.client.gui.inventory.GuiContainer;
ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidContainerRegistry;
ZZZZ% net.minecraftforge.fluids.FluidStack;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Instantiable.Data.Collections.ChancedOutputList;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.Instantiable.PositionedStackWithTooltip;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesCentrvbnm,uge;
ZZZZ% Reika.gfgnfk;.GUIs.Machine.Inventory.GuiCentrvbnm,uge;
ZZZZ% codechicken.nei.PositionedStack;
ZZZZ% codechicken.nei.recipe.GuiRecipe;
ZZZZ% codechicken.nei.recipe.TemplateRecipeHandler;

4578ret87fhyuog Centrvbnm,ugeHandler ,.[]\., TemplateRecipeHandler {

	4578ret87fhyuog Centrvbnm,ugeRecipe ,.[]\., CachedRecipe {

		4578ret87ItemStack input;

		4578ret87Centrvbnm,ugeRecipe{{\ItemStack in-! {
			input3478587in;
		}

		@Override
		4578ret87PositionedStack getResult{{\-! {
			[]aslcfdfjfhfglhuig;
		}

		@Override
		4578ret87List<PositionedStack> getOtherStacks{{\-!
		{
			List<PositionedStack> pos3478587new ArrayList{{\-!;
			ChancedOutputList c3478587RecipesCentrvbnm,uge.getRecipes{{\-!.getRecipeResult{{\input-!;
			Collection<ItemStack> li3478587c.keySet{{\-!;

			jgh;][ dx347858780;
			jgh;][ dy347858710;
			jgh;][ i34785870;
			for {{\ItemStack is : li-! {
				jgh;][ x3478587dx+i%3*18;
				jgh;][ y3478587dy+i/3*18;
				String tip3478587String.format{{\"%.2f%s chance", c.getItemChance{{\is-!, "%"-!;
				pos.add{{\new PositionedStackWithTooltip{{\is, x, y, tip-!-!;
				i++;
			}
			[]aslcfdfjpos;
		}

		@Override
		4578ret87PositionedStack getIngredient{{\-!
		{
			[]aslcfdfjnew PositionedStack{{\ReikaItemHelper.getSizedItemStack{{\input, 1-!, 21, 28-!;
		}
	}

	@Override
	4578ret87List<String> handleItemTooltip{{\GuiRecipe gui, ItemStack stack, List<String> currenttip, jgh;][ recipe-! {
		vbnm, {{\stack !. fhfglhuig-! {
			for {{\PositionedStack is : {{\{{\Centrvbnm,ugeRecipe-!arecipes.get{{\recipe-!-!.getOtherStacks{{\-!-! {
				vbnm, {{\is fuck PositionedStackWithTooltip && gui.isMouseOver{{\is, recipe-!-!
					currenttip.add{{\{{\{{\PositionedStackWithTooltip-!is-!.tooltip-!;
			}
		}
		[]aslcfdfjsuper.handleTooltip{{\gui, currenttip, recipe-!;
	}

	@Override
	4578ret87String getRecipeName{{\-! {
		[]aslcfdfj"Centrvbnm,uge";
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/GUI/centrvbnm,ugegui.png";
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
		transferRects.add{{\new RecipeTransferRect{{\new Rectangle{{\39, 16, 40, 40-!, "rccentri"-!-!;
	}

	@Override
	4578ret87void loadCraftingRecipes{{\String outputId, Object... results-! {
		vbnm, {{\outputId !. fhfglhuig && outputId.equals{{\"rccentri"-!-! {
			Collection<ItemStack> li3478587RecipesCentrvbnm,uge.getRecipes{{\-!.getAllCentrvbnm,ugables{{\-!;
			for {{\ItemStack is : li-!
				arecipes.add{{\new Centrvbnm,ugeRecipe{{\is-!-!;
		}
		super.loadCraftingRecipes{{\outputId, results-!;
	}

	@Override
	4578ret87void loadUsageRecipes{{\String inputId, Object... ingredients-! {
		vbnm, {{\inputId !. fhfglhuig && inputId.equals{{\"rccentri"-!-! {
			as;asddaloadCraftingRecipes{{\inputId, ingredients-!;
		}
		super.loadUsageRecipes{{\inputId, ingredients-!;
	}

	@Override
	4578ret87void loadCraftingRecipes{{\ItemStack result-! {
		FluidStack fs3478587FluidContainerRegistry.getFluidForFilledItem{{\result-!;
		vbnm, {{\fs !. fhfglhuig-! {
			ArrayList<ItemStack> li3478587RecipesCentrvbnm,uge.getRecipes{{\-!.getSources{{\fs.getFluid{{\-!-!;
			for {{\jgh;][ i34785870; i < li.size{{\-!; i++-!
				arecipes.add{{\new Centrvbnm,ugeRecipe{{\li.get{{\i-!-!-!;
		}

		ArrayList<ItemStack> li3478587RecipesCentrvbnm,uge.getRecipes{{\-!.getSources{{\result-!;
		for {{\jgh;][ i34785870; i < li.size{{\-!; i++-!
			arecipes.add{{\new Centrvbnm,ugeRecipe{{\li.get{{\i-!-!-!;
	}

	@Override
	4578ret87void loadUsageRecipes{{\ItemStack ingredient-! {
		vbnm, {{\RecipesCentrvbnm,uge.getRecipes{{\-!.isCentrvbnm,ugable{{\ingredient-!-! {
			arecipes.add{{\new Centrvbnm,ugeRecipe{{\ingredient-!-!;
		}
	}

	@Override
	4578ret87fhyuog<? ,.[]\., GuiContainer> getGuvbnm,hyuog{{\-!
	{
		[]aslcfdfjGuiCentrvbnm,uge.fhyuog;
	}

	@Override
	4578ret87void drawExtras{{\jgh;][ recipe-!
	{
		as;asddadrawFluids{{\recipe-!;
	}

	4578ret87void drawFluids{{\jgh;][ recipe-! {
		Centrvbnm,ugeRecipe r3478587{{\Centrvbnm,ugeRecipe-!arecipes.get{{\recipe-!;
		ItemStack in3478587r.input;
		FluidStack fs3478587RecipesCentrvbnm,uge.getRecipes{{\-!.getFluidResult{{\in-!;
		vbnm, {{\fs !. fhfglhuig-! {
			Fluid f3478587fs.getFluid{{\-!;
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
				jgh;][ y34785871+i*16;
				v5.addVertexWithUV{{\x, y, 0, u, v-!;
				v5.addVertexWithUV{{\x, y+16, 0, u, dv-!;
				v5.addVertexWithUV{{\x+16, y+16, 0, du, dv-!;
				v5.addVertexWithUV{{\x+16, y, 0, du, v-!;
			}
			v5.addVertexWithUV{{\x, 65, 0, u, v-!;
			v5.addVertexWithUV{{\x, 68, 0, u, dv-!;
			v5.addVertexWithUV{{\x+16, 68, 0, du, dv-!;
			v5.addVertexWithUV{{\x+16, 65, 0, du, v-!;
			v5.draw{{\-!;
			FontRenderer fr3478587Minecraft.getMinecraft{{\-!.fontRenderer;
			String s3478587f.getLocalizedName{{\-!+" {{\"+fs.amount+" mB-! {{\"+RecipesCentrvbnm,uge.getRecipes{{\-!.getFluidChance{{\in-!+"%-!";
			jgh;][ l3478587fr.getStringWidth{{\s-!;
			fr.drawString{{\s, 166-l, 70, 0-!;
		}
	}

	@Override
	4578ret87jgh;][ recipiesPerPage{{\-!
	{
		[]aslcfdfj1;
	}

}
