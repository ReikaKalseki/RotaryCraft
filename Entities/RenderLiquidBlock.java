/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Entities;

import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLiquidBlock extends Render
{
	public RenderLiquidBlock()
	{
		shadowSize = 0.15F;
		shadowOpaque = 0.75F;
	}

	public void renderLiquidBlock(EntityLiquidBlock e, double par2, double par4, double par6, float par8, float par9)
	{
		GL11.glTranslated(par2, par4, par6);
		GL11.glColor3f(1, 1, 1);
		Tessellator v5 = Tessellator.instance;
		RenderHelper.disableStandardItemLighting();
		IIcon ico;
		Fluid f = e.getFluid();
		if (f != null) {
			ico = e.getFluid().getIcon();
			ReikaLiquidRenderer.bindFluidTexture(f);
		}
		else {
			ico = Blocks.grass.getIcon(0, 0);
			ReikaTextureHelper.bindTerrainTexture();
		}
		float u = ico.getMinU();
		float v = ico.getMinV();
		float du = ico.getMaxU();
		float dv = ico.getMaxV();

		float color = 1;

		color = 0.5F;
		GL11.glColor3f(color, color, color);
		v5.startDrawingQuads();
		v5.addVertexWithUV(0, 0, 0, u, v);
		v5.addVertexWithUV(1, 0, 0, du, v);
		v5.addVertexWithUV(1, 0, 1, du, dv);
		v5.addVertexWithUV(0, 0, 1, u, dv);
		v5.draw();

		color = 1F;
		GL11.glColor3f(color, color, color);
		v5.startDrawingQuads();
		v5.addVertexWithUV(0, 1, 1, u, dv);
		v5.addVertexWithUV(1, 1, 1, du, dv);
		v5.addVertexWithUV(1, 1, 0, du, v);
		v5.addVertexWithUV(0, 1, 0, u, v);
		v5.draw();

		color = 0.6F;
		GL11.glColor3f(color, color, color);
		v5.startDrawingQuads();
		v5.addVertexWithUV(0, 1, 0, u, dv);
		v5.addVertexWithUV(1, 1, 0, du, dv);
		v5.addVertexWithUV(1, 0, 0, du, v);
		v5.addVertexWithUV(0, 0, 0, u, v);
		v5.draw();

		color = 0.8F;
		GL11.glColor3f(color, color, color);
		v5.startDrawingQuads();
		v5.addVertexWithUV(0, 0, 1, u, v);
		v5.addVertexWithUV(1, 0, 1, du, v);
		v5.addVertexWithUV(1, 1, 1, du, dv);
		v5.addVertexWithUV(0, 1, 1, u, dv);
		v5.draw();

		color = 0.8F;
		GL11.glColor3f(color, color, color);
		v5.startDrawingQuads();
		v5.addVertexWithUV(0, 0, 0, u, v);
		v5.addVertexWithUV(0, 0, 1, du, v);
		v5.addVertexWithUV(0, 1, 1, du, dv);
		v5.addVertexWithUV(0, 1, 0, u, dv);
		v5.draw();

		color = 0.6F;
		GL11.glColor3f(color, color, color);
		v5.startDrawingQuads();
		v5.addVertexWithUV(1, 1, 0, u, dv);
		v5.addVertexWithUV(1, 1, 1, du, dv);
		v5.addVertexWithUV(1, 0, 1, du, v);
		v5.addVertexWithUV(1, 0, 0, u, v);
		v5.draw();

		GL11.glTranslated(-par2, -par4, -par6);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		this.renderLiquidBlock((EntityLiquidBlock)par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}
}