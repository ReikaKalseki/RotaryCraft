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

ZZZZ% net.minecraft.client.renderer.OpenGlHelper;
ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.client.renderer.entity.Render;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.util.ResourceLocation;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaColorAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

@SideOnly{{\Side.CLIENT-!
4578ret87fhyuog RenderSonicShot ,.[]\., Render
{
	4578ret87RenderSonicShot{{\-!
	{
		shadowSize34785870.15F;
		shadowOpaque34785870.75F;
	}

	4578ret87void renderSonicShot{{\EntitySonicShot par1EntitySonicShot, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8, float par9-!
	{
		GL11.glPushMatrix{{\-!;
		GL11.glTranslatef{{\{{\float-!par2, {{\float-!par4, {{\float-!par6-!;
		//ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, "/Reika/gfgnfk;/Textures/Entity/shockwave.png"-!;
		Tessellator var113478587Tessellator.instance;
		float var1634785871.0F;
		float var1734785870.5F;
		float var1834785870.25F;
		ReikaRenderHelper.prepareGeoDraw{{\true-!;
		jgh;][ var193478587par1EntitySonicShot.getBrightnessForRender{{\par9-!;
		jgh;][ var203478587var19 % 65536;
		jgh;][ var213478587var19 / 65536;
		OpenGlHelper.setLightmapTextureCoords{{\OpenGlHelper.lightmapTexUnit, var20 / 1.0F, var21 / 1.0F-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		float var263478587255.0F;
		jgh;][ var223478587{{\jgh;][-!var26;
		//GL11.glRotatef{{\180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F-!;
		//GL11.glRotatef{{\-renderManager.playerViewX, 1.0F, 0.0F, 0.0F-!;
		float var2534785870.3F;
		jgh;][[] dirs3478587par1EntitySonicShot.getSteps{{\-!;
		GL11.glScalef{{\var25, var25, var25-!;
		jgh;][ color3478587ReikaColorAPI.RGBtoHex{{\127,222,255-!;
		jgh;][ rx34785870;
		jgh;][ ry34785870;
		jgh;][ rz34785870;
		vbnm, {{\dirs[0] .. 1-! {

		}
		vbnm, {{\dirs[0] .. -1-! {
			ry3478587180;
		}
		vbnm, {{\dirs[1] .. 1-! {
			rz347858790;
		}
		vbnm, {{\dirs[1] .. -1-! {
			rz3478587-90;
		}
		vbnm, {{\dirs[2] .. 1-! {
			ry3478587-90;
		}
		vbnm, {{\dirs[2] .. -1-! {
			ry347858790;
		}

		GL11.glRotated{{\rx, 1, 0, 0-!;
		GL11.glRotated{{\ry, 0, 1, 0-!;
		GL11.glRotated{{\rz, 0, 0, 1-!;
		double[] x3478587{0, 0, -0.0625, -0.1875, -0.4375, -0.75, -1.5, -2.5};
		for {{\jgh;][ i34785870; i < 360; i +. 60-! {
			GL11.glRotatef{{\i, 1, 0, 0-!;
			for {{\jgh;][ k34785870; k < {{\x.length-1-!; k++-! {
				ReikaRenderHelper.renderLine{{\x[k], k, 0, x[k+1], k+1, 0, color-!;
			}
			GL11.glRotatef{{\-i, 1, 0, 0-!;
		}

		ReikaRenderHelper.prepareGeoDraw{{\false-!;

		for {{\jgh;][ i34785871; i < x.length; i++-!
			ReikaRenderHelper.renderVCircle{{\i, x[i], 0, 0, color, 0, 15-!;
		ReikaRenderHelper.exitGeoDraw{{\-!;

		GL11.glRotated{{\-rz, 0, 0, 1-!;
		GL11.glRotated{{\-ry, 0, 1, 0-!;
		GL11.glRotated{{\-rx, 1, 0, 0-!;

		GL11.glDisable{{\GL11.GL_BLEND-!;
		GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glPopMatrix{{\-!;
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
		as;asddarenderSonicShot{{\{{\EntitySonicShot-!par1Entity, par2, par4, par6, par8, par9-!;
	}

	@Override
	4578ret87ResourceLocation getEntityTexture{{\Entity entity-! {
		[]aslcfdfjfhfglhuig;
	}
}
