/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.NEI;

import static codechicken.core.gui.GuiDraw.drawTexturedModalRect;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiFridge;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class FridgeHandler extends TemplateRecipeHandler {

	public class FridgeRecipe extends CachedRecipe {


		public FridgeRecipe() {

		}

		@Override
		public PositionedStack getResult() {
			return new PositionedStack(ItemStacks.dryice, 127, 62);
		}

		@Override
		public PositionedStack getIngredient()
		{
			return new PositionedStack(new ItemStack(Block.ice), 94, 48);
		}
	}

	@Override
	public String getRecipeName() {
		return "Refrigeration Unit";
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/fridgegui.png";
	}

	@Override
	public void drawBackground(int recipe)
	{
		GL11.glColor4f(1, 1, 1, 1);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, this.getGuiTexture());
		drawTexturedModalRect(0, 1, 5, 11, 166, 70);
	}

	@Override
	public void drawForeground(int recipe)
	{
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glDisable(GL11.GL_LIGHTING);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, this.getGuiTexture());
		this.drawExtras(recipe);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if (ReikaItemHelper.matchStacks(result, ItemStacks.dryice))
			arecipes.add(new FridgeRecipe());
		if (ReikaItemHelper.matchStacks(result, ItemStacks.nitrogenbucket))
			arecipes.add(new FridgeRecipe());
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (ingredient.itemID == Block.ice.blockID)
			arecipes.add(new FridgeRecipe());
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiFridge.class;
	}

	@Override
	public void drawExtras(int recipe)
	{
		this.drawFluids(recipe);
	}

	private void drawFluids(int recipe) {
		Fluid f = FluidRegistry.getFluid("liquid nitrogen");
		if (f != null) {
			Icon ico = f.getIcon();
			float u = ico.getMinU();
			float v = ico.getMinV();
			float du = ico.getMaxU();
			float dv = ico.getMaxV();
			ReikaTextureHelper.bindTerrainTexture();
			Tessellator v5 = Tessellator.instance;
			v5.startDrawingQuads();
			int x = 147;
			for (int i = 0; i < 4; i++) {
				int y = 7+i*16;
				v5.addVertexWithUV(x, y, 0, u, v);
				v5.addVertexWithUV(x, y+16, 0, u, dv);
				v5.addVertexWithUV(x+16, y+16, 0, du, dv);
				v5.addVertexWithUV(x+16, y, 0, du, v);
			}
			float v2 = v+(dv-v)*5/16F;
			v5.addVertexWithUV(x, 64, 0, u, v2);
			v5.addVertexWithUV(x, 69, 0, u, v);
			v5.addVertexWithUV(x+16, 69, 0, du, v);
			v5.addVertexWithUV(x+16, 64, 0, du, v2);
			v5.draw();
		}
	}

}
