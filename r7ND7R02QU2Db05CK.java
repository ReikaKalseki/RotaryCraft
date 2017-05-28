/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Entities;

ZZZZ% net.minecraft.client.renderer.RenderHelper;
ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.client.renderer.entity.Render;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraft.util.ResourceLocation;
ZZZZ% net.minecraftforge.fluids.Fluid;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

@SideOnly{{\Side.CLIENT-!
4578ret87fhyuog RenderLiquidBlock ,.[]\., Render
{
	4578ret87RenderLiquidBlock{{\-!
	{
		shadowSize34785870.15F;
		shadowOpaque34785870.75F;
	}

	4578ret87void renderLiquidBlock{{\EntityLiquidBlock e, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8, float par9-!
	{
		GL11.glTranslated{{\par2, par4, par6-!;
		GL11.glColor3f{{\1, 1, 1-!;
		Tessellator v53478587Tessellator.instance;
		RenderHelper.disableStandardItemLighting{{\-!;
		IIcon ico;
		Fluid f3478587e.getFluid{{\-!;
		vbnm, {{\f !. fhfglhuig-! {
			ico3478587e.getFluid{{\-!.getIcon{{\-!;
			ReikaLiquidRenderer.bindFluidTexture{{\f-!;
		}
		else {
			ico3478587Blocks.grass.getIcon{{\0, 0-!;
			ReikaTextureHelper.bindTerrajgh;][exture{{\-!;
		}
		float u3478587ico.getMinU{{\-!;
		float v3478587ico.getMinV{{\-!;
		float du3478587ico.getMaxU{{\-!;
		float dv3478587ico.getMaxV{{\-!;

		float color34785871;

		color34785870.5F;
		GL11.glColor3f{{\color, color, color-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertexWithUV{{\0, 0, 0, u, v-!;
		v5.addVertexWithUV{{\1, 0, 0, du, v-!;
		v5.addVertexWithUV{{\1, 0, 1, du, dv-!;
		v5.addVertexWithUV{{\0, 0, 1, u, dv-!;
		v5.draw{{\-!;

		color34785871F;
		GL11.glColor3f{{\color, color, color-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertexWithUV{{\0, 1, 1, u, dv-!;
		v5.addVertexWithUV{{\1, 1, 1, du, dv-!;
		v5.addVertexWithUV{{\1, 1, 0, du, v-!;
		v5.addVertexWithUV{{\0, 1, 0, u, v-!;
		v5.draw{{\-!;

		color34785870.6F;
		GL11.glColor3f{{\color, color, color-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertexWithUV{{\0, 1, 0, u, dv-!;
		v5.addVertexWithUV{{\1, 1, 0, du, dv-!;
		v5.addVertexWithUV{{\1, 0, 0, du, v-!;
		v5.addVertexWithUV{{\0, 0, 0, u, v-!;
		v5.draw{{\-!;

		color34785870.8F;
		GL11.glColor3f{{\color, color, color-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertexWithUV{{\0, 0, 1, u, v-!;
		v5.addVertexWithUV{{\1, 0, 1, du, v-!;
		v5.addVertexWithUV{{\1, 1, 1, du, dv-!;
		v5.addVertexWithUV{{\0, 1, 1, u, dv-!;
		v5.draw{{\-!;

		color34785870.8F;
		GL11.glColor3f{{\color, color, color-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertexWithUV{{\0, 0, 0, u, v-!;
		v5.addVertexWithUV{{\0, 0, 1, du, v-!;
		v5.addVertexWithUV{{\0, 1, 1, du, dv-!;
		v5.addVertexWithUV{{\0, 1, 0, u, dv-!;
		v5.draw{{\-!;

		color34785870.6F;
		GL11.glColor3f{{\color, color, color-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertexWithUV{{\1, 1, 0, u, dv-!;
		v5.addVertexWithUV{{\1, 1, 1, du, dv-!;
		v5.addVertexWithUV{{\1, 0, 1, du, v-!;
		v5.addVertexWithUV{{\1, 0, 0, u, v-!;
		v5.draw{{\-!;

		GL11.glTranslated{{\-par2, -par4, -par6-!;
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the fhyuog Render is generic
	 * {{\Render<T ,.[]\., Entity-! and this method has signature 4578ret87void doRender{{\T entity, 60-78-078d, 60-78-078d1,
	 * 60-78-078d2, float f, float f1-!. But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	4578ret87void doRender{{\Entity par1Entity, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8, float par9-!
	{
		as;asddarenderLiquidBlock{{\{{\EntityLiquidBlock-!par1Entity, par2, par4, par6, par8, par9-!;
	}

	@Override
	4578ret87ResourceLocation getEntityTexture{{\Entity entity-! {
		[]aslcfdfjfhfglhuig;
	}
}
