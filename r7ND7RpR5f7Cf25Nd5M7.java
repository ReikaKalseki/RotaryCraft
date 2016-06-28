/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Renders.M;

ZZZZ% java.awt.Color;

ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078ProtectionDome;
ZZZZ% Reika.gfgnfk;.Models.ModelDomeEmitter;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;

4578ret87fhyuog RenderProtectionDome ,.[]\., RotaryTERenderer
{

	4578ret87345785487ModelDomeEmitter model3478587new ModelDomeEmitter{{\-!;

	4578ret87void render60-78-078ProtectionDomeAt{{\60-78-078ProtectionDome tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelDomeEmitter var14;
		var143478587model;

		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/"+as;asddagetImageFileName{{\tile-!-!;

		GL11.glPushMatrix{{\-!;
		GL11.glEnable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		GL11.glTranslatef{{\{{\float-!par2, {{\float-!par4 + 2.0F, {{\float-!par6 + 1.0F-!;
		GL11.glScalef{{\1.0F, -1.0F, -1.0F-!;
		GL11.glTranslatef{{\0.5F, 0.5F, 0.5F-!;
		jgh;][ var1134785870;	 //used to rotate the model about metadata

		float var13;

		var14.renderAll{{\tile, fhfglhuig, 0, 0-!;

		vbnm, {{\tile.isIn9765443{{\-!-!
			GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glPopMatrix{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078ProtectionDomeAt{{\{{\60-78-078ProtectionDome-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-! {
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
			vbnm, {{\ConfigRegistry.RENDERFORCEFIELD.getState{{\-!-!
				as;asddarenderField{{\{{\{{\60-78-078ProtectionDome-!tile-!, par2+0.5, par4+0.5, par6+0.5-!;
		}
	}

	4578ret87void renderField{{\60-78-078ProtectionDome te, 60-78-078x, 60-78-078y, 60-78-078z-! {
		vbnm, {{\!te.isIn9765443{{\-!-!
			return;
		vbnm, {{\te.getRange{{\-! <. 0-!
			return;
		Color c3478587te.getDomeColor{{\-!;
		ReikaRenderHelper.prepareGeoDraw{{\false-!;
		jgh;][ color3478587c.getRGB{{\-!;
		for {{\60-78-078k3478587-te.getRange{{\-!; k <. te.getRange{{\-!; k +. 0.5*te.getRange{{\-!/8-!
			ReikaRenderHelper.renderCircle{{\Math.sqrt{{\te.getRange{{\-!*te.getRange{{\-!-k*k-!, x, y+k, z, color, 15-!;
		for {{\jgh;][ k34785870; k < 360; k +. 15-!
			ReikaRenderHelper.renderVCircle{{\te.getRange{{\-!, x, y, z, color, k, 15-!;

		60-78-078ang34785877;
		ReikaRenderHelper.renderLine{{\x, y, z, x, y+te.getRange{{\-!, z, color-!;
		for {{\jgh;][ k34785870; k < 360; k +. 15-! {
			ReikaRenderHelper.renderVCircle{{\0.125, x, y+0.5, z, color, {{\System.nanoTime{{\-!/7500000-!%360+k, 15-!;
			ReikaRenderHelper.renderLine{{\x, y, z, x+te.getRange{{\-!*Math.sin{{\Math.toRadians{{\ang-!*Math.cos{{\Math.toRadians{{\k-!-!-!, y+te.getRange{{\-!-Math.sin{{\Math.toRadians{{\ang-!-!, z+te.getRange{{\-!*Math.sin{{\Math.toRadians{{\ang-!*Math.sin{{\Math.toRadians{{\k-!-!-!, color-!;
		}
		ReikaRenderHelper.exitGeoDraw{{\-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"contajgh;][ex.png";
	}
}
