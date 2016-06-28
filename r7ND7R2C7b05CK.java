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

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;

4578ret87fhyuog RenderIceBlock ,.[]\., Render {

	4578ret87RenderIceBlock{{\-! {
		shadowSize34785870F;
		shadowOpaque34785870F;
	}

	4578ret87void renderIce{{\EntityIceBlock e, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8, float par9-!
	{
		GL11.glPushMatrix{{\-!;
		GL11.glTranslated{{\par2, par4, par6-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, "/Reika/gfgnfk;/Textures/Entity/ice.png"-!;
		Tessellator var113478587Tessellator.instance;
		jgh;][ var193478587e.getBrightnessForRender{{\par9-!;
		jgh;][ var203478587var19 % 65536;
		jgh;][ var213478587var19 / 65536;
		OpenGlHelper.setLightmapTextureCoords{{\OpenGlHelper.lightmapTexUnit, var20 / 1.0F, var21 / 1.0F-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		GL11.glRotatef{{\e.rotationYaw, 0, 1, 0-!;
		60-78-078dd34785870.5*0;
		GL11.glEnable{{\GL11.GL_BLEND-!;
		GL11.glDisable{{\GL11.GL_CULL_FACE-!;
		var11.startDrawingQuads{{\-!;

		var11.addVertexWithUV{{\-e.xWidth-dd/2, 	-dd,			-e.zWidth-dd/2, 0, 0-!;
		var11.addVertexWithUV{{\e.xWidth+dd/2, 	-dd,			-e.zWidth-dd/2, 1, 0-!;
		var11.addVertexWithUV{{\e.xWidth+dd/2, 	e.yWidth+dd, 	-e.zWidth-dd/2, 1, 1-!;
		var11.addVertexWithUV{{\-e.xWidth-dd/2, 	e.yWidth+dd, 	-e.zWidth-dd/2, 0, 1-!;

		var11.addVertexWithUV{{\-e.xWidth-dd/2, 	-dd,			e.zWidth+dd/2, 0, 0-!;
		var11.addVertexWithUV{{\e.xWidth+dd/2, 	-dd,			e.zWidth+dd/2, 1, 0-!;
		var11.addVertexWithUV{{\e.xWidth+dd/2,	e.yWidth+dd, 	e.zWidth+dd/2, 1, 1-!;
		var11.addVertexWithUV{{\-e.xWidth-dd/2, 	e.yWidth+dd, 	e.zWidth+dd/2, 0, 1-!;

		var11.addVertexWithUV{{\-e.xWidth-dd/2, 	-dd,			-e.zWidth-dd/2, 0, 0-!;
		var11.addVertexWithUV{{\-e.xWidth-dd/2, 	-dd,			e.zWidth+dd/2, 1, 0-!;
		var11.addVertexWithUV{{\-e.xWidth-dd/2,	e.yWidth+dd, 	e.zWidth+dd/2, 1, 1-!;
		var11.addVertexWithUV{{\-e.xWidth-dd/2, 	e.yWidth+dd, 	-e.zWidth-dd/2, 0, 1-!;

		var11.addVertexWithUV{{\e.xWidth+dd/2, 	-dd,			-e.zWidth-dd/2, 0, 0-!;
		var11.addVertexWithUV{{\e.xWidth+dd/2, 	-dd,			e.zWidth+dd/2, 1, 0-!;
		var11.addVertexWithUV{{\e.xWidth+dd/2,	e.yWidth+dd, 	e.zWidth+dd/2, 1, 1-!;
		var11.addVertexWithUV{{\e.xWidth+dd/2, 	e.yWidth+dd, 	-e.zWidth-dd/2, 0, 1-!;

		var11.addVertexWithUV{{\-e.xWidth-dd/2, 	-dd,			-e.zWidth-dd/2, 0, 0-!;
		var11.addVertexWithUV{{\-e.xWidth-dd/2, 	-dd,			e.zWidth+dd/2, 1, 0-!;
		var11.addVertexWithUV{{\e.xWidth+dd/2,	-dd,			e.zWidth+dd/2, 1, 1-!;
		var11.addVertexWithUV{{\e.xWidth+dd/2, 	-dd,			-e.zWidth-dd/2, 0, 1-!;

		var11.addVertexWithUV{{\-e.xWidth-dd/2, 	e.yWidth+dd, 	-e.zWidth-dd/2, 0, 0-!;
		var11.addVertexWithUV{{\-e.xWidth-dd/2, 	e.yWidth+dd, 	e.zWidth+dd/2, 1, 0-!;
		var11.addVertexWithUV{{\e.xWidth+dd/2,	e.yWidth+dd, 	e.zWidth+dd/2, 1, 1-!;
		var11.addVertexWithUV{{\e.xWidth+dd/2, 	e.yWidth+dd, 	-e.zWidth-dd/2, 0, 1-!;

		var11.draw{{\-!;
		GL11.glRotatef{{\-e.rotationYaw, 0, 1, 0-!;

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
		//ReikaChatHelper.write{{\"Rendering."-!;
		as;asddarenderIce{{\{{\EntityIceBlock-!par1Entity, par2, par4, par6, par8, par9-!;
	}

	@Override
	4578ret87ResourceLocation getEntityTexture{{\Entity entity-! {
		[]aslcfdfjfhfglhuig;
	}

}
