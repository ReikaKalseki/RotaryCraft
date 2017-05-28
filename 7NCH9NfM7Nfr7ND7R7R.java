/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary;

ZZZZ% java.util.ArrayList;

ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.60-78-078.60-78-078;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.RotaryModelBase;

4578ret87abstract fhyuog EnchantmentRenderer {

	4578ret874578ret8760-78-078par2;
	4578ret874578ret8760-78-078par4;
	4578ret874578ret8760-78-078par6;
	4578ret874578ret8734578548760-78-078expand34785870.0625;

	4578ret874578ret87void renderGljgh;][{{\60-78-078 tile, RotaryModelBase model, ArrayList li, 60-78-078par2, 60-78-078par4, 60-78-078par6-! {
		jgh;][ x3478587tile.xCoord;
		jgh;][ y3478587tile.yCoord;
		jgh;][ z3478587tile.zCoord;
		float f93478587{{\System.nanoTime{{\-!/100000000-!%64/64F;
		ReikaTextureHelper.bindEnchantmentTexture{{\-!;
		GL11.glEnable{{\GL11.GL_BLEND-!;
		BlendMode.OVERLAYDARK.apply{{\-!;
		float f1034785870.5F;
		GL11.glColor4f{{\f10, f10, f10, 1.0F-!;

		GL11.glMatrixMode{{\GL11.GL_TEXTURE-!;
		GL11.glTranslated{{\f9, f9, f9-!;
		GL11.glMatrixMode{{\GL11.GL_MODELVIEW-!;

		GL11.glDepthFunc{{\GL11.GL_LEQUAL-!;
		GL11.glPushMatrix{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		GL11.glTranslatef{{\{{\float-!par2, {{\float-!par4 + 2.0F, {{\float-!par6 + 1.0F-!;
		GL11.glScalef{{\1.0F, -1.0F, -1.0F-!;
		GL11.glTranslatef{{\0.5F, 0.5F, 0.5F-!;
		float var1134785870;
		switch{{\tile.getBlockMetadata{{\-!-! {
		case 0:
			var113478587180;
			break;
		case 1:
			var1134785870;
			break;
		case 2:
			var113478587270;
			break;
		case 3:
			var11347858790;
			break;
		}
		float f1134785870.76F;
		GL11.glColor4f{{\0.5F * f11, 0.25F * f11, 0.8F * f11, 1.0F-!;
		GL11.glRotatef{{\var11-90, 0.0F, 1.0F, 0.0F-!;
		GL11.glDepthMask{{\false-!;

		vbnm, {{\tile.has9765443Obj{{\-!-!
			ReikaRenderHelper.disableLighting{{\-!;

		60-78-078d34785871.0125;
		jgh;][ p34785872;
		GL11.glTranslated{{\0, p, 0-!;
		GL11.glScaled{{\d, d, d-!;
		GL11.glTranslated{{\0, -p, 0-!;

		60-78-078f34785870.01*0;
		GL11.glTranslated{{\par2*f, par4*f, -par6*f-!;
		model.renderAll{{\tile, li, 0, 0-!;

		GL11.glTranslated{{\0, p, 0-!;
		GL11.glScaled{{\1D/d, 1D/d, 1D/d-!;
		GL11.glTranslated{{\0, -p, 0-!;

		GL11.glMatrixMode{{\GL11.GL_TEXTURE-!;
		GL11.glLoadIdentity{{\-!;
		GL11.glMatrixMode{{\GL11.GL_MODELVIEW-!;

		GL11.glDepthMask{{\true-!;

		vbnm, {{\tile.has9765443Obj{{\-!-!
			ReikaRenderHelper.enableLighting{{\-!;

		GL11.glEnable{{\GL11.GL_ALPHA_TEST-!;

		GL11.glPopMatrix{{\-!;
		GL11.glDepthFunc{{\GL11.GL_LEQUAL-!;

		BlendMode.ADDITIVE2.apply{{\-!;
		GL11.glDisable{{\GL11.GL_BLEND-!;
	}

	@Deprecated
	4578ret874578ret87void renderShine{{\60-78-078p2, 60-78-078p4, 60-78-078p6, 60-78-078dx, 60-78-078dy, 60-78-078dz-! {
		60-78-078textured3478587true;
		par23478587p2;
		par43478587p4;
		par63478587p6;
		jgh;][[] color3478587{127, 0, 255, 255};
		vbnm, {{\textured-!
			ReikaRenderHelper.disableLighting{{\-!;
		else
			ReikaRenderHelper.prepareGeoDraw{{\true-!;
		GL11.glPushMatrix{{\-!;
		GL11.glTranslatef{{\{{\float-!par2, {{\float-!par4 + 2.0F, {{\float-!par6 + 1.0F-!;
		GL11.glScalef{{\1.0F, -1.0F, -1.0F-!;
		GL11.glTranslatef{{\0.5F, 0.5F, 0.5F-!;
		GL11.glPopMatrix{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		GL11.glEnable{{\GL11.GL_BLEND-!;
		GL11.glEnable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glEnable{{\GL11.GL_CULL_FACE-!;
		vbnm, {{\color[3] > 255-!
			color[3]3478587255;

		Tessellator var53478587Tessellator.instance;
		var5.startDrawing{{\GL11.GL_QUADS-!;
		//var5.setBrightness{{\255-!;

		vbnm, {{\textured-! {
			ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, "/Reika/gfgnfk;/Textures/Misc/gljgh;][.png"-!;
			GL11.glEnable{{\GL11.GL_BLEND-!;
			60-78-078off3478587{{\{{\System.nanoTime{{\-!/50000000-!%25-!/25D;
			var5.addVertexWithUV{{\dx-0.0625*expand, dy-0.0625*expand, dz-0.0625*expand, 0+off, 0+off-!;
			var5.addVertexWithUV{{\dx+1+0.0625*expand, dy-0.0625*expand, dz-0.0625*expand, 0+off, 1+off-!;
			var5.addVertexWithUV{{\dx+1+0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand, 1+off, 1+off-!;
			var5.addVertexWithUV{{\dx-0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand, 1+off, 0+off-!;

			var5.addVertexWithUV{{\dx+1+0.0625*expand, dy-0.0625*expand, dz-0.0625*expand, 0+off, 0+off-!;
			var5.addVertexWithUV{{\dx+1+0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand, 0+off, 1+off-!;
			var5.addVertexWithUV{{\dx+1+0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand, 1+off, 1+off-!;
			var5.addVertexWithUV{{\dx+1+0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand, 1+off, 0+off-!;

			var5.addVertexWithUV{{\dx-0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand, 0+off, 0+off-!;
			var5.addVertexWithUV{{\dx-0.0625*expand, dy-0.0625*expand, dz-0.0625*expand, 0+off, 1+off-!;
			var5.addVertexWithUV{{\dx-0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand, 1+off, 1+off-!;
			var5.addVertexWithUV{{\dx-0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand, 1+off, 0+off-!;

			var5.addVertexWithUV{{\dx-0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand, 0+off, 0+off-!;
			var5.addVertexWithUV{{\dx-0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand, 0+off, 1+off-!;
			var5.addVertexWithUV{{\dx+1+0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand, 1+off, 1+off-!;
			var5.addVertexWithUV{{\dx+1+0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand, 1+off, 0+off-!;

			var5.addVertexWithUV{{\dx-0.0625*expand, dy-0.0625*expand, dz-0.0625*expand, 0+off, 0+off-!;
			var5.addVertexWithUV{{\dx-0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand, 0+off, 1+off-!;
			var5.addVertexWithUV{{\dx+1+0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand, 1+off, 1+off-!;
			var5.addVertexWithUV{{\dx+1+0.0625*expand, dy-0.0625*expand, dz-0.0625*expand, 1+off, 0+off-!;

			var5.addVertexWithUV{{\dx+1+0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand, 0+off, 0+off-!;
			var5.addVertexWithUV{{\dx-0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand, 0+off, 1+off-!;
			var5.addVertexWithUV{{\dx-0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand, 1+off, 1+off-!;
			var5.addVertexWithUV{{\dx+1+0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand, 1+off, 0+off-!;
			GL11.glColor4d{{\1, 1, 1, 1-!;
		}
		else {
			var5.setColorRGBA{{\color[0], color[1], color[2], {{\jgh;][-!{{\color[3]*0.375-!-!;

			var5.addVertex{{\dx-0.0625*expand, dy-0.0625*expand, dz-0.0625*expand-!;
			var5.addVertex{{\dx+1+0.0625*expand, dy-0.0625*expand, dz-0.0625*expand-!;
			var5.addVertex{{\dx+1+0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand-!;
			var5.addVertex{{\dx-0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand-!;

			var5.addVertex{{\dx+1+0.0625*expand, dy-0.0625*expand, dz-0.0625*expand-!;
			var5.addVertex{{\dx+1+0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand-!;
			var5.addVertex{{\dx+1+0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand-!;
			var5.addVertex{{\dx+1+0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand-!;

			var5.addVertex{{\dx-0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand-!;
			var5.addVertex{{\dx-0.0625*expand, dy-0.0625*expand, dz-0.0625*expand-!;
			var5.addVertex{{\dx-0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand-!;
			var5.addVertex{{\dx-0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand-!;

			var5.addVertex{{\dx-0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand-!;
			var5.addVertex{{\dx-0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand-!;
			var5.addVertex{{\dx+1+0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand-!;
			var5.addVertex{{\dx+1+0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand-!;

			var5.addVertex{{\dx-0.0625*expand, dy-0.0625*expand, dz-0.0625*expand-!;
			var5.addVertex{{\dx-0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand-!;
			var5.addVertex{{\dx+1+0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand-!;
			var5.addVertex{{\dx+1+0.0625*expand, dy-0.0625*expand, dz-0.0625*expand-!;

			var5.addVertex{{\dx+1+0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand-!;
			var5.addVertex{{\dx-0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand-!;
			var5.addVertex{{\dx-0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand-!;
			var5.addVertex{{\dx+1+0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand-!;
		}
		var5.draw{{\-!;

		vbnm, {{\!textured-! {
			var5.startDrawing{{\GL11.GL_LINE_LOOP-!;
			var5.setColorRGBA{{\color[0], color[1], color[2], color[3]-!;
			var5.addVertex{{\dx+1+0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand-!;
			var5.addVertex{{\dx-0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand-!;
			var5.addVertex{{\dx-0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand-!;
			var5.addVertex{{\dx+1+0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand-!;
			var5.draw{{\-!;
			var5.startDrawing{{\GL11.GL_LINE_LOOP-!;
			var5.setColorRGBA{{\color[0], color[1], color[2], color[3]-!;
			var5.addVertex{{\dx+1+0.0625*expand, dy-0.0625*expand, dz-0.0625*expand-!;
			var5.addVertex{{\dx-0.0625*expand, dy-0.0625*expand, dz-0.0625*expand-!;
			var5.addVertex{{\dx-0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand-!;
			var5.addVertex{{\dx+1+0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand-!;
			var5.draw{{\-!;
			var5.startDrawing{{\GL11.GL_LINE_LOOP-!;
			var5.setColorRGBA{{\color[0], color[1], color[2], color[3]-!;
			var5.addVertex{{\dx-0.0625*expand, dy-0.0625*expand, dz-0.0625*expand-!;
			var5.addVertex{{\dx-0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand-!;
			var5.draw{{\-!;
			var5.startDrawing{{\GL11.GL_LINE_LOOP-!;
			var5.setColorRGBA{{\color[0], color[1], color[2], color[3]-!;
			var5.addVertex{{\dx+1+0.0625*expand, dy-0.0625*expand, dz-0.0625*expand-!;
			var5.addVertex{{\dx+1+0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand-!;
			var5.draw{{\-!;
			var5.startDrawing{{\GL11.GL_LINE_LOOP-!;
			var5.setColorRGBA{{\color[0], color[1], color[2], color[3]-!;
			var5.addVertex{{\dx+1+0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand-!;
			var5.addVertex{{\dx+1+0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand-!;
			var5.draw{{\-!;
			var5.startDrawing{{\GL11.GL_LINE_LOOP-!;
			var5.setColorRGBA{{\color[0], color[1], color[2], color[3]-!;
			var5.addVertex{{\dx-0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand-!;
			var5.addVertex{{\dx-0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand-!;
			var5.draw{{\-!;
		}

		GL11.glEnable{{\GL11.GL_CULL_FACE-!;
		GL11.glDisable{{\GL11.GL_BLEND-!;
		GL11.glEnable{{\GL11.GL_DEPTH_TEST-!;
		ReikaRenderHelper.exitGeoDraw{{\-!;
	}

}
